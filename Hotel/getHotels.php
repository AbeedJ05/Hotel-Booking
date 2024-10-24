<?php
// database connection parameters
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "hoteldb";

// create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Fetch hotel data from the database
// query to retrieve all items from the database
$sql = "SELECT * FROM hotels";
$result = $conn->query($sql);

// Prepare the response array
if ($result->num_rows > 0) {
    // create an array to hold the items
    $items = array();

    // loop through the rows and add each item to the array
    while ($row = $result->fetch_assoc()) {
        $item = array();
         
        $item['id'] = $row['hotel_id'];
        $item['namename'] = $row['hotel_name'];
        $item['co'] = $row['country'];
        $item['ra'] = $row['rating'];
        $item['img'] = $row['omg'];

        // // Assuming your images are stored in a folder named "upload"
        // // and the image filename is stored in the database column "image_filename"
        // $item['img'] = 'uploads/' . $row['omg'];

        array_push($items, $item);
    }

    // set the content type header
    header('Content-Type: application/json');

    // return the items in JSON format
    echo json_encode($items);
} else {
    // return an empty array if there are no items
    echo "[]";
}

// Close the database connection
$conn->close();
?>
