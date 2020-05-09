package program.run;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.special.SnapshotResult;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.model_objects.specification.User;
import com.wrapper.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.util.ArrayList;
import java.util.Objects;

public class MakePlaylist {

    private static final String defaultName = "My Message to You";
    private static final String defaultDescription = "Made at bit.ly/sentenceplaylist";

    public static String makePlaylist(SpotifyApi api, ArrayList<Track> tracks, String givenName, String givenDesc) {
        Objects.requireNonNull(tracks, "given tracks array is null");

        String name = givenName;
        if ((givenName==null) || (givenName.equals(""))) {
            name = defaultName;
        }

        String description = givenDesc;
        if ((givenDesc==null) || (givenDesc.equals(""))) {
            description = defaultDescription;
        } else {
            description += " | " + defaultDescription;
        }

        String playlistID = initialisePL(api, name, description);
        addToPlaylist(api, tracks, playlistID);
        return playlistID;
    }
    public static String initialisePL(SpotifyApi api, String name, String description) {
        CreatePlaylistRequest createPlaylistRequest = api.createPlaylist(getUserID(api), name)
                .collaborative(false)
                .public_(true)
                .description(description)
                .build();

        try {
            final Playlist playlist = createPlaylistRequest.execute();
            return playlist.getId();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    private static String getUserID(SpotifyApi api) {
        GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = api.getCurrentUsersProfile().build();
        try {
            User user = getCurrentUsersProfileRequest.execute();
            return user.getId();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }


    private static void addToPlaylist(SpotifyApi api, ArrayList<Track> tracksToAdd, String playlistId) {
        int toRemove = 0;
        for (Track track : tracksToAdd) {
            if (track == null) {
                toRemove++;
            }
        }

        for (int i=1; i<=toRemove; i++) {
            tracksToAdd.remove(null);
        }

        String[] uris = new String[tracksToAdd.size()];
        for (int i = 0; i < tracksToAdd.size(); i++) {
            if (tracksToAdd.get(i) != null) {

            }
            uris[i] = tracksToAdd.get(i).getUri();
        }

        AddItemsToPlaylistRequest addItemsToPlaylistRequest = api.addItemsToPlaylist(playlistId, uris).build();
        try {
            final SnapshotResult snapshotResult = addItemsToPlaylistRequest.execute();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
