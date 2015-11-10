<?php
$refresh = ($_GET['refresh']) ? 1 : 0;

if (!$_GET['file']) {
	if ($refresh) {
?>
<body "onLoad='javascript:parent.setChair(null); parent.toggleBuy(false); parent.frames["navFrame"].location="navigate.php<?php if ($_GET['close']) echo "?close=1" ?>"'">
<?php
	}
} else {
	$file = $_GET['file'] . ".stl";
	
	/* translate STL file to a string readable by flash
	 * first read $file and put contents in an array
	 */
 	
 	if (is_readable('stl/'.$file)) {
		$lines = file('stl/'.$file);
	} else {
		die("can't open file");
	}

	
	
	/* put only points in an array, sorted by triangles:
	 * triangles[i] = array(point[a], point[b], point[c]);
	 * point[x]    =  array(x, y, z);
	 */
	$triangleCount = 0;
	for ($i=0; $i<sizeof($lines); $i++) {
		$lines[i] = trim($lines[i]);
		if (strstr($lines[$i], "OUTER LOOP")) {
	 		// next three lines will be vertices
	 		for ($j=0; $j<=2; $j++) {
	 			$tempPoint = $lines[$i+$j+1];
	 			$tpoint = explode(" ", $tempPoint);
	 			$point = array($tpoint[1], $tpoint[2], $tpoint[3]);
	 			$points[$triangleCount*3+$j] = $point;
	 		}
	 		//$triangles[$triangleCount] = $points;
	 		$triangleCount++;
	 	}
	}
	
	/* now create a string readable for flash
	 * use the following format:
	 * NUM_POINTS [N]
	 * point [i] [x],[y],[z]
	 * FINISHED
	 *
	 * use '|' as the delimiter
	 */
	 
	 $DTMR = '|'; 
	 $longStr = "NP " . sizeof($points) . $DTMR;
	 for ($i = 0; $i < sizeof($points); $i++)
	 	$longStr .= "p " . $i . " " . $points[$i][0] . "," . $points[$i][1] . "," . trim($points[$i][2]) . $DTMR;
	 $longStr .= "FD";
	 
?>

<html lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!--  BEGIN Browser History required section -->
<link rel="stylesheet" type="text/css" href="history/history.css" />
<link rel="stylesheet" type="text/css" href="src/layout.css" />
<!--  END Browser History required section -->

<title></title>
<script src="AC_OETags.js" language="javascript"></script>

<!--  BEGIN Browser History required section -->
<script src="history/history.js" language="javascript"></script>
<!--  END Browser History required section -->

<style>
body { margin: 0px; overflow:hidden }
</style>
<script language="JavaScript" type="text/javascript">
<!--
// -----------------------------------------------------------------------------
// Globals
// Major version of Flash required
var requiredMajorVersion = 9;
// Minor version of Flash required
var requiredMinorVersion = 0;
// Minor version of Flash required
var requiredRevision = 124;
// -----------------------------------------------------------------------------
// -->
</script>
</head>

<body cellpadding="0" cellspacing="0" scroll="no" onLoad="parent.setChair('<?=$file?>')">
<script language="JavaScript" type="text/javascript">
<!--
// Version check for the Flash Player that has the ability to start Player Product Install (6.0r65)
var hasProductInstall = DetectFlashVer(6, 0, 65);

// Version check based upon the values defined in globals
var hasRequestedVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);

if ( hasProductInstall && !hasRequestedVersion ) {
	// DO NOT MODIFY THE FOLLOWING FOUR LINES
	// Location visited after installation is complete if installation is required
	var MMPlayerType = (isIE == true) ? "ActiveX" : "PlugIn";
	var MMredirectURL = window.location;
    document.title = document.title.slice(0, 47) + " - Flash Player Installation";
    var MMdoctitle = document.title;

	AC_FL_RunContent(
		"src", "playerProductInstall",
		"FlashVars", "MMredirectURL="+MMredirectURL+'&MMplayerType='+MMPlayerType+'&MMdoctitle='+MMdoctitle+"",
		"width", "600",
		"height", "600",
		"align", "left",
		"id", "CustomMesh",
		"quality", "high",
		"bgcolor", "#ffffff",
		"name", "CustomMesh",
		"allowScriptAccess","sameDomain",
		"type", "application/x-shockwave-flash",
		"pluginspage", "http://www.adobe.com/go/getflashplayer"
	);
} else if (hasRequestedVersion) {
	// if we've detected an acceptable version
	// embed the Flash Content SWF when all tests are passed
	AC_FL_RunContent(
			"src", "CustomMesh?s=<?php echo $longStr; ?>",
			"width", "600",
			"height", "600",
			"align", "left",
			"id", "CustomMesh",
			"quality", "high",
			"bgcolor", "#ffffff",
			"name", "CustomMesh",
			"allowScriptAccess","sameDomain",
			"type", "application/x-shockwave-flash",
			"pluginspage", "http://www.adobe.com/go/getflashplayer"
	);
  } else {  // flash is too old or we can't detect the plugin
    var alternateContent = 'Alternate HTML content should be placed here. '
  	+ 'This content requires the Adobe Flash Player. '
   	+ '<a href=http://www.adobe.com/go/getflash/>Get Flash</a>';
    document.write(alternateContent);  // insert non-flash content
  }
// -->
</script>
<noscript>
  	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			id="CustomMesh" width="600" height="600"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="CustomMesh.swf" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="#ffffff" />
			<param name="allowScriptAccess" value="sameDomain" />
			<embed src="CustomMesh.swf" quality="high" bgcolor="#869ca7"
				width="600" height="600" name="CustomMesh" align="left"
				play="true"
				loop="false"
				quality="high"
				allowScriptAccess="sameDomain"
				type="application/x-shockwave-flash"
				pluginspage="http://www.adobe.com/go/getflashplayer">
			</embed>
	</object>
</noscript>
<hr width=100% style="border: 1px solid black">
<p align="right"><i><?=$file?>&nbsp;&nbsp;</i></p>
<?php if ($refresh) { ?>
<script language="JavaScript" type="text/javascript">
parent.frames["navFrame"].location="navigate.php";
</script>
<? } ?>
</body>
</html>
<?php
}
?>