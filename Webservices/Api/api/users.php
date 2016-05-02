<?php
include 'classes/Database.php';
$URL       = "rattrapage-2.estiam.com/api/";
$namespace = $URL . '?wsdl';



/*require_once(“config.php”);*/
require_once("lib/nusoap.php");
$server = new soap_server;

$server->configureWSDL("users", $namespace);

/*$server->configureWSDL("users" , "urn:".$_SERVER['SCRIPT_URI']);*/
$server->wsdl->addComplexType(
"Contact",
"complexType",
"struct",
"all",
"",
array(
"username" => array("name" => "username", "type" => "xsd:string"),
"email" => array("name" => "email", "type" => "xsd:string"),
    "id" => array("name" => "id", "type" => "xsd:integer"),
    "photo" => array("name" => "photo", "type" => "xsd:string"),
)
);


$server->wsdl->addComplexType(
"ContactArray",
"complexType",
"array",
 "",
"SOAP-ENC:Array",
array(),
array(
array("ref"=>"SOAP-ENC:arrayType","wsdl:arrayType"=>"tns:ContactArray[]")
),
"tns:Contact"
);

$server->register("getUsers",
array("email" => "xsd:string", "num" => "xsd:int", "token" => "xsd:string"),
array("return "=> "tns:ContactArray"),
"urn:".$namespace,
"urn:".$namespace."#getUsers",
"rpc",
"encoded",
"get users"); 




$server->register("connectUser",
    array("email" => "xsd:string", "password" => "xsd:string"),
    array("return" => "tns:ContactArray"),
    "urn:".$namespace,
    "urn:".$namespace."#connectUser",
    "rpc",
    "encoded",
    "connect user"
); 
/*
* This function takes an $email address and returns an array of email addresses
* that are the given email address’s recent contacts from their address book.
*
* @param string $email (the email address of the user)
* @param string $token (a password that is used for authentication for use of this function, it is NOT the email users password.)
*/

function connectUser($email, $password) {


$result = array();
 $dbhost = '127.0.0.1';
   $dbuser = 'root';
   $dbpass = 'estiam2';


	$conn = mysql_connect($dbhost, $dbuser, $dbpass);

   if(! $conn ) {
      die('Could not connect: ' . mysql_error());
   }

   $sql = "SELECT * FROM Utilisateur WHERE (email='$email' OR '$email'=username) AND password='$password'";
   mysql_select_db('acmedb');

	$retval = mysql_query( $sql, $conn );

   if(!$retval ) {
      die('Could not get data: ' . mysql_error());
   }
    while($row = mysql_fetch_array($retval, MYSQL_ASSOC)) {

        $result[] = array(
            "username"=>$row['username'],
            "email" =>$row['email'],
            "id" =>$row['id'],
            "photo" =>$row['photo']
        );
        return $result;
        Database::disconnect();
       
    }
return null;
    
}



function getUsers( $email, $num, $token ) {
$result = array();
 $dbhost = '127.0.0.1';
   $dbuser = 'root';
   $dbpass = 'estiam2';

   $conn = mysql_connect($dbhost, $dbuser, $dbpass);

   if(! $conn ) {
      die('Could not connect: ' . mysql_error());
   }
 
   $sql = 'SELECT * FROM Utilisateur';
   mysql_select_db('acmedb');

  $retval = mysql_query( $sql, $conn );
   
   if(!$retval ) {
      die('Could not get data: ' . mysql_error());
   }
    while($row = mysql_fetch_array($retval, MYSQL_ASSOC)) {

       $result[] = array(
           "username"=>$row['username'],
           "email" =>$row['email'],
           "id" =>$row['id'],
           "photo" =>$row['photo']
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
