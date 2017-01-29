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
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_date);

        datePicker = (DatePicker)findViewById(R.id.datePicker);

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        date = day + "/" + month + 1 + "/" + year;
        datePicker.updateDate(year, month, day);

    }



    public void onCancel(View view) {
        finish();
    }

    public void onSave(View view) {

        Intent intent = getIntent();

        intent.putExtra("date", date);

        this.setResult(RESULT_OK, intent);

        finish();

    }
}
