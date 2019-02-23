<?php

$host = "localhost";
$db="db_name";
$user="db_user";
$password="db_pass";
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
