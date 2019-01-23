/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.core;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNatureDescriptor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.pde.internal.core.natures.PDE;
import org.eclipse.pde.internal.ui.wizards.tools.ConvertProjectToPluginOperation;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressConstants;

import com.tibco.xpd.openspacegwtgadget.integration.OpenspaceGadgetPlugin;
import com.tibco.xpd.openspacegwtgadget.integration.internal.Messages;

/**
 * Openspace GWT Gadget Nature utilities.
 * 
 * @author aallway
 * @since 5 Dec 2012
 */
public class OpenspaceGadgetProjectConfigurator {

    private static final String OPENSPACE_GADGET_JOB_FAMILY =
            new String("OPENSPACE_GADGET_JOB_FAMILY"); //$NON-NLS-1$

    /**
     * Enable openspace GWT gadget development by adding the required nature(s)
     * to the given project.
     * 
     * @param project
     * 
     * @return <code>true</code> on success else <code>false</code>
     */
    public void enableOpenspaceGadgetDevelopment(final IProject project) {
        /*
         * Sid XPD-5020 - Pulled add PDE nature from WorkspaceJob. This caused
         * the first ever enablement of openspace gadget dev or throw
         * exceptions...
         * 
         * Java Model Exception: Java Model Status
         * [D:\AMXBPM220\DesigntimeV15\components
         * \shared\1.0.0\plugins\com.tibco.tpcl.gwt.dispatch_1.2.0.006 does not
         * exist] at
         * org.eclipse.jdt.internal.core.JavaElement.newJavaModelException
         * (JavaElement.java:505) at
         * org.eclipse.jdt.internal.core.Openable.generateInfos
         * (Openable.java:246) at
         * org.eclipse.jdt.internal.core.JavaElement.openWhenClosed
         * (JavaElement.java:518)
         * 
         * We think that this is caused by teh Java Model manager resource
         * listener being kicked into action before something else triggered by
         * setting PDE nature had been done.
         */

        try {
            IRunnableWithProgress addPDEWithProgress =
                    new IRunnableWithProgress() {

                        @Override
                        public void run(IProgressMonitor monitor)
                                throws InvocationTargetException,
                                InterruptedException {
                            try {
                                checkAndAddPDENature(project, monitor);

                            } catch (CoreException e) {
                                OpenspaceGadgetPlugin.getDefault().getLogger()
                                        .error(e,
                                                "Enable Openspace Gadget Dev failed adding PDE nature"); //$NON-NLS-1$
                                throw new InterruptedException(e.getMessage());
                            }
                        }
                    };

            PlatformUI.getWorkbench().getProgressService()
                    .busyCursorWhile(addPDEWithProgress);

        } catch (InterruptedException e) {
            return;

        } catch (InvocationTargetException e) {
            OpenspaceGadgetPlugin.getDefault().getLogger().error(e,
                    "Enable Openspace Gadget Dev failed adding PDE nature"); //$NON-NLS-1$
            return;
        }

        /*
         * Once the PDE nature has been added then add openspace gadget dev
         * nature and GWT plugin dependecies etc.
         */
        WorkspaceJob addOSNatureAndDependenciesJob = new WorkspaceJob(String
                .format(Messages.OpenspaceGadgetProjectConfigurator_EnableOpenspaceGadgetDev_title,
                        project.getName())) {

            @Override
            public IStatus runInWorkspace(IProgressMonitor monitor)
                    throws CoreException {
                try {
                    monitor.beginTask("", 2); //$NON-NLS-1$

                    /*
                     * Add openspace gadget nature and it's required nature to
                     * project (the ones that are not set yet)
                     */
                    addOpenspaceGadgetNatures(project,
                            new SubProgressMonitor(monitor, 1));

                    /*
                     * Add Plugin dependencies.
                     */
                    addPluginDependencies(project,
                            new SubProgressMonitor(monitor, 1));

                } finally {
                    monitor.done();
                }

                return new Status(IStatus.OK, OpenspaceGadgetPlugin.PLUGIN_ID,
                        Messages.OpenspaceGadgetProjectConfigurator_EnableOpenspaceGadgetDevSucess_label);
            }
        };

        addOSNatureAndDependenciesJob.setUser(true);
        addOSNatureAndDependenciesJob.belongsTo(OPENSPACE_GADGET_JOB_FAMILY);
        addOSNatureAndDependenciesJob.setProperty(
                IProgressConstants.PROPERTY_IN_DIALOG,
                Boolean.TRUE);
        addOSNatureAndDependenciesJob
                .setProperty(IProgressConstants.KEEPONE_PROPERTY, Boolean.TRUE);
        addOSNatureAndDependenciesJob
                .setRule(ResourcesPlugin.getWorkspace().getRoot());

        addOSNatureAndDependenciesJob.schedule();
    }

    /**
     * Check and add PDE nature and artifacts. This has to be done specifcally
     * because PDE does not add the manifest and build.properties jsut because
     * nature is added (this is done in theNewPLuginProjectWizard /
     * ConvertedProjectWizard.
     * 
     * @param project
     * @param subProgressMonitor
     * @throws CoreException
     */
    private void checkAndAddPDENature(IProject project,
            IProgressMonitor monitor) throws CoreException {
        try {
            monitor.beginTask("", 1); //$NON-NLS-1$
            monitor.setTaskName(
                    Messages.OpenspaceGadgetProjectConfigurator_AddingPDENatures_progessmessage);

            if (project.hasNature(PDE.PLUGIN_NATURE)) {
                monitor.worked(1);

            } else {
                /*
                 * The only way we can do this is EITHER to replicate everything
                 * that the convert-to-plugin-project does OR to use the
                 * internal operation that it uses to achieve this conversion.
                 * 
                 * As the latter is only a single class and operation then it's
                 * probably the safest.
                 */
                @SuppressWarnings("restriction")
                ConvertProjectToPluginOperation op =
                        new ConvertProjectToPluginOperation(
                                new IProject[] { project }, false);

                op.run(new SubProgressMonitor(monitor, 1));

            }

        } catch (Exception e) {
            OpenspaceGadgetPlugin.getDefault().getLogger().error(e,
                    "Error adding PDE Nature and Artefacts to nature(s) to: " //$NON-NLS-1$
                            + project.getName());

            throw new CoreException(new Status(IStatus.ERROR,
                    OpenspaceGadgetPlugin.PLUGIN_ID,
                    String.format(
                            Messages.OpenspaceGadgetProjectConfigurator_ErrorAddingPDE_message,
                            project.getName()),
                    e));
        } finally {
            monitor.done();
        }
    }

    /**
     * Add the Openspace gadget development nature and any missing require
     * natures too.
     * 
     * @param project
     * @throws CoreException
     */
    private void addOpenspaceGadgetNatures(IProject project,
            IProgressMonitor monitor) throws CoreException {
        try {
            monitor.beginTask("", 2); //$NON-NLS-1$
            monitor.setTaskName(
                    Messages.OpenspaceGadgetProjectConfigurator_AddingNatures_progessmessage);

            /* Get the current nature set. */
            IProjectDescription projectDescription = project.getDescription();

            LinkedHashSet<String> projectNatures =
                    getProjectNatures(projectDescription);
            int originalSize = projectNatures.size();

            /* Add the openspace gwt gadget nature. */
            IProjectNatureDescriptor natureDescriptor =
                    ResourcesPlugin.getWorkspace().getNatureDescriptor(
                            OpenspaceGadgetNature.OPENSPACE_GADGET_DEV_NATURE);

            /*
             * Add the required ones for openspace gwt gadget nature. (In
             * reverse order).
             */
            String[] requiredNatures = natureDescriptor.getRequiredNatureIds();

            for (int i = requiredNatures.length - 1; i >= 0; i--) {
                if (!projectNatures.contains(requiredNatures[i])) {
                    projectNatures.add(requiredNatures[i]);
                }
            }

            projectNatures.add(natureDescriptor.getNatureId());

            monitor.worked(1);

            if (projectNatures.size() > originalSize) {
                /* We've changed the set of natures so set them. */

                projectDescription.setNatureIds(projectNatures
                        .toArray(new String[projectNatures.size()]));
                project.setDescription(projectDescription,
                        new SubProgressMonitor(monitor, 1));
            } else {
                monitor.worked(1);
            }

        } catch (CoreException e) {
            OpenspaceGadgetPlugin.getDefault().getLogger().error(e,
                    "Error adding Openspace GWT Gadget nature(s) to: " //$NON-NLS-1$
                            + project.getName());

            throw new CoreException(new Status(IStatus.ERROR,
                    OpenspaceGadgetPlugin.PLUGIN_ID,
                    String.format(
                            Messages.OpenspaceGadgetProjectConfigurator_ErrorAddingNatures_message,
                            project.getName()),
                    e));
        } finally {
            monitor.done();
        }

    }

    /**
     * Add plugin dependencies to target project.
     * 
     * @param project
     * @param subProgressMonitor
     * @throws CoreException
     */
    private void addPluginDependencies(IProject project,
            IProgressMonitor monitor) throws CoreException {
        try {
            monitor.beginTask("", 1); //$NON-NLS-1$
            monitor.setTaskName(
                    Messages.OpenspaceGadgetProjectConfigurator_AddingDependencies_progessmessage);

            OpenspaceGadgetManifestConfigurator manifestConfigurator =
                    new OpenspaceGadgetManifestConfigurator();

            manifestConfigurator.addPluginDependencies(project);

            monitor.worked(1);

        } finally {
            monitor.done();
        }
    }

    /**
     * @param projectDescription
     * 
     * @return Set of current natures for project.
     */
    private LinkedHashSet<String> getProjectNatures(
            IProjectDescription projectDescription) {
        LinkedHashSet<String> natures = new LinkedHashSet<String>();

        for (String nature : projectDescription.getNatureIds()) {
            natures.add(nature);
        }

        return natures;
    }

}
