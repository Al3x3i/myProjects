<?php
header('Content-type:text/html; charset=utf-8');
        define("DB_HOST", "athena01.fhict.local");
	define("DB_LOGIN", "dbi289476");
	define("DB_PASSWORD", "ThJyXJsXmE");
	define("DB_NAME", "dbi289476");

$link = mysqli_connect(DB_HOST, DB_LOGIN, DB_PASSWORD, DB_NAME) or die (mysqli_connect_error());