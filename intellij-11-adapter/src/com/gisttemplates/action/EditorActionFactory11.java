package com.gisttemplates.action;

import com.gisttemplates.adapter.EditorActionFactory;
import com.gisttemplates.api.GistService;
import com.gisttemplates.api.GistTemplate;
import com.gisttemplates.api.LazyGistTemplate;
import com.intellij.codeInsight.template.impl.ListTemplatesHandler;
import com.intellij.codeInsight.template.impl.TemplateImpl;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 27/03/2014
 * Time: 07:07
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class EditorActionFactory11 extends EditorActionFactory {

    @Override public EditorActionHandler createActionHandler() {
        return new GistTemplateActionHandler();
    }

    private class GistTemplateActionHandler extends EditorActionHandler {
        @Override
        public void execute(Editor editor, DataContext dataContext) {
            ListTemplatesHandler.showTemplatesLookup(editor.getProject(), editor, "", createGistTemplatesList());
        }

        private List<TemplateImpl> createGistTemplatesList() {
            List<TemplateImpl> templates = new ArrayList<TemplateImpl>();
            for (GistTemplate gist : GistService.getInstance().fetchGistList()) {
                templates.add(new LazyGistTemplate(gist));
            }
            return templates;
        }
    }
}
