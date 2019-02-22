<?php

$host = "localhost";
$db="shresth3_disease_system";
$user="shresth3_admin";
$password="Arashrobo123!";

$con = mysql_connect($host,$user,$password);

mysql_select_db($db);
if(!$con)
{
echo "connection error...";
}
else
{
//echo "<h3>database connection Success...</h3>";
}
?>