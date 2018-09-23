<?php
    //include_once ('PHP_MODULES/log_inc.php');
    include_once ('PHP_MODULES/dba_inc.php');
    include_once ('PHP_MODULES/SECURE/sign_in_handler.php');
?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1251" />
<title>Piiriisaar webpage</title>
<link href="index.css" type="text/css" rel="stylesheet" />
<style>
.error { color: mediumvioletred;
         font-weight:bold;
         text-align:center;
         width:600px;
         margin:50px auto 0;}
</style>
</head>
<body>
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
			<div class="Logo">
			</div>
			<div class="navbar">
				<div class="holder">
					<ul>
						<li><a href="index.php?id1=&id2=" >About</a></li>
						<li><a href="index.php?id1=page1&id2=" >Gallery</a></li>
						<li><a href="index.php?id1=page2&id2=">Transport</a></li>
						<li><a href="index.php?id1=page3&id2=">Accommodation</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="Middle">
		<div class="Submenu">   
		</div>
	<div class="Content">
            <h1 class = "greeting_message"> A login/logout system </h1>
            <?php 
                checkContent(); 
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