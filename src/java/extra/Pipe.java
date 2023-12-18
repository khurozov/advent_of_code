package extra;

public record Pipe(char c, Direction dir1, Direction dir2) {
    public static Pipe parse(char c) {
        return switch (c) {
            case '|' -> new Pipe(c, Direction.UP, Direction.DOWN);
            case '-' -> new Pipe(c, Direction.RIGHT, Direction.LEFT);
            case 'L' -> new Pipe(c, Direction.UP, Direction.RIGHT);
            case 'J' -> new Pipe(c, Direction.UP, Direction.LEFT);
            case '7' -> new Pipe(c, Direction.DOWN, Direction.LEFT);
            case 'F' -> new Pipe(c, Direction.DOWN, Direction.RIGHT);
            default -> new Pipe(c, null, null);
        };
    }

    public Direction nextDir(Direction dir) {
        if (dir == dir1) return dir2;
        if (dir == dir2) return dir1;
        return null;
    }

    public boolean hasUp() {
        return dir1 == Direction.UP || dir2 == Direction.UP;
    }

    public boolean hasDown() {
        return dir1 == Direction.DOWN || dir2 == Direction.DOWN;
    }

    public boolean hasRight() {
        return dir1 == Direction.RIGHT || dir2 == Direction.RIGHT;
    }

    public boolean hasLeft() {
        return dir1 == Direction.LEFT || dir2 == Direction.LEFT;
    }
}
