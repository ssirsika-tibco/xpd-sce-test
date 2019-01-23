/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.precompile;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.bom.xsdtransform.utils.Bom2XsdUtil;
import com.tibco.xpd.resources.precompile.AbstractPreCompileContributor;
import com.tibco.xpd.resources.precompile.PreCompileContributorManager;

/**
 * Implementation class that contributes bom2xsd folder to the pre compiled
 * folder when a project is flagged as a pre compiled project.
 * 
 * @author bharge
 * @since 28 Oct 2014
 */
public class Bom2XsdPreCompileContributor extends AbstractPreCompileContributor {

    public static final String CONTRIBUTOR_ID = "bom2xsdPrecompileContributor"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.n2.resources.ui.PreCompileContributor#prepareForPrecompile(org.eclipse.core.resources.IProject,
     *      org.eclipse.core.resources.IFolder,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param project
     * @param preCompileFolder
     * @param progressMonitor
     * @return
     */
    @Override
    public IStatus precompile(IProject project, IFolder preCompileFolder,
            IProgressMonitor progressMonitor) throws CoreException {

        try {

            progressMonitor.beginTask("", 0); //$NON-NLS-1$
            return Status.OK_STATUS;
        } finally {

            progressMonitor.done();
        }
    }

    /**
     * @see com.tibco.xpd.resources.precompile.AbstractEnablePreCompileContributor#getSourceResource(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    public IResource getSourceResource(IProject project) {
        /*
         * Return the source resource even if does not exist - caller may still
         * be interested in what it shoudl be.
         */
        IFolder bom2xsdOutputFolder =
                Bom2XsdUtil.getXsdOutputFolder(project, false, true);
        return bom2xsdOutputFolder;
    }

    /**
     * @see com.tibco.xpd.resources.precompile.AbstractEnablePreCompileContributor#getPrecompileTarget(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    public IPath getPrecompileTarget(IProject project) {

        IFolder precompileFolder =
                project.getFolder(PreCompileContributorManager.getInstance().PRECOMPILE_OUTPUTFOLDER_NAME);

        IFolder precompileBom2XsdFolder =
                precompileFolder
                        .getFolder(Bom2XsdUtil.PRECOMPILE_BOM_2_XSD_FOLDER);

        return precompileBom2XsdFolder.getProjectRelativePath();
    }

}
