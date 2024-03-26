public class Cell {

    private char Symbol;
    private int[] Position;

    Cell(char symbol, int[] position){
        Symbol = symbol;
        Position = position;
    }

    public char getSymbol(){
        return Symbol;
    }

    public void setSymbol(char newSymbol){
        Symbol = newSymbol;
    }

    public double getMultiplier(Unit unit){
        if (unit instanceof Walker){
            if (Symbol == '.') return 1.0;
            else if (Symbol == '#') return 1.5;
            else if (Symbol == '@') return 2.0;
            else return 1.2;
        }
        else if (unit instanceof Archer){
            if (Symbol == '.') return 1.0;
            else if (Symbol == '#') return 1.8;
            else if (Symbol == '@') return 2.2;
            else return 1;
        }
        else{
            if (Symbol == '.') return 1.0;
            else if (Symbol == '#') return 2.2;
            else if (Symbol == '@') return 1.2;
            else return 1.5;
        }
    }

    public boolean isUnit(){
        return (Symbol != '.' && Symbol != '#' && Symbol != '@' && Symbol != '!');
    }

    public boolean isEmpty(){
        return (Symbol == '.');
    }

    public double calculateDistanceToOtherCell(int[] otherCellPosition){
        return Math.sqrt(Math.pow((Position[0] - otherCellPosition[0]), 2) + Math.pow((Position[1] - otherCellPosition[1]), 2));
    }

    public boolean isAvailableToAttack(int attackingUnitAttackRange, int[] attackingUnitPosition){
        return ((int)calculateDistanceToOtherCell(attackingUnitPosition) <= attackingUnitAttackRange);
    }

    public boolean isAvailableToMove(Unit unit, Battlefield field){
        int moveRow = Position[0], moveColumn = Position[1];
        if(field.getCell(Position).isEmpty()) {
            int unitRow = field.getUnitPosition(unit.getSymbol())[0], unitColumn = field.getUnitPosition(unit.getSymbol())[1];
            double distance = 0.0;
            if (unitRow == moveRow) {
                if (unitColumn < moveColumn) {
                    for (int curColumn = unitColumn + 1; curColumn <= moveColumn; curColumn++) {
                        if (field.getCell(new int[] {unitRow, curColumn}).isUnit()) {
                            return false;
                        }
                        distance += field.getCell(new int[] {unitRow, curColumn}).getMultiplier(unit);
                    }
                }
                else {
                    for (int curColumn = unitColumn - 1; curColumn >= moveColumn; curColumn--) {
                        if (field.getCell(new int[] {unitRow, curColumn}).isUnit()) {
                            return false;
                        }
                        distance += field.getCell(new int[] {unitRow, curColumn}).getMultiplier(unit);
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
                        distance += field.getCell(new int[] {curRow, unitColumn}).getMultiplier(unit);
                    }
                }
                else {
                    for (int curRow = unitRow - 1; curRow >= moveRow; curRow--) {
                        if (field.getCell(new int[] {curRow, unitColumn}).isUnit()) {
                            return false;
                        }
                        distance += field.getCell(new int[] {curRow, unitColumn}).getMultiplier(unit);
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
                            distance += (2 - cellNum % 2) * field.getCell(new int[] {unitRow - cellNum, unitColumn + cellNum}).getMultiplier(unit);
                        }
                        return (distance <= (double) unit.getMoveRange());
                    }
                    else{
                        for (int cellNum = 1; cellNum <= Math.abs(unitColumn - moveColumn); cellNum++){
                            if (field.getCell(new int[] {unitRow - cellNum, unitColumn - cellNum}).isUnit()) {
                                return false;
                            }
                            distance += (2 - cellNum % 2) * field.getCell(new int[] {unitRow - cellNum, unitColumn - cellNum}).getMultiplier(unit);
                        }
                    }
                }
                else{
                    if (moveColumn > unitColumn){
                        for (int cellNum = 1; cellNum <= Math.abs(unitColumn - moveColumn); cellNum++){
                            if (field.getCell(new int[] {unitRow + cellNum, unitColumn + cellNum}).isUnit()) {
                                return false;
                            }
                            distance += (2 - cellNum % 2) * field.getCell(new int[] {unitRow + cellNum, unitColumn + cellNum}).getMultiplier(unit);
                        }
                    }
                    else{
                        for (int cellNum = 1; cellNum <= Math.abs(unitColumn - moveColumn); cellNum++){
                            if (field.getCell(new int[] {unitRow + cellNum, unitColumn - cellNum}).isUnit()) {
                                return false;
                            }
                            distance += (2 - cellNum % 2) * field.getCell(new int[] {unitRow + cellNum, unitColumn - cellNum}).getMultiplier(unit);
                        }
                    }
                }
                return (distance <= (double) unit.getMoveRange());
            }

        }
        return false;
    }

}
