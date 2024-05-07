package solutions.y2015;

import solutions.Solution;

import java.nio.file.Files;
import java.util.List;
import java.util.OptionalInt;
import java.util.TreeSet;

public class Day11 extends Solution {
    private final TreeSet<String> s223;
    private final TreeSet<String> s232;
    private final TreeSet<String> s322;
    private static final List<Character> deniedChars = List.of('i', 'o', 'l');
    public static void main(String[] args) throws Exception {
        Day11 day11 = new Day11();
        day11.part1();
        day11.part2();
    }

    public Day11() {
        s223 = new TreeSet<>();
        s232 = new TreeSet<>();
        s322 = new TreeSet<>();

        for (int i = 0; i < 24; i++) {
            char[] c = new char[4];
            for (int j = 0; j < 4; j++) {
                c[j] = (char) ('a'+i+j);
            }
            if (deniedChars.contains(c[0]) || deniedChars.contains(c[1]) || deniedChars.contains(c[2])) {
                continue;
            }
            s232.add(""+c[0]+c[0]+c[1]+c[2]+c[2]);

            if (deniedChars.contains(c[3])) continue;

            s223.add(""+c[0]+c[0]+c[1]+c[1]+c[2]+c[3]);
            s322.add(""+c[0]+c[1]+c[2]+c[2]+c[3]+c[3]);
        }
    }

    @Override
    public void part1() throws Exception {
        StringBuilder sb = new StringBuilder(Files.readString(this.inputFile()));
        nextPassword(sb);
        System.out.println(sb);
    }

    @Override
    public void part2() throws Exception {
        StringBuilder sb = new StringBuilder(Files.readString(this.inputFile()));
        nextPassword(sb);
        incCharAt(sb, 7);
        nextPassword(sb);
        System.out.println(sb);
    }

    private static void changeIfContainsDeniedChars(StringBuilder sb, List<Character> deniedChars) {
        OptionalInt min = deniedChars.stream()
                .mapToInt(c -> sb.indexOf(c.toString()))
                .filter(n -> n != -1)
                .min();

        if (min.isEmpty()) return;

        int n = min.getAsInt();
        incCharAt(sb, n);
        n++;
        while (n < sb.length()) {
            sb.setCharAt(n++, 'a');
        }

        changeIfContainsDeniedChars(sb, deniedChars);
    }

    private static void incCharAt(StringBuilder sb, int i) {
        if (i < 0) return;

        char c = sb.charAt(i);
        c++;

        if (c > 'z') {
            sb.setCharAt(i, 'a');
            incCharAt(sb, i-1);
        } else {
            sb.setCharAt(i, c);
        }
    }

    private void nextPassword(StringBuilder sb) {
        changeIfContainsDeniedChars(sb, deniedChars);
        String s = s232.ceiling(sb.substring(3));
        if (s != null) {
            sb.replace(3, 8, s);
            return;
        }

        String tmp = sb.toString();

        incCharAt(sb, 2);
        sb.replace(3, 8, "aaaaa");
        s = s232.ceiling(sb.substring(3));
        if (s != null) {
            sb.replace(3, 8, s);
            return;
        }

        sb.replace(0, 8, tmp);

        String s1 = s223.ceiling(sb.substring(2));
        String s2 = s322.ceiling(sb.substring(2));

        if (s1 == null) {
            s = s2;
        } else if (s2 == null) {
            s = s1;
        } else {
            s = s1.compareTo(s2) < 0 ? s1 : s2;
        }

        if (s == null) return;

        sb.replace(2, 8, s);
    }
}