package ThreeKill;

import ThreeKill.Card.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private Player[] players;
    private Deck deck = new Deck();
    private RuleEngine rule = new RuleEngine();

    public Game(Player[] players){
        this.players = players;
        initializeDeck();
        deck.shuffle();
    }


    public void start(){
        System.out.println("\n**************** 游戏开始！*****************\n");
        System.out.println("玩家列表：");
        for(Player p: players){
            System.out.println(p.getName()+" - "+p.getIdentity());
        }

        //统计牌堆中各类牌的数量
        int shaCount = 0,shanCount=0,taoCount=0,jiuCount=0;
        for (Card card : deck.getDrawPile()){
            if (card instanceof ShaCard){
                shaCount++;
            } else if (card instanceof ShanCard) {
                shanCount++;
            } else if (card instanceof TaoCard) {
                taoCount++;
            } else if (card instanceof JiuCard) {
                jiuCount++;
            }
        }


        System.out.println("初始化牌堆...");
        System.out.println("---【杀】牌数: "+shaCount);
        System.out.println("---【闪】牌数: "+shanCount);
        System.out.println("---【桃】牌数: "+taoCount);
        System.out.println("---【酒】牌数: "+jiuCount+"\n");

        //发牌阶段:
        System.out.println("发牌阶段:");
        for (int i = 0; i < 4; i++){
            for (Player player : players){
                Card card = deck.deal();
                player.drawCard(card);
            }
        }

        int round = 0;

        //游戏主循环
        while(true){
            round++;
            System.out.println("============第 "+round+" 轮==============");

            for(Player p: players){
                if (p.isAlive()){
                    Turn turn = new Turn(this);
                    turn.start(p);
                    turn.drawPhase(p);
                    turn.playPhase(p);

                    if (rule.isGameOver(this)){
                        System.out.println("游戏结束！");
                        return;
                    }

                    turn.discardPhase(p);
                    turn.endPhase(p);
                    //打印当前各方信息
                    printStatus();

                }else{
                    System.out.println(p.getName()+"已阵亡，跳过回合。");
                    continue;
                }
            }
        }
    }

    //统计势力血量
    public int countHealth(Identity identity){
        int total = 0;
        for(Player p: players){
            if (p.getIdentity() == identity && p.isAlive()){
                total += p.getHealth();
            }
        }
        return total;
    }

    //获取势力角色
    public Player[] getIdentityPlayers(Identity identity){
        ArrayList<Player> list = new ArrayList<>();
        for(Player p: players){
            if (p.getIdentity() == identity){
                list.add(p);
            }
        }
        return list.toArray(new Player[0]);
    }


    //求援流程
    public void dangerBrodcast(Player danger,Class <? extends Card> cardClass){
        for (Player player:this.players){
            if (player.isAlive() && player != danger){
                System.out.println(danger.getName()+"向"+player.getName()+"求援.");
                if (this.rule.chooseRescueTarget(player,danger,this)){
                    System.out.println(player.getName()+"决定援助"+danger.getName());
                    while(danger.getDanger()){
                        Card card = player.findCardByType(cardClass);
                        if (card != null) {
                            player.playCard(card, danger);
                            if (card instanceof TaoCard){
                                danger.heal(1);
                            }
                            deck.addDiscardPile(card);
                        }else{
                            break;
                        }
                    }
                }else{
                    continue;
                }
            }else{
                continue;
            }
        }
        System.out.println(danger.getName()+"求援失败");
    }

    //生成牌堆
    public void initializeDeck(){
        for(int i=0;i<20;i++){
            Random random = new Random();

            if (random.nextInt(100)<40){
                deck.addCard(new ShaCard());
            }

            else if (random.nextInt(100)<50){
                deck.addCard(new ShanCard());
            }

            else if (random.nextInt(100)<80){
                deck.addCard(new TaoCard());
            }

            else {
                deck.addCard(new JiuCard());
            }
        }
    }

    private void printStatus(){
        System.out.println("\n----------当前统计----------");
        System.out.printf("%-6s %-4s %-4s %-4s %-4s\n", "玩家", "体力", "手牌", "状态","身份");
        for (Player p : players) {
            String status = p.getAlive() ? "存活" : "阵亡";
            System.out.printf("%-6s %-4d %-4d %-4s %-4s\n", p.getName(), p.getHealth(),
                    p.getHandCards().size(), status,p.getIdentity());
        }

        System.out.println("===================================");
        System.out.println("牌堆剩余牌数: " + deck.getDrawPile().size());
        System.out.println("弃牌堆牌数: " + deck.getDiscardPile().size()+"\n");
    }



    //======================== Getter ========================//

    public Deck getDeck() {
        return deck;
    }

    public RuleEngine getRule(){
        return rule;
    }

    public Player[] getPlayers(){
        return players;
    }
}



