import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> handCard = new ArrayList<>();
    private int health;
    private boolean isDanger;//濒死状态
    private boolean isDrunk;
    private boolean isAlive;

    public void drawCard(Card card){
        this.handCard.add(card);
        System.out.println(this.name+"摸到了 "+card.getName());
    }

    //手牌非空时，总是打出手中第一张牌
    public void playCard(Player source, Player target){
        if (!this.handCard.isEmpty()){
            Card card = this.handCard.remove(0);
            System.out.println(source.name+" 对 "+target.name+" 使用: "+card.getName());
            card.use(source,target);
        }else{
            System.out.println("手中无牌");
        }
    }


    public boolean dodge(){
        for (Card card : handCard){
            if (card.getName().equals("闪")){
                handCard.remove(card);
                System.out.println(this.name+" 使用闪抵消"+" '杀'");
                return true;
            }
        }
        return false;
    }

    public void loseHealth(int damage){
        this.health-=damage;
        System.out.println(this.name+" 受到 "+damage+ " 点伤害，当前体力值为："+this.health);
    }

    public void heal(int healNum){
        this.health += healNum;
        System.out.println(this.name+" 回复体力，当前体力为： "+this.health);
    }

    public void rescue(Player source, Player target){
        System.out.println(source.getName()+"对"+target.getName()+"进行救援");

    }
    public Player(String name, int health) {
        this.health = health;
        this.name = name;

    }

    public String getName() {
        return name;
    }
}
