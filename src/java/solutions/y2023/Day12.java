package solutions.y2023;

import extra.SpringData;
import solutions.Solution;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Day12 extends Solution {
    public static void main(String[] args) throws Exception {
        Day12 day12 = new Day12();
        day12.part1();
        day12.part2();
    }

    @Override
    public void part1() throws Exception {
        System.out.println(getSumOfArrangements(data -> arrangements(data.c(), 0, data.g(), 0, new HashMap<>())));
    }

    @Override
    public void part2() throws Exception {
        System.out.println(getSumOfArrangements(data -> {
            char[] c = new char[data.c().length * 5 + 4];
            int[] g = new int[data.g().length * 5];
            for (int i = 0; i < 5; i++) {
                if (i > 0) {
                    c[(data.c().length + 1) * i - 1] = '?';
                }
                System.arraycopy(data.c(), 0, c, (data.c().length + 1) * i, data.c().length);
                System.arraycopy(data.g(), 0, g, data.g().length * i, data.g().length);
            }
            return arrangements(c, 0, g, 0, new HashMap<>());
        }));
    }

    private long getSumOfArrangements(Function<SpringData, Long> arrangementFunc) throws IOException {
        return Files.readAllLines(this.inputFile())
                .stream()
                .map(input -> {
                    String[] s = input.split(" ");
                    String ss = s[0];
                    s = s[1].split(",");
                    int[] g = new int[s.length];
                    for (int i = 0; i < s.length; i++) {
                        g[i] = Integer.parseInt(s[i]);
                    }

                    return new SpringData(ss.toCharArray(), g);
                })
                .map(arrangementFunc)
                .reduce(0L, Long::sum);
    }

    private static long arrangements(char[] c, int i, int[] g, int j, Map<String, Long> mem) {
        if (i >= c.length) {
            if (j == g.length) {
                return 1;
            }
            return 0;
        }

        Long m = mem.get(i + ":" + j);
        if (m != null) return m;

        if (c[i] == '.') {
            m = arrangements(c, i + 1, g, j, mem);
            mem.put(i + ":" + j, m);
            return m;
        }
        if (c[i] == '#') {
            return withSpring(c, i, g, j, mem);
        }
        if (c[i] == '?') {
            m = arrangements(c, i + 1, g, j, mem) + withSpring(c, i, g, j, mem);
            mem.put(i + ":" + j, m);
            return m;
        }
        throw new IllegalArgumentException();
    }

    private static long withSpring(char[] c, int i, int[] g, int j, Map<String, Long> mem) {
        if (j >= g.length) return 0;

        int x = g[j];
        if (x + i > c.length) return 0;

        for (int k = 0; k < x; k++) {
            if (c[k + i] == '.') return 0;
        }

        if (x + i == c.length) {
            if (j + 1 == g.length) return 1;
            return 0;
        }

        if (c[x + i] == '#') return 0;
        return arrangements(c, i + x + 1, g, j + 1, mem);
    }
}
