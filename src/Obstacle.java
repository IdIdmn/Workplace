public class Obstacle {

    private String name;
    private double walkerFine;
    private double archerFine;
    private double horsemanFine;

    public Obstacle(String name, double walkerFine, double archerFine, double horsemanFine) {
        this.name = name;
        this.walkerFine = walkerFine;
        this.archerFine = archerFine;
        this.horsemanFine = horsemanFine;
    }

    public String getName() {
        return name;
    }

    public double getWalkerFine() {
        return walkerFine;
    }

    public double getArcherFine() {
        return archerFine;
    }

    public double getHorsemanFine() {
        return horsemanFine;
    }

    public void setWalkerFine(double walkerFine) {
        this.walkerFine = walkerFine;
    }

    public void setArcherFine(double archerFine) {
        this.archerFine = archerFine;
    }

    public void setHorsemanFine(double horsemanFine) {
        this.horsemanFine = horsemanFine;
    }

    @Override
    public String toString(){
        return String.format("%s | Штрафы: Пеший воин - %.1f, Лучник - %.1f, Наездник - %.1f", name, walkerFine, archerFine, horsemanFine);
    }
}
