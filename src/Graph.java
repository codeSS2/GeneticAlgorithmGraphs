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

    public Graph crossover(Graph partner) {
        Graph child = new Graph();
        String[] parent1Cities = cities;
        String[] parent2Cities = partner.cities;
        String[] childCities = child.cities;

        childCities[0] = parent1Cities[0];

        for (int i = 1; i < parent1Cities.length; i++) {
            int indexOfLastAddedInParent1 = getIndexOf(childCities[i-1], parent1Cities);
            int indexOfLastAddedInParent2 = getIndexOf(childCities[i-1], parent2Cities);

            boolean[] availableNodes = new boolean[]{false,false,false,false};
            String[] cities = new String[4];

            if (indexOfLastAddedInParent1 != 0) {
                availableNodes[0] = getIndexOf(parent1Cities[indexOfLastAddedInParent1-1], childCities) == -1;
                cities[0] = parent1Cities[indexOfLastAddedInParent1-1];
            }
            if (indexOfLastAddedInParent1 < parent1Cities.length-1) {
                availableNodes[1] = getIndexOf(parent1Cities[indexOfLastAddedInParent1+1], childCities) == -1;
                cities[1] = parent1Cities[indexOfLastAddedInParent1+1];
            }
            if (indexOfLastAddedInParent2 != 0) {
                availableNodes[2] = getIndexOf(parent2Cities[indexOfLastAddedInParent2-1], childCities) == -1;
                cities[2] = parent2Cities[indexOfLastAddedInParent2-1];
            }
            if (indexOfLastAddedInParent2 < parent2Cities.length-1) {
                availableNodes[3] = getIndexOf(parent2Cities[indexOfLastAddedInParent2+1], childCities) == -1;
                cities[3] = parent2Cities[indexOfLastAddedInParent2+1];
            }

            boolean allFalse = true;
            for (boolean b: availableNodes) {
                if (b) {
                    allFalse = false;
                    break;
                }
            }

            if (allFalse) {
                boolean found;
                do {
                    found = false;
                    String node = String.valueOf("abcdefghijklmnopqrst".charAt(new Random().nextInt(parent1Cities.length)));
                    if(getIndexOf(node,childCities) == -1) {
                        childCities[i] = node;
                        found = true;
                    }
                } while (!found);
            }
            else {
                int indexOfShortest = 0;
                int shortestDistance = 300;
                for (int j = 0; j < 4; j++) {
                    if (availableNodes[j] && graphData.getDistance(childCities[i-1], cities[j]) < shortestDistance) {
                            indexOfShortest = j;
                            shortestDistance = graphData.getDistance(childCities[i-1], cities[j]);
                    }
                }
                
                switch(indexOfShortest) {
                    case 0 -> childCities[i] = parent1Cities[indexOfLastAddedInParent1-1];
                    case 1 -> childCities[i] = parent1Cities[indexOfLastAddedInParent1+1];
                    case 2 -> childCities[i] = parent2Cities[indexOfLastAddedInParent2-1];
                    case 3 -> childCities[i] = parent2Cities[indexOfLastAddedInParent2+1];
                }
            }
        }
       // System.out.println(String.join(", ", childCities));
       return child;
    }

    public static int getIndexOf(String s, String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].equals(s)) return i;
        }
        return -1;
    }

    public boolean childCitiesContains(String str, String[] childCities) {
        for (String s: childCities) {
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
