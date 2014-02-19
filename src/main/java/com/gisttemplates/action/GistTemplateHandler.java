package com.gisttemplates.action;

import com.intellij.codeInsight.completion.PlainPrefixMatcher;
import com.intellij.codeInsight.lookup.*;
import com.intellij.codeInsight.lookup.impl.LookupImpl;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.LiveTemplateLookupElement;
import com.intellij.codeInsight.template.impl.TemplateImpl;
import com.intellij.codeInsight.template.impl.TemplateManagerImpl;
import com.intellij.featureStatistics.FeatureUsageTracker;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Date: 19/02/2014
 * Time: 20:31
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistTemplateHandler {
    private final Project project;
    private final Editor editor;

    public GistTemplateHandler(Editor editor, Project project) {
        this.editor = editor;
        this.project = project;
    }

    public void showTemplatesLookup(@NotNull String prefix, GistTemplateEditorAction.MyActionTemplate action, List<TemplateImpl> matchingTemplates) {
        final LookupImpl lookup = getLookup(project, editor, prefix);

        lookup.addItem(new LiveTemplateAction(action), new PlainPrefixMatcher(prefix));

        for (TemplateImpl template : matchingTemplates) {
            lookup.addItem(new LiveTemplateLookupWithDescription(template), new PlainPrefixMatcher(prefix));
        }
        lookup.addLookupListener(new MyLookupAdapter());
        lookup.refreshUi(false, true);
        lookup.showLookup();
    }

    private class MyLookupAdapter extends LookupAdapter {

        public MyLookupAdapter() {
        }

        @Override
        public void itemSelected(LookupEvent event) {
            FeatureUsageTracker.getInstance().triggerFeatureUsed("codeassists.liveTemplates");
            LookupElement item = event.getItem();
            if (item instanceof LiveTemplateLookupElement) {

                LiveTemplateLookupElement liveTemplateLookupElement = (LiveTemplateLookupElement) item;
                writeCodeInEditor(liveTemplateLookupElement);

            } else if (item instanceof LiveTemplateAction) {
                LiveTemplateAction action = (LiveTemplateAction) item;
                action.action.onAction();
            }
        }

        private void writeCodeInEditor(LiveTemplateLookupElement item) {
            final TemplateImpl template = item.getTemplate();
            new WriteCommandAction(project) {
                @Override
                protected void run(Result result) throws Throwable {
                    ((TemplateManagerImpl) TemplateManager.getInstance(project)).startTemplateWithPrefix(editor, template, null, null);
                }
            }.execute();
        }
    }

    private static class TemplatesArranger extends LookupArranger {

        @Override
        public Pair<List<LookupElement>, Integer> arrangeItems(@NotNull Lookup lookup, boolean onExplicitAction) {
            LinkedHashSet<LookupElement> result = new LinkedHashSet<LookupElement>();
            List<LookupElement> items = getMatchingItems();
            for (LookupElement item : items) {
                if (item.getLookupString().startsWith(lookup.itemPattern(item))) {
                    result.add(item);
                }
            }
            result.addAll(items);
            ArrayList<LookupElement> list = new ArrayList<LookupElement>(result);
            int selected = lookup.isSelectionTouched() ? list.indexOf(lookup.getCurrentItem()) : 0;
            return new Pair<List<LookupElement>, Integer>(list, selected >= 0 ? selected : 0);
        }

        @Override
        public LookupArranger createEmptyCopy() {
            return new TemplatesArranger();
        }

    }

    private static class LiveTemplateAction extends LookupElement {

        private GistTemplateEditorAction.MyActionTemplate action;

        public LiveTemplateAction(GistTemplateEditorAction.MyActionTemplate action) {
            this.action = action;
        }

        @NotNull
        @Override
        public String getLookupString() {
            return "refresh";
        }

        public Set<String> getAllLookupStrings() {
            return Collections.singleton("");
        }

        @Override
        public void renderElement(LookupElementPresentation presentation) {
            presentation.setItemText(action.getKey());
        }
    }

    private static class LiveTemplateLookupWithDescription extends LiveTemplateLookupElement {
        private final TemplateImpl template;

        public LiveTemplateLookupWithDescription(TemplateImpl template) {
            super(template, false);
            this.template = template;
        }

        @Override
        public Set<String> getAllLookupStrings() {
            String description = template.getDescription();
            if (description == null) {
                return super.getAllLookupStrings();
            }
            return ContainerUtil.newHashSet(getLookupString(), description);
        }
    }

    private static LookupImpl getLookup(Project project, Editor editor, String prefix) {
        return (LookupImpl) LookupManager.getInstance(project)
                .createLookup(editor, LookupElement.EMPTY_ARRAY, prefix, new TemplatesArranger());
    }


    private static class LookupDecorator extends LookupImpl {

        public LookupDecorator(LookupImpl wrapped) {
            super(null, null, null);
        }
    }


}
