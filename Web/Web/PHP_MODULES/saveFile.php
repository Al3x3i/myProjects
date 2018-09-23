<?php

if ( isset( $_POST["sendPhoto"] )) {
        session_start();
        processForm();
    } 
    function processForm() {
        
        global $errorMessage;
        
            if ( $_FILES['uploadfile']["error"] == 0)
                {
                $allowedExts = array("gif", "jpeg", "jpg", "png");
                $temp = explode(".", $_FILES["uploadfile"]["name"]);
                $extension = end($temp);
        
                    if ((($_FILES["uploadfile"]["type"] == "image/gif")
                    || ($_FILES["uploadfile"]["type"] == "image/jpeg")
                    || ($_FILES["uploadfile"]["type"] == "image/jpg")
                    || ($_FILES["uploadfile"]["type"] == "image/pjpeg")
                    || ($_FILES["uploadfile"]["type"] == "image/x-png")
                    || ($_FILES["uploadfile"]["type"] == "image/png"))
                    && ($_FILES["uploadfile"]["size"] < 200000)
                    && in_array($extension, $allowedExts))
                    {
                        if (file_exists("../../../db/Upload/" . $_FILES["uploadfile"]["name"]))
                        {
                            //$errorMessage = $_FILES["uploadfile"]["name"] . " already exists";
                            $_SESSION['uploadfile']['error'] = 1;
                        }
                        else
                        {
                            $file =$_FILES["uploadfile"]["tmp_name"];
                            //$conn_id = ftp_connect("athena.fhict.nl");
                            //$login_result = ftp_login($conn_id, "i289476", "Samelin1"); 
                            //$upload = ftp_put($conn_id, "test.txt", $file); 
                            // move temporary file to upload dir and restore original name.
                            //$temp = move_uploaded_file($_FILES["uploadfile"]["tmp_name"],
                            //"ftp://i289476@athena.fhict.nl/" . $_FILES["uploadfile"]["name"]);
                            
                            $temp = move_uploaded_file($_FILES["uploadfile"]["tmp_name"],"../../../db/Upload/".$_FILES["uploadfile"]["name"]);
                            
                            //$temp = move_uploaded_file($_FILES["uploadfile"]["tmp_name"],
                            //"ftp://i289476:Samelin1@athena.fhict.nl/ITA2/Website/upload/" . $_FILES["uploadfile"]["name"]);
                           
                           if ($temp) {
                                $_SESSION['uploadfile']['filename'] = $_FILES['uploadfile']['name'];
                           }
                           else{
                               $_SESSION['uploadfile']['error'] = 5;
                           }
                        }                  
                    }
                    else{
                        //$errorMessage = "gif, jpeg, pjpeg, jpg, x-png, png Photos only, thanks!";
                        $_SESSION['uploadfile']['error'] = 2;
                    }
                }
                elseif($_FILES["uploadfile"]['error']==4) {
                     //$errorMessage = 'Please choose a file.';
                     $_SESSION['uploadfile']['error'] = 3;
                }
                elseif($_FILES["uploadfile"]['error']==2){
                    //$errorMessage = 'Sorry file size cannot be bigger than 2mb';
                    $_SESSION['uploadfile']['error'] = 4;
                }
                else {
                    //$errorMessage =  'Some unexpected error occured!';
                    $_SESSION['uploadfile']['error'] = 5;
                }
       
       header('Location: ../index.php?id1=page1&id2=page1');
       exit;          
    }
    function handleFileSaveException($error){
        
        if ($error==1) {
            return 'File name already exists';
        }
        elseif($error==2){
            return 'gif, jpeg, pjpeg, jpg, x-png, png Photos only, thanks!';
        }
        elseif($error==3){
            return 'Please choose a file!';
        }
        elseif($error==4){
            return 'Sorry file size cannot be bigger than 2mb';
        }
        elseif($error==5){
            return 'Some unexpected error!';
        }
        
    }
    
    function displayThanks() {
    $htmlContent= <<<UPLOADFORM
    
    <p class="greeting_message">Thank you for uploading your photo. Now you can find your photo in Gallery : 
UPLOADFORM;

    echo $htmlContent.$_SESSION['uploadfile']['filename'].'</p>';
    }
    
    function deleteSession(){
        unset($_SESSION['uploadfile']['error']);
        session_write_close();
    }
    function checkErrorMessage(){
       if(isset($_SESSION['uploadfile']['error'])){
        echo "<p class=\"error\">". handleFileSaveException($_SESSION['uploadfile']['error']) ."</p>";
         unset($_SESSION['uploadfile']['error']);
    }
    elseif( isset( $_SESSION['uploadfile']['filename'])){
        displayThanks();
        deleteSession();
    }
    else{
        echo "<p class=\"greeting_message\">Please enter your name and choose a photo to upload, then click Send Photo.</p>";          
    } 
    }
?>