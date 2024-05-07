package solutions.y2015;

import solutions.Solution;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Day9 extends Solution {
    public static void main(String[] args) throws Exception {
        Day9 day9 = new Day9();
        day9.part1();
        day9.part2();
    }

    private final TreeMap<String, TreeMap<String, Integer>> routeMap;
    private final TreeSet<String> cities;
    private final TreeSet<String> routes;

    public Day9() throws IOException {
        routeMap = new TreeMap<>();
        cities = new TreeSet<>();

        Files.readAllLines(this.inputFile()).forEach(str -> {
            String[] s = str.split(" ");
            routeMap.computeIfAbsent(s[0], c -> new TreeMap<>()).put(s[2], Integer.parseInt(s[4]));
            routeMap.computeIfAbsent(s[2], c -> new TreeMap<>()).put(s[0], Integer.parseInt(s[4]));
            cities.add(s[0]);
            cities.add(s[2]);
        });

        routes = new TreeSet<>();
        generateFullRoutes(cities, "", routes, 1);
    }

    private void generateFullRoutes(Set<String> cities, String subRoute, Set<String> routes, int i) {
        for (String c : cities) {
            if (subRoute.contains(c)) continue;

            String next = subRoute + (subRoute.isEmpty() ? "" : "-") + c;
            if (i == cities.size()) {
                routes.add(next);
            } else {
                generateFullRoutes(cities, next, routes, i + 1);
            }
        }
    }

    private int routeLength(String route) {
        String[] r = route.split("-");
        int l = 0;

        for (int i = 1; i < r.length; i++) {
            l += routeMap.get(r[i-1]).get(r[i]);
        }

        return l;
    }

    @Override
    public void part1() throws Exception {
        System.out.println(routes.stream()
                .mapToInt(this::routeLength)
                .min().orElse(0));
    }

    @Override
    public void part2() throws Exception {
        System.out.println(routes.stream()
                .mapToInt(this::routeLength)
                .max().orElse(0));
    }
}