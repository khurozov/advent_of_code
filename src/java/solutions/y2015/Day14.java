package solutions.y2015;

import solutions.Solution;

import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.Function;

public class Day14 extends Solution {
    private static final int RACE_TIME = 2503;
    private static final Function<String, int[]> parseDeerParams = line -> {
        String[] s = line.split(" ");

        int speed = Integer.parseInt(s[3]);
        int runTime = Integer.parseInt(s[6]);
        int restRime = Integer.parseInt(s[13]);
        int fullTime = runTime + restRime;

        return new int[]{speed, runTime, restRime, fullTime};
    };

    public static void main(String[] args) throws Exception {
        Day14 day14 = new Day14();
        day14.part1();
        day14.part2();
    }

    @Override
    public void part1() throws Exception {
        System.out.println(Files.readAllLines(this.inputFile()).stream()
                .map(parseDeerParams)
                .mapToInt(p -> p[0] * (p[1] * (RACE_TIME / p[3]) + Math.min(RACE_TIME % p[3], p[1])))
                .max()
                .orElse(0)
        );
    }

    @Override
    public void part2() throws Exception {
        int[][] deerDistances = Files.readAllLines(this.inputFile()).stream()
                .map(parseDeerParams)
                .map(p -> {
                    int[] d = new int[RACE_TIME];
                    int i = 0;
                    while (i < RACE_TIME) {
                        for (int j = 0; j < p[1] && i < RACE_TIME; j++, i++) {
                            d[i] = i == 0 ? p[0] : d[i-1]+p[0];
                        }
                        for (int j = 0; j < p[2] && i < RACE_TIME; j++, i++) {
                            d[i] = d[i-1];
                        }
                    }
                    return d;
                })
                .toArray(int[][]::new);

        int[] points = calculatePoints(deerDistances);

        System.out.println(Arrays.stream(points).max().orElse(0));
    }

    private static int[] calculatePoints(int[][] deerDistances) {
        int[] points = new int[deerDistances.length];

        LinkedList<Integer> deer = new LinkedList<>();
        for (int i = 0; i < RACE_TIME; i++) {
            int max = deerDistances[0][i];

            for (int j = 0; j < deerDistances.length; j++) {
                if (deerDistances[j][i] > max) {
                    deer.clear();
                    max = deerDistances[j][i];
                }
                if (deerDistances[j][i] == max) {
                    deer.add(j);
                }
            }

            deer.forEach(d -> points[d]++);
            deer.clear();
        }
        return points;
    }
}