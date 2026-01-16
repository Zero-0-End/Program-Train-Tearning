public class ShaCard extends Card{
    public ShaCard(){
        super("杀");
    };


    @Override
    public void use(Player source, Player target) {
        System.out.println(source.getName()+"对"+target.getName()+"使用了一张【杀】");
        if (source.getDrunk()){
            System.out.println(source.getName()+"处于【醉酒】状态，【杀】造成的伤害+1");
        }
    }
}
