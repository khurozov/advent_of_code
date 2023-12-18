package solutions.y2023;

import solutions.Solution;

import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;

public class Day14 extends Solution {
    public static void main(String[] args) throws Exception {
        Day14 day14 = new Day14();
        day14.part1();
        day14.part2();
    }

    @Override
    public void part1() throws Exception {
        char[][] data = readInput();
        moveToNorth(data);
        System.out.println(loadSum(data));
    }

    @Override
    public void part2() throws Exception {
        char[][] data = readInput();
        cycle(data);
        System.out.println(loadSum(data));
    }

    private char[][] readInput() throws IOException {
        return Files.readAllLines(this.inputFile())
                .stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    private static int loadSum(char[][] data) {
        int sum = 0;
        for (int i = 0; i < data.length; i++) {
            int load = 0;
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == 'O') load++;
            }
            sum += load * (data.length - i);
        }
        return sum;
    }

    private static void cycle(char[][] data) {
        int cycleCount = 1_000_000_000;

        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < cycleCount; i++) {
            cycleOnce(data);

            int sum = loadSum(data);
            list.add(sum);

            if (list.size() == 211) {
                int i0 = list.indexOf(sum);
                if (i0 < i) {
                    int x = (cycleCount - i0 - 1) % (i - i0);
                    for (int j = 0; j < x; j++) {
                        cycleOnce(data);
                    }
                    return;
                }
            }
        }
    }

    private static void cycleOnce(char[][] data) {
        moveToNorth(data);
        moveToWest(data);
        moveToSouth(data);
        moveToEast(data);
    }

    private static void moveToNorth(char[][] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == '.') {
                    int x = getMovableToNorth(data, i + 1, j);
                    if (x != -1) {
                        data[x][j] = '.';
                        data[i][j] = 'O';
                    }
                }
            }
        }
    }

    private static void moveToSouth(char[][] data) {
        for (int i = data.length - 1; i > 0; i--) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == '.') {
                    int x = getMovableToSouth(data, i - 1, j);
                    if (x != -1) {
                        data[x][j] = '.';
                        data[i][j] = 'O';
                    }
                }
            }
        }
    }

    private static void moveToEast(char[][] data) {
        for (int j = data[0].length - 1; j > 0; j--) {
            for (int i = 0; i < data.length; i++) {
                if (data[i][j] == '.') {
                    int x = getMovableToEast(data, i, j - 1);
                    if (x != -1) {
                        data[i][x] = '.';
                        data[i][j] = 'O';
                    }
                }
            }
        }
    }

    private static void moveToWest(char[][] data) {
        for (int j = 0; j < data[0].length; j++) {
            for (int i = 0; i < data.length; i++) {
                if (data[i][j] == '.') {
                    int x = getMovableToWest(data, i, j + 1);
                    if (x != -1) {
                        data[i][x] = '.';
                        data[i][j] = 'O';
                    }
                }
            }
        }
    }

    private static int getMovableToNorth(char[][] data, int i, int j) {
        if (i >= data.length) return -1;
        if (data[i][j] == '#') return -1;
        if (data[i][j] == 'O') return i;
        return getMovableToNorth(data, i + 1, j);
    }

    private static int getMovableToSouth(char[][] data, int i, int j) {
        if (i < 0) return -1;
        if (data[i][j] == '#') return -1;
        if (data[i][j] == 'O') return i;
        return getMovableToSouth(data, i - 1, j);
    }

    private static int getMovableToEast(char[][] data, int i, int j) {
        if (j < 0) return -1;
        if (data[i][j] == '#') return -1;
        if (data[i][j] == 'O') return j;
        return getMovableToEast(data, i, j - 1);
    }

    private static int getMovableToWest(char[][] data, int i, int j) {
        if (j >= data[i].length) return -1;
        if (data[i][j] == '#') return -1;
        if (data[i][j] == 'O') return j;
        return getMovableToWest(data, i, j + 1);
    }
}
