import java.util.Hashtable;

public class Graph {
    private final int[][] matrix;

    Graph() {
        this.matrix = new int[][]{
                {0, 94, 76, 141, 91, 60, 120, 145, 91, 74, 90, 55, 145, 108, 41, 49, 33, 151, 69, 111, 24},
                {94, 0, 156, 231, 64, 93, 108, 68, 37, 150, 130, 57, 233, 26, 62, 140, 61, 229, 120, 57, 109},
                {},

        };
    }

    public int getIndexOfLetter(String str) {
        return "xabcdefghijklmnopqrs".indexOf(str.toLowerCase());
    }

    public int getDistance(String city1, String city2) {
        return matrix[getIndexOfLetter(city1)][getIndexOfLetter(city2)];
    }

}
