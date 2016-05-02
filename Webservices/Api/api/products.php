<?php
include 'classes/Database.php';
$URL       = "rattrapage-2.estiam.com/api/";
$namespace = $URL . '?wsdl';



/*require_once(“config.php”);*/
require_once("lib/nusoap.php");
$server = new soap_server;

$server->configureWSDL("products", $namespace);


/*$server->configureWSDL("users" , "urn:".$_SERVER['SCRIPT_URI']);*/
$server->wsdl->addComplexType(
    "Product",
    "complexType",
    "struct",
    "all",
    "",
    array(
        "nom" => array("name" => "username", "type" => "xsd:string"),
        "description" => array("name" => "description", "type" => "xsd:string"),
        "id" => array("name" => "id", "type" => "xsd:integer"),
        "statut" => array("name" => "statut", "type" => "xsd:string"),
        "albums_count" => array("name" => "albums_count", "type" => "xsd:integer"),
    )
);


$server->wsdl->addComplexType(
    "ProductArray",
    "complexType",
    "array",
    "",
    "SOAP-ENC:Array",
    array(),
    array(
        array("ref"=>"SOAP-ENC:arrayType","wsdl:arrayType"=>"tns:ProductArray[]")
    ),
    "tns:Product"
);

$server->register("getProducts",
    array("email" => "xsd:string", "password" => "xsd:string", "token" => "xsd:string"),
    array("return "=> "tns:ProductArray"),
    "urn:".$namespace,
    "urn:".$namespace."#getProducts",
    "rpc",
    "encoded",
    "get albums");


function getProducts( $email, $password, $token ) {
    $result = array();
    $dbhost = '127.0.0.1';
    $dbuser = 'root';
    $dbpass = 'estiam2';

    $conn = mysql_connect($dbhost, $dbuser, $dbpass);
    $conn2 = mysql_connect($dbhost, $dbuser, $dbpass);
    if(! $conn ) {
        die('Could not connect: ' . mysql_error());
    }

    $sql = 'SELECT * FROM Produit';
    mysql_select_db('acmedb');

    $retval = mysql_query( $sql, $conn );

    if(!$retval ) {
        die('Could not get data: ' . mysql_error());
    }

    while($row = mysql_fetch_array($retval, MYSQL_ASSOC)) {
        $idp=$row['id'];
        $resultCount = mysql_query("SELECT * FROM Album WHERE Produit_id='$idp'", $conn2);

        $result[] = array(
            "nom"=>$row['nom'],
            "statut" =>$row['statut'],
            "id" =>$row['id'],
            "description" =>$row['description'],
            "albums_count"=>mysql_num_rows($resultCount)
        );

    }
    Database::disconnect();

    return $result;

#return new soap_fault("Server",  "", "Fallthrough error, should have faulted on invalid type above","");


}

$POST_DATA = isset($GLOBALS['HTTP_RAW_POST_DATA']) ? $GLOBALS['HTTP_RAW_POST_DATA'] : '';
$server->service($POST_DATA);
exit();

?>


