package com.example.a.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static TimeTrackerAdapter timeTrackerAdapter;
    private TimeTrackerDatabaseHelper databaseHelper;
    private static final int TIME_ENTRY_REQUEST_CODE = 1;
    private static final int TIME_ENTRY_REQUEST_CODE2 = 2;
    private static long value;
    private static String time;
    private static String notes;
    private static String date;
    private static ListView listView;
    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;
    private static boolean deletedTableRow = true;
    private static AlertDialog.Builder clear;
    private static AlertDialog clear_alert = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign variable values
        databaseHelper = new TimeTrackerDatabaseHelper(this);
        listView = (ListView) findViewById(R.id.times_list);

        timeTrackerAdapter = new TimeTrackerAdapter(this, databaseHelper.getTimeRecordList());
        listView.setAdapter(timeTrackerAdapter);

        //Delete Task
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                value = id;
                AlertDialog.Builder delete = new AlertDialog.Builder(MainActivity.this);

                delete.setMessage("Are you sure you want to delete the task?");
                delete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletedTableRow = databaseHelper.delteTimeRecord(value);

                        //Refresh Activity
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                        if (deletedTableRow == true) {
                            Toast.makeText(MainActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Task NOT Deleted", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                delete.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog delete_alert = delete.create();
                delete_alert.show();
                return true;
            }
        });

        //Edit Task
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                value = id;
                AlertDialog.Builder edit = new AlertDialog.Builder(MainActivity.this);

                edit.setMessage("Are you sure you want to edit the task?");
                edit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                        startActivityForResult(intent, TIME_ENTRY_REQUEST_CODE2);
                    }
                });

                edit.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog edit_alert = edit.create();
                edit_alert.show();
            }
        });

        //ShakeEventListener to Clear Tasks
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {
                    clear = new AlertDialog.Builder(MainActivity.this);
                    clear.setMessage("Are you sure you want to clear Todo List?");
                    clear.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            databaseHelper.clearTimeRecord();

                            //Refresh Activity
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);

                            Toast.makeText(MainActivity.this, "Todo List cleared", Toast.LENGTH_SHORT).show();
                        }
                    });

                    clear.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    //Prevents alert dialog from displaying more than once
                    if((clear_alert == null) || (clear_alert.isShowing()) == false){
                        clear_alert = clear.create();
                        clear_alert.show();
                    }

                }



        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Add Tasks
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, TIME_ENTRY_REQUEST_CODE);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Clear button Activity
        if (id == R.id.clear_time_menu_item) {

            clear = new AlertDialog.Builder(MainActivity.this);
            clear.setMessage("Are you sure you want to clear Todo List?");
            clear.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    databaseHelper.clearTimeRecord();

                    //Refresh Activity
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                    Toast.makeText(MainActivity.this, "Todo List cleared", Toast.LENGTH_SHORT).show();
                }
            });

            clear.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            clear_alert = clear.create();
            clear_alert.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Save Tasks
        if(requestCode == TIME_ENTRY_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                notes = data.getStringExtra("notes");
                time = data.getStringExtra("time");
                date = data.getStringExtra("date");
                databaseHelper.saveTimeRecord(time,notes,date);
                //Save the new time record in the database, and update the cursor in the adapter
                timeTrackerAdapter.changeCursor(databaseHelper.getTimeRecordList());
                Toast.makeText(this, "New Task added", Toast.LENGTH_SHORT).show();

            }
        }

        //Update Tasks
        if(requestCode == TIME_ENTRY_REQUEST_CODE2){
            if(resultCode == RESULT_OK){
                notes = data.getStringExtra("notes");
                time = data.getStringExtra("time");
                date = data.getStringExtra("date");
                databaseHelper.updateTimeRecord(time, notes, date, value);
                //Save the new time record in the database, and update the cursor in the adapter
                timeTrackerAdapter.changeCursor(databaseHelper.getTimeRecordList());
                Toast.makeText(this, "Task edited", Toast.LENGTH_SHORT).show();


            }
        }


    }

    //ShakeEvent
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }


}
