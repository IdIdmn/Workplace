package Buildings;

import java.util.Scanner;

import Players.User;

public class Tavern extends EnhancementBuilding{

    private String name = "Таверна";
    private int level;
    static private int amount = 0;
    private int[] cost = {8, 5};

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
        Scanner in = new Scanner(System.in);
        System.out.println("\nЧто стоит сделать с повышением уровня?\n1 - Увеличить дальность перемещения | 2 - Понизить штрафы препятствий");
        System.out.print("Введите номер соответствующего действия: ");
        if(in.nextInt() == 1) {
            for (Character unitSymbol : player.getTeam().keySet()) {
                player.getTeam().get(unitSymbol).buff(0, 0, 0, 0, 1);
            }
        }
        else{
            player.decreaseFines(0.3);
        }
    }

    @Override
    public boolean levelUp(User player) {
        upgradeParam(player);
        level ++;
        cost[0] += 2;
        cost[1] += 2;
        return true;
    }

}
