package com.gisttemplates.gui;

import com.gisttemplates.adapter.GUIFactory;
import com.gisttemplates.adapter.Icons;
import com.gisttemplates.api.GistTemplate;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.SimpleColoredText;
import com.intellij.ui.SimpleTextAttributes;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Date: 22/03/2014
 * Time: 12:35
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
@SuppressWarnings("UnusedDeclaration")
public class GUIFactory11 extends GUIFactory {

    @Override public ListCellRenderer createGistTemplateColoredListCellRenderer() {
        return new GistTemplateColoredListCellRendererWrapper();
    }

    private class GistTemplateColoredListCellRendererWrapper extends ColoredListCellRendererWrapper<GistTemplate> {
        @Override
        protected void doCustomize(JList list, GistTemplate template, int index, boolean selected, boolean hasFocus) {
            append(new SimpleColoredText(template.getFilename(), new SimpleTextAttributes(SimpleTextAttributes.STYLE_PLAIN, Color.BLACK)));
            setToolTipText(template.getDescription());
            if (template.isStarred())
                setIcon(Icons.getInstance().favorite());
            else
                setIcon(Icons.getInstance().github());
        }
    }


    public abstract class ColoredListCellRendererWrapper<T> extends ColoredListCellRenderer {
        @Override
        protected final void customizeCellRenderer(JList list, Object value, int index, boolean selected, boolean hasFocus) {
            @SuppressWarnings("unchecked") final T t = (T)value;
            doCustomize(list, t, index, selected, hasFocus);
        }

        protected abstract void doCustomize(JList list, T value, int index, boolean selected, boolean hasFocus);

        public void append(@NotNull SimpleColoredText text) {
            int length = text.getTexts().size();
            for (int i = 0; i < length; i++) {
                String fragment = text.getTexts().get(i);
                SimpleTextAttributes attributes = text.getAttributes().get(i);
                append(fragment, attributes);
            }
        }
    }
}
