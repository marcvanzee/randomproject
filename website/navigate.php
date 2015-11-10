<?php
$close = $_GET['close'];

if ($close != 1) {
	if ($handle = @opendir('stl/')) {
		while (false !== ($file=readdir($handle))) {
			if (strpos($file, '.stl',1))
        		$files[]=array(filemtime('stl/'.$file),$file);   #2-D array
   		}
   		closedir($handle);
   	} else {
   		die('I/O error');
   	}
}
?>

<html>
<head>
<style>
html, body, div, span, applet, object, iframe,
h1, h2, h3, h4, h5, h6, p, blockquote, pre,
a, abbr, acronym, address, big, cite, code,
del, dfn, em, font, img, ins, kbd, q, s, samp,
small, strike, strong, sub, sup, tt, var,
dl, dt, dd, ol, ul, li,
fieldset, form, label, legend,
table, caption, tbody, tfoot, thead, tr, th, td {
	margin: 0;
	padding: 0;
	border: 0;
	outline: 0;
	font-weight: inherit;
	font-style: inherit;
	font-size: 100%;
	font-family: inherit;
	vertical-align: baseline;
}

td#collapse {
  cursor: pointer;
}

td#collapse:hover {
}  
</style>
<link type="text/css" rel="stylesheet" href="src/layout.css" />
<base target="_parent">
</head>
<body 
<?php 
if (!$files) {
	echo "onLoad=\"javascript:parent.toggleBuy(false);\"";
} else {
	echo "onLoad=\"javascript:parent.toggleBuy(true);\"";
}
?>
>

<?php
if ($close != 1) {
?>
<table width=100% height=100% STYLE="background-image: url(img/savedChairs.jpg); background-repeat: no-repeat; background-position: 0% 0%; border-bottom: 1px solid black; border-top: 1px solid black">
 <tr valign=top><td width=26 id="collapse" onclick="window.location='navigate.php?close=1'"></td>
  <td><table width=100%>
  
<?php
	if ($files) {
		rsort($files); #sorts by filemtime

    	foreach ($files as $file) {
    		$name = substr($file[1], 0, strpos($file[1], '.'));
    		$closeNext = (sizeof($files) == 1) ? "&close=1" : "";
        	echo "<tr valign=top><td align=right><a target=\"chairFrame\" href=\"viewer.php?file=" . $name . "\"><b>" . $name . "</b></a></td><td> [<a target=\"chairFrame\" href=\"manage.php?file=" . $file[1] . "&delete=1&refresh=1" . $closeNext . "\" target=\"main\">x</a>]</td></tr>\n";
		}
	} else {
		echo "<tr valign=top><td align=center><br /><p>no chairs saved</p></td></tr>\n";
	}
	echo "</table></td></tr></table>";
} else {
   ?>
	<table width=100% cellpadding=0 cellspacing=0>
   	<tr valign=top><td align=right><a target="_self" href="navigate.php"><img src="img/savedChairsT.jpg" border=0></a></td></tr>
    <?php
}
?>
</body>
</html>