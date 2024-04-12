import java.util.Scanner;

public class Game {

    private Battlefield field;
    private User player = new User();
    private Bot enemyPlayer = new Bot();
    private int amountOfMoney = 23;

    public User getPlayer(){
        return player;
    }

    public Bot getBot(){
        return enemyPlayer;
    }

    public void play() {

        Scanner in = new Scanner(System.in);
        System.out.print("\nЖелаете ли вы зарандомить карту? (+/-): ");
        if (in.next().equals("+")) {
            field = new Battlefield(15);
            field.putRandomObstacles();
        }
        else{
            System.out.println("\nВыберите действие, которое хотите совершить\n1 - Загрузить карту | 2 - Создать новую");
            System.out.print("Введите номер соответствующего действия: ");
            if (in.nextInt() == 1){
                System.out.print("\nВведите имя файла: ");
            }
            else{
                System.out.print("\nВведите размер карты: ");
                field = new Battlefield(in.nextInt());
                MapEditor.edit(field);
            }
        }

        System.out.print("\nЖелаете зарандомить вашу команду? (+/-): ");
        player.fillTeam(in.next().equals("+"), amountOfMoney);
        enemyPlayer.fillTeam(amountOfMoney);

        //turnOnCheats();

        player.putUnits(field, true);
        enemyPlayer.putUnits(field);
        Display.clear();

        Weather gameWeather = new Weather();
        int roundCounter = 1;
        System.out.println();
        while (!player.isDefeated() && !enemyPlayer.isDefeated()) {
            Display.makeGap();
            System.out.println("Раунд " + roundCounter + "\n");
            gameWeather.randomWeather(this);
            player.printTeamState();
            enemyPlayer.printTeamState();
            Display.displayField(field);
            System.out.println();
            player.playRound(field, enemyPlayer.getTeam());
            enemyPlayer.playRound(field, player.getTeam());
            roundCounter++;
            gameWeather.decreaseRemain();
        }
        System.out.println((enemyPlayer.isDefeated()) ? "Вы выиграли" : "Вы проиграли");
        System.out.println();
    }

    public void turnOnCheats(){
        for (Character symbol : player.getTeam().keySet()){
            player.getTeam().get(symbol).buff(1000, 1000, 1000);
        }
    }

}
