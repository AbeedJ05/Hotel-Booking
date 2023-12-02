package com.example.hotelbooking.Api;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUpdateRooms {
    private static RetrofitUpdateRooms instance = null;
    private APIService myAPIService;

    private RetrofitUpdateRooms() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.update_Rooms)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();
        myAPIService = retrofit.create(APIService.class);

    }

    public static synchronized RetrofitUpdateRooms getInstance() {
        if (instance == null) {
            instance = new RetrofitUpdateRooms();
        }
        return instance;
    }

    public APIService getMyApi() {
        return myAPIService;
    }
}
