<?php

	include_once 'db-connect.php';
	
	class Menu{

	     private $db_table = "menu";
	     
	     private $db;

	    public function __construct(){
            	$this->db = new DbConnect();
            }
	
	
	    public function viewMenu(){
	
	        $json = array();

	        $sql_query= "select * from ".$this->db_table."";

	        $result = mysqli_query($this->db->getDb(), $sql_query);

	        if(mysqli_num_rows($result)>0){

	             $json['success'] = 1;

	             $menu = array(); 

                     while($row = mysqli_fetch_assoc($result)) {

	                  array_push($menu, $row);
	             }

	             $json['menu'] = $menu;
  	        }

	        else{
  
	            $json['success'] = 0;
	            $json['message'] = 'No data';
	        }
	        echo json_encode($json);
	        mysqli_close($this->db->getDb());
	    }


	    public function insertInto($id, $name, $price) {

		$query = "insert into ".$this->db_table." values ('$id', '$name', '$price')";

	        $inserted = mysqli_query($this->db->getDb(), $query);
		
		if($inserted == 1){

                    $json['success'] = 1;
                    $json['message'] = "Inserted into table";

                }else{

                    $json['success'] = 0;
                    $json['message'] = "Not inserted into table";

                }
		echo json_encode($json);
		mysqli_close($this->db->getDb());
		
	    }


	    public function delFrom($id) {

		$query = "delete from ".$this->db_table." where id = '$id'";

		$deleted = mysqli_query($this->db->getDb(), $query);

		if($deleted == 1){

		     $json['success'] = 1;
		     $json['message'] = "Successfully deleted";

		}else{
		     $json['success'] = 0;
		     $json['message'] = "Deletion unsuccessful";
		}
		echo json_encode($json);
		mysqli_close($this->db->getDb());
		
	    }


	}


?>