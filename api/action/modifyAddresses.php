<?php
// Headers
//header("Access-Control-Allow-Origin: *');
header('Content-Type: application/json');

include_once '../config/Database.php';


include_once '../auth/verify_token.php';


$json = json_decode(file_get_contents('php://input'), true);


if (
    isset($json['address'])
    && isset($json['city'])
    && isset($json['zipCode'])
) {
    $address = htmlspecialchars($json["address"]);
    $city = htmlspecialchars($json["city"]);
    $zipCode = htmlspecialchars($json["zipCode"]);
    $furtherDetails = isset($json["further_details"]) ? htmlspecialchars($json["further_details"]) : null;


    $success = "";

    $verifyUserAddress = $bdd->prepare("SELECT location_id FROM user WHERE email = '$email'");
    $verifyUserAddress->execute();
    $locationId = $verifyUserAddress->fetch(PDO::FETCH_ASSOC)["location_id"];

    if($locationId == null){
        try{
            $createAddress = $bdd->prepare("INSERT INTO 'location' (address, zip_code, city, further_details) VALUES (?, ?, ?, ?);");
            $createAddress->execute(array($address, $city, $zipCode, $furtherDetails));
            
            
            
            $result["success"] = true;
        }
        catch (Exception $e) {
            $result["success"] = false;
            $result["error"] = "Erreur lors de la création de l'address";
        }
    }
    else{
        if ($furtherDetails != null) {

            try {
    
                $query = "UPDATE `location` AS l
                JOIN `user` AS u ON u.location_id = l.id
                SET l.`address`= '4 rue',
                    l.`zip_code`= '91150',
                    l.`city`= 'Pussay',    
                    l.`further_details` = 'C'
                WHERE u.email = '$email'";
                
                $modifyAddress = $bdd->prepare($query);
                $modifyAddress->execute();
    
                $result["success"] = true;
            } catch (Exception $e) {
                $result["success"] = false;
                $result["error"] = "Erreur lors de la modification de l'addresse...";
            }
        } else {
            try {
    
                $query = "UPDATE `location` AS l 
                SET `address`='$address',
                `zip_code`='$zipCode',
                `city`='$city',
                JOIN user AS u ON u.location_id = l.id
                WHERE u.email = '$email'";
                
                $modifyAddress = $bdd->prepare($query);
                $modifyAddress->execute();
    
                $result["success"] = true;
            } catch (Exception $e) {
                $result["success"] = false;
                $result["error"] = "Erreur lors de la modification de l'addresse...";
            }
        }
    }
}
else{
    $result["success"] = false;
    $result["error"] = "Tout les champs ne sont pas définis";
    echo json_encode($result);
}
//echo json_encode($result);
