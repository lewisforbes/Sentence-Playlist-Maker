<%@ page import ="java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="stylesheet.css">
    <title>Playlist Message</title>
</head>
<body>
<center>
    <h1>
        <a href="http://lewis-forbes.us-east-2.elasticbeanstalk.com/sentence/" style="color:inherit">Song Messenger</a>
    </h1>
    <h3>
        No songs could be found for the following words:
    </h3>
    <p>
        <%
        List result= (List) request.getAttribute("invalidWords");
        Iterator it = result.iterator();
        while(it.hasNext()) {
            out.println(it.next()+"<br>");
        }
        %>
    </p>
    <%
        String originalSentence = (String) request.getAttribute("originalSentence");
    %>
    <form method="post" action="result">
            <br><br>
            <label for="sentence">Amend your sentence and try again:</label><br><br>
            <textarea type ="text" id="sentence" name ="sentence" required style="resize:none" cols="50" rows="3"><% out.print(originalSentence); %></textarea>

            <br><br>

            <input type="checkbox" id="allowDups" name="allowDups" value="allowDups">
            <label for="allowDups">Allow duplicate tracks?</label>

            <br><br>

            <input type="submit" value="Make Playlist">
        <br><br><br>
    </form>
    <div>
        <p>Also check out <a href="https://twitter.com/tunes_of_lew/">@tunes_of_lew</a> on Twitter:</p>
        <a class="twitter-timeline" data-width="350" data-height="400" data-theme="dark" href="https://twitter.com/tunes_of_lew?ref_src=twsrc%5Etfw">Tweets by tunes_of_lew</a> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>
    </div>
    <br><br><br>
    <h2 style="font-size:x-large">Hefty merci to</h2>
    <a href="https://github.com/thelinmichael/spotify-web-api-java">thelinmichael on Github</a>
    <div>
        <br><br>
        <p>Made by <a href="https://lewisforbes.com/"> Lewis Forbes</a></p>
        <p>Code available on <a href="https://github.com/lewisforbes/Sentence-Playlist-Maker">GitHub</a></p>
    </div>
</center>


</body>
</html>