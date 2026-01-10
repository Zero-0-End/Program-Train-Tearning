public class Card {
    private String name;
    public void use(Player source, Player target){
        switch (this.name){
            case  "杀":
                target.loseHealth(1);
                break;

            case "桃":
                target.heal(1);
                break;
            default:
                System.out.println(source.getName()+" 对 "+target.getName()+" 使用 "+this.name);
        }
    }

    //构造方法，用于生成牌
    public Card(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
