package com.example.hotelbooking.Fragments;


import android.app.DatePickerDialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.hotelbooking.Activities.MainActivity;

import com.example.hotelbooking.Api.RetrofitReservation;
import com.example.hotelbooking.Model.Resultt;
import com.example.hotelbooking.Model.Rooms;

import com.example.hotelbooking.R;
import com.example.hotelbooking.SharedPref.SharedPrefManager;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomBookedFragment extends Fragment {
    private TextView roomTypeTextView, pricePerNightTextView, descriptionTextView,setTotalPrice;
    private TextView  checkInDateEditText, checkOutDateEditText;
    private Button checkInButton, checkOutButton, calculatePriceButton,confirm;
    private int roomId;
    private double pricePerNight;
    private String imagePath;
    private Date checkInDate;
    private Date checkOutDate;
    public static RoomBookedFragment newInstance(Rooms room) {

        RoomBookedFragment fragment = new RoomBookedFragment();
        Bundle args = new Bundle();
        args.putInt("room_id", room.getId());
        args.putString("room_type", room.getRoomType());
        args.putDouble("price_per_night", room.getPricePerNight());
        args.putString("description", room.getDescription());
        args.putInt("hotel_id", room.getHotelId());
        fragment.imagePath = room.getImagePath();
        // Add more room details as needed
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         View view = inflater.inflate(R.layout.reservation_frag,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        String room_type = getArguments().getString("room_type", "");

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);


        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(room_type);

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
        roomTypeTextView = view.findViewById(R.id.roomType);
        pricePerNightTextView = view.findViewById(R.id.pricePerNight);
        descriptionTextView = view.findViewById(R.id.description);
        checkInDateEditText = view.findViewById(R.id.checkInDateEditText);
        checkOutDateEditText = view.findViewById(R.id.checkOutDateEditText);
        checkInButton = view.findViewById(R.id.checkInButton);
        checkOutButton = view.findViewById(R.id.checkOutButton);
        calculatePriceButton = view.findViewById(R.id.calculatePriceButton);
        confirm = view.findViewById(R.id.confirm);
        setTotalPrice = view.findViewById(R.id.settotalPrice);

        // Get room details from arguments
        Bundle args = getArguments();
        if (args != null) {
            roomId = args.getInt("room_id", 0);
            String roomType = args.getString("room_type");
            pricePerNight = args.getDouble("price_per_night", 0.0);
            String description = args.getString("description");

            // Display room details in the fragment
            roomTypeTextView.setText(roomType);
            pricePerNightTextView.setText(String.valueOf(pricePerNight));
            descriptionTextView.setText(description);
        }
        ImageView roomImageView = view.findViewById(R.id.roomImage);
        if (imagePath != null) {
            Picasso.get().load("http://10.0.2.2/Hotel/rooms/" + imagePath)
                    .error(R.drawable.notfound) // Add a default image in case of loading failure
                    .into(roomImageView);
        }



        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertReservationData();
            }
        });
        // Handle check-in button click
        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle check-in logic
                showCheckInDatePicker();
            }
        });

        // Handle check-out button click
        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle check-out logic
                showCheckOutDatePicker();
            }
        });

        // Handle calculate price button click
        calculatePriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle price calculation logic
                calculatePrice();
            }
        });
    }//end onview created


    // important method

    private void showCheckInDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set the selected date to the check-in date EditText
                        String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        checkInDateEditText.setText(selectedDate);

                        // Parse the selected date to a Date object
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        try {
                            checkInDate = dateFormat.parse(selectedDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },
                year, month, dayOfMonth
        );
        datePickerDialog.show();
    }
    private void showCheckOutDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set the selected date to the check-out date EditText
                        String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        checkOutDateEditText.setText(selectedDate);

                        // Parse the selected date to a Date object
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        try {
                            checkOutDate = dateFormat.parse(selectedDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },
                year, month, dayOfMonth
        );
        datePickerDialog.show();
    }
    private void insertReservationData() {

        int userId = SharedPrefManager.getInstance(getContext()).getUsers().getId();

        int roomId = getArguments().getInt("room_id", 0);

        int hotelId = getArguments().getInt("hotel_id", 0);

        String checkInDateStr = checkInDateEditText.getText().toString();
        String checkOutDateStr = checkOutDateEditText.getText().toString();

        if (checkInDateStr.isEmpty() || checkOutDateStr.isEmpty()) {
            // Dates are not selected, show a toast message
            Toast.makeText(getContext(), "Please select check-in and check-out dates", Toast.LENGTH_SHORT).show();
            return; // Return early, do not proceed with the reservation
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date checkInDate = dateFormat.parse(checkInDateStr);
            Date checkOutDate = dateFormat.parse(checkOutDateStr);

            if (checkOutDate.before(checkInDate)) {
                // Invalid date range, check-out date must be after check-in date
                Toast.makeText(getContext(), "Invalid date range. Check-out date must be after check-in date.", Toast.LENGTH_SHORT).show();
                return; // Return early, do not proceed with the reservation
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        double totalPrice = calculatePrice(); // Implement the calculation logic here


        // Make the API call to insert the reservation data
       Call<Resultt> call = RetrofitReservation.getInstance().getMyApi().insertReservation(
                hotelId,
                roomId,
                userId,
                checkInDateStr,
                checkOutDateStr,
                String.valueOf(totalPrice)
        );


        call.enqueue(new Callback<Resultt>() {
            @Override
            public void onResponse(Call<Resultt> call, Response<Resultt> response) {
                if (response.isSuccessful()) {
                    Resultt result = response.body();
                    Log.d("Response", response.body().toString());
                    if (result != null && result.getError()) {
                        Log.d("Something went wrong", result.getMessage());
                        Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("Response", "User registered successfully");
                        Toast.makeText(getContext(), result != null ? result.getMessage() : "", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Log.d("Response", "Unsuccessful");
                    Toast.makeText(getContext(), "Failed to Insert Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Resultt> call, Throwable t) {
                Log.d("Failed to Insert Data Rerrofit Error", t.getMessage());
                Log.e("API Call Failed", t.getMessage());
                Toast.makeText(getContext(), "Failed to Insert Data: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private double  calculatePrice() {
        // Retrieve check-in and check-out dates from the EditText fields
        String checkInDateStr = checkInDateEditText.getText().toString();
        String checkOutDateStr = checkOutDateEditText.getText().toString();

        // Ensure that both check-in and check-out dates are provided
        if (checkInDateStr.isEmpty() || checkOutDateStr.isEmpty()) {
            Toast.makeText(getContext(), "Please select both check-in and check-out dates.", Toast.LENGTH_SHORT).show();
            return 0;
        }

        // Parse the date strings to Date objects
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            checkInDate = dateFormat.parse(checkInDateStr);
            checkOutDate = dateFormat.parse(checkOutDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error parsing dates.", Toast.LENGTH_LONG).show();
            return 0;
        }

        // Calculate the number of nights between check-in and check-out dates
        assert checkOutDate != null;
        long differenceMillis = checkOutDate.getTime() - checkInDate.getTime();
        int numberOfNights = (int) TimeUnit.DAYS.convert(differenceMillis, TimeUnit.MILLISECONDS);

        // Ensure that check-out date is after check-in date
        if (numberOfNights <= 0) {
            Toast.makeText(getContext(), "Invalid date range. Check-out date must be after check-in date.", Toast.LENGTH_SHORT).show();
            return 0;
        }

        // Calculate the total price
        double totalPrice = pricePerNight * numberOfNights;

        // Display the total price to the user
        Toast.makeText(getContext(), "Total Price: " + totalPrice, Toast.LENGTH_SHORT).show();
        setTotalPrice.setText(String.valueOf(totalPrice));
        return totalPrice;
    }
}
