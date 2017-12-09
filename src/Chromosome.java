import kotlin.reflect.jvm.internal.impl.serialization.jvm.JvmProtoBuf;

import java.util.Random;

public class Chromosome {

    private double fitness;
    private String genome;

    public double getFitnes(){return fitness;}
    public void setFitnes(double fitness){this.fitness = fitness;}
    public String getGenome() {
        return genome;
    }
    public void setGenome(String genome) {
        this.genome = genome;
    }

    public char getGenAt(int pos){
        return genome.charAt(pos);
    }


    public static void fillRandomGenes(Chromosome chromosome){

        StringBuffer result = new StringBuffer( );

        for (int i = 0; i < Knapsack.gensCount; i++ ){
            result.setCharAt(i , getRandomInt(0 , 1)==0 ? '0' : '1');
        }

        chromosome.setGenome(result.toString());
        return ;
    }

    public double calculateFitness(){
        if (getFilledKnapsack().getPrice() > Knapsack.MaxWeight) return 0;
        return getFilledKnapsack().getPrice();
    }

    public Item getFilledKnapsack(){

        double weightSum = 0;
        double priceSum = 0;


        for ( int i=0 ; i<Knapsack.gensCount ; i++ ){
            if ( this.genome.charAt( i ) =='1' ){
                weightSum+=Knapsack.Items[i].getWeight();
                priceSum+=Knapsack.Items[i].getPrice();
            }

        }

        return new Item(weightSum, priceSum);

    }

    public static int getRandomInt( int min, int max ){
        Random randomGenerator;
        randomGenerator = new Random();
        return  randomGenerator.nextInt( max+1 ) + min ;
    }

    public Chromosome Clone(){
        Chromosome result = new Chromosome();
        result.setFitnes(this.getFitnes());
        result.setGenome(this.getGenome());
        return result;
    }

    public Chromosome mutation(){
        Chromosome mutant = this.Clone();
        StringBuffer newGenome = new StringBuffer(mutant.getGenome());

        for (int i = 0 ; i < Knapsack.gensCount; i++){
            if (getRandomInt(0 , 100) > Knapsack.mutationChanse) {
                char newGen = mutant.genome.charAt(i) == '1' ? '0' : '1';
                newGenome.setCharAt(i, newGen);
            }
        }

        mutant.setGenome(newGenome.toString());
        return mutant;
    }

    public Chromosome child(Chromosome chromosome){
        Chromosome first = this;
        Chromosome second = chromosome;

        StringBuffer newGenome = new StringBuffer();
        int aliveChild = getRandomInt(0 ,1);
        int mutLine = getRandomInt( 1 , Knapsack.gensCount - 2);

        for (int i = 0 ; i < Knapsack.gensCount; i++ ) {
            if (aliveChild == 0){
                if (i < mutLine){
                    newGenome.setCharAt(i , first.getGenAt(i));
                }
                else {
                    newGenome.setCharAt(i , second.getGenAt(i));
                }
            }
            else {
                if (i < mutLine){
                    newGenome.setCharAt(i , second.getGenAt(i));
                }
                else {
                    newGenome.setCharAt(i , first.getGenAt(i));
                }
            }
        }

        Chromosome result = new Chromosome();
        result.setGenome(newGenome.toString());
        return result;
    }

    public static void main( String[] args ) {

        Chromosome chromo1 = new Chromosome();
        Chromosome chromo2 = new Chromosome();
        Chromosome chromo3 = new Chromosome();

        fillRandomGenes(chromo1);
        System.out.println("Hop " + chromo1);
        fillRandomGenes(chromo1);
        System.out.println("Hop " + chromo1);
        fillRandomGenes(chromo1);
        System.out.println("first is " + chromo1);

        fillRandomGenes(chromo2);
        System.out.println("second is " + chromo1);

        System.out.println(chromo1.calculateFitness());
        System.out.println(chromo2.calculateFitness());

        chromo3 = chromo1.Clone();
        System.out.println("Clone first " + chromo3);

        System.out.println(chromo1.child(chromo2));
        System.out.println(chromo2.child(chromo1));
    }
}
