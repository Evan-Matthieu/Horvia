<?php

header('Content-Type: application/json');

include_once '../config/Database.php'; // Assure-toi que le chemin vers Database.php est correct.

$farmId = $_GET["id"];

if(isset($farmId)){
    $success = "";
    try {
        $query = "SELECT 
        c.name AS name, 
        p.name AS productName, 
        p.description AS productDescription, 
        p.unit_price AS productUnitPrice,
        p.measuring_unit AS productMeasuringUnit,
        p.picture AS productPicture
        FROM category AS c
        JOIN farm_category AS fc ON fc.category_id = c.id
        JOIN product_category AS pc ON pc.category_id = c.id
        JOIN product AS p ON p.id = pc.product_id
        WHERE fc.farm_id = ".$farmId." AND c.is_main_category = 0";


        $getCategories = $bdd->prepare($query);
        $getCategories->execute();     
    
        $result['entity'] = $getCategories->fetchAll(PDO::FETCH_ASSOC);
        $result["success"] = true;
    } catch (Exception $e) {
        $result["success"] = false;
        $result["error"] = "Erreur lors de la récupération des catégories";
    }
}
else{
    $result["success"] = false;
    $result["error"] = "Paramètres non valides";
}

//var_dump($query);

echo json_encode($result, JSON_PRETTY_PRINT);