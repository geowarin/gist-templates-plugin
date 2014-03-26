package com.gisttemplates.gistfiles;

import com.geowarin.rest.api.Gist;
import com.geowarin.rest.api.GistFile;
import com.gisttemplates.api.GistTemplate;
import org.apache.commons.lang.RandomStringUtils;

import javax.swing.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 21/03/2014
 * Time: 18:33
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistFilesDialogTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public final void run() {
                List<GistTemplate> gistTemplates = Arrays.asList(createFakeTemplate(), createFakeTemplate());
                GistFilesDialog gistFilesDialog = new GistFilesDialog(null, gistTemplates);
                gistFilesDialog.show();
                System.out.println(gistFilesDialog.getSelectedFiles());
            }
        });
    }

    public static GistTemplate createFakeTemplate() {
        return new GistTemplate(createFakeGist(), false);
    }

    private static Gist createFakeGist() {
        Gist gist = new Gist();
        Map<String, GistFile> files = new LinkedHashMap<String, GistFile>();

        String fileName = RandomStringUtils.randomAlphabetic(12);
        files.put(fileName, createGistFile(fileName));
        files.put("AClass.java", createGistFile("AClass.java"));

        gist.setFiles(files);
        gist.setDescription("How dead. You blow like a cannon. Pieces o' death are forever small.");
        return gist;
    }

    private static GistFile createGistFile(String filename) {
        GistFile gistFile = new GistFile();
        gistFile.setFilename(filename);
        gistFile.setContent("content");
        return gistFile;
    }
}
