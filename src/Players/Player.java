package Players;

import java.util.HashMap;

import Units.Unit;

public interface Player {

    public double getFineDecrease();

    public HashMap<Character, Unit> getTeam();

}
