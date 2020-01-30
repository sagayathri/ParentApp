package com.gayathriarumugam.parentapp.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gayathriarumugam.parentapp.Adapters.GridViewAdapter;
import com.gayathriarumugam.parentapp.Model.BookedSlots;
import com.gayathriarumugam.parentapp.Model.GetDistance;
import com.gayathriarumugam.parentapp.Model.Parent;
import com.gayathriarumugam.parentapp.Model.ParentBookedDetails;
import com.gayathriarumugam.parentapp.Model.Tutor;
import com.gayathriarumugam.parentapp.R;
import com.gayathriarumugam.parentapp.Repos.FirebaseRepository;
import com.gayathriarumugam.parentapp.ViewModel.FirebaseViewModel;
import com.gayathriarumugam.parentapp.ViewModel.GetAddressViewModel;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookNewSlot extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    GridView gridView;
    TextView tutorName, travelDistance;
    Spinner spinner, spinnerDays;
    Button btnSave, btnCancel, btnChangeAddress;

    Tutor tutor;
    Parent parent;
    String selectedSubject;
    GridViewAdapter gridViewAdapter;
    List<String> selectedList;
    GetAddressViewModel model;
    int travelTime;
    Double distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_newslot);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        gridView = findViewById(R.id.gridView);
        tutorName = findViewById(R.id.selectedTutorName);
        spinner = findViewById(R.id.spinner);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnChangeAddress = findViewById(R.id.btnChangeAddress);
        travelDistance = findViewById(R.id.distanceTV);
        spinnerDays = findViewById(R.id.spinnerDays);

        SharedPreferences preferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("parent", "");
        parent = gson.fromJson(json, Parent.class);

        tutor = (Tutor) getIntent().getSerializableExtra("tutor");
        tutorName.setText(tutor.getName());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tutor.getSubjects());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString() != "Select subject ") {
                    selectedSubject = adapterView.getItemAtPosition(i).toString();
                }
                else {
                    selectedSubject = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        List<String> slots = new ArrayList<String>();
        slots.add("Monday");
        slots.add("Tuesday");
        slots.add("Wednesday");
        slots.add("Thursday");
        slots.add("Friday");
        slots.add("Saturday");
        slots.add("Sunday");

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slots);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDays.setAdapter(dayAdapter);
        spinnerDays.setOnItemSelectedListener(this);

        // Get a new or existing ViewModel from the ViewModelProvider.
        model = ViewModelProviders.of(BookNewSlot.this).get(GetAddressViewModel.class);
        model.context = this;
        model.loadDistance(parent.getAddress().getPostcode(), tutor.getAddress().getPostcode());
        model.getDistance().observe(BookNewSlot.this, new Observer<GetDistance>() {
            @Override
            public void onChanged(GetDistance getDistance) {
                Double inMeters = getDistance.getMetres();
                 distance = inMeters * 0.000621371;
                //Assuming speed taken is 15m/h
                Double timeTaken = distance/15;
                travelTime = (int) (timeTaken * 100);
                travelDistance.setText("Travel time :   "+String.valueOf(travelTime)+" mins");
            }
        });

        selectedList = new ArrayList<>();
        selectedList = tutor.getAvailableDays().getMonday();
        gridViewAdapter = new GridViewAdapter(this, selectedList);
        gridView.setAdapter(gridViewAdapter);
        gridViewAdapter.travelTime = travelTime;

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> sessionBooked = gridViewAdapter.getBookedSession();
                List<BookedSlots> newSlotsList = new ArrayList<>();

                BookedSlots newSlot = new BookedSlots();
                newSlot.setBookingID(101);
                newSlot.setChildName("Emily");
                newSlot.setDate("31 Jan");
                newSlot.setDuration(60);
                newSlot.setSubject(selectedSubject);
                newSlot.setTimeFrom(sessionBooked.get(0));
                newSlot.setTimeTo(sessionBooked.get(1));
                newSlot.setPostcode(parent.getAddress().getPostcode());
                newSlot.setDistance(distance);
                newSlotsList.add(newSlot);
                tutor.setBookedSlots(newSlotsList);

                ParentBookedDetails bookedByParent = new ParentBookedDetails();
                bookedByParent.setBookingID(101);
                bookedByParent.setDate("31 Jan");
                bookedByParent.setDuration(60);
                bookedByParent.setSubject(selectedSubject);
                bookedByParent.setTimeFrom(sessionBooked.get(0));
                bookedByParent.setTimeTo(sessionBooked.get(1));
                bookedByParent.setPostcode(tutor.getAddress().getPostcode());
                bookedByParent.setTutorName(tutor.getName());

                Toast.makeText(getApplicationContext(),"Session booked", Toast.LENGTH_SHORT).show();

                //Adds to database
                FirebaseRepository.getInstance(getApplicationContext()).updateSlots(bookedByParent,newSlot);

//                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridViewAdapter.addNewValues(selectedList);
                gridViewAdapter.notifyDataSetChanged();
            }
        });
    }

    //onItemSelectedListener for spinner to select subject
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedDay = adapterView.getItemAtPosition(i).toString();
        switch (selectedDay){
            case "Monday":
                selectedList =tutor.getAvailableDays().getMonday();
                break;
            case "Tuesday":
                selectedList =tutor.getAvailableDays().getTuesday();
                break;
            case "Wednesday":
                selectedList =tutor.getAvailableDays().getWednesday();
                break;
            case "Thursday":
                selectedList =tutor.getAvailableDays().getThursday();
                break;
            case "Friday":
                selectedList =tutor.getAvailableDays().getFriday();
                break;
            case "Saturday":
                selectedList =tutor.getAvailableDays().getSaturday();
                break;
            case "Sunday":
                selectedList =tutor.getAvailableDays().getSunday();
                break;
            default:
                selectedList.add("No Slots");
                break;
        }
        gridViewAdapter.addNewValues(selectedList);
        gridViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}


}
