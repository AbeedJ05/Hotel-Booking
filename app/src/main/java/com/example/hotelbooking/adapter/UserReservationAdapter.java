package com.example.hotelbooking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.Model.Reservation;
import com.example.hotelbooking.R;

import java.util.List;

public class UserReservationAdapter  extends RecyclerView.Adapter<UserReservationAdapter.ReservationViewHolder> {


        private List<Reservation> reservations;

    private Context context;
        public UserReservationAdapter(List<Reservation> reservations, Context context) {
            this.reservations = reservations;
            this.context = context;
        }

        @NonNull
        @Override
        public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_res_adapter, parent, false);
            return new ReservationViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
            Reservation reservation = reservations.get(position);

            // Bind data to the views in the CardView
            holder.txtCheckIn.setText(reservation.getCheckIn());
            holder.txtCheckOut.setText(reservation.getCheckOut());
            holder.txtHotelName.setText(reservation.getHotelName());
            holder.txtRoomName.setText(reservation.getRoomName());
            holder.txtTotalPrice.setText(String.valueOf(reservation.getTotalPrice()));

        }

        @Override
        public int getItemCount() {
            return reservations.size();
        }

        public static class ReservationViewHolder extends RecyclerView.ViewHolder {
            TextView txtCheckIn, txtCheckOut, txtHotelName, txtRoomName, txtTotalPrice;
            Button buttonDelete;

            public ReservationViewHolder(@NonNull View itemView) {
                super(itemView);
                txtCheckIn = itemView.findViewById(R.id.txtCheckIn);
                txtCheckOut = itemView.findViewById(R.id.txtCheckOut);
                txtHotelName = itemView.findViewById(R.id.txtHotelName);
                txtRoomName = itemView.findViewById(R.id.txtRoomName);
                txtTotalPrice = itemView.findViewById(R.id.txtTotalPrice);
                buttonDelete = itemView.findViewById(R.id.buttonDelete);
            }
        }
    }



