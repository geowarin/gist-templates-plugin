package com.gisttemplates.gist;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.service.GistService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Date: 09/02/2014
 * Time: 20:45
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistCacheTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testFetch() throws Exception {

        GistCache gistCache = new GistCache("geowarin");
        gistCache.fetch();


        List<Gist> gists = gistCache.getGists();
        for (Gist gist : gists) {
            Map<String, GistFile> files = gist.getFiles();
            for (GistFile gistFile : files.values()) {
                System.out.println(gistFile.getFilename() + " " + gistFile.getContent());
            }
        }
    }
}
