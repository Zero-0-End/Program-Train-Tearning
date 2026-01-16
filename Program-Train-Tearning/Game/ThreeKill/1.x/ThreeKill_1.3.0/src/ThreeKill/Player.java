package ThreeKill;

import java.util.ArrayList;
import ThreeKill.Card.Card;
import java.util.List;

public class Player {
    private String name;
    private int health;
    private int maxHealth;
    private boolean isDanger;
    private boolean isAlive;
    private boolean isDrunk;
    private List<Card> handCards;
    private Identity identity;

    public Player(String name, int health,Identity identity) {
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.identity = identity;
        this.isDanger = false;
        this.isAlive = true;
        this.isDrunk = false;
        this.handCards = new ArrayList<>();
    }

    public void takeTamage(int damage){
        this.health -= damage;
        System.out.println(this.getName()+"受到"+damage+"点伤害，当前体力值为："+this.health);
        if (this.health <=0){
            this.setDanger(true);//进入濒死状态
        }
    }

    public void heal(int num){
        if (this.health<this.maxHealth){
            this.health += num;
            //处理超额回复
            if (this.health>=this.maxHealth){
                this.health = this.maxHealth;
                System.out.println(this.getName()+"体力已满！");
            }else{
                System.out.println(this.getName()+"回复"+num+"点体力，当前体力值为："+this.health);
            }
        //处理体力已满的情况
        }else{
            System.out.println(this.getName()+"体力已满！");
        }
    }

    //摸牌
    //只管摸, 不管从哪里来
    public void drawCard(Card card){
        this.handCards.add(card);
        System.out.println(this.getName()+"摸到牌: "+card.getName());
    }

    //出牌
    //只管出,不管出的去哪里
    //返回被出的牌, 若无此牌则返回null
    public Card playCard(Card card, Player target){
        //contains 是判定引用是否一致,一致则是同一个对象
        if (handCards.contains(card)){
            card.use(this, target);
            this.handCards.remove(card);//主要作用，使用后从手牌中移除
            return card;
        }else{
            System.out.println(this.getName()+"没有相应的牌: "+card.getName());
            return null;
        }
    }

    //响应
    public Card respondCard(Card card,Player source){
        System.out.println(this.getName()+"响应"+source.getName()+"的请求");
        return this.playCard(card, source);
    }

    //弃牌,只管丢,不管丢到哪里
    // 返回Card给其他系统处理
    public Card discardCard(Card card){
        if (handCards.remove(card)){
            System.out.println(this.getName()+"弃掉牌: "+card.getName());
            return card;
        }else{
            System.out.println(this.getName()+"没有这张牌: "+card.getName());
            return null;
        }
    }


    //按类型检索手牌
    public Card findCardByType(Class<? extends Card> clazz){
        for (Card card: handCards){
            if (clazz.isInstance(card)){
                return card;
            }

        }
        return null;
    }



    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public boolean isDanger() {
        return isDanger;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isDrunk() {
        return isDrunk;
    }


    //============ Getter ==========//

    public List<Card> getHandCards() {
        return handCards;
    }

    public boolean getDrunk() {

        return isDrunk;
    }

    public boolean getAlive() {
        return isAlive;
    }

    public Identity getIdentity() {
        return identity;
    }

    public boolean getDanger() {
        return isDanger;
    }
    //========== Setter ==========//
    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setDanger(boolean danger) {
        isDanger = danger;
        if (danger) {
            System.out.println(this.getName() + "进入【濒死】状态");
        } else {
            System.out.println(this.getName() + "脱离【濒死】状态");
        }
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
        if (alive == false){
            System.out.println(this.getName()+"已【死亡】出局.");
        }
    }

    public void setDrunk(boolean drunk) {
        isDrunk = drunk;
        if (drunk){
            System.out.println(this.getName()+"进入【醉酒】状态");
        }else{
            System.out.println(this.getName()+"解除【醉酒】状态");
        }
    }
}
