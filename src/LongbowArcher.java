public class LongbowArcher extends Archer implements CanWalk, CanShoot{

    LongbowArcher(char symbol){
        healthPoints = 30;
        damage = 6;
        attackRange = 5;
        defence = 8;
        moveRange = 2;
        cost = 15;
        this.symbol = symbol;
        name = "Стрелок с длинным луком";
    }

}
