package com.gayathriarumugam.parentapp.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gayathriarumugam.parentapp.Adapters.TutorsAdapter;
import com.gayathriarumugam.parentapp.Model.Parent;
import com.gayathriarumugam.parentapp.Model.Tutor;
import com.gayathriarumugam.parentapp.R;
import com.gayathriarumugam.parentapp.ViewModel.FirebaseViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

public class TutorsListActivity extends AppCompatActivity implements TutorsAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    ArrayList<Tutor> tutorsList = new ArrayList<>();

    FirebaseViewModel firebaseViewModel;
    Parent parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutors_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.tutorsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences preferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("parent", "");
        parent = gson.fromJson(json, Parent.class);

        // Get a new or existing ViewModel from the ViewModelProvider.
        firebaseViewModel = ViewModelProviders.of(TutorsListActivity.this).get(FirebaseViewModel.class);
        firebaseViewModel.context = this;
        firebaseViewModel.getAllTutors().observe(TutorsListActivity.this, new Observer<ArrayList<Tutor>>() {
            @Override
            public void onChanged(ArrayList<Tutor> tutors) {
                if (!tutors.isEmpty()) {
                    tutorsList = tutors;
                    adapter = new TutorsAdapter(getApplicationContext(), tutorsList);
                    ((TutorsAdapter) adapter).setClickListener(TutorsListActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        // Get a new or existing ViewModel from the ViewModelProvider.
        firebaseViewModel = ViewModelProviders.of(TutorsListActivity.this).get(FirebaseViewModel.class);
        firebaseViewModel.context = this;
        firebaseViewModel.getAllTutors().observe(TutorsListActivity.this, new Observer<ArrayList<Tutor>>() {
            @Override
            public void onChanged(ArrayList<Tutor> tutors) {
                if (!tutors.isEmpty()) {
                    tutorsList = tutors;
                    adapter = new TutorsAdapter(getApplicationContext(), tutorsList);
                    ((TutorsAdapter) adapter).setClickListener(TutorsListActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

        @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(TutorsListActivity.this, BookNewSlot.class);
        intent.putExtra("tutor", tutorsList.get(position));
        startActivity(intent);
    }
}
