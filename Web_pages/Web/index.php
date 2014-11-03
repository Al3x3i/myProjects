<?php

include_once ('PHP_MODULES/log_inc.php'); // visiting counter
include ('PHP_MODULES/setIndexPage.php');

include_once ('PHP_MODULES/cookie.php');
include_once ('PHP_MODULES/menuContent_inc.php');
?>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Piirissare webpage</title>
<link href="index.css" type="text/css" rel="stylesheet" />
<style>
.error { color: white; padding: 0.2em; }

</style>


</head>
<body>
<link href="index.css" type="text/css" rel="stylesheet" />
<div class ="Bodyplus">
	<div class="Top">
		<div class="Navigation">
                        <div class ="HeadButtons">
                        <a href='sign_Up.php'>Sign Up</a>
                        <?php if (empty($_SESSION["username"])) {
                                   echo "<a href='sign_In.php'>Sign In</a>";   
                                   }else{
                                       echo "<a href='sign_In.php'>Logout</a>";   
                                   }
                            ?>
                    </div>
			<div class="navbar">
				<div class="holder">
					<ul>
                                            <?php displayMainmenu(); ?>
					</ul>
				</div>
			</div>
		</div>
	</div>
		
	<div class="Middle">
		<div class="Submenu">
                    
                    <ul>
                        <?php displaySubmenu() ?>
                        
                    </ul>

		</div>
            <div class="Content">

                    <?php 
                    subMenuSwitch(); // show content of selected page
                    ?>
            </div>
	</div>	
	<div class="Footer">
		<div class="Contact">aleksei.shashin@gmail.com &copy;me
		</div>
	</div>
</div>
</body>
</html>
