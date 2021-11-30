import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.SourceDataLine;

public class testCrossover {
    static Graph graph = new Graph();

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        crossoverWithWeight();
        long endTime = System.nanoTime();
        System.out.println("Time taken: " + (endTime - startTime) / 100000000 + "mili seconds");
    }

    public static void crossoverWithWeight () {
        String[] parent1 = new String[]{"d", "f", "t", "g", "j", "l", "m", "e", "r", "b", "a", "h", "s", "q", "o", "i", "k", "c", "p", "n"};
        String[] parent2 = new String[]{"i", "p", "h", "k", "r", "t", "o", "c", "a", "b", "l", "j", "e", "d", "f", "g", "m", "q", "n", "s"};
        String[] child = new String[parent1.length];

        child[0] = parent1[0];

        for (int i = 1; i < parent1.length; i++) {
            int indexOfLastAddedInParent1 = getIndexOf(child[i-1], parent1);
            int indexOfLastAddedInParent2 = getIndexOf(child[i-1], parent2);

            boolean[] availableNodes = new boolean[]{false,false,false,false};
            String[] cities = new String[4];

            if (indexOfLastAddedInParent1 != 0) {
                availableNodes[0] = getIndexOf(parent1[indexOfLastAddedInParent1-1], child) == -1;
                cities[0] = parent1[indexOfLastAddedInParent1-1];
            }
            if (indexOfLastAddedInParent1 < parent1.length-1) {
                availableNodes[1] = getIndexOf(parent1[indexOfLastAddedInParent1+1], child) == -1;
                cities[1] = parent1[indexOfLastAddedInParent1+1];
            }
            if (indexOfLastAddedInParent2 != 0) {
                availableNodes[2] = getIndexOf(parent2[indexOfLastAddedInParent2-1], child) == -1;
                cities[2] = parent2[indexOfLastAddedInParent2-1];
            }
            if (indexOfLastAddedInParent2 < parent2.length-1) {
                availableNodes[3] = getIndexOf(parent2[indexOfLastAddedInParent2+1], child) == -1;
                cities[3] = parent2[indexOfLastAddedInParent2+1];
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
                    String node = String.valueOf("abcdefghijklmnopqrst".charAt(new Random().nextInt(parent1.length)));
                    if(getIndexOf(node,child) == -1) {
                        child[i] = node;
                        found = true;
                    }
                } while (!found);
            }
            else {
                int indexOfShortest = 0;
                int shortestDistance = 300;
                for (int j = 0; j < 4; j++) {
                    if (availableNodes[j] && graph.getDistance(child[i-1], cities[j]) < shortestDistance) {
                            indexOfShortest = j;
                            shortestDistance = graph.getDistance(child[i-1], cities[j]);
                    }
                }
                
                switch(indexOfShortest) {
                    case 0 -> child[i] = parent1[indexOfLastAddedInParent1-1];
                    case 1 -> child[i] = parent1[indexOfLastAddedInParent1+1];
                    case 2 -> child[i] = parent2[indexOfLastAddedInParent2-1];
                    case 3 -> child[i] = parent2[indexOfLastAddedInParent2+1];
                }
            }
        }
        System.out.println(String.join(", ", child));
    }

    public static int getIndexOf(String s, String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].equals(s)) return i;
        }
        return -1;
    }

    public static void crossover () {
        String[] parent1 = new String[]{"d", "f", "t", "g", "j", "l", "m", "e", "r", "b", "a", "h", "s", "q", "o", "i", "k", "c", "p", "n"};
        String[] parent2 = new String[]{"i", "p", "h", "k", "r", "t", "o", "c", "a", "b", "l", "j", "e", "d", "f", "g", "m", "q", "n", "s"};
        String[] child = new String[parent1.length];

        child[0] = parent1[0];

        for (int i = 1; i < parent1.length; i++) {
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
                    String node = String.valueOf("abcdefgh".charAt(new Random().nextInt(parent1.length)));
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
