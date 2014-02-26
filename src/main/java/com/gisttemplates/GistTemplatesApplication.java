package com.gisttemplates;

import com.gisttemplates.adapter.GithubAdapter;
import com.gisttemplates.configuration.GistTemplatesSettings;
import com.gisttemplates.gist.GistCache;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.impl.ComponentManagerImpl;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.jetbrains.annotations.NotNull;
import org.picocontainer.MutablePicoContainer;

import java.util.ArrayList;
import java.util.Collections;
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
    private ComponentManagerImpl componentManager;

    public GistTemplatesApplication(@NotNull ComponentManagerImpl componentManager) {
        this.componentManager = componentManager;
        gistTemplatesSettings = GistTemplatesSettings.getInstance();
    }

    public static GistTemplatesApplication getInstance() {
        return ApplicationManager.getApplication().getComponent(GistTemplatesApplication.class);
    }

    public void initComponent() {
        MutablePicoContainer picoContainer = componentManager.getPicoContainer();
        try {
            initGithubAdapter(picoContainer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initGithubAdapter(MutablePicoContainer picoContainer) throws Exception {
        switch (getVersion()) {
            case V11:
                picoContainer.registerComponentInstance(GithubAdapter.class.getName(), Class.forName("com.gisttemplates.github.Github11").newInstance());
                break;
            case V12:
                picoContainer.registerComponentInstance(GithubAdapter.class.getName(), Class.forName("com.gisttemplates.github.Github12").newInstance());
                break;
            case V13:
                picoContainer.registerComponentInstance(GithubAdapter.class.getName(), Class.forName("com.gisttemplates.github.Github13").newInstance());
                break;
        }
    }

    private IntelliJVersion getVersion() {
        int version = ApplicationInfo.getInstance().getBuild().getBaselineVersion();
        if (version >= 130) {
            return IntelliJVersion.V13;
        } else if (version >= 120) {
            return IntelliJVersion.V12;
        }
        return IntelliJVersion.V11;
    }

    enum IntelliJVersion {
        V11, V12, V13
    }

    public void disposeComponent() {
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

    public List<Gist> getStarredGists() {
        if (cacheForGithubUser != null) {
            return cacheForGithubUser.getStarredGists();
        }
        return Collections.emptyList();
    }

    private boolean initCaches() {
        for (GistCache cache : getCaches()) {
            cache.fetch(getGithubClient());
        }
        return caches.isEmpty();
    }

    @NotNull
    public String getComponentName() {
        return "GistTemplatesApplication";
    }

    public void addCacheForGithubUser() {
        cacheForGithubUser = new GistCache(GithubAdapter.getInstance().getLogin(), true);
    }

    public List<GistCache> getCaches() {

        List<GistCache> allCaches = new ArrayList<GistCache>();
        if (cacheForGithubUser == null
                && gistTemplatesSettings.isUseGithubAccount()
                && GithubAdapter.getInstance().isCredentialsDefined()) {
            addCacheForGithubUser();
        }
        if (cacheForGithubUser != null) {
            allCaches.add(cacheForGithubUser);
        }
        allCaches.addAll(caches);
        return allCaches;
    }

    private GitHubClient getGithubClient() {
        GithubAdapter githubAdapter = GithubAdapter.getInstance();
        GitHubClient client = new GitHubClient();
        if (gistTemplatesSettings.isUseGithubAccount() && githubAdapter.isCredentialsDefined()) {
            client.setCredentials(githubAdapter.getLogin(), githubAdapter.getPassword());
        }
        return client;
    }

}
