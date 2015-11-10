<?php

/* - receive a chair ($str) which is ready to put in a file
 *   only change | in \n
 * - put in file ($file)
 * - return to index
 */

if ((!$_POST['str'] && !$_POST['file']) &&
		(!$_GET['file'] && !$_GET['delete'])) {
	header('location: viewer.php');
}

$file = ($_POST['file']) ? $_POST['file'] : $_GET['file'];

if ($_GET['delete']) {
	if (strpos($file, '.stl',1) && !strstr($file, '/')) {
		if (is_readable('stl/'.$file)) {
			unlink('stl/'.$file);
			$close = ($_GET['close']) ? "&close=1" : "";
			header('location: viewer.php?refresh=1' . $close);
		}
	} else {
		header('location: viewer.php');
	}
} else if ($_POST['str']) {
	$str =  $_POST['str'];
	$arr_str = explode("|", $str);

	$fh = fopen('stl/'.$file, 'w') or die("save problem: can't open file");

	foreach ($arr_str as $phrase) {
    	fwrite($fh, $phrase . "\n");
	}

	if (fclose($fh))
		header('location: viewer.php?refresh=1&file=' . substr($file, 0, strpos($file, '.')));
} else {
	header('location: viewer.php');
}
?>