package Buildings;

import java.io.Serializable;

import Players.User;

abstract public class Building implements Serializable {

    abstract public void create(User player);

    abstract public int getMaxAmount();

    abstract public int getAmount();

    abstract public String getName();

    abstract public int[] getCost();

}
