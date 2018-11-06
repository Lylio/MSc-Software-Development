import java.util.Random;

public class Vehicle extends Thread {

    protected String direction; //e.g. "south" or "east"
    protected String icon; //Symbol of vehicle on grid

    protected int moveDelay; //Determines speed of a car (a higher number is a slower car)
    protected int startSquare; //Reference to a vehicle's starting square coordinate
    protected int current; //Reference to vehicle's current square coordinate

    protected GridSquare nextSquare; //Reference to upcoming GridSquare object
    protected GridSquare currentSquare; //Reference to current GridSquare object
    protected GridSquare[][] grid; //Reference to the main grid

    //Counters for total number of spawned vehicles
    protected static int southCounter = 0;
    protected static int eastCounter = 0;

    public Vehicle(GridSquare[][] gs, String icon, String direction) {

        this.grid = gs;
        this.icon = icon;
        this.direction = direction;

        Random random = new Random();

            switch (getDirection()) {
                case "south":
                    current = 0;
                    startSquare = random.nextInt(grid[0].length); //Randomly chooses a vehicle's entry lane
                    nextSquare = grid[current][startSquare];
                    southCounter++;
                    break;
                case "east":
                    current = 0;
                    startSquare = random.nextInt(grid.length); //Randomly chooses a vehicle's entry lane
                    nextSquare = grid[startSquare][current];
                    eastCounter++;
                    break;

            }
    }

    //Getters
    public String getDirection() {
        return direction;
    }

    public int getMoveDelay() {
        return moveDelay;
    }

    public String getIcon() {
        return icon;
    }

    public int getCurrent() {
        return current;
    }

    public static int getSouthCounter() {
        return southCounter;
    }

    public static int getEastCounter() {
        return eastCounter;
    }

    //Gets current GridSquare object
    public GridSquare getCurrentSquare() {

        switch (getDirection()) {
            case "south": currentSquare = grid[getCurrent() - 1][startSquare];
                break;
            case "east": currentSquare = grid[startSquare][getCurrent() - 1];
                break;
        }
        return currentSquare;
    }

    //Setters
    //Sets upcoming GridSquare object
    public void setUpcomingSquare() {

        switch (getDirection()) {
            case "south": nextSquare = grid[++current][startSquare];
                break;
            case "east": nextSquare = grid[startSquare][++current];
                break;
        }
    }

    //Checks if end of grid has been reached
    public boolean isGridEnd() {

        if (direction == "south" && current == grid.length - 1) {
            return true;
        }
        if (direction == "east" && current == grid[0].length - 1) {
            return true;
        } else
            return false;
    }

    //Method to move vehicles
    public void move() {

        nextSquare.moveIn(this);

        while (isGridEnd() == false) {
            setUpcomingSquare();
            nextSquare.moveIn(this);
            getCurrentSquare().moveOut(this);
        }

        //Executed when isGridEnd() == true
        try {
            Thread.sleep(moveDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        nextSquare.moveOut(this);
    }

    //Starts thread
    public void run() {
        move();
    }
}
