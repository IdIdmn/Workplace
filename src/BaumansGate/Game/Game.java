package BaumansGate.Game;

import BaumansGate.Field.Battlefield;
import BaumansGate.Output.Display;
import BaumansGate.Players.Bot;
import BaumansGate.Players.User;
import BaumansGate.Weather.Weather;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Game implements Serializable {

    private Battlefield field;
    private int amountOfMoney;
    private User player;
    private Bot enemyPlayer;
    private int currentRound, currentGame;
    private String name = "Game";
    private Weather gameWeather = new Weather();

    public Game(int money){
        amountOfMoney = money;
        player = new User(amountOfMoney);
        enemyPlayer = new Bot(amountOfMoney);
        currentGame = 1;
    }

    public Battlefield getField() {
        return field;
    }

    public User getPlayer(){
        return player;
    }

    public Bot getBot(){
        return enemyPlayer;
    }

    public void turnOnCheats(Scanner in){
        System.out.print("\nВключить читы? (+/-): ");
        if(in.next().equals("+")){
            for (Character symbol : player.getTeam().keySet()) {
                player.getTeam().get(symbol).buff(1000, 1000, 1000, 1000, 1000);
            }
        }
    }

    public boolean loadMap(Scanner in){
        try {
            File filesDir = new File("Saves\\SavedMaps");
            if (filesDir.list() == null || filesDir.list().length == 0){
                System.out.println("\nГотовых для загрузки карт нет.");
                return false;
            }
            System.out.println();
            for (String fName : filesDir.list()){
                System.out.println(" - " + fName);
            }
            System.out.print("\nЖелаете удалить сохранённую карту? (+/-): ");
            if(in.next().equals("+")){
                deleteMap(filesDir, in);
                return loadMap(in);
            }
            System.out.print("\nВведите имя файла для загрузки из списка выше: ");
            String filename = in.next();
            FileInputStream fileInputStream = new FileInputStream("Saves\\SavedMaps\\" + filename + ".ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            field = (Battlefield) objectInputStream.readObject();
            objectInputStream.close();
            return true;
        }
        catch (IOException e){System.out.print("\nА? Чё?"); return false;}
        catch (ClassNotFoundException e) {return false;}

    }

    public void createMap(Scanner in){
        System.out.print("\nВведите размер карты: ");
        field = new Battlefield(in.nextInt());
        MapEditor.edit(field, in);
    }

    public void randomMap(){
        field = new Battlefield(15);
        field.putRandomObstacles();
    }

    public void saveMap(Scanner in){
        try {
            System.out.print("\nВведи имя файла для сохранения: ");
            FileOutputStream outputStream = new FileOutputStream("Saves\\SavedMaps\\" + in.next() + ".ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(field);
            objectOutputStream.close();
        }
        catch (Exception e){System.out.print("\nА? Чё?");}
    }

    public void chooseMap(Scanner in){
        System.out.print("\nЖелаете ли вы зарандомить карту? (+/-): ");
        if (in.next().equals("+")) {
            randomMap();
        }
        else{
            System.out.println("\nВыберите действие, которое хотите совершить\n1 - Загрузить карту | 2 - Создать новую");
            System.out.print("Введите номер соответствующего действия: ");
            if (in.nextInt() == 1){
                if(!loadMap(in)){
                    chooseMap(in);
                    return;
                }
                System.out.print("\nЖелаете изменить загруженную карту? (+/-): ");
                if(in.next().equals("+")){
                    MapEditor.edit(field, in);
                    System.out.print("\nЖелаете сохранить карту для будущих приключений? (+/-): ");
                    if (in.next().equals("+")) {
                        saveMap(in);
                    }
                }
            }
            else{
                createMap(in);
                System.out.print("\nЖелаете сохранить карту для будущих приключений? (+/-): ");
                if (in.next().equals("+")) {
                    saveMap(in);
                }
            }
        }
    }

    public void deleteMap(File filesDir, Scanner in){
        System.out.print("\nВведите имя файла для удаления из списка выше: ");
        String filename = in.next();
        while (!Arrays.asList(filesDir.list()).contains(filename + ".ser")) {
            System.out.print("\nДанный файл отсутствует в списке. Введите имя заново: ");
            filename = in.next();
        }
        File deleteFile = new File("Saves\\SavedMaps\\" + filename + ".ser");
        deleteFile.delete();
    }

    public void makeTeam(Scanner in){
        System.out.print("\nЖелаете зарандомить вашу команду? (+/-): ");
        if(in.next().equals("+")){
            player.randomTeam();
        }
        else{
            player.fillTeam(in);
        }
        enemyPlayer.randomTeam();
        player.putUnits(field, true, in);
        enemyPlayer.putUnits(field);
    }

    public void makeSaveName(){
        File filesDir = new File("Saves\\GameSaves");
        if (filesDir.list() != null && filesDir.list().length > 0){
            int num = 1;
            while (Arrays.asList(filesDir.list()).contains(name + num + ".ser")){
                num ++;
            }
            name += num;
        }
    }

    public void saveGame(){
        try {
            FileOutputStream outputStream = new FileOutputStream("Saves\\GameSaves\\" + name + ".ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        }
        catch (IOException e){System.out.print("\nА? Чё?\n");}
    }

    public void visitTown(Scanner in){
        while (true) {
            System.out.printf("\nУ вас сейчас \u001B[33m%d\u001B[0m монет, \u001B[35m%d\u001B[0m дерева и \u001B[35m%d\u001B[0m камня\n\n", player.getMoney(), player.getBuildingResources()[0], player.getBuildingResources()[1]);
            System.out.println("\nВыберите действие, которое хотите совершить\n1 - Построить новое здание | 2 - Улучшить здание | 3 - Зайти на рынок | 4 - Посетить академию | 5 - Покинуть город");
            System.out.print("\nВведите номер соответствующего действия: ");
            int chosenOption = in.nextInt();
            if (chosenOption == 1) {
                player.getTown().createBuilding(player);
            }
            else if(chosenOption == 2){
                player.getTown().upgradeBuilding(player);
            }
            else if(chosenOption == 3){
                player.getTown().exchange(player);
            }
            else if (chosenOption == 4){
                player.getTown().startDevelopment(player);
            }
            else{
                break;
            }
            Display.makeGap();
        }
    }

    public void play(boolean newGame, Scanner in) {
        if(newGame) {
            chooseMap(in);

            System.out.print("\nЖелаете заглянуть в ваш город? (+/-): ");
            if (in.next().equals("+")) {
                visitTown(in);
            }

            makeTeam(in);
            turnOnCheats(in);

            // Создаёт имя файла, в который будет сохраняться прогресс конкретно этой игры
            if (name.equals("Game")) {
                makeSaveName();
            }

            if(currentGame == 1) {
                player.setGrainAmount(player.getTeam().size() * 3);
            }
            player.getTown().getMill().setMaxAmountOfGrainPerRound(player.getTeam().size() * 4);

            gameWeather.randomWeather(this);
            currentRound = 1;
        }

        Display.clear();
        System.out.printf("Игра №%d (Файл сохранения - %s)\n", currentGame, name);
        while (!player.isDefeated() && !enemyPlayer.isDefeated()) {
            saveGame();
            Display.makeGap();
            System.out.println("Раунд " + currentRound + "\n");
            System.out.printf("\nУ вас сейчас \u001B[33m%d\u001B[0m монет, \u001B[35m%d\u001B[0m дерева, \u001B[35m%d\u001B[0m камня и \u001B[35m%.2f\u001B[0m зерна\n", player.getMoney(), player.getBuildingResources()[0], player.getBuildingResources()[1], player.getGrainAmount());
            player.feedYourTeam();
            player.getTown().changeTaxes();
            gameWeather.checkWeather();
            player.printTeamState();
            enemyPlayer.printTeamState();
            Display.displayField(field);
            System.out.println();
            player.playRound(field, enemyPlayer.getTeam(), in);
            enemyPlayer.playRound(field, player.getTeam());
            currentRound++;
            gameWeather.decreaseRemain();
            gameWeather.randomWeather(this);
            player.getTown().getMarket().randomRates();
            System.out.printf("\nВо время передышки ваши воины съели \u001B[35m%d\u001B[0m зерна\n", player.getTeam().size() * 2);
            player.getTown().getWorkshop().getIncome(player);
            player.getTown().getMill().payDebt(player);
            player.getTown().getMill().changeLevelOfDiscontent();
            player.getTown().getMill().checkLevelOfDiscontent();
            player.getTown().getMill().tryToRunRiot(player);
        }
        saveGame();
        if (enemyPlayer.isDefeated()){
            System.out.println("\n\nВы выиграли.");
            player.earnMoney(10);
            player.earnResources(30,30);
            System.out.println("Вы получили \u001B[33m10\u001B[0m монет, \u001B[35m30\u001B[0m дерева и \u001B[35m30\u001B[0m камня");
        }
        else{
            if(player.getTeam().isEmpty()){
                System.out.println("\nОтряд оказался полностью разбит.");
            }
            else {
                System.out.println("\nЗапасы провизии полностью иссякли.");
            }
            System.out.println("\nВы проиграли.");
        }
        System.out.println();

        System.out.print("\nЖелаете отправиться в очередное сражение? (+/-): ");
        if (in.next().equals("+")){
            currentGame ++;
            enemyPlayer.setMoney(amountOfMoney);
            enemyPlayer.getTeam().clear();
            player.getTeam().clear();
            play(true, in);
        }
        else{
            System.out.print("\nИнтересное выдалось приключение. Удачи тебе, герой!");
        }

    }

}
