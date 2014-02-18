package com.gisttemplates.configuration;

import com.intellij.openapi.components.*;
import com.intellij.openapi.diagnostic.Logger;
import org.jdom.Element;
import org.jetbrains.annotations.Nullable;

/**
 * Date: 18/02/2014
 * Time: 18:36
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
@State(
        name = "GistTemplatesSettings",
        storages = {
                @Storage(
                        file = StoragePathMacros.APP_CONFIG + "/gisttemplates_settings.xml"
                )}
)
public class GistTemplatesSettings implements PersistentStateComponent<Element> {

    private static final Logger LOG = Logger.getInstance(GistTemplatesSettings.class.getName());
    private static final String GITHUB_TEMPLATES_SETTINGS_TAG = "githubTemplatesSettings";
    private static final String USE_GITHUB_ACCOUNT = "useGithubAccount";
    private boolean useGithubAccount;

    public static GistTemplatesSettings getInstance() {
        return ServiceManager.getService(GistTemplatesSettings.class);
    }


    @Nullable
    @Override
    public Element getState() {
        final Element element = new Element(GITHUB_TEMPLATES_SETTINGS_TAG);
        element.setAttribute(USE_GITHUB_ACCOUNT, String.valueOf(isUseGithubAccount()));
        return element;
    }

    @Override
    public void loadState(Element state) {
        try {
            setUseGithubAccount(Boolean.valueOf(state.getAttributeValue(USE_GITHUB_ACCOUNT)));
        } catch (Exception e) {
            LOG.error("Error happened while loading github settings: " + e);
        }
    }

    public boolean isUseGithubAccount() {
        return useGithubAccount;
    }

    public void setUseGithubAccount(boolean useGithubAccount) {
        this.useGithubAccount = useGithubAccount;
    }
}

