<?php

$host = "localhost";
$db="db_name";
$user="db_user";
$password="db_pass";

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
