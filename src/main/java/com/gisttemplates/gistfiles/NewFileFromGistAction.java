package com.gisttemplates.gistfiles;

import com.gisttemplates.api.GistTemplate;
import com.gisttemplates.api.GistService;
import com.gisttemplates.utils.MyDataKeys;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.List;

/**
 * Date: 20/03/2014
 * Time: 11:33
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class NewFileFromGistAction extends AnAction {

    public void actionPerformed(AnActionEvent e) {
        DataContext dataContext = e.getDataContext();
        Project project = MyDataKeys.PROJECT.getData(dataContext);
        VirtualFile selectedDirectory = getSelectedDirectory(project, MyDataKeys.VIRTUAL_FILE.getData(dataContext));

        List<GistTemplate> gistTemplates = GistService.getInstance().fetchGistList();
        if (gistTemplates.isEmpty()) {
            return;
        }
        GistFilesDialog gistFilesDialog = new GistFilesDialog(project, gistTemplates);
        gistFilesDialog.show();

        if (gistFilesDialog.isOK()) {
            new GistFileCreator(project, selectedDirectory, gistFilesDialog.getSelectedFiles())
                    .insertGistFiles(gistFilesDialog.getSelectedTemplate());
        }

//        JBPopupFactory popupFactory = JBPopupFactory.getInstance();
//        List<String> names = Arrays.asList("kikoo", "lol");
//        popupFactory.createListPopup(new BaseListPopupStep<String>("Select a gist", names) {
//        }).showInBestPositionFor(dataContext);
    }

    private static VirtualFile getSelectedDirectory(Project project, VirtualFile virtualFile) {
        if (virtualFile == null) {
            return project.getBaseDir();
        }
        if (virtualFile.isDirectory())
            return virtualFile;

        return virtualFile.getParent();
    }


}
