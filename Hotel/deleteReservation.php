<?php
$servername = "localhost";
$username = "root";
$password = "";
$db_name = "hoteldb";

$conn = new mysqli($servername, $username, $password, $db_name);

// Get the user_id from the request 
$id = $_POST['reservation_id'];

// Delete the reservations first
$deleteReservationsSql = "DELETE FROM reservation WHERE reservation_id = '$id'";

// Perform the reservation deletions
if (mysqli_query($conn, $deleteReservationsSql)) {
    
   
        // Deletion successful, return a success message
        $response["error"] = false;
        $response["message"] = "reservations Deleted successfully";
 } 
 else {
    // Return an error message if reservation deletion fails
    $response["error"] = true;
    $response["message"] = "Failed to delete reservations";
}

// Set the Content-Type header to indicate JSON response
header('Content-Type: application/json');

// Return the JSON response
echo json_encode($response);
?>
