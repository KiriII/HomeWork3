import java.util.Random;

public class Knapsack {

    public static final double MaxWeight = 100;

    public static Item[] Items = new Item[]{
            new Item (9999 , 99999),
            new Item (101 , 9999),
            new Item (99 , 100),
            new Item (99 , 1),
            new Item (15 , 50),
            new Item (20 , 15),
            new Item (50 , 150),
            new Item (10 , 15 ),
            new Item (5 , 7),
            new Item (3 , 5),
            new Item (2 , 3),
            new Item ( 1 , 1),
            new Item (1 , 15),

    };

    public static final int gensCount = Items.length;
    public static final int populationCount = 100;
    public static final int tournamenterCount = 4;
    public static final int mutationChanse = 80;
    public static final int maxGenerations = 200;

    public Chromosome[] population = new Chromosome[gensCount];

    public void createInitPopulation(){
        for (int i = 0; i < populationCount; i++){
            population[i].fillRandomGenes(population[i]);
            population[i].setFitnes(population[i].calculateFitness());
        }
    }

    public void fillFitness(){
        for (int i = 0; i < populationCount; i++){
            population[i].setFitnes(population[i].calculateFitness());
        }
    }

    // турнирный отбор
    public int findIndividualChromosome(){

        int number =0;

        for ( int i = 0; i < tournamenterCount; i++ ){
            int currentNumber = Chromosome.getRandomInt(0 , populationCount - 1);
            if (population[currentNumber].getFitnes() > population[number].getFitnes()) number = currentNumber;
        }

        return number;
    }

    public int[][] getTournamentPairs(){

        int[][] pairs = new int[populationCount][2];

        for (int i = 0; i < populationCount; i++){

            int first = findIndividualChromosome();
            int second = 0;
            do{
                second = findIndividualChromosome();
            }
            while (second == first);

            pairs[i][0] = first;
            pairs[i][1] = second;

        }

        return pairs;
    }

    public int getMaxFitnessChromosome(){
        int number =0;

        for ( int i = 0; i < tournamenterCount; i++ ){
            if (population[i].getFitnes() > population[number].getFitnes()) number = i;
        }

        return number;
    }

    public Chromosome[] nextGeneration(int[][] pairs){
        Chromosome[] newGeneration = new Chromosome[gensCount];

        newGeneration[0] = population[getMaxFitnessChromosome()];

        for (int i = 0; i < populationCount; i++){
            newGeneration[i] = population[pairs[i][0]].child(population[pairs[i][1]]);
            newGeneration[i].mutation();
        }

        return newGeneration;
    }

    public void setPopulation(Chromosome[] population) {
        this.population = population;
    }

    public static void main( String[] args ) {

        Knapsack max = new Knapsack();
        max.createInitPopulation();

        int generationCounter = 0;
        while (generationCounter <= maxGenerations){
            generationCounter++;
            max.fillFitness();
            System.out.println("max code is " + max.population[max.getMaxFitnessChromosome()] + " with price " + max.population[max.getMaxFitnessChromosome()].getFilledKnapsack().getPrice()
                    + " in " + generationCounter + " generation");
            int[][] pairs = max.getTournamentPairs();
            Chromosome nextGeneration[]= new Chromosome[populationCount];
            nextGeneration = max.nextGeneration(  pairs );
            max.setPopulation(nextGeneration);
        }

    }
}
