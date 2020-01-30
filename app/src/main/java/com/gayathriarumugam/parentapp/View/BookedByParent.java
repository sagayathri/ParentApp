package com.gayathriarumugam.parentapp.View;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.gayathriarumugam.parentapp.Model.Parent;
import com.gayathriarumugam.parentapp.Model.ParentBookedDetails;
import com.gayathriarumugam.parentapp.R;
import com.gayathriarumugam.parentapp.Repos.FirebaseRepository;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookedByParent extends AppCompatActivity {

    TextView childName, tutorName, bookedSessions, duration;
    Button cancel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booked_slot);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        childName = findViewById(R.id.childName);
        tutorName = findViewById(R.id.bookedTutorName);
        bookedSessions = findViewById(R.id.bookedSlots);
        duration = findViewById(R.id.sessionDuration);
        cancel = findViewById(R.id.btnCancelSession);

        SharedPreferences preferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("parent", "");
        Parent parent = gson.fromJson(json, Parent.class);
        final List<ParentBookedDetails> bookedDetailsList = parent.getBookedDetails();
        final ParentBookedDetails bookedDetails = bookedDetailsList.get(bookedDetailsList.size()-1);

        if (bookedDetails != null){
            childName.setText(parent.getChildDetails().getName());
            tutorName.setText(bookedDetails.getTutorName());
            bookedSessions.setText(bookedDetails.getDate() + " From " + bookedDetails.getTimeFrom() + " to " + bookedDetails.getTimeTo());
            duration.setText(String.valueOf(bookedDetails.getDuration()) + " minutes");

            //MARK:- If there is a time gap of 2 hours from booked hours it can be cancellable else can't.
            String fromTime = bookedDetails.getDate()+bookedDetails.getTimeFrom()+"2020";
            DateFormat formatter = new SimpleDateFormat("dd MMMHH:mmyyyy",Locale.getDefault());
            try {
                Date bookedTime = formatter.parse(fromTime);
                Date date = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.HOUR, 2);
                Date now = cal.getTime(); 
                if (bookedTime.compareTo(now) <= 0){
                    cancel.setText("");
                    cancel.setBackgroundColor(Color.WHITE);
                    cancel.setEnabled(false);
                }
                else {
                    cancel.setEnabled(true);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"Session cancelled", Toast.LENGTH_SHORT).show();
                    FirebaseRepository.getInstance(getApplicationContext()).deleteSlot(bookedDetails.getBookingID());
                    finish();
                }
            });
        }
        else {
            childName.setText("");
            tutorName.setText("");
            bookedSessions.setText("");
            duration.setText("");

            cancel.setText("");
            cancel.setBackgroundColor(Color.WHITE);
            cancel.setEnabled(false);

            Toast.makeText(this, "You haven't booked any sessions yet", Toast.LENGTH_LONG).show();
        }
    }
}
