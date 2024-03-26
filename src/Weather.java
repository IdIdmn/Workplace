public class Weather {

    private static boolean isRaining, isSnowing, isDrought, isFoggy;
    private static int RoundsRemain;

    Weather(){
        isRaining = false;
        isSnowing = false;
        isDrought = false;
        isFoggy = false;
        RoundsRemain = 0;
    }

    public void randomWeather(Game game) {
        System.out.print("\u001B[34m");
        double weatherNum = 5 * Math.random();
        if (RoundsRemain <= 0) {
            startClearWeather(game);
            if ((int) weatherNum == 0) {
                startRain(game);
                System.out.print("Начался ливень.");
            }
            else if ((int) weatherNum == 1) {
                startSnowing(game);
                System.out.print("Начался снегопад.");
            }
            else if ((int) weatherNum == 2) {
                startDrought(game);
                System.out.print("Началась засуха.");
            }
            else if((int)weatherNum == 3){
                startFog(game);
                System.out.print("На поле боя опустился густой туман.");
            }
            else {
                if(RoundsRemain == 0) System.out.print("Настала ясная погода.");
            }
        }
        else if ((int) weatherNum == 3) {
            startClearWeather(game);
            System.out.print("Настала ясная погода.");
        }
        System.out.print("\u001B[0m\n");
    }

    public void decreaseRemain(){
        RoundsRemain--;
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
        RoundsRemain = 2;
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
        RoundsRemain = 2;
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
        RoundsRemain = 2;
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
        RoundsRemain = 2;
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
