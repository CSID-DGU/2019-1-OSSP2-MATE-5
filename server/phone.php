<?php
	header("Content-Type: application/json");

	$servername = "localhost";
	$username = "wndnjs9878";
	$password = "open";
	$dbname = "phone";

	$conn = new mysqli($servername, $username, $password, $dbname);

	if($conn->connect_error){
		die("connection failed: ". $conn->connect_error);
	}

	$sql = "select * from phone";
	$result = $conn->query($sql);
	
	$data = array();

	if($result->num_rows > 0) {
		while($row = $result->fetch_assoc()) {
			$data[] = array(
				"id" => $row["id"],
				"phonenum" => $row["phonenum"]
			);
		}
		echo json_encode($data);
	} else {
		echo "0 results";
	}

	$conn->close();
?>

