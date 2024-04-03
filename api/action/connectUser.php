<?php

header('Content-Type: application/json');
include_once '../config/Database.php';

$json = json_decode(file_get_contents('php://input'), true);

if (isset($json['email']) && isset($json['password'])) {
    $email = htmlspecialchars($json['email']);
    $password = htmlspecialchars($json['password']);

    $getUser = $bdd->prepare("SELECT * FROM user WHERE email = ?");
    $getUser->execute(array($email));

    if ($getUser->rowCount() > 0) {
        $user = $getUser->fetch();

        if (password_verify($password, $user['password'])) {
            $result["success"] = true;
        } else {
            $result["success"] = false;
            $result["error"] = "Mot de passe incorrect";
        }
    } else {
        $result["success"] = false;
        $result["error"] = "Ce nom d'utilisateur n'existe pas ";
    }
} else {
    $result["success"] = false;
    $result["error"] = "Champs non remplis";
}

echo json_encode($result);
?>
