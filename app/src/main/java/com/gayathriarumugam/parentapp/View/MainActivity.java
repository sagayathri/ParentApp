package com.gayathriarumugam.parentapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gayathriarumugam.parentapp.Adapters.AccountAdapter;
import com.gayathriarumugam.parentapp.Adapters.TutorsAdapter;
import com.gayathriarumugam.parentapp.Model.Parent;
import com.gayathriarumugam.parentapp.Model.Tutor;
import com.gayathriarumugam.parentapp.R;
import com.gayathriarumugam.parentapp.ViewModel.FirebaseViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AccountAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    FirebaseViewModel firebaseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // data to populate the RecyclerView with
        ArrayList<String> accountList = new ArrayList<>();
        accountList.add("My Profile");
        accountList.add("Child Details");
        accountList.add("Add Schedule");
        accountList.add("Booked Schedules");
        accountList.add("Edit Address");
        accountList.add("Exam Summary");
        accountList.add("Refer & Earn");
        accountList.add("About Sophia");
        accountList.add("Share App");
        accountList.add("Logout");

        recyclerView = (RecyclerView) findViewById(R.id.accountRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AccountAdapter(this, accountList);
        ((AccountAdapter) adapter).setClickListener(this);
        recyclerView.setAdapter(adapter);

        // Get a new or existing ViewModel from the ViewModelProvider.
        firebaseViewModel = ViewModelProviders.of(MainActivity.this).get(FirebaseViewModel.class);
        firebaseViewModel.context = this;
        firebaseViewModel.getParent().observe(MainActivity.this, new Observer<Parent>() {
            @Override
            public void onChanged(Parent parent) {
                SharedPreferences preferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                Gson gson = new Gson();
                String parentJSON = gson.toJson(parent);
                editor.putString("parent", parentJSON);
                editor.commit();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent;
        switch (position) {
            case 2:

                intent = new Intent(MainActivity.this, TutorsListActivity.class);
                startActivity(intent);
                break;
            case 3:
                SharedPreferences preferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
                Gson gson = new Gson();
                String json = preferences.getString("parent", "");
                Parent parent = gson.fromJson(json, Parent.class);
                intent = new Intent(MainActivity.this, BookedByParent.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(MainActivity.this, SearchAddress.class);
                startActivity(intent);
                break;
            case 9:
                Toast.makeText(this,position,Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this,"Nothing to show",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
