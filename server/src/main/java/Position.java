public class Position {
    private final int x;
    private final int y;

    /**
     * constructor for the position
     * */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * getter for the x co-ord
     * */
    public int getX() {
        return x;
    }

    /**
     * getter for the x co-ord
     * */
    public int getY() {
        return y;
    }

    /**
     * an override for the equals command (follow the toy robot scenario from last years curriculum).
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    /**
     * Used to check weather the co-ordinates are inside the boundaries
     * @return true if they are within the bounds of the world.
     * */
    public boolean isIn(Position topLeft, Position bottomRight) {
        boolean withinTop = this.y <= topLeft.getY();
        boolean withinBottom = this.y >= bottomRight.getY();
        boolean withinLeft = this.x >= topLeft.getX();
        boolean withinRight = this.x <= bottomRight.getX();
        return withinTop && withinBottom && withinLeft && withinRight;
    }

    /**
     * overrides the to string method, with the co-ordinates
     * */
    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
