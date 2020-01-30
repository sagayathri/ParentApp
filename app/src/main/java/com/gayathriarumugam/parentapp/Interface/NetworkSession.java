package com.gayathriarumugam.parentapp.Interface;

import android.content.Context;
import android.util.Log;

import com.gayathriarumugam.parentapp.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkSession {

    static String URL = String.valueOf(R.string.urlPath);
    static String API_KEY = String.valueOf(R.string.API_Key);
    String postcode;

    public NetworkSession(String postcode) {
        this.postcode = postcode;
    }

    public static JSONObject getJSON(Context context, String postcode){

        try{
            java.net.URL url = new URL(URL+postcode+"?api-key="+API_KEY);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //do something with response
            System.out.println("Response code : "+conn.getResponseCode());
            System.out.println(" Response Message : " + conn.getResponseMessage());

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                // print result
                System.out.println(response.toString());

                //clean up
                in.close();
                conn.disconnect();

                JSONObject reponseJson = new JSONObject(response.toString());
                return reponseJson;
            } else {
                System.out.println("GET error");
                return null;
            }

        }catch (Exception e){
            Log.d("err", ""+e);
            return null;
        }
    }
}
