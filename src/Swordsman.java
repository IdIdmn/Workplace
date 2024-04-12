public class Swordsman extends Walker implements CanStab, CanWalk{

    Swordsman(char symbol){
        healthPoints = 50;
        damage = 5;
        attackRange = 1;
        defence = 8;
        moveRange = 3;
        cost = 10;
        this.symbol = symbol;
        name = "Мечник";
    }

}
