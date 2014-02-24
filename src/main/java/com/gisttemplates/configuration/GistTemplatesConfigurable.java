package com.gisttemplates.configuration;

import com.gisttemplates.GistTemplatesApplication;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.github.util.GithubSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Date: 09/02/2014
 * Time: 16:11
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistTemplatesConfigurable implements Configurable {

    private JPanel parentPanel;
    private JCheckBox useMyGithubAccountCheckBox;
    private final GistTemplatesSettings gistTemplatesSettings;
    private boolean isModified;

    private static final Logger LOG = Logger.getInstance(GistTemplatesConfigurable.class.getName());


    public GistTemplatesConfigurable() {
        gistTemplatesSettings = GistTemplatesSettings.getInstance();
        useMyGithubAccountCheckBox.addActionListener(new UseMyAccountActionListener());
        init(gistTemplatesSettings);
    }

    private void init(GistTemplatesSettings settings) {
        useMyGithubAccountCheckBox.setSelected(settings.isUseGithubAccount());
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Gist Templates";
    }

    @Override
    public boolean isModified() {
        return isModified;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return parentPanel;
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "preferences.gisttemplates";
    }

    @Override
    public void apply() throws ConfigurationException {
        gistTemplatesSettings.setUseGithubAccount(useMyGithubAccountCheckBox.isSelected());
        GistTemplatesApplication.getInstance().invalidateCaches();
    }

    @Override
    public void reset() {
    }

    @Override
    public void disposeUIResources() {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GistTemplatesConfigurable");
        frame.setContentPane(new GistTemplatesConfigurable().parentPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private class UseMyAccountActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            isModified = true;

            if (useMyGithubAccountCheckBox.isSelected()) {

                if (GithubSettings.getInstance().isAuthConfigured()) {
                    LOG.info("GithubSettings are not set");
                    Messages.showErrorDialog(parentPanel, "Github settings are not defined", "Login Failure");

                    useMyGithubAccountCheckBox.setSelected(false);
                    isModified = false;
                } else {
                    GistTemplatesApplication.getInstance().addCacheForGithubUser();
                }
            }
        }
    }
}
