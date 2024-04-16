<?php
// Headers
//header("Access-Control-Allow-Origin: *');
header('Content-Type: application/json');

include_once '../config/Database.php';

$json = json_decode(file_get_contents('php://input'), true);

// if(isset($_POST['image'])){
//     $base64_image = $_POST['image'];
    

//     $image_data = base64_decode($base64_image);
    

//     $image_path = "chemin/vers/dossier/"; 
//     $image_name = uniqid() . '.jpg'; 
//     $full_image_path = $image_path . $image_name; 

//     if(file_put_contents($full_image_path, $image_data)){

//         echo "Image enregistrée avec succès.";
//     } else {

//         echo "Erreur lors de l'enregistrement de l'image.";
//     }
// } else {
//     echo "Aucune image reçue.";
// }


if (
    isset($json['name']) 
    && isset($json['description'])
    && isset($json['category']) 
    && isset($json['address'])
    && isset($json['zip_code'])
    && isset($json['city'])
    && isset($json['picture'])
) {
    $img = $json['picture'];
    $category = array($json['category']);
    $name = htmlspecialchars($json["name"]);
    $description = htmlspecialchars($json["description"]);
    $address = htmlspecialchars($json["address"]);
    $zip_code = htmlspecialchars($json["zip_code"]);
    $city = htmlspecialchars($json["city"]);
    $further_details = isset($json["further_details"]) ? htmlspecialchars($location["further_details"]) : null;

    $success = "";

    if ($name == "" || $address == "" || $zip_code == "" || $city == "") {
        $result["success"] = false;
        $result["error"] = "Certains champs sont vides";
    } else {
        try {
        
            $createLocation = $bdd->prepare("INSERT INTO `location` (address, zip_code, city) VALUES (?, ?, ?)");
            $createLocation->execute(array($address, $zip_code, $city));
            $location_id = $bdd->lastInsertId();
        
            $createFarm = $bdd->prepare("INSERT INTO `farm` (name, description, rate_number, picture, location_id) VALUES (?, ?, ?, ?, ?)");
            $createFarm->execute(array($name, $description, 0,$img, $location_id));
            $farm_id = $bdd->lastInsertId();
        
                $category = "9";
                $addCategory = $bdd->prepare("INSERT INTO `farm_category` (category_id, farm_id) VALUES (?, ?)");
                $addCategory->execute(array($category , $farm_id));
        
            $result["success"] = true;
        } catch (PDOException $e) {
            $result["success"] = false;
            $result["error"] = "Erreur lors de la création de la ferme : " . $e->getMessage();
        }
    }
} else {
    $result['success'] = false;
    $result['error'] = "Veuillez compléter tous les champs...";
}

echo json_encode($result);

