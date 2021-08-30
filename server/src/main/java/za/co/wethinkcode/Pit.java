package za.co.wethinkcode;

public class Pit extends Obstructions{

    /**
     * Constructors for the pit, takes the bottom right, and top left. And passes it through as super
     * */
    public Pit(Position topLeft, Position bottomRight) {
        super(topLeft, bottomRight);
    }
}
