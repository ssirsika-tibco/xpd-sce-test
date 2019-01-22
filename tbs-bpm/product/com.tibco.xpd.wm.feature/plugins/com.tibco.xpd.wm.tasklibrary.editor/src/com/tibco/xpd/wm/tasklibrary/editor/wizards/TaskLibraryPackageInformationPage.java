/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.wizards;

import com.tibco.xpd.processeditor.xpdl2.wizards.AbstractXpdlPackageInformationPage;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorPlugin;
import com.tibco.xpd.wm.tasklibrary.editor.internal.Messages;

/**
 * Task Library Package information wizard page
 * @author aallway
 * @since 3.2 
 */
public class TaskLibraryPackageInformationPage extends
        AbstractXpdlPackageInformationPage {

    @Override
    protected String getDefaultPackageName() {
        return Messages.TaskLibraryDefaultName_label;
    }

    /* (non-Javadoc)
     * @see com.tibco.xpd.processeditor.xpdl2.wizards.AbstractXpdlPackageInformationPage#getPackageFileExtension()
     */
    @Override
    protected String getPackageFileExtension() {
        return "."+TaskLibraryEditorPlugin.TASKLIBRARY_FILE_EXTENSION; //$NON-NLS-1$
    }

    @Override
    protected String getPackageUIHeaderInfoLabel() {
        return Messages.TaskLibraryPackageInformationPage_TaskLibHeaderInfo_label;
    }

    @Override
    protected String getPackageUILabelNameLabel() {
        return Messages.TaskLibraryPackageInformationPage_TaskLibraryLabel_label;
    }

    @Override
    protected String getPackageUINameGroupLabel() {
        return Messages.TaskLibraryPackageInformationPage_TaskLibraryGroup_name;
    }

    @Override
    protected String getPackageUINameLabel() {
        return Messages.TaskLibraryPackageInformationPage_TaskLibraryName_label;
    }

}
