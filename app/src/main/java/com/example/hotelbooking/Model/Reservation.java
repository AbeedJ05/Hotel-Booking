package com.example.hotelbooking.Model;

import com.google.gson.annotations.SerializedName;
        public class Reservation {
            @SerializedName("reservation_id")
            private int reservationId;
            @SerializedName("total_price")
            private double totalPrice;
            @SerializedName("check_in_date")
            private String checkIn;
            @SerializedName("phone")
            private String phone;
            @SerializedName("check_out_date")
            private String checkOut;
            @SerializedName("hotel_name")
            private String hotelName;
            @SerializedName("room_type")
            private String roomName;
            @SerializedName("username")
            private String userName;

            public int getReservationId() {
                return reservationId;
            }

            public double getTotalPrice() {
                return totalPrice;
            }

            public String getCheckIn() {
                return checkIn;
            }

            public String getCheckOut() {
                return checkOut;
            }

            public String getHotelName() {
                return hotelName;
            }

            public String getRoomName() {
                return roomName;
            }
            public String getUserName() {
                return userName;
            }
            public String getPhone() {
                return phone;
            }

            // Add getters and setters as needed
        }








