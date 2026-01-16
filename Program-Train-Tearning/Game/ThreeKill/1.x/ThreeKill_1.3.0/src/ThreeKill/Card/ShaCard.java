package ThreeKill.Card;

import ThreeKill.Player;

public class ShaCard extends Card{

    public ShaCard() {
        super("杀");
    }

    //只管打印使用信息
    //不管具体逻辑
    //逻辑在 RuleEngine中处理
    public void use(Player source, Player target){
        System.out.println(source.getName()+"对"+target.getName()+"使用【杀】");
    }
}
