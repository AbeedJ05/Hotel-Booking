package com.example.hotelbooking.Api;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitDeleteRoom {
    private static RetrofitDeleteRoom instance = null;
    private APIService myAPIService;

    private RetrofitDeleteRoom() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.deleteRoom)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();
        myAPIService = retrofit.create(APIService.class);

    }
    public static synchronized RetrofitDeleteRoom getInstance() {
        if (instance == null) {
            instance = new RetrofitDeleteRoom();
        }
        return instance;
    }

    public APIService getMyApi() {
        return myAPIService;
    }

}
