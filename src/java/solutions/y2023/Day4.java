package solutions.y2023;

import solutions.Solution;

import java.nio.file.Files;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Day4 extends Solution {
    public static void main(String[] args) throws Exception {
        Day4 day4 = new Day4();
        day4.part1();
        day4.part2();
    }

    @Override
    public void part1() throws Exception {
        long sum = Files.readAllLines(this.inputFile())
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

    @Override
    public void part2() throws Exception {
        long sum = Files.readAllLines(this.inputFile())
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
