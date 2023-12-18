package solutions.y2023;

import solutions.Solution;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.function.Function;

public class Day2 extends Solution {
    public static void main(String[] args) throws Exception {
        Day2 day2 = new Day2();
        day2.part1();
        day2.part2();
    }

    @Override
    public void part1() throws Exception {
        HashMap<String, Integer> limitByColor = new HashMap<>();
        limitByColor.put("red", 12);
        limitByColor.put("green", 13);
        limitByColor.put("blue", 14);

        System.out.println(getSum(input -> {
            String[] s = input.substring(5).split("[:;, ]+");
            for (int i = 1; i < s.length; i += 2) {
                if (limitByColor.get(s[i + 1]) < Integer.parseInt(s[i])) {
                    return 0;
                }
            }
            return Integer.parseInt(s[0]);
        }));
    }

    @Override
    public void part2() throws Exception {
        System.out.println(getSum(input -> {
            String[] s = input.substring(input.indexOf(':') + 2).split("[;, ]+");
            HashMap<String, Integer> map = new HashMap<>();
            for (int i = 0; i < s.length; i += 2) {
                int n = Integer.parseInt(s[i]);
                map.compute(s[i + 1], (key, oldVal) -> oldVal == null ? n : Math.max(oldVal, n));
            }
            return map.values().stream().reduce(1, (n0, n1) -> n0 * n1);
        }));
    }

    private int getSum(Function<String, Integer> gameToAddendFunc) throws IOException {
        return Files.readAllLines(this.inputFile())
                .stream()
                .map(gameToAddendFunc)
                .reduce(0, Integer::sum);
    }
}