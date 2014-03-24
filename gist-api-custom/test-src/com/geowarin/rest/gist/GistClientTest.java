package com.geowarin.rest.gist;

import com.geowarin.rest.api.Gist;
import com.geowarin.rest.api.GistFile;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GistClientTest {
    public static final String PASSWORD = "CHANGE_ME";
    private GistClient gistClient;

    @Test
    public void testGetGists() throws Exception {

        List<Gist> gists = new GistClient().getGists("geowarin");
        assertThat(gists).isNotEmpty();
        System.out.println(gists);
    }

    @Test
    public void testGetOneGist() throws Exception {
        Gist gist = new GistClient().getGist("8720930");
        assertThat(gist).isNotNull();
        assertThat(gist.getFiles()).isNotEmpty();
        GistFile firstFile = gist.getFiles().values().iterator().next();
        assertThat(firstFile.getContent()).isNotEmpty();
        System.out.println(firstFile.getContent());
    }

    @Test
    public void testGetGistsAuthenticated() throws Exception {

        List<Gist> gists = new GistClient("geowarin", PASSWORD).getGists("geowarin");
        assertThat(gists).isNotEmpty();
        System.out.println(gists);
    }

    @Test
    public void testGetStarredGistsAuthenticated() throws Exception {

        List<Gist> gists = new GistClient("geowarin", PASSWORD).getStarredGists();
        assertThat(gists).isNotEmpty();
        System.out.println(gists);
    }

    @Test
    public void testGetGistsFilesAuthenticated() throws Exception {

        List<Gist> gists = new GistClient("geowarin", PASSWORD).getGists("geowarin");
        assertThat(gists).isNotEmpty();
        System.out.println(gists);
        Gist gist = gists.get(0);
        Collection<GistFile> files = gist.getFiles().values();
        assertThat(files).isNotEmpty();
        System.out.println(files);
    }


}
