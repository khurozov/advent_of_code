package solutions.y2015;

import solutions.Solution;

import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5 extends Solution {
    public static void main(String[] args) throws Exception {
        Day5 day1 = new Day5();
        day1.part1();
        day1.part2();
    }

    @Override
    public void part1() throws Exception {
        System.out.println(niceStringCount(s -> {
            if (s.contains("ab") || s.contains("cd") || s.contains("pq") || s.contains("xy")) {
                return false;
            }

            Matcher matcher = Pattern.compile("[aeiou]").matcher(s);
            int n = 0;
            while (matcher.find() && n < 3) {
                n++;
            }
            if (n < 3) {
                return false;
            }

            return Pattern.compile("([a-z])\\1").matcher(s).find();
        }));
    }

    @Override
    public void part2() throws Exception {
        System.out.println(niceStringCount(s ->
                Pattern.compile("([a-z]{2})[a-z]*\\1").matcher(s).find()
                && Pattern.compile("([a-z])[a-z]\\1").matcher(s).find()
        ));
    }

    private long niceStringCount(Predicate<String> isNice) throws IOException {
        return Files.readAllLines(this.inputFile()).stream()
                .filter(isNice)
                .count();
    }
}
