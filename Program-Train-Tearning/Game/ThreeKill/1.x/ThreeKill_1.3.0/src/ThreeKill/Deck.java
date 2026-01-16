package ThreeKill;
import java.util.ArrayList;
import java.util.Collections;
import ThreeKill.Card.Card;
import java.util.List;

public class Deck {
    private final List<Card> drawPile = new ArrayList<>();
    private final List<Card> discardPile = new ArrayList<>();

    public void addCard(Card card){
        this.drawPile.add(card);
        System.out.println("牌堆加入 "+card.getName());
    }

    public void addCards(List<Card> cards){
        this.drawPile.addAll(cards);
        System.out.println("牌堆加入 "+cards.size()+" 张牌");
    }

    public void shuffle(){
        Collections.shuffle(this.drawPile);
        System.out.println("牌堆已洗牌");
    }

    public void reshuffle(){
        if (!discardPile.isEmpty()){
            drawPile.addAll(discardPile);
            discardPile.clear();
            shuffle();
        }
    }

    public Card deal(){
        if (this.drawPile.isEmpty()){
            this.reshuffle();
        }
        if (this.drawPile.isEmpty()) {
            System.out.println("牌堆牌不够了！");
            return null;
        }
        Card card = this.drawPile.remove(0);
        System.out.println("牌堆发出 "+card.getName());
        return card;
    }

    public void addDiscardPile(Card card){
        this.discardPile.add(card);
        System.out.println(card.getName()+"进入弃牌堆");
    }

    public int getDrawPileSize() {

        return this.drawPile.size();
    }

    public int getDiscardPileSize() {
        return this.discardPile.size();
    }

    public List<Card> getDrawPile(){
        return this.drawPile;
    }

    public List<Card> getDiscardPile(){
        return this.discardPile;
    }

}
