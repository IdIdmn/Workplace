package BaumansGate.Game;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Scanner;

public class GameMenu {

    static public void loadGame(Game game){
        Scanner in = new Scanner(System.in);
        File filesDir = new File("Saves\\GameSaves");
        if (filesDir.list() == null || filesDir.list().length == 0) {
            System.out.println("\nГотовых для загрузки сохранений нет. Будет создана новая игра.");
            startNewGame(game);
        }
        else {
            System.out.println();
            for (String fName : filesDir.list()) {
                System.out.println(" - " + fName);
            }
            System.out.print("\nЖелаете удалить сохранение? (+/-): ");
            if(in.next().equals("+")){
                deleteGame(filesDir);
                loadGame(game);
            }
            else {
                try {
                    System.out.print("\nВведите имя файла для загрузки из списка выше: ");
                    String filename = in.next();
                    while (!Arrays.asList(filesDir.list()).contains(filename + ".ser")) {
                        System.out.print("\nДанный файл отсутствует в списке. Введите имя заново: ");
                        filename = in.next();
                    }
                    FileInputStream fileInputStream = new FileInputStream("Saves\\GameSaves\\" + filename + ".ser");
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    game = (Game) objectInputStream.readObject();
                    objectInputStream.close();
                    game.play(false);
                }
                catch (IOException e) {
                    System.out.print("\nТы втираешь мне какую-то дичь.");
                }
                catch (ClassNotFoundException e) {}
            }
        }
    }

    static public void startNewGame(Game game){
        game = new Game();
        game.play(true);
    }

    static public void startAdventure(Game game){
        Scanner in = new Scanner(System.in);
        System.out.println("\n\nЧто вы желаете сделать?\n1 - Начать новую игру | 2 - Продолжить игру");
        System.out.print("Введите номер соответствующего действия: ");
        if (in.nextInt() == 1) {
            startNewGame(game);
        }
        else{
            loadGame(game);
        }
    }

    static public void deleteGame(File filesDir){
        Scanner in = new Scanner(System.in);
        System.out.print("\nВведите имя файла для удаления из списка выше: ");
        String filename = in.next();
        while (!Arrays.asList(filesDir.list()).contains(filename + ".ser")) {
            System.out.print("\nДанный файл отсутствует в списке. Введите имя заново: ");
            filename = in.next();
        }
        File deleteFile = new File("Saves\\GameSaves\\" + filename + ".ser");
        deleteFile.delete();
    }

}
