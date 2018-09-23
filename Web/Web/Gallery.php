<?php 
include ('PHP_MODULES/getGalleryFiles.php');
?>
	<link rel="stylesheet" type="text/css" href="js/jquery.ad-gallery.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.ad-gallery.js"></script>
	<script type="text/javascript">
  $(function() {
    var galleries = $('.ad-gallery').adGallery();
  });
  </script>
    <div id="container">
            <div id="gallery" class="ad-gallery">
              <div class="ad-image-wrapper">
              </div>
              <div class="ad-controls">
              </div>
              <div class="ad-nav">
                    <div class="ad-thumbs">
                      <ul class="ad-thumb-list">
                            <li>
                              <a href="images/1.jpg">
                                    <img src="images/thumbs/t1.jpg" class="image0">
                              </a>
                            </li>
                            <li>
                              <a href="images/2.jpg">
                                    <img src="images/thumbs/t2.jpg"  class="image1">
                              </a>
                            </li>
                            <li>
                              <a href="images/3.jpg">
                                    <img src="images/thumbs/t3.jpg"  class="image2">
                              </a>
                            </li>
                            <li>
                              <a href="images/4.jpg">
                                    <img src="images/thumbs/t4.jpg"  class="image3">
                              </a>
                            </li>
                            <li>
                              <a href="images/5.jpg">
                                    <img src="images/thumbs/t5.jpg"  class="image4">
                              </a>
                            </li>
                            <?php 
                                $dirPath="../../db/Upload/";  
                                traverseDir( $dirPath );  
                            ?>
                      </ul>
                    </div>
              </div>
            </div>
    </div>
