<?php
$visitCounter=0;
	if(isset($_COOKIE['visitCounter']))
		{
			$visitCounter=$_COOKIE['visitCounter'];
		}
	$visitCounter++;
	$lastVisit='';
	if(isset($_COOKIE['lastVisit']))
		{
			@$lastVisit= date('d-m-Y H:i:s',$_COOKIE['lastVisit']);
		}
                if ( $lastVisit!='') {   //I add cockies olny by one time per day
                    if (date('d-m-Y', $_COOKIE['lastVisit']) != date('d-m-Y'))  // I add visit counter only one time per day.  
                        {
                        setcookie('visitCounter',$visitCounter,0x7FFFFFFF);  //this means that cookie for hole life, until undelete
                        setcookie('lastVisit',time());
                        }
                }
                else{
                        setcookie('visitCounter',$visitCounter,0x7FFFFFFF);
                        setcookie('lastVisit',time());    
                        }
?>