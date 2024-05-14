package solutions.y2015;

import solutions.Solution;

import java.io.IOException;
import java.nio.file.Files;

public class Day17 extends Solution {

    public static void main(String[] args) throws Exception {
        Day17 day14 = new Day17();
        day14.part1();
        day14.part2();
    }

    @Override
    public void part1() throws Exception {
        System.out.println(rec1(150, sortedContainers(), 0));
    }

    @Override
    public void part2() throws Exception {
        int[] c = sortedContainers();

        // reversed for better performance
        int n = c.length / 2;
        for (int i = 0; i < n; i++) {
            int tmp = c[i];
            c[i] = c[c.length - 1 - i];
            c[c.length - 1 - i] = tmp;
        }

        System.out.println(rec2(150, c, 0));
    }

    private static int rec1(int m, int[] c, int i) {
        if (m == 0) return 1;
        if (c.length == i) return 0;

        if (c[i] <= m) {
            return rec1(m-c[i], c, i+1) + rec1(m, c, i+1);
        }

        return 0;
    }

    private static int rec2(int m, int[] c, int i) {
        if (m == 0) return 0;
        if (c.length == i) return Integer.MAX_VALUE-100;

        if (c[i] <= m) {
            return Math.min(rec2(m-c[i], c, i+1)+1, rec2(m, c, i+1));
        }

        return Integer.MAX_VALUE-100;
    }

    private int[] sortedContainers() throws IOException {
        return Files.readAllLines(this.inputFile()).stream()
                .mapToInt(Integer::parseInt)
                .sorted()
                .toArray();
    }
}