<?php
header("Content-Type: application/json");
	$servername = "localhost";
    $username = "wndnjs9878";
    $password = "open";
    $dbname = "phone";

     $con = new mysqli($servername, $username, $password, $dbname);     
	 $userID = $_POST['userID'];
     $statement = mysqli_prepare($con, "SELECT userID FROM USER WHERE userID = ?");
     mysqli_stmt_bind_param($statement, "s", $userID);
     mysqli_stmt_execute($statement);
     mysqli_stmt_store_result($statement);
     mysqli_stmt_bind_result($statement, $userID);
     $response = array();
     $response["success"] = true;
     while(mysqli_stmt_fetch($statement)){
       $response["success"] = false;//회원가입불가를 나타냄
       $response["userID"] = $userID;
     }
     //데이터베이스 작업이 성공 혹은 실패한것을 알려줌
     echo json_encode($response);
?>