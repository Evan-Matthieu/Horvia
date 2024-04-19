<?php
header('content-type: text/html; charset=utf-8');

$bdd = new PDO('mysql:host=localhost;dbname=horvia;charset=utf8;', 'root', '');
$bdd->setAttribute(PDO::MYSQL_ATTR_INIT_COMMAND, "SET NAMES 'utf8'");