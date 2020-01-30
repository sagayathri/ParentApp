package com.gayathriarumugam.parentapp.Interface;

import com.gayathriarumugam.parentapp.R;
import com.gayathriarumugam.parentapp.Utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient sharedInstance;
    Retrofit retrofit;
    Constants constants = new Constants();

    //Creates a shared instance of RetrofitClient class
    public static synchronized RetrofitClient getInstance() {
        if (sharedInstance == null) {
            sharedInstance = new RetrofitClient();
        }
        return sharedInstance;
    }

    //Creating a retrofit object for Address
    public Retrofit apiServer() {
        String DEV_BASE_URL = constants.getUrlPath();
        retrofit = new Retrofit.Builder()
                .baseUrl(DEV_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    //Creating a retrofit object for Distance
    public Retrofit apiServerDistance() {
        String DEV_BASE_URL = constants.getUrlDistance();
        retrofit = new Retrofit.Builder()
                .baseUrl(DEV_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    //creating the api interface
    public FetchFromAPI getFetchFromAPI() {
        Retrofit database = sharedInstance.apiServer();
        return database.create(FetchFromAPI.class);
    }

    //creating the api interface
    public FetchFromAPI getFetchDistance() {
        Retrofit database = sharedInstance.apiServerDistance();
        return database.create(FetchFromAPI.class);
    }
}
