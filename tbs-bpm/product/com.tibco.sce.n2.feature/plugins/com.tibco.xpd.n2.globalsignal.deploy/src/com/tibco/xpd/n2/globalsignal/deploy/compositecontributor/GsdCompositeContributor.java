/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.deploy.compositecontributor;

import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.osgi.framework.Version;

import com.tibco.amf.sca.componenttype.ComponentTypeActivator;
import com.tibco.amf.sca.componenttype.CompositeModelBuilder;
import com.tibco.amf.sca.model.composite.Component;
import com.tibco.amf.sca.model.composite.ComponentProperty;
import com.tibco.amf.sca.model.composite.ComponentReference;
import com.tibco.amf.sca.model.composite.ComponentService;
import com.tibco.amf.sca.model.composite.Composite;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.daa.CompositeContributor;
import com.tibco.xpd.daa.internal.IChangeRecorder;
import com.tibco.xpd.globalSignalDefinition.util.GsdConstants;
import com.tibco.xpd.n2.daa.utils.UnprotectedWriteOperation;
import com.tibco.xpd.n2.globalsignal.deploy.internal.Messages;
import com.tibco.xpd.n2.globalsignal.resource.util.GSDModelUtil;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Contributes the GSD component to composite.
 * 
 * @author kthombar
 * @since Mar 2, 2015
 */
public class GsdCompositeContributor extends CompositeContributor {

    private static final Logger LOG = Xpdl2ResourcesPlugin.getDefault()
            .getLogger();

    /**
     * The component name.
     */
    private static final String COMPONENT_NAME = "GSD"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.daa.CompositeContributor#contributeToComposite(org.eclipse.core.resources.IProject,
     *      org.eclipse.core.resources.IContainer,
     *      com.tibco.amf.sca.model.composite.Composite,
     *      org.eclipse.emf.common.util.URI, java.lang.String, boolean,
     *      com.tibco.xpd.daa.internal.IChangeRecorder,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param project
     * @param stagingArea
     * @param composite
     * @param compositeLocation
     * @param timeStamp
     * @param replaceWithTS
     * @param changeRecorder
     * @param monitor
     * @return
     */
    @SuppressWarnings("restriction")
    @Override
    public IStatus contributeToComposite(IProject project,
            IContainer stagingArea, Composite composite, URI compositeLocation,
            String timeStamp, boolean replaceWithTS,
            IChangeRecorder changeRecorder, IProgressMonitor monitor) {

        try {

            List<IResource> gsdResources =
                    SpecialFolderUtil
                            .getAllDeepResourcesInSpecialFolderOfKind(project,
                                    GsdConstants.GSD_SPECIAL_FOLDER_KIND,
                                    GsdConstants.GSD_FILE_EXTENSION,
                                    false);

            if (monitor.isCanceled()) {
                return Status.CANCEL_STATUS;
            }

            /*
             * get the project version
             */
            String projectVersion = ProjectUtil.getProjectVersion(project);

            if (gsdResources != null && !gsdResources.isEmpty()
                    && projectVersion != null && !projectVersion.isEmpty()) {
                /*
                 * Proceed only if the project has any gsd resources.
                 */
                monitor.beginTask(Messages.GsdCompositeContributor_CreateGsdComponentMonitor_msg,
                        1);

                generateComponent(composite,
                        compositeLocation,
                        SpecialFolderUtil.getSpecialFolderOfKind(project,
                                GsdConstants.GSD_SPECIAL_FOLDER_KIND),
                        projectVersion);

                monitor.worked(1);
            }

        } catch (Exception e) {

            LOG.error(e);
        } finally {

            monitor.done();
        }

        return Status.OK_STATUS;
    }

    /**
     * @see com.tibco.xpd.daa.CompositeContributor#resetCompositeName(com.tibco.amf.sca.model.composite.Composite,
     *      org.eclipse.core.resources.IProject)
     * 
     * @param composite
     * @param project
     */
    @Override
    public void resetCompositeName(Composite composite, IProject project) {

        if (GSDModelUtil.isGsdProject(project)) {
            /*
             * for a GSD project the composite/app name will have project id
             * suffixed with "-" + <major version of the project>. so
             * constructing the new app name.
             * 
             * for instance for a project with id "com.example.firstgsdproject"
             * and version "1.0.0.qualifier" say, app name will be
             * "com.example.firstgsdproject-1"
             */
            String versionStr = ProjectUtil.getProjectVersion(project);
            Version version = Version.parseVersion(versionStr);
            int major = version.getMajor();
            String gsdFormattedName = composite.getName() + "-" + major; //$NON-NLS-1$

            composite.setName(gsdFormattedName);
        }
    }

    /**
     * Generates the Component
     * 
     * @param composite
     * @param compositeLocation
     * @param specialFolder
     *            the special folder containing the GSD resources
     * @param projectVersion
     */
    private void generateComponent(final Composite composite,
            URI compositeLocation, SpecialFolder specialFolder,
            final String projectVersion) {

        TransactionalEditingDomain editingDomain =
                TransactionUtil.getEditingDomain(composite);

        final ComponentTypeActivator ctActivator =
                ComponentTypeActivator.getDefault();

        final CompositeModelBuilder modelBuilder =
                ctActivator.getModelBuilder();

        final Implementation impl =
                ctActivator.getComponentTypeService()
                        .createImplementationFromURL(specialFolder.getFolder()
                                .getFullPath().toPortableString(),
                                compositeLocation);

        UnprotectedWriteOperation setContentsOp =
                new UnprotectedWriteOperation(editingDomain) {
                    @Override
                    protected Object doExecute() {
                        Component comp =
                                modelBuilder.createComponent(composite, impl);

                        comp.setName(COMPONENT_NAME);
                        comp.setVersion(projectVersion);

                        for (ComponentService cs : comp.getServices()) {
                            modelBuilder.promoteService(cs);
                        }
                        for (ComponentReference cs : comp.getReferences()) {
                            modelBuilder.promoteReference(cs);
                        }
                        for (ComponentProperty cs : comp.getProperties()) {
                            modelBuilder.promoteProperty(cs);
                        }
                        return comp;
                    }
                };
        setContentsOp.execute();
    }
}
