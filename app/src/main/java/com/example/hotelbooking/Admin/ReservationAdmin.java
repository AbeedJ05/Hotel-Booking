package com.example.hotelbooking.Admin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.hotelbooking.Api.retrofitShowReservation;
import com.example.hotelbooking.Model.Reservation;
import com.example.hotelbooking.R;
import com.example.hotelbooking.adapter.ReservationAdapter;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationAdmin extends AppCompatActivity {
    RecyclerView recyclerView;
    ReservationAdapter reservationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_admin);
// Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back); // Customize the back button icon
        }

        recyclerView = (RecyclerView) findViewById(R.id.resycleradminres);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        fetchReservation();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Go back when the back button is clicked
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchReservation() {
        Call<List<Reservation>> call = retrofitShowReservation.getInstance().getMyApi().getReservations();
        call.enqueue(new Callback<List<Reservation>>() {

            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                if (response.isSuccessful()) {
                    List<Reservation> reservationsList = response.body();
                    if (reservationsList != null) {
                        reservationAdapter = new ReservationAdapter(reservationsList,getApplicationContext());
                        recyclerView.setAdapter(reservationAdapter);
                    }
                    Toast.makeText(getApplicationContext(), "Connection successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle API error
                    Toast.makeText(getApplicationContext(), "Failed to fetch reservations", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                // Handle network failure
                Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_SHORT).show();
                Log.d("error",t.getMessage());
            }
        });
    }
}