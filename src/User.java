import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class User implements Player, Serializable {

    private LinkedList<Character> AimSymbols = new LinkedList<>();
    private HashMap<Character, Unit> Team = new HashMap<>();

    public void printTeamState(){
        System.out.println();
        System.out.println("Ваша команда: ");
        for (Character symbol : Team.keySet()){
            System.out.println(Team.get(symbol).toString());
        }
        System.out.println();
    }

    public HashMap<Character, Unit> getTeam(){
        return Team;
    }

    public int chooseUnitIndex(){
        Scanner in = new Scanner(System.in);
        int unitNum;
        System.out.print("\tВыберите юнита: ");
        unitNum = in.nextInt();
        System.out.println();
        return unitNum;
    }

    public int chooseUnitInitialPosition(Unit unit){
        Scanner in = new Scanner(System.in);
        System.out.printf("Задайте стартовое значения столбца для <%s> : ", unit.getSymbol());
        return in.nextInt();
    }

    public int chooseWay(){
        Scanner in = new Scanner(System.in);
        System.out.println("\nКакое действие вы желаете совершить?\n1 - Перемещение | 2 - Атака | 3 - Пропустить ход");
        System.out.print("Введите номер соответствующего действия: ");
        try {
            return in.nextInt();
        }
        catch (Exception e){
            return 0;
        }
    }

    public void fillTeam(boolean randomBuy, int money){
        int name = 0;
        Unit chosenUnit;
        if (randomBuy){
            double unitIndex;
            while (money >= 10 && Team.size() <= 10){
                unitIndex = 0 + Math.random() * 9;
                chosenUnit = createUnit((int)unitIndex,Character.toChars(name + 48)[0]);
                while(money < chosenUnit.getCost()){
                    unitIndex = 0 + Math.random() * 9;
                    chosenUnit = createUnit((int)unitIndex, Character.toChars(name + 48)[0]);
                }
                Team.put(chosenUnit.getSymbol(), chosenUnit);
                money -= chosenUnit.getCost();
                name++;
            }
        }
        else{
            int unitIndex;
            System.out.println();
            System.out.println("Доступны следующие классы:");
            for (int i = 1; i < 10; i++){
                chosenUnit = createUnit(i,' ');
                System.out.println(i + chosenUnit.toString() + " | Цена: " + chosenUnit.getCost());
            }
            System.out.println();
            while (money >= 10 && Team.size() <= 10){
                System.out.print("В вашем кошельке \u001B[33m" + money + "\u001B[0m золотых монет.");
                unitIndex = chooseUnitIndex();
                chosenUnit = createUnit(unitIndex, Character.toChars(name + 48)[0]);
                while(money < chosenUnit.getCost()) {
                    System.out.print("Недостаточно денег на покупку данного юнита. Выберите кого-нибудь ещё.");
                    unitIndex = chooseUnitIndex();
                    chosenUnit = createUnit(unitIndex, Character.toChars(name + 48)[0]);
                }
                Team.put(chosenUnit.getSymbol(), chosenUnit);
                money -= chosenUnit.getCost();
                name++;
            }
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
        else{
            return new HorsebackArcher(symbol);
        }
    }

    public void putUnits(Battlefield field , boolean randomPlace){
        int row = field.getLength() - 1;
        if (randomPlace){
            double column;
            for (Character symbol : Team.keySet()){
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
            for (Character symbol : Team.keySet()){
                column = chooseUnitInitialPosition(Team.get(symbol));
                while (column >= field.getLength() || column < 0 || !field.getCell(new int[]{row, column}).isEmpty()){
                    System.out.println("Данная ячейка занята или находится за границами поля. Выберите другую.");
                    column = chooseUnitInitialPosition(Team.get(symbol));
                }
                field.put(symbol, new int[] {row, column});
            }
        }
    }

    public void playRound(Battlefield field, HashMap<Character, Unit> enemyTeam){
        System.out.println();
        int chosenWay;
        for (Character symbol : Team.keySet()) {
            System.out.println("\n\nХод юнита " + Team.get(symbol).toString());
            chosenWay = chooseWay();
            while(chosenWay > 3 || chosenWay < 1){
                System.out.println("\nБыл выбран символ, не соответствующий ни одной из предложенных опций.");
                chosenWay = chooseWay();
            }
            if (chosenWay != 3) {
                while ((chosenWay == 1) ? !unitMove(field, Team.get(symbol)) : !unitAttack(field, enemyTeam, AimSymbols, Team.get(symbol))) {
                    chosenWay = chooseWay();
                    System.out.println();
                }
            }
        }
        Display.makeGap();
    }

    public boolean unitMove(Battlefield field, Unit unit){
        Display.makeGap();
        System.out.println("\tЯчейки доступные для перемещения:");
        Display.displayMoveCells(unit, field);
        System.out.print("\nВернуться назад? (+/-): ");
        Scanner in = new Scanner(System.in);
        if (in.next().equals("+")) {
            return false;
        }
        System.out.print("\nВведите координаты для перемещения в формате 'ряд столбец' : ");
        int row = in.nextInt(), column = in.nextInt();
        System.out.println();
        while (!unit.move(new int[]{row, column},field)){
            System.out.println("\nДанное поле не доступно для перемещения. Пожалуйста взгляните на карту и выберите одну из отмеченных клеток.");
            System.out.print("Введите координаты для перемещения в формате 'ряд столбец' : ");
            row = in.nextInt();
            column = in.nextInt();
            System.out.println();
        }
        return true;
    }

    public boolean unitAttack(Battlefield field, HashMap<Character,Unit> enemyTeam, LinkedList<Character> aimSymbols, Unit attackingUnit){
        System.out.println();
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
                enemyTeam.get(enemyUnitSymbol).death(enemyTeam, field);
                System.out.printf("%nЮнит <%s> был убит.", enemyUnitSymbol);
            }
            System.out.println();
            aimSymbols.clear();
            return true;
        }
        System.out.println("Поблизости нет вражеских юнитов. Атаковать не удастся. Попробуйте походить");
        System.out.println();
        return false;
    }

    public boolean isDefeated(){
        return Team.isEmpty();
    }

}
