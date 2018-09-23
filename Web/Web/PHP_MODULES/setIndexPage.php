<?php
session_start();
$mainMenu;
$submenu;

    if (isset($_GET['id1']) || isset($_GET['id2'])) {
       
    setSession();
    header('Location: index.php');
    exit;
    }
    elseif ((isset($_SESSION['page']['id1']) || isset($_SESSION['page']['id2']))) {
        savePageId();
        
    }

    function setSession(){
        
        $_SESSION['page'] = array();
        if (isset($_GET['id1'])) {
            $_SESSION['page']['id1'] = $_GET['id1'];
        }
        if (isset($_GET['id2'])) {
            $_SESSION['page']['id2'] = $_GET['id2'];
        }
        session_write_close();
    }
    
    function savePageId()
    {
        global $mainMenu;
        global $submenu;
        if (isset($_SESSION['page']['id1'])) {
            $mainMenu = $_SESSION['page']['id1'];
        }
        if (isset($_SESSION['page']['id2'])) {
            $submenu = $_SESSION['page']['id2'];
        }
        
    }