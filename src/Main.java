import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;

public class Main {

    static public Game BaumansGate;

    static public void loadGame() {
        Scanner in = new Scanner(System.in);
        File filesDir = new File("Saves\\GameSaves");
        if (filesDir.list() == null || filesDir.list().length == 0) {
            System.out.println("\nГотовых для загрузки сохранений нет. Будет создана новая игра.");
            startNewGame();
        }
        else {
            for (String fName : filesDir.list()) {
                System.out.println(" - " + fName);
            }
            System.out.print("\nЖелаете удалить сохранение? (+/-): ");
            if(in.next().equals("+")){
                deleteGame(filesDir);
                loadGame();
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
                    BaumansGate = (Game) objectInputStream.readObject();
                    objectInputStream.close();
                    BaumansGate.continueToPlay();
                }
                catch (IOException e) {
                    System.out.print("\nТы втираешь мне какую-то дичь.");
                }
                catch (ClassNotFoundException e) {}
            }
        }
    }

    static public void startNewGame(){
        BaumansGate = new Game();
        BaumansGate.play();
    }

    static public void chooseGame(){
        Scanner in = new Scanner(System.in);
        System.out.println("\n\nЧто вы желаете сделать?\n1 - Начать новую игру | 2 - Продолжить игру");
        System.out.print("Введите номер соответствующего действия: ");
        if (in.nextInt() == 1) {
            startNewGame();
        }
        else{
            loadGame();
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

    public static void main(String[] args) {
        chooseGame();
    }
}