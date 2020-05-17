<%@ page import ="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="stylesheet.css">
<title>Playlist Message</title>
</head>
<body>
<center>
<h1>
    <a href="http://lewis-forbes.us-east-2.elasticbeanstalk.com/sentence/" style="color:inherit">Check your Spotify!</a>
</h1>
<%
String playlistID = (String) request.getAttribute("playlistID");

String embedSrc = "https://open.spotify.com/embed/playlist/" + playlistID;
String shareURL = "https://www.facebook.com/plugins/share_button.php?href=https%3A%2F%2Fopen.spotify.com%2Fplaylist%" + playlistID + "&layout=button&size=large&width=77&height=28&appId";

%>
<h3>Your playlist has already been added to your library, here's a preview though...<h3>
<iframe src="<%= embedSrc %>" width="350" height="400" frameborder="0" allowtransparency="true" allow="encrypted-media"></iframe>

<br><br>
<p>Share it with the world!<p>

<iframe src="<%=shareURL %>" width="77" height="28" style="border:none;overflow:hidden" scrolling="no" frameborder="0" allowTransparency="true" allow="encrypted-media"></iframe>

<br><br>
<a href="http://lewis-forbes.us-east-2.elasticbeanstalk.com/sentence/"> <h2>Make Another</h2></a>

<br>
 <div>
        <p>Made by <a href="https://lewisforbes.com/">Lewis Forbes</a></p>
        <p>Code available on <a href="https://github.com/lewisforbes/Sentence-Playlist-Maker">GitHub</a></p>
    </div>
</body>
</html>