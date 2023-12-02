package com.example.hotelbooking.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;
import com.example.hotelbooking.Model.Resultt;
import com.example.hotelbooking.Api.RetrofitGetRoomsAdmin;
import com.example.hotelbooking.Api.RetrofitHotels;
import com.example.hotelbooking.Model.Hotel;
import com.example.hotelbooking.Model.Resultt;
import com.example.hotelbooking.Model.Rooms;
import com.example.hotelbooking.Model.User;
import com.example.hotelbooking.R;
import com.example.hotelbooking.SharedPref.SharedPrefManager;
import com.example.hotelbooking.adapter.EditRoomsAdapter;
import com.example.hotelbooking.adapter.HotelAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditRooms extends AppCompatActivity {
    EditRoomsAdapter editRoomAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rooms);
        recyclerView = findViewById(R.id.rvEditrooms);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        fetchRooms();
    }
    private void fetchRooms() {
        Call<List<Rooms>> call = RetrofitGetRoomsAdmin.getInstance().getMyApi().getRoomsAdmin();
        call.enqueue(new Callback<List<Rooms>>() {
            @Override
            public void onResponse(Call<List<Rooms>> call, Response<List<Rooms>> response) {
                if (response.isSuccessful()) {
                    List<Rooms> editRoom = response.body();
                    if (editRoom != null) {
                        editRoomAdapter = new EditRoomsAdapter( getApplicationContext(), editRoom);
                        recyclerView.setAdapter(editRoomAdapter);
                    }
                    Toast.makeText(getApplicationContext(), "Connection successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle API error
                    Toast.makeText(getApplicationContext(), "Failed to fetch Rooms", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Rooms>> call, Throwable t) {
                // Handle network failure
                Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}