package com.example.hotelbooking.Api;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitDeleteUser {


        private static RetrofitDeleteUser instance = null;
        private APIService myAPIService;

        private RetrofitDeleteUser() {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.DELETE_USER)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .build();
            myAPIService = retrofit.create(APIService.class);

        }

        public static synchronized RetrofitDeleteUser getInstance() {
            if (instance == null) {
                instance = new RetrofitDeleteUser();
            }
            return instance;
        }

        public APIService getMyApi() {
            return myAPIService;
        }

    }


