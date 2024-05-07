package solutions.y2015;

import solutions.Solution;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Function;

public class Day15 extends Solution {
    public static void main(String[] args) throws Exception {
        Day15 day15 = new Day15();
        day15.part1();
        day15.part2();
    }

    @Override
    public void part1() throws Exception {
        solve(0);
    }

    @Override
    public void part2() throws Exception {
        solve(500);
    }

    private void solve(long plannedCalories) throws IOException {
        Ingredient[] ingredients = getIngredients();
        long max = 0;
        for (long i = 0; i <= 100; i++) {
            for (long ii = 0; ii <= 100-i; ii++) {
                for (long iii = 0; iii <= 100-i-ii; iii++) {
                    for (long iv = 0; iv <= 100-i-ii-iii; iv++) {
                        if (plannedCalories == 0 || calculatePropScore(Ingredient::calories, ingredients, i, ii, iii, iv) == plannedCalories) {
                            long score = calculateScore(ingredients, i, ii, iii, iv);
                            if (score > max) max = score;
                        }
                    }
                }
            }
        }

        System.out.println(max);
    }

    private Ingredient[] getIngredients() throws IOException {
        Ingredient[] ingredients = new Ingredient[4];
        List<String> lines = Files.readAllLines(this.inputFile());
        for (int i = 0; i < 4; i++) {
            ingredients[i] = Ingredient.parse(lines.get(i));
        }
        return ingredients;
    }

    private long calculateScore(Ingredient[] ingredients, long ... spoons) {
        long score = 1, sub;

        sub = calculatePropScore(Ingredient::capacity, ingredients, spoons);
        if (sub <= 0) {
            return 0L;
        }
        score *= sub;

        sub = calculatePropScore(Ingredient::durability, ingredients, spoons);
        if (sub <= 0) {
            return 0L;
        }
        score *= sub;

        sub = calculatePropScore(Ingredient::flavor, ingredients, spoons);
        if (sub <= 0) {
            return 0L;
        }
        score *= sub;

        sub = calculatePropScore(Ingredient::texture, ingredients, spoons);
        if (sub <= 0) {
            return 0L;
        }
        score *= sub;

        return score;
    }

    private long calculatePropScore(Function<Ingredient, Integer> getProp, Ingredient[] ingredients, long ... spoons) {
        long score = 0;
        for (int i = 0; i < ingredients.length; i++) {
            score += spoons[i] * getProp.apply(ingredients[i]);
        }
        return score;
    }
}

record Ingredient(int capacity, int durability, int flavor, int texture, int calories) {
    public static Ingredient parse(String line) {
        String[] s = line.substring(line.indexOf(':') + 11).split("[a-zA-Z: ,]+");

        return new Ingredient(
                Integer.parseInt(s[0]),
                Integer.parseInt(s[1]),
                Integer.parseInt(s[2]),
                Integer.parseInt(s[3]),
                Integer.parseInt(s[4])
        );
    }
}