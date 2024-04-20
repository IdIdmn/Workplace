package BaumansGate.Output;

import BaumansGate.Field.Battlefield;
import BaumansGate.Field.Cell;
import BaumansGate.Units.Unit;

public class Display {

    public static void makeGap(){
        System.out.println();
        System.out.println();
        System.out.println("-------------------------------------------------");
        System.out.println();
        System.out.println();
    }

    public static void displayField(Battlefield field){
        System.out.print("\n   ");
        for (int i = 0; i < field.getLength(); i++){
            System.out.printf("%3d",i);
        }
        System.out.println();
        System.out.print("    ");
        for (int i = 0; i < field.getLength()*3 - 1; i++){
            System.out.print('-');
        }
        System.out.println();
        Cell currentCell;
        for (int i = 0; i < field.getLength(); i++ ){
            System.out.printf("%3d|", i);
            for (int j = 0; j < field.getLength(); j++) {
                currentCell = field.getCell(new int[] {i, j});
                if (Character.isDigit(currentCell.getSymbol())){
                    System.out.printf("\u001B[32m%2s \u001B[0m", currentCell.getSymbol());
                }
                else if (Character.isLetter(currentCell.getSymbol())){
                    System.out.printf("\u001B[31m%2s \u001B[0m", currentCell.getSymbol());
                }
                else {
                    System.out.printf("%2s ", field.getCell(new int[] {i, j}).getSymbol());
                }
            }
            System.out.println();
        }
    }

    public static void displayMoveCells(Unit unit, Battlefield field, double fineDecrease){
        System.out.print("   ");
        for (int i = 0; i < field.getLength(); i++){
            System.out.printf("%3d",i);
        }
        System.out.println();
        System.out.print("    ");
        for (int i = 0; i < field.getLength() * 3 - 1; i++){
            System.out.print('-');
        }
        System.out.println();
        Cell currentCell;
        for (int i = 0; i < field.getLength(); i++ ){
            System.out.printf("%3d|", i);
            for (int j = 0; j < field.getLength(); j++) {
                currentCell = field.getCell(new int[] {i, j});
                if (currentCell.isAvailableToMove(unit, field, fineDecrease)) {
                    System.out.printf("\u001B[42m%2s \u001B[0m", currentCell.getSymbol());
                }
                else {
                    if (Character.isDigit(currentCell.getSymbol())){
                        System.out.printf("\u001B[32m%2s \u001B[0m", currentCell.getSymbol());
                    }
                    else if (Character.isLetter(currentCell.getSymbol())){
                        System.out.printf("\u001B[31m%2s \u001B[0m", currentCell.getSymbol());
                    }
                    else {
                        System.out.printf("%2s ", field.getCell(new int[] {i, j}).getSymbol());
                    }
                }
            }
            System.out.println();
        }
    }

    public static void displayAttackCells(Unit attackingUnit, Battlefield field){
        Cell currentCell;
        System.out.print("   ");
        for (int i = 0; i < field.getLength(); i++){
            System.out.printf("%3d",i);
        }
        System.out.println();
        System.out.print("    ");
        for (int i = 0; i < field.getLength() * 3 - 1; i++){
            System.out.print('-');
        }
        System.out.println();
        for (int i = 0; i < field.getLength(); i++){
            System.out.printf("%3d|", i);
            for (int j = 0; j < field.getLength(); j++) {
                currentCell = field.getCell(new int[] {i, j});
                if (currentCell.isUnit() && attackingUnit.isEnemy(currentCell.getSymbol()) && currentCell.isAvailableToAttack(attackingUnit.getAttackRange(), field.getUnitPosition(attackingUnit.getSymbol()))){
                    System.out.printf("\u001B[43m%2s \u001B[0m", currentCell.getSymbol());
                }
                else {
                    if (Character.isDigit(currentCell.getSymbol())){
                        System.out.printf("\u001B[32m%2s \u001B[0m", currentCell.getSymbol());
                    }
                    else if (Character.isLetter(currentCell.getSymbol())){
                        System.out.printf("\u001B[31m%2s \u001B[0m", currentCell.getSymbol());
                    }
                    else {
                        System.out.printf("%2s ", field.getCell(new int[] {i, j}).getSymbol());
                    }
                }
            }
            System.out.println();
        }
    }

    public static void clear(){
        for(int i = 0; i < 28; i++){
            System.out.println();
        }
    }

}
