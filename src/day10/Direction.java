package day10;

enum Direction {
    NORTH(-1, 0),
    SOUTH(1, 0),
    EAST(0, 1),
    WEST(0, -1),
    UNKNOWN(0, 0);

    final int i;
    final int j;

    Direction(int i, int j) {
        this.i = i;
        this.j = j;
    }

    Direction reverse() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
            case UNKNOWN -> UNKNOWN;
        };
    }
}
