package day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Part2 {
    public static void main(String[] args) throws IOException {
        long sum = Files.readAllLines(Path.of("src", "day9", "input"))
                .stream()
                .map(input -> {
                    String[] s = input.split(" ");
                    long[] nums = new long[s.length];
                    for (int i = 0; i < s.length; i++) {
                        nums[i] = Long.parseLong(s[i]);
                    }
                    return Util.prevExtraNum(nums);
                })
                .reduce(0L, Long::sum);
        System.out.println(sum);
    }
}
