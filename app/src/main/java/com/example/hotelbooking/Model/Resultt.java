package com.example.hotelbooking.Model;

import com.google.gson.annotations.SerializedName;

public class Resultt {

    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("user")
    private User user;
    @SerializedName("reservation")
    private Reservation reservation;
    @SerializedName("rooms")
    private Rooms rooms;

    public Resultt(Boolean error, String message, User user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }



    public Resultt(Boolean error, String message){
        this.error = error;
        this.message = message;

    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Rooms getRooms() {
        return rooms;
    }

    public User getUser() {
        return user;
    }
    public Reservation getReservation() {
        return reservation;
    }
}
