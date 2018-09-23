<?php
    //include_once ('PHP_MODULES/log_inc.php');
    include ('PHP_MODULES/dba_inc.php');
    include ('PHP_MODULES/SECURE/sign_up_handler.php');
    //confrirmRegistration("aleksei.shashin@gmail.com");
    function displayHeader() {
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
<?php 
        }
        function displayContent($missingFields)
        {
        ?>
            <h1 class="greeting_message">Membership Form</h2>
            
                <?php if ( $missingFields ) { ?>
                <p class="error">There were some problems with the form you submitted.
                Please complete the fields highlighted below.</p>
                <?php } else { ?>
                <p class="greeting_message">Thanks for registration on Web site. To register, please
                fill in your details below and click Send Details.</p>
                <?php } ?>
            
            <form class="fieldset" action="Sign_up.php" method="post">
                <ul>
                <label <?php validateField( "login",$missingFields ) ?>><li>Choose a login</li> <input class="input" type="text" name="login" value="<?php setValue( "login" ) ?>"/></label>   
                <label <?php validateField( "password",$missingFields ) ?>><li>Choose a password</li> <input class="input" type="password" name="password"  value=""/></label>
                <label <?php validateField( "password2",$missingFields ) ?>><li>Retype password</li><input class="input" type="password" name="password2"  value=""/></label>
                <label <?php validateField( "fname",$missingFields ) ?>><li>First Name </li><input class="input" type="text" name="fname" value="<?php setValue( "fname" ) ?>" /></label>
                <label <?php validateField( "lname",$missingFields ) ?>><li>Last Name</li><input class="input" type="text" name="lname" value="<?php setValue( "lname" ) ?>"/></label>
                <label <?php validateField( "bday",$missingFields ) ?>><li>Date of Birth</li><input class="input" type="date" name="bday" value="<?php setValue( "bday" ) ?>"></label>			
                <label <?php validateField( "gender",$missingFields ) ?>><li>Gender</li></label>
                <label class="gender"><input type="radio" name="gender" value="M"<?php setChecked( "gender", "M" )?> /> Male</label>
                <label class="gender"><input type="radio" name="gender" value="F"<?php setChecked( "gender", "F" )?> /> Female</label>
                <label><li>Phone</li> <input class="input" name="phone" type="text" /></label>
                <label <?php validateField( "email",$missingFields ) ?>><li>Email</li><input class="input" name="email" type="email" value="<?php setValue( "email" ) ?>"/></label>
                <div class="fieldSubmit">
                <input class="submit" type="submit" name="register"  value="Send Details" />
                </div>
                </ul>
            </form>
<?php } ?>
</div>
	</div>
	<div class="Footer">
		<div class="Contact">aleksei.shashin@gmail.com &copy;me
		</div>
	</div>
</div>
</body>
</html>