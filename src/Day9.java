import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

public class Day9 {
    public static void main(String[] args) throws IOException {
        part1();
        part2();
    }

    private static void part1() throws IOException {
        System.out.println(getExtraNum(Day9::nextExtraNum));
    }

    private static void part2() throws IOException {
        System.out.println(getExtraNum(Day9::prevExtraNum));
    }

    private static long getExtraNum(Function<long[], Long> extraNumFun) throws IOException {
        return Files.readAllLines(Path.of("src", "Day9.txt"))
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
