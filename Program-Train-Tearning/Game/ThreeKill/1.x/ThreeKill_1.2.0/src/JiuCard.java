public class JiuCard extends Card {
    public JiuCard(){
        super("酒");
    };

    @Override
    public void use(Player source, Player target) {
        if (source.getDanger()){
            System.out.println(source.getName()+"在【濒死阶段】使用【酒】,回复一点体力.");
            source.heal(1);
        }else{
            System.out.println(source.getName()+"使用【酒】,进入【醉酒】状态,下一次【杀】造成伤害+1");
        }

    }
}
