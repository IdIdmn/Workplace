package Buildings;

import Players.User;

import java.io.Serializable;
import java.util.Scanner;

public class Town implements Serializable {

    private Building[] buildings = new Building[7];

    public Town(){
        buildings[0] = new Hospital();
        buildings[1] = new Forge();
        buildings[2] = new Armory();
        buildings[3] = new Tavern();
        buildings[4] = new Workshop();
        buildings[5] = new Market();
        buildings[6] = new Academy();
    }

    public void startDevelopment(User player){
        if(buildings[6].getAmount() > 0) {
            if(player.getMoney() >= ((Academy) buildings[6]).getDevelopmentCost()[2] && player.getResourses()[0] >= ((Academy) buildings[6]).getDevelopmentCost()[0] && player.getResourses()[1] >= ((Academy) buildings[6]).getDevelopmentCost()[1]) {
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

    public Building getBuilding(int buildingIndex){
        return buildings[buildingIndex];
    }

    public void exchange(User player){
        if (buildings[5].getAmount() > 0) {
            Scanner in = new Scanner(System.in);
            System.out.println("На рынке сейчас следующие курсы валют: ");
            System.out.printf("1) Дерево -> Камни : %d -> %d\n", ((Market) buildings[5]).getWoodRockRate()[0], ((Market) buildings[5]).getWoodRockRate()[1]);
            System.out.printf("2) Камни -> Дерево : %d -> %d\n", ((Market) buildings[5]).getRockWoodRate()[0], ((Market) buildings[5]).getRockWoodRate()[1]);
            System.out.printf("3) Дерево -> Монеты : %d -> %d\n", ((Market) buildings[5]).getWoodMoneyRate()[0], ((Market) buildings[5]).getWoodMoneyRate()[1]);
            System.out.printf("4) Камни -> Монеты : %d -> %d\n", ((Market) buildings[5]).getRockMoneyRate()[0], ((Market) buildings[5]).getRockMoneyRate()[1]);
            System.out.print("\nЖелаете совершить обмен? (+/-): ");
            if (in.next().equals("+")) {
                System.out.println("\nВведите номер соответствующей позиции: ");
                int chosenOption = in.nextInt();
                if (chosenOption == 1) {
                    if(player.getResourses()[0] >= ((Market) buildings[5]).getWoodRockRate()[0]) {
                        player.earnResources(-((Market) buildings[5]).getWoodRockRate()[0], ((Market) buildings[5]).getWoodRockRate()[1]);
                        return;
                    }
                } else if (chosenOption == 2) {
                    if(player.getResourses()[1] >= ((Market) buildings[5]).getRockWoodRate()[1]) {
                        player.earnResources(((Market) buildings[5]).getRockWoodRate()[1], -((Market) buildings[5]).getRockWoodRate()[0]);
                        return;
                    }
                } else if (chosenOption == 3) {
                    if(player.getResourses()[0] >= ((Market) buildings[5]).getWoodMoneyRate()[0]){
                        player.earnResources(-((Market) buildings[5]).getWoodMoneyRate()[0], 0);
                        player.earnMoney(((Market) buildings[5]).getWoodMoneyRate()[1]);
                        return;
                    }
                } else {
                    if(player.getResourses()[1] >= ((Market) buildings[5]).getRockMoneyRate()[0]) {
                        player.earnResources(0, -((Market) buildings[5]).getRockMoneyRate()[0]);
                        player.earnMoney(((Market) buildings[5]).getRockMoneyRate()[1]);
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
        if (buildings[4].getAmount() > 0) {
            int earnedMoney = ((Workshop) buildings[4]).makeProfit();
            player.earnMoney(earnedMoney);
            System.out.println("За этот раунд мастерские выплатили \u001B[33m" + earnedMoney + "\u001B[0m золотых монет.");
        }
    }

    public void createBuilding(User player){
        boolean isSomethingAvailableToBuy = false;
        System.out.println();
        for (int i = 0; i < buildings.length; i++){
            if (buildings[i].getAmount() < buildings[i].getMaxAmount() && player.getResourses()[0] >= buildings[i].getCost()[0] && player.getResourses()[1] >= buildings[i].getCost()[1]) {
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
        player.getResourses()[0] -= buildings[chosenBuilding].getCost()[0];
        player.getResourses()[1] -= buildings[chosenBuilding].getCost()[1];
    }

    public void upgradeBuilding(User player){
        boolean isSomethingAvailableToUpgrade = false;
        System.out.println();
        for (int i = 0; i < buildings.length; i++){
            if (buildings[i] instanceof EnhancementBuilding && buildings[i].getAmount() > 0 && ((EnhancementBuilding)buildings[i]).getLevel() < 3 && player.getResourses()[0] >= buildings[i].getCost()[0] && player.getResourses()[1] >= buildings[i].getCost()[1]) {
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
        player.getResourses()[0] -= buildings[chosenBuilding].getCost()[0];
        player.getResourses()[1] -= buildings[chosenBuilding].getCost()[1];
    }

}
