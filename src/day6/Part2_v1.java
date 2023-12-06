package day6;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Part2_v1 {
    // equation: -x^2+tx-d>0
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(Path.of("src", "day6", "input"));
        StringBuilder sb;

        sb = new StringBuilder();
        in.next();
        while (in.hasNextInt()) {
            sb.append(in.nextInt());
        }
        long t = Long.parseLong(sb.toString());

        sb = new StringBuilder();
        in.next();
        while (in.hasNextInt()) {
            sb.append(in.nextInt());
        }
        long d = Long.parseLong(sb.toString());

        double sqrt = Math.sqrt(Math.pow(t, 2) - 4 * d);
        int left = (int) Math.ceil((t-sqrt)/2);
        int right = (int) Math.floor((t+sqrt)/2);
        System.out.println(right-left+1);
    }
}
