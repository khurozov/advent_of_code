package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Part1 {
    public static void main(String[] args) throws IOException {
        HashMap<String, Integer> limitByColor = new HashMap<>();
        limitByColor.put("red", 12);
        limitByColor.put("green", 13);
        limitByColor.put("blue", 14);

        Integer sum = Files.readAllLines(Path.of("src", "day2", "input"))
                .stream()
                .map(input -> {
                    String[] s = input.substring(5).split("[:;, ]+");
                    for (int i = 1; i < s.length; i += 2) {
                        if (limitByColor.get(s[i + 1]) < Integer.parseInt(s[i])) {
                            return 0;
                        }
                    }
                    return Integer.parseInt(s[0]);
                })
                .reduce(0, Integer::sum);

        System.out.println(sum);
    }
}