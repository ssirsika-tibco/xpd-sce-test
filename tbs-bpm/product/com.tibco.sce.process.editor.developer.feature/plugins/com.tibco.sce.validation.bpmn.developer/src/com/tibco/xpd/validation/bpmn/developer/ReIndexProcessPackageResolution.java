/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Resolution simply forces a re-index of the XPDL file that contains a given
 * resource.
 * 
 * @author aallway
 * @since 30 Jan 2012
 */
public class ReIndexProcessPackageResolution extends
        AbstractWorkingCopyResolution {

    /**
     * 
     */
    public ReIndexProcessPackageResolution() {
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @SuppressWarnings("restriction")
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target != null && marker != null && marker.exists()) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(target);

            if (wc != null) {
                /* Force re-index of file. */
                IndexerServiceImpl service =
                        (IndexerServiceImpl) XpdResourcesPlugin.getDefault()
                                .getIndexerService();

                if (service != null) {
                    service.index(wc, true);
                }

                /*
                 * Touch to force a revalidate because the resolution does not
                 * touch the original file it would not normally get
                 * revalidated.
                 * 
                 * Also, in theory, the file should not be in a dirty state when
                 * we do this because IF the user had made some change THEN the
                 * file will have been re-indexed anyway!
                 */
                IResource resource = marker.getResource();
                if (resource != null && resource.exists()) {
                    try {
                        resource.touch(new NullProgressMonitor());

                    } catch (CoreException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        return null;
    }
}
