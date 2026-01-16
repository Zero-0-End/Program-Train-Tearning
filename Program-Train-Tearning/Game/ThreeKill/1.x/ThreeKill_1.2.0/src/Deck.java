import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> drawPile = new ArrayList<>();
    private List<Card> discardPile = new ArrayList<>();

    //牌进入牌堆
    public void addDrawPile(Card card){
        //System.out.println("牌堆加入 "+card.getName());
        this.drawPile.add(card);
    }

    //牌进入弃牌堆
    public void addDiscardPile(Card card){
        //System.out.println(card.getName()+"进入弃牌堆");
        this.discardPile.add(card);
    }

    //发牌
    public Card deal(){
        if (this.drawPile.isEmpty()){
            this.reshuffle();
        }
        Card card = this.drawPile.remove(0);
        System.out.println("牌堆发出 "+card.getName());
        return card;
    }

    //洗牌
    public void reshuffle(){
        if (!this.discardPile.isEmpty()){
            Collections.shuffle(discardPile);
            this.drawPile.addAll(this.discardPile);
            this.discardPile.clear();
            System.out.println("洗牌完成，牌堆重新生成.");
        }else{
            System.out.println("无牌可洗！");
        }
    }

    public List<Card> getDrawPile(){
        return this.drawPile;
    }

    public List<Card> getDiscardPile(){
        return this.discardPile;
    }
    public boolean getDrawPileEmpty(){
        return this.drawPile.isEmpty();
    }

}
