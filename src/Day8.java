import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;

public class Day8 {
    private record Pair(String left, String right) {
    }

    public static void main(String[] args) throws IOException {
        part1();
        part2();
    }

    private static void part1() throws IOException {
        System.out.println(steps(readDirections(), readMap(), "AAA", "ZZZ"));
    }

    private static void part2() throws IOException {
        char[] directions = readDirections();
        HashMap<String, Pair> map = readMap();

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

    private static char[] readDirections() throws IOException {
        Scanner in = new Scanner(Path.of("src", "Day8.txt"));

        return in.nextLine().toCharArray();
    }

    private static HashMap<String, Pair> readMap() throws IOException {
        Scanner in = new Scanner(Path.of("src", "Day8.txt"));

        in.nextLine();
        in.nextLine();

        HashMap<String, Pair> map = new HashMap<>();

        while (in.hasNextLine()) {
            String[] points = in.nextLine().split("[ =(),]+");

            map.put(points[0], new Pair(points[1], points[2]));
        }

        return map;
    }

    private static long steps(char[] dirs, HashMap<String, Pair> map, String start, String end) {
        String current = start;
        long n = 0;
        while (!current.endsWith(end)) {
            Pair p = map.get(current);
            current = dirs[(int) (n % dirs.length)] == 'L' ? p.left() : p.right();

            n++;
        }
        return n;
    }
}
