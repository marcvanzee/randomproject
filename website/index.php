<?php 
/*
$login = $_POST['login'];
$name = $_POST['name'];

if (!$login || !$name) {
	header('location: login.php');
}
*/
if ($handle = @opendir('stl/')) {
	while (false !== ($r_file=readdir($handle))) {
		if (strpos($r_file, '.stl',1))
			$stl_files[]=array(filemtime('stl/'.$r_file),$r_file);   #2-D array
   	}
   	closedir($handle);
} else {
	die('I/O error');
}
/*
$file = null;

$setLegsAngle   = false;
$setBackAngle   = false;
$setChairHeight = false;

if ($handle = @opendir('usr/')) {
	while (false !== ($r_file=readdir($handle))) {
		if ($r_file == $name . '.usr') {
			// user already exists, load data
			$lines = file('usr/'.$r_file);
			foreach($lines as $line) {
				if (strpos($line, 'legsMinAngle') !== false) {
					$setLegsAngle = true;
					$legsMinAngle = trim(substr(strstr($line, '='), 2)); 
				} else if (strpos($line, 'legsMaxAngle') !== false) {
					$legsMaxAngle = trim(substr(strstr($line, '='), 2));
				} else if (strpos($line, 'backMinAngle') !== false) {
					$setBackAngle = true;
					$backMinAngle = trim(substr(strstr($line, '='), 2)); 
				} else if (strpos($line, 'backMaxAngle') !== false) {
					$backMaxAngle = trim(substr(strstr($line, '='), 2)); 
				} else if (strpos($line, 'seatMinHeight') !== false) {
					$setChairHeight = true;
					$chairMinHeight = trim(substr(strstr($line, '='), 2)); 
				} else if (strpos($line, 'seatMaxHeight') !== false) {
					$chairMaxHeight = trim(substr(strstr($line, '='), 2)); 
				}
			}
			$file = $r_file;
		}
   	}
   	closedir($handle);
} else {
	die('I/O error');
}

if ($file == null) { // user doesn't exist, create new file
	$file = $name . '.usr';
	fopen('usr/'.$file, 'w') or die('I/O error');
}	
*/
if ($stl_files) {
	$chair = $stl_files[0][1];
} else {
	$chair = null;
}

?>
<html>
<head>
<style>
div#mailForm {
  margin-left: 20px;
}
p#closeBuy {
  cursor: pointer;
}

p#closeBuy:hover {
  text-decoration: underline;
} 
.butt {
  border: 1px solid black;
}
.butt:hover {
  border: 1px solid red;
}
</style>
<script language="javascript">
var current_chair = <?php echo $chair ? "\"" . $chair . "\"" : "null" ?>;

<?php
/*
if ($setLegsAngle) {
	//echo "document.chairApplet.setLegsAngle(" . $legsMinAngle . "," . $legsMaxAngle . ");\n";
} 
if ($setBackAngle) {
	//echo "document.chairApplet.setBackAngle(" . $backMinAngle . "," . $backMaxAngle . ");\n";
} 
if ($setChairHeight) {
	//echo "document.chairApplet.setLegsAngle(" . $chairMinHeight . "," . $chairMaxHeight . ");\n";
} 
*/
?>

function run() {
	if (current_chair) {
		// first get all the current values of this chair from the java applet
	
		// seatHeight[0] = seatMinHeight
		// seatHeight[1] = seatMaxHeight
		var curSeatHeight = document.chairApplet.getSeatHeight().split('|');
		var curMinSeat = parseFloat(curSeatHeight[0]);
		var curMaxSeat = parseFloat(curSeatHeight[1]);
		alert('curMinSeat: ' + curMinSeat + ' en curMaxSeat: ' + curMaxSeat);
	
		// same for legs
		var curLegsAngle = document.chairApplet.getLegsAngle().split('|');
		var curMinLegs = parseFloat(curLegsAngle[0]);
		var curMaxLegs = parseFloat(curLegsAngle[1]);
		
		// and for the back
		var curBackAngle = document.chairApplet.getBackAngle().split('|');
		var curMinBack = parseFloat(curBackAngle[0]);
		var curMaxBack = parseFloat(curBackAngle[1]);
		
		var elSel = document.getElementById('selectX');
  		for (i = 0; i<elSel.length; i++) {
  			switch(parseInt(elSel.options[i].value)) {
  				case 1: // more skewed legs -> //
  					//
  					break;
  				case 2: // less skewed legs -> //
  					//
  					break;
  				case 3: // steeper back -> //
  					alert('steeper back');
  					document.chairApplet.setBackAngle(0);
  					break;
  				case 4: // straighter back -> //
  					alert('straighter back');
  					document.chairApplet.setBackAngle(1);
  					break  			
  	  			case 5: // higher seat -> set the minimal border to the current lowest point of the chair
  	    			document.chairApplet.setSeatMinHeight(curMinSeat);
  	    			break;
  	    		case 6: // lower seat -> set the maximal border to the current highest point
  	    			document.chairApplet.setSeatMaxHeight(curMaxSeat);
  	    			break;
  	    	}
  		}
  		elSel.options.length = 1;
  		var backMinMax = document.chairApplet.getBackMinMax().split('|');
		var backMin = parseFloat(backMinMax[0]);
		var backMax = parseFloat(backMinMax[1]);
		alert('min=' + backMin + ', max=' + backMax);
  	}

	var filename = document.chairApplet.generateFile();
	var longStr = document.chairApplet.createChair() + "";
	
	document.getElementById('choiceDiv').style.visibility = 'visible';
	document.body.innerHTML += '<form id="' + filename + '" action="manage.php" target="chairFrame" method="post"><input type="hidden" name="file" value="' + filename + '"><input type="hidden" name="str" value="' + longStr + '"></form>';
	document.getElementById(filename).submit();
}

function getSTL() {
	if (!arguments[0]) {
		if (current_chair != null) {
			document.getElementById('mailform').style.visibility = 'visible';
			document.getElementById('close').style.border = '1px solid black';
		}
	} else {
		document.getElementById('mailform').style.visibility = 'hidden';
		document.getElementById('close').style.border = '0px';
	}
}

function toggleBuy(show) {
	if (show) {
		document.getElementById('buyC').style.visibility = 'visible';
		document.getElementById('choiceDiv').style.visibility = 'visible';
	} else {
		document.getElementById('buyC').style.visibility = 'hidden';
		document.getElementById('choiceDiv').style.visibility = 'hidden';
	}
}

function setChair(chair) {
	current_chair = chair;
	document.mailForm.file.value = chair;
}

function removeOptionSelected()
{
  var elSel = document.getElementById('selectX');
  var i;
  var done=false;
  for (i = elSel.length - 1; i>=0; i--) {
    if ((elSel.options[i].selected) && (elSel.value != 0)) {
      elSel.remove(i);
      done=true;
    }
  }
}

function appendOptionLast(txt, val)
{
  var elOptNew = document.createElement('option');
  elOptNew.text = txt;
  elOptNew.value = val;
  var elSel = document.getElementById('selectX');

  try {
    elSel.add(elOptNew, null); // standards compliant; doesn't work in IE
  }
  catch(ex) {
    elSel.add(elOptNew); // IE only
  }
}
//-->
</script>
<title>designdesign - ontwerp tot nut</title>
<link type="text/css" rel="stylesheet" href="src/layout.css" />
</head>
<body <?php if (!$chair) echo "onLoad=\"toggleBuy(false);\"" ?>>
<applet code="nl.ontwerptotnut.chair.GUI.class" name="chairApplet" height="0" width="0"></applet>
<h1>chair maker with learning algorithm. ALFA VERSION
<table width=100%>
<tr><td align="center">
  <table width=1000 cellpadding="0" cellspacing="0">
  <tr valign="top">
  <td width=200 align="right"><iframe width="300" height="625" style="border: 0px solid black" name="navFrame" src="navigate.php<?php if (!$chair) echo "?close=1" ?>"></iframe></td>
  <td width=600 align="center"><iframe width="600" height="625" style="border: 1px solid black" name="chairFrame" id="ifr" <?php if ($chair) echo "src=viewer.php?file=" . substr($chair, 0, strpos($chair, '.')) ?>></iframe></td>
  <td width=200>
  <a href="javascript:run()" onMouseOver="document.newChair.src='img/newChairAct.jpg';" onMouseOut="document.newChair.src='img/newChair.jpg';">
    <img src="img/newChair.jpg" name="newChair" border=0>
  </a><br />
  <a href="javascript:getSTL()" onMouseOver="document.buyChair.src='img/buyChairAct.jpg';" onMouseOut="document.buyChair.src='img/buyChair.jpg';">
    <img src="img/buyChair.jpg" name="buyChair" id="buyC" border=0">
  </a>
  </td>
  </tr><tr>
  
  <td colspan=3 align="center"><br />
     <div id="choiceDiv" <?php if (!$chair) echo "style=\"visibility: hidden\"" ?>>
   <form name="choices">
<table>
<tr valign="top"><td width=250>
Make...<br>
 <input class="butt" type="button" value="the legs more skewed" onclick="appendOptionLast('i want the legs more skewed', 1);" />
 <input class="butt" type="button" value="the legs less skewed" onclick="appendOptionLast('i want the legs less skewed', 2);" /><br />
  
 <input class="butt" type="button" value="the back steeper" onclick="appendOptionLast('i want a steeper back', 3);" />
 <input class="butt" type="button" value="the back more straight" onclick="appendOptionLast('i want the back to be more straight', 4);" /><br />
 
 <input class="butt" type="button" value="the seat higher" onclick="appendOptionLast('i want a higher seat', 5);" />
 <input class="butt" type="button" value="the seat lower" onclick="appendOptionLast('i want a lower seat', 6);" /><br />
 
 <input class="butt" type="button" value="legs like these" onclick="appendOptionLast('i want legs like these', 7);" />
 <input class="butt" type="button" value="a back like this" onclick="appendOptionLast('i want a back like this', 8);" /><br />
 <input class="butt" type="button" value="a seat like this" onclick="appendOptionLast('i want a seat like this', 9);" /><br />
 
</td><td>
 <select id="selectX" size="10" width="30" multiple="multiple">
  <option value="0" selected="selected">-- What do you think of the chair?</option>
 </select>
</td><td>
 <input class="butt" type="button" value="x" onclick="removeOptionSelected();" />
</td></tr>
</table>
</form>
</div>
</td></tr><tr>


   <td></td><td id="close">
   

   <div id="mailform" style="visibility: hidden" margin>
    <br /><b>enter emailaddress to receive the STL file.</b><br /><br />
    <form method="POST" action="mail.php" target="_blank" name="mailForm">
      <input type="hidden" name="file" value=current_chair>
      emailaddress: <input type="text" name="email" length="30">
      <input type="submit" name="send" value="send STL">
    </form><br />
    <p onClick="javascript:getSTL(true)" id="closeBuy">close</p>
   </div>
   </td></tr>
  </table>
</td></tr>
</table>

</body>
</html>