/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.wizards;

import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractXpdlProjectSelectPage;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorPlugin;
import com.tibco.xpd.wm.tasklibrary.editor.internal.Messages;

/**
 * Task Library's project selection page.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryProjectSelectPage extends AbstractXpdlProjectSelectPage {

    public TaskLibraryProjectSelectPage() {
        super(
                Messages.TaskLibraryProjectSelectPage_TaskLibraryWizard_title,
                Messages.TaskLibraryProjectSelectPage_TaskLibraryWizard_longdesc);
    }

    @Override
    protected String getBaseFileName() {
        return Messages.TaskLibraryProjectSelectPage_DefaultBaseFileName;
    }

    @Override
    protected String getFileExtension() {
        return "." + TaskLibraryEditorPlugin.TASKLIBRARY_FILE_EXTENSION; //$NON-NLS-1$
    }

    @Override
    protected String getSpecialFolderKind() {
        return TaskLibraryEditorPlugin.TASK_LIBRARY_SPECIAL_FOLDER_KIND;
    }

    @Override
    protected String getFileFolderLabelText() {
        return Messages.TaskLibraryProjectSelectPage_WorkforceMgmtFolder_label;
    }

    @Override
    protected String getPageInternalDescription() {
        return Messages.TaskLibraryProjectSelectPage_SelectTaskLibraryFolderAndFile_longdesc;
    }

    @Override
    protected String getNotCorrectSpecialFolderMessage() {
        return Messages.TaskLibraryProjectSelectPage_NotATaskLibFolder_longdesc;
    }

    @Override
    protected String getBrowseSpecialFolderTitle() {
        return Messages.TaskLibraryProjectSelectPage_SelectTaskLibFolder;
    }

}
