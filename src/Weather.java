import java.io.Serializable;

public class Weather implements Serializable {

    private boolean isRaining, isSnowing, isDrought, isFoggy;
    private int roundsRemain;

    Weather(){
        isRaining = false;
        isSnowing = false;
        isDrought = false;
        isFoggy = false;
        roundsRemain = 0;
    }

    public void checkWeather(){
        System.out.print("\u001B[34m");
        if (isRaining) {
            System.out.print("Небеса разверзлись, обрушив на головы путников настоящий водопад. Стрелки оказались беспомощны перед беснующейся природой.");
        } else if (isSnowing) {
            System.out.print("Леденящий ветер без конца усиливает свой натиск, заметая снегом все тропинки. Лошади начинают вязнуть в растущих на глазах сугробах.");
        } else if (isDrought) {
            System.out.print("Последние капли влаги были беспощадно высушены палящим солнцем. Утомлённые путники с трудом продожают передвигать ногами по раскалённой почве.");
        } else if (isFoggy) {
            System.out.print("Беспроглядная белая мгла опустилась на поле боя. Каждый взмах меча даётся воинам с огромным трудом, будто само пространство вокрут противится движению лезвия.");
        } else {
            System.out.print("Боги смилостивились над вами, разогнав непогоду.");
        }
        System.out.print("\u001B[0m\n");
    }

    public void randomWeather(Game game) {
        double weatherNum = 5 * Math.random();
        if (roundsRemain <= 0) {
            startClearWeather(game);
            if ((int) weatherNum == 0) {
                startRain(game);
            }
            else if ((int) weatherNum == 1) {
                startSnowing(game);
            }
            else if ((int) weatherNum == 2) {
                startDrought(game);
            }
            else if((int)weatherNum == 3){
                startFog(game);
            }
        }
        else if ((int) weatherNum == 4) {
            startClearWeather(game);
        }
    }

    public void decreaseRemain(){
        roundsRemain--;
    }

    public void startClearWeather(Game game){
        if (isRaining) endRain(game);
        else if (isSnowing) endSnowing(game);
        else if (isDrought) endDrought(game);
        else if (isFoggy) endFog(game);
    }

    public void startRain(Game game){
        Player player = game.getPlayer();
        for (Character symbol : player.getTeam().keySet()){
            if(player.getTeam().get(symbol) instanceof CanShoot){
                player.getTeam().get(symbol).buff(0,-3,0);
            }
        }
        player = game.getBot();
        for (Character symbol : player.getTeam().keySet()){
            if(player.getTeam().get(symbol) instanceof CanShoot){
                player.getTeam().get(symbol).buff(0,-3,0);
            }
        }
        roundsRemain = 2;
        isRaining = true;
    }

    public void startSnowing(Game game){
        Player player = game.getPlayer();
        for (Character symbol : player.getTeam().keySet()){
            if(player.getTeam().get(symbol) instanceof CanRide){
                player.getTeam().get(symbol).buff(0,0,-2);
            }
        }
        player = game.getBot();
        for (Character symbol : player.getTeam().keySet()){
            if(player.getTeam().get(symbol) instanceof CanRide){
                player.getTeam().get(symbol).buff(0,0,-2);
            }
        }
        roundsRemain = 2;
        isSnowing = true;
    }

    public void startDrought(Game game){
        Player player = game.getPlayer();
        for (Character symbol : player.getTeam().keySet()){
            if(player.getTeam().get(symbol) instanceof CanWalk){
                player.getTeam().get(symbol).buff(0,0,-1);
            }
        }
        player = game.getBot();
        for (Character symbol : player.getTeam().keySet()){
            if(player.getTeam().get(symbol) instanceof CanWalk){
                player.getTeam().get(symbol).buff(0,0,-1);
            }
        }
        roundsRemain = 2;
        isDrought = true;
    }

    public void startFog(Game game){
        Player player = game.getPlayer();
        for (Character symbol : player.getTeam().keySet()){
            if(player.getTeam().get(symbol) instanceof CanStab){
                player.getTeam().get(symbol).buff(-2,0,0);
            }
        }
        player = game.getBot();
        for (Character symbol : player.getTeam().keySet()){
            if(player.getTeam().get(symbol) instanceof CanStab){
                player.getTeam().get(symbol).buff(-2,0,0);
            }
        }
        roundsRemain = 2;
        isFoggy = true;
    }

    public void endRain(Game game){
        Player player = game.getPlayer();
        for (Character symbol : player.getTeam().keySet()){
            if(player.getTeam().get(symbol) instanceof CanShoot){
                player.getTeam().get(symbol).buff(0,3,0);
            }
        }
        player = game.getBot();
        for (Character symbol : player.getTeam().keySet()){
            if(player.getTeam().get(symbol) instanceof CanShoot){
                player.getTeam().get(symbol).buff(0,3,0);
            }
        }
        isRaining = false;
    }

    public void endSnowing(Game game){
        Player player = game.getPlayer();
        for (Character symbol : player.getTeam().keySet()){
            if(player.getTeam().get(symbol) instanceof CanRide){
                player.getTeam().get(symbol).buff(0,0,2);
            }
        }
        player = game.getBot();
        for (Character symbol : player.getTeam().keySet()){
            if(player.getTeam().get(symbol) instanceof CanRide){
                player.getTeam().get(symbol).buff(0,0,2);
            }
        }
        isSnowing = false;
    }

    public void endDrought(Game game){
        Player player = game.getPlayer();
        for (Character symbol : player.getTeam().keySet()){
            if(player.getTeam().get(symbol) instanceof CanWalk){
                player.getTeam().get(symbol).buff(0,0,1);
            }
        }
        player = game.getBot();
        for (Character symbol : player.getTeam().keySet()){
            if(player.getTeam().get(symbol) instanceof CanShoot){
                player.getTeam().get(symbol).buff(0,0,1);
            }
        }
        isDrought = false;
    }

    public void endFog(Game game){
        Player player = game.getPlayer();
        for (Character symbol : player.getTeam().keySet()){
            if(player.getTeam().get(symbol) instanceof CanStab){
                player.getTeam().get(symbol).buff(2,0,0);
            }
        }
        player = game.getBot();
        for (Character symbol : player.getTeam().keySet()){
            if(player.getTeam().get(symbol) instanceof CanStab){
                player.getTeam().get(symbol).buff(2,0,0);
            }
        }
        isFoggy = false;
    }
}
