<?php


$medication_rate=$_POST["medication"];
$vaccination_rate=$_POST["vaccination"];
$isolation=$_POST["isolation"];
$zipcode=$_POST["zipcode"];
$disease=$_POST["disease"];
$date=$_POST["date"];





$host = "localhost";
$db="shresth3_disease_system";
$user="shresth3_admin";
$password="Arashrobo123!";

$con = mysql_connect($host,$user,$password);

mysql_select_db($db);

$update="UPDATE final_parameter SET medication='$medication_rate',vaccination='$vaccination_rate',isolation='$isolation' WHERE disease='$disease' && zipcode='$zipcode' && date='$date'";
mysql_query($update);

?>