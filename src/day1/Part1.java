package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Part1 {
    public static void main(String[] args) throws IOException {
        int sum = Files.readAllLines(Path.of("src", "day1", "input"))
                .stream()
                .map(input -> {
                    char[] chars = input.toCharArray();
                    int n = 0;
                    for (char c : chars) {
                        if (isDigit(c)) {
                            n += (c - '0') * 10;
                            break;
                        }
                    }
                    for (int i = chars.length - 1; i >= 0; --i) {
                        if (isDigit(chars[i])) {
                            n += chars[i] - '0';
                            break;
                        }
                    }

                    return n;
                })
                .reduce(0, Integer::sum);

        System.out.println(sum);
    }

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
}