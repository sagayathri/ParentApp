package com.gayathriarumugam.parentapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.view.Gravity.CENTER;

public class GridViewAdapter extends BaseAdapter {

    private final Context mContext;
    private List<String> availbleSlots;
    private boolean isON = false;
    public List<String> bookedSession;
    public int travelTime;

    public GridViewAdapter(Context context, List<String> availbleSlots) {
        this.mContext = context;
        this.availbleSlots = availbleSlots;
    }

    public void addNewValues(List<String> availbleSlots){
        this.availbleSlots = availbleSlots;
    }

    @Override
    public int getCount() {
        return availbleSlots.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        Collections.sort(availbleSlots);
        System.out.println(Arrays.toString(availbleSlots.toArray()));

        final Button slotsButton = new Button(mContext);
        slotsButton.setLayoutParams(new GridView.LayoutParams(250,150));
        slotsButton.setBackgroundColor(Color.WHITE);
        slotsButton.setTextColor(Color.BLACK);
        slotsButton.setText(availbleSlots.get(position));
        slotsButton.setTextSize(18);
        slotsButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        slotsButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                List<String> slots = new ArrayList<>();
                for (int i = 0; i<parent.getChildCount(); i++) {
                    View c = parent.getChildAt(i);
                    c.setBackgroundColor(Color.WHITE);
                }
                for (int i = 0; i<parent.getChildCount(); i++) {
                    View c = parent.getChildAt(i);
                    if (i == position) {
                        c.setBackgroundColor(Color.YELLOW);
                        String str = availbleSlots.get(position);
                        DateFormat formatter = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = formatter.parse(str);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                            slots.add(availbleSlots.get(position));
                            for (int n = 0; n < 5; n++) {
                                cal.add(Calendar.MINUTE, 30);
                                String bookedUntil = formatter.format(cal.getTime());
                                slots.add(bookedUntil);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                for (int i=0; i<parent.getChildCount();i++) {
                    Button b = (Button) parent.getChildAt(i);
                        for (int k =0;k<slots.size();k++){
                            String str = b.getText().toString();
                            String str1 = slots.get(k);
                        if (str1.equals(str)) {
                            b.setBackgroundColor(Color.GRAY);
                        }
                    }
                }
                setBookedSession(slots);
            }
        });
        return slotsButton;
    }

    public List<String> getBookedSession() {
        return bookedSession;
    }

    public void setBookedSession(List<String> bookedSession) {
        this.bookedSession = bookedSession;
    }
}
