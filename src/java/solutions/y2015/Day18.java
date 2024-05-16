package solutions.y2015;

import solutions.Solution;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class Day18 extends Solution {
    private static final int N = 100;
    private static final int C = 100;

    public static void main(String[] args) throws Exception {
        Day18 day14 = new Day18();
        day14.part1();
        day14.part2();
    }

    @Override
    public void part1() throws Exception {
        solve(false);
    }

    @Override
    public void part2() throws Exception {
        solve(true);
    }

    private void solve(boolean isCornerStuck) throws IOException {
        Boolean[][] grid = grid();

        if (isCornerStuck) {
            grid[0][0] = true;
            grid[0][N-1] = true;
            grid[N-1][0] = true;
            grid[N-1][N-1] = true;
        }

        for (int c = 0; c < C; c++) {
            Boolean[][] next = new Boolean[N][N];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    int n = onNeighboursCount(grid, i, j);

                    next[i][j] = n == 3 || (grid[i][j] && n == 2);
                }
            }

            grid = next;

            if (isCornerStuck) {
                grid[0][0] = true;
                grid[0][N-1] = true;
                grid[N-1][0] = true;
                grid[N-1][N-1] = true;
            }
        }


        System.out.println(Arrays.stream(grid).flatMap(Arrays::stream).filter(c -> c).count());
    }

    private Boolean[][] grid() throws IOException {
        return Files.readAllLines(this.inputFile()).stream()
                .map(
                        s -> s.chars()
                                .boxed()
                                .map(n -> n == '#')
                                .toArray(Boolean[]::new)
                )
                .toArray(Boolean[][]::new);
    }
    
    private static int onNeighboursCount(Boolean[][] grid, int row, int col) {
        return
                // top
                (isOn(grid, row-1, col-1) ? 1 : 0)
                + (isOn(grid, row-1, col) ? 1 : 0)
                + (isOn(grid, row-1, col+1) ? 1 : 0)

                // sides
                + (isOn(grid, row, col-1) ? 1 : 0)
                + (isOn(grid, row, col+1) ? 1 : 0)

                // bottom
                + (isOn(grid, row+1, col-1) ? 1 : 0)
                + (isOn(grid, row+1, col) ? 1 : 0)
                + (isOn(grid, row+1, col+1) ? 1 : 0);
    }
    
    private static boolean isOn(Boolean[][] grid, int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N && grid[row][col];
    }
}