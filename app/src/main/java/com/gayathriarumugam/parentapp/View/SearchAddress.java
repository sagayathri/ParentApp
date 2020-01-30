package com.gayathriarumugam.parentapp.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Observable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gayathriarumugam.parentapp.Model.GetAddress;
import com.gayathriarumugam.parentapp.Model.Parent;
import com.gayathriarumugam.parentapp.R;
import com.gayathriarumugam.parentapp.ViewModel.GetAddressViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchAddress extends AppCompatActivity {

    TextView line1, line2, locality, city, county, country;
    EditText postcode;
    Button search, save, cancel;
    ListView listView;
    List<String> addresses = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    GetAddressViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_address);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        locality = findViewById(R.id.locality);
        city = findViewById(R.id.city);
        county = findViewById(R.id.county);
        country = findViewById(R.id.country);
        postcode = findViewById(R.id.postcode);
        search = findViewById(R.id.btnSearchAddress);
        save = findViewById(R.id.btnSaveAddress);
        cancel = findViewById(R.id.btnCancelAddress);
        listView = findViewById(R.id.addressListView);


        SharedPreferences preferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("parent", "");
        final Parent parent = gson.fromJson(json, Parent.class);

        // Get a new or existing ViewModel from the ViewModelProvider.
        model = ViewModelProviders.of(SearchAddress.this).get(GetAddressViewModel.class);
        model.context = this;
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.loadAddress(String.valueOf(parent.getAddress().getPostcode()));
                model.getAddresses().observe(SearchAddress.this, new Observer<GetAddress>() {
                    @Override
                    public void onChanged(GetAddress getAddress) {
                        addresses = getAddress.getAddresses();
                        Toast.makeText(getApplicationContext(), addresses.get(0), Toast.LENGTH_SHORT).show();
                        arrayAdapter = new ArrayAdapter<String>(SearchAddress.this, android.R.layout.simple_list_item_1, addresses);
                        listView.setAdapter(arrayAdapter);
                    }
                });
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3) {
                String selectedAddress=addresses.get(position);
                List<String> selectedAddressList = Arrays.asList(selectedAddress.split(","));
                line1.setText(selectedAddressList.get(0));
                line2.setText(selectedAddressList.get(1));
                locality.setText(selectedAddressList.get(4));
                city.setText(selectedAddressList.get(5));
                county.setText(selectedAddressList.get(6));
                country.setText("England");
            }
        });
    }
}
