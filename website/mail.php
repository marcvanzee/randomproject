<?php
if ($_POST['send'] && $_POST['email'] && $_POST['file']) {
	$email = $_POST['email'];
	$file =  $_POST['file'];
	
//define the receiver of the email
$to = $email;
//define the subject of the email
$subject = 'STL file from ontwerptotnut (' . $file . ')';
//create a boundary string. It must be unique
//so we use the MD5 algorithm to generate a random hash
$random_hash = md5(date('r', time()));
//define the headers we want passed. Note that they are separated with \r\n
$headers = "From: mailer@ontwerptotnut.nl\r\nReply-To: admin@ontwerptotnut.nl";
//add boundary string and mime type specification
$headers .= "\r\nContent-Type: multipart/mixed; boundary=\"PHP-mixed-".$random_hash."\"";
//read the atachment file contents into a string,
//encode it with MIME base64,
//and split it into smaller chunks
$attachment = chunk_split(base64_encode(file_get_contents('stl/'.$file)));
//define the body of the message.
ob_start(); //Turn on output buffering
?>
--PHP-mixed-<?php echo $random_hash; ?> 
Content-Type: multipart/alternative; boundary="PHP-alt-<?php echo $random_hash; ?>"

--PHP-alt-<?php echo $random_hash; ?> 
Content-Type: text/plain; charset="iso-8859-1"
Content-Transfer-Encoding: 7bit

Here is the chair that you have chosen from the chair generator.
You can open this file in any 3d rendering program.

Have fun!

--PHP-alt-<?php echo $random_hash; ?> 
Content-Type: text/html; charset="iso-8859-1"
Content-Transfer-Encoding: 7bit

<h2>here it is!</h2>
<p>Your very own chair, chosen from the chair generator<br />
You can open this file in any 3d rendering program.<br />
</br>
<b>have fun!</b>

--PHP-alt-<?php echo $random_hash; ?>--

--PHP-mixed-<?php echo $random_hash; ?> 
Content-Type: application/zip; name="<?=$file?>" 
Content-Transfer-Encoding: base64 
Content-Disposition: attachment 

<?php echo $attachment; ?>
--PHP-mixed-<?php echo $random_hash; ?>--

<?php
//copy current buffer contents into $message variable and delete current output buffer
$message = ob_get_clean();
//send the email
$mail_sent = @mail( $to, $subject, $message, $headers );
//if the message is sent successfully print "Mail sent". Otherwise print "Mail failed"
if ($mail_sent) {
	echo "Mail sent, you may now close this window";
} else {
	echo "Mail failed, please close this window and try again";
}
}
?>