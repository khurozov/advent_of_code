package solutions.y2015;

import solutions.Solution;

import java.io.IOException;
import java.nio.file.Files;
import java.util.TreeMap;
import java.util.TreeSet;

public class Day13 extends Solution {
    public static void main(String[] args) throws Exception {
        Day13 day13 = new Day13();
        day13.part1();
        day13.part2();
    }

    private final TreeMap<String, TreeMap<String, Integer>> happinessMap;
    private final TreeSet<String> people;
    private final TreeSet<String> arrangements;

    public Day13() throws IOException {
        happinessMap = new TreeMap<>();
        people = new TreeSet<>();

        Files.readAllLines(this.inputFile()).forEach(str -> {
            String[] s = str.split(" ");
            happinessMap.computeIfAbsent(s[0], c -> new TreeMap<>()).put(s[10].replace(".", ""), (s[2].equals("gain") ? 1 : -1) * Integer.parseInt(s[3]));
            people.add(s[0]);
        });
        arrangements = new TreeSet<>();
    }

    private void generateArrangements(String subArrangement, int i) {
        for (String p : people) {
            if (subArrangement.contains(p)) continue;

            String next = subArrangement + (subArrangement.isEmpty() ? "" : "=") + p;
            if (i == people.size()) {
                arrangements.add(next);
            } else {
                generateArrangements(next, i + 1);
            }
        }
    }

    private int happinessLevel(String arrangement) {
        String[] a = arrangement.split("=");
        int h = 0;

        for (int i = 1; i < a.length; i++) {
            h += happinessMap.get(a[i-1]).get(a[i]) + happinessMap.get(a[i]).get(a[i-1]);
        }
        h += happinessMap.get(a[0]).get(a[a.length-1]) + happinessMap.get(a[a.length-1]).get(a[0]);

        return h;
    }

    @Override
    public void part1() throws Exception {
        arrangements.clear();
        generateArrangements("", 1);

        System.out.println(maxHappinessLevel());
    }

    @Override
    public void part2() throws Exception {
        TreeMap<String, Integer> fromMe = new TreeMap<>();
        happinessMap.forEach((key, value) -> {
            value.put("Me", 0);
            fromMe.put(key, 0);
        });
        happinessMap.put("Me", fromMe);
        people.add("Me");

        arrangements.clear();
        generateArrangements("", 1);

        System.out.println(maxHappinessLevel());
    }

    private int maxHappinessLevel() {
        return arrangements.stream()
                .mapToInt(this::happinessLevel)
                .max().orElse(0);
    }
}