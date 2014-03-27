package com.gisttemplates.gist;

import com.gisttemplates.api.GistTemplate;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.GistService;

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

    public List<GistTemplate> fetchGistsList(GitHubClient githubClient) {
        try {
            return loadGistListInBackground(githubClient);
        } catch (IOException e) {
            notifyFetchError(e);
        }
        return Collections.emptyList();
    }

    private void notifyFetchError(IOException e) {
        Notifications.Bus.notify(new Notification("GistTemplates", "Error while fetching gists for " + githubUserName, e.getClass().getSimpleName(), NotificationType.ERROR));
    }

    private List<GistTemplate> loadGistListInBackground(GitHubClient githubClient) throws IOException {
        LoadGistListTask process = new LoadGistListTask(new GistService(githubClient), githubUserName, includeFavorites);
        return ProgressManager.getInstance().runProcessWithProgressSynchronously(process, "Loading gists for " + githubUserName, true, null);
    }
}
