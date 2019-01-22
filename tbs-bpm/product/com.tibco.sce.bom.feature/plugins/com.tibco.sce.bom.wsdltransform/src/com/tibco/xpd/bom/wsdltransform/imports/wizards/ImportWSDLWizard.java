/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.wsdltransform.imports.wizards;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.wst.wsdl.util.WSDLResourceImpl;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.wsdltransform.Activator;
import com.tibco.xpd.bom.wsdltransform.api.WSDLTransformUtil;
import com.tibco.xpd.bom.wsdltransform.internal.Messages;
import com.tibco.xpd.bom.xsdtransform.utils.NamespaceURIToJavaPackageMapper;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.ui.dialogs.SpecialFolderContainerGroup;
import com.tibco.xpd.ui.importexport.importwizard.AbstractImportWizard;
import com.tibco.xpd.ui.importexport.importwizard.pages.FilteredFSElement;

/**
 * Import wsdl wizard to convert a WSDL file to BOM model.
 * 
 * @author glewis
 * 
 */
public class ImportWSDLWizard extends AbstractImportWizard implements
        IImportWizard {

    private static final String IMG_LOC = "icons/BOMImport.png"; //$NON-NLS-1$

    /**
     * Import UML Wizard.
     */
    public ImportWSDLWizard() {
        setWindowTitle(Messages.ImportWSDLWizard_importWSDLWizard_title);
        setDefaultPageImageDescriptor(Activator
                .imageDescriptorFromPlugin(Activator.PLUGIN_ID, IMG_LOC));
    }

    @Override
    protected void performOperation(IProgressMonitor monitor)
            throws CoreException, InterruptedException {

        final List<FilteredFSElement> importResources = getSelectedResources();
        OverwriteStatus overwriteStatus = getOverwriteStatus();
        final IContainer destContainer = getDestinationContainer();
        if (importResources != null && !importResources.isEmpty()
                && destContainer != null) {
            monitor.beginTask(Messages.ImportWSDLWizard_progress_label,
                    importResources.size() * 6);

            // find out which files already reside in the special folder
            List<String> existingFilesNames = new ArrayList<String>();
            IFolder folder =
                    destContainer.getWorkspace().getRoot()
                            .getFolder(destContainer.getFullPath());
            IProject project = folder.getProject();
            folder.refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
            for (IResource resource : folder.members()) {
                existingFilesNames.add(resource.getName());
            }

            for (FilteredFSElement elem : importResources) {
                if (monitor.isCanceled()) {
                    throw new OperationCanceledException();
                }

                /*
                 * If overwrite dialog was shown and NO selected then we need to
                 * reset it to the default value, otherwise no further files
                 * will be processed
                 */
                if (overwriteStatus == OverwriteStatus.NO) {
                    overwriteStatus = OverwriteStatus.FILE;
                }

                if (elem.getFileSystemObject() instanceof File) {
                    File file = (File) elem.getFileSystemObject();
                    monitor.subTask(file.getName());

                    final IFile diagramFile =
                            destContainer.getFile(new Path(
                                    getDestinationFileName(project, file)));

                    if (diagramFile.exists()
                            && overwriteStatus != OverwriteStatus.ALL_FILES) {
                        final OverwriteStatus status[] = new OverwriteStatus[1];

                        getShell().getDisplay().syncExec(new Runnable() {
                            public void run() {
                                status[0] =
                                        canOverwriteFile(diagramFile.getName(),
                                                destContainer.getName());
                            }
                        });

                        overwriteStatus = status[0];

                        if (overwriteStatus == OverwriteStatus.CANCEL) {
                            throw new OperationCanceledException();
                        }
                    }

                    if (overwriteStatus.canOverwrite()) {

                        IPath outPath =
                                getDestinationContainer().getFullPath()
                                        .append(getDestinationFileName(project,
                                                file));
                        // run the transformation action
                        List<IStatus> statusArr =
                                WSDLTransformUtil.transformWSDLtoBOM(file,
                                        outPath,
                                        new SubProgressMonitor(monitor, 5),
                                        false,
                                        true);

                        // If the result reports an error then throw core
                        // exception
                        List<IStatus> errors = new ArrayList<IStatus>();
                        for (IStatus status : statusArr) {
                            if (status.getSeverity() == IStatus.ERROR) {
                                errors.add(status);
                            }
                        }

                        if (!errors.isEmpty()) {
                            folder.refreshLocal(IResource.DEPTH_INFINITE,
                                    new NullProgressMonitor());
                            for (IResource resource : folder.members()) {
                                if (resource.exists()
                                        && !existingFilesNames
                                                .contains(resource.getName())) {
                                    resource.delete(IResource.FORCE,
                                            new NullProgressMonitor());
                                }
                            }

                            // Report error
                            throw new CoreException(
                                    new MultiStatus(
                                            Activator.PLUGIN_ID,
                                            IStatus.ERROR,
                                            errors.toArray(new IStatus[errors
                                                    .size()]),
                                            Messages.ImportWSDLWizard_importWSDLError_message,
                                            null));
                        }
                    }
                }
            }
        }
    }

    @Override
    protected String getImportFileExtension() {
        return BOMResourcesPlugin.BOM_FILE_EXTENSION;
    }

    /**
     * Get the destination file name. This will replace the file extension of
     * the given source file with the import file extension
     * 
     * @param srcFile
     * @return String
     */
    private String getDestinationFileName(IProject project, File srcFile) {
        ResourceSet resourceSet = new ResourceSetImpl();
        Resource createResource =
                resourceSet.createResource(URI.createFileURI(srcFile.toURI()
                        .getPath()));
        if (createResource instanceof WSDLResourceImpl) {
            WSDLResourceImpl wsdlFile = (WSDLResourceImpl) createResource;
            try {
                wsdlFile.load(Collections.EMPTY_MAP);
                if (wsdlFile.getDefinition().getTargetNamespace() != null) {
                    String tmpName =
                            NamespaceURIToJavaPackageMapper
                                    .getJavaPackageNameFromNamespaceURI(project,
                                            wsdlFile.getDefinition()
                                                    .getTargetNamespace())
                                    + "." + BOMResourcesPlugin.BOM_FILE_EXTENSION; //$NON-NLS-1$
                    wsdlFile.unload();
                    return tmpName;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String szFileName = srcFile.getName();
        String szRetVal = null;

        szRetVal = szFileName.substring(0, szFileName.lastIndexOf('.') + 1);
        szRetVal += getImportFileExtension();

        return szRetVal;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.importexport.utils.ImportExportTransformer.
     * ITransformationStylesheetsProvider#getXsltParameters()
     */
    public Map<String, String> getXsltParameters() {
        // Not using XSLT for import
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.importexport.utils.ImportExportTransformer.
     * ITransformationStylesheetsProvider#getXslts()
     */
    public URL[] getXslts() {
        // Not using XSLT for import, but return dummy array so that the wizard
        // can finish
        return new URL[1];
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.importexport.importWizard.IImportWizardPageProvider#
     * getDestinationContainerSelectionDialog()
     */
    public SelectionDialog getDestinationContainerSelectionDialog() {
        return new TargetFolderSelectionDialog(getShell());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.importexport.importWizard.IImportWizardPageProvider#
     * getFileExtensionFilter()
     */
    public String[] getFileExtensionFilter() {
        return new String[] { "wsdl" }; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.importexport.importWizard.IImportWizardPageProvider#
     * validateDestinationContainer(org.eclipse.core.resources.IContainer)
     */
    public IStatus validateDestinationContainer(IContainer container) {
        IStatus status =
                new Status(
                        IStatus.WARNING,
                        Activator.PLUGIN_ID,
                        Messages.ImportWSDLWizard_destinationNotBOMFolder_warning_shortdesc);

        if (container instanceof IFolder) {
            // If destination folder is BOM special folder then return OK status
            SpecialFolder sFolder = getSpecialFolder((IFolder) container);

            if (sFolder != null) {
                if (sFolder.getKind()
                        .equals(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND)) {
                    status = Status.OK_STATUS;
                }
            } else if (isContainedInSpecialFolder((IFolder) container,
                    BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND)) {
                status = Status.OK_STATUS;
            }
        }

        if (container instanceof IProject) {
            status =
                    new Status(
                            IStatus.ERROR,
                            Activator.PLUGIN_ID,
                            Messages.ImportWSDLWizard_destinationProject_error_shortdesc);
        }

        return status;
    }

    /**
     * Get the <code>SpecialFolder</code> representing the given
     * <code>IFolder</code> if it is marked as a special folder.
     * 
     * @param folder
     *            <code>IFolder</code> to check
     * @return <code>SpecialFolder</code> if the folder is marked as special
     *         folder, <code>null</code> otherwise.
     */
    private SpecialFolder getSpecialFolder(IFolder folder) {
        SpecialFolder sf = null;

        if (folder != null) {
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault()
                            .getProjectConfig(folder.getProject());

            if (config != null) {
                sf = config.getSpecialFolders().getFolder(folder);
            }
        }
        return sf;
    }

    /**
     * Check if the given folder is contained in a special folder of the given
     * kind.
     * 
     * @param folder
     *            folder to check
     * @param kind
     *            special folder kind
     * @return <code>true</code> if the folder is contained in the special
     *         folder, <code>false</code> otherwise.
     */
    private boolean isContainedInSpecialFolder(IFolder folder, String kind) {
        boolean contained = false;

        ProjectConfig config =
                XpdResourcesPlugin.getDefault()
                        .getProjectConfig(folder.getProject());

        if (config != null) {
            SpecialFolder container =
                    config.getSpecialFolders().getFolderContainer(folder);

            contained = container != null && container.getKind().equals(kind);
        }

        return contained;
    }

    /**
     * UML import target folder selection dialog.
     * 
     * @author njpatel
     */
    private class TargetFolderSelectionDialog extends SelectionDialog implements
            Listener {

        private SpecialFolderContainerGroup containerGroup;

        /**
         * UML import Target folder selection dialog.
         * 
         * @param parentShell
         */
        protected TargetFolderSelectionDialog(Shell parentShell) {
            super(parentShell);
            setTitle(Messages.ImportWSDLWizard_selectFolderDialog_title);
            setMessage(Messages.ImportWSDLWizard_selectFolderDialog_shortdesc);
        }

        @Override
        protected Control createDialogArea(Composite parent) {
            Composite root = new Composite(parent, SWT.NONE);
            root.setLayout(new GridLayout());
            containerGroup = new SpecialFolderContainerGroup(root, this, false);

            IContainer container = getDestinationContainer();

            if (container != null) {
                // Check if the folder is a special folder - if it is then set
                // that as he initial selection
                Object selection = container;

                if (container instanceof IFolder) {
                    SpecialFolder sFolder =
                            getSpecialFolder((IFolder) container);

                    if (sFolder != null) {
                        selection = sFolder;
                    }
                }
                containerGroup.setInitialSelection(new StructuredSelection(
                        selection));
            }

            return root;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets
         * .Event)
         */
        public void handleEvent(Event event) {
            // Set the result
            Object selection = containerGroup.getSelection();
            setResult(selection != null ? Collections.singletonList(selection)
                    : null);
        }
    }
}
