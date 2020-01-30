package com.gayathriarumugam.parentapp.ViewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.gayathriarumugam.parentapp.Interface.FetchFromAPI;
import com.gayathriarumugam.parentapp.Interface.RetrofitClient;
import com.gayathriarumugam.parentapp.Model.GetAddress;
import com.gayathriarumugam.parentapp.Model.GetDistance;
import com.gayathriarumugam.parentapp.R;
import com.gayathriarumugam.parentapp.Utils.Constants;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetAddressViewModel extends AndroidViewModel {

    public Context context;
    Constants constants;

    public MutableLiveData<GetAddress> getAddressMutableLiveData;
    public MutableLiveData<GetDistance> getDistanceMutableLiveData;

    public GetAddressViewModel(Application application) {
        super(application);
        this.constants = new Constants();
    }

    public MutableLiveData<GetAddress> getAddresses() {
        if (getAddressMutableLiveData == null) {
            getAddressMutableLiveData = new MutableLiveData<GetAddress>();
        }
        return getAddressMutableLiveData;
    }

    public MutableLiveData<GetDistance> getDistance() {
        if (getDistanceMutableLiveData == null) {
            getDistanceMutableLiveData = new MutableLiveData<GetDistance>();
        }
        return getDistanceMutableLiveData;
    }

    // This method is using Retrofit to get the JSON data from URL
    public void loadAddress(String postcode) {
        Call<GetAddress> call = RetrofitClient.getInstance().getFetchFromAPI().getAddress(postcode, constants.getApiKey());
        call.enqueue(new Callback<GetAddress>() {
            @Override
            public void onResponse(Call<GetAddress> call, Response<GetAddress> response) {
                if (response.code() == 200) {
                    try {
                        getAddressMutableLiveData.postValue(response.body());
                    } catch (Error error) {
                        error.printStackTrace();
                    }
                } else if (response.code() == 400) {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplication(), "Your postcode is not valid", Toast.LENGTH_SHORT).show();
                }
                else if (response.code() == 404) {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplication(), "No addresses could be found for this postcode", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplication(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAddress> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }

    // This method is using Retrofit to get the JSON data from URL
    public void loadDistance(String from, String to) {
        Call<GetDistance> call = RetrofitClient.getInstance().getFetchDistance().getDistance(from, to, constants.getApiKey());
        call.enqueue(new Callback<GetDistance>() {
            @Override
            public void onResponse(Call<GetDistance> call, Response<GetDistance> response) {
                if (response.code() == 200) {
                    try {
                        getDistanceMutableLiveData.postValue(response.body());
                        Log.d("Response", String.valueOf(getDistanceMutableLiveData));
                    } catch (Error error) {
                        Log.d("Err", String.valueOf(error));
                        error.printStackTrace();
                    }
                } else {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplication(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetDistance> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }
}