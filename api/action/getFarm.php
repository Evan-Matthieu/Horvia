<?php
// Headers
//header("Access-Control-Allow-Origin: *');
header('Content-Type: application/json');

include_once '../config/Database.php'; // Assure-toi que le chemin vers Database.php est correct.

$json = json_decode(file_get_contents('php://input'), true);


if (isset($json['farm']) && $json['farm'] == 'getFarm') 
 {
   
    $success = "";
        try {
            

            $getFarm = $bdd->prepare("SELECT f.id AS farmId, f.name AS Name, f.description AS Description, f.rate AS Rate, f.rate_number AS Rate_number, f.picture AS Picture l.address AS Address, l.zip_code AS ZipCode, l.city AS City, c.name AS Category FROM farm AS f
            JOIN location AS l ON l.id = f.location_id
            JOIN farm_category AS fc ON fc.farm_id = f.id
            JOIN category AS c ON c.id = fc.category_id");
            $getFarm->execute();

            $result['entity'] = $getFarm->fetchAll();


            $result["success"] = true;
        } catch (Exception $e) {
            $result["success"] = false;
            $result["error"] = "Erreur lors de la création de la ferme...";
        }
    }

echo json_encode($result);