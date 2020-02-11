<?php

	require_once 'menu.php';

	$id = "";

   	$name = "";

        $price = "";

        if(isset($_POST['id'])){

             $id = $_POST['id'];

        }

        if(isset($_POST['name'])){

             $name = $_POST['name'];

        }

        if(isset($_POST['price'])){

             $price = $_POST['price'];

        }

	$menuObject = new Menu();

	if(!empty($id) && empty($name) && empty($price)){

	     $menuObject->delFrom($id);	

	}

        if(!empty($id) && !empty($name) && !empty($price)){

             $menuObject->insertInto($id, $name, $price);

    	}
?>