<?php

$servername = "localhost";
$username = "root";
$password = "";
$db_name = "hoteldb";

$ID = $_POST["id"];
$hotel_id = $_POST["hotel_id"];
$room_type = $_POST["room_type"];
$price = $_POST["price"];
$description = $_POST["description"];

$conn = new mysqli($servername, $username, $password, $db_name);

if ($conn->connect_error) {
    die("not connected successfully :(");
} else {
    // Check if the hotel_id exists in the Hotels table
    $checkHotelQuery = "SELECT hotel_id FROM hotels WHERE hotel_id = $hotel_id";
    $checkHotelResult = $conn->query($checkHotelQuery);

    if ($checkHotelResult->num_rows == 0) {
        // Hotel Id not found in the database
        $response["error"] = true;
        $response["message"] = "Hotel Id not found";
        echo json_encode($response);
    } else {
        // Hotel Id exists, proceed with updating the rooms table
        $sql = "UPDATE rooms SET hotel_id = '$hotel_id', room_type = '$room_type', price = '$price', description = '$description' WHERE id = '$ID'";

        if ($conn->query($sql) === TRUE) {
            $rooms = array(
                "hotel_id" => $hotel_id,
                "room_type" => $room_type,
                "price" => $price,
                "description" => $description
            );
            $response["error"] = false;
            $response["message"] = "Rooms updated successfully";
            $response["rooms"] = $rooms;

            echo json_encode($response);
        } else {
            //echo "something goes wrong"."<br>";
            $rooms = array(
                "hotel_id" => "no data",
                "room_type" => "no data",
                "price" => "no data",
                "description" => "no data"
            );
            $response["error"] = true;
            $response["message"] = "Something went wrong - rooms not updated correctly!";
            $response["rooms"] = $rooms;

            echo json_encode($response);
        }
    }
}
?>
