<?php

header('Content-Type: application/json');

include_once '../config/Database.php'; // Assure-toi que le chemin vers Database.php est correct.



$farmId = $_GET["id"];

if (isset($farmId)) {
    $success = "";
    try {

        $query = "SELECT
            p.id AS productId,
            c.name AS name, 
            p.name AS productName, 
            p.description AS productDescription, 
            p.unit_price AS productUnitPrice,
            p.picture AS productPicture,
            p.measuring_unit AS productMeasuringUnit
            FROM category AS c
            JOIN product AS p ON p.category_id = c.id
            JOIN farm_category AS fc ON fc.category_id = c.id
            JOIN farm AS f ON f.id = fc.farm_id";


        $query .= " WHERE fc.farm_id = " . $farmId." AND c.is_main_category = 0";

        $getCategories = $bdd->prepare($query);
        $getCategories->execute();

        $result["entity"] = $getCategories->fetchAll(PDO::FETCH_ASSOC);
        $result["success"] = true;
    } catch (Exception $e) {
        $result["success"] = false;
        $result["error"] = "Erreur lors de la récupération des catégories";
    }
} else {
    $result["success"] = false;
    $result["error"] = "Paramètres non valides";
}

//var_dump($query);

echo json_encode($result, JSON_PRETTY_PRINT);
