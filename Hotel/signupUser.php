<?php

$servername = "localhost";
$username = "root";
$password= "";
$db_name = "hoteldb";

// Get the values sent through POST request
$name = $_POST["username"];
$password = $_POST["password"];
$email = $_POST["email"];
$phone = $_POST["phone"];

// Create a new MySQLi object to connect to the database
$conn = new mysqli($servername, $username, $password, $db_name);

// Check if the connection was successful
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

// Check if the username and email already exist in the database
$sql = "SELECT * FROM user WHERE username = '$name' OR email = '$email'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
  // Username or email already exist in the database
  $user = array(
    "name" => "no data",
    "email" => "no data",
    "password" => "no data",
    "phone" => "no data"
  );

  $response["error"] = true;
  $response["message"] = "Username or email already exists";
  $response["user"] = $user;

  echo json_encode($response);
} else {
  // Insert the new user's data into the database
  $sql2 = "INSERT INTO user (username, password, email, phone) VALUES ('$name', '$password', '$email', '$phone')";
  if ($conn->query($sql2) === TRUE) {
    // User registration successful
    $user = array(
      "name" => $name,
      "email" => $email,
      "password" => $password,
      "phone" => $phone
    );

    $response["error"] = false;
    $response["message"] = "User registered successfully";
    $response["user"] = $user;
    echo json_encode($response);
	
  } else {
    // User registration failed
    $user = array(
      "name" => "no data",
      "email" => "no data",
      "password" => "no data",
      "phone" => "no data"
    );
    $response["error"] = true;
    $response["message"] = "Something went wrong";
    $response["user"] = $user;
    echo json_encode($response);
  }
}

// Close the database connection
$conn->close();

?>
