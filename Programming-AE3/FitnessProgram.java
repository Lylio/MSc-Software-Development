import java.util.*;

/**
 * Maintains a list of Fitness Class objects
 * The list is initialised in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */
public class FitnessProgram {

	private final int MAX_CLASSES = 7; //Maximum of 7 hourly classes between 9am - 3pm
	private final int FIRST_CLASS_TIME = 9; //First class starts at 9am
	private FitnessClass[] fcArray; //An array of fitness class objects
	private int[] fitClassTimes; //An array of fitness class start times
	private int numOfClasses = 0; //Number of fitness classes in array

	/**
	 * Default constructor - instantiates an array of FitnessClass objects and
	 * instantiates and initialises an array of fitness class times
	 */
	public FitnessProgram() {
		fcArray = new FitnessClass[MAX_CLASSES];
		fitClassTimes = new int[MAX_CLASSES];

		for(int i = 0; i < fitClassTimes.length; i++) {
			fitClassTimes[i] = FIRST_CLASS_TIME + i;
		}
	}

	/**
	 * Creates array of fitness class objects ordered by start time
	 * @param classIn A line of text from ClassesIn.txt
	 */
	public void createClassArray(String classIn) {
		FitnessClass fc = new FitnessClass(classIn); //Create fitness class object
		int classStartTime = fc.getStartTime(); //Get start time of class
		int classPos = classStartTime - FIRST_CLASS_TIME; //Get classes' position in array based on start time
		fcArray[classPos] = fc; //Assign class object to array
		numOfClasses++;
	}

	/**
	 * Populates attendance data
	 * @param attendIn A line of text from AttendancesIn.txt
	 */
	public void createAttendArray(String attendIn) {
		//Instantiates array to hold attendance figures 
		int[] attRates = new int[5];
		Scanner scan = new Scanner(attendIn);	
		//First element of AttendancesIn.txt is stored as fitness class ID
		String classID = scan.next();

		int index = 0; //Index location for attendances array
		while(scan.hasNext()) {
			//Scans and stores attendances from AttendancesIn.txt
			attRates[index] = Integer.parseInt(scan.next());	
			index++;
		}
		//Calls getFCID() method which returns fitness class object based on ID
		FitnessClass tempFitClass = getFCID(classID);
		//Checks if object is null; if not null, the array of attendances is set to this object
		if(tempFitClass != null) {
			tempFitClass.setAttendances(attRates);
		}
		scan.close();
	}

	/**
	 * Returns a FitnessClass object based on class ID
	 * @param classID The ID of the fitness class to be returned
	 * @return The FitnessClass object
	 */
	public FitnessClass getFCID(String classID) {
		for(FitnessClass classObj : fcArray) {
			if(classObj != null) {
				if(classObj.getClassID().equals(classID)) {
					//Returns fitness class object if IDs match
					return classObj;
				}
			}
		}
		return null;
	}

	/**Returns number of fitness classes in array*/
	public int numOfClasses() {
		return numOfClasses;
	}

	/**
	 * Returns fitness class object from the array based on index
	 * @param index Array position of fitness class to be returned
	 */
	public FitnessClass classElement (int index) {
		return fcArray[index];
	}

	/**
	 * Returns fitness class object from the array based on start time
	 * @param t Start time of fitness class to be returned
	 * @return Fitness class object
	 */
	public FitnessClass classByTime(int t) {
		for(int i = 0; i < MAX_CLASSES; i++) {
			if(fcArray[i] != null && fcArray[i].getStartTime() == t) {
				return fcArray[i];										  	
			}
		}
		return null;
	}

	/**Returns first available start time*/
	public int availableTimeSlot() {
		for(int i = 0; i < MAX_CLASSES; i++) {
			if(fcArray[i] == null) {
				return i + 9;
			}
		}
		return -1; //Returns -1 if no slots available
	}

	/**Returns fitness class array*/
	public FitnessClass[] getClassArray() {
		return fcArray;
	}

	/**Returns maximum number of fitness classes*/
	public int getMaxClasses() {
		return MAX_CLASSES;
	}

	/**Returns start time of first fitness class*/
	public int getFirstClassTime() {
		return FIRST_CLASS_TIME;
	}

	/**Returns array of fitness class times*/
	public int[] getClassTimes() {
		return fitClassTimes;
	}

	/**
	 * Inserts new fitness class into array
	 * @param ID ID of new fitness class
	 * @param className Name of new fitness class
	 * @param tutorName Tutor's name
	 */
	public void addClass(String ID, String className, String tutorName) {
		//Gets first available time slot
		int firstTime = availableTimeSlot();

		//Formats new fitness class data
		String newClassData = String.format("%s %s %s %s", ID, className, tutorName, firstTime);

		//Searches for first available time slot, then creates new class object and inserts into array
		for (int i = 0 ; i < fcArray.length ; i++) {
			if (fcArray[i]== null) {
				FitnessClass newClass = new FitnessClass(newClassData);
				fcArray[i] = newClass;
				numOfClasses++;
				break ;
			}
		}
	}

	/**
	 * Removes fitness class from array
	 * @param ID ID of fitness class to be deleted
	 */
	public void deleteClass(String ID) {
		//Search for ID in fitness class array
		for(int i = 0; i < fcArray.length; i++) {
			if(fcArray[i] != null) {
				if(fcArray[i].getClassID().equals(ID)) {
					//Removes fitness class object from array
					fcArray[i] = null;
					numOfClasses--; 
				}
			}
		}
	}

	/**Returns a list sorted in non-increasing order on the average attendance at each class*/
	public FitnessClass[] avgClassAtt() {
		//Creates a new array of size that equals the actual number of fitness class objects
		FitnessClass[] tempFC = new FitnessClass[numOfClasses];
		int index = 0;

		//Loops through each FitnessClass object
		for(FitnessClass fc : fcArray) {
			if(fc != null){
				//If fitness class object is not null, the object is added to the tempFC array in the index position
				tempFC[index] = fc;	
				index++;
			}
		}
		//Sorts tempFC array
		Arrays.sort(tempFC);
		return tempFC;
	}

	/**Returns overall average of all fitness class attendances*/
	public double overallAvg() {
		int totalClasses = numOfClasses();
		double total = 0.0;
		double avg = 0.0;

		for(int i = 0; i < totalClasses; i++) {
			if(fcArray[i] != null) {
				total += fcArray[i].getAvgAtt();
			} 
		}
		avg = (double)total / totalClasses;
		return avg;
	}
}
