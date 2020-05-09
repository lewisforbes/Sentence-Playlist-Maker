package program.run;

import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import program.GetUserApproval;
import com.wrapper.spotify.SpotifyApi;

import java.util.ArrayList;
import java.util.HashMap;

public class Go {

    private static SpotifyApi api = null;
    private static String name;
    private static String desc;
    private static HashMap<Integer, Track> tracksHM;

    public static ArrayList<String> firstAttempt(String code, String sentence, String givenName, String givenDesc) {
        name = givenName;
        desc = givenDesc;
        try {
            api = mkNewApi(code);
            return nextAttempt(sentence);
        } catch (Exception e) { }
        throw new IllegalArgumentException("Got to here in Go.go.");
    }

    public static ArrayList<String> nextAttempt(String sentence) {
        try {
            tracksHM = GetTracks.getTracks(api, sentence);
            return getUnmatchedWords(tracksHM, sentence);
        } catch (Exception e) { }
        throw new IllegalArgumentException("Got here in Go.nextAttempt");
    }

    private static SpotifyApi mkNewApi(String code) {
        SpotifyApi output = GetUserApproval.api;
        AuthorizationCodeRequest authorizationCodeRequest = output.authorizationCode(code).build();
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            output.setAccessToken(authorizationCodeCredentials.getAccessToken());
            output.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            return output;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        throw new IllegalArgumentException("Was unable set credentials.");
    }

    public static String getID() {
        return MakePlaylist.makePlaylist(api, new ArrayList<>(tracksHM.values()), name, desc);
    }

    private static ArrayList<String> getUnmatchedWords(HashMap<Integer, Track> tracksHM, String sentence) {
        ArrayList<String> output = new ArrayList<>();
        String[] splitSentence = sentence.split(" ");

        for (Integer i : tracksHM.keySet()) {
            if (tracksHM.get(i) == null) {
                output.add(splitSentence[i]);
            }
        }

        return output;
    }
}
