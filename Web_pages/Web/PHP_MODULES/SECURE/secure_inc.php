<?php
function getHash($password, $salt = '555', $iterationCount ='10'){   // to make the code more secure, while creating account add uniqs $salt and $iteration. Then read first this data and check pw
   for($i = 0; $i <$iterationCount; $i++){
       $password =sha1($password.$salt);
    }   
       return $password;
}
