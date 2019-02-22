<?php


$date=$_POST["date"];
$zipcode=$_POST["zipcode"];
$disease=$_POST["disease"];




$host = "localhost";
$db="shresth3_disease_system";
$user="shresth3_admin";
$password="Arashrobo123!";

$con = mysql_connect($host,$user,$password);

mysql_select_db($db);



$insert="INSERT INTO rawdata SET zipcode='$zipcode', disease='$disease', date='$date'";

mysql_query($insert);
////////////////////////////////////////////////////////////////////////////////////////



$select="SELECT disease_id,susceptible,infected,recovered FROM final_parameter WHERE zipcode='$zipcode' && disease='$disease' && date='$date' ";
$result=mysql_query($select);

if (mysql_num_rows($result) > 0) {

echo 'his';
while($row = mysql_fetch_array($result))
{
$disease_id=$row['disease_id'];
$susceptible= $row['susceptible']-1;

$infected= ($row['infected'])+1;
}


$update="UPDATE final_parameter SET susceptible='$susceptible',infected='$infected' WHERE disease_id='$disease_id'";
mysql_query($update);

} 
else 
{


 $select_population="SELECT population FROM population_census WHERE zipcode='$zipcode'";
 $result_population=mysql_query($select_population);
 while($row = mysql_fetch_array($result_population))
  {
   $population=$row['population'];
  }
  
  
  $select_infected_recovered="SELECT date,infected,recovered FROM final_parameter WHERE zipcode='$zipcode'&& disease='$disease' ORDER BY date";
 $result_infected_recovered=mysql_query($select_infected_recovered);
 $max=(mysql_num_rows($result_infected_recovered))-1;
 if (mysql_num_rows($result_infected_recovered) > 0)

 {
 echo "hi123";
 while($row = mysql_fetch_array($result_infected_recovered))
  {
    $infected=$row['infected'];
    $recovered=$row['recovered'];
  }
 
 
}

 else 
 {
  $infected=1;
  $recovered=0;
 
 }
      
       
   $susceptible= $population-($infected+$recovered);
   $insert_new="INSERT INTO final_parameter SET zipcode='$zipcode',disease='$disease',date='$date',susceptible='$susceptible',infected='$infected',recovered='$recovered'";
   

 mysql_query($insert_new);
 
}




?>