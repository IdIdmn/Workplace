import java.io.Serializable;

public class Cell implements Serializable {

    private char symbol;
    private int[] position;

    Cell(char symbol, int[] position){
        this.symbol = symbol;
        this.position = position;
    }

    Cell(Cell newCell){
        symbol = newCell.getSymbol();
        position = newCell.getPosition();
    }

    public char getSymbol(){
        return symbol;
    }

    public int[] getPosition(){
        return position;
    }

    public void setSymbol(char newSymbol){
        symbol = newSymbol;
    }

    public double getMultiplier(Unit unit, Obstacle obstacle){
        if (unit instanceof Walker){
            return obstacle.getWalkerFine();
        }
        else if (unit instanceof Archer){
            return obstacle.getArcherFine();
        }
        else{
            return obstacle.getHorsemanFine();
        }
    }

    public boolean isUnit(){
        return (Character.isDigit(symbol) || Character.isLetter(symbol));
    }

    public boolean isEmpty(){
        return (symbol == '.');
    }

    public double calculateDistanceToOtherCell(int[] otherCellPosition){
        return Math.sqrt(Math.pow((position[0] - otherCellPosition[0]), 2) + Math.pow((position[1] - otherCellPosition[1]), 2));
    }

    public boolean isAvailableToAttack(int attackingUnitAttackRange, int[] attackingUnitPosition){
        return ((int)calculateDistanceToOtherCell(attackingUnitPosition) <= attackingUnitAttackRange);
    }

    public boolean isAvailableToMove(Unit unit, Battlefield field){
        int moveRow = position[0], moveColumn = position[1];
        if(isEmpty()) {
            int unitRow = field.getUnitPosition(unit.getSymbol())[0], unitColumn = field.getUnitPosition(unit.getSymbol())[1];
            Cell curCell;
            double distance = 0.0;
            if (unitRow == moveRow) {
                if (unitColumn < moveColumn) {
                    for (int curColumn = unitColumn + 1; curColumn <= moveColumn; curColumn++) {
                        if (field.getCell(new int[] {unitRow, curColumn}).isUnit()) {
                            return false;
                        }
                        curCell = new Cell(field.getCell(new int[] {unitRow, curColumn}));
                        distance += (curCell.isEmpty()) ? 1.0 : curCell.getMultiplier(unit, field.getObstacles().get(curCell.getSymbol()));
                    }
                }
                else {
                    for (int curColumn = unitColumn - 1; curColumn >= moveColumn; curColumn--) {
                        if (field.getCell(new int[] {unitRow, curColumn}).isUnit()) {
                            return false;
                        }
                        curCell = new Cell(field.getCell(new int[] {unitRow, curColumn}));
                        distance += (curCell.isEmpty()) ? 1.0 : curCell.getMultiplier(unit, field.getObstacles().get(curCell.getSymbol()));
                    }
                }
                return (distance <= (double) unit.getMoveRange());
            }
            else if (unitColumn == moveColumn) {
                if (unitRow < moveRow) {
                    for (int curRow = unitRow + 1; curRow <= moveRow; curRow++) {
                        if (field.getCell(new int[] {curRow, unitColumn}).isUnit()) {
                            return false;
                        }
                        curCell = new Cell(field.getCell(new int[] {curRow, unitColumn}));
                        distance += (curCell.isEmpty()) ? 1.0 : curCell.getMultiplier(unit, field.getObstacles().get(curCell.getSymbol()));
                    }
                }
                else {
                    for (int curRow = unitRow - 1; curRow >= moveRow; curRow--) {
                        if (field.getCell(new int[] {curRow, unitColumn}).isUnit()) {
                            return false;
                        }
                        curCell = new Cell(field.getCell(new int[] {curRow, unitColumn}));
                        distance += (curCell.isEmpty()) ? 1.0 : curCell.getMultiplier(unit, field.getObstacles().get(curCell.getSymbol()));
                    }
                }
                return (distance <= (double) unit.getMoveRange());
            }
            else if (Math.abs(unitColumn - moveColumn) == Math.abs(unitRow - moveRow)){
                if (moveRow < unitRow){
                    if(moveColumn > unitColumn){
                        for (int cellNum = 1; cellNum <= Math.abs(unitColumn - moveColumn); cellNum++){
                            if (field.getCell(new int[] {unitRow - cellNum, unitColumn + cellNum}).isUnit()) {
                                return false;
                            }
                            curCell = new Cell(field.getCell(new int[] {unitRow - cellNum, unitColumn + cellNum}));
                            distance += (2 - cellNum % 2) * ((curCell.isEmpty()) ? 1.0 : curCell.getMultiplier(unit, field.getObstacles().get(curCell.getSymbol())));
                        }
                        return (distance <= (double) unit.getMoveRange());
                    }
                    else{
                        for (int cellNum = 1; cellNum <= Math.abs(unitColumn - moveColumn); cellNum++){
                            if (field.getCell(new int[] {unitRow - cellNum, unitColumn - cellNum}).isUnit()) {
                                return false;
                            }
                            curCell = new Cell(field.getCell(new int[] {unitRow - cellNum, unitColumn - cellNum}));
                            distance += (2 - cellNum % 2) * ((curCell.isEmpty()) ? 1.0 : curCell.getMultiplier(unit, field.getObstacles().get(curCell.getSymbol())));
                        }
                    }
                }
                else{
                    if (moveColumn > unitColumn){
                        for (int cellNum = 1; cellNum <= Math.abs(unitColumn - moveColumn); cellNum++){
                            if (field.getCell(new int[] {unitRow + cellNum, unitColumn + cellNum}).isUnit()) {
                                return false;
                            }
                            curCell = new Cell(field.getCell(new int[] {unitRow + cellNum, unitColumn + cellNum}));
                            distance += (2 - cellNum % 2) * ((curCell.isEmpty()) ? 1.0 : curCell.getMultiplier(unit, field.getObstacles().get(curCell.getSymbol())));
                        }
                    }
                    else{
                        for (int cellNum = 1; cellNum <= Math.abs(unitColumn - moveColumn); cellNum++){
                            if (field.getCell(new int[] {unitRow + cellNum, unitColumn - cellNum}).isUnit()) {
                                return false;
                            }
                            curCell = new Cell(field.getCell(new int[] {unitRow + cellNum, unitColumn - cellNum}));
                            distance += (2 - cellNum % 2) * ((curCell.isEmpty()) ? 1.0 : curCell.getMultiplier(unit, field.getObstacles().get(curCell.getSymbol())));
                        }
                    }
                }
                return (distance <= (double) unit.getMoveRange());
            }

        }
        return false;
    }

}