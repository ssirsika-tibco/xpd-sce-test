/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.deployprojectcontributor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.amf.deployproject.api.DeployProjectProvider;
import com.tibco.amf.tools.composite.resources.CompositeResourcesActivator;
import com.tibco.xpd.daa.internal.util.BpmProjectChecksumUtils;
import com.tibco.xpd.daa.internal.util.DAANamingUtils;
import com.tibco.xpd.n2.daa.Activator;
import com.tibco.xpd.n2.daa.internal.wizards.MultiProjectDAAGenerationWithProgress;
import com.tibco.xpd.n2.daa.propertytester.DeployableBPMAssetsTester;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.n2.deployprojectcontributor.internal.Messages;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.ProjectUtil2;

/**
 * This class provides BPM contribution for the build-deploy scripts
 * configuration editor to provide studio projects and and their DAA generation
 * 
 * @author agondal
 * @since 8 Mar 2013
 */
public class BPMDeployProjectContributor extends DeployProjectProvider {

    private WorkbenchLabelProvider labelProvider;

    /**
     * @see com.tibco.amf.deployproject.api.DeployProjectProvider#generateDaa(java.lang.Object,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param obj
     * @param monitor
     * @return
     */
    @SuppressWarnings("restriction")
    @Override
    public IStatus generateDaa(IResource res, IProgressMonitor monitor) {

        if (res instanceof IProject) {
            final IProject project = (IProject) res;

            IFile daaFile = getDaaFile(project);

            final MultiProjectDAAGenerationWithProgress daaGenOperation =
                    new MultiProjectDAAGenerationWithProgress(
                            Arrays.asList(project), true);
            daaGenOperation.setReplaceQualifierWithTS(true);

            CompositeResourcesActivator.getDefault()
                    .setOpenEditorsOnChange(false);
            daaGenOperation.run(monitor);
            IStatus status = daaGenOperation.getStatus();

            if (status.getSeverity() > Status.WARNING) {
                return status;
            }
            CompositeResourcesActivator.getDefault()
                    .setOpenEditorsOnChange(true);
            if (daaFile != null && daaFile.exists()) {
                return Status.OK_STATUS;
            }
        }

        return new Status(Status.ERROR, Activator.PLUGIN_ID,
                Messages.BPMDeployProjectContributor_DAAGenerationErrorMessage);
    }

    /**
     * @see com.tibco.amf.deployproject.api.DeployProjectProvider#getDaaFile(java.lang.Object)
     * 
     * @param obj
     * @return handle to the DAA file. It may not exist.
     */
    @Override
    public IFile getDaaFile(IResource res) {

        if (res instanceof IProject) {
            /*
             * Sid XPD-7579 - Return the appropriate DAA IFile regardless of
             * whether it exists or not.
             */
            IProject project = (IProject) res;

            return project.getFile(new Path(
                    N2PENamingUtils.COMPOSITE_OUTPUTFOLDER_NAME)
                    .append(ProjectUtil.getProjectId(project) + "." //$NON-NLS-1$
                            + DAANamingUtils.DAA_FILE_EXTENSION));
        }
        return null;
    }

    /**
     * @see com.tibco.amf.deployproject.api.IDeployProjectProvider#getDeployProjectLabelProvider()
     * 
     * @return
     */
    @Override
    public ILabelProvider getDeployProjectLabelProvider() {
        if (labelProvider == null) {
            labelProvider = new WorkbenchLabelProvider();
        }
        return labelProvider;
    }

    private class BPMDeployProjectContentProvider implements
            ITreeContentProvider {

        /**
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         * 
         * @param inputElement
         * @return
         */
        @Override
        public Object[] getElements(Object inputElement) {

            if (inputElement instanceof IWorkspaceRoot) {
                DeployableBPMAssetsTester tester =
                        new DeployableBPMAssetsTester();
                List<IProject> projects = new ArrayList<IProject>();
                for (IProject project : ProjectUtil.getAllStudioProjects()) {

                    /*
                     * Only include if its BPM project with deploy-able
                     * asset(s).
                     */
                    if (tester
                            .test(project,
                                    DeployableBPMAssetsTester.PROP_HAS_DEPLOYABLE_BPM_ASSETS,
                                    new Object[] {},
                                    null)) {
                        projects.add(project);
                    }
                }
                return projects.toArray();
            }
            return null;
        }

        /**
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
         * 
         * @param parentElement
         * @return
         */
        @Override
        public Object[] getChildren(Object parentElement) {
            // TODO Auto-generated method stub
            return null;
        }

        /**
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        public Object getParent(Object element) {
            // TODO Auto-generated method stub
            return null;
        }

        /**
         * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        public boolean hasChildren(Object element) {
            // TODO Auto-generated method stub
            return false;
        }

        /**
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         * 
         */
        @Override
        public void dispose() {
            // TODO Auto-generated method stub

        }

        /**
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         * 
         * @param viewer
         * @param oldInput
         * @param newInput
         */
        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            // TODO Auto-generated method stub

        }

    }

    /**
     * @see com.tibco.amf.deployproject.api.IDeployProjectProvider#getDeployProjectContentProvider()
     * 
     * @return
     */
    @Override
    public ITreeContentProvider getDeployProjectContentProvider() {

        return new BPMDeployProjectContentProvider();
    }

    /**
     * @see com.tibco.amf.deployproject.api.DeployProjectProvider#getDependants(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public List<IResource> getDependantResources(IResource res) {

        if (res != null && res instanceof IProject) {

            try {
                Set<IProject> projects =
                        ProjectUtil2
                                .getReferencedProjectsHierarchy((IProject) res,
                                        true);
                List<IResource> referencedProjects = new ArrayList<IResource>();
                DeployableBPMAssetsTester tester =
                        new DeployableBPMAssetsTester();
                for (IProject proj : projects) {

                    // Only add if its BPM project with deploy-able asset(s).
                    if (tester
                            .test(proj,
                                    DeployableBPMAssetsTester.PROP_HAS_DEPLOYABLE_BPM_ASSETS,
                                    new Object[] {},
                                    null)) {
                        referencedProjects.add(proj);
                    }
                }
                return referencedProjects;

            } catch (CyclicDependencyException e) {
                Activator.LOG.error(e);
            }
        }
        return null;
    }

    /**
     * @see com.tibco.amf.deployproject.api.DeployProjectProvider#getContainerProject(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public IProject getContainerProject(IResource res) {
        if (res instanceof IProject) {
            return (IProject) res;
        }
        return null;
    }

    @SuppressWarnings("restriction")
    @Override
    public boolean isDaaRegenerationRequired(IResource daaSource, IFile daaFile) {
        if (daaSource instanceof IProject
                && ((IProject) daaSource).isAccessible()) {
            return BpmProjectChecksumUtils
                    .hasProjectChangedForDaa((IProject) daaSource, daaFile);
        }
        return true;
    }

}
