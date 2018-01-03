<?php


//$zipcode_new=$_POST["zipcode"];
//$id=$_POST["id"];
$zipcode_new=50000;
$id=107;






$host = "localhost";
$db="db_name";
$user="db_user";
$password="db_pass";

$con = mysql_connect($host,$user,$password);

mysql_select_db($db);



 $select_migrate="SELECT zipcode,disease,date FROM rawdata WHERE id='$id'";
 $result_migrate=mysql_query($select_migrate);


 while($row = mysql_fetch_array($result_migrate))
  {
    $zipcode=$row['zipcode'];
    $disease=$row['disease'];
    $date=$row['date'];
  }

 $select_migrate_dec="SELECT infected FROM final_parameter WHERE zipcode='$zipcode' && disease='$disease' && date='$date'";
 $result_migrate_dec=mysql_query($select_migrate_dec);
  while($row = mysql_fetch_array($result_migrate_dec))
  {
    $infected=$row['infected'];

  }

  $infected=$infected-1;
  $update_migrate_dec="UPDATE final_parameter SET infected='$infected' WHERE zipcode='$zipcode' && disease='$disease' && date='$date' ";
  mysql_query($update_migrate_dec);

 $update="UPDATE rawdata SET zipcode='$zipcode_new', date=CURDATE() WHERE id='$id'";
 mysql_query($update);

 $select_migrate_new="SELECT zipcode,disease,date FROM rawdata WHERE id='$id'";
 $result_migrate_new=mysql_query($select_migrate_new);
  while($row = mysql_fetch_array($result_migrate_new))
  {
    $zipcode=$row['zipcode'];
    $disease=$row['disease'];
    $date=$row['date'];

  }



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
