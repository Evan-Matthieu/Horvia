<?php

header('Content-Type: application/json');

include_once '../config/Database.php';

include_once '../auth/verify_token.php';

$success = "";
try {

    $query = "SELECT 
    o.id AS 'order_id',
   f.name,
   o.status,
   f.picture,
    SUM(op.quantity * p.unit_price) AS 'total_price' 
FROM `order` AS o
JOIN user AS u ON u.id = o.user_id
JOIN farm AS f ON f.id = o.farm_id
JOIN order_product AS op ON op.order_id = o.id
JOIN product AS p ON p.id = op.product_id
WHERE u.email = '".$email."'
GROUP BY o.id;";

    $getLastOrders = $bdd->prepare($query);
    $getLastOrders->execute();

    $result['entity'] = $getLastOrders->fetchAll(PDO::FETCH_ASSOC);
    $result["success"] = true;
} catch (Exception $e) {
    $result["success"] = false;
    $result["error"] = "Erreur lors de la récupération des dernières commandes";
}


echo json_encode($result, JSON_PRETTY_PRINT);
