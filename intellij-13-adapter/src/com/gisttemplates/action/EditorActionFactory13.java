package com.gisttemplates.action;

import com.gisttemplates.adapter.EditorActionFactory;
import com.intellij.codeInsight.template.impl.ListTemplatesHandler;
import com.intellij.codeInsight.template.impl.TemplateImpl;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 27/03/2014
 * Time: 07:07
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class EditorActionFactory13 extends EditorActionFactory {

    @Override public EditorActionHandler createActionHandler(List<TemplateImpl> gistTemplates) {
        return new GistTemplateActionHandler(gistTemplates);
    }

    private class GistTemplateActionHandler extends EditorActionHandler {
        private final List<TemplateImpl> templates;

        public GistTemplateActionHandler(List<TemplateImpl> templates) {
            this.templates = templates;
        }

        @Override protected void doExecute(Editor editor, @Nullable Caret caret, DataContext dataContext) {
            Map<TemplateImpl, String> template2Args = new HashMap<TemplateImpl, String>();
            for (TemplateImpl template : templates) {
                template2Args.put(template, template.getDescription());
            }
            ListTemplatesHandler.showTemplatesLookup(editor.getProject(), editor, template2Args);
        }
    }
}
