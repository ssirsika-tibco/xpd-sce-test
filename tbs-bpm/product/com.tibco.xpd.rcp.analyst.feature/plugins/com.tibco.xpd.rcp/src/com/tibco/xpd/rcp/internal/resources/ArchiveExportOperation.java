/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.resources;

import java.util.List;

import org.eclipse.core.resources.IContainer;
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
/* public */class ArchiveExportOperation extends ArchiveFileExportOperation {

    private final boolean includeSVNResources;

    /**
     * @param resources
     * @param filename
     */
    public ArchiveExportOperation(List<? extends IResource> resources,
            String filename, boolean includeSVNResources) {
        super(resources, filename);
        this.includeSVNResources = includeSVNResources;
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

        if (!(exportResource instanceof IProject) && !includeSVNResources
                && Team.isIgnoredHint(exportResource)) {
            // Resource should be ignored for archive
            return;
        }

        if (exportResource instanceof IContainer) {
            IResource[] children = null;

            try {
                if (includeSVNResources) {
                    children =
                            ((IContainer) exportResource)
                                    .members(IContainer.INCLUDE_TEAM_PRIVATE_MEMBERS);

                } else {
                    children = ((IContainer) exportResource).members();
                }
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