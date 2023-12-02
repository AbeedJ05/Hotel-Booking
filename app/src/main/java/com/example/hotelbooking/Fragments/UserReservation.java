package com.example.hotelbooking.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hotelbooking.Activities.MainActivity;
import com.example.hotelbooking.Api.RetrofitUserReservation;
import com.example.hotelbooking.Model.Reservation;
import com.example.hotelbooking.R;
import com.example.hotelbooking.SharedPref.SharedPrefManager;
import com.example.hotelbooking.adapter.HotelAdapter;
import com.example.hotelbooking.adapter.ReservationAdapter;
import com.example.hotelbooking.adapter.UserReservationAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserReservation extends Fragment {
    UserReservationAdapter reservationAdapter;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_user_reservation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.rvuserres);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);


        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Recently");

        //Enable the back button
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Handle back button click
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.replaceFragment(new HomeFragment());
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Set the layout manager

        int userID = SharedPrefManager.getInstance(getContext()).getUsers().getId();
        fetchUserReservation(userID);

    }//endOnCreate

    private void fetchUserReservation(int userId) {
        Call<List<Reservation>> call = RetrofitUserReservation.getInstance().getMyApi().getUserReservations(userId);
        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                if (response.isSuccessful()) {
                    List<Reservation> reservationsList = response.body();
                    if (reservationsList != null) {
                        reservationAdapter = new UserReservationAdapter(reservationsList, getContext());
                        recyclerView.setAdapter(reservationAdapter);
                    }
                    Toast.makeText(getContext(), "Connection successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle API error
                    Toast.makeText(getContext(), "Failed to fetch reservations", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                Log.e("NO reservations for this user", t.getMessage());
            }
        });
    }

}
