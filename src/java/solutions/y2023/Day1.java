package solutions.y2023;

import extra.Util;
import solutions.Solution;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Day1 extends Solution {
    public static void main(String[] args) throws Exception {
        Day1 day1 = new Day1();
        day1.part1();
        day1.part2();
    }

    @Override
    public void part1() throws Exception {
        System.out.println(calibrationValuesSum(input -> {
            char[] chars = input.toCharArray();
            int n = 0;
            for (char c : chars) {
                if (Util.isDigit(c)) {
                    n += (c - '0') * 10;
                    break;
                }
            }
            for (int i = chars.length - 1; i >= 0; --i) {
                if (Util.isDigit(chars[i])) {
                    n += chars[i] - '0';
                    break;
                }
            }

            return n;
        }));
    }

    @Override
    public void part2() throws Exception {
        Map<String, Integer> map = new HashMap<>();
        map.put("seven", 7);
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);
        map.put("five", 5);
        map.put("six", 6);
        map.put("eight", 8);
        map.put("nine", 9);
        for (int i = 1; i < 10; i++) {
            map.put(i + "", i);
        }

        System.out.println(calibrationValuesSum(input -> {
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
        }));
    }

    private int calibrationValuesSum(Function<String, Integer> calibrationValueReader) throws IOException {
        return Files.readAllLines(this.inputFile())
                .stream()
                .map(calibrationValueReader)
                .reduce(0, Integer::sum);
    }
}