package com.gisttemplates.utils;

import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * Date: 21/03/2014
 * Time: 23:34
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class MyDataKeys {

    public static final DataKey<Project> PROJECT = DataKey.create("project");
    public static final DataKey<VirtualFile> VIRTUAL_FILE = DataKey.create("virtualFile");

}
