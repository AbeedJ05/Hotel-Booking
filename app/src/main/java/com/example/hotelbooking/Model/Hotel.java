package com.example.hotelbooking.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.http.GET;

public class Hotel {

    @SerializedName("id")
    private int id ;
    @SerializedName("namename")
    private String hotelName;
    @SerializedName("co")
    private String country;
    @SerializedName("ra")
    private int rating;
    @SerializedName("img")
    private String img ;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<Rooms> getRooms() {
        return rooms;
    }

    public void setRooms(List<Rooms> rooms) {
        this.rooms = rooms;
    }

    private List<Rooms> rooms;
}
