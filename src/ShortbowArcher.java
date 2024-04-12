public class ShortbowArcher extends Archer implements CanWalk, CanShoot{

    ShortbowArcher(char symbol){
        healthPoints = 25;
        damage = 3;
        attackRange = 3;
        defence = 4;
        moveRange = 4;
        cost = 19;
        this.symbol = symbol;
        name = "Стрелок с коротким луком";
    }

}
