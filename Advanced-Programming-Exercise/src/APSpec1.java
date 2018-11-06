import java.util.Random;

public class APSpec1 {

    public static void main(String args[]) {

        final int ROWS = 10; //Grid width
        final int COLUMNS = 20; //Grid length
        final int SPAWN_DELAY = 500; //Determines the spawn rate of cars (a higher number is a slower rate)
        String car1Icon = "O"; //South car symbol
        String car2Icon = ">"; //East car symbol
        Random random = new Random();

        //Creates and instantiates the main grid
        GridSquare[][] grid = new GridSquare[ROWS][COLUMNS];
        for(int r = 0; r < ROWS; r++) {
            for(int c = 0; c < COLUMNS; c++) {
                grid[r][c] = new GridSquare();
            }
        }

        //Creates and starts the display which prints the grid
        Display display = new Display(grid);
        display.start();

        //Continuously spawns cars while the Display thread is running
        while(display.isAlive()) {
            int randDirection = random.nextInt(2);
            //Car direction is randomly decided
            if(randDirection == 0) {
                new Car(grid, car1Icon, "south").start();
            } else if(randDirection == 1) {
                new Car(grid, car2Icon, "east").start();
            }

            //Delay between spawns
            try {
                Thread.sleep(SPAWN_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Successful exit of program when Display thread finishes
        System.exit(0);
    }
}
