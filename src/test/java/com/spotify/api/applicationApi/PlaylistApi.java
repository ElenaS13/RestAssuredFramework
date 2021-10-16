package com.spotify.api.applicationApi;

import com.spotify.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.utls.ConfigLoader;
import io.restassured.response.Response;


import static com.spotify.api.Route.PLAYLISTS;
import static com.spotify.api.Route.USERS;
import static com.spotify.api.TokenManager.getToken;


public class PlaylistApi {
//    static String access_token = "BQDL9Dk3LUyETJqQZi7Nqj68PnVEag0zFvOzfbpxX8Hf08FjLljS5bq82YR3iqXZTcXr7ijoB8mk55R_aruvFipNELYEciaxPVYL3eZwMQT2ggcVvyyMuZCQ2pnP18wLyOiKFwGlO2vQ94nJYG8_XunKK2MfGyPxopSxWQo_eZVsjRXg4YOuvvJE6ipnZLP6lZhk3u9DPwWEVCHg_6mhboMmPkRimgUOLIkm6FWyqAdW";


    public static Response post(Playlist requestPlaylist){
       return RestResource.post(USERS +"/" + ConfigLoader.getInstance().getUser() + PLAYLISTS, getToken(), requestPlaylist);

    }

    public static Response post(String token, Playlist requestPlaylist){
       return RestResource.post(USERS +"/" + ConfigLoader.getInstance().getUser() + PLAYLISTS, token, requestPlaylist );

    }

    public static Response get(String playlistId){
        return RestResource.get(PLAYLISTS + "/" + playlistId, getToken());
    }

    public static Response update(String playlistId, Playlist requestPlaylist){
        return RestResource.update(PLAYLISTS + "/" + playlistId, getToken(), requestPlaylist);

    }
}
