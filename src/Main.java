public class Main {
    public static void main(String[] args) {
        int populationMax = 1000;
        double mutationRate = 0.01; //meaning 1%
        double targetFitness = 0.93;
        Population population = new Population(targetFitness, mutationRate, populationMax);

        long startTime = System.nanoTime();
        do { 
            population.naturalSelection();
            population.generate();
            population.calcFitness();
            population.evaluate();
        } while(!population.finished); 
        System.out.println(population.best);
        long endTime = System.nanoTime();
        System.out.println("" + "Population: " + populationMax);
        System.out.println("Total Generations: " + population.generations);
        System.out.println("Mutation Rate: " + mutationRate * 100 + "%");
        System.out.println("Average Fitness: " + Math.round(population.getAverageFitness() * 100) + "%");
        System.out.println("Time taken: " + (endTime - startTime) / 1000000000 + " seconds");  
        //hello
    }
}
