package BaumansGate.Players;

import BaumansGate.Units.Unit;

import java.util.HashMap;

public interface Player {

    public double getFineDecrease();

    public HashMap<Character, Unit> getTeam();

}
