package day5;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Part2 {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(Path.of("src", "day5", "input"));
        LinkedList<Pair> seeds = new LinkedList<>();

        in.next();
        while (in.hasNextLong()) {
            long seed = in.nextLong();
            long len = in.nextLong();
            seeds.add(new Pair(seed, seed + len - 1));
        }

        long min = getDestinations(
                in,
                getDestinations(
                    in,
                    getDestinations(
                        in,
                        getDestinations(
                            in,
                            getDestinations(
                                in,
                                getDestinations(
                                    in,
                                    getDestinations(
                                        in,
                                        seeds
                                    ) // seed-to-soil
                                ) // soil-to-fertilizer
                            ) // fertilizer-to-water
                        ) // water-to-light
                    ) // light-to-temperature
                ) // temperature-to-humidity
            ) // humidity-to-location
            .stream()
            .map(p -> p.left)
            .min(Long::compareTo)
            .orElse(-1L);

        System.out.println(min);
    }

    private static LinkedList<Pair> getDestinations(Scanner in, LinkedList<Pair> sources) {
        in.next();
        in.next();

        LinkedList<Pair> destinations = new LinkedList<>();

        while (in.hasNextLong()) {
            long dLeft = in.nextLong();
            long sLeft = in.nextLong();
            long len = in.nextLong();
            long diff = dLeft - sLeft;
            long dRight = dLeft + len - 1;
            long sRight = sLeft + len - 1;

            LinkedList<Pair> toAdd = new LinkedList<>();
            Iterator<Pair> iter = sources.iterator();

            while (iter.hasNext()) {
                Pair p = iter.next();

                if (p.right < sLeft) continue;
                if (p.left > sRight) continue;
                iter.remove();

                if (sLeft == p.left && sRight >= p.right) {
                    destinations.add(new Pair(dLeft, p.right+diff));
                } else if (sLeft == p.left) {
                    destinations.add(new Pair(dLeft, dRight));
                    toAdd.add(new Pair(sRight+1, p.right));
                } else if (sLeft > p.left && sRight >= p.right) {
                    toAdd.add(new Pair(p.left, sLeft-1));
                    destinations.add(new Pair(dLeft, p.right+diff));
                } else if (sLeft > p.left) {
                    toAdd.add(new Pair(p.left, sLeft-1));
                    destinations.add(new Pair(dLeft, dRight));
                    toAdd.add(new Pair(sRight+1, p.right));
                } else if (sRight >= p.right) {
                    destinations.add(new Pair(p.left+diff, p.right+diff));
                } else {
                    destinations.add(new Pair(p.left+diff, dRight));
                    toAdd.add(new Pair(sRight+1, p.right));
                }
            }
            sources.addAll(toAdd);
        }

        destinations.addAll(sources);
        return destinations;
    }

    private static class Pair {
        long left;
        long right;

        public Pair(long left, long right) {
            this.left = left;
            this.right = right;
        }
    }
}
