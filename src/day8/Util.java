package day8;

import java.util.HashMap;

class Util {
    static long steps(char[] dirs, HashMap<String, Pair> map, String start, String end) {
        String current = start;
        long n = 0;
        while (!current.endsWith(end)) {
            Pair p = map.get(current);
            current = dirs[(int) (n % dirs.length)] == 'L' ? p.left() : p.right();

            n++;
        }
        return n;
    }

    static long lcm(long n1, long n2) {
        long max = Math.max(n1, n2);
        long min = Math.min(n1, n2);
        long lcm = max;
        while (lcm % min != 0) {
            lcm += max;
        }
        return lcm;
    }
}
