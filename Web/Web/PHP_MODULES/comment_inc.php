<?php
session_start();


function displayCommentbox(){
    $htmlContent = <<<CONTENT
<form style="margin:auto;text-align:center;padding-top :25px;" action="{$_SERVER['PHP_SELF']}" method="post">
    <h3 ">Alexei, you are welcome to write comments<br> on my web page</h3> 
    Your message:<br />
    <textarea name="msg" cols="50" rows="10" maxlength="200"   
></textarea><br />
    <br />
    <input type="submit" name="addComment" value="Submit!" />
    </form>
CONTENT;
echo $htmlContent;
}
function showContent($link){          //WHY I HAVE ERROR IF I JUST USE GLOBAL $LINK
    $sql = "SELECT * FROM msgs ORDER BY id DESC";    
    $result = mysqli_query($link, $sql);
    $rows = mysqli_num_rows($result);
    print "<p style=\"margin-left:10px\">Total ammount of messages: $rows</p>";    
    while($message = mysqli_fetch_assoc($result)){
            $id = $message['ID'];
            $name = $message['user'];
            $date = $message['date'];
            $msg = nl2br($message['comment']);
    echo <<<HTML
	<hr>
	<p style="width:600px;display:inline-block;margin-left:10px" ><b>$date $name</b><br />$msg</p>
	<p style="display:inline-block"><a href="{$_SERVER['PHP_SELF']}?del=$id">Delete</a></p> 
HTML;
        }
    }
function addComment($link){
    $user = $_SESSION['username'];
    
    $comment = stripslashes(trim(htmlspecialchars($_POST['msg'],ENT_QUOTES)));
    $sql = "INSERT INTO msgs (user, comment) VALUES ('$user','$comment')";
    mysqli_query($link, $sql);
    // reload page
    header('Location: ' . $_SERVER['PHP_SELF']);
    exit;    

}
function deleteComment($link){
    // filter getinput
	$del = $_GET['del'] * 1;
	// create sql querry
	$sql = "DELETE FROM msgs WHERE id=$del";
	mysqli_query($link, $sql);
	// reload page
	header('Location: ' . $_SERVER['PHP_SELF']);
	exit;
    
}




