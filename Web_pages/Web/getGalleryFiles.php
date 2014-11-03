<?php
session_start();
//traverseDir("../upload");
$Counter = 4;                               
function traverseDir( $dir ) {
  global $Counter;
    if ( !( $handle = opendir( $dir ) ) ) die( "Cannot open $dir." );
    $files = array();
    while ( $file = readdir( $handle ) ) {
    if ( $file != "." && $file != ".." ) {
    if ( is_dir( $dir . "/" . $file ) ) $file .= "/";
    $files[] = $file;
    }
    }
    sort( $files );

    foreach ( $files as $file ) {
    if ( substr( $file, -1 ) == "/" )

         traverseDir( "$dir/" . substr( $file,0, -1 ) );
    }
    if(substr( $file, -1 ) != "/"){
      
    foreach ( $files as $file ){
      $Counter++;
        //echo "<li>";    
        //echo "<li>$file</li>";
         echo "<a href=\"$dir/$file\">";
         echo "<img src=\"$dir/$file\" class=\"image\".$Counter>";
                 
         //echo "<li><a href=\"$dir/$file\" class=\"lightbox_trigger\"> <img class=\"enterimg\" src=\"$dir\\$file\"></a></li>";
         echo "</a>";
    }


    closedir( $handle );
    }
}

?>
