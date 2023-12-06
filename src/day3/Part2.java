package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

public class Part2 {
    public static void main(String[] args) throws IOException {
        char[][] c = Files.readAllLines(Path.of("src", "day3", "input"))
                .stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);

        HashSet<String> set = new HashSet<>();
        int sum = 0;

        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].length; j++) {
                if (c[i][j] == '*') {
                    int first = -1;
                    //top
                    if (i > 0) {
                        if (Util.isDigit(c[i - 1][j])) {
                            first = Util.numCenter(c, j, i - 1, set);
                        } else {
                            if (j > 0 && Util.isDigit(c[i - 1][j - 1])) {
                                first = Util.numLeft(c, j - 1, i - 1, set);
                            }
                            if (j + 1 < c[i - 1].length && Util.isDigit(c[i - 1][j + 1])) {
                                int num = Util.numRight(c, j + 1, i - 1, set);
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
                    if (j > 0 && Util.isDigit(c[i][j - 1])) {
                        int num = Util.numLeft(c, j - 1, i, set);
                        if (first == -1) {
                            first = num;
                        } else {
                            sum += first * num;
                            continue;
                        }
                    }
                    //right
                    if (j + 1 < c[i].length && Util.isDigit(c[i][j + 1])) {
                        int num = Util.numRight(c, j + 1, i, set);
                        if (first == -1) {
                            first = num;
                        } else {
                            sum += first * num;
                            continue;
                        }
                    }
                    // bottom
                    if (i + 1 < c.length) {
                        if (Util.isDigit(c[i + 1][j])) {
                            int num = Util.numCenter(c, j, i + 1, set);
                            if (first != -1) {
                                sum += first * num;
                            }
                        } else {
                            if (j > 0 && Util.isDigit(c[i + 1][j - 1])) {
                                int num = Util.numLeft(c, j - 1, i + 1, set);
                                if (first == -1) {
                                    first = num;
                                } else {
                                    sum += first * num;
                                    continue;
                                }
                            }
                            if (j + 1 < c[i + 1].length && Util.isDigit(c[i + 1][j + 1])) {
                                int num = Util.numRight(c, j + 1, i + 1, set);
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

}
