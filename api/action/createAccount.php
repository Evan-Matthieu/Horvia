<?php
// Headers
//header("Access-Control-Allow-Origin: *');
header('Content-Type: application/json');

include_once '../config/Database.php';
$json = json_decode(file_get_contents('php://input'), true);


if (isset($json['firstname']) 
    && isset($json['lastname']) 
    && isset($json['password'])
    && isset($json['email'])
    && isset($json['phone'])
    && isset($json['birth'])
    && isset($json['civility'])
) {
    $email = htmlspecialchars($json["email"]);
    $firstname = htmlspecialchars($json["firstname"]);
    $lastname = htmlspecialchars($json["lastname"]);
    $phone = htmlspecialchars($json["phone"]);
    $birth = htmlspecialchars($json["birth"]);
    $civility = htmlspecialchars($json["civility"]);
    $password = htmlspecialchars($json["password"]);
    $creationDate = date("Y-m-d");
    $passwordHashed = password_hash ($password, PASSWORD_DEFAULT);

    //echo $password;
    $success = "";

    if ($email == "" || $password = "") {
        $result["success"] = false;
        $result["error"] = "Le mot de passe et/ou le nom d'utilisateur est vide";
    } else {
        $checkIfEmailExists = $bdd->prepare('SELECT id FROM user WHERE email = ?');
        $checkIfEmailExists->execute(array($email));

        if ($checkIfEmailExists->rowCount() > 0) {
        $result["success"] = false;
        $result["error"] = "Cette adresse mail est déja enregistrée";
        } else {
            try {

            $createAccount = $bdd->prepare("INSERT INTO `user` (lastname, firstname, email, phone, password, date_inscription, birth, civility) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
            $result["password"] = $passwordHashed;
            $createAccount->execute(array($lastname, $firstname, $email, $phone, $passwordHashed, $creationDate, $birth, $civility));
            $result["success"] = true;

            } catch (Exception $e) {

            $result["success"] = false;
            $result["error"] = "erreur lors de la création du compte...";
        
            }
        }
    }
} else {
    $result['success'] = false;
    $result['error'] = "Veuillez compléter tous les champs...";

}

echo json_encode($result);
