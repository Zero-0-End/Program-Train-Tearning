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

        int index = 0;
        int round = 1;
        while ( true){

            int alive = 0;
            for (Player player:players){
                if (player.getAlive()){
                    alive++;
                }
            }
            System.out.println("存活"+alive+"名玩家。");
            //仅剩一名玩家结束游戏
            if (alive<=1){
                break;
            }


            //玩家顺序轮流
            Player source = players.get(index);
            Player target = source.findTarget(players);


            index = (index+1)%players.size();

            System.out.println("-------第 "+round+" 回合-------");
            round++;
            System.out.println("当前玩家: "+source.getName());
            //发牌、摸牌、出牌、弃牌
            if (!source.getAlive()){
                System.out.println(source.getName()+"已死亡,下一个玩家行动.");
                continue;
            }
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

            if (target.getHealth() <=0) {
                target.setDanger(true);
                System.out.println(target.getName()+"进入濒死状态；");
                if (target.rescue(deck)) {
                    System.out.println(target.getName() + " 被救活了！");
                } else {
                    System.out.println(target.getName() + " 阵亡了！");
                    target.setAlive(false);
                }
            }

            System.out.println(players.get(0).getName()+players.get(0).getAlive());
            System.out.println(players.get(1).getName()+players.get(1).getAlive());
            System.out.println(players.get(2).getName()+players.get(2).getAlive());
            System.out.println(players.get(3).getName()+players.get(3).getAlive());

        }

        int winnerIndex = -1;
        for (Player player:players
             ) {
            if (player.getAlive()){
                winnerIndex = players.indexOf(player);
            }
        }
        System.out.println("游戏结束！"+players.get(winnerIndex).getName()+" 获胜！");
    }
}
