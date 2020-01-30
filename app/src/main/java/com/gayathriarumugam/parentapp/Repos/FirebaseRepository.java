package com.gayathriarumugam.parentapp.Repos;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.gayathriarumugam.parentapp.Model.BookedSlots;
import com.gayathriarumugam.parentapp.Model.Parent;
import com.gayathriarumugam.parentapp.Model.ParentBookedDetails;
import com.gayathriarumugam.parentapp.Model.Tutor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class FirebaseRepository {

    private static FirebaseRepository sInstance;
    private ArrayList<Tutor> tutorsList = new ArrayList<>();

    MutableLiveData<ArrayList<Tutor>>  allTutors;
    MutableLiveData<Parent> parentMutableLiveData;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferenceTutor = database.getReference("tutors");
    DatabaseReference databaseReferenceParent = database.getReference("parent");

    int bookingID = 5000;
    Parent parent;

    public static FirebaseRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new FirebaseRepository();
        }
        return sInstance;
    }

    public MutableLiveData<ArrayList<Tutor>> getAllTutors() {
        allTutors = new MutableLiveData<>();
        tutorsList.clear();
        loadAllTutors();
        return allTutors;
    }

    public MutableLiveData<Parent> getParent() {
        parentMutableLiveData = new MutableLiveData<>();
        loadParent();
        return parentMutableLiveData;
    }

    private void loadAllTutors() {
        databaseReferenceTutor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tutorsList.clear();
                for(DataSnapshot tutorSnapshot: dataSnapshot.getChildren()) {
                    String json = new Gson().toJson(tutorSnapshot.getValue());
                    JsonParser parser = new JsonParser();
                    JsonElement mJson =  parser.parse(json);
                    Gson gson = new Gson();
                    Tutor tutor  = gson.fromJson(mJson, Tutor.class);
                    tutorsList.add(tutor);
                }
                allTutors.postValue(tutorsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadParent() {
        databaseReferenceParent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String json = new Gson().toJson(dataSnapshot.getValue());
                JsonParser parser = new JsonParser();
                JsonElement mJson =  parser.parse(json);
                Gson gson = new Gson();
                parent  = gson.fromJson(mJson, Parent.class);
                parentMutableLiveData.postValue(parent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateSlots(ParentBookedDetails bookedByParent, BookedSlots bookedSlots) {
        int index = -1;
        for(int i = 0; i < tutorsList.size(); i++) {
            Tutor tutor = tutorsList.get(i);
            if(tutor.getName().equals(bookedByParent.getTutorName())) {
                index = i;
                bookingID = tutor.getBookedSlots().size();
            }
        }
        if( index != -1) {
            bookedSlots.setBookingID(1000+bookingID);
            bookedByParent.setBookingID(1000+bookingID);
            DatabaseReference ref = databaseReferenceTutor.child(String.valueOf(index)).child("bookedSlots");
            ref.child(String.valueOf(bookingID)).setValue(bookedSlots);

            int indexSlot = 0;
            if (parent.getBookedDetails() != null) {
                indexSlot = parent.getBookedDetails().size();
                databaseReferenceParent.child("bookedSlots").child(String.valueOf(indexSlot)).setValue(bookedByParent);
            }
            else{
                databaseReferenceParent.child("bookedSlots").child(String.valueOf(indexSlot)).setValue(bookedByParent);
            }
        }
    }

    public void deleteSlot(int bookingID) {
        int tutorIndex = -1;
        int slotIndex = -1;
        for(int i = 0; i < tutorsList.size(); i++) {
            Tutor tutor = tutorsList.get(i);
            for(int j = 0; j<tutor.getBookedSlots().size();j++) {
                if (tutor.getBookedSlots().get(j).getBookingID() == bookingID) {
                    slotIndex = j;
                    tutorIndex = i;
                }
            }
        }

        for(int k=0; k<parent.getBookedDetails().size();k++) {
            if (parent.getBookedDetails().get(k).bookingID == bookingID) {
                DatabaseReference ref = databaseReferenceParent.child("bookedSlots").child(String.valueOf(k));
                ref.removeValue();
            }
        }

        if( tutorIndex != -1 && slotIndex != -1) {
            DatabaseReference ref = databaseReferenceTutor.child(String.valueOf(tutorIndex)).child("bookedSlots");
            ref.child(String.valueOf(slotIndex)).removeValue();
        }
    }
}