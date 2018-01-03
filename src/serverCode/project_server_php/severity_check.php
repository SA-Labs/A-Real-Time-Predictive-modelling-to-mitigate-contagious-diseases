<?php



//$zipcode=$_POST["zipcode"];
$zipcode='492014';

$host = "localhost";
$db="db_name";
$user="db_user";
$password="db_pass";

$con = mysql_connect($host,$user,$password);

mysql_select_db($db);

$select="SELECT tp_th_ratio, disease  FROM result WHERE zipcode='$zipcode' ";
$result=mysql_query($select);

while($row = mysql_fetch_array($result))
{
$tp_th_ratio=$row['tp_th_ratio'];
$disease= $row['disease'];

echo $tp_th_ratio;
echo $disease;
}
