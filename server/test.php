<?php
    header("Content-Type: text/html;charset=UTF-8");
    
    $servername = "localhost";
    $username = "root";
    $password = "open"
    $dbname = "test";

    $conn = new mysqli($servername, $username, $password, $dbname);
   if($conn->connect_error){
		die("connection failed: ". $conn->connect_error);
	}
    $data_stream = "'".$_POST['Data1']."'";
    $query = "insert into phone(Data1) values (".$data_stream.")";
    $result = mysqli_query($conn, $query);
     
    if($result)
      echo "1";
    else
      echo "-1";
     
    mysqli_close($conn);
?>


