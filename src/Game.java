import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Game implements Serializable {

    private Battlefield field;
    private User player = new User();
    private Bot enemyPlayer = new Bot();
    private int amountOfMoney = 23;
    private int currentRound;
    private String name = "Game";
    private Weather gameWeather = new Weather();

    public User getPlayer(){
        return player;
    }

    public Bot getBot(){
        return enemyPlayer;
    }

    public void turnOnCheats(){
        for (Character symbol : player.getTeam().keySet()){
            player.getTeam().get(symbol).buff(1000, 1000, 1000);
        }
    }

    public boolean loadMap(){
        Scanner in = new Scanner(System.in);
        try {
            File filesDir = new File("Saves\\SavedMaps");
            if (filesDir.list() == null || filesDir.list().length == 0){
                System.out.println("\nГотовых для загрузки карт нет.");
                return false;
            }
            for (String fName : filesDir.list()){
                System.out.println(" - " + fName);
            }
            System.out.print("\nЖелаете удалить сохранённую карту? (+/-): ");
            if(in.next().equals("+")){
                deleteMap(filesDir);
                return loadMap();
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

    public void createMap(){
        Scanner in = new Scanner(System.in);
        System.out.print("\nВведите размер карты: ");
        field = new Battlefield(in.nextInt());
        MapEditor.edit(field);
    }

    public void randomMap(){
        field = new Battlefield(15);
        field.putRandomObstacles();
    }

    public void saveMap(){
        Scanner in = new Scanner(System.in);
        try {
            System.out.print("\nВведи имя файла для сохранения: ");
            FileOutputStream outputStream = new FileOutputStream("Saves\\SavedMaps\\" + in.next() + ".ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(field);
            objectOutputStream.close();
        }
        catch (Exception e){System.out.print("\nА? Чё?");}
    }

    public void chooseMap(){
        Scanner in = new Scanner(System.in);
        System.out.print("\nЖелаете ли вы зарандомить карту? (+/-): ");
        if (in.next().equals("+")) {
            randomMap();
        }
        else{
            System.out.println("\nВыберите действие, которое хотите совершить\n1 - Загрузить карту | 2 - Создать новую");
            System.out.print("Введите номер соответствующего действия: ");
            if (in.nextInt() == 1){
                if(!loadMap()){
                    chooseMap();
                    return;
                }
            }
            else{
                createMap();
                System.out.print("\nЖелаете сохранить карту для будущих приключений? (+/-): ");
                if (in.next().equals("+")) {
                    saveMap();
                }
            }
        }
    }

    static public void deleteMap(File filesDir){
        Scanner in = new Scanner(System.in);
        System.out.print("\nВведите имя файла для удаления из списка выше: ");
        String filename = in.next();
        while (!Arrays.asList(filesDir.list()).contains(filename + ".ser")) {
            System.out.print("\nДанный файл отсутствует в списке. Введите имя заново: ");
            filename = in.next();
        }
        File deleteFile = new File("Saves\\SavedMaps\\" + filename + ".ser");
        deleteFile.delete();
    }

    public void chooseTeam(){
        Scanner in = new Scanner(System.in);
        System.out.print("\nЖелаете зарандомить вашу команду? (+/-): ");
        player.fillTeam(in.next().equals("+"), amountOfMoney);
        enemyPlayer.fillTeam(amountOfMoney);
        player.putUnits(field, true);
        enemyPlayer.putUnits(field);
    }

    public void makeSaveName(){
        File filesDir = new File("Saves\\GameSaves");
        int num = 2;
        if (filesDir.list() == null || filesDir.list().length == 0){
            name += 1;
        }
        else{
            name += num;
            while (Arrays.asList(filesDir.list()).contains(name + num + ".ser")){
                num ++;
            }

        }
    }

    public void saveGame(){
        try {
            FileOutputStream outputStream = new FileOutputStream("Saves\\GameSaves\\" + name + ".ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        }
        catch (Exception e){System.out.print("\nА? Чё?");}
    }
    
    public void play() {

        chooseMap();
        chooseTeam();

        Display.clear();

        //turnOnCheats();

        // Создаёт имя файла, в который будет сохраняться прогресс конкретно этой игры
        makeSaveName();

        gameWeather.randomWeather(this);
        currentRound = 1;
        System.out.println();
        while (!player.isDefeated() && !enemyPlayer.isDefeated()) {
            saveGame();
            Display.makeGap();
            System.out.println("Раунд " + currentRound + "\n");
            gameWeather.checkWeather();
            player.printTeamState();
            enemyPlayer.printTeamState();
            Display.displayField(field);
            System.out.println();
            player.playRound(field, enemyPlayer.getTeam());
            enemyPlayer.playRound(field, player.getTeam());
            currentRound++;
            gameWeather.decreaseRemain();
            gameWeather.randomWeather(this);
        }
        System.out.println((enemyPlayer.isDefeated()) ? "Вы выиграли" : "Вы проиграли");
        System.out.println();
    }

    public void continueToPlay(){
        System.out.println();
        while (!player.isDefeated() && !enemyPlayer.isDefeated()) {
            Display.makeGap();
            System.out.println("Раунд " + currentRound + "\n");
            gameWeather.checkWeather();
            player.printTeamState();
            enemyPlayer.printTeamState();
            Display.displayField(field);
            System.out.println();
            player.playRound(field, enemyPlayer.getTeam());
            enemyPlayer.playRound(field, player.getTeam());
            currentRound++;
            gameWeather.decreaseRemain();
            gameWeather.randomWeather(this);
            saveGame();
        }
        System.out.println((enemyPlayer.isDefeated()) ? "Вы выиграли" : "Вы проиграли");
        System.out.println();
    }

}
