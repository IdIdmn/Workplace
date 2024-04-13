import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class Bot implements Player, Serializable {

    private LinkedList<Character> aimSymbols = new LinkedList<>();
    private HashMap<Character, Unit> team = new HashMap<>();

    public void printTeamState(){
        System.out.println();
        System.out.println("Вражеская команда: ");
        for (Character symbol : team.keySet()){
            System.out.println(team.get(symbol).toString());
        }
        System.out.println();
    }

    public HashMap<Character, Unit> getTeam(){
        return team;
    }

    public void fillTeam(int money){
        int name = 0;
        Unit chosenUnit;
        double unitIndex;
        while (money >= 10 && team.size() <= 10){
            unitIndex = 0 + Math.random() * 9;
            chosenUnit = createUnit((int)unitIndex, Character.toChars(name + 97)[0]);
            while(money < chosenUnit.getCost()){
                unitIndex = 0 + Math.random() * 9;
                chosenUnit = createUnit((int)unitIndex, Character.toChars(name + 97)[0]);
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
        else{
            return new HorsebackArcher(symbol);
        }
    }

    public void putUnits(Battlefield field){
        int row = 0;
        double column;
        for (Character symbol : team.keySet()){
            column = field.getLength() * Math.random();
            while (!field.getCell(new int[]{row, (int) column}).isEmpty()){
                column = field.getLength() * Math.random();
            }
            field.put(symbol, new int[] {row, (int)column});
        }

    }

    public void playRound(Battlefield field, HashMap<Character,Unit> enemyTeam){
        for (Character symbol : team.keySet()) {
            if(team.get(symbol).canAttack(field, enemyTeam, aimSymbols)){
                unitAttack(field, enemyTeam, aimSymbols, team.get(symbol));
            }
            else{
                unitMove(field, team.get(symbol));
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
        return team.isEmpty();
    }

}


