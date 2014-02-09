package com.gisttemplates.action;

import com.intellij.codeInsight.template.impl.ListTemplatesHandler;
import com.intellij.codeInsight.template.impl.TemplateImpl;
import com.intellij.codeInsight.template.impl.TemplateSettings;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Editor;

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

        templates.add(TemplateSettings.getInstance().getTemplate("I", "surround"));
        TemplateImpl template = new TemplateImpl("kikoo", "gists");
        template.addTextSegment("Kikoo");
        template.setDescription("generates a kikoo");
        templates.add(template);

        ListTemplatesHandler.showTemplatesLookup(editor.getProject(), editor, "", templates);
    }
}
