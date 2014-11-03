<?php
session_start();
require_once 'secure_inc.php';
if ( isset( $_POST["register"] ) ) {
    processForm();
    } 
else {
    displayHeader();
    displayContent(array());
    }
function clearStr($data){
    global $link;
    return mysqli_real_escape_string($link, trim(strip_tags($data))); 
    } 
function handleDatabase() {
    global $link;
    $login = clearStr($_POST['login']);
    $password = clearStr($_POST['password']);
    $password = getHash($password);
    $fname = clearStr($_POST['fname']);
    $lname = clearStr($_POST['lname']);
    $bday = $_POST['bday'];
    $phone = clearStr($_POST['phone']);
    $email = clearStr($_POST['email']);
    $registered = time();
    if (empty($phone)) {
          $sql = "INSERT INTO user_account (login, password, fname, lname, dob, email, registered) VALUES ('$login','$password','$fname','$lname','$bday', '$email','$registered')";    
        }
    else {
          $sql = "INSERT INTO user_account (login, password, fname, lname, dob, phone, email, registered) VALUES ('$login','$password','$fname','$lname','$bday', '$phone', '$email','$registered')";  
        }
        mysqli_query($link, $sql) or die(mysqli_error($link));
        mysqli_close($link);
    }
function validateField( $fieldName, $missingFields ) {
    if ( in_array( $fieldName, $missingFields ) ) {
        echo ' class="error"';
        }
    }
function setValue( $fieldName ) {
    if ( isset( $_POST[$fieldName] ) ) {
        echo $_POST[$fieldName];
        }
    }
function setChecked( $fieldName, $fieldValue ) {
    if ( isset( $_POST[$fieldName] ) and $_POST[$fieldName] == $fieldValue ) {
        echo ' checked="checked"';
        }
    }
function setSelected( $fieldName, $fieldValue ) {
    if ( isset( $_POST[$fieldName] ) and $_POST[$fieldName] == $fieldValue ) {
        echo ' selected="selected"';
        }
    }
function is_email($email){
    return filter_var($email, FILTER_VALIDATE_EMAIL);
         //$test= preg_match("/^([a-zA-Z0-9])+([\.a-zA-Z0-9_-])*@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-]+)*\.([a-zA-Z]{2,6})$/", $email);
    }
function processForm() {
    $requiredFields = array("login","fname", "lname", "password","password2","bday","gender","email");
    $missingFields = array();
    if ($_POST["password"] != $_POST["password2"]  ) {
        $missingFields[] = "password";
}
    foreach ( $requiredFields as $requiredField ) {
    if ($requiredField == 'email') {   //Use validator to email.
        if(!is_email($_POST[$requiredField] )){
           $missingFields[] = $requiredField; 
        }
    }
    if ( !isset( $_POST[$requiredField] ) or !$_POST[$requiredField] ) {
        $missingFields[] = $requiredField;
        }
    }
        if ( $missingFields ) {
        displayHeader();   
        displayContent( $missingFields );

    } else {                  //if everything is correct show all and send email
        displayHeader();
        handleDatabase();     //save in database
        confrirmRegistration($_POST["email"]); // send email
        displayThanks();
        }
    }
function confrirmRegistration($email){
    $to = $email;
    $subject = 'Registration on website';
    $message = "Hello! Thank you for registraton on website \"www.piirisaare.ee\". To confirm your registration on website piirisaare, please go to the link below:\n";
    $message.= "https://portal.fhict.nl/\n";
    $message.= 'If you do not want to confirm your registration please do not click on the link!';
    $from = "lehas@hot.ee";
    $headers = "From:" . $from;
    $temp = mail($to,$subject,$message,$headers);
    }
function displayThanks() {
$htmlContent = <<<CONTENT
        <h1 class="greeting_message">Thank You</h1>
        <p class="greeting_message">Thank you, your application has been received. Check your e-mail and verify your account</p>
CONTENT;
    echo $htmlContent;
}