package com.example.assignment;

import static com.example.assignment.CalendarUtils.daysInWeekArray;
import static com.example.assignment.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    // Widgets
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Initialize the widgets
        initWidgets();

        // Set up the initial week view
        setWeekView();
    }



    // Initialize the widgets
    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
    }

    // Set up the week view
    private void setWeekView()
    {
        // Set the month and year text
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));

        // Get the days in the selected week
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        // Set up the calendar adapter and layout manager
        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);

        // Set the adapter and layout manager for the calendar recycler view
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    // Handle the previous week button click
    public void previousWeekAction(View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Decrement the selected date by one week
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        }
        // Update the week view
        setWeekView();
    }

    // Handle the next week button click
    public void nextWeekAction(View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Increment the selected date by one week
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        }
        // Update the week view
        setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    // Handle the calendar item click
    @Override
    public void onItemClick(int position, LocalDate date) {

    }
}
