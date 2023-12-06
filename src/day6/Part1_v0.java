package day6;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Part1_v0 {
    // brute force
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
            int n = 0;
            for (Integer j = 1; j < t.get(i); j++) {
                if ((t.get(i)-j)*j > d.get(i)) n++;
            }
            mul *= n;
        }
        System.out.println(mul);
    }
}
