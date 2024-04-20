package BaumansGate.Units;

public class Knight extends Horseman implements CanRide, CanStab {

    public Knight(char symbol){
        healthPoints = 30;
        damage = 5;
        attackRange = 1;
        defence = 3;
        moveRange = 6;
        cost = 20;
        this.symbol = symbol;
        name = "Рыцарь";
    }

}
