package day3;

import java.util.HashSet;

class Util {
    static boolean isDigit(char x) {
        return x >= '0' && x <= '9';
    }

    static int firstLeftNonDigitIndex(char[] row, int x) {
        for (int i = x - 1; i >= 0; i--) {
            if (!isDigit(row[i])) {
                return i;
            }
        }

        return -1;
    }

    static int firstRightNonDigitIndex(char[] row, int x) {
        for (int i = x + 1; i < row.length; i++) {
            if (!isDigit(row[i])) {
                return i;
            }
        }

        return row.length;
    }

    static int getInt(char[] row, int before, int after) {
        int n = 0;
        for (int i = before + 1; i < after; i++) {
            n = n * 10 + row[i] - '0';
        }

        return n;
    }

    static int numCenter(char[][] c, int x, int y, HashSet<String> set) {
        int before = Util.firstLeftNonDigitIndex(c[y], x);
        int after = Util.firstRightNonDigitIndex(c[y], x);
        String key = y + ":" + before;
        if (!set.contains(key)) {
            set.add(key);
            return Util.getInt(c[y], before, after);
        }
        return 0;
    }

    static int numLeft(char[][] c, int x, int y, HashSet<String> set) {
        int before = Util.firstLeftNonDigitIndex(c[y], x);
        String key = y + ":" + before;
        if (!set.contains(key)) {
            set.add(key);
            return Util.getInt(c[y], before, x + 1);
        }
        return 0;
    }

    static int numRight(char[][] c, int x, int y, HashSet<String> set) {
        int after = Util.firstRightNonDigitIndex(c[y], x);
        String key = y + ":" + x;
        if (!set.contains(key)) {
            set.add(key);
            return Util.getInt(c[y], x - 1, after);
        }
        return 0;
    }
}
