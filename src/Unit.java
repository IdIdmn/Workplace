import java.util.HashMap;
import java.util.LinkedList;

public class Unit{

    protected int HealthPoints;
    protected int Damage;
    protected int AttackRange;
    protected int Defence;
    protected int MoveRange;
    protected int Cost;
    protected char Symbol;
    protected String Name;

    Unit(){};

    public char getSymbol(){
        return  Symbol;
    }

    public void setSymbol(char newSymbol){
        Symbol = newSymbol;
    }

    public String getName(){
        return Name;
    }

    public int getMoveRange(){
        return MoveRange;
    }

    public int getDamage(){
        return Damage;
    }

    public int getCost(){
        return Cost;
    }

    public int getAttackRange(){
        return AttackRange;
    }

    public void takeDamage(int takenDamage){
        if (takenDamage > Defence){
            HealthPoints -= (takenDamage - Defence);
            Defence = 0;
        }
        else{
            Defence -= takenDamage;
        }
    }

    public void buff(int damageBuff, int attackRangeBuff, int moveRangeBuff){
        Damage += damageBuff;
        AttackRange += attackRangeBuff;
        MoveRange += moveRangeBuff;
    }

    public boolean isDead(){
        return (HealthPoints <= 0);
    }

    public void death(HashMap<Character, Unit> team, Battlefield field){
        field.remove(Symbol);
        team.remove(Symbol, this);
    }

    public boolean isEnemy(char otherUnitSymbol){
        if (Character.getNumericValue(Symbol) < 10) {
            return (Character.getNumericValue(otherUnitSymbol) >= 10);
        }
        return (Character.getNumericValue(otherUnitSymbol) < 10);
    }

    public boolean move(int[] newPosition, Battlefield field){
        if (newPosition[0] < 0 || newPosition[0] >= field.getLength() || newPosition[1] < 0 || newPosition[1] >= field.getLength() || !field.getCell(newPosition).isAvailableToMove(this, field)){
            return false;
        }
        field.remove(Symbol);
        field.put(Symbol, newPosition);
        return true;
    }

    public boolean canAttack(Battlefield field, HashMap<Character, Unit> team, LinkedList<Character> aimSymbols){
        boolean flag = false;
        for (Character attackedUnitSymbol : team.keySet()){
            if (field.getCell(field.getUnitPosition(attackedUnitSymbol)).isAvailableToAttack(AttackRange, field.getUnitPosition(Symbol))){
                aimSymbols.add(attackedUnitSymbol);
                flag = true;
            }
        }
        return flag;
    }

    public void attack(Unit enemyUnit){
        enemyUnit.takeDamage(Damage);
    }

    @Override
    public String toString(){
        return String.format("%s - Класс: %-24s | Дальность перемещения: %d | Дальность атаки: %d | Урон: %d | Здоровье: %2d | Защита: %d",  Symbol, Name, MoveRange, AttackRange, Damage, HealthPoints, Defence);
    }

}