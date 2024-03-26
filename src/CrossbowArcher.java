public class CrossbowArcher extends Archer implements CanWalk, CanShoot{

    CrossbowArcher(char symbol){
        HealthPoints = 40;
        Damage = 7;
        AttackRange = 6;
        Defence = 3;
        MoveRange = 2;
        Cost = 23;
        Symbol = symbol;
        Name = "Арбалетчик";
    }

}
