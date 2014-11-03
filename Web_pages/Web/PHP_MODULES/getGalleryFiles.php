<?php
session_start();
//traverseDir("../upload");
                             
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
    $Counter = 4;    
    foreach ( $files as $file ){
      $Counter++;
         echo "<li>\n";
         echo "<a href=\"$dir/$file\">\n";
         echo "<img src=\"$dir/$file\" class=\"image$Counter\" height=\"60\" width=\"90\">\n";
         echo "</a>\n";
         echo "</li>\n";
    }


    closedir( $handle );
    }
}

?>
