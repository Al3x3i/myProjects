<?php   //this file saves data if I open some index.page
$dt = time();
$page = $_SERVER['REQUEST_URI'];
$page = pathinfo($page, PATHINFO_BASENAME);

$path = "$dt|$page\n";

if ($page !="index.php") {
    if ( !( $handle = fopen("../../db/path.log", "a" ) ) ) {
	
        die( "Cannot create the counter file." );
        } else {
            $temp =fwrite( $handle, $path );
            fclose( $handle );
        }
}
?>