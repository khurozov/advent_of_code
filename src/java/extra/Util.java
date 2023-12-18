package extra;

public class Util {
    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean inRange(char[][] grid, int i, int j) {
        return i >= 0 && i < grid.length && j >= 0 && j < grid[0].length;
    }
}
