package Buildings;

import Players.User;

public class Armory extends EnhancementBuilding{

    private String name = "Арсенал";
    private int level;
    static private int amount = 0;
    private int[] cost = {5, 6};

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getAmount(){
        return amount;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public int[] getCost() {
        return cost;
    }

    @Override
    public void create(User player) {
        upgradeParam(player);
        level = 1;
        amount++;
        cost[0] += 2;
        cost[1] += 2;
    }

    @Override
    public void upgradeParam(User player) {
        for(Character unitSymbol : player.getTeam().keySet()){
            player.getTeam().get(unitSymbol).buff(0,0,0,1,0);
        }
    }

    @Override
    public boolean levelUp(User player) {
        if (level == 3){
            System.out.println("Максимальный уровень уже достигнут.");
            return false;
        }
        else{
            upgradeParam(player);
            level ++;
            cost[0] += 2;
            cost[1] += 2;
            return true;
        }
    }
}
