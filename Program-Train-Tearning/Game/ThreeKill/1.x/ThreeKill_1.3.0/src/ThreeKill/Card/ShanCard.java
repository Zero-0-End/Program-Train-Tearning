package ThreeKill.Card;

import ThreeKill.Player;

public class ShanCard extends Card{

    public ShanCard() {
        super("闪");
    }

    public void use(Player source, Player target){
        System.out.println(source.getName()+"对"+target.getName()+"使用【闪】, 抵消一次 【杀】");
    }
}
