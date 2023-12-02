package com.example.hotelbooking.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hotelbooking.Activities.Sign_Up;
import com.example.hotelbooking.R;

public class Admin extends AppCompatActivity implements View.OnClickListener {
Button addHotel,addRoom,Reservation,EditRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        addHotel = findViewById(R.id.btnAddHotels);
        addRoom =   findViewById(R.id.btnAddrooms);
        Reservation = findViewById(R.id.btnShowReservations);
        EditRoom = findViewById(R.id.editrooms);

        addHotel.setOnClickListener(this);
        addRoom.setOnClickListener(this);
        Reservation.setOnClickListener(this);
        EditRoom.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == addHotel) {
            startActivity(new Intent(getApplicationContext(), AddHotel.class));
        }else if(view == addRoom){

            startActivity(new Intent(getApplicationContext(), AddRoom.class));
        }else if(view == Reservation) {
            startActivity(new Intent(getApplicationContext(), ReservationAdmin.class));
        }else if(view == EditRoom){
            startActivity(new Intent(getApplicationContext(), EditRooms.class));
        }

    }
}