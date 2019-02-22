<?php

$zipcode=$_POST["zipcode"];
//$zipcode='492014';
$host = "localhost";
$db="shresth3_disease_system";
$user="shresth3_admin";
$password="Arashrobo123!";

$con = mysql_connect($host,$user,$password);

mysql_select_db($db);

$select="SELECT disease_id,cyber_quarantine FROM final_parameter WHERE zipcode='492014'";

$result=mysql_query($select);
while($row = mysql_fetch_array($result))
{
$disease_id=$row['disease_id'];
$cyber_quarantine= $row['cyber_quarantine']+1;

echo $cyber_quarantine;
echo $disease_id;
$update="UPDATE final_parameter SET cyber_quarantine='$cyber_quarantine' WHERE disease_id='$disease_id'";
mysql_query($update);

}

$insert="INSERT INTO cyber_quarantine SET zipcode='$zipcode',date=CURDATE()";

mysql_query($insert);



?>