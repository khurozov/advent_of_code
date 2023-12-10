package day10;

record Pipe(char c, Direction dir1, Direction dir2) {
    Direction nextDir(Direction dir) {
        if (dir == dir1) return dir2;
        if (dir == dir2) return dir1;
        return Direction.UNKNOWN;
    }

    static Pipe parse(char c) {
        return switch (c) {
            case '|' -> new Pipe(c, Direction.NORTH, Direction.SOUTH);
            case '-' -> new Pipe(c, Direction.EAST, Direction.WEST);
            case 'L' -> new Pipe(c, Direction.NORTH, Direction.EAST);
            case 'J' -> new Pipe(c, Direction.NORTH, Direction.WEST);
            case '7' -> new Pipe(c, Direction.SOUTH, Direction.WEST);
            case 'F' -> new Pipe(c, Direction.SOUTH, Direction.EAST);
            default -> new Pipe(c, Direction.UNKNOWN, Direction.UNKNOWN);
        };
    }

    boolean hasNorth() {
        return dir1 == Direction.NORTH || dir2 == Direction.NORTH;
    }

    boolean hasSouth() {
        return dir1 == Direction.SOUTH || dir2 == Direction.SOUTH;
    }

    boolean hasEast() {
        return dir1 == Direction.EAST || dir2 == Direction.EAST;
    }

    boolean hasWest() {
        return dir1 == Direction.WEST || dir2 == Direction.WEST;
    }
}
