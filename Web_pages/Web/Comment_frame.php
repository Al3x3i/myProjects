<?php
include_once ('PHP_MODULES/log_inc.php');
include_once ('PHP_MODULES/dba_inc.php');
include_once ('PHP_MODULES/comment_inc.php');

if (isset($_SESSION['username'])) {
    if (isset($_POST['addComment'])&& !empty($_POST['msg'])) {
        addComment($link);
}
    elseif(isset($_GET['del']) && is_numeric($_GET['del'])){
	deleteComment($link);
    }
    else{
        displayCommentbox();
        showContent($link);
    }
}
?>