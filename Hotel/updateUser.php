<?php

    $servername = "localhost";
	$username = "root";
	$password= "";
	$db_name = "hoteldb";

	$ID = $_POST["id"];
	$Name =  $_POST["username"];
	$Pass =  $_POST["password"];
	$Email =  $_POST["email"];
	$phone = $_POST["phone"];


	$conn = new mysqli($servername,$username,$password,$db_name);

	if($conn->connect_error){


		die("not connected successfully :(");

	}else{


		$sql = "UPDATE user SET username = '$Name', email = '$Email', password = '$Pass', phone = '$phone' WHERE id = '$ID'";

		if($conn ->query($sql) === TRUE){

			$user = array(

				"name" => $Name,
				"email" => $Email,
				"password" => $Pass,
				"phone" => $phone
				);
				$response["error"] = false;
				$response["message"]= "User Info updated successfully";
				$response["user"] = $user;

				echo json_encode($response);
		}else{

			//echo "something goes wrong"."<br>";
			$user = array(

				"name" => "no data",
				"email" => "no data",
				"password" => "no data",
				"phone" => "no data"
				);
				$response["error"] = true;
				$response["message"]= "something goes worng - User Info not updated correctly!";
				$response["user"] = $user;

				echo json_encode($response);

		}
	}
?>
