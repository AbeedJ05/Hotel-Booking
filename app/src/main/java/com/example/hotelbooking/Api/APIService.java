package com.example.hotelbooking.Api;

import com.example.hotelbooking.Model.Hotel;
import com.example.hotelbooking.Model.Reservation;
import com.example.hotelbooking.Model.Resultt;
import com.example.hotelbooking.Model.Rooms;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {
    // method is used to login a user
    @FormUrlEncoded
    @POST("loginUser.php")
    Call<Resultt> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    // method is used to create a new user.
    @FormUrlEncoded
    @POST("signupUser.php")
    Call<Resultt> insertUser(
            @Field("username") String name,
            @Field("password") String password,
            @Field("email") String email,
            @Field("phone") String phone);

    //method is used to update a user's information.
    @FormUrlEncoded
    @POST("updateUser.php")
    Call<Resultt> updateUser(
            @Field("id") int id,
            @Field("username") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone
    );

    //method is used to get a list of hotels
    @GET("getHotels.php")
    Call<List<Hotel>> getHotels();

    //method is used to get a list of rooms for a specific hotel
    @GET("getRooms.php")
    Call<List<Rooms>> getRooms(@Query("hotel_id") int hotelId);

    //method is used to create a new reservation.
    @FormUrlEncoded
    @POST(APIUrl.insert_Reser_Url)
    Call<Resultt> insertReservation(
            @Field("hotel_id") int hotelId,
            @Field("room_id") int roomId,
            @Field("user_id") int userId,
            @Field("check_in_date") String checkInDate,
            @Field("check_out_date") String checkOutDate,
            @Field("total_price") String totalPrice
    );


    //method is used to get all reservations in admin Mode
    @GET("getReservation.php")
    Call<List<Reservation>> getReservations();


//method is used to delete account
    @FormUrlEncoded
    @POST("deleteUser.php")
    Call<Resultt> deleteUser(@Field("user") int id);

    //method is used to get  a reservation for specific user.
    @GET("userReservation.php")
    Call<List<Reservation>> getUserReservations(@Query("user_id") int userId);


    @FormUrlEncoded
    @POST("deleteReservation.php")
    Call<Resultt> deleteReservation(@Field("reservation_id") int id);

    @GET("getRoomsAdmin.php")
    Call<List<Rooms>> getRoomsAdmin();

    @FormUrlEncoded
    @POST("updateRooms.php")
    Call<Resultt> updateRooms(
            @Field("id") int id,
            @Field("hotel_id") int hotel_id,
            @Field("room_type") String room_type,
            @Field("price") double price,
            @Field("description") String description
    );

    @FormUrlEncoded
    @POST("deleteRoom.php")
    Call<Resultt> deleteRoom(@Field("room") int id);

}
