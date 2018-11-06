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

public class ExamsActivity extends AppCompatActivity {

    ExamDatabaseHelper examsDB;

    Button btnAddData, btnViewData, btnUpdateData, btnDelete;
    EditText etNewExam, etExamDate, etExamTime, etExamLocation, etExamDuration, etExamInfo, etID;

    //onCreate method runs when the activity launches
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);

        examsDB = new ExamDatabaseHelper(this);

        etNewExam = (EditText) findViewById(R.id.etNewExam);
        etExamDate = (EditText) findViewById(R.id.etExamDate);
        etExamTime = (EditText) findViewById(R.id.etExamTime);
        etExamLocation = (EditText) findViewById(R.id.etExamLocation);
        etExamDuration = (EditText) findViewById(R.id.etExamDuration);
        etExamInfo = (EditText) findViewById(R.id.etExamInfo);
        etID = (EditText) findViewById(R.id.etID);
        btnAddData = (Button) findViewById(R.id.btnAddData);
        btnViewData = (Button) findViewById(R.id.btnViewData);
        btnUpdateData = (Button) findViewById(R.id.btnUpdateData);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        AddData();
        ViewData();
        UpdateData();
        DeleteData();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

                String newExam = etNewExam.getText().toString();
                String date = etExamDate.getText().toString();
                String time = etExamTime.getText().toString();
                String location = etExamLocation.getText().toString();
                String duration = etExamDuration.getText().toString();
                String info = etExamInfo.getText().toString();


                boolean insertData = examsDB.addData(newExam, date, time, location, duration, info);

                if (insertData == true) {
                    Toast.makeText(ExamsActivity.this, "Data Successfully Added!", Toast.LENGTH_LONG).show();
                    etNewExam.setText("");
                    etExamDate.setText("");
                    etExamTime.setText("");
                    etExamLocation.setText("");
                    etExamDuration.setText("");
                    etExamInfo.setText("");

                } else {
                    Toast.makeText(ExamsActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Shows pop-up of database information when View button is clicked
    public void ViewData() {
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor data = examsDB.showData();

                if (data.getCount() == 0) {
                    display("Error", "No Data Found.");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (data.moveToNext()) {
                    buffer.append("ID: " + data.getString(0) + "\n");
                    buffer.append("Course: " + data.getString(1) + "\n");
                    buffer.append("Date: " + data.getString(2) + "\n");
                    buffer.append("Time: " + data.getString(3) + "\n");
                    buffer.append("Location: " + data.getString(4) + "\n");
                    buffer.append("Duration: " + data.getString(5) + "\n");
                    buffer.append("Info: " + data.getString(6) + "\n");
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
                    boolean update = examsDB.updateData(etID.getText().toString(), etNewExam.getText().toString(), etExamDate.getText().toString(), etExamTime.getText().toString(), etExamLocation.getText().toString(), etExamDuration.getText().toString(), etExamInfo.getText().toString());
                    if (update == true) {
                        Toast.makeText(ExamsActivity.this, "Successfully updated data", Toast.LENGTH_LONG).show();
                        etNewExam.setText("");
                        etExamDate.setText("");
                        etExamTime.setText("");
                        etExamLocation.setText("");
                        etExamDuration.setText("");
                        etExamInfo.setText("");
                        etID.setText("");
                    } else {
                        Toast.makeText(ExamsActivity.this, "ID not found", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ExamsActivity.this, "You must enter an ID to update", Toast.LENGTH_LONG).show();
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
                    Integer deleteRow = examsDB.deleteData(etID.getText().toString());
                    if (deleteRow > 0) {
                        Toast.makeText(ExamsActivity.this, "Successfully deleted the data!", Toast.LENGTH_LONG).show();
                        etID.setText("");
                    } else {
                        Toast.makeText(ExamsActivity.this, "ID not found", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ExamsActivity.this, "You must enter an ID to delete", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
