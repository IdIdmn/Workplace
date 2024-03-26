import java.util.HashMap;
import java.util.LinkedList;

public class Bot implements Player{

    private LinkedList<Character> aimSymbols = new LinkedList<>();
    private HashMap<Character, Unit> Team = new HashMap<>();

    public void printTeamState(){
        System.out.println();
        System.out.println("Вражеская команда: ");
        for (Character symbol : Team.keySet()){
            System.out.println(Team.get(symbol).toString());
        }
        System.out.println();
    }

    public HashMap<Character, Unit> getTeam(){
        return Team;
    }

    public void fillTeam(int money){
        int name = 0;
        Unit chosenUnit;
        double unitIndex;
        while (money >= 10 && Team.size() <= 10){
            unitIndex = 0 + Math.random() * 9;
            chosenUnit = createUnit((int)unitIndex, Character.toChars(name + 97)[0]);
            while(money < chosenUnit.getCost()){
                unitIndex = 0 + Math.random() * 9;
                chosenUnit = createUnit((int)unitIndex, Character.toChars(name + 97)[0]);
            }
            Team.put(chosenUnit.getSymbol(), chosenUnit);
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
        else{
            return new HorsebackArcher(symbol);
        }
    }

    public void putUnits(Battlefield field){
        int row = 0;
        double column;
        for (Character symbol : Team.keySet()){
            column = 0 + 14 * Math.random();
            while (!field.getCell(new int[]{row, (int) column}).isEmpty()){
                column = 0 + 14 * Math.random();
            }
            field.put(symbol, new int[] {row, (int)column});
        }

    }

    public void playRound(Battlefield field, HashMap<Character,Unit> enemyTeam){
        for (Character symbol : Team.keySet()) {
            if(Team.get(symbol).canAttack(field, enemyTeam, aimSymbols)){
                unitAttack(field, enemyTeam, aimSymbols, Team.get(symbol));
            }
            else{
                unitMove(field, Team.get(symbol));
            }
        }
    }

    public void unitMove(Battlefield field, Unit unit){
        double row = field.getUnitPosition(unit.getSymbol())[0] + 1 + (unit.getMoveRange() - 1) * Math.random();
        int column = field.getUnitPosition(unit.getSymbol())[1];
        if (row < field.getLength()){
            unit.move(new int[]{(int)row, column},field);
        }
    }

    public void unitAttack(Battlefield field, HashMap<Character,Unit> enemyTeam, LinkedList<Character> aimSymbols, Unit attackingUnit){
        if (!aimSymbols.isEmpty()) {
            Character minDistanceSymbol = aimSymbols.getFirst();
            double minDistance = field.getLength() + 1.0, unitDistance;
            for (Character enemyUnitSymbol : aimSymbols){
                unitDistance = field.getCell(field.getUnitPosition(attackingUnit.getSymbol())).calculateDistanceToOtherCell(field.getUnitPosition(enemyUnitSymbol));
                if (unitDistance < minDistance){
                    minDistance = unitDistance;
                    minDistanceSymbol = enemyUnitSymbol;
                }
            }
            attackingUnit.attack(enemyTeam.get(minDistanceSymbol));
            System.out.printf("Вражеский %s <%s> наносит воину <%s> %d урона", attackingUnit.getName(), attackingUnit.getSymbol(), minDistanceSymbol, attackingUnit.getDamage());
            if(enemyTeam.get(minDistanceSymbol).isDead()){
                enemyTeam.get(minDistanceSymbol).death(enemyTeam, field);
                System.out.println();
                System.out.printf("Юнит <%s> был убит.", minDistanceSymbol);
            }
            System.out.println();
            aimSymbols.clear();
        }
    }

    public boolean isDefeated(){
        return Team.isEmpty();
    }

}


