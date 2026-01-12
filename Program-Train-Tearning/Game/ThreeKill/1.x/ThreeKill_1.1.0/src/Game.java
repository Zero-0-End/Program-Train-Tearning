import java.util.List;

public class Game {
    private List<Player> players;
    private Deck deck;
    public Game(List<Player> players, Deck deck){
        this.players = players;
        this.deck = deck;
    }

    //按照输入的回合数进行游戏
    public void gameStart(){
        System.out.println("-------游戏开始！-------");

        int index = 0;//玩家索引
        while ( !this.deck.isEmpty()){
            //玩家顺序轮流
            Player source = players.get(index);
            Player target = players.get((index+1)%players.size());
            index = (index+1)%players.size();

            //发牌、摸牌、出牌
            Card card = this.deck.deal();
            source.drawCard(card);
            source.playCard(source,target);
        }
        System.out.println("游戏结束！");
    }
}
