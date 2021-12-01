import java.util.Random;

public class Graph {
    String[] cities;
    double fitness;
    int totalDistance;
    GraphData graphData = new GraphData();

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
        fitness = (double) totalDistance / 5000;
        System.out.println(totalDistance);
    }

    public Graph crossover(Graph partner) {
        System.out.println("enter");
        String[] parent1 = cities;
        String[] parent2 = partner.cities;
        Graph child = new Graph(true);

        child.cities[0] = parent1[0];

        for (int i = 1; i < parent1.length-1; i++) {
            int indexOfLastAddedInParent1 = getIndexOf(child.cities[i-1], parent1);
            int indexOfLastAddedInParent2 = getIndexOf(child.cities[i-1], parent2);
            boolean allFalse = true;
            int indexOfShortest = 0;
            int shortestDistance = 300;

            if (indexOfLastAddedInParent1 != 0) {
                allFalse = false;
                if (graphData.getDistance(child.cities[i-1], parent1[indexOfLastAddedInParent1-1]) < shortestDistance) {
                    indexOfShortest = 0;
                    shortestDistance = graphData.getDistance(child.cities[i-1], parent1[indexOfLastAddedInParent1-1]);
                }   
            }
            if (indexOfLastAddedInParent1 < parent1.length-1) {
                allFalse = false;
                if (graphData.getDistance(child.cities[i-1], parent1[indexOfLastAddedInParent1+1]) < shortestDistance) {
                    indexOfShortest = 1;
                    shortestDistance = graphData.getDistance(child.cities[i-1], parent1[indexOfLastAddedInParent1+1]);
                }   
            }
            if (indexOfLastAddedInParent2 != 0) {
                allFalse = false;
                if (graphData.getDistance(child.cities[i-1], parent2[indexOfLastAddedInParent2-1]) < shortestDistance) {
                    indexOfShortest = 2;
                    shortestDistance = graphData.getDistance(child.cities[i-1], parent2[indexOfLastAddedInParent2-1]);
                }   
            }
            if (indexOfLastAddedInParent2 < parent2.length-1) {
                allFalse = false;
                if (graphData.getDistance(child.cities[i-1], parent2[indexOfLastAddedInParent2+1]) < shortestDistance) {
                    indexOfShortest = 3;
                    shortestDistance = graphData.getDistance(child.cities[i-1], parent2[indexOfLastAddedInParent2+1]);
                }   
            }

            if (allFalse) {
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
        child.cities[child.cities.length-1] = "x";
        return child;
    }

    public int getIndexOf(String s, String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].equals(s)) return i;
        }
        return -1;
    }

    public boolean childCitiesCitiesContains(String str, String[] childCitiesCities) {
        for (String s: childCitiesCities) {
            if (s.equals(str)) return true;
        }
        return false;
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
