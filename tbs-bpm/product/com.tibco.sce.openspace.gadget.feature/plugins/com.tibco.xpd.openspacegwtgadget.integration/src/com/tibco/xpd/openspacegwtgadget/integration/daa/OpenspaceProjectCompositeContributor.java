/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.daa;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.xml.namespace.QName;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.xsd.util.XSDConstants;

import com.tibco.amf.sca.componenttype.ComponentTypeActivator;
import com.tibco.amf.sca.componenttype.CompositeModelBuilder;
import com.tibco.amf.sca.model.composite.Component;
import com.tibco.amf.sca.model.composite.ComponentProperty;
import com.tibco.amf.sca.model.composite.Composite;
import com.tibco.amf.sca.model.composite.CompositeFactory;
import com.tibco.amf.sca.model.extensionpoints.Extension;
import com.tibco.amf.sca.model.extensionpoints.SCAExtensionsFactory;
import com.tibco.amf.sca.model.implementationtype.webapp.StaticResource;
import com.tibco.amf.sca.model.implementationtype.webapp.WebAppFactory;
import com.tibco.amf.sca.model.implementationtype.webapp.WebAppImplementation;
import com.tibco.amf.sca.model.implementationtype.webapp.WebAppUpdate;
import com.tibco.xpd.daa.CompositeContributor;
import com.tibco.xpd.daa.internal.IChangeRecorder;
import com.tibco.xpd.openspacegwtgadget.integration.OpenspaceGadgetPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.UnprotectedWriteOperation;
import com.tibco.xpd.ui.util.NameUtil;

/**
 * Composite contribution for Openspace gadget development project DAAs
 * 
 * @author aallway
 * @since 31 Jan 2013
 */
public class OpenspaceProjectCompositeContributor extends CompositeContributor {

    private static final Logger LOG = OpenspaceGadgetPlugin.getDefault()
            .getLogger();

    public OpenspaceProjectCompositeContributor() {
    }

    /**
     * @see com.tibco.xpd.daa.CompositeContributor#contributeToComposite(org.eclipse.core.resources.IProject,
     *      org.eclipse.core.resources.IContainer,
     *      com.tibco.amf.sca.model.composite.Composite,
     *      org.eclipse.emf.common.util.URI, java.lang.String, boolean,
     *      com.tibco.xpd.daa.internal.IChangeRecorder,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param project
     * @param stagingAreaFolder
     * @param composite
     * @param compositeLocation
     * @param timeStamp
     * @param replaceWithTS
     * @param changeRecorder
     *            For our purposes this will always be null!!
     * @param monitor
     * @return
     */
    @Override
    public IStatus contributeToComposite(IProject project,
            IContainer stagingAreaFolder, Composite composite,
            URI compositeLocation, String timeStamp, boolean replaceWithTS,
            IChangeRecorder changeRecorder, IProgressMonitor monitor) {

        try {
            monitor.beginTask("Adding Openspace Gadget Component", 3); //$NON-NLS-1$

            /*
             * Add resources to staging area.
             */
            final IFolder gadgetResourceFolder =
                    stagingAreaFolder.getFolder(new Path(
                            getGadgetResourceFolderName(project)));

            IStatus status =
                    addResourcesToStagingArea(project,
                            gadgetResourceFolder,
                            stagingAreaFolder);

            if (status.getSeverity() > IStatus.WARNING) {
                return status;
            } else if (monitor.isCanceled()) {
                return Status.CANCEL_STATUS;
            }

            monitor.worked(1);

            /*
             * Create the component.
             */
            status =
                    addWebAppComponent(project,
                            composite,
                            compositeLocation,
                            gadgetResourceFolder,
                            stagingAreaFolder);

            if (status.getSeverity() > IStatus.WARNING) {
                return status;
            } else if (monitor.isCanceled()) {
                return Status.CANCEL_STATUS;
            }

            monitor.worked(1);

            return Status.OK_STATUS;

        } finally {
            monitor.done();
        }

    }

    /**
     * Add all the runtime resource for gadget to the staging area.
     * 
     * @param project
     * @param gadgetResourceFolder
     * @param stagingAreaFolder
     * 
     * @return status
     */
    private IStatus addResourcesToStagingArea(IProject project,
            final IFolder gadgetResourceFolder,
            final IContainer stagingAreaFolder) {

        try {

            if (!gadgetResourceFolder.exists()) {
                gadgetResourceFolder.create(true, true, null);
            }

            final IFolder warFolder = project.getFolder(getWARFolderName());

            IResource[] members = warFolder.members();

            for (IResource member : members) {
                /* Ignore WEB-INF folder. */
                if (member instanceof IFolder
                        && getWebInfoFolderName().equals(member.getName())
                        && warFolder.equals(member.getParent())) {
                    continue;
                }

                member.copy(gadgetResourceFolder.getFullPath().append(member
                        .getName()),
                        true,
                        new NullProgressMonitor());
            }

            /*
             * Add the static webapp implementation file web.xml file to the
             * staging folder.
             * 
             * Sid XPD-4971: Pick up the web.xml file from source project war
             * folder (in case user wants to update it) ; just fall back on the
             * one in installed source plugin if there ins't one.
             */
            boolean webInfCopied = false;

            IFolder webInfFolder = warFolder.getFolder(getWebInfoFolderName());
            if (webInfFolder.exists()) {
                IFile webXmlFile =
                        webInfFolder.getFile(getWebAppImplementationFileName());

                if (webXmlFile.exists()) {
                    webXmlFile.copy(stagingAreaFolder.getFullPath()
                            .append(webXmlFile.getName()),
                            true,
                            new NullProgressMonitor());
                    webInfCopied = true;
                }
            }

            if (!webInfCopied) {
                /*
                 * If web.xml not found in project/war/WEB-INF folder then copy
                 * the static one in source plugin.
                 */
                copyPluginFile(new Path(
                        OpenspaceGadgetPlugin.PLUGIN_DAA_RESOURCES_FOLDER).append(getWebAppImplementationFileName()),
                        stagingAreaFolder);
            }

        } catch (Exception e) {
            LOG.error(e,
                    "Error copying resources to DAA staging area for project: " //$NON-NLS-1$
                            + project.getName());

            return new Status(IStatus.ERROR, OpenspaceGadgetPlugin.PLUGIN_ID,
                    "Error copying resources to DAA staging area."); //$NON-NLS-1$
        }

        return Status.OK_STATUS;
    }

    /**
     * Add the openspace gadget web app component to the composite.
     * 
     * @param project
     * @param composite
     * @param compositeLocation
     * @param gadgetResourceFolder
     * @param stagingAreaFolder
     * @return status
     */
    private IStatus addWebAppComponent(final IProject project,
            final Composite composite, final URI compositeLocation,
            final IFolder gadgetResourceFolder,
            final IContainer stagingAreaFolder) {

        UnprotectedWriteOperation operation =
                new UnprotectedWriteOperation(
                        TransactionUtil.getEditingDomain(composite)) {

                    @Override
                    protected Object doExecute() {

                        final CompositeModelBuilder modelBuilder =
                                ComponentTypeActivator.getDefault()
                                        .getModelBuilder();

                        /*
                         * Create the component.
                         */
                        WebAppImplementation webAppImplementation =
                                WebAppFactory.eINSTANCE
                                        .createWebAppImplementation();
                        webAppImplementation
                                .setWebUri(getWebAppImplementationFileName());

                        Component component =
                                modelBuilder.createComponent(composite,
                                        webAppImplementation);

                        component.setName(String.format("%1$s_GadgetComponent", //$NON-NLS-1$
                                getProjectPsuedonym(project)));
                        component.setVersion(composite.getVersion());

                        /*
                         * Add properties.
                         */
                        addComponentProperty(component, "contextRoot", //$NON-NLS-1$
                                getProjectPsuedonym(project));

                        addComponentProperty(component, "defaultConnector", //$NON-NLS-1$
                                "httpConnector"); //$NON-NLS-1$

                        /*
                         * Add extensions.
                         */
                        component.getExtensions()
                                .add(createWebAppUpdateExtension(project,
                                        gadgetResourceFolder));

                        /*
                         * Add the component to the composite.
                         */
                        composite.getComponents().add(component);

                        return Status.OK_STATUS;
                    }

                };

        IStatus status = (IStatus) operation.execute();

        return status;
    }

    /**
     * Add the given component property to the given component. If it already
     * exists, remove the exiting one.
     * 
     * @param component
     * @param name
     * @param simpleValue
     */
    private void addComponentProperty(Component component, String name,
            String simpleValue) {
        ComponentProperty property =
                CompositeFactory.eINSTANCE.createComponentProperty();

        property.setName(name);
        property.setSimpleValue(simpleValue);
        property.setType(new QName(XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001,
                "string")); //$NON-NLS-1$

        /*
         * Remove existing component property if there is one - in favour of our
         * own ones. When we add the web app implementation these seem to get
         * added by default but not necessarily with required values.
         */
        for (Iterator iterator = component.getProperties().iterator(); iterator
                .hasNext();) {
            ComponentProperty existingProperty =
                    (ComponentProperty) iterator.next();

            if (name.equals(existingProperty.getName())) {
                iterator.remove();
            }
        }

        /* Add new one. */
        component.getProperties().add(property);

    }

    /**
     * @param project
     * @param gadgetResourceFolder
     * 
     * @return The web-app-update extension for component.
     */
    private Extension createWebAppUpdateExtension(final IProject project,
            final IFolder gadgetResourceFolder) {
        /*
         * Create the web-app-update content for the extension.
         * 
         * This will have a single static resource reference to the
         * .openspaceGadgetApplication/<projectname_gadgets folder.
         */
        WebAppUpdate webAppUpdate =
                WebAppFactory.eINSTANCE.createWebAppUpdate();

        StaticResource gadgetResources =
                WebAppFactory.eINSTANCE.createStaticResource();

        /*
         * SID XPD-4982 - OS team decided they need trailing "/" on and of
         * location as well as path.
         */
        gadgetResources.setLocation(gadgetResourceFolder.getFullPath()
                .toPortableString() + "/"); //$NON-NLS-1$

        gadgetResources.setPath("/" //$NON-NLS-1$
                + getProjectPsuedonym(project).toLowerCase() + "/"); //$NON-NLS-1$

        webAppUpdate.getStaticResources().add(gadgetResources);

        /*
         * Create the extension and add the web-app-update content.
         */
        Extension gadgetExtension =
                SCAExtensionsFactory.INSTANCE.createExtension();
        gadgetExtension.setName("GadgetContribution"); //$NON-NLS-1$
        gadgetExtension
                .setExtensionPoint("com.tibco.n2.openspace.gadget-spec.extension-point"); //$NON-NLS-1$
        gadgetExtension.setRequiredVersion("1.0.0"); //$NON-NLS-1$

        gadgetExtension.setExtensionConfiguration(webAppUpdate);

        return gadgetExtension;
    }

    /**
     * Copy a file from the {@link OpenspaceGadgetPlugin} plugin bundle to
     * target
     * 
     * @param append
     * @param targetFolder
     * @throws IOException
     * @throws CoreException
     * 
     * @return the target file.
     */
    private IFile copyPluginFile(IPath sourcePath, IContainer targetFolder)
            throws IOException, CoreException {

        InputStream sourceStream =
                FileLocator.openStream(OpenspaceGadgetPlugin.getDefault()
                        .getBundle(), sourcePath, false);

        IFile targetFile =
                targetFolder.getFile(new Path(sourcePath.lastSegment()));

        if (!targetFile.exists()) {
            targetFile.create(sourceStream, true, new NullProgressMonitor());
        } else {
            targetFile.setContents(sourceStream,
                    true,
                    false,
                    new NullProgressMonitor());

        }

        return targetFile;
    }

    /**
     * @return The source project WAR folder name.
     */
    private String getWARFolderName() {
        return "war"; //$NON-NLS-1$
    }

    /**
     * @return The source project WEB-INF folder name.
     */
    private String getWebInfoFolderName() {
        return "WEB-INF"; //$NON-NLS-1$
    }

    /**
     * @param project
     * 
     * @return The name of the folder in which all runtime gadget resource files
     *         are stored.
     */
    public static String getGadgetResourceFolderName(IProject project) {
        return getProjectPsuedonym(project).toLowerCase() + "_gadgets"; //$NON-NLS-1$
    }

    /**
     * @param project
     * @return The pseudonym for the project (to be used for composite/daa file
     *         naming.
     */
    private static String getProjectPsuedonym(IProject project) {
        String nmToken = NameUtil.getInternalName(project.getName(), false);
        return nmToken;
    }

    /**
     * @return Name of the web app implementation file name,
     */
    private String getWebAppImplementationFileName() {
        return "web.xml"; //$NON-NLS-1$
    }

}
