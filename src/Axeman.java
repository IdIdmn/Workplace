public class Axeman extends Walker implements CanWalk, CanStab{

    Axeman(char symbol){
        HealthPoints = 45;
        Damage = 9;
        AttackRange = 1;
        Defence = 3;
        MoveRange = 4;
        Cost = 20;
        Symbol = symbol;
        Name = "Берсерк";
    }

}
