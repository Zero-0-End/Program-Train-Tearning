import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Random rand = new Random();

        Deck deck = new Deck();
        List<Player> players = new ArrayList<>();

        //生成20张牌加入牌堆
        for(int i=0;i<20;i++){
            int num = rand.nextInt(100);//生成 0-99 的随机整数
            if (num<60){
                deck.addCard(new Card("杀"));
            }else{
                deck.addCard(new Card("桃"));
            }
        }

        //生成4名玩家
        Player p1 = new Player("曹操"); players.add(p1);
        Player p2 = new Player("刘备"); players.add(p2);
        Player p3 = new Player("孙权"); players.add(p3);
        Player p4 = new Player("关羽"); players.add(p4);

        //创建新游戏

        Game game = new Game(players,deck);
        game.gameStart();
    }
}