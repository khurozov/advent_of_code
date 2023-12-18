package solutions.y2023;

import solutions.Solution;
import extra.Pair;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Day5 extends Solution {
    public static void main(String[] args) throws Exception {
        Day5 day5 = new Day5();
        day5.part1();
        day5.part2();
    }

    @Override
    public void part1() throws Exception {
        Scanner in = new Scanner(this.inputFile());
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

    @Override
    public void part2() throws Exception {
        Scanner in = new Scanner(this.inputFile());
        LinkedList<Pair<Long>> seeds = new LinkedList<>();

        in.next();
        while (in.hasNextLong()) {
            long seed = in.nextLong();
            long len = in.nextLong();
            seeds.add(Pair.of(seed, seed + len - 1));
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

    private LinkedList<Long> getDestinationsPart1(Scanner in, LinkedList<Long> sources) {
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

    private LinkedList<Pair<Long>> getDestinationsPart2(Scanner in, LinkedList<Pair<Long>> sources) {
        in.next();
        in.next();

        LinkedList<Pair<Long>> destinations = new LinkedList<>();

        while (in.hasNextLong()) {
            long dLeft = in.nextLong();
            long sLeft = in.nextLong();
            long len = in.nextLong();
            long diff = dLeft - sLeft;
            long dRight = dLeft + len - 1;
            long sRight = sLeft + len - 1;

            LinkedList<Pair<Long>> toAdd = new LinkedList<>();
            Iterator<Pair<Long>> iter = sources.iterator();

            while (iter.hasNext()) {
                Pair<Long> p = iter.next();

                if (p.right() < sLeft) continue;
                if (p.left() > sRight) continue;
                iter.remove();

                if (sLeft == p.left() && sRight >= p.right()) {
                    destinations.add(Pair.of(dLeft, p.right() + diff));
                } else if (sLeft == p.left()) {
                    destinations.add(Pair.of(dLeft, dRight));
                    toAdd.add(Pair.of(sRight + 1, p.right()));
                } else if (sLeft > p.left() && sRight >= p.right()) {
                    toAdd.add(Pair.of(p.left(), sLeft - 1));
                    destinations.add(Pair.of(dLeft, p.right() + diff));
                } else if (sLeft > p.left()) {
                    toAdd.add(Pair.of(p.left(), sLeft - 1));
                    destinations.add(Pair.of(dLeft, dRight));
                    toAdd.add(Pair.of(sRight + 1, p.right()));
                } else if (sRight >= p.right()) {
                    destinations.add(Pair.of(p.left() + diff, p.right() + diff));
                } else {
                    destinations.add(Pair.of(p.left() + diff, dRight));
                    toAdd.add(Pair.of(sRight + 1, p.right()));
                }
            }
            sources.addAll(toAdd);
        }

        destinations.addAll(sources);
        return destinations;
    }
}
