package Test;

import BaumansGate.Field.Battlefield;
import BaumansGate.Field.Cell;
import BaumansGate.Units.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnitsTest {

    @Test
    public void testMovementFine(){
        boolean swampWalkerTest, hillWalkerTest, treeWalkerTest, swampArcherTest, hillArcherTest, treeArcherTest ,swampHorsemanTest, hillHorsemanTest, treeHorsemanTest;
        Battlefield field = new Battlefield(9);
        Spearman testWalker1 = new Spearman('1'), testWalker2 = new Spearman('2'), testWalker3 = new Spearman('3');
        field.put('1', new int[]{8, 0});
        field.put('2', new int[]{8, 1});
        field.put('3', new int[]{8, 2});
        field.fill('#', new int[]{7,0});
        field.fill('@', new int[]{7,1});
        field.fill('!', new int[]{7,2});
        swampWalkerTest = !field.getCell(new int[]{2,0}).isAvailableToMove(testWalker1, field, 0) && field.getCell(new int[]{3,0}).isAvailableToMove(testWalker1, field, 0);
        hillWalkerTest = !field.getCell(new int[]{2,1}).isAvailableToMove(testWalker2, field, 0) && field.getCell(new int[]{3,1}).isAvailableToMove(testWalker2, field, 0);
        treeWalkerTest = !field.getCell(new int[]{2,2}).isAvailableToMove(testWalker3, field, 0) && field.getCell(new int[]{3,2}).isAvailableToMove(testWalker3, field, 0);

        ShortbowArcher testArcher1 = new ShortbowArcher('4'), testArcher2 = new ShortbowArcher('5'), testArcher3 = new ShortbowArcher('6');
        field.put('4', new int[]{8, 3});
        field.put('5', new int[]{8, 4});
        field.put('6', new int[]{8, 5});
        field.fill('#', new int[]{7,3});
        field.fill('@', new int[]{7,4});
        field.fill('!', new int[]{7,5});
        swampArcherTest = !field.getCell(new int[]{4,3}).isAvailableToMove(testArcher1, field, 0) && field.getCell(new int[]{5,3}).isAvailableToMove(testArcher1, field, 0);
        hillArcherTest = !field.getCell(new int[]{4,4}).isAvailableToMove(testArcher2, field, 0) && !field.getCell(new int[]{5,4}).isAvailableToMove(testArcher2, field, 0) && field.getCell(new int[]{6,4}).isAvailableToMove(testArcher2, field, 0);
        treeArcherTest = field.getCell(new int[]{4,5}).isAvailableToMove(testArcher3, field, 0);

        Knight testHorseman1 = new Knight('7'), testHorseman2 = new Knight('8'), testHorseman3 = new Knight('9');
        field.put('7', new int[]{8, 6});
        field.put('8', new int[]{8, 7});
        field.put('9', new int[]{8, 8});
        field.fill('#', new int[]{7,6});
        field.fill('@', new int[]{7,7});
        field.fill('!', new int[]{7,8});
        swampHorsemanTest = !field.getCell(new int[]{2,6}).isAvailableToMove(testHorseman1, field, 0) && !field.getCell(new int[]{3,6}).isAvailableToMove(testHorseman1, field, 0) && field.getCell(new int[]{4,6}).isAvailableToMove(testHorseman1, field, 0);
        hillHorsemanTest = !field.getCell(new int[]{2,7}).isAvailableToMove(testHorseman2, field, 0) && field.getCell(new int[]{3,7}).isAvailableToMove(testHorseman2, field, 0);
        treeHorsemanTest = !field.getCell(new int[]{2,8}).isAvailableToMove(testHorseman3, field, 0) && field.getCell(new int[]{3,8}).isAvailableToMove(testHorseman3, field, 0);


        assertTrue(swampWalkerTest && hillWalkerTest && treeWalkerTest && swampArcherTest && hillArcherTest && treeArcherTest && swampHorsemanTest && hillHorsemanTest && treeHorsemanTest );
    }

    @Test
    public void testAttackRange(){
        Battlefield field = new Battlefield(15);

        ShortbowArcher testUnit = new ShortbowArcher('0');

        field.put('0', new int[]{6, 6});

        boolean isAlgorithmWorkingCorrect = true;
        Cell testUnitCell = field.getCell(new int[]{6,6}), curCell;

        for (int i = 0; i < 15; i ++){
            for (int j = 0; j < 15; j ++){
                curCell = field.getCell(new int[] {i, j});
                if (curCell.isEmpty() && (int)testUnitCell.calculateDistanceToOtherCell(curCell.getPosition()) <= testUnit.getAttackRange()){
                    isAlgorithmWorkingCorrect = isAlgorithmWorkingCorrect && curCell.isAvailableToAttack(testUnit.getAttackRange(), testUnitCell.getPosition());
                }
                else if (curCell.isEmpty()) {
                    isAlgorithmWorkingCorrect = isAlgorithmWorkingCorrect && !curCell.isAvailableToAttack(testUnit.getAttackRange(), testUnitCell.getPosition());
                }

            }
        }
        assertTrue(isAlgorithmWorkingCorrect);
    }

    @Test
    public void testAttack(){
        Battlefield field = new Battlefield(15);
        CrossbowArcher testUnit = new CrossbowArcher('0');
        Swordsman target = new Swordsman('a');
        int targetHealthPoints = target.getHealthPoints(), targetDefence = target.getDefence();

        HashMap<Character, Unit> enemyTeam = new HashMap<>();
        enemyTeam.put('a',target);
        LinkedList<Character> aimSymbols = new LinkedList<>();

        field.put('0', new int[]{6,6});
        field.put('a', new int[] {4,8});

        testUnit.canAttack(field,enemyTeam, aimSymbols);
        testUnit.attack(target);

        assertEquals(targetDefence + targetHealthPoints - testUnit.getDamage(), target.getHealthPoints() + target.getDefence());
    }

    @Test
    public void testMovement(){
        Battlefield field = new Battlefield(9);
        Axeman testUnit = new Axeman('0'), obstacleUnit = new Axeman('1');
        field.put('0',new int[]{7,7});
        field.put('1', new int[]{7,3});
        boolean testCorrectMovement = true, testMovementToTakenCell = true, testMovementToOutBordersSpace;
        Cell curCell;

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                curCell = field.getCell(new int[] {i, j});
                if (curCell.isUnit()){
                    testMovementToTakenCell =  testMovementToTakenCell && !curCell.isAvailableToMove(testUnit, field, 0);
                }
                else if (curCell.isEmpty() && ((i == 7 || j == 7) && curCell.calculateDistanceToOtherCell(new int[]{7, 7}) <= testUnit.getMoveRange())) {
                    testCorrectMovement = testCorrectMovement && curCell.isAvailableToMove(testUnit, field, 0);
                }
                else if (curCell.isEmpty() && Math.abs(i - 7) == Math.abs(j - 7) && Math.abs(i - 7) + (int)Math.abs(i - 7) / 2 <= testUnit.getMoveRange()) {
                    testCorrectMovement = testCorrectMovement && curCell.isAvailableToMove(testUnit, field, 0);
                }
                else{
                    testCorrectMovement = testCorrectMovement && !curCell.isAvailableToMove(testUnit, field, 0);
                }
            }
        }

        testMovementToOutBordersSpace = !(testUnit.move(new int[] {-1, 2}, field, 0) && testUnit.move(new int[] {2, -6}, field, 0)
                && testUnit.move(new int[] {100, 5}, field, 0) && testUnit.move(new int[] {5, 100}, field, 0));

        assertTrue(testCorrectMovement && testMovementToTakenCell && testMovementToOutBordersSpace);

    }

    @Test
    public void testUnitDeath(){
        Swordsman testUnit = new Swordsman(' ');
        testUnit.takeDamage(testUnit.getHealthPoints() + testUnit.getDefence());
        assertTrue(testUnit.isDead());
    }

    @Test
    public void testUnitDefence(){
        Swordsman testUnit = new Swordsman(' ');
        int defaultDefence = testUnit.getDefence(), defaultHealthPoints = testUnit.getHealthPoints(), remainDefence;
        boolean testLittleDamage, testDefenceHealthPointsTransition;

        testUnit.takeDamage(1);
        testLittleDamage = testUnit.getDefence() + 1 == defaultDefence;
        remainDefence = testUnit.getDefence();

        testUnit.takeDamage(defaultDefence);
        testDefenceHealthPointsTransition = testUnit.getDefence() == 0 && testUnit.getHealthPoints() + (defaultDefence - remainDefence) == defaultHealthPoints;
        assertTrue(testLittleDamage && testDefenceHealthPointsTransition);
    }

}
