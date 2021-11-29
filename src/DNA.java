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

    //constructor
    DNA(int size) {
        this.genes = new String[size];
        this.fitness = 0;
        for (int i = 0; i < size; i++) {
            this.genes[i] = newChar();
        }
    }

    //randomly selects a new char from set given bounds
    public String newChar() {
        int c = new Random().nextInt(58);
        //int cbc = new Random().nextInt(65,91);
        if (c == 47) c = 48;
        return Character.toString(c);
    }

    //returns a string of all values in teh array combined
    public String getPhrase() {
        return String.join("", genes);
    }

    //fitness function
    public void calcFitness(String[] target) {
        int score = 0;
        for (int i = 0; i < genes.length; i++) {
            if (genes[i].equals(target[i])) {
                score++;
            }
        }
        fitness = (double) score / target.length;
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
