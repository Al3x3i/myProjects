<?php


if (isset($_SESSION['sitemapUpdated'])) {
$sitemapUpdated = <<<SITEMAPUPDATED
        <h3 class = "greeting_message"> Your sitemat is updated! </h3>
SITEMAPUPDATED;
echo $sitemapUpdated;

 unset($_SESSION['sitemapUpdated']);
   }
 elseif(!isset($_SESSION['sitemapUpdated'])){
     if(file_exists("../../db/Sitemap.xml")){
        $date = date("Y-m-d",filemtime("../../db/Sitemap.xml")); 
     }
    $sitemapUpdated = <<<SITEMALASTTIMEPUPDATED
    <h3 class = "greeting_message"> Your sitemap was last time updated in: {$date}  ! </h3>
SITEMALASTTIMEPUPDATED;
echo $sitemapUpdated;  
 }  
if (isset($_POST["save_sitemap"])) {
    
    $_SESSION['sitemapUpdated']='true';
    session_write_close();
    saveSitemap();
    
   

    header('Location: ../index.php?id1=&id2=Site_map');
    exit;
}

function displaySitemap(){
    $Sitemap="";
    if(file_exists("../../db/Sitemap.xml")){
    $items = file("../../db/Sitemap.xml");
        if(is_array($items)){
            foreach($items as $line){
                $Sitemap.=$line;
            }
            echo $Sitemap;
        }
    }
}

function saveSitemap(){
    $sitemap = stripslashes(trim($_POST['saveSitemap']));
    if ( !( $handle = fopen("../../../db/Sitemap.xml", "w" ) ) ) {
      
        die( "Cannot create the counter file." );
        } else {
            $temp =fwrite( $handle, $sitemap );
            fclose( $handle );
        }
   }