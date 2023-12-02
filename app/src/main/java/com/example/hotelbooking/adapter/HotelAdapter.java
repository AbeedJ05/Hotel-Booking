package com.example.hotelbooking.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.Api.APIUrl;
import com.example.hotelbooking.Api.RetrofitHotels;
import com.example.hotelbooking.Api.RetrofitRooms;
import com.example.hotelbooking.Fragments.RoomDetailsFragment;
import com.example.hotelbooking.Model.Hotel;
import com.example.hotelbooking.Model.Rooms;
import com.example.hotelbooking.Model.User;
import com.example.hotelbooking.R;
import com.example.hotelbooking.SharedPref.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {
    private List<Hotel> mData;
    private Context mContext;
    //Search view methode
    private List<Hotel> filteredItemList;

      Rooms rooms;
    public HotelAdapter(Context context, List<Hotel> data) {
        mContext = context;
        mData = data;
        this.filteredItemList = new ArrayList<>(mData);
    }
    public  void filter(String searchText) {
        filteredItemList.clear();
        if (TextUtils.isEmpty(searchText)) {
            filteredItemList.addAll(mData);
        } else {
            for (Hotel item : mData) {
                if (item.getHotelName().toLowerCase().contains(searchText.toLowerCase()) || item.getCountry().toLowerCase().contains(searchText.toLowerCase()) ) {
                    filteredItemList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_category, parent, false);
        return new HotelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder,@SuppressLint("RecyclerView") int position) {

        Hotel hotel = filteredItemList.get(position);
        holder.hotelNameTextView.setText(hotel.getHotelName());
        holder.countryTextView.setText(hotel.getCountry());
        holder.id.setText(String.valueOf(hotel.getId()));

        if (filteredItemList.get(position).getImg() != null) {
            Picasso.get().load("http://10.0.2.2/Hotel/uploads/"+mData.get(position).getImg())
                    .error(R.drawable.notfound).into(holder.img);
        }

        int rating = filteredItemList.get(position).getRating();

        holder.star1.setColorFilter(rating >= 1 ? Color.GREEN : Color.RED);
        holder.star2.setColorFilter(rating >= 2 ? Color.GREEN : Color.RED);
        holder.star3.setColorFilter(rating >= 3 ? Color.GREEN : Color.RED);
        holder.star4.setColorFilter(rating >= 4 ? Color.GREEN : Color.RED);
        holder.star5.setColorFilter(rating >= 5 ? Color.GREEN : Color.RED);




        // Set click listener for the button
        holder.visitHotelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the hotel ID
                int hotelId = hotel.getId(); // Use the ID


                Log.d("tag", "Hotel ID: " + hotelId);
                Log.d("tag", "API URL: " + APIUrl.Room_Url + "getRooms.php?hotel_id=" + hotelId);


                Call<List<Rooms>> call = RetrofitRooms.getInstance().getMyApi().getRooms(hotelId);
                call.enqueue(new Callback<List<Rooms>>() {
                    @Override
                    public void onResponse(Call<List<Rooms>> call, Response<List<Rooms>> response) {
                        if (response.isSuccessful()) {
                            List<Rooms> roomList = response.body();
                            Log.d("tag", response.message() + hotel.getId());
                            if (roomList != null && !roomList.isEmpty()) {
                                // Get the hotel ID
                                int hotelId = hotel.getId();
                                String hotelName = hotel.getHotelName();
                                // Create a new instance of RoomDetailsFragment and pass the hotel ID to it
                                RoomDetailsFragment roomDetailsFragment = RoomDetailsFragment.newInstance(hotelId,hotelName);
                                FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_container, roomDetailsFragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            } else {
                                Toast.makeText(mContext, "No room details found for this hotel", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Failed to fetch room details", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Rooms>> call, Throwable t) {
                        Toast.makeText(mContext, "Network error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredItemList.size();
    }

    static class HotelViewHolder extends RecyclerView.ViewHolder {
        TextView hotelNameTextView, countryTextView,id;
        ImageView star1, star2, star3, star4, star5;
        ImageView img ;

        Button visitHotelButton;

        HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelNameTextView = itemView.findViewById(R.id.hotelName);
            countryTextView = itemView.findViewById(R.id.country);
            img=itemView.findViewById(R.id.hotelImage);
            star1 = itemView.findViewById(R.id.star1);
            star2 = itemView.findViewById(R.id.star2);
            star3 = itemView.findViewById(R.id.star3);
            star4 = itemView.findViewById(R.id.star4);
            star5 = itemView.findViewById(R.id.star5);
            visitHotelButton = itemView.findViewById(R.id.visitHotel);
            id = itemView.findViewById(R.id.idd);
        }
    }
}
