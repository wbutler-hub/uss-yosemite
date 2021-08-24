public abstract class Obstructions {
    private final Position topLeft;
    private final Position bottomRight;
    private final Position bottomLeft;
    private final int x;
    private final int y;
    private final int size = 5;

    /**
     * Constructor for the Obstructions; setting up all the corners and co-ordinates.
     * @param bottomRight: The position of the bottom right co-ordinate.
     * @param topLeft: The position of the top left co-ordinate.
     * */
    public Obstructions(Position topLeft, Position bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.bottomLeft = new Position(bottomRight.getX()-4, bottomRight.getY());
        this.x = bottomLeft.getX();
        this.y = bottomLeft.getY();
    }

    /**
     * @return the top bottom right corner.
     * */
    public Position getBottomRightPosition() {
        return this.bottomRight;
    }

    /**
     * @return the top left corner.
     * */
    public Position getTopLeftPosition() {
        return this.topLeft;
    }

    /**
     * @return the size of the robot;
     * */
    public Position getBottomLeftPosition() {return  this.bottomLeft;}

    /**
     * @return the size of the robot;
     * */
    public int getSize() {
        return this.size;
    }

    /**
     * @return the x co-ordinate (bottomleft);
     * */
    public int getX() {
        return this.x;
    }

    /**
     * @return the y co-ordinate (bottomleft);
     * */
    public int getY() {
        return this.y;
    }

    /**
     * Checks the a boolean if the position is in blocked
     * @param position: passes through the position.
     * @return true if the x and y is blocked.
     * @return false, if the x and y is not blocked.
     * */
    public boolean blocksPosition(Position position) {
        Boolean checkY = this.y <= position.getY() && (this.y + 4) >= position.getY();
        Boolean checkX = this.x <= position.getX() && (this.x + 4) >= position.getX();

        if (checkX && checkY) {
            return true;
        }
        return  false;
    }

    /**
     * Checks to see if the original position up unto the next (newly updated) is blocked or can be done or not.
     * @return false if it cannot be done
     * */
    public boolean blocksPath(Position a, Position b) {
        if (a.getX() == b.getX() && (this.x <= a.getX() && a.getX() <= this.x + 4)) {
            if (b.getY() < this.y) {
                return a.getY() >= this.y;
            }
            else if (b.getY() > (this.y + 4)) {
                return a.getY() <= this.y + 4;
            }
        }
        else if (a.getY() == b.getY() && (this.y <= a.getY() && a.getY() <= this.y + 4)) {
            if (b.getX() < this.x) {
                return a.getX() >= this.x;
            }
            else if (b.getX() > (this.x + 4)) {
                return a.getX() <= this.x + 4;
            }
        }

        return false;
    }

    /**
     * @returns the top left position to the bottom right position
     * */
    @Override
    public String toString() {
        return topLeft + " to " + bottomRight;
    }
}
