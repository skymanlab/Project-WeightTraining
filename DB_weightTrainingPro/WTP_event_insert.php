<?php

header("Content-Type:text/html; charset=UTF-8");

  // step 1. mysql connection infomation
  $server_name = "localhost";
  $user_name = "skyman13";
  $password = "dlghkd1570tg!";
  $db_name = "skyman13";

  // step 2. table name
  $table_name = "WTP_event";

  // step 3. get POST data 
  $column_userCount = $_POST["userCount"];
  $column_eventName = $_POST["eventName"];
  $column_muscleArea = $_POST["muscleArea"];
  $column_equipmentType = $_POST["equipmentType"];
  $column_groupType = $_POST["groupType"];
  $column_properWeight = $_POST["properWeight"];
  $column_maxWeight = $_POST["maxWeight"];

  // step 4. DB connection
  $con = mysqli_connect($server_name, $user_name, $password, $db_name);

  // setp 5. query : insert into HM_event
  $sql = "INSERT INTO $table_name ( userCount, 
                                    eventName, 
                                    muscleArea, 
                                    equipmentType, 
                                    groupType, 
                                    properWeight, 
                                    maxWeight)
               VALUES       ( $column_userCount, 
                              $column_eventName, 
                              $column_muscleArea, 
                              $column_equipmentType, 
                              $column_groupType, 
                              $column_properWeight, 
                              $column_maxWeight)";

  // setp 6. query execute
  $result = mysqli_query($con, $sql);

  // step 7. return result
  if (0 < $result) {

    echo "success";

  } else {

    echo "fail";
  }

  mysqli_close($con);

  ?>
