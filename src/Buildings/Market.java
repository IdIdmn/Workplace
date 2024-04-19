package Buildings;

import Players.User;

public class Market extends Building{

    private String name = "Рынок";
    private int maxAmount = 1, amount = 0;
    private int[] cost = {7,6};
    private int[] wood_rockRate = new int[2], rock_woodRate = new int[2], wood_moneyRate  = new int[2], rock_moneyRate  = new int[2];

    public void randomRates(){
        wood_rockRate = new int[]{1 + (int)(10*Math.random()),1 + (int)(7 * Math.random() * 3)};
        rock_woodRate[0] = wood_rockRate[1];
        rock_woodRate[1] = wood_rockRate[0];
        wood_moneyRate = new int[]{1 + (int)(10*Math.random()),1 + (int)(7 * Math.random() * 3)};
        rock_moneyRate = new int[]{1 + (int)(10*Math.random()),1 + (int)(7 * Math.random() * 3)};
    }

    public int[] getWoodRockRate() {
        return wood_rockRate;
    }

    public int[] getRockWoodRate() {
        return rock_woodRate;
    }

    public int[] getWoodMoneyRate() {
        return wood_moneyRate;
    }

    public int[] getRockMoneyRate() {
        return rock_moneyRate;
    }

    @Override
    public void create(User player) {
        amount++;
        randomRates();
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