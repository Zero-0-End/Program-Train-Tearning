import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> handCard = new ArrayList<>();
    private int health;

    public void drawCard(Card card){
        this.handCard.add(card);
        System.out.println(this.name+"摸到了 "+card.getName());
    }

    //手牌非空时，总是打出手中第一张牌
    public void playCar(Player source, Player target){
        if (this.handCard != null){
            Card card = this.handCard.remove(0);
            System.out.println(source.name+" 对 "+target.name+" 使用: "+card.getName());
            card.use(source,target);
        }else{
            System.out.println("手中无牌");
        }
    }

    public void loseHealth(int damage){
        this.health-=damage;
        System.out.println(this.name+" 受到 "+damage+ " 点伤害，当前体力值为："+this.health);
    }

    public void heal(int healNum){
        this.health += healNum;
        System.out.println(this.name+" 回复体力，当前体力为： "+this.health);
    }

    public Player(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
