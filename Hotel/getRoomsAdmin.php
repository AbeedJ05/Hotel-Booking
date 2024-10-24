<?php
// database connection parameters
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "hoteldb";

// create connection
$conn = new mysqli($servername, $username, $password, $dbname);


// Check for successful connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// Query to fetch reservations with hotel and room details
 $sql = "SELECT  rm.id,rm.hotel_id ,rm.price , h.hotel_name, rm.room_type , rm.description , rm.img
        FROM rooms rm
        INNER JOIN hotels h ON rm.hotel_id = h.hotel_id";
    

$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
    $items = array();

    while ($row = mysqli_fetch_assoc($result)) {
       $item = array();
        $item['id'] = $row['id'];
        $item['hotelid'] = $row['hotel_id'];
        $item['hotel_name'] = $row['hotel_name'];
        $item['room_type'] = $row['room_type'];
        $item['price'] = $row['price'];
        $item['description'] = $row['description'];
        $item['img'] = $row['img'];

          array_push($items, $item);
    }
 
    header('Content-Type: application/json');
    // return the items in JSON format
    echo json_encode($items);
} else {
    echo "No rooms found.";
}
mysqli_close($conn);
?>