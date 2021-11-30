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
        int c = 0;//new Random().nextInt(97,117);
        return Character.toString(c);
    }

    //returns a string of all values in teh array combined
    public String getPhrase() {
        return String.join(", ", genes);
    }

    //calculates total Distance
    public void calcTotalDistance() {
        totalDistance = 0;
        for (int i = 0; i < genes.length - 1; i++) {
            totalDistance += graph.getDistance(genes[i],genes[i+1]);
        }
        totalDistance += graph.getDistance("x", genes[genes.length-1]) + graph.getDistance("x", genes[0]);
    }

    //fitness function
    public void calcFitness() {
        calcTotalDistance();
        fitness = (double) totalDistance / 1000;
    }

/*    //crossover function
    public DNA crossover(DNA partner) {
        DNA child = new DNA(genes.length);
        int midpoint = new Random().nextInt(genes.length);
        for (int i = 0; i < genes.length; i++) {
            if (i > midpoint) child.genes[i] = genes[i];
            else child.genes[i] = partner.genes[i];
        }
        return child;
    }*/

    public DNA crossover(DNA partner) {
        DNA child = new DNA(genes.length);

        for (int i = 0; i < genes.length; i+=2) {
            int rand = new Random().nextInt(2);
            String node1 = genes[i];

            if (childContains(node1, child.genes)) continue;
            else if (rand == 0) { //getting edge from this
                if (i != genes.length && !childContains(genes[i+1],child.genes)){
                    child.genes[i] = node1;
                    child.genes[i+1] = genes[i+1];
                }
                else if (i != 0 && !childContains(genes[i-1],child.genes)) {
                    child.genes[i] = node1;
                    child.genes[i+1] = genes[i-1];
                } else {

                }
            }
            else if (rand == 0) { //getting edge from this
                if (i != genes.length && !childContains(genes[i+1],child.genes)){
                    child.genes[i] = node1;
                    child.genes[i+1] = genes[i+1];
                }
                else if (i != 0 && !childContains(genes[i-1],child.genes)) {
                    child.genes[i] = node1;
                    child.genes[i+1] = genes[i-1];
                } else {

                }
            }
        }
        return child;
    }

    public boolean childContains(String str, String[] child) {
        for (String s: child) {
            if (s.equals(str)) return true;
        }
        return false;
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
