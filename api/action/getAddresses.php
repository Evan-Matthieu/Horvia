<?php

header('Content-Type: application/json');

include_once '../config/Database.php'; 

include_once '../auth/verify_token.php';

$success = "";
try {

    $query = "SELECT l.address AS address, l.city AS city, l.further_details AS further_details, l.zip_code AS zip_code
    FROM user AS u 
    JOIN location AS l ON l.id = u.location_id 
    WHERE u.email = "."'".$email."'";

    $getUsers = $bdd->prepare($query);
    $getUsers->execute();

    $result['entity'] = $getUsers->fetch(PDO::FETCH_ASSOC);
    $result["success"] = true;
} catch (Exception $e) {
    $result["success"] = false;
    $result["error"] = "Erreur lors de la récupération de l'addresse";
}


echo json_encode($result, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);