package solutions.y2023;

import extra.Pair;
import extra.Util;
import solutions.Solution;

import java.io.IOException;
import java.util.ArrayList;

public class Day11 extends Solution {

    public static void main(String[] args) throws Exception {
        Day11 day11 = new Day11();
        day11.part1();
        day11.part2();
    }

    @Override
    public void part1() throws Exception {
        System.out.println(getDistanceSum(2));
    }

    @Override
    public void part2() throws Exception {
        System.out.println(getDistanceSum(1000000));
    }

    private long getDistanceSum(int expansion) throws IOException {
        char[][] m = Util.inputAsCharGrid(this.inputFile());
        boolean[][] hasG = hasG(m);
        ArrayList<Pair<Integer>> galaxies = readGalaxies(m);

        long sum = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            Pair<Integer> g = galaxies.get(i);
            for (int j = i + 1; j < galaxies.size(); j++) {
                sum += distance(g, galaxies.get(j), hasG[0], hasG[1], expansion);
            }
        }
        return sum;
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

    private static ArrayList<Pair<Integer>> readGalaxies(char[][] m) {
        ArrayList<Pair<Integer>> galaxies = new ArrayList<>();
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if (m[i][j] == '#') {
                    galaxies.add(Pair.of(i, j));
                }
            }
        }
        return galaxies;
    }

    private static long distance(Pair<Integer> g0, Pair<Integer> g1, boolean[] hasGRow, boolean[] hasGCol, int expand) {
        long d = 0;
        int max = Math.max(g0.left(), g1.left());
        int min = Math.min(g0.left(), g1.left());
        for (int x = min; x < max; x++) {
            d += hasGRow[x] ? 1 : expand;
        }
        max = Math.max(g0.right(), g1.right());
        min = Math.min(g0.right(), g1.right());
        for (int x = min; x < max; x++) {
            d += hasGCol[x] ? 1 : expand;
        }
        return d;
    }
}
