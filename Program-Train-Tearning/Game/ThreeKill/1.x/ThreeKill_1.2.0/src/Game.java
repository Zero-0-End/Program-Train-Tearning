import java.sql.SQLSyntaxErrorException;
import java.util.List;

public class Game {
    private List<Player> players;
    private Deck deck;
    private Turn turn;
    private int round;//记录当前是第几轮
    public Game(List<Player> players, Deck deck){
        this.players = players;
        this.deck = deck;
        this.turn = new Turn();
    }

    public void start(){
        round =1;

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


        System.out.println("\n**************** 游戏开始！*****************\n");
        System.out.println("初始化牌堆...");
        System.out.println("---【杀】牌数: "+shaCount);
        System.out.println("---【闪】牌数: "+shanCount);
        System.out.println("---【桃】牌数: "+taoCount);
        System.out.println("---【酒】牌数: "+jiuCount+"\n");


        //
        System.out.println("发牌阶段:");

        for (int i = 0; i < 4; i++){
            for (Player player : players){
                if (deck.getDrawPileEmpty()){
                    deck.reshuffle();
                }
                Card card = deck.deal();
                player.drawCard(card);
            }
        }

        while (countAlivePlayers() > 1){
            System.out.println("------------第 "+round+" 轮------------");
            for (Player player : players){
                turn.executeTurn(player, players, deck);
                printStatus();
                if (countAlivePlayers() <= 1){
                    break;
                }
            }
            round++;
        }
        announceWinner();
    }


    //计算存活玩家数量
    public int countAlivePlayers(){
        int count = 0;
        for (Player player : players){
            if (player.getAlive()){
                count++;
            }
        }
        return count;
    }


    private void printStatus(){
        System.out.println("\n----------当前统计----------");
        System.out.printf("%-6s %-4s %-4s %-6s\n", "玩家", "体力", "手牌", "状态");
        for (Player p : players) {
            String status = p.getAlive() ? "存活" : "阵亡";
            System.out.printf("%-6s %-4d %-4d %-6s\n", p.getName(), p.getHealth(),
                    p.getHandCards().size(), status);
        }

        System.out.println("===================================");
        System.out.println("牌堆剩余牌数: " + deck.getDrawPile().size());
        System.out.println("弃牌堆牌数: " + deck.getDiscardPile().size()+"\n");
    }

    public void announceWinner(){
        for (Player player : players){
            if (player.getAlive()){
                System.out.println("游戏结束！获胜者是: " + player.getName());
                return;
            }
        }
        System.out.println("游戏结束！没有获胜者。");
    }
}
