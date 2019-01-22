/**
 * Copyright 2006 TIBCO Software Inc.
 */

package com.tibco.xpd.wm.tasklibrary.editor.resources;

import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorPlugin;
import com.tibco.xpd.xpdl2.resources.AbstractXpdl2WorkingCopyFactory;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl.Xpdl2FileType;

/**
 * Working copy factory for XPDL format files with .xpdl extension.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryWorkingCopyFactory extends
        AbstractXpdl2WorkingCopyFactory {

    @Override
    public String getFileExtension() {
        return TaskLibraryEditorPlugin.TASKLIBRARY_FILE_EXTENSION;
    }

    @Override
    public String getSpecialFolderKind() {
        return TaskLibraryEditorPlugin.TASK_LIBRARY_SPECIAL_FOLDER_KIND;
    }

    @Override
    public Xpdl2FileType getXpdl2FileType() {
        return Xpdl2FileType.TASK_LIBRARY;
    }
}
