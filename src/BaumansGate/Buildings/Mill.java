package BaumansGate.Buildings;

import BaumansGate.Output.Display;
import BaumansGate.Players.User;

import java.io.Serializable;

public class Mill implements Serializable {

    private int taxes, levelOfDiscontent;
    private double maxAmountOfGrainPerRound;

    public Mill(){
        levelOfDiscontent = 0;
        taxes = 0;
    }

    public int getTaxes() {
        return taxes;
    }

    public void setMaxAmountOfGrainPerRound(double maxAmountOfGrainPerRound) {
        this.maxAmountOfGrainPerRound = maxAmountOfGrainPerRound;
    }

    public void changeLevelOfDiscontent(){
        if (taxes < 15){
            levelOfDiscontent = Math.max(levelOfDiscontent - 10, 0);
        }
        else if (taxes <= 25) {
            levelOfDiscontent += 5;
        }
        else if (taxes <= 50) {
            levelOfDiscontent += 10;
        }
        else if (taxes <= 70) {
            levelOfDiscontent += 15;
        }
        else if (taxes <= 90) {
            levelOfDiscontent += 20;
        }
        else {
            levelOfDiscontent += 35;
        }
    }

    public void checkLevelOfDiscontent(){
        if (levelOfDiscontent < 10){
            System.out.printf("\nКрестьяне пока довольны своим нынешним положением. Уровень недовольства - %d/100.\n", levelOfDiscontent);
        }
        else if (levelOfDiscontent <= 20) {
            System.out.printf("\nКрестьяне смиренно приняли новость об изменении размера налога. Уровень недовольства - %d/100.\n", levelOfDiscontent);
        }
        else if (levelOfDiscontent <= 35) {
            System.out.printf("\nПо деревне поползли разговоры новых налогах. Уровень недовольства - %d/100.\n", levelOfDiscontent);
        }
        else if (levelOfDiscontent <= 60) {
            System.out.printf("\nГуляя по деревне вы всюду ловите неодобрительные взгляды крестьян. Уровень недовольства - %d/100.\n", levelOfDiscontent);
        }
        else if (levelOfDiscontent <= 75) {
            System.out.printf("\nВ очередной раз заглянув в деревню, вы ощущаете сильную непрязнь крестьян к вашей персоне. Кто-то лишь проклинает в сердцах, а кто-то крепче хвататеся за вилы, завидев вас в поле. Уровень недовольства - %d/100.\n", levelOfDiscontent);
        }
        else {
            System.out.printf("\nВы играете с огнём, милорд. Ещё немного и, кажется, крестьяне возьмут власть в свои руки. Уровень недовольства - %d/100.\n", Math.min(levelOfDiscontent, 100));
        }
    }

    public void setTaxes(int newTax){
        taxes = newTax;
    }

    public void payDebt(User user){
        int earnedMoney = (int)Math.log(maxAmountOfGrainPerRound * taxes) * 2 + (int)maxAmountOfGrainPerRound * taxes * 4 / 100 ;
        double earnedGrain = maxAmountOfGrainPerRound * (100 - taxes) / 100;
        user.setGrainAmount(user.getGrainAmount() + earnedGrain);
        user.earnMoney(earnedMoney);
        System.out.printf("\nЗа этот раунд крестьяне выплатили \u001B[35m%.2f\u001B[0m зерна и \u001B[33m%d\u001B[0m золотых монет.\n", earnedGrain, earnedMoney);
    }

    public void tryToRunRiot(User user){
        if (levelOfDiscontent >= 100) {
            user.setGrainAmount(0);
            System.out.print("\nКрестьяне устроили бунт и сожгли все ваши запасы зерна.");
            Display.makeGap();
        }
    }

}
