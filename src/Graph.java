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
            if(!contains(cityTrial,cities)) {
                return cityTrial;
            }
        } while (true);
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
        fitness = (double)1000/totalDistance;
    }

    public Graph crossover(Graph partner) {
        String[] parent1 = cities;
        String[] parent2 = partner.cities;
        Graph child = new Graph(true);

        child.cities[1] = parent1[1];
        for (int i = 2; i < parent1.length-1; i++) {
            // getting index of last added city in to child in both parents
            int indexOfLastAddedInParent1 = getIndexOf(child.cities[i-1], parent1);
            int indexOfLastAddedInParent2 = getIndexOf(child.cities[i-1], parent2);
            int indexOfShortest = -1;
            shortestDistance = 300;
            
            //nfinds which out of the 4 nodes adhacent nodes to the last added node to the child has shortest distance
            indexOfShortest = getIndexOfShortestDistance(indexOfLastAddedInParent1-1, parent1, child.cities, indexOfShortest, child.cities[i-1], 0);
            indexOfShortest = getIndexOfShortestDistance(indexOfLastAddedInParent1+1, parent1, child.cities, indexOfShortest, child.cities[i-1], 1);
            indexOfShortest = getIndexOfShortestDistance(indexOfLastAddedInParent2-1, parent2, child.cities, indexOfShortest, child.cities[i-1], 2);
            indexOfShortest = getIndexOfShortestDistance(indexOfLastAddedInParent2+1, parent2, child.cities, indexOfShortest, child.cities[i-1], 3);

            // if there is no available adjacent nodes, a new node is randomly generated, otherwise the adjacvent  
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
        if (indexInParent != 0 && indexInParent != parent.length-1 &&  !contains(parent[indexInParent], child) && graphData.getDistance(lastAddedInChild, parent[indexInParent]) < shortestDistance) {
            indexOfShortest = index;
            shortestDistance = graphData.getDistance(lastAddedInChild, parent[indexInParent]);
        }
        return indexOfShortest;
    }

    //order  crossover (OX)
    public Graph orderCrossover(Graph partner) {
        String[] parent1 = cities;
        String[] parent2 = partner.cities;
        Graph child = new Graph(true);
        
        int bound1 = new Random().nextInt(1, cities.length/2);
        int bound2 = new Random().nextInt(cities.length/2, cities.length-1);

        //putting random subsequence from parent 1 into child
        for (int i = bound1; i < bound2; i++) {
            child.cities[i] = parent1[i];
        }         
        //filling in missing indexes from parent2 with cities that are not laready in  child
        for (int i = 0; i < parent2.length; i++) {
            if(child.cities[i] == null) {
                child.cities[i] = parent2[getNextAvailable(child.cities, parent2)];
            }
        }
        return child;
    }

    //mutation
    public void mutate(double mutationRate) {
        for (int i = 1; i < cities.length-1; i++) {
            if (new Random().nextDouble(1) < mutationRate) {
                int randomIndex = new Random().nextInt(1,cities.length-1);
                String city1 = cities[randomIndex];
                String city2 = cities[i];
                cities[i] = city1;
                cities[randomIndex] = city2;
            }
        }
    }

    //returns a string of all values in teh array combined
    public String getPhrase() {
        return String.join(", ", cities);
    }

    public int getIndexOf(String s, String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].equals(s)) return i;
        }
        return -1;
    }

    public boolean contains(String s, String[] array) {
        return getIndexOf(s, array) != -1;
    }

    public int getNextAvailable(String[] child, String[] parent) {
        for (int i = 0; i < parent.length; i++) {
            if(!contains(parent[i], child)) return i;
        }
        return 0;
    }

}
