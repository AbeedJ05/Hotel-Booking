package com.example.hotelbooking.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.Admin.UpdateRoomActivity;
import com.example.hotelbooking.Api.RetrofitDeleteRoom;
import com.example.hotelbooking.Api.RetrofitDeleteUser;
import com.example.hotelbooking.Model.Reservation;
import com.example.hotelbooking.Model.Resultt;
import com.example.hotelbooking.Model.Rooms;
import com.example.hotelbooking.R;
import com.example.hotelbooking.SharedPref.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditRoomsAdapter extends RecyclerView.Adapter<EditRoomsAdapter.ViewHolder> {

    private Context context;
    private List<Rooms> roomList;

    public EditRoomsAdapter(Context context, List<Rooms> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.editroomadapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Rooms room = roomList.get(position);

        holder.txtHotelName.setText("Hotel Name: "+room.getHotel_name());
        holder.txtRoomName.setText("Room Type:"+room.getRoomType());
        holder.txtPrice.setText("Price: " + room.getPricePerNight());;
        holder.description.setText(room.getDescription());

        holder.btnEditRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the action for Update button click here
                Intent intent = new Intent(context, UpdateRoomActivity.class);
                // Pass any data you need to the UpdateRoomActivity using intent.putExtra()
                intent.putExtra("id", room.getId());
                intent.putExtra("hotel_id", room.getHotelId());
                intent.putExtra("price", room.getPricePerNight());
                intent.putExtra("description", room.getDescription());
                intent.putExtra("hotel_name", room.getHotel_name());
                intent.putExtra("roomtype", room.getRoomType());
                // For example, passing the room ID
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("room_iD","ID" + room.getId());

                Call<Resultt> call = RetrofitDeleteRoom.getInstance().getMyApi().deleteRoom(room.getId());
                call.enqueue(new Callback<Resultt>() {
                    @Override
                    public void onResponse(Call<Resultt> call, Response<Resultt> response) {

                        if (response.body().getError() == false) {

                            Toast.makeText(v.getContext(), "Response msg ---> " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                            Log.d("msg ---> ", response.body().getMessage());

                        } else if (response.body().getError() == true) {
                            Toast.makeText(v.getContext(), "Response msg ---> " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                            Log.d("msg ---> ", response.body().getMessage());

                        }
                        roomList.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Resultt> call, Throwable t) {

                        Toast.makeText(v.getContext(), "Error ---> " + t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("Error ---> ", t.getMessage());

                    }
                });
            }
        });

        }
    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtHotelName;
        TextView txtRoomName;
        TextView txtPrice;
        TextView description;
        Button btnEditRoom;
        Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            txtHotelName = itemView.findViewById(R.id.txtHotelName);
            txtRoomName = itemView.findViewById(R.id.txtRoomName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            description = itemView.findViewById(R.id.description);
            btnEditRoom = itemView.findViewById(R.id.btnEditRoom);
            btnDelete = itemView.findViewById(R.id.buttonDelete);
        }

    }
}

