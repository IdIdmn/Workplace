package BaumansGate.Buildings;

import BaumansGate.Players.User;

import java.io.Serializable;

abstract public class Building implements Serializable {

    abstract public void create(User player);

    abstract public int getMaxAmount();

    abstract public int getAmount();

    abstract public String getName();

    abstract public int[] getCost();

}
