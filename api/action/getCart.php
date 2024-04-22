<?php

header('Content-Type: application/json');

include_once '../config/Database.php';

include_once '../auth/verify_token.php';

$success = "";
try {

    $query = "SELECT 
    f.name AS farmName,
    f.picture AS farmPicture,
    p.name AS productName,
    p.picture AS productPicture,
    p.unit_price AS productPrice,
    c.quantity,
    p.measuring_unit,
    (p.unit_price * c.quantity) AS totalPricePerProduct,
    SUM(p.unit_price * c.quantity) OVER () AS totalCartPrice
    FROM `cart` AS c
    JOIN product AS p ON p.id = c.product_id
    JOIN user AS u ON u.id = c.user_id
    JOIN farm AS f ON f.id = p.farm_id
    WHERE u.email = '$email'";

    $getCart = $bdd->prepare($query);
    $getCart->execute();

    $result['entity'] = $getCart->fetchAll(PDO::FETCH_ASSOC);
    $result["success"] = true;
} catch (Exception $e) {
    $result["success"] = false;
    $result["error"] = "Erreur lors de la récupération du panier";
}


echo json_encode($result, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
