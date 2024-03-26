import java.util.Scanner;

public class Game {

    private Battlefield Field = new Battlefield();
    private User Player = new User();
    private Bot EnemyPlayer = new Bot();
    private int amountOfMoney = 50;

    public User getPlayer(){
        return Player;
    }

    public Bot getBot(){
        return EnemyPlayer;
    }

    public void play() {

        Scanner in = new Scanner(System.in);
        System.out.print("\nЖелаете зарандомить вашу команду? (+/-): ");
        Player.fillTeam(in.next().equals("+"), amountOfMoney);
        EnemyPlayer.fillTeam(amountOfMoney);

        //turnOnCheats();

        Field.putObstacles();
        Player.putUnits(Field, true);
        EnemyPlayer.putUnits(Field);

        Weather gameWeather = new Weather();
        int roundCounter = 1;
        System.out.println();
        while (!Player.isDefeated() && !EnemyPlayer.isDefeated()) {
            Display.makeGap();
            System.out.println("Раунд " + roundCounter + "\n");
            gameWeather.randomWeather(this);
            Player.printTeamState();
            EnemyPlayer.printTeamState();
            Display.displayField(Field);
            System.out.println();
            Player.playRound(Field, EnemyPlayer.getTeam());
            EnemyPlayer.playRound(Field, Player.getTeam());
            roundCounter++;
            gameWeather.decreaseRemain();
        }
        System.out.println((EnemyPlayer.isDefeated()) ? "Вы выиграли" : "Вы проиграли");
        System.out.println();
    }

    public void turnOnCheats(){
        for (Character symbol : Player.getTeam().keySet()){
            Player.getTeam().get(symbol).buff(1000, 1000, 1000);
        }
    }

}
