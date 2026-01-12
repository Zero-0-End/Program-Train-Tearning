import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {
    private String name;
    private List<Card> handCard = new ArrayList<>();
    private int health;
    private boolean isDanger=false;//濒死状态
    private boolean isDrunk=false;
    private boolean isAlive=true;

    public void drawCard(Card card){
        this.handCard.add(card);
        System.out.println(this.name+"摸到了 "+card.getName());
    }

    //出牌
    //杀的目标是另一个玩家,酒和桃的目标是自己
    //不能主动出闪
    public void playCard(Player source, Player target, Deck deck){
        if (!this.isAlive){
            System.out.println(this.name+" 已阵亡，无法出牌");
            return;
        }else {
            if (!this.handCard.isEmpty()){
                //遍历手牌打出非闪牌
                Iterator<Card> iterator = this.handCard.iterator();
                while (iterator.hasNext()){
                    Card card =iterator.next();
                    if (!card.getName().equals("闪")){
                        if (card.getName().equals("杀")){
                            System.out.println(source.name+" 对 "+target.name+" 使用: "+card.getName());
                        } else if (card.getName().equals("桃")){
                            System.out.println(source.name+" 使用: "+card.getName());
                        }else if (card.getName().equals("酒")){
                            System.out.println(source.name+" 使用: "+card.getName());
                        }
                        else if (!card.getName().equals("闪")){
                            System.out.println(source.name+" 使用未知定义牌: "+card.getName());
                        }

                        card.use(source,target,deck);
                        iterator.remove();
                    }
                }
            }else{
                System.out.println("手中无可用牌");
            }
        }

    }


    //寻找目标
    public Player findTarget(List<Player> players){
        Player taregt = new Player("",0);
        for (int i=0;i<100;i++){
            //给他100张手牌,用于寻找手牌最少的玩家
            taregt.handCard.add(new Card("占位牌"));
        }
        for (Player player : players){
            if (player != this && player.getAlive()){
                if (player.getHandCardSize() < taregt.getHandCardSize()){
                    taregt = player;
                }
            }
        }
        return taregt;
    }


    //响应
    //被动出闪
    public boolean dodge(Deck deck){
        for (Card card : handCard){
            if (card.getName().equals("闪")){
                handCard.remove(card);
                System.out.println(this.name+" 使用闪抵消"+" '杀'");
                deck.addDiscardPile(card);
                return true;
            }
        }
        return false;
    }


    //弃牌
    public void disCard(Deck deck){
        while(handCard.size()>this.health && !handCard.isEmpty()){
            Card card = handCard.remove(0);
            System.out.println(this.name+" 弃掉了 "+card.getName());
            deck.addDiscardPile(card);
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

    public boolean rescue(Deck deck){
        for (Card card : handCard){
            if (card.getName().equals("桃") || card.getName().equals("酒")){
                card.use(this,this,deck);
                System.out.println(this.getName()+"进行救援");
                return true;
            }
        }
        this.setAlive(false);
        return false;
    }


    public Player(String name, int health) {
        this.health = health;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setDrunk(boolean drunk) {
        isDrunk = drunk;
    }

    public void setDanger(boolean danger) {
        isDanger = danger;
    }



    public boolean getDrunk(){
        return this.isDrunk;
    }

    public boolean getAlive(){
        return this.isAlive;
    }
    public int getHealth(){
        return this.health;
    }

    public int getHandCardSize(){
        return this.handCard.size();
    }
}
