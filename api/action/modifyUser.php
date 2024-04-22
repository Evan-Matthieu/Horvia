<?php
// Headers
//header("Access-Control-Allow-Origin: *');
header('Content-Type: application/json');

include_once '../config/Database.php';


include_once '../auth/verify_token.php';


$json = json_decode(file_get_contents('php://input'), true);


if (
    isset($json['firstname'])
    && isset($json['lastname'])
    && isset($json['email'])
    && isset($json['phone'])
    && isset($json['birth'])
    && isset($json['civility'])
) {
    $JSONemail = htmlspecialchars($json["email"]);
    $firstname = htmlspecialchars($json["firstname"]);
    $lastname = htmlspecialchars($json["lastname"]);
    $phone = htmlspecialchars($json["phone"]);
    $birth = htmlspecialchars($json["birth"]);
    $civility = htmlspecialchars($json["civility"]);
    $passwordHashed = password_hash($password, PASSWORD_DEFAULT);
    $success = "";


    if ($email == $JSONemail) {
        try {

            $query = "UPDATE user 
            SET `lastname`='$lastname',
            `firstname`='$firstname',
            `email`='$JSONemail',
            `phone`='$phone',
            `birth_date`='$birth',
            `civility`='$civility'
            WHERE email = '$email'";
            
            $modifyUser = $bdd->prepare($query);
            $modifyUser->execute();
            $result["success"] = true;
            $result['entity'] = generateJWT($JSONemail);
        } catch (Exception $e) {
            $result["success"] = false;
            $result["error"] = "erreur lors de la modification du compte...";
        }
    } else {
        $checkIfEmailExists = $bdd->prepare('SELECT id FROM user WHERE email = ?');
        $checkIfEmailExists->execute(array($JSONemail));

        if ($checkIfEmailExists->rowCount() > 0) {
            $result["success"] = false;
            $result["error"] = "Cette adresse mail est déja enregistrée";
        } else {
            try {
                $query = "UPDATE user 
                        SET `lastname`='$lastname',
                        `firstname`='$firstname',
                        `email`='$JSONemail',
                        `phone`='$phone',
                        `birth_date`='$birth',
                        `civility`='$civility'
                        WHERE email = '$email'";

                $modifyUser = $bdd->prepare($query);
                $modifyUser->execute();

                $result["success"] = true;
                $result['entity'] = generateJWT($JSONemail);

                var_dump($entity);
            } catch (Exception $e) {
                $result["success"] = false;
                $result["error"] = "erreur lors de la modification du compte...";
            }
        }
    }
}
else{
    $result["success"] = false;
    $result["error"] = "Tout les champs ne sont pas définis";
}
echo json_encode($result);
