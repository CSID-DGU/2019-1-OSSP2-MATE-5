<?php

	header("Content-Type: application/json");
	$servername = "localhost";
    $username = "wndnjs9878";
    $password = "open";
    $dbname = "phone";
     $con = new mysqli($servername, $username, $password, $dbname);


	$userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    $statement = mysqli_prepare($con, "SELECT userID FROM USER WHERE userID = ? AND userPassword = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID);
    $response = array();
    $response["success"] = false;
    while(mysqli_stmt_fetch($statement)){
      $response["success"] = true;
      $response["userID"] = $userID;
    }
    echo json_encode($response);
?>
