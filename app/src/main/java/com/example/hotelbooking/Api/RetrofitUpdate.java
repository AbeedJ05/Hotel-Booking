package com.example.hotelbooking.Api;


import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUpdate {

    private static RetrofitUpdate instance = null;
    private APIService myAPIService;

    private RetrofitUpdate() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.update_Url)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();
        myAPIService = retrofit.create(APIService.class);

    }

    public static synchronized RetrofitUpdate getInstance() {
        if (instance == null) {
            instance = new RetrofitUpdate();
        }
        return instance;
    }

    public APIService getMyApi() {
        return myAPIService;
    }

}
