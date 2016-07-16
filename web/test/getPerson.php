<!DOCTYPE html>
<?php 
   	$db=mysqli_connect("localhost","root","","person_test");
$result=mysqli_query($db,"SELECT * FROM person");
$response = array();
if(mysqli_num_rows($result)>0){
	while($row = mysqli_fetch_assoc($result)){
		 $product = array("id"=>$row["id"]);
        // push single product nto final response array
        array_push($response,$product);
	}
    // echoing JSON response
    $arrayName = array('status' =>'success');
    $arrayx = array("data"=>$response);
    $array = array($arrayName,$arrayx);
    echo json_encode($array);
}
?>