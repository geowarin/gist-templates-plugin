package com.gisttemplates.gist;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.GistService;

import java.io.IOException;
import java.util.List;

/**
 * Date: 09/02/2014
 * Time: 20:32
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistCache {
    private final String githubUserName;
    private final boolean includeFavorites;
    private List<GistTemplate> gists;

    public GistCache(String githubUserName, boolean includeFavorites) {
        this.githubUserName = githubUserName;
        this.includeFavorites = includeFavorites;
    }

    public void fetchGists(GitHubClient githubClient, Project project) {
        try {
            fetch(githubClient, project);
        } catch (IOException e) {
            notifyFetchError(e);
        }
    }

    private void notifyFetchError(IOException e) {
        Notifications.Bus.notify(new Notification("GistTemplates", "Error while fetching gists for " + githubUserName, e.getMessage(), NotificationType.ERROR));
    }

    private void fetch(GitHubClient githubClient, Project project) throws IOException {
        final GistService gistService = new GistService(githubClient);
        gists = loadGist(project, gistService);
    }

    private List<GistTemplate> loadGist(Project project, GistService gistService) throws IOException {
        LoadGistTask process = new LoadGistTask(gistService, githubUserName, includeFavorites);
        return ProgressManager.getInstance().runProcessWithProgressSynchronously(process, "Loading gists for " + githubUserName, true, project);
    }

    public List<GistTemplate> getGists() {
        return gists;
    }
}
