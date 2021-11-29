import java.util.Hashtable;

public class Main {
    public static void main(String[] args) {
        String target = "3.14159265358979323846264338327950";
        int populationMax = 2000;
        double mutationRate = 0.01;
        Population population = new Population(target, mutationRate, populationMax);

        System.out.println("Target: " + target + "");
        long startTime = System.nanoTime();

        do {
            population.naturalSelection();
            population.generate();
            population.calcFitness();
            population.evaluate();
            //System.out.println(population.best);
        } while(!population.finished);

        long endTime = System.nanoTime();
        System.out.println("" + "Population: " + populationMax);
        System.out.println("Mutation Rate: " + mutationRate);
        System.out.println("Total Generations: " + population.generations);
        System.out.println("Average Fitness: " + Math.round(population.getAverageFitness() * 100) + "%");
        System.out.println("Time taken: " + (endTime - startTime) / 1000000000 + " seconds");

    }
}
