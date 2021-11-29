// A class to describe a pseudo-DNA, i.e. genotype
//   Here, a virtual organism's DNA is an array of character.
//   Functionality:
//      -- convert DNA into a string
//      -- calculate DNA's "fitness"
//      -- mate DNA with another set of DNA
//      -- mutate DNA
import java.util.Random;

public class DNA {
    String[] genes;
    double fitness;
    int totalDistance;
    Graph graph = new Graph();

    //constructor
    DNA(int size) {
        this.genes = new String[size];
        this.fitness = 0;
        this.genes[0] = "x";
        for (int i = 1; i < size; i++) {
            this.genes[i] = newChar();
        }
    }

    //randomly selects a new char from set given bounds
    public String newChar() {
        int c = new Random().nextInt(97,117);
        return Character.toString(c);
    }

    //returns a string of all values in teh array combined
    public String getPhrase() {
        return String.join(",", genes);
    }

    //calculates total Distance
    public void calcTotalDistance() {
        totalDistance = 0;
        for (int i = 0; i < genes.length - 1; i++) {
            totalDistance += graph.getDistance(genes[i],genes[i+1]);
        }
    }

    //fitness function
    public void calcFitness() {
        calcTotalDistance();
        fitness = (double) totalDistance / 1000;
    }

    //crossover function
    public DNA crossover(DNA partner) {
        DNA child = new DNA(genes.length);
        int midpoint = new Random().nextInt(genes.length);
        for (int i = 0; i < genes.length; i++) {
            if (i > midpoint) child.genes[i] = genes[i];
            else child.genes[i] = partner.genes[i];
        }
        return child;
    }

    //mutation
    public void mutate(double mutationRate) {
        for (int i = 0; i < genes.length; i++) {
            if (new Random().nextInt(100) < mutationRate) {
                genes[i] = newChar();
            }
        }
    }

}
