import java.awt.*;

import javax.swing.*;

/**
 * Class to define window in which attendance report is displayed.
 */
public class ReportFrame extends JFrame {

	private FitnessProgram fitProg;
	private JTextArea textArea;
	private FitnessClass[] tempFC;

	/**
	 * Constructor for the report window
	 * @param fitProg Fitness programme object
	 */
	public ReportFrame(FitnessProgram fitProg) {

		//Sets properties of report frame
		this.fitProg = fitProg;
		this.setTitle("Class Attendance Report");
		this.setSize(670,250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		//Sets properties of report text area
		textArea = new JTextArea();
		textArea.setFont(new Font("Courier", Font.PLAIN, 14));
		add(textArea, BorderLayout.CENTER);
		textArea.append(reportText());
		setVisible(true); 
	}

	/**Returns the report text for display in the JTextArea.*/
	public String reportText(){
		textArea.setText(null);
		double totalAttendances = 0.0;
		StringBuffer sb = new StringBuffer("");
		tempFC = fitProg.avgClassAtt();

		//Creates formatted header of report
		sb.append(String.format("%3s %7s %14s %22s %23s\n", "Id", "Class", "Tutor", "Attendances", "Average Attendances"));

		//Creates a line break using hyphens 
		for(int i = 0; i < 82; i++) {
			sb.append(String.format("-"));
		}
		sb.append("\n");

		for(FitnessClass FC : tempFC) {
			if(FC != null) {
				sb.append(FC.report());
				totalAttendances += FC.getAvgAtt();
			}
		}
		//Creates overall average line
		sb.append(String.format("\n%44sOverall average: %5.2f"," ", totalAttendances / fitProg.numOfClasses()));
		return sb.toString();
	}
}
