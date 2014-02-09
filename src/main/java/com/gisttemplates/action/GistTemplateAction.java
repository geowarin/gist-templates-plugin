package com.gisttemplates.action;

import com.gisttemplates.GistTemplateApplication;
import com.intellij.codeInsight.template.impl.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 09/02/2014
 * Time: 16:01
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistTemplateAction extends AnAction {


    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getData(LangDataKeys.EDITOR);
        List<TemplateImpl> templates = new ArrayList<TemplateImpl>();

        GistTemplateApplication application = ApplicationManager.getApplication().getComponent(GistTemplateApplication.class);
        List<Gist> allGists = application.getAllGists();
        for (Gist gist : allGists) {
            TemplateImpl template = createTemplateFromGist(gist);
            templates.add(template);
        }

        ListTemplatesHandler.showTemplatesLookup(editor.getProject(), editor, "", templates);
    }

    private TemplateImpl createTemplateFromGist(Gist gist) {
        GistFile firstFile = gist.getFiles().values().iterator().next();
        TemplateImpl template = new TemplateImpl(firstFile.getFilename(), "gists");
        template.addTextSegment(firstFile.getContent());
        template.setDescription(gist.getDescription());
        return template;
    }
}
