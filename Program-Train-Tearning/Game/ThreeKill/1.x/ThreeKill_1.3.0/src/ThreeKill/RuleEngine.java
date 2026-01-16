package ThreeKill;

import ThreeKill.Card.*;

import java.util.HashMap;
import java.util.Iterator;

import static ThreeKill.Identity.*;

/**
 *RuleEngine: 负责所有规则策略、判定、效果结算
 *不负责回合流程，交给Turn
 *不负责状态存储，交给Player
 *不负责卡牌效果触发，交给Card，但负责卡牌效果的结算
 *
 */
public class RuleEngine {

    public RuleEngine(){

    }

    //============
    //策略
    //============

    //出牌策略
    //有酒有杀出酒杀
    //有杀无酒出杀
    //血量不满出桃
    //尽可能多出牌
    //每回合只能出一张杀
    public void chooseCardToPlay(Player player, Game game) {
        Card jiu = player.findCardByType(JiuCard.class);
        Card sha = player.findCardByType(ShaCard.class);


        if (sha != null){
            if (jiu != null){
                player.playCard(jiu, player);
            }
            Player target = chooseAttacTarget(player, game);
           //System.out.println(target.getName());
            player.playCard(sha, target);
            game.getDeck().addDiscardPile(sha);

            //目标响应杀
            this.shaCardSettlement(player, target, sha,game);
            if (target.getDanger()){
               this.dangerSettlement(target, game);
            }
        }

        while(player.getHealth() < player.getMaxHealth()){
            Card tao = player.findCardByType(TaoCard.class);
            if (tao != null ){
                player.playCard(tao, player);
                player.heal(1);
                game.getDeck().addDiscardPile(tao);
            }else{
                break;
            }
        }
    }

    //攻击目标选择策略
    //反贼总是攻击主公
    //忠臣和主公优先攻击反贼内奸手牌更少的，一样则攻击反贼
    //内奸根据血量平衡选择攻击目标
    public Player chooseAttacTarget(Player source, Game game) {
        if (source.getIdentity().toString().equals("REBEL")){
            return game.getIdentityPlayers(LORD)[0];
        } else if (source.getIdentity().toString().equals("LORD") ||
                source.getIdentity().toString().equals("LOYALIST")) {
            //主忠优先攻击反贼手牌更少的，一样则攻击反贼
            int rebelHandCards = game.getIdentityPlayers(Identity.REBEL)[0].getHandCards().size();
            int traitorHandCards = game.getIdentityPlayers(TRAITOR)[0].getHandCards().size();

            if (!game.getIdentityPlayers(TRAITOR)[0].getAlive() && game.getIdentityPlayers(Identity.REBEL)[0].getAlive()){
                return game.getIdentityPlayers(Identity.REBEL)[0];

            } else if (!game.getIdentityPlayers(Identity.REBEL)[0].getAlive() && game.getIdentityPlayers(TRAITOR)[0].getAlive()){
                return game.getIdentityPlayers(TRAITOR)[0];
            }else {
                if (rebelHandCards > traitorHandCards) {
                    return game.getIdentityPlayers(TRAITOR)[0];
                } else {
                    return game.getIdentityPlayers(REBEL)[0];
                }
            }
        }else {
            //内奸根据血量平衡选择攻击目标
            //主忠>=反贼+内奸时 攻击主忠
            //主忠<反贼+内奸时 有反贼攻击反贼，否则攻击主公
            if (game.countHealth(LORD) + game.countHealth(Identity.LOYALIST) >=
                    game.countHealth(Identity.REBEL) + game.countHealth(Identity.TRAITOR)){
                if (game.countHealth(LORD)>=game.countHealth(LOYALIST)){
                    return game.getIdentityPlayers(LORD)[0];
                }else{
                    return  game.getIdentityPlayers(Identity.LOYALIST)[0];
                }
            } else {
                if (game.getIdentityPlayers(Identity.REBEL)[0].getAlive()){
                    return game.getIdentityPlayers(Identity.REBEL)[0];
                }else{
                    return game.getIdentityPlayers(Identity.LORD)[0];
                }
            }
        }
    }

    //援救目标策略、是否援救
    //主忠互救
    //反贼除了自己谁也不救
    //内奸根据血量平衡救援
    public boolean chooseRescueTarget(Player source, Player target,Game game) {
        if (source.getIdentity().toString().equals("LORD")){
            if(target.getIdentity().equals("LOYALIST")){
                return true;
            }
        } else if (source.getIdentity().toString().equals("LOYALIST")) {
            if (target.getIdentity().equals("LORD")){
                return true;
            }

        } else if (source.getIdentity().toString().equals("REBEL")) {
                return false;
        } else if (source.getIdentity().toString().equals("TRAITOR")) {
            //内奸根据血量平衡救援
            //主忠<反贼+内奸时救, 只救主公
            //主忠>= 反贼+内奸时 救反贼
            if (game.countHealth(LORD) + game.countHealth(Identity.LOYALIST) <
                    game.countHealth(Identity.REBEL) + game.countHealth(Identity.TRAITOR)){
                if (target.getIdentity().toString().equals("LORD")){
                    return true;
                }
            } else if (game.countHealth(LORD) + game.countHealth(Identity.LOYALIST) >
                    game.countHealth(Identity.REBEL) + game.countHealth(Identity.TRAITOR)){
                if (target.getIdentity().toString().equals("TRAITOR")){
                    return true;
                }
            }
        }
        return false;
    }

    //弃牌策略
    //优先弃置重复的杀和闪
    //无重复随意弃置
    public Card chooseCardToDiscard(Player player) {
        int shaHaandCards = 0;
        int shanHaandCards = 0;
        for (Card card: player.getHandCards()){
            if (card.getClass() == ShaCard.class){
                shaHaandCards += 1;
                if (shaHaandCards > 1){
                    return card;
                }
            }

            if (card.getClass() == ShanCard.class) {
                shanHaandCards += 1;
                if (shanHaandCards > 1) {
                    return card;
                }
            }
        }
        //没有重复的杀和闪则弃置第一张牌
        return player.getHandCards().get(0);
    }


    //=========
    //规则
    //判定与结算
    //=========

    //杀的具体响应逻辑
    public void shaCardSettlement(Player source, Player target, Card card,Game game) {
        //如果有闪,则出闪抵消
        //如果没闪,则根据醉酒结算伤害
        Card shan = target.findCardByType(ShanCard.class);
        if (shan!=null){
            target.playCard(shan,source);
            game.getDeck().addDiscardPile(shan);
        }else{
            if (source.getDrunk()){
                System.out.println(source.getName()+"处于醉酒状态，造成额外1点伤害");
                target.takeTamage(2);
                source.setDrunk(false);//杀出后解除醉酒状态
            }else{
                target.takeTamage(1);
            }

        }
    }

    //濒死判定与处理规则
    //尽可能出多张援救
    public void dangerSettlement(Player danger, Game game){
        //优先自救
        Card tao = danger.findCardByType(TaoCard.class);
        while(tao!=null && danger.getHealth()<=0){
            danger.playCard(tao, danger);
            danger.heal(1);
            game.getDeck().addDiscardPile(tao);
            tao = danger.findCardByType(TaoCard.class);
        }
        if (danger.getHealth()>0){
            System.out.println(danger.getName()+"自救成功.");
            danger.setDanger(false);
            return;
        }

        //其次 Game 广播求援
        game.dangerBrodcast(danger, TaoCard.class);

        if (danger.getHealth()<=0) {
            //仍然濒死则死亡
            danger.setAlive(false);
            System.out.println(danger.getName() + "死亡！");

            Iterator<Card> iterator = danger.getHandCards().iterator();
            while (iterator.hasNext()) {
                Card card = iterator.next();
                iterator.remove();
                game.getDeck().addDiscardPile(card);
            }
        }else{
            System.out.println(danger.getName()+"被成功救援");
            danger.setDanger(false);
        }
    }


    //游戏结束判定
    public boolean isGameOver(Game game){
        HashMap<Identity, Boolean> aliveIdentities = new HashMap<>();
        for (Player p: game.getPlayers()){
            if (p.getAlive()){
                aliveIdentities.put(p.getIdentity(), true);
            }else{
                aliveIdentities.putIfAbsent(p.getIdentity(), false);
            }
        }

        if (aliveIdentities.get(LORD) == false &&
                aliveIdentities.get(REBEL) == true){
            System.out.println("反贼阵营获胜！");
            return true;
        }

        if (aliveIdentities.get(LORD) == true &&
                aliveIdentities.get(REBEL) == false && aliveIdentities.get(TRAITOR) == false){
            System.out.println("主公与忠臣阵营获胜！");
            return true;
        }

        if (aliveIdentities.get(LORD) == false &&
                aliveIdentities.get(REBEL) == false && aliveIdentities.get(TRAITOR) == true){
            System.out.println("内奸获胜！");
            return true;
        }
        return false;
    }

}

