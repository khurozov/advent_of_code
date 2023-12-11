import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Day5 {
    private record Pair(long left, long right) {
    }

    public static void main(String[] args) throws IOException {
        part1();
        part2();
    }

    private static void part1() throws IOException {
        Scanner in = new Scanner(Path.of("src", "Day5.txt"));
        LinkedList<Long> seeds = new LinkedList<>();

        in.next();
        while (in.hasNextLong()) {
            seeds.add(in.nextLong());
        }

        long min = getDestinationsPart1(
                in,
                getDestinationsPart1(
                        in,
                        getDestinationsPart1(
                                in,
                                getDestinationsPart1(
                                        in,
                                        getDestinationsPart1(
                                                in,
                                                getDestinationsPart1(
                                                        in,
                                                        getDestinationsPart1(
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
                .min(Long::compareTo)
                .orElse(-1L);

        System.out.println(min);
    }

    private static void part2() throws IOException {
        Scanner in = new Scanner(Path.of("src", "Day5.txt"));
        LinkedList<Pair> seeds = new LinkedList<>();

        in.next();
        while (in.hasNextLong()) {
            long seed = in.nextLong();
            long len = in.nextLong();
            seeds.add(new Pair(seed, seed + len - 1));
        }

        long min = getDestinationsPart2(
                in,
                getDestinationsPart2(
                        in,
                        getDestinationsPart2(
                                in,
                                getDestinationsPart2(
                                        in,
                                        getDestinationsPart2(
                                                in,
                                                getDestinationsPart2(
                                                        in,
                                                        getDestinationsPart2(
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
                .map(Pair::left)
                .min(Long::compareTo)
                .orElse(-1L);

        System.out.println(min);
    }

    private static LinkedList<Long> getDestinationsPart1(Scanner in, LinkedList<Long> sources) {
        in.next();
        in.next();

        LinkedList<Long> destinations = new LinkedList<>();
        while (in.hasNextLong()) {
            long dStart = in.nextLong();
            long sStart = in.nextLong();
            long length = in.nextLong();
            long diff = dStart - sStart;

            if (sources.isEmpty()) continue;
            Iterator<Long> iterator = sources.iterator();
            while (iterator.hasNext()) {
                long s = iterator.next();

                if (s >= sStart && s < sStart + length) {
                    destinations.add(s + diff);
                    iterator.remove();
                }
            }
        }
        destinations.addAll(sources);
        return destinations;
    }

    private static LinkedList<Pair> getDestinationsPart2(Scanner in, LinkedList<Pair> sources) {
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
                    destinations.add(new Pair(dLeft, p.right + diff));
                } else if (sLeft == p.left) {
                    destinations.add(new Pair(dLeft, dRight));
                    toAdd.add(new Pair(sRight + 1, p.right));
                } else if (sLeft > p.left && sRight >= p.right) {
                    toAdd.add(new Pair(p.left, sLeft - 1));
                    destinations.add(new Pair(dLeft, p.right + diff));
                } else if (sLeft > p.left) {
                    toAdd.add(new Pair(p.left, sLeft - 1));
                    destinations.add(new Pair(dLeft, dRight));
                    toAdd.add(new Pair(sRight + 1, p.right));
                } else if (sRight >= p.right) {
                    destinations.add(new Pair(p.left + diff, p.right + diff));
                } else {
                    destinations.add(new Pair(p.left + diff, dRight));
                    toAdd.add(new Pair(sRight + 1, p.right));
                }
            }
            sources.addAll(toAdd);
        }

        destinations.addAll(sources);
        return destinations;
    }
}
