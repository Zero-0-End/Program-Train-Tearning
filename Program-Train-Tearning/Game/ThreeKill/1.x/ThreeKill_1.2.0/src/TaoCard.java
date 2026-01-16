public class TaoCard extends Card{
    public TaoCard(){
        super("桃");
    };


    @Override
    public void use(Player source, Player target) {
        System.out.println(source.getName()+"对"+target.getName()+"使用【桃】, "+target.getName()+"回复一点体力.");
        source.heal(1);
    }
}
