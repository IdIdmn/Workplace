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
        if (getMarket().getAmount() > 0) {
            getMarket().trade(player);
        }
        else{
            System.out.println("\nРынок ещё не построен.");
        }
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
