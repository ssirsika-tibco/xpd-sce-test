/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.wizards;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.wizards.newdatafield.AbstractNewDataFieldWizard;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.PackageOrProcessSelectionPage;
import com.tibco.xpd.wm.tasklibrary.editor.resources.TaskLibraryFactory;
import com.tibco.xpd.xpdl2.Package;

/**
 * New data field wizard for task library data fields.
 * <p>
 * Requests to add data field to package are redirected to the task library (the
 * one process in task library package).
 * 
 * @author aallway
 * @since 3.2
 */
public class NewDataFieldWizard extends AbstractNewDataFieldWizard {

    @Override
    protected EObject getEContainer(
            AbstractSpecialFolderFileSelectionPage locationPage) {
        EObject container = locationPage.getEContainer();

        if (container instanceof Package) {
            return TaskLibraryFactory.INSTANCE
                    .getTaskLibrary((Package) container);
        }

        return container;
    }

    @Override
    protected void addExtraContainerPageListeners(
            AbstractSpecialFolderFileSelectionPage containerSelectionPage) {
        // No extra controls to listen to for task library selection page.
    }

    @Override
    protected AbstractSpecialFolderFileSelectionPage getFileSelectionPage() {
        return new TaskLibraryAndProjectSelectionPage();
    }

}
