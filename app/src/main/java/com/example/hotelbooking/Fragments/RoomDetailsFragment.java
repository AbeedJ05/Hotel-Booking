package com.example.hotelbooking.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.Activities.MainActivity;

import com.example.hotelbooking.Api.RetrofitRooms;
import com.example.hotelbooking.Model.Rooms;
import com.example.hotelbooking.R;

import com.example.hotelbooking.adapter.RoomAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomDetailsFragment extends Fragment {


    private RecyclerView recyclerRoom;
    private List<Rooms> rooms;
    private RoomAdapter adapter;
    RetrofitRooms retrofitRoom;

    public static RoomDetailsFragment newInstance(int hotelId, String hotelName) {
        RoomDetailsFragment fragment = new RoomDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("hotel_id", hotelId);
        args.putString("hotel_Name", hotelName);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_room_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerRoom = (RecyclerView) view.findViewById(R.id.roomrev);
        recyclerRoom.setLayoutManager(new LinearLayoutManager(getContext()));

        MainActivity mainActivity = (MainActivity) requireActivity();
        String namehotel = getArguments().getString("hotel_Name",null);
        mainActivity.setupCustomToolbar(namehotel);

        ImageButton back = view.findViewById(R.id.backinRoom);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.replaceFragment(new HomeFragment());
            }
        });

        //key from adapter to get same hotel_id inside instance cons
        int hotelId = getArguments().getInt("hotel_id", -1); // -1 is a default value if the argument is not found

        // Check if the hotelId is valid before making the API call
        if (hotelId != -1) {
            Call<List<Rooms>> call = retrofitRoom.getInstance().getMyApi().getRooms(hotelId);

            call.enqueue(new Callback<List<Rooms>>() {
                @Override
                public void onResponse(Call<List<Rooms>> call, Response<List<Rooms>> response) {
                    rooms = response.body();
                    adapter = new RoomAdapter(rooms,getContext());
                    recyclerRoom.setAdapter(adapter);
                    Log.d("connection done-->", response.toString());
                }

                @Override
                public void onFailure(Call<List<Rooms>> call, Throwable t) {
                    Log.d("connection failed-->", t.getMessage());
                }
            });
        } else {
            // Handle the case when the hotelId is not provided properly
            Toast.makeText(getContext(), "Invalid hotel ID", Toast.LENGTH_SHORT).show();
        }

    }//end on view Created

    //back button in room details




}