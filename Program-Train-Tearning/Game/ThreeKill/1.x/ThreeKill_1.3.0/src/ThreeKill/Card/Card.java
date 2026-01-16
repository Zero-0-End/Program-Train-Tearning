package ThreeKill.Card;
import ThreeKill.Player;
public abstract class Card {
    private String name;


    public Card(String name){
        this.name = name;
    }

    public  String getName(){
        return this.name;
    };

    public void use(Player source, Player target){
        System.out.println(source.getName()+"对"+target.getName()+"使用了【"+this.getName()+"】");
    }

}


