package Buildings;

import Players.User;

abstract public class EnhancementBuilding extends Building {

    private int maxAmount = 1;

    public int getMaxAmount(){
        return maxAmount;
    };

    abstract public int getLevel();

    abstract public void upgradeParam(User player);

    abstract public boolean levelUp(User player);

}
