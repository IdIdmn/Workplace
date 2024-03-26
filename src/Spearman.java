public class Spearman extends Walker implements CanWalk, CanStab{

    Spearman(char symbol){
        HealthPoints = 35;
        Damage = 3;
        AttackRange = 1;
        Defence = 4;
        MoveRange = 6;
        Cost = 15;
        Symbol = symbol;
        Name = "Копьеносец";
    }

}
