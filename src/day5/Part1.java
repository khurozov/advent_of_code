package day5;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Part1 {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(Path.of("src", "day5", "input"));
        LinkedList<Long> seeds = new LinkedList<>();

        in.next();
        while (in.hasNextLong()) {
            seeds.add(in.nextLong());
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
            .min(Long::compareTo)
            .orElse(-1L);

        System.out.println(min);
    }

    private static LinkedList<Long> getDestinations(Scanner in, LinkedList<Long> sources) {
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
                    destinations.add(s+diff);
                    iterator.remove();
                }
            }
        }
        destinations.addAll(sources);
        return destinations;
    }
}
