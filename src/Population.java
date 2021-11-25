// A class to describe a population of virtual organisms
// In this case, each organism is just an instance of a DNA object

import java.util.ArrayList;
import java.util.Random;

public class Population {
    DNA[] population;
    String best;
    ArrayList<DNA> matingPool;
    int generations;
    boolean finished;
    String target;
    String[] targetArray;
    double mutationRate;
    int perfectScore;

    Population(String t, double m, int num) {
        finished = false;
        target = t;
        mutationRate = m;
        perfectScore = 1;
        best = "";

        population = new DNA[num];
        for (int i = 0; i < population.length; i++) {
            population[i] = new DNA(target.length());
        }

        targetArray = new String[target.length()];
        for (int i = 0; i < target.length(); i++) {
            targetArray[i] = String.valueOf(target.toCharArray()[i]);
        }
        //calcFitness();
    }

    //filling fitness array for all the DNA objects
    public void calcFitness() {
        for (DNA dna : population) {
            dna.calcFitness(targetArray);
        }
    }

    //generating mating pool
    public void naturalSelection() {
        calcFitness();
        matingPool = new ArrayList<>();

        double totalFitness = 0;
        for (int i = 0; i < population.length; i++) {
            totalFitness += population[i].fitness;
        }

        for (int i = 0; i < population.length; i++) {
            double n = population[i].fitness/totalFitness;
            int num = (int)Math.round(n * 10000);
            for (int j = 0; j < num; j++) {
                matingPool.add(population[i]);
            }
        }
    }

    //create new generation
    public void generate() {
        if (matingPool.size() == 0) {
            finished = true;
            System.out.println("Error mating pool is empty");
        }
        else {
            for (int i = 0; i < population.length; i++) {
                int a = new Random().nextInt(matingPool.size());
                int b = new Random().nextInt(matingPool.size());
                DNA partnerA = matingPool.get(a);
                DNA partnerB = matingPool.get(b);
                DNA child = partnerA.crossover(partnerB);
                child.mutate(mutationRate);
                population[i] = child;
            }
            generations++;
        }
    }

    //getting the best phrase in the population and checking if it is finished
    public void evaluate() {
        double highestFitness = 0;
        int index = 0;
        for (int i = 0; i < population.length; i++) {
            if (population[i].fitness > highestFitness) {
                index = i;
                highestFitness = population[i].fitness;
            }
        }
        best = population[index].getPhrase();
        if (highestFitness == perfectScore) {
            finished = true;
        }
    }

    public double getAverageFitness() {
        double totalFitness = 0;
        for (int i = 0; i < population.length; i++) {
            totalFitness += population[i].fitness;
        }
        return totalFitness / population.length;
    }
}
