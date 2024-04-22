<?php

header('Content-Type: application/json; charset=utf-8');

include_once '../config/Database.php'; // Assure-toi que le chemin vers Database.php est correct.


    $success = "";
    try {

        $query = "SELECT 
        c.id AS id,
        c.name AS name,
        c.picture AS picture,
        COUNT(fc.id) AS farm_count
        FROM category AS c
        LEFT JOIN farm_category AS fc ON fc.category_id = c.id
        WHERE c.is_main_category = 1
        GROUP BY c.id";

        $getCategories = $bdd->prepare($query);
        $getCategories->execute();     

        $result["entity"] = $getCategories->fetchAll(PDO::FETCH_ASSOC);
        $result["success"] = true;
    } catch (Exception $e) {
        $result["success"] = false;
        $result["error"] = "Erreur lors de la récupération des catégories";
    }

echo json_encode($result, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);

