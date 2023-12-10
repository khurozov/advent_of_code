package day9;

class Util {

    private static boolean allZero(long[] nums) {
        for (long num : nums) {
            if (num != 0) return false;
        }
        return true;
    }

    static long nextExtraNum(long[] nums) {
        long[] prev = new long[nums.length - 1];
        for (int i = 0; i < nums.length - 1; i++) {
            prev[i] = nums[i + 1] - nums[i];
        }

        return allZero(prev)
                ? nums[nums.length - 1]
                : nums[nums.length - 1] + nextExtraNum(prev);
    }

    static long prevExtraNum(long[] nums) {
        long[] prev = new long[nums.length - 1];
        for (int i = 0; i < nums.length - 1; i++) {
            prev[i] = nums[i + 1] - nums[i];
        }

        return allZero(prev)
                ? nums[0]
                : nums[0] - prevExtraNum(prev);
    }
}
