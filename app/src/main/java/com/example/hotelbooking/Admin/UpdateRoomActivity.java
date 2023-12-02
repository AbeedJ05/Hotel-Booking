package com.example.hotelbooking.Admin;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelbooking.Api.RetrofitUpdateRooms;
import com.example.hotelbooking.Model.Resultt;
import com.example.hotelbooking.Model.Rooms;
import com.example.hotelbooking.Model.User;
import com.example.hotelbooking.R;
import com.example.hotelbooking.SharedPref.SharedPrefManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateRoomActivity extends AppCompatActivity {
    private TextInputLayout etHotelId;
TextView tHtelname;
    private TextInputLayout etRoomType;
    private TextInputLayout etPrice;
    private TextInputLayout etDescription;
    private Button btnAddRoom;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_room_activiy);

        // Initialize the TextInputEditText views
        etHotelId = findViewById(R.id.etHotelId);

        etRoomType = findViewById(R.id.etRoomType);
        etPrice = findViewById(R.id.etPrice);
        etDescription = findViewById(R.id.etDescription);
         tHtelname = findViewById(R.id.editHotelName);
        // Initialize the buttons
        btnAddRoom = findViewById(R.id.UpdateEditRooms);
        btnBack = findViewById(R.id.back_ROOM);

        Intent intent = getIntent();

        // Retrieve the extras using the specified keys
        int id = intent.getIntExtra("id", 0);
        int hotelId = intent.getIntExtra("hotel_id", 0);
        double price = intent.getDoubleExtra("price", 0.0);
        String description = intent.getStringExtra("description");
        String roomType = intent.getStringExtra("roomtype");
        String hotel_name = intent.getStringExtra("hotel_name");


        tHtelname.setText(hotel_name);
        etHotelId.getEditText().setText(String.valueOf(hotelId));
        etRoomType.getEditText().setText(roomType);
        etPrice.getEditText().setText(String.valueOf(price));
        etDescription.getEditText().setText(description);

        btnAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int updatedId = id; // Assuming id is already set in the activity
                int updatedHotelId = Integer.parseInt(etHotelId.getEditText().getText().toString());
                String updatedRoomType = etRoomType.getEditText().getText().toString();
                double updatedPrice = Double.parseDouble(etPrice.getEditText().getText().toString());
                String updatedDescription = etDescription.getEditText().getText().toString();

                // Make the POST request using Retrofit
                Call<Resultt> call = RetrofitUpdateRooms.getInstance()
                        .getMyApi().updateRooms(updatedId, updatedHotelId, updatedRoomType, updatedPrice, updatedDescription);

                call.enqueue(new Callback<Resultt>() {
                    @Override
                    public void onResponse(Call<Resultt> call, Response<Resultt> response) {
                        if (response.isSuccessful()) {
                            Resultt result = response.body();
                            if (result.getError() || result.getRooms().getRoomType() == null) {
                                // Update failed, show error message
                                Toast.makeText(getApplicationContext(), "Update Failed: " + result.getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                // Update successful, update user in shared preferences and header info

                                startActivity(new Intent(getApplicationContext(),intro_Admin.class));
                                Toast.makeText(getApplicationContext(), "Update successful: " + result.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            // Request failed, show error message
                            Toast.makeText(getApplicationContext(), "Request failed: " + response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Resultt> call, Throwable t) {
                        // Request failed, show error message
                        Toast.makeText(getApplicationContext(), "Request failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("updateActivity", "onFailure: ", t);
                    }
                });
            }
        });



        btnBack.setOnClickListener(v -> onBackPressed());

    }
}