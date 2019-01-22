/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.daa.internal.deploy;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.daa.internal.util.DAANamingUtils;
import com.tibco.xpd.daa.internal.wizards.AbstractMultiProjectDAAGenerationWithProgress;
import com.tibco.xpd.daa.wizards.AbstractProjectDeployWizard;
import com.tibco.xpd.n2.daa.internal.Messages;
import com.tibco.xpd.n2.daa.internal.wizards.MultiProjectDAAGenerationWithProgress;
import com.tibco.xpd.n2.daa.propertytester.DeployableBPMAssetsTester;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Wizard for BPM Project deployment to AMX Administrator.
 * <p>
 * Sid: Much content abstracted from into {@link AbstractProjectDeployWizard}
 * during v3.6.0 work
 * 
 * @author aallway
 * @since 25 Jan 2013
 */
public class BPMProjectDeployWizard extends AbstractProjectDeployWizard {

    /**
     * @see com.tibco.xpd.daa.wizards.AbstractProjectDeployWizard#getProjectTypeName()
     * 
     * @return
     */
    @Override
    protected String getProjectTypeName() {
        return Messages.BPMProjectDeployWizard2_BPM_ProjectTypeName_label;
    }

    /**
     * @see com.tibco.xpd.daa.wizards.AbstractProjectDeployWizard#isApplicableProject(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    protected boolean isApplicableProject(IProject project) {
        DeployableBPMAssetsTester tester = new DeployableBPMAssetsTester();
        return tester.test(project,
                DeployableBPMAssetsTester.PROP_HAS_DEPLOYABLE_BPM_ASSETS,
                new Object[] {},
                null);
    }

    /**
     * @see com.tibco.xpd.daa.wizards.AbstractProjectDeployWizard#createDAAGenerationOperation(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    protected AbstractMultiProjectDAAGenerationWithProgress createDAAGenerationOperation(
            IProject project) {
        /**
         * Sid: This BPM project DAA generation operation was originally in the
         * com.tibco.xpd.n2.daa.internal.deploy.ProjectSelectionPage class
         * (which has also been abstracted down to
         * com.tibco.xpd.daa.wizards.AbstractProjectDeploySelectionPage
         */

        /*
         * Sid: There's a new facility to tell DAA gen operation to preserve the
         * DAA during cleanup phase. So we'll use that rather than overriding
         * the whole of clean up.
         */
        // Generate DAA for new selected projects.
        MultiProjectDAAGenerationWithProgress daaGenOperation =
                new MultiProjectDAAGenerationWithProgress(
                        Arrays.asList(project), true);
        return daaGenOperation;
    }

    /**
     * @see com.tibco.xpd.daa.wizards.AbstractProjectDeployWizard#getGeneratedDAAFromProject(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    protected IFile getGeneratedDAAFromProject(IProject project) {
        /**
         * Sid: This method was originally in the
         * com.tibco.xpd.n2.daa.internal.deploy.ProjectSelectionPage class
         * (which has also been abstracted down to
         * com.tibco.xpd.daa.wizards.AbstractProjectDeploySelectionPage
         */
        ArrayList<IResource> sfResources =
                SpecialFolderUtil.getResourcesInSpecialFolderOfKind(project,
                        N2PENamingUtils.COMPOSITE_MODULES_OUTPUT_KIND,
                        DAANamingUtils.DAA_FILE_EXTENSION);

        if (!sfResources.isEmpty() && sfResources.get(0) instanceof IFile) {
            return (IFile) sfResources.get(0);
        }

        return null;
    }

}
