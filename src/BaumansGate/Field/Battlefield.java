package BaumansGate.Field;

import java.io.Serializable;
import java.util.HashMap;

public class Battlefield implements Serializable {

    private int length;
    private Cell[][] field;
    private HashMap<Character, Obstacle> obstacles = new HashMap<>();
    HashMap<Character, int[]> unitPositions = new HashMap<>();

    public Battlefield(int fieldSize) {
        length = fieldSize;
        field = new Cell[length][length];
        obstacles.put('#',new Obstacle("Болото", 1.5, 1.8, 2.2));
        obstacles.put('@',new Obstacle("Холм", 2.0, 2.2, 1.2));
        obstacles.put('!',new Obstacle("Дерево", 1.2, 1.0, 1.5));
        for (int i = 0; i < length; i++ ){
            for (int j = 0; j < length; j++){
                field[i][j] = new Cell('.', new int[] {i, j});
            }
        }
    }

    public int getLength(){
        return length;
    }

    public Cell getCell(int[] position){
        return field[position[0]][position[1]];
    }

    public int[] getUnitPosition(Character unitSymbol){
        return unitPositions.get(unitSymbol);
    }

    public HashMap<Character, Obstacle> getObstacles(){
        return obstacles;
    }

    public void fill(char newSymbol, int[] position){
        field[position[0]][position[1]].setSymbol(newSymbol);
    }

    public void put(char unitSymbol, int[] newUnitPosition){
        int row = newUnitPosition[0], column = newUnitPosition[1];
        field[row][column].setSymbol(unitSymbol);
        unitPositions.put(unitSymbol, newUnitPosition);
    }

    public void putRandomObstacles(){
        double row, column, obstacleIndex;
        Character[] obstacleSymbols = obstacles.keySet().toArray(new Character[obstacles.size()]);
        for (int i = 0; i < length * 3 / 2; i++){
            row = 1 + Math.random() * (length - 3); column = 0 + Math.random() * (length - 1);
            while (!field[(int)row][(int)column].isEmpty()){
                row = 1 + Math.random() * (length - 3); column = 0 + Math.random() * (length - 1);
            }
            obstacleIndex = 0 + obstacles.size() * Math.random();
            field[(int)row][(int)column].setSymbol(obstacleSymbols[(int) obstacleIndex]);
        }
    }

    public void remove(char unitSymbol){
        int row = unitPositions.get(unitSymbol)[0], column = unitPositions.get(unitSymbol)[1];
        field[row][column].setSymbol('.');
        unitPositions.remove(unitSymbol, new int[] {row, column});
    }

}