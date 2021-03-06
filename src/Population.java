// A class to describe a population of virtual organisms
// In this case, each organism is just an instance of a Graph object

import java.util.ArrayList;
import java.util.Random;

public class Population {
    Graph[] population;
    ArrayList<Graph> matingPool;
    int generations;
    String best;
    boolean finished;
    double mutationRate;
    double targetFitness;

    Population(double tf, double m, int num) {
        finished = false;
        mutationRate = m;
        targetFitness = tf;
        population = new Graph[num];
        for (int i = 0; i < population.length; i++) {
            population[i] = new Graph(false);
        }
    }

    //filling fitness array for all the Graph objects
    public void calcFitness() {
        for (Graph dna : population) {
            dna.calcFitness();
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
            int num = (int)Math.round(n * 1000);
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
                Graph partnerA = matingPool.get(a);
                Graph partnerB = matingPool.get(b);
                Graph child = partnerA.crossover(partnerB);
                // Graph child = partnerA.orderCrossover(partnerB);
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
        best = "[" + population[index].getPhrase() + "]";
        if (highestFitness > targetFitness) {
            System.out.println("Total distance: " + population[index].totalDistance);
            System.out.println("Fitness of best: " + population[index].fitness * 100 + "%");
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
