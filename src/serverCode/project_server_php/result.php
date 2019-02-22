<?php




$id=$_POST["id"];



$host = "localhost";
$db="shresth3_disease_system";
$user="shresth3_admin";
$password="Arashrobo123!";

$con = mysql_connect($host,$user,$password);

mysql_select_db($db);

$select="SELECT zipcode,tp_th_ratio,disease FROM result ";
$result=mysql_query($select);
echo "-";
echo mysql_num_rows($result);
while($row = mysql_fetch_array($result))
{   echo "-";
    echo $row['zipcode'];
     echo "-";
    echo $row['disease'];
    echo "-";
    echo $row['tp_th_ratio'];
   
    


}
echo "-";
?>