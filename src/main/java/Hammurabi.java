package Hammurabi;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Hammurabi {
        Random rand = new Random();  // this is an instance variable
        Scanner input = new Scanner(System.in);
        static Logger logger = Logger.getLogger("Hammurabi.log");

        boolean uprising = false;
        int year = 1;
        int harvested = 0;

        public static void main(String[] args) throws IOException { // required in every Java program
                logger.setLevel(Level.OFF);
                new Hammurabi().playGame();
        }

        void playGame() {
                System.out.println("Congratulations, you are the new ruler. Your office term is 10 years. Can you complete the term successfully.....");
                int priceOfLand = 19;
                int numOfBushels = 2800;
                int acresOwned = 1000;
                int numOfPeople = 100;
                //int year = 1;

                //Playing or not playing
                boolean start = true;
                boolean end = false;

                //People
                int numOfDeaths = 0;
                int deaths = 0;
                int percentageDied = 0;
                int immigrants = 5;

                int output, numOfFood;

                while (year <= 10 && !end) {

                        output = askHowManyAcresToBuy(priceOfLand, numOfBushels);
                        if (output > 0) {
                                numOfBushels = numOfBushels - (output * priceOfLand);
                                acresOwned += output;
                                logger.info("Current bushels: " + numOfBushels + "Acres of land" + acresOwned);
                        } else if (output <= 0) {
                                output = askHowManyAcresToSell(acresOwned);
                                acresOwned = acresOwned - output;
                                numOfBushels = numOfBushels + (output * priceOfLand);
                        }

                        numOfFood = askHowMuchGrainToFeedPeople(numOfBushels);
                        numOfBushels = numOfBushels - numOfFood;
                        logger.info("Current Bushels: " + numOfBushels + " Acres of Land: " + acresOwned);


                        int numOfBushelsToPlant = askHowManyAcresToPlant(acresOwned, numOfPeople, numOfBushels);

                        int killedByPlague = plagueDeaths(numOfPeople);
                        numOfPeople = numOfPeople - killedByPlague;

                        logger.info("Total number of people" + numOfPeople);
                        int starvationDeaths = starvationDeaths(output, numOfFood);
                        deaths += starvationDeaths;
                        logger.info("Starvation death count: " + starvationDeaths);
                        numOfPeople = numOfPeople - starvationDeaths;

                        uprising = uprising(numOfPeople, starvationDeaths);
                        if (uprising) {
                                break;
                        }
                        if (starvationDeaths == 0) {
                                immigrants = immigrants(numOfPeople, acresOwned, numOfBushels);
                                logger.info("Immigrants" + immigrants);
                                numOfPeople = numOfPeople - immigrants;
                        }
                        //harvested = harvest(acresOwned);

                        priceOfLand = newCostOfLand();

                        year++;
                        numOfBushels = numOfBushels + harvested;
                        //gameSummary(year, starvationDeaths, immigrants, numOfPeople, numOfBushels, acresOwned, priceOfLand);

                }
                gameEnd();
        }

        public int askHowManyAcresToBuy(int price, int bushels) {
                int output;
                System.out.println("Would you like to buy more acres? How many: ");
                output = input.nextInt();
                if (output * price > bushels) {
                        System.out.println("You do not have enough to buy that many, try again. You only have " + bushels + " bushels of grain.");
                }
                return output;
        }

        public int askHowManyAcresToSell(int acresOwned) {
                int output;
                System.out.println("Would you like to sell any acres? How many: ");
                output = input.nextInt();
                if (output > acresOwned) {
                        System.out.println("You do not have enough to sell that many, try again. You only have " + acresOwned + " acres of land.");
                }
                return output;
        }

        public int askHowMuchGrainToFeedPeople(int bushels) {
                int output;
                System.out.println("Would you like to feed your people? How many bushels: ");
                output = input.nextInt();
                if (output > bushels) {
                        System.out.println("You do not have enough to feed that many, try again. You only have " + bushels + " bushels of grain.");
                }
                return output;
        }

        public int askHowManyAcresToPlant(int acresOwned, int population, int bushels) {
                int output;
                System.out.println("Would you like to plant acres? How many: ");
                output = input.nextInt();
                if (output > acresOwned) {
                        System.out.println("You do not have enough, try again. You only have " + acresOwned + " acres of land.");
                }
                if (output / 2 > bushels) {
                        System.out.println("You do not have enough, try again. You only have " + bushels + " bushels of grain.");
                }
                if (output > population * 10) {
                        System.out.println("You will only have " + population + " to assist with the fields");
                }
                return output;
        }

                public int plagueDeaths ( int population){
                        return population;
                }

                public int starvationDeaths ( int population, int bushelsFedToPeople) {
                        int starvation = population - (bushelsFedToPeople / 20);
                        if (!(bushelsFedToPeople >= (population * 20))) {
                                return starvation;
                        }
                        return 0;
                }

                public boolean uprising ( int population, int howManyPeopleStarved){
                        if (howManyPeopleStarved > (population * 0.45)){
                                return true;
                        }
                        return false;
                }

                public int immigrants ( int population, int acresOwned, int grainInStorage){
                        int numOfImmigrants = (20 * acresOwned + grainInStorage)/(100 * population) + 1;
                        return numOfImmigrants;
                }

                public int harvest ( int acres, int bushelsUsedAsSeed){
                        int harvested = (acres * bushelsUsedAsSeed);
                        return harvested;
                }

                public int grainEatenByRats ( int bushels){
                int eaten = rand.nextInt(100);
                if (eaten < 40){
                        return (int) (bushels * (rand.nextDouble()/5 + 0.1));
                }
                        return 0;
                }
                public int newCostOfLand () {
                        return (rand.nextInt(7) + 17);
                }

                public void gameEnd(){
                StringBuilder sb = new StringBuilder();
                if (uprising){
                        sb.append("Hammurabi, we must leave the city, the people rebel! You have been thrown out of office.\n");
                } else if (year == 11){
                        sb.append("You win");
                }
                        System.out.println(sb.toString());
        }

        public void gameSummary(int immigrants, int starvationDeaths, int year, int harvest, int acresOwned, int priceOfLand, int grainEatenByRats, int numOfPeople, int numOfBushels, int bushelsOfGrian){
                StringBuilder sb = new StringBuilder();
                sb.append("O great Hammurabi! \n");
                sb.append("You are in year " + year + " of your ten year rule\n");
                sb.append("In the previous year " + starvationDeaths + " people starved to death.\n");
                sb.append("In the previous year " + immigrants + " people entered the kingdom.\n");
                sb.append("The population is now " + numOfPeople + ".\n");
                sb.append("We harvested " + harvest + " bushels at " + (harvest/numOfBushels) + " bushels per acre.\n");
                sb.append("Rats destroyed " + grainEatenByRats + " bushels, leaving " + bushelsOfGrian + " bushels in storage.\n");
                sb.append("The city owns "+ acresOwned + " acres of land.\n");
                sb.append("Land is currently worth "+ priceOfLand + " bushels per acre.\n");


                System.out.println(sb.toString());
        }



}









