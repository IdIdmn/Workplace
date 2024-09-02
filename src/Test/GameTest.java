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
        User testPlayer1 = new User(0, 0,0 ), testPlayer2 = new User(30, 0, 0);
        testPlayer1.randomTeam();
        testPlayer2.randomTeam();
        testPlayer2.setGrainAmount(0);
        assertTrue(testPlayer1.isDefeated() && testPlayer2.isDefeated());
    }

    @Test
    public void testUnitBuying(){
        User testPlayer = new User(30, 1000, 1000);
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
        Game testGame = new Game(70, 1000, 1000);
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
        Game testGame = new Game(60, 1000, 1000);
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
        Game testGame = new Game(60, 1000, 1000);
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
        Game testGame = new Game(60, 1000, 1000);
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
}