public class Knight extends Horseman implements CanRide, CanStab{

    Knight(char symbol){
        HealthPoints = 30;
        Damage = 5;
        AttackRange = 1;
        Defence = 3;
        MoveRange = 6;
        Cost = 20;
        Symbol = symbol;
        Name = "Рыцарь";
    }

}
