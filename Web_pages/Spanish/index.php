<?php

include_once ('PHP/dba_inc.php');
include_once ('PHP/counter.php');
?>

<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
  <head>
    <title>TODO supply a title</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="style/index.css">
    <script type="text/javascript">
      var index =0;
      var photos = new Array 
      (
        "Hola.png","1.jpg","2.jpg","3.jpg","4.jpg","5.jpg",
        "6.jpg","7.jpg","8.jpg","9.jpg","10.jpg",
        "11.jpg","12.jpg","13.jpg","14.jpg","15.jpg",
        "16.jpg","17.jpg","18.jpg","19.jpg","20.jpg",
        "21.jpg","22.jpg","23.jpg","24.jpg","25.jpg",
        "26.jpg","27.jpg","28.jpg"
      )
      function photoClick()
      {
        var shadow = document.getElementById('shadow');
        var photo = document.getElementById('shadowPhoto');
        shadow.style.display= "block";
        photo.style.display = "block";
        photo.style.background = 'url("'+this.src+'") no-repeat center center';
      }
      function hidePhoto()
      {
        var shadow = document.getElementById('shadow');
        var photo = document.getElementById('shadowPhoto');
        shadow.style.display= "";
        photo.style.display = "";
      }
      function nextPhoto()
      {
        index++;
        if(index == photos.length)
                        index = 0;
        var img = document.getElementById("photo");
        var sp_text = document.getElementById("sp_text");
        var text = document.getElementById('t'+index);
        
        sp_text.innerHTML=text.firstChild.nodeValue;
        
        
        img.src ="Images/" + photos[index];
        
      }
      function backPhoto()
      {
        index--;
        if(index < 0)
                        index = photos.length-1;
        var img = document.getElementById("photo");
        img.src = "Images/" + photos[index];
        var sp_text = document.getElementById("sp_text");
        var text = document.getElementById('t'+index);
        
        sp_text.innerHTML=text.firstChild.nodeValue;
      }
      
      window.onload = function()
      {
        var img = document.getElementById("photo");
        img.src = 'Images/Hola.png';
        img.onclick = photoClick;

        var next = document.getElementById("left");
        next.onclick = backPhoto;
        
        var back = document.getElementById("right");
        back.onclick = nextPhoto;
        
        var shadowPhoto = document.getElementById("shadowPhoto");
        shadowPhoto.onclick = hidePhoto;
      }
    </script>
  </head>
  <body>
    <div id="header">
    <h1 class="titulo">Mi dia a partir de fotos</h1>
    <div id="logo_spn"><img src="Images/Learn_Logo.png" width="200"></div>
    </div>
    <div class="content">
        <div id="left"><img src="" width="200"></div>
	<div id="right"><img src="" width="200"></div>
        <div id="center">
          <img id="photo" src="">
          <p id="sp_text">Bienvenidos a mi página</p>
        </div>
    </div>
    <div id="shadow"></div>
    <div id="shadowPhoto"></div>
    <p id="t0">Bienvenidos a mi página</p>
    <p id="t1">Hoy es mi día duro. Es hora de despertar.</p>
    <p id="t2">Ya va en bicicleta.</p>
    <p id="t3">Puedo cambiar muchas carreteras.</p>
    <p id="t4">En algún lugar por ahí Eindhoven.</p>
    <p id="t5">Estoy en Eindhoven y otras bicicletas.</p>
    <p id="t6">muchas bicicletas</p>
    <p id="t7">Más bicicletas.</p>
    <p id="t8">Llegué al centro de educación.</p>
    <p id="t9">Mientras que beber té.</p>
    <p id="t10">Mis amigos.</p>
    <p id="t11">Comienza la programación.</p>
    <p id="t12">Selfie :)))</p>
    <p id="t13">Ista desde la ventana</p>
    <p id="t14">Más</p>
    <p id="t15">Muchos árboles</p>
    <p id="t16">Es hora de ir a trabajar</p>
    <p id="t17">Iglesia</p>
    <p id="t18">Carretera</p>
    <p id="t19">Estoy empezando a trabajar.</p>
    <p id="t20">Una gran cantidad de metales</p>
    <p id="t21">Selfie :SS</p>
    <p id="t22">hmm..</p>
    <p id="t23">Otro tipo de metal</p>
    <p id="t24">.....</p>
    <p id="t25">Que como, y enseño un poco de español</p>
    <p id="t26">Es tarde y es hora de ir a casa.</p>
    <p id="t27">más bicicletas.</p>
    <p id="t28">Era mi día, tuvo un día difícil y que es hora de dormir.</p>
    
    
    
  </body>
</html>
