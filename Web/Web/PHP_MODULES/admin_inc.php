<?php

function getTotalLines(){
    $counter=0;
   if(file_exists("../../db/path.log")){
     $counter = file("../../db/path.log");
     $counter = count($counter);
    echo $counter;
   }
}
function getLogContent(){
    $data ="";
    $counter =1;
    if(file_exists("../../db/path.log")){
    $log = file("../../db/path.log");
        if(is_array($log)){
            foreach($log as $line){
                list($dt, $page) = explode('|',$line);
                $dt= @date('d-m-Y H:i:s',$dt);
                $data .= "<li>($counter) $dt: $page </li>";
                $counter++;
            }
            echo $data;
        }
    }
  }
?>