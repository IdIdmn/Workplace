public class TemplateWalker extends Walker implements CanWalk{

    TemplateWalker(String name, int healthPoints, int damage, int attackRange, int defence, int moveRange){
        this.healthPoints = healthPoints;
        this.damage = damage;
        this.attackRange = attackRange;
        this.defence = defence;
        this.moveRange = moveRange;
        this.cost = 30 + (int)((0.5 - Math.random()) * 20);
        this.name = name;
    }

}
