package BaumansGate.Players;

import BaumansGate.Buildings.Town;
import BaumansGate.Field.Battlefield;
import BaumansGate.Output.Display;
import BaumansGate.Units.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class User implements Player, Serializable {

    private LinkedList<Character> aimSymbols = new LinkedList<>();
    private HashMap<Character, Unit> team = new HashMap<>();
    private int money;
    private int[] buildingResources;
    private double fineDecrease, grainAmount;
    private Town town = new Town();
    private ArrayList<Unit> addedUnits = new ArrayList<>();

    public User(int money){
        this.money = money;
        buildingResources = new int[] {20, 30};
        fineDecrease = 0;
    }

    public void setBuildingResources(int[] buildingResources) {
        this.buildingResources = buildingResources;
    }

    public void setTeam(HashMap<Character, Unit> team) {
        this.team = team;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public ArrayList<Unit> getAddedUnits() {
        return addedUnits;
    }

    public Town getTown() {
        return town;
    }

    public int[] getBuildingResources() {
        return buildingResources;
    }

    public double getGrainAmount() {
        return grainAmount;
    }

    public void setGrainAmount(double grainAmount) {
        this.grainAmount = grainAmount;
    }

    @Override
    public double getFineDecrease() {
        return fineDecrease;
    }

    public HashMap<Character, Unit> getTeam(){
        return team;
    }

    public void decreaseFines(double num){
        fineDecrease += num;
    }

    public void earnResources(int earnedWood, int earnedRocks){
        buildingResources[0] += earnedWood;
        buildingResources[1] += earnedRocks;
    }

    public void earnMoney(int earnedMoney){
        money += earnedMoney;
    }

    public void printTeamState(){
        System.out.println();
        System.out.println("Ваша команда: ");
        for (Character symbol : team.keySet()){
            System.out.println(team.get(symbol).toString());
        }
        System.out.println();
    }

    public int chooseUnitIndex(Scanner in){
        int unitNum;
        System.out.print("\tВыберите юнита: ");
        unitNum = in.nextInt();
        System.out.println();
        return unitNum;
    }

    public int chooseUnitInitialPosition(Unit unit, Scanner in){
        System.out.printf("Задайте стартовое значения столбца для <%s> : ", unit.getSymbol());
        return in.nextInt();
    }

    public int chooseWay(Scanner in){
        System.out.println("\nКакое действие вы желаете совершить?\n1 - Перемещение | 2 - Атака | 3 - Пропустить ход");
        System.out.print("Введите номер соответствующего действия: ");
        try {
            return in.nextInt();
        }
        catch (Exception e){
            return 0;
        }
    }

    public void fillTeam(Scanner in){
        int unitIndex;
        Unit chosenUnit;
        int name = 0;
        System.out.println();
        System.out.println("Доступны следующие классы:");
        for (int i = 1; i < (10 + addedUnits.size()); i++){
            chosenUnit = createUnit(i,' ');
            System.out.println(i + chosenUnit.toString() + " | Цена: " + chosenUnit.getCost());
        }
        System.out.println();
        while (money >= 10 && team.size() <= 10){
            System.out.print("В вашем кошельке \u001B[33m" + money + "\u001B[0m золотых монет.");
            unitIndex = chooseUnitIndex(in);
            chosenUnit = createUnit(unitIndex, Character.toChars(name + 48)[0]);
            while(money < chosenUnit.getCost()) {
                System.out.print("Недостаточно денег на покупку данного юнита. Выберите кого-нибудь ещё.");
                unitIndex = chooseUnitIndex(in);
                chosenUnit = createUnit(unitIndex, Character.toChars(name + 48)[0]);
            }
            team.put(chosenUnit.getSymbol(), chosenUnit);
            money -= chosenUnit.getCost();
            name++;
            if(money >= 10) {
                System.out.print("Хватит? (+/-): ");
                if (in.next().equals("+")) {
                    return;
                }
            }
        }
    }

    public void randomTeam(){
        double unitIndex;
        Unit chosenUnit;
        int name = 0;
        while (money >= 10 && team.size() <= 10){
            unitIndex = 1 + Math.random() * (9 + addedUnits.size());
            chosenUnit = createUnit((int)unitIndex,Character.toChars(name + 48)[0]);
            while(money < chosenUnit.getCost()){
                unitIndex = 1 + Math.random() * (9 + addedUnits.size());
                chosenUnit = createUnit((int)unitIndex, Character.toChars(name + 48)[0]);
            }
            team.put(chosenUnit.getSymbol(), chosenUnit);
            money -= chosenUnit.getCost();
            name++;
        }
    }

    public Unit createUnit(int chosenUnitNumber, char symbol){
        if (chosenUnitNumber == 1){
            return new Swordsman(symbol);
        }
        else if (chosenUnitNumber == 2){
            return new Spearman(symbol);
        }
        else if (chosenUnitNumber == 3){
            return new Axeman(symbol);
        }
        else if (chosenUnitNumber == 4){
            return new LongbowArcher(symbol);
        }
        else if (chosenUnitNumber == 5){
            return new ShortbowArcher(symbol);
        }
        else if (chosenUnitNumber == 6){
            return new CrossbowArcher(symbol);
        }
        else if (chosenUnitNumber == 7){
            return new Knight(symbol);
        }
        else if (chosenUnitNumber == 8){
            return new Cuirassier(symbol);
        }
        else if(chosenUnitNumber == 9){
            return new HorsebackArcher(symbol);
        }
        else{
            addedUnits.get(chosenUnitNumber - 10).setSymbol(symbol);
            return addedUnits.get(chosenUnitNumber - 10);
        }
    }

    public void putUnits(Battlefield field , boolean randomPlace, Scanner in){
        int row = field.getLength() - 1;
        if (randomPlace){
            double column;
            for (Character symbol : team.keySet()){
                column = field.getLength() * Math.random();
                while (!field.getCell(new int[]{row, (int) column}).isEmpty()){
                    column = field.getLength() * Math.random();
                }
                field.put(symbol, new int[] {row, (int)column});
            }
        }
        else{
            printTeamState();
            Display.displayField(field);
            System.out.println();
            int column;
            for (Character symbol : team.keySet()){
                column = chooseUnitInitialPosition(team.get(symbol), in);
                while (column >= field.getLength() || column < 0 || !field.getCell(new int[]{row, column}).isEmpty()){
                    System.out.println("Данная ячейка занята или находится за границами поля. Выберите другую.");
                    column = chooseUnitInitialPosition(team.get(symbol), in);
                }
                field.put(symbol, new int[] {row, column});
            }
        }
    }

    public void playRound(Battlefield field, HashMap<Character, Unit> enemyTeam, Scanner in){
        System.out.println();
        int chosenWay;
        for (Character symbol : team.keySet()) {
            System.out.println("\n\nХод юнита " + team.get(symbol).toString());
            chosenWay = chooseWay(in);
            while(chosenWay > 3 || chosenWay < 1){
                System.out.println("\nБыл выбран символ, не соответствующий ни одной из предложенных опций.");
                chosenWay = chooseWay(in);
            }
            if (chosenWay != 3) {
                while ((chosenWay == 1) ? !unitMove(field, team.get(symbol)) : !unitAttack(field, enemyTeam, aimSymbols, team.get(symbol))) {
                    chosenWay = chooseWay(in);
                    System.out.println();
                    if(chosenWay == 3){
                        break;
                    }
                }
            }
        }
        Display.makeGap();
    }

    public boolean unitMove(Battlefield field, Unit unit){
        Display.makeGap();
        System.out.println("\tЯчейки доступные для перемещения:");
        Display.displayMoveCells(unit, field, fineDecrease);
        System.out.print("\nВернуться назад? (+/-): ");
        Scanner in = new Scanner(System.in);
        if (in.next().equals("+")) {
            return false;
        }
        System.out.print("\nВведите координаты для перемещения в формате 'ряд столбец' : ");
        int row = in.nextInt(), column = in.nextInt();
        System.out.println();
        while (!unit.move(new int[]{row, column},field, fineDecrease)){
            System.out.println("\nДанное поле не доступно для перемещения. Пожалуйста взгляните на карту и выберите одну из отмеченных клеток.");
            System.out.print("Введите координаты для перемещения в формате 'ряд столбец' : ");
            row = in.nextInt();
            column = in.nextInt();
            System.out.println();
        }
        Display.makeGap();
        return true;
    }

    public boolean unitAttack(Battlefield field, HashMap<Character, Unit> enemyTeam, LinkedList<Character> aimSymbols, Unit attackingUnit){
        System.out.println();
        aimSymbols.clear();
        if (attackingUnit.canAttack(field, enemyTeam, aimSymbols)) {
            Display.makeGap();
            System.out.println("\tЯчейки доступные для проведения атаки");
            Display.displayAttackCells(attackingUnit, field);
            System.out.print("\nВернуться назад? (+/-): ");
            Scanner in = new Scanner(System.in);
            if (in.next().equals("+")) {
                return false;
            }
            System.out.println("Атакует союзный юнит: " + attackingUnit.toString());
            System.out.println("\nДля атаки доступны: ");
            for (Character enemyUnitSymbol : aimSymbols){
                System.out.println(enemyTeam.get(enemyUnitSymbol).toString());
            }
            System.out.print("\nВведите имя вражеского юнита, которого следут атаковать: ");
            char enemyUnitSymbol = in.next().charAt(0);
            while (!Character.isLetter(enemyUnitSymbol) || !aimSymbols.contains(enemyUnitSymbol)){
                System.out.print("Введённый символ не является именем доступного для атаки юнита. Введите имя заново:");
                enemyUnitSymbol = in.next().charAt(0);
            }
            System.out.println();
            attackingUnit.attack(enemyTeam.get(enemyUnitSymbol));
            System.out.printf("Ваш %s <%s> наносит воину <%s> %d урона%n", attackingUnit.getName(), attackingUnit.getSymbol(), enemyUnitSymbol, attackingUnit.getDamage());
            if(enemyTeam.get(enemyUnitSymbol).isDead()){
                Display.makeGap();
                enemyTeam.get(enemyUnitSymbol).death(enemyTeam, field);
                System.out.printf("%nЮнит <%s> был убит. ", enemyUnitSymbol);
                earnMoney(5);
                earnResources(8,8);
                System.out.println("Вы получили \u001B[33m5\u001B[0m монет, \u001B[35m8\u001B[0m дерева и \u001B[35m8\u001B[0m камня");
            }
            System.out.println();
            aimSymbols.clear();
            Display.makeGap();
            return true;
        }
        System.out.println("Поблизости нет вражеских юнитов. Атаковать не удастся. Попробуйте походить");
        System.out.println();
        return false;
    }

    public boolean isDefeated(){
        return team.isEmpty() || grainAmount <= 0;
    }

    public void feedYourTeam(){
        grainAmount -= 2 * team.size();
    }

}
