package day6;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Part1_v1 {
    // equation: -x^2+tx-d>0
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(Path.of("src", "day6", "input"));
        ArrayList<Integer> t = new ArrayList<>();
        ArrayList<Integer> d = new ArrayList<>();

        in.next();
        while (in.hasNextInt()) {
            t.add(in.nextInt());
        }

        in.next();
        while (in.hasNextInt()) {
            d.add(in.nextInt());
        }

        int mul = 1;
        for (int i = 0; i < t.size(); i++) {
            double sqrt = Math.sqrt(Math.pow(t.get(i), 2) - 4 * d.get(i));
            int left = (int) Math.ceil((t.get(i)-sqrt)/2);
            int right = (int) Math.floor((t.get(i)+sqrt)/2);
            mul *= (right-left+1);
        }
        System.out.println(mul);
    }
}
