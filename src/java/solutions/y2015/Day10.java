package solutions.y2015;

import solutions.Solution;

import java.nio.file.Files;

public class Day10 extends Solution {
    public static void main(String[] args) throws Exception {
        Day10 day1 = new Day10();
        day1.part1();
        day1.part2();
    }

    @Override
    public void part1() throws Exception {
        String s = Files.readString(this.inputFile());
        for (int i = 0; i < 40; i++) {
            s = lookAndSay(s);
        }
        System.out.println(s.length());
    }

    @Override
    public void part2() throws Exception {
        String s = Files.readString(this.inputFile());
        for (int i = 0; i < 50; i++) {
            s = lookAndSay(s);
        }
        System.out.println(s.length());
    }

    private static String lookAndSay(String s) {
        StringBuilder sb = new StringBuilder();
        int n = 0;
        char p = s.charAt(0);
        for (char c : s.toCharArray()) {
            if (c == p) {
                n++;
            } else {
                sb.append(n).append(p);
                p = c;
                n = 1;
            }
        }
        return sb.append(n).append(p).toString();
    }
}