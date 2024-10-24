<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "hoteldb";

// create connection
$conn =  mysqli_connect($servername, $username, $password, $dbname);

if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

if (!empty($_POST['image'])) {
    // Define the path to save the image on the server
    $path = 'rooms/' . date("d-m-Y") . '-' . time() . '-' . rand(10000, 100000) . '.jpg';

    // Save the image to the server
    if (file_put_contents($path, base64_decode($_POST['image']))) {

        $hotel_id = $_POST['hotel_id'];
        $room_type = $_POST['room_type'];
        $price = $_POST['price'];
        $description = $_POST['description'];

        // Check if the hotel_id exists in the hotels table
        $hotel_check_sql = "SELECT * FROM hotels WHERE hotel_id = '$hotel_id'";
        $hotel_result = mysqli_query($conn, $hotel_check_sql);

        if (mysqli_num_rows($hotel_result) > 0) {
            // Remove "rooms/" from the $path variable
            $dbImagePath = str_replace('rooms/', '', $path);

            // Insert room data into the database
            $sql = "INSERT INTO rooms (hotel_id, room_type, price, description, img) VALUES ('$hotel_id', '$room_type', '$price', '$description', '$dbImagePath')";
            
            if (mysqli_query($conn, $sql)) {
                echo 'success';
            } else {
                echo "Failed to insert to database";
            }
        } else {
            echo "Hotel ID not found in the hotels table. Cannot add this room.";
        }
    } else {
        echo 'Failed to upload image';
    }
} else {
    echo 'No image found';
}

mysqli_close($conn);
?>
