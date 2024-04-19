package Units;

public class Axeman extends Walker implements CanWalk, CanStab{

    public Axeman(char symbol){
        healthPoints = 45;
        damage = 9;
        attackRange = 1;
        defence = 3;
        moveRange = 4;
        cost = 20;
        this.symbol = symbol;
        name = "Берсерк";
    }

}
