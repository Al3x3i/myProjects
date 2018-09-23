<?php
include_once ('PHP_MODULES/admin_inc.php');

?>

<h3 style="padding-left:40px;padding-top:40px"> Your page visited times: <?php getTotalLines();?> </h3>

<h3 style="padding-left:40px;padding-top:10px;text-align:center"> The log file of visited pages listed below:</h3>
<ul style="list-style: none"><?= getLogContent(); ?></ul>