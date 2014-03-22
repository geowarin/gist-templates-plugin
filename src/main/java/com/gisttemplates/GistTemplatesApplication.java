package com.gisttemplates;

import com.gisttemplates.adapter.GUIFactory;
import com.gisttemplates.adapter.GithubAdapter;
import com.gisttemplates.adapter.Icons;
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
            initIconsAdapter(picoContainer);
            initGuiFactory(picoContainer);
        } catch (Exception e) {
            LOG.error("Error while registering adapters", e);
        }
    }

    private void initGuiFactory(MutablePicoContainer picoContainer) throws Exception {
        switch (getVersion()) {
            case V11:
                picoContainer.registerComponentInstance(GUIFactory.class.getName(), Class.forName("com.gisttemplates.gui.GUIFactory11").newInstance());
                break;
            case V12:
                picoContainer.registerComponentInstance(GUIFactory.class.getName(), Class.forName("com.gisttemplates.gui.GUIFactory12").newInstance());
                break;
            case V13:
                picoContainer.registerComponentInstance(GUIFactory.class.getName(), Class.forName("com.gisttemplates.gui.GUIFactory13").newInstance());
                break;
        }
    }

    private void initIconsAdapter(MutablePicoContainer picoContainer) throws Exception {
        switch (getVersion()) {
            case V11:
                picoContainer.registerComponentInstance(Icons.class.getName(), Class.forName("com.gisttemplates.icons.Icons11").newInstance());
                break;
            case V12:
                picoContainer.registerComponentInstance(Icons.class.getName(), Class.forName("com.gisttemplates.icons.Icons12").newInstance());
                break;
            case V13:
                picoContainer.registerComponentInstance(Icons.class.getName(), Class.forName("com.gisttemplates.icons.Icons13").newInstance());
                break;
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
