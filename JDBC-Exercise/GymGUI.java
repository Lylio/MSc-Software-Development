import javax.swing.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;


public class GymGUI extends JFrame implements ActionListener {

	private JLabel memberIDLabel, courseNameLabel;
	private JTextField memberIDField;
	private JButton bookButton, membersButton, coursesButton, bookingsButton;
	private JTextArea textArea;
	private DBLocalConnection dbconnection = new DBLocalConnection();
	JComboBox jComboBox;
	JScrollPane scrollPane = new JScrollPane();
	private int memberID;
	private int courseid;

	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 400;

	public GymGUI() throws IOException {

		//Connects to database
		dbconnection.connect();

		//Creates JComboBox
		String[] courses = dbconnection.getCourseArray();
		jComboBox = new JComboBox(courses); 

		//Creates frame elements
		createMemberIDField();
		createCourseLabel();
		createBookButton();
		createMembersButton();
		createCoursesButton();
		createBookingsButton();
		createTextArea();
		createPanel();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);

		//Initialises frame properties
		this.setTitle("Glasgow Gym");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);

	}

	//Creates member ID label and field
	private void createMemberIDField() {

		memberIDLabel = new JLabel("Member ID: ");
		final int FIELD_WIDTH = 10;
		memberIDField = new JTextField(FIELD_WIDTH);
	}

	//Creates course name label
	private void createCourseLabel() {

		courseNameLabel = new JLabel("    Course Name: ");
	}

	//Creates 'confirm booking' button and attaches listener
	private void createBookButton() {

		bookButton = new JButton("Confirm Booking");
		bookButton.addActionListener(this);
	}

	//Creates 'view members' button and attaches listener
	private void createMembersButton() {
		membersButton = new JButton("View Members");
		membersButton.addActionListener(this);
	}

	//Creates 'view courses' button and attaches listener
	private void createCoursesButton() {

		coursesButton = new JButton("View Courses");
		coursesButton.addActionListener(this);
	}

	//Creates 'view bookings' button and attaches listener
	private void createBookingsButton() {

		bookingsButton = new JButton("View Bookings");
		bookingsButton.addActionListener(this);
	}

	//Creates text area with vertical scroll to display database information
	private void createTextArea() {

		textArea = new JTextArea(7, 40);
		scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
	}

	// Creates panels and attach labels, fields and buttons
	private void createPanel() throws IOException {

		//Gym logo imported from URL
		URL url = new URL("http://i64.tinypic.com/28v8zs3.jpg");
		Image image = ImageIO.read(url);
		JLabel logoLabel = new JLabel(new ImageIcon(image));

		setLayout(new GridLayout(3, 1));

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();

		//Panel 1: member ID label/field, course name label, combo box and 'confirm booking' button
		panel1.add(memberIDLabel);
		panel1.add(memberIDField);
		panel1.add(courseNameLabel);
		panel1.add(jComboBox);
		panel1.add(bookButton);

		//Panel 2: text area
		panel2.add(scrollPane);

		//Panel 3: 'view members', 'view courses' and 'view bookings' buttons; gym logo
		panel3.add(membersButton);
		panel3.add(coursesButton);
		panel3.add(bookingsButton);
		panel3.add(logoLabel);

		//Attaches panels to frame
		this.add(panel1);
		this.add(panel2);
		this.add(panel3);

		//Sets GUI colour to pale blue
		panel1.setBackground(new Color(209, 249, 247));
		panel2.setBackground(new Color(209, 249, 247));
		panel3.setBackground(new Color(209, 249, 247));

	}

	//Checks if entered membership ID is legal - cannot be empty and must be an integer
	private boolean checkMemberID()
	{
		//Checks if keyword field is empty
		if(memberIDField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Membership ID cannot be empty - please try again", "Blank membership ID error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		//Checks if membership ID is an integer
		try {
			Integer.parseInt(memberIDField.getText());
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Membership ID must be a number - please try again", "Non-number membership ID error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true; 
	}

	//Button actions
	@Override
	public void actionPerformed(ActionEvent e) {

		//New booking button
		if(e.getSource() == bookButton) {

			String boxValue = jComboBox.getSelectedItem().toString();

			/*If member ID is legal and current number of bookings does not exceed the maximum places for the course, 
			then the booking is processed*/
			if(checkMemberID() && dbconnection.checkPlaces(boxValue)) {
				memberID = Integer.parseInt(memberIDField.getText());
				dbconnection.getMaxPlaces(boxValue);

				//Checks for course ID value against course name
				if(boxValue.equals("Aerobics")) {courseid = 106;}
				else if(boxValue.equals("Cardio")) {courseid = 104;}
				else if(boxValue.equals("Karate")) {courseid = 102;}
				else if(boxValue.equals("Pilates")) {courseid = 90;}
				else if(boxValue.equals("Spinning")) {courseid = 17;}
				else if(boxValue.equals("Tai Chi")) {courseid = 111;}
				else if(boxValue.equals("Yoga")) {courseid = 109;}

				dbconnection.newBooking(memberID, courseid);

				//Clear membership ID field after new booking has been processed
				memberIDField.setText("");

				//Display updated list of bookings
				textArea.setText(dbconnection.getBookings());
			}

		}

		//View members button
		if(e.getSource() == membersButton) {
			textArea.setText(dbconnection.getMemberNames());
		}

		//View courses button
		if(e.getSource() == coursesButton) {
			textArea.setText(dbconnection.getCourses());
		}

		//View bookings button
		if(e.getSource() == bookingsButton) {
			textArea.setText(dbconnection.getBookings());
		}
	}

}
