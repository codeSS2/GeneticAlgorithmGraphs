public class Main {
    public static void main(String[] args) {
        int populationMax = 100;
        double mutationRate = 1; //meaning 1%
        double targetFitness = 0.1;
        Population population = new Population(targetFitness, mutationRate, populationMax);

        long startTime = System.nanoTime();

        do {
            System.out.println("start");
            population.naturalSelection();
            System.out.println("done");
            population.generate();
            System.out.println("done");
            population.calcFitness();
            population.evaluate();
            
        } while(!population.finished);

        long endTime = System.nanoTime();
        System.out.println("" + "Population: " + populationMax);
        System.out.println("Mutation Rate: " + mutationRate);
        System.out.println("Total Generations: " + population.generations);
        System.out.println("Average Fitness: " + Math.round(population.getAverageFitness() * 100) + "%");
        System.out.println("Time taken: " + (endTime - startTime) / 1000000000 + " seconds");
        
    }
}
