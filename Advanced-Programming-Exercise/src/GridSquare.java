import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class GridSquare {

    private ReentrantLock squareLock = new ReentrantLock();
    private Condition waitCondition = squareLock.newCondition();
    private String squareIcon = "| "; //Represents lane separator
    private boolean squareIsTaken = false; //Flags if square is occupied

    //Getters
    public String getSquareIcon() {
        return squareIcon;
    }

    //Called when vehicle enters a square. Applies locks to square and appends grid with vehicle icons
    public void moveIn(Vehicle vehicle) {
        try {
            Thread.sleep(vehicle.getMoveDelay());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        squareLock.lock();

        try {
            while (squareIsTaken) {
                waitCondition.await(); //Thread waits if square is occupied
            }
            squareIsTaken = true;
            squareIcon = "|" + vehicle.getIcon();

        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {
            squareLock.unlock();
        }
    }

    //Called when a vehicle leaves a square
    public void moveOut(Vehicle vehicle) {
        squareLock.lock();
        try {
            squareIsTaken = false;
            waitCondition.signalAll(); //Signals any waiting vehicles can try to enter square
        } finally {
            squareLock.unlock();
            squareIcon = "| ";
        }
    }
}
