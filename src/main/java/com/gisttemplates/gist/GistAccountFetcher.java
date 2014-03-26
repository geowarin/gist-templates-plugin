package com.gisttemplates.gist;

import com.geowarin.rest.gist.GistClient;
import com.gisttemplates.api.GistTemplate;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Date: 09/02/2014
 * Time: 20:32
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistAccountFetcher {
    private final String githubUserName;
    private final boolean includeFavorites;

    public GistAccountFetcher(String githubUserName, boolean includeFavorites) {
        this.githubUserName = githubUserName;
        this.includeFavorites = includeFavorites;
    }

    public List<GistTemplate> fetchGistsList(GistClient gistClient, Project project) {
        try {
            return loadGistListInBackground(gistClient, project);
        } catch (IOException e) {
            notifyFetchError(e);
        }
        return Collections.emptyList();
    }

    private void notifyFetchError(IOException e) {
        Notifications.Bus.notify(new Notification("GistTemplates", "Error while fetching gists for " + githubUserName, e.getClass().getSimpleName(), NotificationType.ERROR));
    }

    private List<GistTemplate> loadGistListInBackground(GistClient gistClient, Project project) throws IOException {
        LoadGistListTask process = new LoadGistListTask(gistClient, githubUserName, includeFavorites);
        return ProgressManager.getInstance().runProcessWithProgressSynchronously(process, "Loading gists for " + githubUserName, true, project);
    }
}
