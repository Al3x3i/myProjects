<?php
session_start();
//header("HTTP/1.0 401 Unauthorized");
require_once 'secure_inc.php';

if ( isset( $_POST["login"] )) {
    login();
}
elseif ( isset( $_GET["action"] ) and $_GET["action"] == "logout" ) {
    logout();
}
function checkContent(){
    if( isset( $_SESSION["username"] ) ) {
        displayWelcome();
    } elseif ( isset( $_POST["login"] ) && !isset( $_SESSION["username"] ) ) {
        displayLoginForm( "Sorry, that username/password could not be found. Please try again." );
    }  else{
        displayLoginForm();
    }   
}
function login() {
    if (!empty($_POST['username']) && !empty($_POST['password'])) {
    $username = clearStr($_POST['username']);
    $password = clearStr($_POST['password']);
    $password = getHash($password);
    checkData($username, $password );
    }
    session_write_close();
}
function clearStr($data){
    global $link;
    return mysqli_real_escape_string($link, trim(strip_tags($data))); 
}
function checkData($username,$password){
    global $link;
    $sql = "SELECT login, password FROM user_account WHERE login='$username'";
    
    if (!$result = mysqli_query($link, $sql)) { 
        return false;
    }
    $id_user = mysqli_fetch_assoc($result);
    
    if (empty($id_user)) {
        return false;
    }
    else{
        $_SESSION['username'] = $username; 
        return true;
    }
}   
function logout() {
    unset( $_GET["action"] );
    unset( $_SESSION["username"] );
    session_write_close();
}
function displayLoginForm( $message="" ) {
    if ( $message ) echo ' <p class="error"> ' . $message . ' </p> ';
    
$htmlFormt = <<<UPLOADFORM
    <form class="fieldset" action="sign_in.php" method="post" >
        <ul>
            <label><li> Username</li></label><input class="input" type="text" name="username"  value="" />
            <label><li> Password</li></label><input class="input" type="password" name="password"  value="" />
            <div class="fieldSubmit">
                <input class="submit" type="submit" name="login" value="Login" />
            </div>
        </ul>
    </form>
    </body>
    </html>
UPLOADFORM;
echo $htmlFormt;
}
function displayWelcome() {
$htmlWelcome = <<<UPLOADWELCOME
    <h3 class = "greeting_message"> Welcome, <strong>  {$_SESSION["username"]} </strong> ! You are
    currently logged in. </h3>
    <h2 class = "greeting_message"> <a href="Sign_in.php?action=logout" > Logout </a> </h2>
UPLOADWELCOME;
    
$visitCounter = <<<VISITCOUNTER
        
        
VISITCOUNTER;
echo $htmlWelcome;
}
