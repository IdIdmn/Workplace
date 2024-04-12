public class Cuirassier extends Horseman implements CanRide, CanStab{

    Cuirassier(char symbol){
        healthPoints = 50;
        damage = 2;
        attackRange = 1;
        defence = 7;
        moveRange = 5;
        cost = 23;
        this.symbol = symbol;
        name = "Кирасир";
    }

}
