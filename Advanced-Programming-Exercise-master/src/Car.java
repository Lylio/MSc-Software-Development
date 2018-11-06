import java.util.Random;

public class Car extends Vehicle{

    //The speed of each car is determined by a random delay (a higher number is a slower car)
    private final int MIN_MOVE_DELAY = 100;
    private final int MAX__MOVE_DELAY = 950;

    public Car(GridSquare[][] gs, String icon, String direction) {
        super(gs, icon, direction);
        Random random = new Random();
        moveDelay = random.nextInt(MAX__MOVE_DELAY) + MIN_MOVE_DELAY;
    }
}
