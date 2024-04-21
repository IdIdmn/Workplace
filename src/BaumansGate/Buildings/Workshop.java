package BaumansGate.Buildings;

import BaumansGate.Players.User;

public class Workshop extends Building {

    private String name = "Ремесленная мастерская";
    private int maxAmount = 4, amount = 0;
    private int[] cost = {8,8};
    private int maxIncome = 3;

    public void getIncome(User player){
        if (amount > 0) {
            int earnedMoney = 1 + (int)(maxIncome * Math.random() * amount);
            player.earnMoney(earnedMoney);
            System.out.println("\nЗа этот раунд мастерские выплатили \u001B[33m" + earnedMoney + "\u001B[0m золотых монет.");
        }
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
