<?php
	$servername = "localhost";
	$username = "root";
	$password= "";
	$db_name = "hoteldb";

	$Email = $_POST["email"];
	$username = $_POST["email"];
	$PHONE = $_POST["email"];
	$Pass = $_POST["password"];




	$conn = new mysqli($servername,$username,$password,$db_name);

	if($conn->connect_error){


		die("not connected successfully :(");

	}else{


		//echo "connected successfully :)";
		$sql = "SELECT * FROM user WHERE (email ='$Email' or username ='$username' or phone = '$PHONE') AND password = '$Pass'";
		$result = $conn->query($sql);
		if($result ->num_rows>0){

			//echo "some data selected";
			while($row = $result -> fetch_assoc()){

				$user = array(
				"id" => $row["id"],
				"name" => $row["username"],
				"email" => $row["email"],
				"password" => $row["password"],
				"phone" => $row["phone"] // this line to include the phone number
				);

				$response["error"] = false;
				$response["message"]= "Loged In Successfully";
				$response["user"] = $user;
			}

			 echo json_encode($response);

		}else{


				$user = array(

				"id" => 0,
				"name" => "No Data",
				"email" => "No Data",
				"password" => "No Data",
				"phone" => "No Data"
				);

				$response["error"] = true;
				$response["message"]= " Faield to Logging";
				$response["user"] = $user;

			echo json_encode($response);
		}

	}

?>
