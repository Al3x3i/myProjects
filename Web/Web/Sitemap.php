<?php
    include_once ('PHP_MODULES/sitemap_inc.php');
   
?>
<form action="PHP_MODULES/sitemap_inc.php" method="post" style="margin:auto;text-align:center;padding-top :40px;">
<textarea id="styled" name="saveSitemap" cols="80" rows="40"><?php displaySitemap();?></textarea>
<br/><input type="submit" name="save_sitemap" value="SAVE SITEMAP">
</form>
