package extra;

public enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    RIGHT(0, 1),
    LEFT(0, -1);

    public final int i;
    public final int j;

    Direction(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public Direction reverse() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case RIGHT -> LEFT;
            case LEFT -> RIGHT;
        };
    }
}
