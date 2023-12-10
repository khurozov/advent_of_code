package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Part2 {
    public static void main(String[] args) throws IOException {
        Map<String, Integer> map = str2IntMap();

        int sum = Files.readAllLines(Path.of("src", "day1", "input"))
                .stream()
                .map(input -> {
                    int left = Integer.MAX_VALUE, right = Integer.MIN_VALUE;
                    String first = "", last = "";
                    for (String num : map.keySet()) {
                        int l = input.indexOf(num);
                        int r = input.lastIndexOf(num);

                        if (l >= 0 && l < left) {
                            left = l;
                            first = num;
                        }
                        if (r >= 0 && r > right) {
                            right = r;
                            last = num;
                        }
                    }
                    return map.getOrDefault(first, 0) * 10 + map.getOrDefault(last, 0);
                })
                .reduce(0, Integer::sum);

        System.out.println(sum);
    }

    private static Map<String, Integer> str2IntMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        map.put("4", 4);
        map.put("5", 5);
        map.put("6", 6);
        map.put("7", 7);
        map.put("8", 8);
        map.put("9", 9);
        map.put("seven", 7);
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);
        map.put("five", 5);
        map.put("six", 6);
        map.put("eight", 8);
        map.put("nine", 9);
        return map;
    }
}