package com.gisttemplates.adapter;

import com.intellij.openapi.components.ServiceManager;

import javax.swing.*;

/**
 * Date: 22/03/2014
 * Time: 12:34
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public abstract class GUIFactory {

    public static GUIFactory getInstance() {
        return ServiceManager.getService(GUIFactory.class);
    }

    public abstract ListCellRenderer createGistTemplateColoredListCellRenderer();

}
