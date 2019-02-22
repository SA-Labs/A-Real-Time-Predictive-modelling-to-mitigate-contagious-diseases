<?php

$host = "localhost";
$db="shresth3_disease_system";
$user="shresth3_admin";
$password="Arashrobo123!";

$con = mysql_connect($host,$user,$password);

mysql_select_db($db);



$sql_query="SELECT @last_id := MAX(id) FROM rawdata;";
$result= mysql_query($sql_query);
$sql_query="SELECT * FROM rawdata WHERE id = @last_id;";
$result= mysql_query($sql_query);

if(mysql_num_rows($result)>0)
{
$row =mysql_fetch_assoc($result);
$name= $row["id"];
echo $name;
}
else
{
echo"error..";
}
?>