package Units;

public class CrossbowArcher extends Archer implements CanWalk, CanShoot{

    public CrossbowArcher(char symbol){
        healthPoints = 40;
        damage = 7;
        attackRange = 6;
        defence = 3;
        moveRange = 2;
        cost = 23;
        this.symbol = symbol;
        name = "Арбалетчик";
    }

}
