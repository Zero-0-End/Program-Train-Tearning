package ThreeKill;

import ThreeKill.Card.Card;

import java.util.stream.StreamSupport;

public class Turn {
    private Game game;
    public Turn(Game game){
        this.game = game;
    }

    //回合开始
    public void start(Player player){
        System.out.println("---------"+player.getName()+"的回合"+"-------------");
    }

    //摸牌阶段
    public void drawPhase(Player player){
        System.out.println(player.getName()+"进入摸牌阶段");
        for (int i=0;i<2;i++){
            Card card = game.getDeck().deal();
            player.drawCard(card);
        }
    }

    //出牌阶段
    public void playPhase(Player player){
        System.out.println(player.getName()+"进入出牌阶段");
        game.getRule().chooseCardToPlay(player,game);
    }

    //弃牌阶段
    public void discardPhase(Player player){
        System.out.println(player.getName()+"进入弃牌阶段");
        if (player.getHandCards().size() <= player.getHealth()){
            System.out.println(player.getName()+"不需要弃牌，当前手牌数："+player.getHandCards().size()+"，体力值："+player.getHealth());
            return;
        }
        while(player.getHandCards().size() > player.getHealth()){
            System.out.println(player.getName()+"需要弃牌，当前手牌数："+player.getHandCards().size()+"，体力值："+player.getHealth());
            Card card = game.getRule().chooseCardToDiscard(player);
            player.discardCard(card);
            game.getDeck().addDiscardPile(card);
        }
    }

    //结束阶段
    public void endPhase(Player player){
        System.out.println(player.getName()+"回合结束");
    }

}
