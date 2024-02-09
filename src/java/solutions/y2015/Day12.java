package solutions.y2015;

import solutions.Solution;

import java.nio.file.Files;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Day12 extends Solution {
    public static void main(String[] args) throws Exception {
        Day12 day1 = new Day12();
        day1.part1();
        day1.part2();
    }

    @Override
    public void part1() throws Exception {
        System.out.println(numberSum(Files.readString(this.inputFile())));
    }

    @Override
    public void part2() throws Exception {
        String s = Files.readString(this.inputFile());
        Pattern curlyPattern = Pattern.compile("\\{[^\\[\\]{}]+}");
        Pattern squarePattern = Pattern.compile("\\[[^\\[\\]{}]+]");
        while (s.contains("[") || s.contains("{")) {
            s = curlyPattern.matcher(s).replaceAll(mr -> mr.group().contains(":\"red\"") ? "0" : ""+numberSum(mr.group()));
            s = squarePattern.matcher(s).replaceAll(mr -> ""+numberSum(mr.group()));
        }
        System.out.println(s);
    }

    private static int numberSum(String s) {
        return Pattern.compile("-?\\d+")
                .matcher(s)
                .results()
                .map(MatchResult::group)
                .mapToInt(Integer::parseInt)
                .sum();
    }
}