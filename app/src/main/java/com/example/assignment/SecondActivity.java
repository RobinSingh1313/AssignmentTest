package com.example.assignment;
import static com.example.assignment.CalendarUtils.daysInWeekArray;
import static com.example.assignment.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.assignment.adapter.MainCategoryAdapter;
import com.example.assignment.adapter.RecyclerViewInterface;
import com.example.assignment.dao.dataModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener, RecyclerViewInterface {

    // Declare the UI elements
    private TextView monthYearText;
    private ImageView mainItems;
    private RecyclerView calendarRecyclerView;

    // Declare the data models for the main category adapter
    private ArrayList<dataModel> arrMainCategory = new ArrayList<>();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getSupportActionBar().setTitle("Header");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize the UI elements
        initWidgets();

        // Set the selected date to today's date
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate = LocalDate.now();
        }

        // Set up the calendar view
        setWeekView();

        // Add some temporary data to the main category adapter
        arrMainCategory.add(new dataModel(R.drawable.image_1,"Chicken"));
        arrMainCategory.add(new dataModel(R.drawable.image_2,"Fish"));
        arrMainCategory.add(new dataModel(R.drawable.image_3,"Meat"));
        arrMainCategory.add(new dataModel(R.drawable.image_1,"Fruits"));
        arrMainCategory.add(new dataModel(R.drawable.image_6,"Veg"));
        arrMainCategory.add(new dataModel(R.drawable.image_5,"Chicken"));

        // Set up the main category adapter
        MainCategoryAdapter mainCategoryAdapter = new MainCategoryAdapter(this, arrMainCategory, this);

        RecyclerView rv_main_category = findViewById(R.id.rv_main_category);
        rv_main_category.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rv_main_category.setAdapter(mainCategoryAdapter);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    /**
     * Initialize the UI elements
     */
    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        mainItems = findViewById(R.id.imges);
    }

    /**
     * Set up the week view
     */
    private void setWeekView() {
        // Set the month/year text to the selected date
      //  monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));

        // Get the days of the week for the selected date
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        // Set up the calendar adapter with the days of the week
        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);

        // Set up the layout manager for the calendar recycler view
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);

        // Set the calendar adapter and layout manager for the recycler view
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    /**
     * Handle the previous week button click
     * @param view The button view
     */
    public void previousWeekAction(View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        }
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        }
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }






    public void weeklyAction(View view)
    {
        startActivity(new Intent(this, WeekViewActivity.class));
    }


    @Override
    public void onItemclick(int position) {
        mainItems.setImageResource(arrMainCategory.get(position).img);

    }
}

