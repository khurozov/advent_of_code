package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Part2 {
    public static void main(String[] args) throws IOException {
        Integer sum = Files.readAllLines(Path.of("src", "day2", "input"))
                .stream()
                .map(input -> {
                    String[] s = input.substring(input.indexOf(':')+2).split("[;, ]+");
                    HashMap<String, Integer> map = new HashMap<>();
                    for (int i = 0; i < s.length; i += 2) {
                        int n = Integer.parseInt(s[i]);
                        map.compute(s[i+1], (key, oldVal) -> oldVal == null ? n : Math.max(oldVal, n));
                    }
                    return map.values().stream().reduce(1, (n0, n1) -> n0*n1);
                })
                .reduce(0, Integer::sum);

        System.out.println(sum);
    }
}