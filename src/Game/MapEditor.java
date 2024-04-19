package Game;

import Field.Obstacle;
import Field.Battlefield;
import Output.Display;

import java.util.Scanner;

public class MapEditor {

    static public void edit(Battlefield field){
        Scanner in = new Scanner(System.in);
        int chosenOption;
        while(true) {
            Display.displayField(field);
            System.out.println("\n\nЧто вы желаете сделать?\n1 - Создать новое препятсвие | 2 - Добавить препятствие на поле | 3 - Не мучиться и всё-таки зарандомить | 4 - Выйти из редактора");
            System.out.print("Введите номер соответствующего действия: ");
            chosenOption = in.nextInt();
            if (chosenOption == 1) {
                System.out.print("\nВведите название и символ нового препятсвия: ");
                String newObstacleName = in.next();
                char newObstacleSymbol = in.next().charAt(0);
                while (Character.isLetter(newObstacleSymbol) || Character.isDigit(newObstacleSymbol) || field.getObstacles().containsKey(newObstacleSymbol)){
                    System.out.print("\nСимвол не должен являться числом или буквой, а также совпадать с обозначением уже существующих препятсвий. Введите заново: ");
                    newObstacleSymbol = in.next().charAt(0);
                }
                System.out.print("\nВведите штрафы нового препятсвия для пеших воинов, лучников и наездников соответсвенно: ");
                field.getObstacles().put(newObstacleSymbol, new Obstacle(newObstacleName, in.nextDouble(), in.nextDouble(), in.nextDouble()));
            }
            else if (chosenOption == 2) {
                System.out.println("\nДоступны следующие препятсвия");
                for (Character obstacleSymbol : field.getObstacles().keySet()) {
                    System.out.println(obstacleSymbol + " - " + field.getObstacles().get(obstacleSymbol).toString());
                }
                System.out.print("Введите символ препятствия, которое хотите разместить: ");
                char ObstacleSymbol = in.next().charAt(0);
                while (Character.isLetter(ObstacleSymbol) || Character.isDigit(ObstacleSymbol) || !field.getObstacles().containsKey(ObstacleSymbol)){
                    System.out.print("\nВведённый символ должен соответствовать обозначению одного из существующих препятсвий. Введите заново: ");
                    ObstacleSymbol = in.next().charAt(0);
                }
                System.out.print("Введите позицию: ");
                int row = in.nextInt(), column = in.nextInt();
                while(row >= field.getLength() - 1 || row <= 0 || column >= field.getLength() || column < 0){
                    System.out.print("\nНовое препятсвие должно лежать в пределах поля, а также располагаться не на самой верхней или нижней строке. Введите заново: ");
                    row = in.nextInt();
                    column = in.nextInt();
                }
                field.fill(ObstacleSymbol, new int[]{row, column});
            }
            else if (chosenOption == 3) {
                field.putRandomObstacles();
                System.out.println("\nИтоговый вариант карты:");
                Display.displayField(field);
                break;
            }
            else{
                break;
            }
            System.out.println("\n\n");
        }
    }

}