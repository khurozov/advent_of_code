package solutions.y2015;

import solutions.Solution;

import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Function;

public class Day2 extends Solution {
    public static void main(String[] args) throws Exception {
        Day2 day1 = new Day2();
        day1.part1();
        day1.part2();
    }

    @Override
    public void part1() throws Exception {
        solve(l -> {
            int s0 = l[0]*l[1];
            int s1 = l[0]*l[2];
            int s2 = l[1]*l[2];

            return Math.min(s0, Math.min(s1, s2)) + 2 * (s0 + s1 + s2);
        });
    }

    @Override
    public void part2() throws Exception {
        solve(l ->
                2 * (l[0] + l[1] + l[2] - Math.max(l[0], Math.max(l[1], l[2]))) + l[0] * l[1] * l[2]
        );
    }

    private void solve(Function<Integer[], Integer> func) throws IOException {
        System.out.println(
                Files.readAllLines(this.inputFile())
                        .parallelStream()
                        .map(s -> s.split("x"))
                        .map(s -> new Integer[]{Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])})
                        .map(func)
                        .reduce(0, Integer::sum)
        );
    }
}
