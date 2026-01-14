import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Player {
    private String name;
    private int health;
    private int maxHealth;
    private boolean isDanger;
    private boolean isAlive;
    private boolean isDrunk;
    private List<Card> handCards = new ArrayList<>();

    public Player(String name, int health){
        this.health = health;
        this.name = name;
        this.maxHealth = health;
        this.isAlive = true;
    }


    //受到伤害
    //受伤害后血量低于0进入濒死
    public void takeDamage(int damage){
        this.health -= damage;
        System.out.println(this.getName()+"受到"+damage+"点伤害，当前体力值为："+this.health);
        if (this.health <=0){
            this.setDanger(true);//进入濒死状态
        }
    }

    //自救
    public void rescueSelf(){
        Iterator<Card> iterator = handCards.iterator();
        while(iterator.hasNext()){
            Card card = iterator.next();
            if (card instanceof JiuCard || card instanceof TaoCard){
                System.out.println(this.getName()+"使用"+card.getName()+"回复体力");
                this.heal(1);
                iterator.remove();
                card.use(this,this);

                if (this.health> 0){
                    this.setDanger(false);//脱离濒死状态
                    return;
                }
            }
        }
    }



    //求援
    public Class<? extends Card> askForHelp(Class clazz){
        System.out.println(this.getName()+"请求救援");
        return clazz;
    }

    //恢复
    //不能超过最大体力
    public void heal(int healNum){
       if (this.health<this.maxHealth){
           this.health += healNum;
       }

       if (this.health>this.maxHealth){
           this.health = this.maxHealth;
           System.out.println(this.getName()+"体力已满");
       }
    }

    //摸牌
    public void drawCard(Card card){
        this.handCards.add(card);
        System.out.println(this.name+"摸到了 "+card.getName());
    }



    //出牌
    //出牌和弃牌动作分离
    public void playCard(Card card, Player target){
        card.use(this, target);
    }

    //弃牌
    public void disCard(Card card){
        this.handCards.remove(card);
    }

    //响应
    //按类型检索手牌
    public Card  findCardByType(Class<?extends Card> cardTYpe){
        for (Card card : handCards){
            if (cardTYpe.isInstance(card)){
                return card;
            }
        }
        return null;
    }

    //响应
    //按名称检索手牌
    public Card findCardByName(String name){
        for (Card card : handCards){
            if (card.getName().equals(name)){
                return card;
            }
        }
        return null;
    }

    public String getName(){
        return this.name;
    }
    public boolean getDanger() {
        return this.isDanger;
    }

    public void setDanger(boolean danger) {
        this.isDanger = danger;
        if (this.isDanger){
            System.out.println(this.getName()+"进入【濒死】状态");
        }else{
            System.out.println(this.getName()+"脱离【濒死】状态");
        }
    }



    public boolean getAlive() {
        return this.isAlive;
    }

    public void setAlive(boolean alive) {
        this.isAlive = alive;
        if (alive == false){
            System.out.println(this.getName()+"已【死亡】出局.");
        }
    }
    public int getHealth(){
        return this.health;
    }

    public int getMaxHealth(){
        return this.maxHealth;
    }

    public List<Card> getHandCards(){
        return this.handCards;
    }

    public boolean getDrunk(){
        return this.isDrunk;
    }

    public void setDrunk(boolean Drunk){
        this.isDrunk = Drunk;
        if (this.isDrunk){
            System.out.println(this.getName()+"处于【醉酒】状态");
        }else{
            System.out.println(this.getName()+"解除【醉酒】状态");
        }
    }
}

