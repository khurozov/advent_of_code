package solutions.y2015;

import solutions.Solution;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Day16 extends Solution {
    private static final List<String> list = Arrays.asList(
            "children: 3",
            "cats: 7",
            "samoyeds: 2",
            "pomeranians: 3",
            "akitas: 0",
            "vizslas: 0",
            "goldfish: 5",
            "trees: 3",
            "cars: 2",
            "perfumes: 1"
    );

    public static void main(String[] args) throws Exception {
        Day16 day14 = new Day16();
        day14.part1();
        day14.part2();
    }

    @Override
    public void part1() throws Exception {
        solve(s -> (x0, x1) -> !Objects.equals(x0, x1));
    }

    @Override
    public void part2() throws Exception {
        solve(s -> switch (s) {
            case "cats", "trees" -> (x0, x1) -> x0 <= x1;
            case "pomeranians", "goldfish" -> (x0, x1) -> x0 >= x1;
            default -> (x0, x1) -> !Objects.equals(x0, x1);
        });
    }

    private void solve(Function<String, BiFunction<Integer, Integer, Boolean>> filterBuilder) throws Exception {
        HashMap<String, HashMap<Integer, List<Integer>>> map = inputMap();
        List<Integer> aunts = new ArrayList<>(IntStream.rangeClosed(1, 500).boxed().toList());

        list.forEach(p -> {
            String[] s = p.split(": ");
            int x = Integer.parseInt(s[1]);
            BiFunction<Integer, Integer, Boolean> filter = filterBuilder.apply(s[0]);

            aunts.removeAll(
                    map.get(s[0])
                        .entrySet()
                        .stream()
                            .filter(e -> filter.apply(e.getKey(), x))
                            .flatMap(e -> e.getValue().stream())
                            .toList()
            );
        });

        System.out.println(aunts);
    }

    private HashMap<String, HashMap<Integer, List<Integer>>> inputMap() throws IOException {
        HashMap<String, HashMap<Integer, List<Integer>>> map = new HashMap<>();
        Files.readAllLines(this.inputFile()).forEach(s -> {
            String[] a = s.split("[ :,]+");
            int n = Integer.parseInt(a[1]);
            for (int i = 3; i < a.length; i += 2) {
                map.computeIfAbsent(a[i - 1], k -> new HashMap<>()).computeIfAbsent(Integer.parseInt(a[i]), k -> new LinkedList<>()).add(n);
            }
        });
        return map;
    }
}