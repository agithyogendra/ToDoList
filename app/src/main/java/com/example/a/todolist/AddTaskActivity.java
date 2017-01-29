package com.example.a.todolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class AddTaskActivity extends Activity {

    private static final int TIME_ENTRY_REQUEST_CODE = 1;
    private static final int TIME_ENTRY_REQUEST_CODE2 = 2;
    String time;
    String date;
    Button add_time;
    Button add_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_task);
    }

    protected void onAddTime(View view){
        Intent intent = new Intent(AddTaskActivity.this, AddTimeActivity.class);
        startActivityForResult(intent, TIME_ENTRY_REQUEST_CODE);

    }

    protected void onAddDate(View view){
        Intent intent = new Intent(AddTaskActivity.this, AddDateActivity.class);
        startActivityForResult(intent, TIME_ENTRY_REQUEST_CODE2);

    }


    public void onCancel(View view) {
        finish();
    }

    public void onSave(View view) {
    /*Package time, date, and note data to be sent to main*/
        Intent intent = getIntent();

        intent.putExtra("time",time);

        intent.putExtra("date", date);

        EditText notesView = (EditText) findViewById(R.id.notes_view);
        intent.putExtra("notes", notesView.getText().toString());;

        this.setResult(RESULT_OK, intent);

        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Retrieve Time data from AddTimeActivity
        if (requestCode == TIME_ENTRY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                time = data.getStringExtra("time");
                /*Set button text*/
                add_time = (Button)findViewById(R.id.add_time);
                add_time.setText(time);
            }
        }

        //Retrieve Time data from AddTimeActivity
        if (requestCode == TIME_ENTRY_REQUEST_CODE2) {
            if (resultCode == RESULT_OK) {
                date = data.getStringExtra("date");
                /*Set button text*/
                add_date = (Button)findViewById(R.id.add_date);
                add_date.setText(date);
            }
        }

    }
}
