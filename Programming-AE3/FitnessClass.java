/** Defines an object representing a single fitness class */


public class FitnessClass implements Comparable<FitnessClass> {

	private final int WEEKS = 5; //Number of weeks that attendances are recorded
	private String classID, className, tutorName; //Fitness class ID, name and tutor name
	private int startTime; //Start time of fitness class
	private int[] attendances = new int[WEEKS]; //Instantiates an array to hold the attendance figures

	/**Non-default constructor
	 * Fitness class details passed as a String and assigned to instance variables
	 * @param classIn A line of text from ClassesIn.txt
	 */
	public FitnessClass(String classIn) {
		//Elements from each line of text are separated into tokens using the String split() method
		String[] classTokens = classIn.split(" ");
		classID = classTokens[0];
		className = classTokens[1];
		tutorName = classTokens[2];
		startTime = Integer.parseInt(classTokens[3]);
	}

	//Getters
	/**Returns fitness class ID*/
	public String getClassID() {return this.classID;}

	/**Returns name of fitness class*/
	public String getClassName() {return this.className;}

	/**Returns name of tutor*/
	public String getTutorName() {return this.tutorName;}

	/**Returns start time of fitness class*/
	public int getStartTime() {return this.startTime;}

	/**Returns attendances*/
	public int[] getAttendances() {return this.attendances;}

	/**Returns average attendance for fitness class*/
	public double getAvgAtt() {
		int total = 0;
		double avg = 0.0;
		for(int i = 0; i < WEEKS; i++) {
			total += attendances[i];
		}
		avg = (double)total / WEEKS;
		return avg;
	}

	//Setters
	/**Sets fitness class ID*/
	public void setClassID(String classID) {this.classID = classID;}

	/**Sets name of fitness class*/
	public void setClassName(String className) {this.className = className;}

	/**Sets name of tutor*/
	public void setTutorName(String tutorName) {this.tutorName = tutorName;}

	/**Sets start time of fitness class*/
	public void setStartTime(int startTime) {this.startTime = startTime;}

	/**Populates attendance array*/
	public void setAttendances(int[] attendIn) {this.attendances = attendIn;} 

	//Attendance report methods
	/**Compares two Fitness Class objects on average attendance*/
	public int compareTo(FitnessClass other) {
		double thisFCAvg = this.getAvgAtt();
		double otherFCAvg = other.getAvgAtt();

		if(thisFCAvg < otherFCAvg) {return 1; } 
		else if(thisFCAvg == otherFCAvg) {return 0;}
		else {return -1;}
	}

	/**Creates line of attendance figures for inclusion in attendance report*/
	public String attLine() {
		StringBuilder attBuild = new StringBuilder();
		for(int i = 0; i < WEEKS; i++) {
			attBuild.append(String.format("%3d", attendances[i]));
		}
		return attBuild.toString();
	}

	/**Returns attendance report*/
	public String report() {
		String attLine = attLine();  //Gets line of attendances
		double avgAtt = getAvgAtt(); //Gets average of attendances

		//Creates and formats a complete report line
		String completeLine = String.format(" %-5s%-15s%-15s%-25s%5.2f%n", classID, className, tutorName, attLine, avgAtt);
		return completeLine;
	}
}