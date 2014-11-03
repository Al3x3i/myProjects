<?php

    $combineIndex;
    function displayMainmenu(){
      
        $Menu = array(
                "About"=>"", 
		"Gallery"=>"page1", 
		"Transport"=>"page2", 
		"Accommodation"=>"page3"
		);
    mainMenu($Menu);
    }
    
    function mainMenu($menu){
        global $mainMenu;
        
    if ( !empty ($mainMenu)) {
                    setMainMenuArray($menu,$mainMenu);
                }
    else {
        setMainMenuArray($menu);
    }    
}
function setMainMenuArray($menu, $item=''){ //modify menu address link, same set on:focus
        global $combineIndex;
        $combineIndex = 'index.php?id1=';
        foreach ($menu as $link=>$href){
            $temp = $combineIndex;
            $temp .= $href.'&id2=';
            if ($href == $item) {
                echo "<li><a href=\"$temp\" id=\"onlink\">", $link, '</a></li>';
                        }
            else{
                echo "<li><a href=\"$temp\">", $link, '</a></li>';
            } 
        }
        $combineIndex = 'index.php?id1='.$item;
}
    function displaySubmenu(){    
    $subMenuAbout  = array(
                "Home"=>"", 
		"History"=>"page1", 
		"Location"=>"page2", 
		"News"=>"page3", 
		"Contact"=>"page4");
        if (isset($_SESSION['username'])) {
               $subMenuAbout ["Comment"]="page5";

            }
    $subMenuGallery = array(
                "Events"=>""
		);
        if (isset($_SESSION['username'])) {
               $subMenuGallery ["Upload"]="page1"; 
               }           
    $subMenuTransport = array(
                "Timetable"=>"", 
		);
    $subMenuAccommodation = array(
		);
    global $mainMenu;                                 //index id1 = It shows main menu, and shows active menu
                if (empty($mainMenu)) {
                    subMenu($subMenuAbout); 
                }
                elseif ($mainMenu == 'page1') {
                    subMenu($subMenuGallery);
                }
                elseif ($mainMenu == 'page2') {
                     subMenu($subMenuTransport);
                }
                elseif ($mainMenu == 'page3') {
                    subMenu($subMenuAccommodation);
                }
                
}
    function subMenu($menu){
        global $submenu;
 
        setSubMenuArray($menu,$submenu);
}

    function setSubMenuArray($menu, $submenu =''){
        
        global $combineIndex;
            
        foreach ($menu as $link=>$href){
                $temp = $combineIndex;       
                $temp .='&id2='.$href;
                if ($href == $submenu) {
                    
                    echo "<li><a href=\"$temp\" id=\"onlink\">", $link, '</a></li>'; 
                } 
                else{ 
                    echo "<li><a href=\"$temp\">", $link, '</a></li>';
                }
             }
             if (isset($_SESSION['username'])&& ($_SESSION['username'] =='admin')&& ($_SESSION['page']['id1']=="")) {
                   echo '<li>';
                   if ($_SESSION['page']['id2']=='Admin_access' ||$_SESSION['page']['id2']=='Site_map') {
                    echo "<a href='#' id=\"onlink\"> Admin Access </a>"; 
                   }
                   else{
                        echo "<a href='#' > Admin Access </a>"; 
                   }
$admin_access = <<<ADMIN
                        <ul>
                            <li> <a id="test" href="index.php?id1=&id2=Admin_access"> Info</a></li>
                            <li> <a href="index.php?id1=&id2=Site_map"> Sitemap </a></li>
                        </ul>
                        </li>
ADMIN;
                   echo $admin_access;  //only for administrator 
                }  
      }

    function subMenuSwitch(){
        global $mainMenu;
        global $submenu;
        
                if (empty($mainMenu)) {
                    switch($submenu){
                    case '': include'About.html';break; 
                    case 'page1': include'History.html';break;
                    case 'page2': include'Location.html';break;
                    case 'page3': include'News.html';break;
                    case 'page4': include'Contact.html';break;
                    case 'page5': include'Comment.php';break;
                    case 'Admin_access': include'Admin_access.php';break;
                    case 'Site_map': include'Sitemap.php';break;
                    default: include'About.html';	
		}
                
                }
                elseif ($mainMenu == 'page1') {
                    switch($submenu){
                    case '': include'Gallery.php';break;
                    case 'page1': include'Private.php';break;
                    default: include'Gallery.php';
                    }
                }
                elseif ($mainMenu == 'page2') {
                    switch($submenu){
                    case '': include'Transport.html';break; 
                    default: include'Transport.html';
                    }
                }
                elseif ($mainMenu == 'page3') {
                  switch($submenu){
                    case '': include'Accommodation.html';break; 
                    default: include'Accommodation.html';
                    }
                }
    }