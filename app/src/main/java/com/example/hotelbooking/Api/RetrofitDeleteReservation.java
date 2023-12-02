package com.example.hotelbooking.Api;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitDeleteReservation {
    private static RetrofitDeleteReservation instance = null;
    private APIService myAPIService;

    private RetrofitDeleteReservation() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.deleteReservation)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();
        myAPIService = retrofit.create(APIService.class);

    }

    public static synchronized RetrofitDeleteReservation getInstance() {
        if (instance == null) {
            instance = new RetrofitDeleteReservation();
        }
        return instance;
    }

    public APIService getMyApi() {
        return myAPIService;
    }

}
