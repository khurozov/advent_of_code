package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Part2 {
    public static void main(String[] args) throws IOException {
        long sum = Files.readAllLines(Path.of("src", "day4", "input"))
                .stream()
                .map(input -> {
                    String[] parts = input.split("[: ]+", 3);
                    long card = Long.parseLong(parts[1]);
                    String[] nums = parts[2].split(" \\| +");
                    HashSet<String> winningNums = new HashSet<>(List.of(nums[0].split(" +")));
                    long n = Stream.of(nums[1].split(" +"))
                            .filter(winningNums::contains)
                            .count();

                    TreeMap<Long, Long> map = new TreeMap<>();
                    for (long i = 0; i <= n; i++) {
                        map.put(card + i, 0L);
                    }

                    return map;
                })
                .reduce(new TreeMap<>(), (map, mapNext) -> {
                    Iterator<Long> iter = mapNext.keySet().iterator();

                    if (iter.hasNext()) {
                        Long i = iter.next();
                        long x = map.compute(i, (key, oldVal) -> oldVal == null ? 1 : oldVal + 1);

                        while (iter.hasNext()) {
                            map.compute(iter.next(), (key, oldVal) -> oldVal == null ? x : oldVal + x);
                        }
                    }

                    return map;
                })
                .values().stream()
                .reduce(0L, Long::sum);

        System.out.println(sum);
    }
}
