package day8;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;

public class Part1 {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(Path.of("src", "day8", "input"));

        char[] dirs = in.nextLine().toCharArray();
        in.nextLine();

        HashMap<String, Pair> map = new HashMap<>();
        while (in.hasNextLine()) {
            String[] points = in.nextLine().split("[ =(),]+");
            map.put(points[0], new Pair(points[1], points[2]));
        }

        System.out.println(Util.steps(dirs, map, "AAA", "ZZZ"));
    }
}
