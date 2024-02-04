package solutions.y2015;

import solutions.Solution;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.function.Function;

public class Day4 extends Solution {
    public static void main(String[] args) throws Exception {
        Day4 day1 = new Day4();
        day1.part1();
        day1.part2();
    }

    @Override
    public void part1() throws Exception {
        System.out.println(solve(bytes -> bytes[0] == 0 && bytes[1] == 0 && bytes[2] < 16 && bytes[2] > -1));
    }

    @Override
    public void part2() throws Exception {
        System.out.println(solve(bytes -> bytes[0] == 0 && bytes[1] == 0 && bytes[2] == 0));
    }

    private int solve(Function<byte[], Boolean> hasFound) throws IOException {
        String s = new Scanner(this.inputFile()).next();
        int i = 0;
        while (true) {
            i++;
            if (hasFound.apply(md5(s + i))) return i;
        }
    }

    private static byte[] md5(String s) {
        try {
            return MessageDigest.getInstance("MD5").digest(s.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
