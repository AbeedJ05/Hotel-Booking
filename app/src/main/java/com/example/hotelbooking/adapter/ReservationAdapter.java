package com.example.hotelbooking.adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.Api.RetrofitDeleteReservation;
import com.example.hotelbooking.Model.Reservation;
import com.example.hotelbooking.Model.Resultt;
import com.example.hotelbooking.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private List<Reservation> reservationList;
    private Context context;

    public ReservationAdapter(List<Reservation> reservationList, Context context) {
        this.reservationList = reservationList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_reservation_admin, parent, false);
        return new ReservationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder,@SuppressLint("RecyclerView")  int position) {
        Reservation reservation = reservationList.get(position);

        holder.txtReservationId.setText("Reservation ID: " + reservation.getReservationId());
        holder.txtHotelName.setText("Hotel: " + reservation.getHotelName());
        holder.txtuserName.setText("username: " + reservation.getUserName());
        holder.txtRoomName.setText("Room: " + reservation.getRoomName());
        holder.txtTotalPrice.setText("Total Price: " + reservation.getTotalPrice());
        holder.txtCheckIn.setText("Check-in: " + reservation.getCheckIn());
        holder.txtCheckOut.setText("Check-out: " + reservation.getCheckOut());
        holder.phone.setText(reservation.getPhone());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reservation item = reservationList.get(position);
                Call<Resultt> call = RetrofitDeleteReservation.getInstance().getMyApi().deleteReservation(item.getReservationId());
                call.enqueue(new Callback<Resultt>() {
                    @Override
                    public void onResponse(Call<Resultt> call, Response<Resultt> response) {

                        if(response.body().getError() == false){
                            reservationList.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(v.getContext(), "Response msg ---> "+ response.body().getMessage(),Toast.LENGTH_LONG).show();

                            Log.d("msg ---> ",response.body().getMessage());
                            // Remove the reservation from the list


                        }else if (response.body().getError() == true){
                            Toast.makeText(v.getContext(), "Response msg ---> "+ response.body().getMessage(),Toast.LENGTH_LONG).show();
                            Log.d("msg ---> ",response.body().getMessage());

                        }
                    }

                    @Override
                    public void onFailure(Call<Resultt> call, Throwable t) {

                        Toast.makeText(v.getContext(), "Error ---> "+t.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("Error ---> ",t.getMessage());

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public class ReservationViewHolder extends RecyclerView.ViewHolder {
        TextView txtReservationId, txtHotelName, txtRoomName, txtTotalPrice,txtuserName ,txtCheckIn, txtCheckOut,phone;
        Button delete;
        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            txtReservationId = itemView.findViewById(R.id.txtReservationId);
            txtHotelName = itemView.findViewById(R.id.txtHotelName);
            txtuserName = itemView.findViewById(R.id.textViewUserName);
            txtRoomName = itemView.findViewById(R.id.txtRoomName);
            txtTotalPrice = itemView.findViewById(R.id.txtTotalPrice);
            txtCheckIn = itemView.findViewById(R.id.txtCheckIn);
            txtCheckOut = itemView.findViewById(R.id.txtCheckOut);
            phone = itemView.findViewById(R.id.phone);

            delete = itemView.findViewById(R.id.buttonDelete);

        }
    }
}
