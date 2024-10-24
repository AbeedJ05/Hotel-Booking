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

// Retrieve the user_id from the query parameters
$user_id = $_GET['user_id'];

// Query to fetch reservations with hotel and room details for the specific user
$sql = "SELECT r.reservation_id, r.total_price, r.check_in_date, r.check_out_date, h.hotel_name, rm.room_type
        FROM reservation r
        INNER JOIN rooms rm ON r.room_id = rm.id
        INNER JOIN hotels h ON rm.hotel_id = h.hotel_id
        INNER JOIN user u ON r.user_id = u.id
        WHERE r.user_id = '$user_id' ORDER BY r.reservation_id DESC";

$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
    $items = array();

    while ($row = mysqli_fetch_assoc($result)) {
        $item = array();
        $item['hotel_name'] = $row['hotel_name'];
        $item['room_type'] = $row['room_type'];
        $item['check_in_date'] = $row['check_in_date'];
        $item['check_out_date'] = $row['check_out_date'];
        $item['total_price'] = $row['total_price'];
        $item['reservation_id'] = $row['reservation_id'];
        array_push($items, $item);
    }

    header('Content-Type: application/json');
    // return the items in JSON format
    echo json_encode($items);
} else {
    echo "No reservations found for the specified user.";
}

mysqli_close($conn);
?>
