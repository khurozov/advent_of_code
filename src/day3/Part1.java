package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

public class Part1 {
    public static void main(String[] args) throws IOException {
        char[][] c = Files.readAllLines(Path.of("src", "day3", "input"))
                .stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);

        HashSet<String> set = new HashSet<>();
        int sum = 0;

        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].length; j++) {
                if (c[i][j] != '.' && !Util.isDigit(c[i][j])) {
                    //top
                    if (i > 0) {
                        if (Util.isDigit(c[i - 1][j])) {
                            sum += Util.numCenter(c, j, i - 1, set);
                        } else {
                            if (j > 0 && Util.isDigit(c[i - 1][j - 1])) {
                                sum += Util.numLeft(c, j - 1, i - 1, set);
                            }
                            if (j + 1 < c[i - 1].length && Util.isDigit(c[i - 1][j + 1])) {
                                sum += Util.numRight(c, j + 1, i - 1, set);
                            }
                        }
                    }
                    //left
                    if (j > 0 && Util.isDigit(c[i][j - 1])) {
                        sum += Util.numLeft(c, j - 1, i, set);
                    }
                    //right
                    if (j + 1 < c[i].length && Util.isDigit(c[i][j + 1])) {
                        sum += Util.numRight(c, j + 1, i, set);
                    }
                    // bottom
                    if (i + 1 < c.length) {
                        if (Util.isDigit(c[i + 1][j])) {
                            sum += Util.numCenter(c, j, i + 1, set);
                        } else {
                            if (j > 0 && Util.isDigit(c[i + 1][j - 1])) {
                                sum += Util.numLeft(c, j - 1, i + 1, set);
                            }
                            if (j + 1 < c[i + 1].length && Util.isDigit(c[i + 1][j + 1])) {
                                sum += Util.numRight(c, j + 1, i + 1, set);
                            }
                        }
                    }
                }
            }
        }

        System.out.println(sum);
    }
}
