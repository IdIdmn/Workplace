public class Swordsman extends Walker implements CanStab, CanWalk{

    Swordsman(char symbol){
        HealthPoints = 50;
        Damage = 5;
        AttackRange = 1;
        Defence = 8;
        MoveRange = 3;
        Cost = 10;
        Symbol = symbol;
        Name = "Мечник";
    }

}
