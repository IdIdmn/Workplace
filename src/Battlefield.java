import java.util.HashMap;

public class Battlefield{

    private final int Length = 15, amountOfObstacles = Length * 2 / 3;
    private Cell[][] Field = new Cell[Length][Length];
    private final char[] Obstacles = {'#', '@', '!'};
    HashMap<Character, int[]> UnitPositions = new HashMap<>();

    Battlefield(){
        for (int i = 0; i < Length; i++ ){
            for (int j = 0; j < Length; j++){
                Field[i][j] = new Cell('.', new int[] {i, j});
            }
        }
    }

    public int getLength(){
        return Length;
    }

    public Cell getCell(int[] position){
        return Field[position[0]][position[1]];
    }

    public int[] getUnitPosition(Character unitSymbol){
        return UnitPositions.get(unitSymbol);
    }

    public void fill(int[] position, char newSymbol){
        Field[position[0]][position[1]].setSymbol(newSymbol);
    }

    public void put(char unitSymbol, int[] newUnitPosition){
        int row = newUnitPosition[0], column = newUnitPosition[1];
        Field[row][column].setSymbol(unitSymbol);
        UnitPositions.put(unitSymbol, newUnitPosition);
    }

    public void putObstacles(){
        double row, column, obstacleIndex;
        for (int i = 0; i < amountOfObstacles; i++){
            row = 1 + Math.random() * 12; column = 0 + Math.random() * 14;
            while (!Field[(int)row][(int)column].isEmpty()){
                row = 1 + Math.random() * 12; column = 0 + Math.random() * 14;
            }
            obstacleIndex = 0 + 3 * Math.random();
            Field[(int)row][(int)column].setSymbol(Obstacles[(int)obstacleIndex]);
        }
    }

    public void remove(char unitSymbol){
        int row = UnitPositions.get(unitSymbol)[0], column = UnitPositions.get(unitSymbol)[1];
        Field[row][column].setSymbol('.');
        UnitPositions.remove(unitSymbol, new int[] {row, column});
    }

}