<?php
	header("Content-Type: application/json");
	$servername = "localhost";
    $username = "wndnjs9878";
    $password = "";
    $dbname = "phone";
    $con = new mysqli($servername, $username, $password, $dbname);
     //안드로이드 앱으로부터 아래 값들을 받음
    $userID = $_POST['userID'];
 	$userTime = $_POST['userTime'];
     //insert 쿼리문을 실행함
    $statement = mysqli_prepare($con, "UPDATE USER SET userTime = userTime + ? WHERE userID=?");
    mysqli_stmt_bind_param($statement, "is", $userTime, $userID);
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
