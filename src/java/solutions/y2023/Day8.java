package solutions.y2023;

import extra.Pair;
import solutions.Solution;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Day8 extends Solution {

    public static void main(String[] args) throws Exception {
        Day8 day8 = new Day8();
        day8.part1();
        day8.part2();
    }

    @Override
    public void part1() throws Exception {
        System.out.println(steps(readDirections(), readMap(), "AAA", "ZZZ"));
    }

    @Override
    public void part2() throws Exception {
        char[] directions = readDirections();
        HashMap<String, Pair<String>> map = readMap();

        long n = map.keySet()
                .stream()
                .filter(s -> s.endsWith("A"))
                .map(s -> steps(directions, map, s, "Z"))
                .reduce(1L, (Long n1, Long n2) -> {
                    long max = Math.max(n1, n2);
                    long min = Math.min(n1, n2);
                    long lcm = max;

                    while (lcm % min != 0) {
                        lcm += max;
                    }

                    return lcm;
                });

        System.out.println(n);
    }

    private char[] readDirections() throws IOException {
        Scanner in = new Scanner(this.inputFile());

        return in.nextLine().toCharArray();
    }

    private HashMap<String, Pair<String>> readMap() throws IOException {
        Scanner in = new Scanner(this.inputFile());

        in.nextLine();
        in.nextLine();

        HashMap<String, Pair<String>> map = new HashMap<>();

        while (in.hasNextLine()) {
            String[] points = in.nextLine().split("[ =(),]+");

            map.put(points[0], Pair.of(points[1], points[2]));
        }

        return map;
    }

    private static long steps(char[] dirs, HashMap<String, Pair<String>> map, String start, String end) {
        String current = start;
        long n = 0;
        while (!current.endsWith(end)) {
            Pair<String> p = map.get(current);
            current = dirs[(int) (n % dirs.length)] == 'L' ? p.left() : p.right();

            n++;
        }
        return n;
    }
}
