/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.importexport;

import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.team.core.Team;
import org.eclipse.ui.internal.wizards.datatransfer.ArchiveFileExportOperation;

import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Extended {@link ArchiveFileExportOperation} to stop the archiving of
 * resources that are derived or set to ignored under the Team preferences.
 */
@SuppressWarnings("restriction")
public class ArchiveExportOperation extends ArchiveFileExportOperation {

    /**
     * @param resources
     * @param filename
     */
    public ArchiveExportOperation(List resources, String filename) {
        super(resources, filename);
    }

    /**
     * @see org.eclipse.ui.internal.wizards.datatransfer.ArchiveFileExportOperation#exportResource(org.eclipse.core.resources.IResource,
     *      int)
     * 
     * @param exportResource
     * @param leadupDepth
     * @throws InterruptedException
     */
    @Override
    protected void exportResource(IResource exportResource, int leadupDepth)
            throws InterruptedException {

        if (!(exportResource instanceof IProject)
                && Team.isIgnoredHint(exportResource)) {
            // Resource should be ignored for archive
            return;
        }

        if (exportResource instanceof IFolder) {
            IResource[] children = null;

            try {
                children = ((IContainer) exportResource).members();
            } catch (CoreException e) {
                // this should never happen because an #isAccessible check
                // is done before #members is invoked
                addError(String.format(Messages.StudioExportProjectWizard_errorExportingProject_error_shortdesc,
                        exportResource.getFullPath()),
                        e);
            }

            for (int i = 0; i < children.length; i++) {
                exportResource(children[i], leadupDepth + 1);
            }

        } else {
            super.exportResource(exportResource, leadupDepth);
        }
    }
}