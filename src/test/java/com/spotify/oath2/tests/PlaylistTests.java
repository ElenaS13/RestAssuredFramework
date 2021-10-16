package com.spotify.oath2.tests;

import com.spotify.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.utls.DataLoader;
import io.qameta.allure.Description;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.spotify.api.SpecBuilder.getRequestSpec;
import static com.spotify.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {

    public Playlist playlistBuilder(String name, String description, boolean _public){
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.set_public(_public);
        return playlist;

    }

    public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist){

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    public void assertStatusCode(int actualStatusCode, int expectedStatusCode){
        assertThat(actualStatusCode, equalTo(expectedStatusCode));
    }
    @Description("this is a description")
    @Test(description = "should be able to create a playlist")
    public void ShouldBeAbleToCreateAPlaylist(){
        Playlist requestPlaylist = playlistBuilder("New Playlist", "New playlist description", false);

        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), 201);
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);



    }

    @Test
    public void ShouldBeAbleToGetAPlaylist(){
        Playlist requestPlaylist = playlistBuilder("Updated Playlist Name", "Updated playlist description", false);

        Response response = PlaylistApi.get(DataLoader.getInstance().getPlaylistId());
        assertStatusCode(response.statusCode(), 200);
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);


    }

    @Test
    public void ShouldBeAbleToUpdateAPlaylist(){
        Playlist requestPlaylist = playlistBuilder("New playlist", "New playlist descriptioon", false);

        Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(), requestPlaylist);
        assertThat(response.statusCode(), equalTo(200));



    }



    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithoutName(){
        Playlist requestPlaylist = playlistBuilder("", "New playlist descriptioon", false);


        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(400));

        Error error = response.as(Error.class);


        assertThat(error.getError().getStatus(), equalTo(400));
        assertThat(error.getError().getMessage(), equalTo("Missing required field: name"));
    }

    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithExpiredToken(){
        String invalid_token = "12344";
        Playlist requestPlaylist = playlistBuilder("", "New playlist descriptioon", false);


        Response response = PlaylistApi.post(invalid_token, requestPlaylist);
        assertThat(response.statusCode(), equalTo(401));

        Error error = response.as(Error.class);


        assertThat(error.getError().getStatus(), equalTo(401));
        assertThat(error.getError().getMessage(), equalTo("Invalid access token"));

    }
}
