// A class to describe a pseudo-DNA, i.e. genotype
//   Here, a virtual organism's DNA is an array of character.
//   Functionality:
//      -- convert DNA into a string
//      -- calculate DNA's "fitness"
//      -- mate DNA with another set of DNA
//      -- mutate DNA
import java.util.Random;

public class Graph {
    String[] cities;
    double fitness;
    int totalDistance;
    GraphData graphData = new GraphData();

    //constructor
    Graph() {
        this.cities = new String[22];
        this.fitness = 0;
        this.cities[0] = "x";
        for (int i = 1; i < 21; i++) {
            this.cities[i] = newChar();
        }
        this.cities[21] = "x";
    }

    //randomly selects a new char from set given bounds
    public String newChar() {
        int c = new Random().nextInt(97,117);
        return Character.toString(c);
    }

    //returns a string of all values in teh array combined
    public String getPhrase() {
        return String.join(", ", cities);
    }

    //calculates total Distance
    public void calcTotalDistance() {
        totalDistance = 0;
        for (int i = 0; i < cities.length - 1; i++) {
            totalDistance += graphData.getDistance(cities[i],cities[i+1]);
        }
    }

    //fitness function
    public void calcFitness() {
        calcTotalDistance();
        fitness = (double) totalDistance / 1000;
    }

/*    //crossover function
    public DNA crossover(DNA partner) {
        DNA child = new DNA(cities.length);
        int midpoint = new Random().nextInt(cities.length);
        for (int i = 0; i < cities.length; i++) {
            if (i > midpoint) child.cities[i] = cities[i];
            else child.cities[i] = partner.cities[i];
        }
        return child;
    }*/

    public Graph crossover(Graph partner) {
        Graph child = new Graph();

        for (int i = 0; i < cities.length; i+=2) {
            int rand = new Random().nextInt(2);
            String node1 = cities[i];

            if (childContains(node1, child.cities)) continue;
            else if (rand == 0) { //getting edge from this
                if (i != cities.length && !childContains(cities[i+1],child.cities)){
                    child.cities[i] = node1;
                    child.cities[i+1] = cities[i+1];
                }
                else if (i != 0 && !childContains(cities[i-1],child.cities)) {
                    child.cities[i] = node1;
                    child.cities[i+1] = cities[i-1];
                } else {

                }
            }
            else if (rand == 0) { //getting edge from this
                if (i != cities.length && !childContains(cities[i+1],child.cities)){
                    child.cities[i] = node1;
                    child.cities[i+1] = cities[i+1];
                }
                else if (i != 0 && !childContains(cities[i-1],child.cities)) {
                    child.cities[i] = node1;
                    child.cities[i+1] = cities[i-1];
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
        for (int i = 0; i < cities.length; i++) {
            if (new Random().nextInt(100) < mutationRate) {
                cities[i] = newChar();
            }
        }
    }

}
