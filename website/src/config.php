<?php

function DBConnect() {
	$dbhost = 'localhost';
	$dbuser = 'root';
	$dbpass = 'root';
	$dbname = 'ontwerptotnut';
	
	mysql_connect($dbhost, $dbuser, $dbpass) or die ('Error connecting to mysql');
	mysql_select_db($dbname);
}

function varP($var) {
	if (isset($_POST[$var])) {
		return $_POST[$var];
	} else {
		return null;
	}
}

function SQLquery($query) {
	$result = mysql_query ($query);
	
	if (!$result) {
	    $message  = 'Invalid query: ' . mysql_error() . "\n";
	    $message .= 'Whole query: ' . $query;
	    die($message);
	} else {
		return $result;
	}
}