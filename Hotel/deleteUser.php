<?php
$servername = "localhost";
$username = "root";
$password = "";
$db_name = "hoteldb";

$conn = new mysqli($servername, $username, $password, $db_name);

// Get the user_id from the request 
$id = $_POST['user'];

// Delete the reservations first
$deleteReservationsSql = "DELETE FROM reservation WHERE user_id = '$id'";

// Perform the reservation deletions
if (mysqli_query($conn, $deleteReservationsSql)) {
    // If reservations are deleted successfully, proceed to delete the user
    $deleteUserSql = "DELETE FROM user WHERE id = '$id'";
    if (mysqli_query($conn, $deleteUserSql)) {
        // Deletion successful, return a success message
        $response["error"] = false;
        $response["message"] = "Account Deleted successfully";
    } else {
        // Return an error message if user deletion fails
        $response["error"] = true;
        $response["message"] = "Failed to delete user";
    }
} else {
    // Return an error message if reservation deletion fails
    $response["error"] = true;
    $response["message"] = "Failed to delete reservations";
}

// Set the Content-Type header to indicate JSON response
header('Content-Type: application/json');

// Return the JSON response
echo json_encode($response);
?>
