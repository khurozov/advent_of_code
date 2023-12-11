import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

public class Day3 {
    public static void main(String[] args) throws IOException {
        part1();
        part2();
    }

    private static void part1() throws IOException {
        char[][] c = readInput();

        HashSet<String> set = new HashSet<>();
        int sum = 0;

        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].length; j++) {
                if (c[i][j] != '.' && !isDigit(c[i][j])) {
                    //top
                    if (i > 0) {
                        if (isDigit(c[i - 1][j])) {
                            sum += numCenter(c, j, i - 1, set);
                        } else {
                            if (j > 0 && isDigit(c[i - 1][j - 1])) {
                                sum += numLeft(c, j - 1, i - 1, set);
                            }
                            if (j + 1 < c[i - 1].length && isDigit(c[i - 1][j + 1])) {
                                sum += numRight(c, j + 1, i - 1, set);
                            }
                        }
                    }
                    //left
                    if (j > 0 && isDigit(c[i][j - 1])) {
                        sum += numLeft(c, j - 1, i, set);
                    }
                    //right
                    if (j + 1 < c[i].length && isDigit(c[i][j + 1])) {
                        sum += numRight(c, j + 1, i, set);
                    }
                    // bottom
                    if (i + 1 < c.length) {
                        if (isDigit(c[i + 1][j])) {
                            sum += numCenter(c, j, i + 1, set);
                        } else {
                            if (j > 0 && isDigit(c[i + 1][j - 1])) {
                                sum += numLeft(c, j - 1, i + 1, set);
                            }
                            if (j + 1 < c[i + 1].length && isDigit(c[i + 1][j + 1])) {
                                sum += numRight(c, j + 1, i + 1, set);
                            }
                        }
                    }
                }
            }
        }

        System.out.println(sum);
    }

    private static void part2() throws IOException {
        char[][] c = readInput();

        HashSet<String> set = new HashSet<>();
        int sum = 0;

        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].length; j++) {
                if (c[i][j] == '*') {
                    int first = -1;
                    //top
                    if (i > 0) {
                        if (isDigit(c[i - 1][j])) {
                            first = numCenter(c, j, i - 1, set);
                        } else {
                            if (j > 0 && isDigit(c[i - 1][j - 1])) {
                                first = numLeft(c, j - 1, i - 1, set);
                            }
                            if (j + 1 < c[i - 1].length && isDigit(c[i - 1][j + 1])) {
                                int num = numRight(c, j + 1, i - 1, set);
                                if (first == -1) {
                                    first = num;
                                } else {
                                    sum += first * num;
                                    continue;
                                }
                            }
                        }
                    }
                    //left
                    if (j > 0 && isDigit(c[i][j - 1])) {
                        int num = numLeft(c, j - 1, i, set);
                        if (first == -1) {
                            first = num;
                        } else {
                            sum += first * num;
                            continue;
                        }
                    }
                    //right
                    if (j + 1 < c[i].length && isDigit(c[i][j + 1])) {
                        int num = numRight(c, j + 1, i, set);
                        if (first == -1) {
                            first = num;
                        } else {
                            sum += first * num;
                            continue;
                        }
                    }
                    // bottom
                    if (i + 1 < c.length) {
                        if (isDigit(c[i + 1][j])) {
                            int num = numCenter(c, j, i + 1, set);
                            if (first != -1) {
                                sum += first * num;
                            }
                        } else {
                            if (j > 0 && isDigit(c[i + 1][j - 1])) {
                                int num = numLeft(c, j - 1, i + 1, set);
                                if (first == -1) {
                                    first = num;
                                } else {
                                    sum += first * num;
                                    continue;
                                }
                            }
                            if (j + 1 < c[i + 1].length && isDigit(c[i + 1][j + 1])) {
                                int num = numRight(c, j + 1, i + 1, set);
                                if (first != -1) {
                                    sum += first * num;
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println(sum);
    }

    static char[][] readInput() throws IOException {
        return Files.readAllLines(Path.of("src", "Day3.txt"))
                .stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

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
        int before = firstLeftNonDigitIndex(c[y], x);
        int after = firstRightNonDigitIndex(c[y], x);
        String key = y + ":" + before;
        if (!set.contains(key)) {
            set.add(key);
            return getInt(c[y], before, after);
        }
        return 0;
    }

    static int numLeft(char[][] c, int x, int y, HashSet<String> set) {
        int before = firstLeftNonDigitIndex(c[y], x);
        String key = y + ":" + before;
        if (!set.contains(key)) {
            set.add(key);
            return getInt(c[y], before, x + 1);
        }
        return 0;
    }

    static int numRight(char[][] c, int x, int y, HashSet<String> set) {
        int after = firstRightNonDigitIndex(c[y], x);
        String key = y + ":" + x;
        if (!set.contains(key)) {
            set.add(key);
            return getInt(c[y], x - 1, after);
        }
        return 0;
    }
}
