package Buildings;

import Players.User;

public class Workshop extends Building{

    private String name = "Ремесленная мастерская";
    private int maxAmount = 4, amount = 0;
    private int[] cost = {8,8};
    private int maxIncome = 3;

    public int makeProfit(){
        return 1 + (int)(maxIncome * Math.random() * amount);
    }

    @Override
    public void create(User player) {
        amount++;
    }

    @Override
    public int getMaxAmount() {
        return maxAmount;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int[] getCost() {
        return cost;
    }
}
