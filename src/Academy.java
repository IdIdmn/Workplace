import java.util.Scanner;

public class Academy extends Building{

    private String name = "Академия";
    private int maxAmount = 1, amount = 0;
    private int[] cost = {10,15}, developmentCost = {1,1,1};

    public int[] getDevelopmentCost() {
        return developmentCost;
    }

    public void makeUnit(User player){
        Scanner in = new Scanner(System.in);
        System.out.printf("\nРазработка стоит \u001B[33m%d\u001B[0m монет, \u001B[35m%d\u001B[0m дерева и \u001B[35m%d\u001B[0m камня\n", developmentCost[2], developmentCost[0], developmentCost[1]);
        System.out.println("\nЮнита какого типа вы желаете создать\n1 - Пеший | 2 - Лучник | 3 - Наездник");
        System.out.print("Введите номер соответствующего действия: ");
        int chosenOption = in.nextInt();
        if(chosenOption == 1){
            System.out.print("Введите последовательно имя нового юнита и его здоровье, урон, дальность атаки, защиту и дальность перемещения: ");
            player.getAddedUnits().add(new TemplateWalker(in.next(), in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(),in.nextInt()));
        }
        else if (chosenOption == 2){
            System.out.print("Введите последовательно имя нового юнита и его здоровье, урон, дальность атаки, защиту и дальность перемещения: ");
            player.getAddedUnits().add(new TemplateArcher(in.next(), in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(),in.nextInt()));
        }
        else{
            System.out.print("Введите последовательно имя нового юнита и его здоровье, урон, дальность атаки, защиту и дальность перемещения: ");
            player.getAddedUnits().add(new TemplateHorseman(in.next(), in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(),in.nextInt()));
        }
        player.earnResources(-developmentCost[0], -developmentCost[1]);
        player.earnMoney(-developmentCost[2]);
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
