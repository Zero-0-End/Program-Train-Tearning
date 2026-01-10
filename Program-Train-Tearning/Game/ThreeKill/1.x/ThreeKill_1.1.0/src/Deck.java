import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> drawPile = new ArrayList<>();

    public void addCard(Card card){
        System.out.println("牌堆加入 "+card.getName());
        this.drawPile.add(card);
    }


    //判断牌堆是否为空
    public boolean isEmpty(){
        return this.drawPile.isEmpty();
    }

    //总是发出第一张牌
    public Card deal(){
        Card card = this.drawPile.remove(0);
        System.out.println("牌堆发出 "+card.getName());
        return card;
//        if (!this.isEmpty()){
//            Card card = this.drawPile.remove(0);
//            System.out.println("牌堆加入 "+card.getName());
//            return card;
//        }else{
//            System.out.println("牌堆无牌！");
//            return null;
//        }

    }
}
