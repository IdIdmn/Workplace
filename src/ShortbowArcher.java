public class ShortbowArcher extends Archer implements CanWalk, CanShoot{

    ShortbowArcher(char symbol){
        HealthPoints = 25;
        Damage = 3;
        AttackRange = 3;
        Defence = 4;
        MoveRange = 4;
        Cost = 19;
        Symbol = symbol;
        Name = "Стрелок с коротким луком";
    }

}
