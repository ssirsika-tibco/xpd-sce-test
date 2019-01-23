/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.om.transform.de.component;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.StructuredSelection;

import com.tibco.amf.sca.componenttype.ComponentTypeActivator;
import com.tibco.amf.sca.componenttype.CompositeModelBuilder;
import com.tibco.amf.sca.model.composite.Component;
import com.tibco.amf.sca.model.composite.ComponentProperty;
import com.tibco.amf.sca.model.composite.ComponentReference;
import com.tibco.amf.sca.model.composite.ComponentService;
import com.tibco.amf.sca.model.composite.Composite;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.n2.model.common.DEImplementation;
import com.tibco.xpd.daa.CompositeContributor;
import com.tibco.xpd.daa.internal.IChangeRecorder;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.n2.daa.utils.UnprotectedWriteOperation;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.om.transform.de.TransformDEActivator;
import com.tibco.xpd.om.transform.de.actions.ExportToDEAction;
import com.tibco.xpd.om.transform.de.internal.Messages;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;

/**
 * @author kupadhya
 * 
 */
public class DECompositeContributor extends CompositeContributor {

    @Override
    public IStatus contributeToComposite(IProject project,
            IContainer stagingArea, Composite composite, URI compositeLocation,
            final String timeStamp, final boolean replaceWithTS,
            final IChangeRecorder changeRecorder, IProgressMonitor monitor) {

        try {
            long beforeTimeMillis = System.currentTimeMillis();
            
            List<IFile> list = getOMForDAAGen(project);

            IStatus status = generateComponent(composite,
                    compositeLocation,
                    stagingArea,
                    list, // deModules,
                    project,
                    timeStamp,
                    monitor);

            long afterTimeMillis = System.currentTimeMillis();
            long timeTaken = afterTimeMillis - beforeTimeMillis;
            System.err
                    .println("*********The time taken to create a DE Component "
                            + timeTaken + " milliseconds");

            return status;
            
        } catch (CoreException e) {
            return Status.OK_STATUS; /*
                                      * Sid - Always Used to do return OK, so
                                      * didn't want to change it in case I broke
                                      * something.
                                      */
        }
    }

    private List<IFile> getOMForDAAGen(IProject project) throws CoreException {
        List<SpecialFolder> sFolders =
                SpecialFolderUtil.getAllSpecialFoldersOfKind(project,
                        OMUtil.OM_SPECIAL_FOLDER_KIND);
        List<IFile> omForDAAFiles = new ArrayList<IFile>();

        for (final SpecialFolder sFolder : sFolders) {
            if (sFolder.getFolder() != null && sFolder.getFolder().exists()) {

                IResource[] members = sFolder.getFolder().members();
                for (IResource resource : members) {
                    if (resource instanceof IFile
                            && OMUtil.OM_FILE_EXTENSION.equals(resource
                                    .getFileExtension())) {
                        IFile file =
                                buildOM((IFile) resource, sFolder, project);
                        omForDAAFiles.add(file);
                    }
                }
            }

        }
        return omForDAAFiles;
    }

    /**
     * @param resource
     * @param srcFolder
     */
    protected IFile buildOM(IFile resource, SpecialFolder srcFolder,
            IProject project) {
        if (null != resource) {

            IFolder outPath = getOutputPath(project);

            /* Get the special folder relative path of the given resource */
            IPath path =
                    SpecialFolderUtil.getSpecialFolderRelativePath(srcFolder,
                            resource);
            /*
             * If this is project relative path then this resource is not
             * contained in the given special folder
             */
            if (path != null && !path.equals(resource.getProjectRelativePath())) {
                path =
                        path.removeFileExtension()
                                .addFileExtension(N2PENamingUtils.DE_FILEEXTENSION);
            }

            try {
                StructuredSelection selection =
                        new StructuredSelection(resource);
                IFile outFile =
                        resource.getProject().getFile(outPath
                                .getProjectRelativePath().append(path));
                ExportToDEAction exportToDEAction =
                        new ExportToDEAction(true, true);
                exportToDEAction.selectionChanged(selection);

                /*
                 * If the selected resource is valid (ie no validation problems)
                 * and there aren't more than one OM model with the same special
                 * folder relative path (duplicate resource) then build it,
                 * otherwise remove any of its derived resource
                 */
                if (exportToDEAction.isValid()) {
                    exportToDEAction.setOutputPath(outFile.getFullPath()
                            .toPortableString());

                    exportToDEAction.run();
                    return outFile;
                }
            } catch (Exception e) {
                if (!path.toFile().exists()) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    /**
     * @param project
     * @return
     */
    private IFolder getOutputPath(IProject project) {
        return N2PENamingUtils.getModuleOutputFolder(project, false);
    }

    public IStatus generateComponent(final Composite composite,
            final URI compositeLocation, final IContainer stageFolder,
            final List<IFile> deModules, final IProject project,
            final String timeStamp, IProgressMonitor monitor) {

        try {
            if (!deModules.isEmpty()) {
                monitor.beginTask(Messages.DECompositeContributor_AddingOrgModels_messages,
                        deModules.size());

                final ComponentTypeActivator ctActivator =
                        ComponentTypeActivator.getDefault();
                final CompositeModelBuilder modelBuilder =
                        ctActivator.getModelBuilder();
                TransactionalEditingDomain editingDomain =
                        TransactionUtil.getEditingDomain(composite);

                for (final IFile file : deModules) {
                    if (monitor.isCanceled()) {
                        return Status.CANCEL_STATUS;
                    }

                    if (file.exists()) {
                        addDeModule(composite,
                                file,
                                compositeLocation,
                                project,
                                timeStamp,
                                ctActivator,
                                modelBuilder,
                                editingDomain,
                                SubProgressMonitorEx
                                        .createSubTaskProgressMonitor(monitor,
                                                1));
                    }
                }
            }

            return Status.OK_STATUS;

        } finally {
            monitor.done();
        }
    }

    /**
     * @param composite
     * @param file
     * @param compositeLocation
     * @param project
     * @param timeStamp
     * @param ctActivator
     * @param modelBuilder
     * @param editingDomain
     * @param monitor
     */
    private void addDeModule(final Composite composite, final IFile file,
            final URI compositeLocation, final IProject project,
            final String timeStamp, final ComponentTypeActivator ctActivator,
            final CompositeModelBuilder modelBuilder,
            TransactionalEditingDomain editingDomain, IProgressMonitor monitor) {

        try {
            monitor.beginTask(file.getName(), 1);

            final String deLoc = file.getFullPath().toPortableString();
            final Implementation impl =
                    ctActivator.getComponentTypeService()
                            .createImplementationFromURL(deLoc,
                                    compositeLocation);
            UnprotectedWriteOperation setContentsOp =
                    new UnprotectedWriteOperation(editingDomain) {
                        @Override
                        protected Object doExecute() {
                            /* update implementation model version */
                            DEImplementation deImplemetation =
                                    (DEImplementation) impl;
                            String origVersion = deImplemetation.getVersion();
                            String updatedBundleVersion =
                                    PluginManifestHelper
                                            .getUpdatedBundleVersion(origVersion,
                                                    timeStamp);
                            deImplemetation.setVersion(updatedBundleVersion);
                            /* update implementation model version */
                            Component comp =
                                    modelBuilder.createComponent(composite,
                                            impl);

                            comp.setName(getName(file));
                            comp.setVersion(getOMImplementationVersion(project,
                                    file,
                                    timeStamp));

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

        } finally {
            monitor.done();
        }
    }

    /**
     * Get the name of the component from the file. The file's persisted
     * property will be checked for the details and if not found then the file
     * name will be used to generate the name.
     * 
     * @param file
     * @return
     */
    private String getName(IFile file) {
        String name = null;
        if (file != null) {
            try {
                name =
                        file.getPersistentProperty(TransformDEActivator.MODEL_NAME);
            } catch (CoreException e) {
                // Do nothing
            }

            if (name == null) {
                /* Default to using the file name */
                name = file.getName();

                if (name.indexOf('.') > 0) {
                    name = name.substring(0, name.indexOf('.'));
                    name = name.replaceAll("[\\W_]", ""); //$NON-NLS-1$ //$NON-NLS-2$
                }
            }
        }
        return name;
    }

    /**
     * @param project
     * @param file
     * @return
     */
    private String getOMImplementationVersion(final IProject project,
            final IFile file, final String timeStamp) {
        String omVersion = DECompositeUtil.getOMModelVersion(file, project);
        return omVersion;
    }

}
