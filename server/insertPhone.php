<?php
	if($_SERVER['REQUEST_METHOD'] != "POST") {
		header("HTTP/1.0 404 Not Found", true, 404);
		exit();
	}
	//header("Content-Type: application/json");

	$servername = "localhost";
	$username = "wndnjs9878";
	$password = "open";
	$dbname = "phone";
	
	$conn = new mysqli($servername, $username, $password, $dbname);
	
	if($conn->connect_error){
		die("connection failed: ". $conn->connect_error);
	}
	$data = json_decode(file_get_contents("php://input"));
	$sql = "insert into phone(phonenum) values ('".$data->phone."')";
	if(mysqli_query($conn, $sql)){
		echo "success";
	} else {
		echo "error";
	}
	
	$conn->close();
?>

