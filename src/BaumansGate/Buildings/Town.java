package BaumansGate.Buildings;

import BaumansGate.Players.User;

import java.io.Serializable;
import java.util.Scanner;

public class Town implements Serializable {

    private Building[] buildings = new Building[7];
    private Mill mill;

    public Town(){
        buildings[0] = new Hospital();
        buildings[1] = new Forge();
        buildings[2] = new Armory();
        buildings[3] = new Tavern();
        buildings[4] = new Workshop();
        buildings[5] = new Market();
        buildings[6] = new Academy();
        mill = new Mill();
    }

    public Hospital getHospital(){
        return (Hospital) buildings[0];
    }

    public Forge getForge(){
        return (Forge) buildings[1];
    }

    public Armory getArmory(){
        return (Armory) buildings[2];
    }

    public Tavern getTavern(){
        return (Tavern) buildings[3];
    }

    public Workshop getWorkshop(){
        return (Workshop) buildings[4];
    }

    public Market getMarket(){
        return (Market) buildings[5];
    }

    public Academy getAcademy(){
        return (Academy) buildings[6];
    }

    public Mill getMill(){
        return mill;
    }

    public void changeTaxes(){
        Scanner in = new Scanner(System.in);
        System.out.printf("\nСейчас налог на зерно составляет %d процента(-ов)", mill.getTaxes());
        System.out.print("\nЖелаете изменить налог на зерно? (+/-): ");
        if (in.next().equals("+")) {
            System.out.print("\nВведите новое значение в процентах?: ");
            int newTax = in.nextInt();
            while(newTax < 0 || newTax > 100){
                if (newTax > 100){
                    System.out.print("\nГосподин, вам не кажется, что это слишком? Даже самый глупый крестьянин заподозрит здесь что-то неладное.\nВведите корректное значение: ");
                }
                else {
                    System.out.print("\nЧто за вздор? Платить крестьянам за работу... Так недолго и банкротом остаться.\nВведите корректное значение: ");
                }
                newTax = in.nextInt();
            }
            mill.setTaxes(newTax);
        }
    }

    public void startDevelopment(User player){
        if(buildings[6].getAmount() > 0) {
            if(player.getMoney() >= ((Academy) buildings[6]).getDevelopmentCost()[2] && player.getBuildingResources()[0] >= ((Academy) buildings[6]).getDevelopmentCost()[0] && player.getBuildingResources()[1] >= ((Academy) buildings[6]).getDevelopmentCost()[1]) {
                ((Academy) buildings[6]).makeUnit(player);
            }
            else{
                System.out.println("\nНедостаточно средств на разработку.");
            }
        }
        else{
            System.out.println("\nАкадемия ещё не построена.");
        }
    }

    public void exchange(User player){
        if (buildings[5].getAmount() > 0) {
            Scanner in = new Scanner(System.in);
            System.out.println("На рынке сейчас следующие курсы валют: ");
            System.out.printf("1) Дерево -> Камни : %d -> %d\n", getMarket().getWoodRockRate()[0], getMarket().getWoodRockRate()[1]);
            System.out.printf("2) Камни -> Дерево : %d -> %d\n", getMarket().getRockWoodRate()[0], getMarket().getRockWoodRate()[1]);
            System.out.printf("3) Дерево -> Монеты : %d -> %d\n", getMarket().getWoodMoneyRate()[0], getMarket().getWoodMoneyRate()[1]);
            System.out.printf("4) Камни -> Монеты : %d -> %d\n", getMarket().getRockMoneyRate()[0], getMarket().getRockMoneyRate()[1]);
            System.out.print("\nЖелаете совершить обмен? (+/-): ");
            if (in.next().equals("+")) {
                System.out.println("\nВведите номер соответствующей позиции: ");
                int chosenOption = in.nextInt();
                if (chosenOption == 1) {
                    if(player.getBuildingResources()[0] >= getMarket().getWoodRockRate()[0]) {
                        player.earnResources(-getMarket().getWoodRockRate()[0], getMarket().getWoodRockRate()[1]);
                        return;
                    }
                } else if (chosenOption == 2) {
                    if(player.getBuildingResources()[1] >= getMarket().getRockWoodRate()[1]) {
                        player.earnResources(getMarket().getRockWoodRate()[1], -getMarket().getRockWoodRate()[0]);
                        return;
                    }
                } else if (chosenOption == 3) {
                    if(player.getBuildingResources()[0] >= getMarket().getWoodMoneyRate()[0]){
                        player.earnResources(-getMarket().getWoodMoneyRate()[0], 0);
                        player.earnMoney(getMarket().getWoodMoneyRate()[1]);
                        return;
                    }
                } else {
                    if(player.getBuildingResources()[1] >= getMarket().getRockMoneyRate()[0]) {
                        player.earnResources(0, -getMarket().getRockMoneyRate()[0]);
                        player.earnMoney(getMarket().getRockMoneyRate()[1]);
                        return;
                    }
                }
                System.out.println("\nДля этой сделки у вас недостаточно ресурсов.");
            }
        }
        else{
            System.out.println("\nРынок ещё не построен.");
        }
    }

    public void getIncome(User player){
        if (getWorkshop().getAmount() > 0) {
            int earnedMoney = getWorkshop().makeProfit();
            player.earnMoney(earnedMoney);
            System.out.println("За этот раунд мастерские выплатили \u001B[33m" + earnedMoney + "\u001B[0m золотых монет.");
        }
        mill.payDebt(player);
    }

    public void createBuilding(User player){
        boolean isSomethingAvailableToBuy = false;
        System.out.println();
        for (int i = 0; i < buildings.length; i++){
            if (buildings[i].getAmount() < buildings[i].getMaxAmount() && player.getBuildingResources()[0] >= buildings[i].getCost()[0] && player.getBuildingResources()[1] >= buildings[i].getCost()[1]) {
                isSomethingAvailableToBuy = true;
                System.out.printf("%d - %s | Для постройки необходимо \u001B[35m%d\u001B[0m дерева и \u001B[35m%d\u001B[0m камня\n", i, buildings[i].getName(), buildings[i].getCost()[0], buildings[i].getCost()[1]);
            }
        }
        if(!isSomethingAvailableToBuy){
            System.out.println("Ни одно из здание не доступно для постройки.");
            return;
        }
        Scanner in = new Scanner(System.in);
        System.out.print("\nВведите номер здания которое вы хотите приобрести: ");
        int chosenBuilding = in.nextInt();
        buildings[chosenBuilding].create(player);
        player.getBuildingResources()[0] -= buildings[chosenBuilding].getCost()[0];
        player.getBuildingResources()[1] -= buildings[chosenBuilding].getCost()[1];
    }

    public void upgradeBuilding(User player){
        boolean isSomethingAvailableToUpgrade = false;
        System.out.println();
        for (int i = 0; i < buildings.length; i++){
            if (buildings[i] instanceof EnhancementBuilding && buildings[i].getAmount() > 0 && ((EnhancementBuilding)buildings[i]).getLevel() < 3 && player.getBuildingResources()[0] >= buildings[i].getCost()[0] && player.getBuildingResources()[1] >= buildings[i].getCost()[1]) {
                isSomethingAvailableToUpgrade = true;
                System.out.printf("%d - %s | Для улучшения необходимо \u001B[35m%d\u001B[0m дерева и \u001B[35m%d\u001B[0m камня\n", i, buildings[i].getName(), buildings[i].getCost()[0], buildings[i].getCost()[1]);
            }
        }
        if(!isSomethingAvailableToUpgrade){
            System.out.println("Ни одно здание не доступно для улучшения.");
            return;
        }
        Scanner in = new Scanner(System.in);
        System.out.print("\nВведите номер здания которое вы хотите улучшить: ");
        int chosenBuilding = in.nextInt();
        ((EnhancementBuilding)buildings[chosenBuilding]).levelUp(player);
        player.getBuildingResources()[0] -= buildings[chosenBuilding].getCost()[0];
        player.getBuildingResources()[1] -= buildings[chosenBuilding].getCost()[1];
    }

}
