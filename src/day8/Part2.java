package day8;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;

public class Part2 {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(Path.of("src", "day8", "input"));

        char[] dirs = in.nextLine().toCharArray();
        in.nextLine();

        HashMap<String, Pair> map = new HashMap<>();

        while (in.hasNextLine()) {
            String[] points = in.nextLine().split("[ =(),]+");

            map.put(points[0], new Pair(points[1], points[2]));
        }

        long n = map.keySet()
                .stream()
                .filter(s -> s.endsWith("A"))
                .map(s -> Util.steps(dirs, map, s, "Z"))
                .reduce(1L, Util::lcm);

        System.out.println(n);
    }


}
