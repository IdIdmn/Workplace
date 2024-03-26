public class HorsebackArcher extends Horseman implements CanRide, CanShoot{

    HorsebackArcher(char symbol){
        HealthPoints = 25;
        Damage = 3;
        AttackRange = 3;
        Defence = 2;
        MoveRange = 5;
        Cost = 25;
        Symbol = symbol;
        Name = "Конный лучник";
    }

}
