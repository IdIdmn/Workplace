package BaumansGate.Buildings;

import BaumansGate.Players.User;

import java.util.Scanner;

public class Market extends Building {

    private String name = "Рынок";
    private int maxAmount = 1, amount = 0;
    private int[] cost = {7,6};
    private int[] WoodRockRate = new int[2], RockWoodRate = new int[2], WoodMoneyRate  = new int[2], RockMoneyRate  = new int[2];

    public void randomRates(){
        WoodRockRate = new int[]{1 + (int)(10*Math.random()),1 + (int)(7 * Math.random() * 3)};
        RockWoodRate[0] = WoodRockRate[1];
        RockWoodRate[1] = WoodRockRate[0];
        WoodMoneyRate = new int[]{1 + (int)(10*Math.random()),1 + (int)(7 * Math.random() * 3)};
        RockMoneyRate = new int[]{1 + (int)(10*Math.random()),1 + (int)(7 * Math.random() * 3)};
    }

    public void trade(User player){
        Scanner in = new Scanner(System.in);
        System.out.println("На рынке сейчас следующие курсы валют: ");
        System.out.printf("1) Дерево -> Камни : %d -> %d\n", WoodRockRate[0], WoodRockRate[1]);
        System.out.printf("2) Камни -> Дерево : %d -> %d\n", RockWoodRate[0], RockWoodRate[1]);
        System.out.printf("3) Дерево -> Монеты : %d -> %d\n", WoodMoneyRate[0], WoodMoneyRate[1]);
        System.out.printf("4) Камни -> Монеты : %d -> %d\n", RockMoneyRate[0], RockMoneyRate[1]);
        System.out.print("\nЖелаете совершить обмен? (+/-): ");
        if (in.next().equals("+")) {
            System.out.println("\nВведите номер соответствующей позиции: ");
            int chosenOption = in.nextInt();
            if (chosenOption == 1) {
                if(player.getBuildingResources()[0] >= WoodRockRate[0]) {
                    player.earnResources(-WoodRockRate[0], WoodRockRate[1]);
                    return;
                }
            } else if (chosenOption == 2) {
                if(player.getBuildingResources()[1] >= RockWoodRate[1]) {
                    player.earnResources(RockWoodRate[1], -RockWoodRate[0]);
                    return;
                }
            } else if (chosenOption == 3) {
                if(player.getBuildingResources()[0] >= WoodMoneyRate[0]){
                    player.earnResources(-WoodMoneyRate[0], 0);
                    player.earnMoney(WoodMoneyRate[1]);
                    return;
                }
            } else {
                if(player.getBuildingResources()[1] >= RockMoneyRate[0]) {
                    player.earnResources(0, -RockMoneyRate[0]);
                    player.earnMoney(RockMoneyRate[1]);
                    return;
                }
            }
            System.out.println("\nДля этой сделки у вас недостаточно ресурсов.");
        }
    }
    
    public int[] getWoodRockRate() {
        return WoodRockRate;
    }

    public int[] getRockWoodRate() {
        return RockWoodRate;
    }

    public int[] getWoodMoneyRate() {
        return WoodMoneyRate;
    }

    public int[] getRockMoneyRate() {
        return RockMoneyRate;
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