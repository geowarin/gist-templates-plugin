package com.gisttemplates.github;

import com.gisttemplates.adapter.GithubAdapter;
import com.jgoodies.common.base.Strings;
import org.jetbrains.plugins.github.GithubSettings;

/**
 * Date: 25/02/2014
 * Time: 21:17
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
@SuppressWarnings("UnusedDeclaration")
public class Github12 extends GithubAdapter {

    @Override
    public boolean isCredentialsDefined() {
        return Strings.isNotBlank(getLogin());
    }

    @Override
    public String getLogin() {
        return GithubSettings.getInstance().getLogin();
    }

    @Override
    public String getPassword() {
        return GithubSettings.getInstance().getPassword();
    }
}
