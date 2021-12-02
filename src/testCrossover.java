import java.util.Random;

public class testCrossover {
    static GraphData graphData = new GraphData();
    static int shortestDistance;

    public static void main(String[] args) {
        System.out.println(crossoverWithWeight(
            new String[]{"x", "h", "l", "i", "m", "g", "q", "j", "r", "o", "c", "k", "t", "f", "a", "e", "d", "s", "b", "p", "n", "x"}, 
            new String[]{"x", "d", "q", "i", "m", "f", "c", "o", "j", "n", "k", "g", "l", "t", "s", "b", "a", "e", "r", "h", "p", "x"}));
                    
    }


    public static String crossoverWithWeight(String[] parent1, String[] parent2) {
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
        return String.join(", ", child.cities);
    }

    public static int getIndexOf(String s, String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].equals(s)) return i;
        }
        return -1;
    }

    public static int getIndexOfShortestDistance(int indexInParent, String[] parent, String[] child, int indexOfShortest, String lastAddedInChild, int index) {
        if (indexInParent != 0 && indexInParent != parent.length-1 && getIndexOf(parent[indexInParent], child) == -1 && graphData.getDistance(lastAddedInChild, parent[indexInParent]) < shortestDistance) {
            indexOfShortest = index;
            shortestDistance = graphData.getDistance(lastAddedInChild, parent[indexInParent]);
        }
        return indexOfShortest;
    }

    //randomly selects a new char from set given bounds
    public static String newChar(String[] cities) {
        do {
            String cityTrial = Character.toString(new Random().nextInt(97,117));
            if(getIndexOf(cityTrial,cities) == -1) {
                return cityTrial;
            }
        } while (true);
    }

    public static void crossover () {
        String[] parent1 = new String[]{"x", "d", "f", "t", "g", "j", "l", "m", "e", "r", "b", "a", "h", "s", "q", "o", "i", "k", "c", "p", "n", "x"};
        String[] parent2 = new String[]{"x", "i", "p", "h", "k", "r", "t", "o", "c", "a", "b", "l", "j", "e", "d", "f", "g", "m", "q", "n", "s", "x"};
        String[] child = new String[parent1.length];

        child[0] = parent1[0];

        for (int i = 1; i < parent1.length-1; i++) {
            int indexOfLastAddedInParent1 = getIndexOf(child[i-1], parent1);
            int indexOfLastAddedInParent2 = getIndexOf(child[i-1], parent2);

            boolean[] availableNodes = new boolean[]{false,false,false,false};

            if (indexOfLastAddedInParent1 != 0) availableNodes[0] = getIndexOf(parent1[indexOfLastAddedInParent1-1], child) == -1;
            if (indexOfLastAddedInParent1 != parent1.length-1) availableNodes[1] = getIndexOf(parent1[indexOfLastAddedInParent1+1], child) == -1;
            if (indexOfLastAddedInParent2 != 0) availableNodes[2] = getIndexOf(parent2[indexOfLastAddedInParent2-1], child) == -1;
            if (indexOfLastAddedInParent2 != parent2.length-1) availableNodes[3] = getIndexOf(parent2[indexOfLastAddedInParent2+1], child) == -1;

            int rand;
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
                    String node = String.valueOf("abcdefghijklmnopqrst".charAt(new Random().nextInt(parent1.length)));
                    if(getIndexOf(node,child) == -1) {
                        child[i] = node;
                        found = true;
                    }
                } while (!found);
            }
            else {
                boolean found = false;
                do {
                    rand = new Random().nextInt(4);
                    if (availableNodes[rand]) found = true;
                } while (!found);

                switch(rand) {
                    case 0 -> child[i] = parent1[indexOfLastAddedInParent1-1];
                    case 1 -> child[i] = parent1[indexOfLastAddedInParent1+1];
                    case 2 -> child[i] = parent2[indexOfLastAddedInParent2-1];
                    case 3 -> child[i] = parent2[indexOfLastAddedInParent2+1];
                }
            }
        }
        System.out.println(String.join(", ", child));
    }
}
