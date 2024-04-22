<?php

header('Content-Type: application/json');

include_once '../config/Database.php'; 

include_once '../auth/verify_token.php';

$success = "";
try {

    $query = "SELECT firstname, lastname, email, phone, birth_date, picture, civility
    FROM user 
    WHERE email = "."'".$email."'";

    $getUsers = $bdd->prepare($query);
    $getUsers->execute();

    $result["entity"] = $getUsers->fetch(PDO::FETCH_ASSOC);
    $result["success"] = true;
} catch (Exception $e) {
    $result["success"] = false;
    $result["error"] = "Erreur lors de la récupération de l'utilisateur";
}


echo json_encode($result, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);