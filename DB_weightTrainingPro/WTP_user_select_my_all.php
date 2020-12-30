<?php
	header("Content-Type:text/html; charset=UTF-8");

	// step 1. mysql connection infomation
	$server_name = "localhost";
	$user_name = "skyman13";
	$password = "dlghkd1570tg!";
	$db_name = "skyman13";

	// step 2. table name
	$table_name = "WTP_user";

	// step 3. get POST data 
	$column_name = $_POST["name"];
	$column_email = $_POST["email"];
	$column_salt = $_POST["salt"];
	$column_password = $_POST["password"];

	// step 4. DB connection
	$con = mysqli_connect($server_name, $user_name, $password, $db_name);
	
  	// setp 5. query 
  	$sql = "SELECT * FROM $table_name WHERE name=$column_name AND email=$column_email AND salt=$column_salt AND password=$column_password";

	// setp 6. query execute
	$result = mysqli_query($con, $sql);

	if (0 < $result) {

		$response = array();

		while($row = mysqli_fetch_array($result) ) {
			array_push($response, array("count"=>$row[0] ,
										"name" => $row[1],
										"email"=>$row[2],
										"salt"=>$row[3],
										"password"=>$row[4],
							)
					);
		}

	echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);

	} else {

		echo "fail";
	}

	mysqli_close($con);

?>