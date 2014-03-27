package com.gisttemplates.action;

import com.gisttemplates.adapter.EditorActionFactory;
import com.intellij.codeInsight.template.impl.ListTemplatesHandler;
import com.intellij.codeInsight.template.impl.TemplateImpl;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

import java.util.List;

/**
 * Date: 27/03/2014
 * Time: 07:07
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class EditorActionFactory11 extends EditorActionFactory {

    @Override public EditorActionHandler createActionHandler(List<TemplateImpl> gistTemplates) {
        return new GistTemplateActionHandler(gistTemplates);
    }

    private class GistTemplateActionHandler extends EditorActionHandler {
        private final List<TemplateImpl> templates;

        public GistTemplateActionHandler(List<TemplateImpl> templates) {
            this.templates = templates;
        }

        @Override
        public void execute(Editor editor, DataContext dataContext) {
            ListTemplatesHandler.showTemplatesLookup(editor.getProject(), editor, "", templates);
        }
    }
}
