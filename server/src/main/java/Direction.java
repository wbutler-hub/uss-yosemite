public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    static private final Direction[] values = values();

    /**
     * @return the directions for left, -1 (as enum is in a list)
     * */
    public Direction left() {
        return values[Math.floorMod(ordinal() - 1, values.length)];
    }

    /**
     * @return the directions for left, +1 (as enum is in a list)
     * */
    public Direction right() {
        return values[Math.floorMod(ordinal() + 1, values.length)];
    }
}
