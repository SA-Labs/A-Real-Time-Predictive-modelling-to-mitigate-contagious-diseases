<?php




$id=$_POST["id"];



$host = "localhost";
$db="db_name";
$user="db_user";
$password="db_pass";

$con = mysql_connect($host,$user,$password);

mysql_select_db($db);

$update="UPDATE rawdata SET recovered_date=CURDATE() WHERE id='$id'";
mysql_query($update);

$select="SELECT date,recovered_date,disease FROM rawdata WHERE id='$id' ";
$result=mysql_query($select);


while($row = mysql_fetch_array($result))
{
$disease=$row['disease'];
$date=$row['date'];
$recovered_date= $row['recovered_date'];

}

$update="UPDATE rawdata SET days=DATEDIFF('$recovered_date','$date') WHERE id='$id'";
mysql_query($update);

$select="SELECT date,recovered_date,disease FROM rawdata WHERE id='$id' ";
$result=mysql_query($select);

$select="SELECT days FROM rawdata WHERE disease='$disease' ";
$result=mysql_query($select);
$total=0;
$x=0;

while($row = mysql_fetch_array($result))
{
    if($row['days']!=null)
    {
$total+=$row['days'];
$x++;
    }
}
$av=$total/$x;
$update="UPDATE final_parameter SET days='$av' WHERE disease='$disease'";
mysql_query($update);


?>
