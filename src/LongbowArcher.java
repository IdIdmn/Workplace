public class LongbowArcher extends Archer implements CanWalk, CanShoot{

    LongbowArcher(char symbol){
        HealthPoints = 30;
        Damage = 6;
        AttackRange = 5;
        Defence = 8;
        MoveRange = 2;
        Cost = 15;
        Symbol = symbol;
        Name = "Стрелок с длинным луком";
    }

}
