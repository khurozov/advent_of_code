package solutions.y2015;

import solutions.Solution;

import java.nio.file.Files;

public class Day8 extends Solution {
    public static void main(String[] args) throws Exception {
        Day8 day1 = new Day8();
        day1.part1();
        day1.part2();
    }

    @Override
    public void part1() throws Exception {
        System.out.println(Files.readAllLines(this.inputFile()).stream()
                .map(s -> 2
                        + s.length()
                        - s
                        .replaceAll("\\\\\\\\", "*")
                        .replaceAll("\\\\\"", "-")
                        .replaceAll("\\\\x[0-9a-f]{2}", "+")
                        .length()
                )
                .reduce(0, Integer::sum));
    }

    @Override
    public void part2() throws Exception {
        System.out.println(Files.readAllLines(this.inputFile()).stream()
                .map(s -> 2
                        -s.length()
                        + s
                        .replaceAll("\\\\", "\\\\\\\\")
                        .replaceAll("\"", "\\\\\"")
                        .length()
                )
                .reduce(0, Integer::sum));
    }
}