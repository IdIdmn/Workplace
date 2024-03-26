public class Cuirassier extends Horseman implements CanRide, CanStab{

    Cuirassier(char symbol){
        HealthPoints = 50;
        Damage = 2;
        AttackRange = 1;
        Defence = 7;
        MoveRange = 5;
        Cost = 23;
        Symbol = symbol;
        Name = "Кирасир";
    }

}
