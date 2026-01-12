import java.util.List;

public class Game {
    private List<Player> players;
    private Deck deck;
    public Game(List<Player> players, Deck deck){
        this.players = players;
        this.deck = deck;
    }

    //按照输入的回合数进行游戏
    public void gameStart(int rounds){
        System.out.println("-------游戏开始！-------");


        //每人发4张牌
        for (Player player: this.players){
            for (int i=0;i<4;i++){
                if (this.deck.isEmpty()){
                    this.deck.reshuffle();
                }
                Card card = this.deck.deal();
                player.drawCard(card);
            }
        }

        int round = 0;
        int index = 0;//玩家索引
        while ( round < rounds){
            int alive = 0;
            for (Player player:players){
                if (player.getAlive()){
                    alive++;
                }
            }
            //仅剩一名玩家结束游戏
            if (alive<=1){
                break;
            }
            round++;

            //玩家顺序轮流
            Player source = players.get(index);
            Player target = source.findTarget(players);


            index = (index+1)%players.size();

            System.out.println("-------第 "+round+" 回合-------");
            System.out.println("当前玩家: "+source.getName());
            //发牌、摸牌、出牌、弃牌
            for (int i=0;i<2;i++){
                if (this.deck.isEmpty()){
                    this.deck.reshuffle();
                }
                Card card = this.deck.deal();
                source.drawCard(card);
            }

            source.playCard(source,target,deck);
            source.disCard(deck);
            System.out.println(source.getName()+" 生命="+source.getHealth()+" 手牌="+source.getHandCardSize());

            if (target.getHealth() ==0) {
                target.setDanger(true);
                System.out.println(target.getName()+"进入濒死状态；");
                if (target.rescue(deck)) {
                    System.out.println(target.getName() + " 被救活了！");
                } else {
                    System.out.println(target.getName() + " 阵亡了！");
                    players.remove(target);
                }
            }

        }
        System.out.println("游戏结束！"+players.get(0).getName()+" 获胜！");
    }
}
