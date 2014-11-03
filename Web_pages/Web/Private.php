<?php 
include ('PHP_MODULES/saveFile.php');
?>

<style>
.error { color: mediumvioletred;
         font-weight:bold;
         text-align:center;
         width:600px;
         margin:50px auto 0;}
</style>

    <h1 class="greeting_message">Uploading a Photo</h1>
    <?php
    checkErrorMessage();
    ?>
    
    <form class="fieldset" action="PHP_MODULES/saveFile.php" method="post" enctype="multipart/form-data">
    
        <ul>
        <input type="hidden" name="MAX_FILE_SIZE" value="2000000" />
        
        <label><li>Your name</li>
        <input class="input" type="text" name="fileName" id="fileName" value="" /></label>
        
        <label ><li>Your photo</li>
        <input class="input" type="file" name="uploadfile" id="photo" value="" /></label>
        <div class="fieldSubmit">
            <input class="submit" type="submit" name="sendPhoto" value="Send Photo" />
        </div>
        </ul>
    </form>
