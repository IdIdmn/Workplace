package BaumansGate.Units;

public class HorsebackArcher extends Horseman implements CanRide, CanShoot {

    public HorsebackArcher(char symbol){
        healthPoints = 25;
        damage = 3;
        attackRange = 3;
        defence = 2;
        moveRange = 5;
        cost = 25;
        this.symbol = symbol;
        name = "Конный лучник";
    }

}
