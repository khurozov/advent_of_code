package solutions.y2015;

import solutions.Solution;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.function.Function;

public class Day6 extends Solution {
    public static void main(String[] args) throws Exception {
        Day6 day6 = new Day6();
        day6.part1();
        day6.part2();
    }

    @Override
    public void part1() throws Exception {
        System.out.println(solve(Boolean.class, false, b -> true, b -> false, b -> !b, b -> b ? 1 : 0));
    }

    @Override
    public void part2() throws Exception {
        System.out.println(solve(Integer.class, 0, n -> n + 1, n -> n > 0 ? n - 1 : 0, n -> n + 2, Integer::intValue));
    }

    @SuppressWarnings("unchecked")
    private <T> int solve(Class<T> clazz, T initial, Function<T, T> turnOn, Function<T, T> turnOff, Function<T, T> toggle, Function<T, Integer> intMapper) throws IOException {
        final T[][] grid = (T[][]) Array.newInstance(clazz, 1000, 1000);
        for (T[] row : grid) {
            Arrays.fill(row, initial);
        }

        Files.readAllLines(this.inputFile()).forEach(str -> {
            String[] s = str.split("[ ,]");

            int iFrom;
            int jFrom;
            int iTo;
            int jTo;
            Function<T, T> act;

            if (s[0].equals("turn")) {
                if (s[1].equals("on")) {
                    iFrom = Integer.parseInt(s[2]);
                    jFrom = Integer.parseInt(s[3]);
                    iTo = Integer.parseInt(s[5]);
                    jTo = Integer.parseInt(s[6]);

                    act = turnOn;
                } else {
                    iFrom = Integer.parseInt(s[2]);
                    jFrom = Integer.parseInt(s[3]);
                    iTo = Integer.parseInt(s[5]);
                    jTo = Integer.parseInt(s[6]);

                    act = turnOff;
                }
            } else {
                iFrom = Integer.parseInt(s[1]);
                jFrom = Integer.parseInt(s[2]);
                iTo = Integer.parseInt(s[4]);
                jTo = Integer.parseInt(s[5]);

                act = toggle;
            }

            action(grid, iFrom, jFrom, iTo, jTo, act);
        });

        return Arrays.stream(grid).flatMap(Arrays::stream).map(intMapper).reduce(0, Integer::sum);
    }

    private static <T> void action(T[][] grid, int iFrom, int jFrom, int iTo, int jTo, Function<T, T> act) {
        for (int i = iFrom; i <= iTo; i++) {
            for (int j = jFrom; j <= jTo; j++) {
                grid[i][j] = act.apply(grid[i][j]);
            }
        }
    }
}
