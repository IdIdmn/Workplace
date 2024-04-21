package Test;


import BaumansGate.Field.*;
import BaumansGate.Game.Game;
import BaumansGate.Game.GameMenu;
import BaumansGate.Players.*;
import BaumansGate.Units.*;
import BaumansGate.Output.*;

import BaumansGate.Weather.Weather;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;



class GameTest {


    @Test
    public void testPlayerWin(){
        Bot testBot = new Bot(0);
        assertTrue(testBot.isDefeated());
    }

    @Test
    public void testBotWin() {
        User testPlayer1 = new User(0), testPlayer2 = new User(30);
        testPlayer1.randomTeam();
        testPlayer2.randomTeam();
        testPlayer2.setGrainAmount(0);
        assertTrue(testPlayer1.isDefeated() && testPlayer2.isDefeated());
    }

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

    @Test
    public void testUnitBuying(){
        User testPlayer = new User(30);
        testPlayer.randomTeam();
        assertFalse(testPlayer.getTeam().isEmpty());
    }

    @Test
    public void testBotGameplay(){
        Bot testBot = new Bot(10);
        HashMap<Character, Unit> enemyTeam = new HashMap<>();
        enemyTeam.put('1', new Swordsman('1'));
        Battlefield field = new Battlefield(10);
        boolean testBotAttack, testBotMovement;

        testBot.randomTeam();
        field.put('a', new int[]{0,0});
        field.put('1', new int[]{0,1});
        int defaultTargetDefence = enemyTeam.get('1').getDefence();

        testBot.playRound(field,enemyTeam);
        testBotAttack = enemyTeam.get('1').getDefence() < defaultTargetDefence;

        field.remove('1');
        field.put('1', new int[] {9,9});
        testBot.playRound(field,enemyTeam);
        testBotMovement = field.getUnitPosition('a')[0] > 0;

        assertTrue(testBotAttack && testBotMovement);
    }

    @Test
    public void testFieldDisplay(){
        int fieldLength = 10;

        Battlefield field = new Battlefield(fieldLength);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        field.putRandomObstacles();
        Display.displayField(field);

        String consoleOutput = outputStream.toString().trim();
        System.setOut(System.out);

        int freeCellsAmount = consoleOutput.length() - consoleOutput.replace(".","").length(),
                obstaclesAmount = consoleOutput.length() * 3 - consoleOutput.replace("#","").length() - consoleOutput.replace("@","").length()
                        - consoleOutput.replace("!","").length();
        assertTrue(obstaclesAmount == fieldLength * 3 / 2 && freeCellsAmount + obstaclesAmount == fieldLength * fieldLength);
    }

    @Test
    public void testWeather(){
        Game testGame = new Game(70);
        HashMap<Character, Unit> team = new HashMap<>();
        team.put('1', new Swordsman('1'));
        team.put('2', new LongbowArcher('2'));
        team.put('3', new Knight('3'));
        team.put('4', new HorsebackArcher('4'));
        testGame.getPlayer().setTeam(team);

        int swordsmanDamage = 5, swordsmanMoveRange = 3, longbowArcherMoveRange = 2, longbowArcherAttackRange = 5, knightDamage = 5, knightMoveRange = 6, horsebackArcherMoveRange = 5, horsebackArcherAttackRange = 3;
        boolean testRain, testSnow, testDraught, testFog, testClearWeather;

        Weather weather = new Weather();

        weather.startRain(testGame);
        testRain =  testGame.getPlayer().getTeam().get('2').getAttackRange() + 3 == longbowArcherAttackRange && testGame.getPlayer().getTeam().get('4').getAttackRange() + 3 == horsebackArcherAttackRange;
        weather.startClearWeather(testGame);
        testClearWeather = testGame.getPlayer().getTeam().get('2').getAttackRange() == longbowArcherAttackRange && testGame.getPlayer().getTeam().get('4').getAttackRange() == horsebackArcherAttackRange;

        weather.startSnowing(testGame);
        testSnow = testGame.getPlayer().getTeam().get('3').getMoveRange() + 2 == knightMoveRange && testGame.getPlayer().getTeam().get('4').getMoveRange() + 2 == horsebackArcherMoveRange;
        weather.startClearWeather(testGame);
        testClearWeather = testClearWeather && (testGame.getPlayer().getTeam().get('3').getMoveRange() == knightMoveRange && testGame.getPlayer().getTeam().get('4').getMoveRange() == horsebackArcherMoveRange);

        weather.startDrought(testGame);
        testDraught = testGame.getPlayer().getTeam().get('1').getMoveRange() + 1 == swordsmanMoveRange && testGame.getPlayer().getTeam().get('2').getMoveRange() + 1 == longbowArcherMoveRange;
        weather.startClearWeather(testGame);
        testClearWeather = testClearWeather && (testGame.getPlayer().getTeam().get('1').getMoveRange() == swordsmanMoveRange && testGame.getPlayer().getTeam().get('2').getMoveRange() == longbowArcherMoveRange);

        weather.startFog(testGame);
        testFog = testGame.getPlayer().getTeam().get('1').getDamage() + 2 == swordsmanDamage && testGame.getPlayer().getTeam().get('3').getDamage() + 2 == knightDamage;
        weather.startClearWeather(testGame);
        testClearWeather = testClearWeather && (testGame.getPlayer().getTeam().get('1').getDamage() == swordsmanDamage && testGame.getPlayer().getTeam().get('3').getDamage() == knightDamage);


        assertTrue(testRain && testSnow && testDraught && testFog && testClearWeather);

    }

    @Test
    public void testMapCreating(){
        Game testGame = new Game(60);
        Scanner in = new Scanner(new ByteArrayInputStream("10 2 @ 1 1 2 # 5 6 2 ! 7 3 4".getBytes()));
        // 10 - размер поля, 2 - разместить новое препятствие, @ - символ препятствия, 1 1 - его позиция, затем снова кидает в меню выбора действия. 4 - выход из редактора карт.
        testGame.createMap(in);
        boolean testSize, testObstaclePositions, testObstacleAmount;
        testSize = testGame.getField().getLength() == 10;
        testObstaclePositions = testGame.getField().getCell(new int[]{1, 1}).getSymbol() == '@' && testGame.getField().getCell(new int[]{5, 6}).getSymbol() == '#' && testGame.getField().getCell(new int[]{7, 3}).getSymbol() == '!';

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Display.displayField(testGame.getField());
        String consoleOutput = outputStream.toString();
        System.setOut(System.out);

        testObstacleAmount = (consoleOutput.length() * 3 - consoleOutput.replace("#","").length() - consoleOutput.replace("@","").length()
                - consoleOutput.replace("!","").length()) == 3;


        assertTrue(testSize && testObstaclePositions && testObstacleAmount);

    }

    @Test
    public void testObstacleCreating(){
        Game testGame = new Game(60);
        int startObstacleAmount = 3;
        Scanner in = new Scanner(new ByteArrayInputStream("10 1 asd % 1,2 2,3 3,4 4".getBytes()));
        // 10 - размер поля, 1 - создать новое препятствие, asd % - навзвание и символ нового препятствия, затем идут его штрафы для пешего воина, лучника и наездника, 4 - выйти из редактора.
        testGame.createMap(in);

        boolean testObstacleAmount, testNewObstacleParameters;
        testObstacleAmount = testGame.getField().getObstacles().size() == startObstacleAmount + 1;

        Obstacle newObstacle = new Obstacle(testGame.getField().getObstacles().get('%'));
        testNewObstacleParameters = newObstacle != null && newObstacle.getName().equals("asd") && newObstacle.getWalkerFine() == 1.2 && newObstacle.getArcherFine() == 2.3 && newObstacle.getHorsemanFine() == 3.4;

        assertTrue(testObstacleAmount && testNewObstacleParameters);
    }

    @Test
    public void testMapLoading(){
        Game testGame = new Game(60);
        int startObstacleAmount = 3;
        Scanner in = new Scanner(new ByteArrayInputStream("- testMapLoading".getBytes()));
        // 1 - выбор опции загрузки карты, '-' - отказ от удаления карт, далее имя нужной карты(её параметры, кол-во и расположение препятсвий известно заранее)
        testGame.loadMap(in);

        boolean testSize, testObstaclePositions, testObstacleAmount;
        testSize = testGame.getField().getLength() == 7;
        testObstaclePositions = testGame.getField().getCell(new int[]{1, 1}).getSymbol() == '@' && testGame.getField().getCell(new int[]{3, 6}).getSymbol() == '!' && testGame.getField().getCell(new int[]{5, 2}).getSymbol() == '#';

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Display.displayField(testGame.getField());
        String consoleOutput = outputStream.toString();
        System.setOut(System.out);

        testObstacleAmount = (consoleOutput.length() * 3 - consoleOutput.replace("#","").length() - consoleOutput.replace("@","").length()
                - consoleOutput.replace("!","").length()) == 3;


        assertTrue(testSize && testObstaclePositions && testObstacleAmount);
    }

    @Test
    public void testSimpleEnhancementBuildings(){
        User testPlayer = new User(0);
        HashMap<Character, Unit> team = new HashMap<>();
        team.put('1', new Spearman('1'));
        testPlayer.setTeam(team);
        int defaultSpearmanHealthPoints = 35, defaultSpearmanDefence = 4, defaultSpearmanDamage = 3;
        boolean testCreateBonus, testLevelUpBonus, testMaxLevelOvercome;

        testPlayer.getTown().getHospital().create(testPlayer);
        testPlayer.getTown().getArmory().create(testPlayer);
        testPlayer.getTown().getForge().create(testPlayer);

        testCreateBonus = testPlayer.getTeam().get('1').getHealthPoints() == defaultSpearmanHealthPoints + 1 && testPlayer.getTeam().get('1').getDamage() == defaultSpearmanDamage + 1
                && testPlayer.getTeam().get('1').getDefence() == defaultSpearmanDefence + 1;

        testPlayer.getTown().getHospital().levelUp(testPlayer);
        testPlayer.getTown().getArmory().levelUp(testPlayer);
        testPlayer.getTown().getForge().levelUp(testPlayer);

        testLevelUpBonus = testPlayer.getTeam().get('1').getHealthPoints() == defaultSpearmanHealthPoints + 2 && testPlayer.getTeam().get('1').getDamage() == defaultSpearmanDamage + 2
                && testPlayer.getTeam().get('1').getDefence() == defaultSpearmanDefence + 2;

        testPlayer.getTown().getHospital().levelUp(testPlayer);
        testPlayer.getTown().getArmory().levelUp(testPlayer);
        testPlayer.getTown().getForge().levelUp(testPlayer);

        testMaxLevelOvercome = !(testPlayer.getTown().getHospital().levelUp(testPlayer) || testPlayer.getTown().getArmory().levelUp(testPlayer) || testPlayer.getTown().getForge().levelUp(testPlayer));

        assertTrue(testCreateBonus && testLevelUpBonus && testMaxLevelOvercome);
    }

    @Test
    public void testTavern(){
        boolean testMoveRangeBonus, testFineDecrease, testMaxLevelOvercome;
        int defaultSpearmanMoveRange = 6;

        User testPlayer = new User(0);
        HashMap<Character, Unit> team = new HashMap<>();
        team.put('1', new Spearman('1'));
        testPlayer.setTeam(team);

        Battlefield field = new Battlefield(9);
        field.fill('!', new int[]{7, 0});
        field.put('1',new int[] {8, 0});

        testFineDecrease = !field.getCell(new int []{2, 0}).isAvailableToMove(testPlayer.getTeam().get('1'), field, testPlayer.getFineDecrease());

        System.setIn(new ByteArrayInputStream("2".getBytes()));
//      1 - увеличить дальность перемещения, 2 - уменьшить штрафы препятствий
        testPlayer.getTown().getTavern().create(testPlayer);
        testFineDecrease = testFineDecrease && field.getCell(new int []{2, 0}).isAvailableToMove(testPlayer.getTeam().get('1'), field, testPlayer.getFineDecrease());

        System.setIn(new ByteArrayInputStream("1".getBytes()));
        testPlayer.getTown().getTavern().levelUp(testPlayer);
        testMoveRangeBonus = testPlayer.getTeam().get('1').getMoveRange() == defaultSpearmanMoveRange + 1;

        System.setIn(new ByteArrayInputStream("1".getBytes()));
        testPlayer.getTown().getTavern().levelUp(testPlayer);

        System.setIn(new ByteArrayInputStream("1".getBytes()));
        testMaxLevelOvercome = !testPlayer.getTown().getTavern().levelUp(testPlayer);

        System.setIn(System.in);
        assertTrue(testFineDecrease && testMoveRangeBonus && testMaxLevelOvercome);
    }

    @Test
    public void testWorkshop(){
        User testPlayer = new User(0);
        testPlayer.getTown().getWorkshop().create(testPlayer);
        testPlayer.getTown().getWorkshop().getIncome(testPlayer);
        assertTrue(testPlayer.getMoney() > 0);
    }

    @Test
    public void testAcademy(){
        User testPlayer = new User(10);
        testPlayer.getTown().getAcademy().create(testPlayer);
        System.setIn(new ByteArrayInputStream("1 asd 10 10 10 10 10".getBytes()));
        testPlayer.getTown().getAcademy().makeUnit(testPlayer);

        boolean testNewUnitParameters, testUnitPurchase;
        Unit newUnit = new Unit(testPlayer.getAddedUnits().get(0));
        testNewUnitParameters = newUnit.getHealthPoints() == 10 && newUnit.getDefence() == 10 && newUnit.getDamage() == 10 && newUnit.getAttackRange() == 10 && newUnit.getMoveRange() == 10 && newUnit.getName().equals("asd");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        testPlayer.fillTeam(new Scanner(System.in));

        String consoleOutput = outputStream.toString();
        testUnitPurchase = consoleOutput.contains("asd");

        assertTrue(testNewUnitParameters && testUnitPurchase);
    }

    @Test
    public void testMarket(){
        boolean testWoodRockTrade, testRockWoodTrade, testWoodMoneyTrade, testRockMoneyTrade;

        User testPlayer = new User(10);
        int curMoney = 100, curWood = 100, curRocks = 100, moneyChange, woodChange, rocksChange;
        testPlayer.setMoney(curMoney);
        testPlayer.setBuildingResources(new int[]{curWood, curRocks});

        testPlayer.getTown().getMarket().create(testPlayer);

        System.setIn(new ByteArrayInputStream("+ 1".getBytes()));
        testPlayer.getTown().getMarket().trade(testPlayer);
        woodChange = testPlayer.getTown().getMarket().getWoodRockRate()[0];
        rocksChange = testPlayer.getTown().getMarket().getWoodRockRate()[1];
        testWoodRockTrade = testPlayer.getBuildingResources()[0] == curWood - woodChange && testPlayer.getBuildingResources()[1] == curRocks + rocksChange;

        System.setIn(new ByteArrayInputStream("+ 2".getBytes()));
        testPlayer.getTown().getMarket().trade(testPlayer);
        testRockWoodTrade = testPlayer.getBuildingResources()[0] == curWood && testPlayer.getBuildingResources()[1] == curRocks;

        System.setIn(new ByteArrayInputStream("+ 3".getBytes()));
        testPlayer.getTown().getMarket().trade(testPlayer);
        woodChange = testPlayer.getTown().getMarket().getWoodMoneyRate()[0];
        moneyChange = testPlayer.getTown().getMarket().getWoodMoneyRate()[1];
        testWoodMoneyTrade = testPlayer.getBuildingResources()[0] == curWood - woodChange && testPlayer.getMoney() == curMoney + moneyChange;

        curMoney += moneyChange;

        System.setIn(new ByteArrayInputStream("+ 4".getBytes()));
        testPlayer.getTown().getMarket().trade(testPlayer);
        rocksChange = testPlayer.getTown().getMarket().getRockMoneyRate()[0];
        moneyChange = testPlayer.getTown().getMarket().getRockMoneyRate()[1];
        testRockMoneyTrade = testPlayer.getBuildingResources()[1] == curRocks - rocksChange && testPlayer.getMoney() == curMoney + moneyChange;

        assertTrue(testWoodRockTrade && testRockWoodTrade && testWoodMoneyTrade && testRockMoneyTrade);


    }

    @Test
    public void testBuildingsProgressSave(){
        boolean testAmount, testLevels;
        int hospitalAmount, armoryAmount, forgeAmount, tavernAmount, workshopAmount, marketAmount, academyAmount, hospitalLevel, armoryLevel, forgeLevel, tavernLevel;
        System.setIn(new ByteArrayInputStream("- testBuildingsProgress".getBytes()));
        Game testGame = GameMenu.loadGame();
        // В игре уже есть Дом Лекаря(2 лвл), Арсенал(3 лвл), Таверна(1лвл), Ремесленная Мастерская и Академия

        hospitalAmount = testGame.getPlayer().getTown().getHospital().getAmount();
        armoryAmount = testGame.getPlayer().getTown().getArmory().getAmount();
        forgeAmount = testGame.getPlayer().getTown().getForge().getAmount();
        tavernAmount = testGame.getPlayer().getTown().getTavern().getAmount();
        workshopAmount = testGame.getPlayer().getTown().getWorkshop().getAmount();
        marketAmount = testGame.getPlayer().getTown().getMarket().getAmount();
        academyAmount = testGame.getPlayer().getTown().getAcademy().getAmount();

        testAmount = hospitalAmount == 1 && armoryAmount == 1 && forgeAmount == 0 && tavernAmount == 1 && workshopAmount == 2 && marketAmount == 0 && academyAmount == 1;

        hospitalLevel = testGame.getPlayer().getTown().getHospital().getLevel();
        armoryLevel = testGame.getPlayer().getTown().getArmory().getLevel();
        forgeLevel = testGame.getPlayer().getTown().getForge().getLevel();
        tavernLevel = testGame.getPlayer().getTown().getTavern().getLevel();

        testLevels = hospitalLevel == 2 && armoryLevel == 3 & forgeLevel == 0 && tavernLevel == 1;

        assertTrue(testAmount && testLevels);
    }
}