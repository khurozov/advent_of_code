package solutions.y2015;

import solutions.Solution;

import java.nio.file.Files;

public class Day1 extends Solution {
    public static void main(String[] args) throws Exception {
        Day1 day1 = new Day1();
        day1.part1();
        day1.part2();
    }

    @Override
    public void part1() throws Exception {
        String s = Files.readString(this.inputFile());
        int n = 0;

        for (char c : s.toCharArray()) {
            switch (c) {
                case '(' -> ++n;
                case ')' -> --n;
            }
        }

        System.out.println(n);
    }

    @Override
    public void part2() throws Exception {
        String s = Files.readString(this.inputFile());
        char[] c = s.toCharArray();
        int n = 0;
        int i;

        for (i = 0; i < c.length; i++) {
            switch (c[i]) {
                case '(' -> ++n;
                case ')' -> --n;
            }

            if (n < 0) break;
        }

        System.out.println(i+1);
    }
}
