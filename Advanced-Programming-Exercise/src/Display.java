
public class Display extends Thread {

    private final int MAX_PRINT = 2000; //Number of times grid is printed (a higher number is a longer program run-time)
    private final int PRINT_DELAY = 20; //Determines print frequency (a lower number is a higher 'frame rate')
    private final int EDGE_LENGTH; //Length of dashed edging

    private GridSquare[][] grid; //Reference to the main grid
    private StringBuilder gridSB; //String version of whole main grid

    public Display(GridSquare[][] gs) {
        this.grid = gs;
        this.EDGE_LENGTH = gs[0].length;
    }

    //Creates dashed edging
    public void printEdge(StringBuilder sb) {

        sb.append(System.getProperty("line.separator"));
        for(int i = 1; i < EDGE_LENGTH; i++) {
            sb.append("--");
        }
        sb.append("--");
    }

    //Prints entire grid
    public void printGrid() {

        for(int printCount = 0; printCount < MAX_PRINT; printCount++) {

            gridSB = new StringBuilder();

            printEdge(gridSB); //Dashed edge i.e. "------------"

            for(int i = 0; i < grid.length; i++) {
                gridSB.append(System.getProperty("line.separator"));
                for(int j = 0; j < grid[i].length; j++) {
                    gridSB.append(grid[i][j].getSquareIcon()); //Appends lane separators: "| "
                }
            }
            printEdge(gridSB); //Dashed edge i.e. "------------"
            gridSB.append(System.getProperty("line.separator"));

            //Prints total number of vehicles categorised by direction
            gridSB.append("   South total: " + Vehicle.getSouthCounter() + " | " + "East total: " + Vehicle.getEastCounter());

            try {
                Thread.sleep(PRINT_DELAY); //Delay between 'frame' prints
               printCount++;
                System.out.print(gridSB);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Starts thread
    public void run() {
        printGrid();
    }
}
