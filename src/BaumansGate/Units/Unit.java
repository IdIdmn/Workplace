package BaumansGate.Units;

import BaumansGate.Field.Battlefield;
import BaumansGate.Output.GameLogger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class Unit implements Serializable {

    protected int healthPoints;
    protected int damage;
    protected int attackRange;
    protected int defence;
    protected int moveRange;
    protected int cost;
    protected char symbol;
    protected String name;

    public Unit(){};

    public Unit(Unit newUnit){
        healthPoints = newUnit.getHealthPoints();
        damage = newUnit.getDamage();
        attackRange = newUnit.getAttackRange();
        defence = newUnit.getDefence();
        moveRange = newUnit.getMoveRange();
        cost = newUnit.getCost();
        symbol = newUnit.getSymbol();
        name = newUnit.getName();
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getDefence() {
        return defence;
    }

    public char getSymbol(){
        return symbol;
    }

    public void setSymbol(char newSymbol){
        symbol = newSymbol;
    }

    public String getName(){
        return name;
    }

    public int getMoveRange(){
        return moveRange;
    }

    public int getDamage(){
        return damage;
    }

    public int getCost(){
        return cost;
    }

    public int getAttackRange(){
        return attackRange;
    }

    public void takeDamage(int takenDamage){
        if (takenDamage > defence){
            healthPoints -= (takenDamage - defence);
            defence = 0;
        }
        else{
            defence -= takenDamage;
        }
        GameLogger.logInfo(String.format("Юнит <%s> получил %d урона. Осталось: %d ХП и %d брони.", symbol, takenDamage, Math.max(0, healthPoints), defence));
    }

    public void buff(int healthPointsBuff,int damageBuff, int attackRangeBuff, int defenceBuff, int moveRangeBuff){
        healthPoints += healthPointsBuff;
        damage += damageBuff;
        attackRange += attackRangeBuff;
        defence += defenceBuff;
        moveRange += moveRangeBuff;
    }

    public boolean isDead(){
        return (healthPoints <= 0);
    }

    public void death(HashMap<Character, Unit> team, Battlefield field){
        field.remove(symbol);
        team.remove(symbol, this);
        GameLogger.logSevere(String.format("Юнит <%s> был повержен.", symbol));
    }

    public boolean isEnemy(char otherUnitSymbol){
        if (Character.getNumericValue(symbol) < 10) {
            return (Character.getNumericValue(otherUnitSymbol) >= 10);
        }
        return (Character.getNumericValue(otherUnitSymbol) < 10);
    }

    public boolean move(int[] newPosition, Battlefield field, double fineDecrease){
        if (newPosition[0] < 0 || newPosition[0] >= field.getLength() || newPosition[1] < 0 || newPosition[1] >= field.getLength() || !field.getCell(newPosition).isAvailableToMove(this, field, fineDecrease)){
            return false;
        }
        field.remove(symbol);
        field.put(symbol, newPosition);
        GameLogger.logInfo(String.format("Юнит <%s> переместился на клетку [%d,%d]", symbol, newPosition[0], newPosition[1]));
        return true;
    }

    public boolean canAttack(Battlefield field, HashMap<Character, Unit> enemyTeam, LinkedList<Character> aimSymbols){
        boolean flag = false;
        for (Character attackedUnitSymbol : enemyTeam.keySet()){
            if (field.getCell(field.getUnitPosition(attackedUnitSymbol)).isAvailableToAttack(attackRange, field.getUnitPosition(symbol))){
                aimSymbols.add(attackedUnitSymbol);
                flag = true;
            }
        }
        return flag;
    }

    public void attack(Unit enemyUnit){
        enemyUnit.takeDamage(damage);
        GameLogger.logInfo(String.format("Юнит <%s> атаковал <%s>, нанеся %d урона.", symbol, enemyUnit.getSymbol(), damage));
    }

    @Override
    public String toString(){
        return String.format("%s - Класс: %-24s | Дальность перемещения: %d | Дальность атаки: %d | Урон: %d | Здоровье: %2d | Защита: %d", symbol, name, moveRange, attackRange, damage, healthPoints, defence);
    }

}