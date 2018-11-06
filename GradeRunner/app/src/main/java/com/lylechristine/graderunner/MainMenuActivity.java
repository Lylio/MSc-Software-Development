package com.lylechristine.graderunner;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {

    DatabaseHelper courseDB;

    Button btnAddData, btnViewData, btnUpdateData, btnDelete;
    EditText etNewCourse, etAssignment, etDueDate, etID;

    //onCreate method runs when the activity launches
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        courseDB = new DatabaseHelper(this);

        etNewCourse = (EditText) findViewById(R.id.etNewExam);
        etAssignment = (EditText) findViewById(R.id.etGradesAssignment);
        etDueDate = (EditText) findViewById(R.id.etDueDate);
        etID = (EditText) findViewById(R.id.etID);
        btnAddData = (Button) findViewById(R.id.btnAddData);
        btnViewData = (Button) findViewById(R.id.btnViewData);
        btnUpdateData = (Button) findViewById(R.id.btnUpdateData);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        AddData();
        ViewData();
        UpdateData();
        DeleteData();

    }

    //Creates 3-dot menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //Creates 3-dot menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.grades:
                Intent gradesIntent = new Intent(this, GradesActivity.class);
                this.startActivity(gradesIntent);
                return true;
            case R.id.attendance:
                Intent attendanceIntent = new Intent(this, AttendanceActivity.class);
                this.startActivity(attendanceIntent);
                return true;
            case R.id.representative:
                Intent representativeIntent = new Intent(this, RepresentativeActivity.class);
                this.startActivity(representativeIntent);
                return true;
            case R.id.exams:
                Intent examsIntent = new Intent(this, ExamsActivity.class);
                this.startActivity(examsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    //Writes data to database when Add button is clicked
    public void AddData() {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newCourse = etNewCourse.getText().toString();
                String assignment = etAssignment.getText().toString();
                String dueDate = etDueDate.getText().toString();


                boolean insertData = courseDB.addData(newCourse, assignment, dueDate);

                if (insertData == true) {
                    Toast.makeText(MainMenuActivity.this, "Data Successfully Added!", Toast.LENGTH_LONG).show();
                    etNewCourse.setText("");
                    etAssignment.setText("");
                    etDueDate.setText("");

                } else {
                    Toast.makeText(MainMenuActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    //Shows pop-up of database information when View button is clicked
    public void ViewData() {
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor data = courseDB.showData();

                if (data.getCount() == 0) {
                    display("Error", "No Data Found.");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (data.moveToNext()) {
                    buffer.append("ID: " + data.getString(0) + "\n");
                    buffer.append("Course: " + data.getString(1) + "\n");
                    buffer.append("Assignment: " + data.getString(2) + "\n");
                    buffer.append("Due date: " + data.getString(3) + "\n");
                    buffer.append("\n");


                }

                display("All Stored Data:", buffer.toString());
            }
        });
    }

    //Formats how the database information is presented in the view pop-up
    public void display(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    //Overwrites data in the database when the Update button is clicked
    public void UpdateData() {
        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = etID.getText().toString().length();
                if (temp > 0) {
                    boolean update = courseDB.updateData(etID.getText().toString(), etNewCourse.getText().toString(), etAssignment.getText().toString(), etDueDate.getText().toString());
                    if (update == true) {
                        Toast.makeText(MainMenuActivity.this, "Successfully updated data", Toast.LENGTH_LONG).show();
                        etNewCourse.setText("");
                        etAssignment.setText("");
                        etDueDate.setText("");
                        etID.setText("");
                    } else {
                        Toast.makeText(MainMenuActivity.this, "ID not found", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainMenuActivity.this, "You must enter an ID to update", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Deletes a row in the database based on an ID number when the Delete button is clicked
    public void DeleteData() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = etID.getText().toString().length();
                if (temp > 0) {
                    Integer deleteRow = courseDB.deleteData(etID.getText().toString());
                    if (deleteRow > 0) {
                        Toast.makeText(MainMenuActivity.this, "Successfully deleted the data!", Toast.LENGTH_LONG).show();
                        etID.setText("");
                    } else {
                        Toast.makeText(MainMenuActivity.this, "ID not found", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainMenuActivity.this, "You must enter an ID to delete", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
