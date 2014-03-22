package com.gisttemplates.gui;

import com.gisttemplates.adapter.GUIFactory;
import com.gisttemplates.adapter.Icons;
import com.gisttemplates.api.GistTemplate;
import com.intellij.ui.ColoredListCellRendererWrapper;
import com.intellij.ui.JBColor;
import com.intellij.ui.SimpleColoredText;
import com.intellij.ui.SimpleTextAttributes;

import javax.swing.*;

/**
 * Date: 22/03/2014
 * Time: 12:36
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
@SuppressWarnings("UnusedDeclaration")
public class GUIFactory13 extends GUIFactory {

    @Override public ListCellRenderer createGistTemplateColoredListCellRenderer() {
        return new GistTemplateColoredListCellRendererWrapper();
    }

    private static class GistTemplateColoredListCellRendererWrapper extends ColoredListCellRendererWrapper<GistTemplate> {
        @Override
        protected void doCustomize(JList list, GistTemplate template, int index, boolean selected, boolean hasFocus) {
            append(new SimpleColoredText(template.getFilename(), new SimpleTextAttributes(SimpleTextAttributes.STYLE_PLAIN, JBColor.BLACK)));
            setToolTipText(template.getDescription());
            if (template.isStarred())
                setIcon(Icons.getInstance().favorite());
            else
                setIcon(Icons.getInstance().github());
        }
    }
}
