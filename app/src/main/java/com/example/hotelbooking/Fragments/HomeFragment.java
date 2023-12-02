package com.example.hotelbooking.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.Activities.MainActivity;
import com.example.hotelbooking.Activities.Sign_In;
import com.example.hotelbooking.Api.RetrofitHotels;
import com.example.hotelbooking.Model.Hotel;
import com.example.hotelbooking.R;
import com.example.hotelbooking.SharedPref.SharedPrefManager;
import com.example.hotelbooking.adapter.HotelAdapter;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private TextView navUsername;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private HotelAdapter hotelAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toolbar toolbar = view.findViewById(R.id.toolbar);
//        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        if (!SharedPrefManager.getInstance(requireContext()).isLoggedIn()) {
            // If not logged in, navigate to the Sign In activity and finish this activity
            Intent intent = new Intent(requireContext(), Sign_In.class);
            startActivity(intent);
            requireActivity().finish();
        }

        navUsername = view.findViewById(R.id.UserNameHome);
        // Set the user information in the TextViews
        setInfoHeader();

        recyclerView = (RecyclerView) view.findViewById(R.id.revHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //search view
        searchView =(SearchView) view.findViewById(R.id.search_view);
        searchView.clearFocus();

        //methods to Call Search view
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                hotelAdapter.filter(newText);
                return false;

            }
        });

        // Fetch hotel data from the API or database
        fetchHotels();

    }//end Fragment

    //set name for user Header
    private void setInfoHeader() {
       //  Get the user information from the shared preferences
        String name = SharedPrefManager.getInstance(getContext()).getUsers().getName();
        navUsername.setText(name);
    }

    //get Hotels from database
    private void fetchHotels() {
        Call<List<Hotel>> call = RetrofitHotels.getInstance().getMyApi().getHotels();
        call.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                if (response.isSuccessful()) {
                    List<Hotel> hotelList = response.body();
                    if (hotelList != null) {
                        hotelAdapter = new HotelAdapter(getContext(), hotelList);
                        recyclerView.setAdapter(hotelAdapter);
                    }
                    Toast.makeText(requireContext(), "Connection successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle API error
                    Toast.makeText(requireContext(), "Failed to fetch hotels", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                // Handle network failure
                Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
