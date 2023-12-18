package solutions.y2023;

import solutions.Solution;

import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Function;

public class Day9 extends Solution {
    public static void main(String[] args) throws Exception {
        Day9 day9 = new Day9();
        day9.part1();
        day9.part2();
    }

    @Override
    public void part1() throws Exception {
        System.out.println(getExtraNum(Day9::nextExtraNum));
    }

    @Override
    public void part2() throws Exception {
        System.out.println(getExtraNum(Day9::prevExtraNum));
    }

    private long getExtraNum(Function<long[], Long> extraNumFun) throws IOException {
        return Files.readAllLines(this.inputFile())
                .stream()
                .map(input -> {
                    String[] s = input.split(" ");
                    long[] nums = new long[s.length];
                    for (int i = 0; i < s.length; i++) {
                        nums[i] = Long.parseLong(s[i]);
                    }
                    return extraNumFun.apply(nums);
                })
                .reduce(0L, Long::sum);
    }

    private static boolean allZero(long[] nums) {
        for (long num : nums) {
            if (num != 0) return false;
        }
        return true;
    }

    private static long nextExtraNum(long[] nums) {
        long[] prev = new long[nums.length - 1];
        for (int i = 0; i < nums.length - 1; i++) {
            prev[i] = nums[i + 1] - nums[i];
        }

        return allZero(prev)
                ? nums[nums.length - 1]
                : nums[nums.length - 1] + nextExtraNum(prev);
    }

    private static long prevExtraNum(long[] nums) {
        long[] prev = new long[nums.length - 1];
        for (int i = 0; i < nums.length - 1; i++) {
            prev[i] = nums[i + 1] - nums[i];
        }

        return allZero(prev)
                ? nums[0]
                : nums[0] - prevExtraNum(prev);
    }
}
