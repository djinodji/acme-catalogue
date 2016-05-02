<?php
include 'classes/Database.php';
$URL       = "rattrapage-2.estiam.com/api/";
$namespace = $URL . '?wsdl';



/*require_once(“config.php”);*/
require_once("lib/nusoap.php");
$server = new soap_server;

$server->configureWSDL("images", $namespace);

$server->wsdl->addComplexType(
    "Image",
    "complexType",
    "struct",
    "all",
    "",
    array(
        "nom" => array("name" => "nom", "type" => "xsd:string"),
        "description" => array("name" => "description", "type" => "xsd:string"),
        "id" => array("name" => "id", "type" => "xsd:integer"),
        "order" => array("name" => "order", "type" => "xsd:int"),
        "path" => array("name" => "path", "type" => "xsd:string"),
    )
);


$server->wsdl->addComplexType(
    "ImageArray",
    "complexType",
    "array",
    "",
    "SOAP-ENC:Array",
    array(),
    array(
        array("ref"=>"SOAP-ENC:arrayType","wsdl:arrayType"=>"tns:ImageArray[]")
    ),
    "tns:Image"
);

$server->register("getImages",
    array("albumId"=>"xsd:string"),
    array("return "=> "tns:ImageArray"),
    "urn:".$namespace,
    "urn:".$namespace."#getImages",
    "rpc",
    "encoded",
    "get images");


function getImages($albumId) {
    $result = array();
    $dbhost = '127.0.0.1';
    $dbuser = 'root';
    $dbpass = 'estiam2';

    $conn = mysql_connect($dbhost, $dbuser, $dbpass);
    $conn2 = mysql_connect($dbhost, $dbuser, $dbpass);
    if(! $conn ) {
        die('Could not connect: ' . mysql_error());
    }

    $sql = "SELECT * FROM Album_photo WHERE Album_id='$albumId'";
    mysql_select_db('acmedb');

    $retval = mysql_query( $sql, $conn );

    if(!$retval ) {
        die('Could not get data: ' . mysql_error());
    }
    while($row = mysql_fetch_array($retval, MYSQL_ASSOC)) {
        $idp=$row['Image_id'];
        $order=$row['order'];
        $sql2 = "SELECT * FROM Image WHERE id='$idp'";
        $retval2 = mysql_query( $sql2, $conn2);

        while($row2 = mysql_fetch_array($retval2, MYSQL_ASSOC)) {
        $result[] = array(
            "nom"=>$row2['nom'],
            "description" =>$row2['description'],
            "id" =>$row2['id'],
            "order" =>$row['order'],
            "path"=>$row2['path']
        );}

    }
    Database::disconnect();

    return $result;

#return new soap_fault("Server",  "", "Fallthrough error, should have faulted on invalid type above","");


}


$POST_DATA = isset($GLOBALS['HTTP_RAW_POST_DATA']) ? $GLOBALS['HTTP_RAW_POST_DATA'] : '';
$server->service($POST_DATA);
exit();

?>






