<?php
header('Content-Type: application/json');
include_once '../config/Database.php';



    $success = "";
    try {
        $query = "";
        if($withProducts = 0){
            $query = "SELECT c.name AS name
            FROM category AS c
            WHERE c.farm_id = ".$farmId;
        }
    
        else{
            
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
            JOIN product AS p ON p.id = pc.product_id";
        }

        $query = $query + "WHERE fc.farm_id = ".$farmId." AND c.is_main_category = 0";
        $query = "SELECT 
        c.name AS name,
        c.picture AS picture,
        COUNT(fc.id) AS farm_count
        FROM category AS c
        LEFT JOIN farm_category AS fc ON fc.category_id = c.id
        WHERE c.is_main_category = 1
        GROUP BY c.id";


        $getCategories = $bdd->prepare($query);
        $getCategories->execute();     
    
        $result['entity'] = $getCategories->fetchAll(PDO::FETCH_ASSOC);
        $result["success"] = true;
    } catch (Exception $e) {
        $result["success"] = false;
        $result["error"] = "Erreur lors de la récupération des catégories";
    }


//var_dump($query);

echo json_encode($result, JSON_PRETTY_PRINT);