<?php
session_start();
handleDatabase();

function handleDatabase() {
        $format = "d/m/Y H:i:s";
        $time = date($format, time());
        $sql = "INSERT INTO counter (Time) VALUES ('$time')";    
        global $link;
        mysqli_query($link, $sql) or die(mysqli_error($link));
        mysqli_close($link);
    }