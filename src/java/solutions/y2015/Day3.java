package solutions.y2015;

import extra.Pair;
import solutions.Solution;

import java.nio.file.Files;
import java.util.HashSet;

public class Day3 extends Solution {
    public static void main(String[] args) throws Exception {
        Day3 day3 = new Day3();
        day3.part1();
        day3.part2();
    }

    @Override
    public void part1() throws Exception {
        char[] path = Files.readString(this.inputFile()).toCharArray();
        int i = 0;
        int j = 0;
        HashSet<Pair<Integer>> houses = new HashSet<>();
        houses.add(new Pair<>(i, j));

        for (char p : path) {
            switch (p) {
                case '>' -> ++j;
                case '<' -> --j;
                case '^' -> --i;
                case 'v' -> ++i;
            }
            houses.add(new Pair<>(i, j));
        }

        System.out.println(houses.size());
    }

    @Override
    public void part2() throws Exception {
        char[] path = Files.readString(this.inputFile()).toCharArray();
        int si = 0;
        int sj = 0;
        int ri = 0;
        int rj = 0;

        HashSet<Pair<Integer>> houses = new HashSet<>();
        houses.add(new Pair<>(si,sj));

        int n = path.length / 2;

        for (int i = 0; i < n; i++) {
            switch (path[2*i]) {
                case '>' -> ++sj;
                case '<' -> --sj;
                case '^' -> --si;
                case 'v' -> ++si;
            }
            houses.add(new Pair<>(si, sj));
            switch (path[2*i+1]) {
                case '>' -> ++rj;
                case '<' -> --rj;
                case '^' -> --ri;
                case 'v' -> ++ri;
            }
            houses.add(new Pair<>(ri, rj));
        }

        System.out.println(houses.size());
    }
}
