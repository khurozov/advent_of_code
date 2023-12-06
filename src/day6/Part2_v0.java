package day6;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Part2_v0 {
    // brute force
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

        int n = 0;
        for (int i = 1; i < t; i++) {
            if ((t-i)*i > d) n++;
        }
        System.out.println(n);
    }
}
