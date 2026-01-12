public class Card {
    private String name;
    public void use(Player source, Player target,Deck deck){
        switch (this.name){
            case  "杀":
                if (target.dodge(deck)){
                    System.out.println(target.getName()+" 未受伤害.");
                } else if (source.getDrunk()) {
                    System.out.println(source.getName()+" 处于醉酒状态, 伤害 +1.");
                    target.setDrunk(false);
                    target.loseHealth(2);
                }else {
                    target.loseHealth(1);
                }
                break;

            case "桃":
                source.heal(1);
                break;
            case "酒":
                source.setDrunk(true);
                source.heal(1);

            default:
                System.out.println("未知卡牌效果.");
        }
        deck.addDiscardPile(this);
    }

    //构造方法，用于生成牌
    public Card(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
