import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Day11 {

    private record Galaxy(int i, int j) {
        long distance(Galaxy g, boolean[] hasGRow, boolean[] hasGCol, int expand) {
            long d = 0;
            int max = Math.max(this.i, g.i);
            int min = Math.min(this.i, g.i);
            for (int x = min; x < max; x++) {
                d += hasGRow[x] ? 1 : expand;
            }
            max = Math.max(this.j, g.j);
            min = Math.min(this.j, g.j);
            for (int x = min; x < max; x++) {
                d += hasGCol[x] ? 1 : expand;
            }
            return d;
        }
    }

    public static void main(String[] args) throws IOException {
        part1();
        part2();
    }

    private static void part1() throws IOException {
        System.out.println(getDistanceSum(2));
    }

    private static void part2() throws IOException {
        System.out.println(getDistanceSum(1000000));
    }

    private static long getDistanceSum(int expansion) throws IOException {
        char[][] m = readInput();
        boolean[][] hasG = hasG(m);
        ArrayList<Galaxy> galaxies = readGalaxies(m);

        long sum = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            Galaxy g = galaxies.get(i);
            for (int j = i + 1; j < galaxies.size(); j++) {
                sum += g.distance(galaxies.get(j), hasG[0], hasG[1], expansion);
            }
        }
        return sum;
    }


    private static char[][] readInput() throws IOException {
        return Files.readAllLines(Path.of("src", "Day11.txt"))
                .stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    private static boolean[][] hasG(char[][] input) {
        boolean[] rows = new boolean[input.length];
        boolean[] cols = new boolean[input[0].length];

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j] == '#') {
                    rows[i] = true;
                    cols[j] = true;
                }
            }
        }

        return new boolean[][]{rows, cols};
    }

    private static ArrayList<Galaxy> readGalaxies(char[][] m) {
        ArrayList<Galaxy> galaxies = new ArrayList<>();
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if (m[i][j] == '#') {
                    galaxies.add(new Galaxy(i, j));
                }
            }
        }
        return galaxies;
    }
}
