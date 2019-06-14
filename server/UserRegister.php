<?php
	header("Content-Type: application/json");
	$servername = "localhost";
    $username = "wndnjs9878";
    $password = "open";
    $dbname = "phone";
     $con = new mysqli($servername, $username, $password, $dbname);
     //안드로이드 앱으로부터 아래 값들을 받음
     $userID = $_POST['userID'];
     $userPassword = $_POST['userPassword'];
     $userEmail = $_POST['userEmail'];
	 $userTime = "00:00:00:00";
     //insert 쿼리문을 실행함
     $statement = mysqli_prepare($con, "INSERT INTO USER VALUES (?, ?, ?, ?)");
     mysqli_stmt_bind_param($statement, "sssi", $userID, $userPassword, $userEmail, $userTime);
     mysqli_stmt_execute($statement);
     $response = array();
     $response["success"] = true;
     //회원 가입 성공을 알려주기 위한 부분임
     echo json_encode($response); 
?>