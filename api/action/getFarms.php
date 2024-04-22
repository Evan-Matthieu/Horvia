<?php

header('Content-Type: application/json');

include_once '../config/Database.php'; // Assure-toi que le chemin vers Database.php est correct.

$pageSize = isset($_GET['page_size']) ? $_GET['page_size'] : 20;
$pageNumber = isset($_GET['page_number']) ? $_GET['page_number'] : 1;


$success = "";


if (isset($_GET['cId'])) {
    $categoryId = $_GET['cId'];

    try {
    
        // Récupération des fermes avec la pagination
        $query = "SELECT f.id AS id, 
        f.name AS name, 
        f.description AS description,
         f.rate AS rate,
          f.rate_number AS rate_number,
           f.picture AS picture,
            l.address AS address,
             l.zip_code AS zipCode,
              l.city AS city
         FROM farm_category AS fc 
         JOIN farm AS f ON f.id = fc.farm_id 
         JOIN location AS l ON l.id = f.location_id 
         JOIN category AS c ON c.id = fc.category_id";
    
        if(isset($_GET['query'])){
            $query .= " WHERE f.name LIKE '%". $_GET['query']."%'";
        } elseif (isset($_GET['cId'])) {
            $query .= " WHERE c.id = '$categoryId'";
        }
    
        $query = $query." GROUP BY f.id LIMIT ".(($pageNumber - 1) * $pageSize).",".$pageSize;
        $getFarm = $bdd->prepare($query);
        $getFarm->execute();
    
        $result['entity']['items'] = $getFarm->fetchAll(PDO::FETCH_ASSOC);
    
        // Récupération du nombre total de ferme correspondant au filtre
        $queryTotalCount = "SELECT COUNT(*) AS total FROM farm AS f";

        if (isset($_GET['cId'])){
            $queryTotalCount.= " JOIN farm_category AS fc ON fc.farm_id = f.id WHERE fc.category_id = '$categoryId'";
        }
    
        if(isset($_GET['query'])){
            $queryTotalCount = $queryTotalCount." WHERE name LIKE '%". $_GET['query']."%'";
        }
    
        $getNumberFarm = $bdd->prepare($queryTotalCount);
        $getNumberFarm->execute();
        
    
        $result['entity']['totalCount'] = $getNumberFarm->fetch(PDO::FETCH_ASSOC)['total'];
        $result["success"] = true;
    } catch (Exception $e) {
        $result["success"] = false;
        $result["error"] = "Erreur lors de la récupération de la ferme";
    }
} else {
    try {
    
        // Récupération des fermes avec la pagination
        $query = "SELECT 
        f.id AS id,
        f.name AS name, 
        f.description AS description, 
        f.rate AS rate, 
        f.rate_number AS rate_number, 
        f.picture AS picture, 
        l.address AS address, 
        l.zip_code AS zipCode, 
        l.city AS city,
        GROUP_CONCAT(c.id) AS categories
        FROM farm AS f
        JOIN location AS l ON l.id = f.location_id
        JOIN farm_category AS fc ON fc.farm_id = f.id
        JOIN category AS c ON c.id = fc.category_id";
    
        if(isset($_GET['query'])){
            $query = $query." WHERE f.name LIKE '%". $_GET['query']."%'";
        }
    
        $query = $query." GROUP BY f.id LIMIT ".(($pageNumber - 1) * $pageSize).",".$pageSize;
        $getFarm = $bdd->prepare($query);
        $getFarm->execute();
    
        $result['entity']['items'] = $getFarm->fetchAll(PDO::FETCH_ASSOC);
    
        // Récupération du nombre total de ferme correspondant au filtre
        $queryTotalCount = "SELECT COUNT(*) AS total FROM farm";
    
        if(isset($_GET['query'])){
            $queryTotalCount = $queryTotalCount." WHERE name LIKE '%". $_GET['query']."%'";
        }
    
        $getNumberFarm = $bdd->prepare($queryTotalCount);
        $getNumberFarm->execute();
        
    
        $result['entity']['totalCount'] = $getNumberFarm->fetch(PDO::FETCH_ASSOC)['total'];
        $result["success"] = true;
    } catch (Exception $e) {
        $result["success"] = false;
        $result["error"] = "Erreur lors de la récupération de la ferme";
    }
}






echo json_encode($result, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);