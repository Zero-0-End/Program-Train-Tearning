import java.util.Iterator;
import java.util.List;

public class Turn {
    //1个回合内各阶段
    public void executeTurn(Player currentPlayer, List<Player> players, Deck deck) {
        System.out.println("========= "+currentPlayer.getName() + "的回合=========");

        if (!currentPlayer.getAlive()) {
            System.out.println(currentPlayer.getName() + " 已阵亡，跳过回合。");
            return;

        //存活时进入摸牌阶段
        }else{

            //摸牌阶段,摸两张牌
            for (int i = 0; i < 2; i++) {
                if (deck.getDrawPileEmpty()) {
                    deck.reshuffle();
                }
                Card card = deck.deal();
                currentPlayer.drawCard(card);
            }

            //出牌阶段, 尽可能多地出牌
            //有杀有酒 出 “酒杀”
            //有杀无酒 出 “杀”
            //能回血就回血
            boolean isShaUsed = false;

            //从手牌查找杀\酒\桃
            Card sha = (!isShaUsed) ? currentPlayer.findCardByType(ShaCard.class): null;
            Card jiu = currentPlayer.findCardByType(JiuCard.class);

            //查找目标
            Player target = findTarget(currentPlayer, players);

            //杀
            if (sha != null) {
                if (target != null) {
                    if (jiu != null) {
                        currentPlayer.playCard(jiu, currentPlayer);//喝酒
                        currentPlayer.disCard(jiu);//打出的牌视为丢弃
                        deck.addDiscardPile(jiu);//弃牌进入弃牌堆
                    }

                    //出杀
                    currentPlayer.playCard(sha, target);
                    currentPlayer.disCard(sha);
                    deck.addDiscardPile(sha);
                    isShaUsed = true;

                    //响应阶段:目标尝试出闪
                    Card shan = target.findCardByType(ShanCard.class);
                    if (shan != null) {
                        //检索是否有闪
                        //有则闪避
                        target.playCard(shan, currentPlayer);
                        target.disCard(shan);
                        deck.addDiscardPile(shan);

                        System.out.println(target.getName() + " 未受到伤害");
                    //没有闪, 则结算伤害
                    } else {
                        int damage = currentPlayer.getDrunk()?2:1;
                        target.takeDamage(damage);

                        //检查目标是否进入濒死
                        if (target.getHealth() <= 0) {
                            target.setDanger(true);

                            //尝试求援, 设置25%概率
                            int probability = 25;
                            Class clazz = target.askForHelp(TaoCard.class);

                            //遍历玩家和手牌，直到回血成功
                            //不成功则出局
                            for (Player player: players
                            ) {
                                if (player == currentPlayer || !player.getAlive()) {
                                    continue;
                                }
                                if (Math.random() * 100 > probability) {
                                    continue;
                                }

                                Iterator<Card> iterator = player.getHandCards().iterator();
                                while (iterator.hasNext()) {
                                    Card card = iterator.next();
                                    if (clazz.isInstance(card)) {
                                        target.heal(1);
                                        iterator.remove();

                                        player.playCard(card, target);
                                        player.disCard(card);
                                        deck.addDiscardPile(card);

                                        //检查是否脱离濒死状态
                                        if (target.getHealth() > 0) {
                                            target.setDanger(false);//脱离濒死状态
                                            break;
                                        }
                                    }
                                }
                                if (!target.getDanger()) {
                                    break;
                                }
                            }


                            //没有求援成功则自救
                            if (target.getDanger()){
                                target.rescueSelf();

                                //没有自救成功则出局
                                if (target.getDanger()){
                                    target.setAlive(false);
                                }
                            }
                        }
                    }

                } else {
                    System.out.println("无可用目标，无法出杀");
                }
            }

            //回血
            while(true){
                Card tao = currentPlayer.findCardByType(TaoCard.class);
                if (tao != null && currentPlayer.getHealth() < currentPlayer.getMaxHealth()) {
                    currentPlayer.playCard(tao, currentPlayer);
                    currentPlayer.disCard(tao);
                    deck.addDiscardPile(tao);
                } else {
                    break;
                }
            }

            //弃牌阶段
            while(currentPlayer.getHandCards().size() > currentPlayer.getHealth()) {
                Card card = currentPlayer.getHandCards().remove(0);
                System.out.println(currentPlayer.getName() + " 弃置 " + card.getName());
                deck.addDiscardPile(card);
            }

            System.out.println("============ " + currentPlayer.getName() + " 回合结束 ==========");


        }


    }

    //寻找目标
    //以手牌最少的玩家为目标
    public Player findTarget(Player currentPlayer, List<Player> players) {
        Player target = null;//如果没找到，则返回空

        int minHandSize = Integer.MAX_VALUE;//使用最大值来迭代查找

        for (Player indexPlayer : players) {
            if (indexPlayer != currentPlayer && indexPlayer.getAlive()) {
                if (indexPlayer.getHandCards().size() < minHandSize) {
                    minHandSize = indexPlayer.getHandCards().size();
                    target = indexPlayer;
                }
            }
        }
        return target;
    }
}
