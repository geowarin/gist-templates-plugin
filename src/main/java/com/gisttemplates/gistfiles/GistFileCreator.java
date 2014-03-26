package com.gisttemplates.gistfiles;

import com.geowarin.rest.api.GistFile;
import com.gisttemplates.adapter.Icons;
import com.gisttemplates.api.GistTemplate;
import com.gisttemplates.gist.GistService;
import com.google.common.collect.ComparisonChain;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.VcsShowConfirmationOption;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiManager;
import com.intellij.util.ui.ConfirmationDialog;

import java.util.*;

/**
 * Date: 21/03/2014
 * Time: 18:53
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistFileCreator {
    private Project project;
    private VirtualFile parentDirectory;
    private final SortedSet<GistFile> filesToInsert;

    public GistFileCreator(Project project, VirtualFile parentDirectory, List<GistFile> gistFilesToInsert) {
        this.project = project;
        this.parentDirectory = parentDirectory;
        filesToInsert = new TreeSet<GistFile>(new Comparator<GistFile>() {
            @Override public int compare(GistFile gistFile1, GistFile gistFile2) {
                return ComparisonChain.start().compare(gistFile1.getRawUrl(), gistFile2.getRawUrl()).result();
            }
        });
        filesToInsert.addAll(gistFilesToInsert);
    }

    public void insertGistFiles(final GistTemplate parentGist) {
        GistService.getInstance().loadGist(parentGist);
        askToConfirmForReplacement();
        createOrReplaceFiles(parentGist);
    }

    private void createOrReplaceFiles(final GistTemplate parentGist) {
        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            @Override
            public void run() {
                for (GistFile gistFile : parentGist.getFiles()) {
                    if (filesToInsert.contains(gistFile))
                        createOrReplaceFileFromGist(gistFile);
                }
            }
        });
    }

    private void createOrReplaceFileFromGist(GistFile gistFile) {
        if (gistFile.isBinary())
            createOrReplaceBinaryFile(gistFile.getFilename(), gistFile.getContent());
        else
            createOrReplaceFileSourceFile(gistFile.getFilename(), gistFile.getContent());
    }

    private void createOrReplaceBinaryFile(String filename, String content) {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void askToConfirmForReplacement() {
        Iterator<GistFile> iterator = filesToInsert.iterator();
        while (iterator.hasNext()) {
            GistFile gistFile = iterator.next();
            VirtualFile fileToInsert = parentDirectory.findChild(gistFile.getFilename());
            if (fileToInsert != null && fileToInsert.exists()) {

                String message = String.format("File %s already exists. Would you like to replace it ?", fileToInsert.getName());
                boolean replaceFile = ConfirmationDialog.requestForConfirmation(VcsShowConfirmationOption.STATIC_SHOW_CONFIRMATION, project,
                        message, "File exists", Icons.getInstance().warning());

                if (!replaceFile)
                    iterator.remove();
            }
        }
    }

    private VirtualFile createFile(String fileName, String text) {
        PsiFile javaFile = PsiFileFactory.getInstance(project).createFileFromText(fileName, StdFileTypes.PLAIN_TEXT, text);
        PsiDirectory root = PsiManager.getInstance(project).findDirectory(parentDirectory);
        PsiFile added = (PsiFile) root.add(javaFile);
        return added.getVirtualFile();
    }

    private VirtualFile createOrReplaceFileSourceFile(String fileName, String text) {
        VirtualFile file = parentDirectory.findChild(fileName);
        if (file != null && file.exists()) {
            replaceContent(file, text);
            return file;
        }
        return createFile(fileName, text);
    }

    private void replaceContent(VirtualFile file, String text) {
        Document docFile = FileDocumentManager.getInstance().getDocument(file);
        docFile.setText(text);
    }

}
