import ThreeKill.Game;
import ThreeKill.Player;

import static ThreeKill.Identity.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
    //生成玩家
        Player p1 = new Player("曹操",4, TRAITOR);
        Player p2 = new Player("刘备",4,LORD);
        Player p3 = new Player("孙权",5,REBEL);
        Player p4 = new Player("关羽",4,LOYALIST);

        Player[] players = {p1,p2,p3,p4};

        //生成游戏
        Game game = new Game(players);

        game.start();

    }



}