<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "hoteldb";

// create connection
$conn =  mysqli_connect($servername, $username, $password, $dbname);

if(!empty($_POST['image'])) {
    // Define the path to save the image on the server
    $path = 'uploads/'.date("d-m-Y").'-'.time().'-'.rand(10000, 100000) . '.jpg';

    // Save the image to the server
    if(file_put_contents($path, base64_decode($_POST['image']))) {
        $hotelName = $_POST['hotelName'];
        $country = $_POST['country'];
        $rating = $_POST['rating'];

        // Replace "uploads/" with an empty string in the $path variable
        $dbImagePath = str_replace('uploads/', '', $path);

        // Insert the hotel data into the database
        $sql = "INSERT INTO hotels (hotel_name, country, rating, omg) VALUES ('$hotelName', '$country', '$rating', '$dbImagePath')";
        
        if(mysqli_query($conn, $sql)) {
            echo 'success';
        } else {
            echo 'Failed to insert to database';
        }
    } else {
        echo 'Failed to upload image';
    }
} else {
    echo 'No image found';
}
?>
