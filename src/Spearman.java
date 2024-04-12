public class Spearman extends Walker implements CanWalk, CanStab{

    Spearman(char symbol){
        healthPoints = 35;
        damage = 3;
        attackRange = 1;
        defence = 4;
        moveRange = 6;
        cost = 15;
        this.symbol = symbol;
        name = "Копьеносец";
    }

}
