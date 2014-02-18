package com.gisttemplates;

import com.gisttemplates.configuration.GistTemplatesSettings;
import com.gisttemplates.gist.GistCache;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.jetbrains.plugins.github.GithubSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 09/02/2014
 * Time: 20:56
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistTemplatesApplication implements ApplicationComponent {
    private GistTemplatesSettings gistTemplatesSettings;
    private final List<GistCache> caches = new ArrayList<GistCache>();
    private GistCache cacheForGithubUser;
    private boolean areCachesInitialized = false;

    public GistTemplatesApplication() {
        gistTemplatesSettings = GistTemplatesSettings.getInstance();
    }

    public static GistTemplatesApplication getInstance() {
        return ApplicationManager.getApplication().getComponent(GistTemplatesApplication.class);
    }

    public void initComponent() {
    }

    public void disposeComponent() {
    }

    public void addCacheForUser(String githubUserName) {
        GistCache gistCache = new GistCache(githubUserName);
        gistCache.fetch(getGithubClient());
        caches.add(gistCache);
    }

    public void invalidateCaches() {
        areCachesInitialized = false;
    }

    public List<Gist> getAllGists() {
        if (!areCachesInitialized) {
            areCachesInitialized = initCaches();
        }

        List<Gist> allGists = new ArrayList<Gist>();
        for (GistCache cache : getCaches()) {
            allGists.addAll(cache.getGists());
        }
        return allGists;
    }

    private boolean initCaches() {

        for (GistCache cache : getCaches()) {
            cache.fetch(getGithubClient());
        }
        return caches.isEmpty();
    }

    public String getComponentName() {
        return "GistTemplatesApplication";
    }

    public void addCacheForGithubUser() {
        cacheForGithubUser = new GistCache(GithubSettings.getInstance().getLogin());
    }

    public List<GistCache> getCaches() {

        List<GistCache> allCaches = new ArrayList<GistCache>();
        if (cacheForGithubUser == null && gistTemplatesSettings.isUseGithubAccount()) {
            addCacheForGithubUser();
        }
        if (cacheForGithubUser != null) {
            allCaches.add(cacheForGithubUser);
        }
        allCaches.addAll(caches);
        return allCaches;
    }

    private GitHubClient getGithubClient() {
        GitHubClient client = new GitHubClient();
        if (gistTemplatesSettings.isUseGithubAccount()) {
            GithubSettings githubSettings = GithubSettings.getInstance();
            client.setCredentials(githubSettings.getLogin(), githubSettings.getPassword());
        }
        return client;
    }

}
