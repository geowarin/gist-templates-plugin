package com.gisttemplates.configuration;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Date: 09/02/2014
 * Time: 16:11
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistTemplatesConfigurable implements Configurable {

    private JList accountList;
    private JButton addButton;
    private JPanel parentPanel;

    @Nls
    @Override
    public String getDisplayName() {
        return "Gist Templates";
    }

    // No override because method is not in intellij 12+
    public Icon getIcon() {
        return null;
    }

    @Override
    public boolean isModified() {
        return true;
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
    }

    @Override
    public void reset() {
    }

    @Override
    public void disposeUIResources() {
    }
}
