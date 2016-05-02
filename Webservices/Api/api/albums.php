<?php
include 'classes/Database.php';
$URL       = "rattrapage-2.estiam.com/api/";
$namespace = $URL . '?wsdl';



/*require_once(“config.php”);*/
require_once("lib/nusoap.php");
$server = new soap_server;

$server->configureWSDL("albums", $namespace);

$server->wsdl->addComplexType(
    "Album",
    "complexType",
    "struct",
    "all",
    "",
    array(
        "nom" => array("name" => "nom", "type" => "xsd:string"),
        "last_update_time" => array("name" => "last_update_time", "type" => "xsd:date"),
        "id" => array("name" => "id", "type" => "xsd:integer"),
        "Produit_id" => array("name" => "Produit_id", "type" => "xsd:string"),
        "photos_count" => array("name" => "photos_count", "type" => "xsd:integer"),
    )
);


$server->wsdl->addComplexType(
    "AlbumArray",
    "complexType",
    "array",
    "",
    "SOAP-ENC:Array",
    array(),
    array(
        array("ref"=>"SOAP-ENC:arrayType","wsdl:arrayType"=>"tns:AlbumArray[]")
    ),
    "tns:Album"
);

$server->register("getAlbums",
    array("productId"=>"xsd:string"),
    array("return "=> "tns:AlbumArray"),
    "urn:".$namespace,
    "urn:".$namespace."#getAlbums",
    "rpc",
    "encoded",
    "get albums");

$server->register("getAlbumsByUser",
    array("userId"=>"xsd:string"),
    array("return "=> "tns:AlbumArray"),
    "urn:".$namespace,
    "urn:".$namespace."#getAlbumsByUser",
    "rpc",
    "encoded",
    "get user albums");

$server->register("isUserBindTo",
    array("userId"=>"xsd:string", "albumId"=>"xsd:string"),
    array("return"=>"xsd:boolean"),
    "urn:".$namespace,
    "urn:".$namespace."#isUserBindTo",
    "rpc",
    "encoded",
    "isUserBindTo");

$server->register("bindUserTo",
    array("userId"=>"xsd:string", "albumId"=>"xsd:string"),
    array("return"=>"xsd:boolean"),
    "urn:".$namespace,
    "urn:".$namespace."#bindUserTo",
    "rpc",
    "encoded",
    "bindUserTo");

function bindUserTo($userId, $albumId) {
    $result = array();
    $dbhost = '127.0.0.1';
    $dbuser = 'root';
    $dbpass = 'estiam2';
    $conn = mysql_connect($dbhost, $dbuser, $dbpass);
    if(! $conn ) {
        die('Could not connect: ' . mysql_error());
    }
    $sql = "INSERT INTO Abonnement (Utilisateur_id, Album_id, status) VALUES ($userId, $albumId, 1)";
    mysql_select_db('acmedb');
   if(mysql_query($sql, $conn))
   {
       return true;
   }
    return false;
}

function isUserBindTo($userId, $albumId) {
    $result = array();
    $dbhost = '127.0.0.1';
    $dbuser = 'root';
    $dbpass = 'estiam2';
    $conn = mysql_connect($dbhost, $dbuser, $dbpass);
    if(! $conn ) {
        die('Could not connect: ' . mysql_error());
    }
    $sql = "SELECT * FROM Abonnement WHERE Album_id='$albumId' AND Utilisateur_id='$userId'";
    mysql_select_db('acmedb');
    $retval = mysql_query( $sql, $conn );
    if( mysql_num_rows($retval))
    {
        return true;
    }
    return false;
}

function getAlbums($productId ) {
    $result = array();
    $dbhost = '127.0.0.1';
    $dbuser = 'root';
    $dbpass = 'estiam2';

    $conn = mysql_connect($dbhost, $dbuser, $dbpass);
    $conn2 = mysql_connect($dbhost, $dbuser, $dbpass);
    if(! $conn ) {
        die('Could not connect: ' . mysql_error());
    }

    $sql = "SELECT * FROM Album WHERE Produit_id='$productId'";
    mysql_select_db('acmedb');

    $retval = mysql_query( $sql, $conn );

    if(!$retval ) {
        die('Could not get data: ' . mysql_error());
    }
    while($row = mysql_fetch_array($retval, MYSQL_ASSOC)) {
        $idp=$row['id'];
        $resultCount = mysql_query("SELECT * FROM Album_photo WHERE Album_id='$idp'", $conn2);
        $result[] = array(
            "last_update_time"=>$row['last_update_time'],
            "nom" =>$row['nom'],
            "id" =>$row['id'],
            "Produit_id" =>$row['Produit_id'],
            "photos_count"=>mysql_num_rows($resultCount)
        );

    }
    Database::disconnect();

    return $result;

#return new soap_fault("Server",  "", "Fallthrough error, should have faulted on invalid type above","");


}


function getAlbumsByUser($userId ) {
    $result = array();
    $dbhost = '127.0.0.1';
    $dbuser = 'root';
    $dbpass = 'estiam2';

    $conn = mysql_connect($dbhost, $dbuser, $dbpass);
    $conn2 = mysql_connect($dbhost, $dbuser, $dbpass);
    if(! $conn ) {
        die('Could not connect: ' . mysql_error());
    }

    $sql = "SELECT * FROM Abonnement WHERE Utilisateur_id='$userId'";
    mysql_select_db('acmedb');

    $retval = mysql_query( $sql, $conn );

    if(!$retval ) {
        die('Could not get data: ' . mysql_error());
    }
    while($row = mysql_fetch_array($retval, MYSQL_ASSOC)) {
        $idp=$row['Album_id'];
        $rq2 = "SELECT * FROM Album WHERE id='$idp'";
        $retval2 = mysql_query( $rq2, $conn );
        while($row2 = mysql_fetch_array($retval2, MYSQL_ASSOC)) {
            $idp2=$row2['id'];
            $resultCount = mysql_query("SELECT * FROM Album_photo WHERE Album_id='$idp2'", $conn2);
            $result[] = array(
                "last_update_time" => $row2['last_update_time'],
                "nom" => $row2['nom'],
                "id" => $row2['id'],
                "Produit_id" => $row2['Produit_id'],
                "photos_count" => mysql_num_rows($resultCount)
            );
        }
    }
    Database::disconnect();

    return $result;

#return new soap_fault("Server",  "", "Fallthrough error, should have faulted on invalid type above","");


}

$POST_DATA = isset($GLOBALS['HTTP_RAW_POST_DATA']) ? $GLOBALS['HTTP_RAW_POST_DATA'] : '';
$server->service($POST_DATA);
exit();

?>






