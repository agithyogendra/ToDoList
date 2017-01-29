package com.example.a.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by A on 2017-01-07.
 */
public class AddTimeActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_time);
    }

    public void onCancel(View view) {
        finish();
    }

    public void onSave(View view) {

        Intent intent = getIntent();

        TimePicker time_picker = (TimePicker) findViewById(R.id.time_picker);
        int selected_hour = time_picker.getCurrentHour();
        int selected_minute = time_picker.getCurrentMinute();
        String AM_PM = "";
        String time;

        if (selected_hour > 12) {
            AM_PM = "PM";
            selected_hour = selected_hour - 12;

        } else if (selected_hour <= 12) {
            AM_PM = "AM";
            if (selected_hour == 12) {
                AM_PM = "PM";
            } else if (selected_hour == 0) {
                selected_hour = 12;
            }
        }

        String hour = (String) ((selected_hour < 10) ? "0" + selected_hour : String.valueOf(selected_hour));
        String minute = (String) ((selected_minute < 10) ? "0" + selected_minute : String.valueOf(selected_minute));
        time = hour + " : " + minute + " " + AM_PM;
        intent.putExtra("time", time);

        this.setResult(RESULT_OK, intent);

        finish();

    }

}
