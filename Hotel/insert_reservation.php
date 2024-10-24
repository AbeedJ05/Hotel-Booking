<?php
// Assuming you have a connection to your MySQL database already established
$servername = "localhost";
$username = "root";
$password = "";
$db_name = "hoteldb";

// Get the data from the API request
$hotelId = (int)$_POST["hotel_id"];
$roomId = (int)$_POST["room_id"];
$userId = (int)$_POST["user_id"];
$checkInDate = $_POST["check_in_date"];
$checkOutDate = $_POST["check_out_date"];
$totalPrice = $_POST["total_price"];
// Create a new MySQLi object to connect to the database
$conn = new mysqli($servername, $username, $password, $db_name);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$availabilityCheckSql = "SELECT * FROM reservation 
                         WHERE room_id = '$roomId' 
                         AND check_out_date >= '$checkInDate' 
                         AND check_in_date <= '$checkOutDate'";

$availabilityResult = $conn->query($availabilityCheckSql);

if ($availabilityResult->num_rows > 0) {
    // Room is not available for the selected dates
    $response["error"] = true;
    $response["message"] = "Room is already reserved for the selected dates.";
    echo json_encode($response);
} else {
    $sql = "INSERT INTO reservation (hotel_id, room_id, user_id, check_in_date, check_out_date, total_price) 
    VALUES ('$hotelId', '$roomId', '$userId', '$checkInDate', '$checkOutDate', '$totalPrice')";

// Initialize the response array
if ($conn->query($sql) === TRUE) {
// Successful insertion
$reservation = array(
    "hotel_id" => $hotelId,
    "room_id" => $roomId,
    "user_id" => $userId,
    "check_in_date" => $checkInDate,
    "check_out_date" => $checkOutDate,
    "total_price" => $totalPrice
);

$response["error"] = false;
$response["message"] = "Reservation inserted successfully";
$response["reservation"] = $reservation;
echo json_encode($response);
} else {
// Error in insertion
$response["error"] = true;
$response["message"] = "Something went wrong";
$response["reservation"] = $reservation;
echo json_encode($response);
}

}






// Close the database connection
$conn->close();
?>
