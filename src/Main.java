import java.util.ArrayList;
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

    public void createGraph() {
        Hashtable<String, Integer> edges = new Hashtable<>();

        edges.put("ab",156);

        int[][] matrix = {
                {0,94,76,141,91,60,120,145,91,74,90,55,145,108,41,49,33,151,69,111,24},
                {},
                {},

        };


    }
}
