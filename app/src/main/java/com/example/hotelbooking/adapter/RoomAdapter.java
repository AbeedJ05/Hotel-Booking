package com.example.hotelbooking.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hotelbooking.Fragments.RoomBookedFragment;
import com.example.hotelbooking.Model.Rooms;
import com.example.hotelbooking.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Rooms> mData;
    private Context mContext;

    public RoomAdapter(List<Rooms> data,Context mContext) {
        this.mContext =mContext;
        mData = data;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rooms_details, parent, false);
        return new RoomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Rooms room = mData.get(position);

        holder.roomTypeTextView.setText(room.getRoomType());
        holder.pricePerNightTextView.setText(String.valueOf(room.getPricePerNight()));
        holder.descriptionTextView.setText(room.getDescription());

        if (mData.get(position).getImagePath() != null) {
            Picasso.get().load("http://10.0.2.2/Hotel/rooms/"+mData.get(position).getImagePath())
                    .error(R.drawable.notfound).into(holder.roomImageView);
        }



        holder.showRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle room reservation button click event
                // Handle "Show Room" button click event
                Rooms room = mData.get(position);

                // Navigate to the RoomBookedFragment and pass room details as arguments
                FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, RoomBookedFragment.newInstance(room));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView roomTypeTextView, pricePerNightTextView, descriptionTextView;
        ImageView roomImageView;
        Button showRoom;

        RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            roomTypeTextView = itemView.findViewById(R.id.roomType);
            pricePerNightTextView = itemView.findViewById(R.id.pricePerNight);
            descriptionTextView = itemView.findViewById(R.id.description);
            roomImageView = itemView.findViewById(R.id.roomImage);

            showRoom = itemView.findViewById(R.id.showRoom);
        }
    }
}

