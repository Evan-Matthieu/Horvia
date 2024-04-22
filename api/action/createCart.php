<?php

header('Content-Type: application/json');

include_once '../config/Database.php';

$json = json_decode(file_get_contents('php://input'), true);

include_once '../auth/verify_token.php';

$result = array();

if (isset($json['id']) && isset($json['quantity'])) {

    $productID = $json['id'];
    $quantity = $json['quantity'];

    try {
        $farmIDQuery = $bdd->prepare("SELECT farm_id FROM product WHERE id = :productID");
        $farmIDQuery->bindParam(':productID', $productID);
        $farmIDQuery->execute();
        $farmID = $farmIDQuery->fetchColumn();

        $userIDQuery = $bdd->prepare("SELECT id FROM user WHERE email = :email");
        $userIDQuery->bindParam(':email', $email);
        $userIDQuery->execute();
        $userID = $userIDQuery->fetchColumn();

        $cartExistQuery = $bdd->prepare("SELECT * FROM `cart` AS c
                                        JOIN product AS p ON p.id = c.product_id
                                        JOIN user AS u ON u.id = c.user_id
                                        WHERE p.farm_id != :farmID AND u.email = :email");
        $cartExistQuery->bindParam(':farmID', $farmID);
        $cartExistQuery->bindParam(':email', $email);
        $cartExistQuery->execute();

        if ($cartExistQuery->rowCount() > 0) {
            $result['success'] = false;
            $result['error'] = "Vous avez déjà un panier avec une autre ferme";
        } else {
            $createCartQuery = $bdd->prepare("INSERT INTO `cart`(`user_id`, `product_id`, `quantity`) VALUES (:userID, :productID, :quantity)");
            $createCartQuery->bindParam(':userID', $userID);
            $createCartQuery->bindParam(':productID', $productID);
            $createCartQuery->bindParam(':quantity', $quantity);
            $createCartQuery->execute();
            $result["success"] = true;
        }
    } catch (Exception $e) {
        $result["success"] = false;
        $result["error"] = "Erreur lors de l'ajout au panier : " . $e->getMessage();
    }
} else {
    $result["success"] = false;
    $result["error"] = "Les paramètres requis sont manquants.";
}

echo json_encode($result, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
?>
