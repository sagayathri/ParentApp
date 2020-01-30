package com.gayathriarumugam.parentapp.Interface;

import android.database.Observable;

import com.gayathriarumugam.parentapp.Model.GetAddress;
import com.gayathriarumugam.parentapp.Model.GetDistance;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FetchFromAPI {

    @GET("{postcode}")
    Call<GetAddress>
    getAddress(@Path("postcode") String postcode,
               @Query("api-key") String API_KEY);


    @GET("{postcode_from}/{postcode_to}")
    Call<GetDistance>
    getDistance(@Path("postcode_from") String from,
               @Path("postcode_to") String to,
               @Query("api-key") String API_KEY);
}
