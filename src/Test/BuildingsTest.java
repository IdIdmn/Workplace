package Test;

import BaumansGate.Field.Battlefield;
import BaumansGate.Game.Game;
import BaumansGate.Game.GameMenu;
import BaumansGate.Players.User;
import BaumansGate.Units.Spearman;
import BaumansGate.Units.Unit;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BuildingsTest {

    User testPlayer;

    @BeforeEach
    public void CreateTestObjects(){
        testPlayer = new User(1000, 1000, 1000);
    }

    @Test
    public void testSimpleEnhancementBuildings(){
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
        testPlayer.getTown().getWorkshop().create(testPlayer);
        testPlayer.getTown().getWorkshop().getIncome(testPlayer);
        assertTrue(testPlayer.getMoney() > 0);
    }

    @Test
    public void testAcademy(){
        testPlayer.getTown().getAcademy().create(testPlayer);
        System.setIn(new ByteArrayInputStream("1 asd 10 10 10 10 10".getBytes()));
        testPlayer.getTown().getAcademy().makeUnit(testPlayer);

        boolean testNewUnitParameters, testUnitPurchase;
        Unit newUnit = new Unit(testPlayer.getAddedUnits().getFirst());
        testNewUnitParameters = newUnit.getHealthPoints() == 10 && newUnit.getDefence() == 10 && newUnit.getDamage() == 10 && newUnit.getAttackRange() == 10 && newUnit.getMoveRange() == 10 && newUnit.getName().equals("asd");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        testPlayer.fillTeam(new Scanner(new ByteArrayInputStream("10 +".getBytes())));

        String consoleOutput = outputStream.toString();
        testUnitPurchase = consoleOutput.contains("asd");

        assertTrue(testNewUnitParameters && testUnitPurchase);
    }

    @Test
    public void testMarket(){
        boolean testWoodRockTrade, testRockWoodTrade, testWoodMoneyTrade, testRockMoneyTrade;

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
        // В игре уже есть Дом Лекаря(2 лвл), Арсенал(3 лвл), Таверна(1лвл), 2 Ремесленные Мастерские и Академия

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
