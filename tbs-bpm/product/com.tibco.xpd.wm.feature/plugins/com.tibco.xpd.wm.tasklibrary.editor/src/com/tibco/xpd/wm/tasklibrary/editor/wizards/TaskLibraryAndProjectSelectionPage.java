/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.wm.tasklibrary.editor.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorPlugin;
import com.tibco.xpd.wm.tasklibrary.editor.internal.Messages;

/**
 * Select project and task librarywizard page. Extends
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryAndProjectSelectionPage extends
        AbstractSpecialFolderFileSelectionPage {

    private Text txtPackageFile;

    private Button btnPackageFile;

    protected IFile packageFile;

    /**
     * Project, package and process (optional) selection page used by the New
     * Wizards.
     */
    public TaskLibraryAndProjectSelectionPage() {
        super(
                Messages.TaskLibraryAndProjectSelectionPage_SelectTaskLibrary_title,
                Messages.TaskLibraryAndProjectSelectionPage_SelectTaskLibrary_longdesc);
    }

    protected String getSpecialFolderKind() {
        return TaskLibraryEditorPlugin.TASK_LIBRARY_SPECIAL_FOLDER_KIND;
    }

    @Override
    protected String getFileNameEmptyMessage() {
        return Messages.TaskLibraryAndProjectSelectionPage_YouMustEnterATaskLibraryName_message;
    }

    @Override
    protected String getFileNotExistMessage() {
        return Messages.TaskLibraryAndProjectSelectionPage_TaskLibraryNotExit_message;
    }

    @Override
    protected String getFileSelectionMessage() {
        return Messages.TaskLibraryAndProjectSelectionPage_CHooseTaskLib_label;
    }

    @Override
    protected String getFileTypeNameLabel() {
        return Messages.TaskLibraryAndProjectSelectionPage_TaskLibraryFileEnter_label;
    }

    @Override
    protected String getBrowseSpecialFolderTitle() {
        return Messages.TaskLibraryAndProjectSelectionPage_SelectWorkMgmtDialog_title;
    }

    @Override
    protected String getFileFolderLabelText() {
        return Messages.TaskLibraryAndProjectSelectionPage_WorkforceMgmtFolderSelect_label;
    }

    @Override
    protected String getNotCorrectSpecialFolderMessage() {
        return Messages.TaskLibraryAndProjectSelectionPage_NotAWorkforceFolder_message;
    }

    @Override
    protected String getPageInternalDescription() {
        return Messages.TaskLibraryAndProjectSelectionPage_SelectWorkforceMgmtFolder_longdesc;
    }

}
