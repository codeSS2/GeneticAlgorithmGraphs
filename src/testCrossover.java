import java.util.Random;

public class testCrossover {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            crossover();
        }
    }

    public static void crossover () {
        String[] parent1 = new String[]{"a","c","b","h","f","g","e","d"};
        String[] parent2 = new String[]{"e","f","g","d","c","b","a","h"};
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

    public static int getIndexOf(String s, String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].equals(s)) return i;
        }
        return -1;
    }
}
