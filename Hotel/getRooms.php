<?php
// Connect to MySQL database
// database connection parameters
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "hoteldb";

// create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection


// Check if the hotel ID is provided in the query string
if (!isset($_GET['hotel_id'])) {
    die("Hotel ID not provided");
}

// Sanitize the hotel ID
$query = "SELECT hotel_id FROM hotels";
$result = $conn->query($query);
$hotel_id = $conn->real_escape_string($_GET['hotel_id']);

// Fetch room data from the database for the specified hotel ID
$query = "SELECT * FROM rooms WHERE hotel_id = '$hotel_id'";
$result = $conn->query($query);

if (!$result) {
    die("Error executing the query: " . $conn->error);
}

// Prepare the response array
$response = array();
 
// Check if there are any rows returned
if ($result->num_rows > 0) {
    // Loop through each row and add it to the response array
    while ($row = $result->fetch_assoc()) {
        $room = array();
            $room['id']= $row['id'];
            $room['hotelid']= $row['hotel_id'];
            $room['room_type'] = $row['room_type'];
            $room['price'] = $row['price'];
            $room['description'] = $row['description'];
            $room['img'] = $row['img'];
        
        
            array_push($response, $room);
    }
   
    header('Content-Type: application/json');
   
    echo json_encode($response);
}else {
    // return an empty array if there are no items
    echo "[]";
}

// Close the database connection
$conn->close();


?>
