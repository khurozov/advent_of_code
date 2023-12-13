import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class Day13 {
    public static void main(String[] args) throws IOException {
        part1();
        part2();
    }

    private static void part1() throws IOException {
        System.out.println(detectMirrors(0));
    }

    private static void part2() throws IOException {
        System.out.println(detectMirrors(1));
    }

    private static int detectMirrors(int maxErr) throws IOException {
        Scanner in = new Scanner(Path.of("src", "Day13.txt"));
        int sum = 0;
        LinkedList<String> list = new LinkedList<>();

        while (in.hasNextLine()) {
            String s = in.nextLine();
            if (s.isBlank()) {
                if (list.isEmpty()) continue;
                char[][] p = parsePattern(list);
                sum += getMirrors(p, maxErr);
                list.clear();
            } else {
                list.add(s);
            }
        }
        return sum;
    }

    private static char[][] parsePattern(LinkedList<String> list){
        char[][] a = new char[list.size()][];

        for (int i = 0; i < list.size(); i++) {
            a[i] = list.get(i).toCharArray();
        }
        return a;
    }

    private static int getMirrors(char[][] p, int maxErr) {
        int n;

        n = getFlopMirror(p, 1, maxErr, new HashSet<>());
        if (n > 0) {
            return n * 100;
        }

        n = getFlipMirror(p, 1, maxErr, new HashSet<>());

        return Math.max(n, 0);
    }

    private static int getFlopMirror(char[][] a, int i, int maxErr, HashSet<Integer> mem) {
        if (i >= a.length) return 0;
        if (mem.contains(i)) return 0;

        int n = isFlopMirror(a, i, maxErr) ? i : getFlopMirror(a, i + 1, maxErr, mem);

        mem.add(i);
        return n;
    }

    private static int getFlipMirror(char[][] a, int j, int maxErr, HashSet<Integer> mem) {
        if (j >= a[0].length) return 0;
        if (mem.contains(j)) return 0;

        int n = isFlipMirror(a, j, maxErr) ? j : getFlipMirror(a, j + 1, maxErr, mem);

        mem.add(j);
        return n;
    }

    private static boolean isFlopMirror(char[][] a, int x, int maxErr) {
        int n = Math.min(x, a.length - x);
        int m = 0;

        for (int i = 0; i < n && m <= maxErr; i++) {
            for (int j = 0; j < a[0].length && m <= maxErr; j++) {
                if (a[x - i - 1][j] != a[x + i][j]) {
                    m++;
                }
            }
        }
        return m == maxErr;
    }

    private static boolean isFlipMirror(char[][] a, int x, int maxErr) {
        int n = Math.min(x, a[0].length - x);
        int m = 0;

        for (char[] row : a) {
            for (int j = 0; j < n && m <= maxErr; j++) {
                if (row[x - j - 1] != row[x + j]) {
                    m++;
                }
            }
        }
        return m == maxErr;
    }
}
