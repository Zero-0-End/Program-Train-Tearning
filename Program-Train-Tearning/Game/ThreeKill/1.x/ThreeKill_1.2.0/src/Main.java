import java.util.List;
import java.util.Random;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        //生成玩家
        Player p1 = new Player("曹操",4);
        Player p2 = new Player("刘备",4);
        Player p3 = new Player("孙权",4);
        Player p4 = new Player("关羽",4);

        //生成牌堆
        Deck deck = new Deck();

        //随机生成一些牌加入牌堆
        for (int i=0;i<20;i++){
            Random random = new Random();
            if (random.nextInt(100)<60){
                deck.addDrawPile(new ShaCard());
            } else if (random.nextInt(100)<70) {
                deck.addDrawPile(new ShanCard());
            } else if (random.nextInt(100)<80) {
                deck.addDrawPile(new TaoCard());
            } else{
                deck.addDrawPile(new JiuCard());
            }
        }

        List players = List.of(p1,p2,p3,p4);

        Game game = new Game(players,deck);
        game.start();
    }
}