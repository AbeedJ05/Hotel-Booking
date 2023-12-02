package com.example.hotelbooking.Api;

public interface APIUrl {
    //get Hotel
    String BASE_URL = "https://10.0.2.2/Hotel/";//display to User all Hotels
    String addHotelUrl = "http://10.0.2.2/Hotel/insert_Hotels.php/";//Admin Mode

    //User
    String SIGN_IN = "https://10.0.2.2/Hotel/loginUser.php/";
    String SIGN_UP = "http://10.0.2.2/Hotel/signupUser.php/";
    String DELETE_USER = "http://10.0.2.2//Hotel/deleteUser.php/";//delete Account
    String update_Url = "http://10.0.2.2/Hotel/updateUser.php/";//update profile contents

    //Room
    String Room_Url = "https://10.0.2.2/Hotel/getRooms.php/";//get Rooms via Hotel_id
    String getRoomAdmin = "http://10.0.2.2/Hotel/getRoomsAdmin.php/";//Admin Mode
    String deleteRoom = "http://10.0.2.2/Hotel/deleteRoom.php/";//Admin Mode
    String INSERT_ROOM_URL = "http://10.0.2.2/Hotel/insert_room.php/";//Admin Mode
    String update_Rooms = "http://10.0.2.2/Hotel/updateRooms.php/";

    //Reservations
    String insert_Reser_Url = "https://10.0.2.2/Hotel/insert_reservation.php/";//when user make reservation
    String showReservation = "http://10.0.2.2//Hotel/getReservation.php/";//display all in Admin Mode
    String UserReservation = "http://10.0.2.2//Hotel/userReservation.php/";//display reservations in user Fragment
    String deleteReservation = "http://10.0.2.2//Hotel/deleteReservation.php/";//delete from admin Mode
}
