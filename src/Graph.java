import java.util.Random;

public class Graph {
    String[] cities;
    double fitness;
    int totalDistance;
    GraphData graphData = new GraphData();
    int shortestDistance;

    //constructor
    Graph(boolean child) {
        this.cities = new String[22];
        this.fitness = 0;
        if(!child) {
            this.cities[0] = "x";
            for (int i = 1; i < 21; i++) {
                this.cities[i] = newChar(cities);
            }
            this.cities[cities.length-1] = "x";
        }
    }

    //randomly selects a new char from set given bounds
    public String newChar(String[] cities) {
        do {
            String cityTrial = Character.toString(new Random().nextInt(97,117));
            if(getIndexOf(cityTrial,cities) == -1) {
                return cityTrial;
            }
        } while (true);
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
        fitness = (double)1300/totalDistance;
    }

    public Graph crossover(Graph partner) {
        String[] parent1 = cities;
        String[] parent2 = partner.cities;
        Graph child = new Graph(true);

        child.cities[1] = parent1[1];
        for (int i = 2; i < parent1.length-1; i++) {
            int indexOfLastAddedInParent1 = getIndexOf(child.cities[i-1], parent1);
            int indexOfLastAddedInParent2 = getIndexOf(child.cities[i-1], parent2);
            int indexOfShortest = -1;
            shortestDistance = 300;
            
            indexOfShortest = getIndexOfShortestDistance(indexOfLastAddedInParent1-1, parent1, child.cities, indexOfShortest, child.cities[i-1], 0);
            indexOfShortest = getIndexOfShortestDistance(indexOfLastAddedInParent1+1, parent1, child.cities, indexOfShortest, child.cities[i-1], 1);
            indexOfShortest = getIndexOfShortestDistance(indexOfLastAddedInParent2-1, parent2, child.cities, indexOfShortest, child.cities[i-1], 2);
            indexOfShortest = getIndexOfShortestDistance(indexOfLastAddedInParent2+1, parent2, child.cities, indexOfShortest, child.cities[i-1], 3);

            if (indexOfShortest == -1) {
                child.cities[i] = newChar(child.cities);
            } else {
                switch(indexOfShortest) {
                    case 0 -> child.cities[i] = parent1[indexOfLastAddedInParent1-1];
                    case 1 -> child.cities[i] = parent1[indexOfLastAddedInParent1+1];
                    case 2 -> child.cities[i] = parent2[indexOfLastAddedInParent2-1];
                    case 3 -> child.cities[i] = parent2[indexOfLastAddedInParent2+1];
                }
            }
        }
        child.cities[0] = "x";
        child.cities[child.cities.length-1] = "x";
        return child;
    }
    
    public int getIndexOfShortestDistance(int indexInParent, String[] parent, String[] child, int indexOfShortest, String lastAddedInChild, int index) {
        if (indexInParent != 0 && indexInParent != parent.length-1 && getIndexOf(parent[indexInParent], child) == -1 && graphData.getDistance(lastAddedInChild, parent[indexInParent]) < shortestDistance) {
            indexOfShortest = index;
            shortestDistance = graphData.getDistance(lastAddedInChild, parent[indexInParent]);
        }
        return indexOfShortest;
    }

    public int getIndexOf(String s, String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].equals(s)) return i;
        }
        return -1;
    }

    //mutation
    public void mutate(double mutationRate) {
        for (int i = 0; i < cities.length; i++) {
            if (new Random().nextInt(100) < mutationRate) {
                cities[i] = newChar(cities);
            }
        }
    }

}
