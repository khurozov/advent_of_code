package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

public class Part1 {
    public static void main(String[] args) throws IOException {
        long sum = Files.readAllLines(Path.of("src", "day4", "input"))
                .stream()
                .map(input -> {
                    String[] all = input.substring(10).split(" \\| ");
                    HashSet<String> winningNumbers = new HashSet<>(List.of(all[0].trim().split(" +")));
                    long n = Stream.of(all[1].trim().split(" +"))
                            .filter(winningNumbers::contains)
                            .count();
                    return n == 0 ? 0L : (long) Math.pow(2, n - 1);
                })
                .reduce(0L, Long::sum);
        System.out.println(sum);
    }
}
