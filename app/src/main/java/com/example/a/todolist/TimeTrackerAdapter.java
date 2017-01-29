package com.example.a.todolist;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class TimeTrackerAdapter extends CursorAdapter {

    public TimeTrackerAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //The LayoutInflater is retrieved and the layout is inflated and returned
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.time_list_item, parent, false);
        return view;


    }

    @Override
    //Display the values in the selected row
    public void bindView(View view, Context context, Cursor cursor) {
        //The time, date and notes fields are retrieved and populated with data from getString calls to cursor
        TextView timeTextView = (TextView) view.findViewById(R.id.time_view);
        timeTextView.setText(cursor.getString(1));

        TextView dateTextView = (TextView) view.findViewById(R.id.date_view);
        dateTextView.setText(cursor.getString(2));

        TextView notesTextView = (TextView) view.findViewById(R.id.notes_view);
        notesTextView.setText(cursor.getString(3));

    }
}
