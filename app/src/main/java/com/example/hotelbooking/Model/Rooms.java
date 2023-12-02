package com.example.hotelbooking.Model;

import com.google.gson.annotations.SerializedName;

public class Rooms {
    @SerializedName("id")
    private int id;
    @SerializedName("hotelid")
    private int hotelId;
    @SerializedName("hotel_name")
    private String hotel_name;
    @SerializedName("room_type")
    private String roomType;
    @SerializedName("price")
    private double pricePerNight;
    @SerializedName("description")
    private String description;
    @SerializedName("img")
    private String imagePath;


    public String getHotel_name() {
        return hotel_name;
    }
    public String getImagePath() {
        return imagePath;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getHotelId() {
        return hotelId;
    }
    public String getRoomType() {
        return roomType;
    }
    public double getPricePerNight() {
        return pricePerNight;
    }
    public String getDescription() {
        return description;
    }


}
