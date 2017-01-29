package com.example.a.todolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.util.Calendar;
import android.widget.DatePicker;
import android.widget.Toast;

/**
 * Created by A on 2017-01-28.
 */
public class AddDateActivity extends Activity {

    DatePicker datePicker;
    String date;
    private int year;
    private int month;
    String m;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.add_date);


}
    public void onCancel(View view) {
        finish();
    }

    public void onSave(View view) {

        Intent intent = getIntent();

        DatePicker datePicker = (DatePicker)findViewById(R.id.datePicker);
        year = datePicker.getYear();
        month = datePicker.getMonth() + 1;
        day = datePicker.getDayOfMonth();

        switch(month){
            case 1:
                m = "Jan";
                break;

            case 2:
                m = "Feb";
                break;

            case 3:
                m = "March";
                break;

            case 4:
                m = "April";
                break;

            case 5:
                m = "May";
                break;

            case 6:
                m = "June";
                break;

            case 7:
                m = "July";
                break;

            case 8:
                m = "Aug";
                break;

            case 9:
                m = "Sept";
                break;

            case 10:
                m = "Oct";
                break;

            case 11:
                m = "Nov";
                break;

            case 12:
                m = "Dec";
                break;

        }

        date = m + " " + day + ", " + year;

        intent.putExtra("date", date);

        this.setResult(RESULT_OK, intent);

        finish();

    }
}
