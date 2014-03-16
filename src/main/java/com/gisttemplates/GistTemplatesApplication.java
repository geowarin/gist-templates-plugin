package com.gisttemplates;

import com.gisttemplates.adapter.GithubAdapter;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.impl.ComponentManagerImpl;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.picocontainer.MutablePicoContainer;

/**
 * Date: 09/02/2014
 * Time: 20:56
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistTemplatesApplication implements ApplicationComponent {
    private ComponentManagerImpl componentManager;

    private static final Logger LOG = Logger.getInstance(GistTemplatesApplication.class.getName());

    public GistTemplatesApplication(@NotNull ComponentManagerImpl componentManager) {
        this.componentManager = componentManager;
    }

    public void initComponent() {
        MutablePicoContainer picoContainer = componentManager.getPicoContainer();
        try {
            initGithubAdapter(picoContainer);
        } catch (Exception e) {
            LOG.error("Error while registering adapters", e);
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

    @NotNull
    public String getComponentName() {
        return "GistTemplatesApplication";
    }

}
