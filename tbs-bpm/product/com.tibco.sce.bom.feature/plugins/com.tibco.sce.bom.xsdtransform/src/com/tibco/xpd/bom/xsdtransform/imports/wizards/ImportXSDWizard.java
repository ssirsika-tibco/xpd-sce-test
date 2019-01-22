/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.imports.wizards;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.xsd.XSDImport;
import org.eclipse.xsd.XSDInclude;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDParser;
import org.eclipse.xsd.util.XSDResourceImpl;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.bom.xsdtransform.internal.Messages;
import com.tibco.xpd.bom.xsdtransform.preferences.XsdTransformPreferenceConstants;
import com.tibco.xpd.bom.xsdtransform.utils.NamespaceURIToJavaPackageMapper;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.ui.dialogs.SpecialFolderContainerGroup;
import com.tibco.xpd.ui.importexport.importwizard.AbstractImportWizard;
import com.tibco.xpd.ui.importexport.importwizard.pages.FilteredFSElement;
import com.tibco.xpd.ui.importexport.importwizard.pages.ImportWizardPageIO;
import com.tibco.xpd.ui.util.MessageDialogUtil;

/**
 * Import XSD wizard to convert an XSD file to BOM model.
 * 
 * @author glewis
 * 
 */
public class ImportXSDWizard extends AbstractImportWizard implements
        IImportWizard {

    private static final String IMG_LOC = "icons/BOMImport.png"; //$NON-NLS-1$

    private Button applyXSDNotationButton;

    private boolean removeXSDNotation = true;

    /**
     * Import UML Wizard.
     */
    public ImportXSDWizard() {
        setWindowTitle(Messages.ImportXSDWizard_importXSDWizard_title);
        setDefaultPageImageDescriptor(Activator
                .imageDescriptorFromPlugin(Activator.PLUGIN_ID, IMG_LOC));
    }

    /**
     * @see com.tibco.xpd.ui.importexport.importwizard.AbstractImportWizard#beforePerformFinish()
     * 
     */
    @Override
    protected IStatus beforePerformFinish() {
        super.beforePerformFinish();
        synchronized (this) {
            removeXSDNotation = !applyXSDNotationButton.getSelection();

            if (removeXSDNotation) {
                // Display warning dialog
                int openOkCancelConfirm =
                        MessageDialogUtil
                                .openOkCancelConfirm(Display.getDefault()
                                        .getActiveShell(),
                                        Messages.ImportXSDWizard_XsdNotationRemoval_title,
                                        Messages.ImportXSDWizard_XsdNotationRemoval_message,
                                        Messages.ImportXSDWizard_XsdNotationRemoval_AlwaysShowWarnAgain_message,
                                        true,
                                        XsdTransformPreferenceConstants.DONT_SHOW_XSD_ANNOTATION_REMOVAL_WARNING,
                                        Activator.getDefault()
                                                .getPreferenceStore());

                if (openOkCancelConfirm == MessageDialog.OK) {
                    return new Status(
                            IStatus.OK,
                            Activator.PLUGIN_ID,
                            "Business Object Model: Proceed with XML Schema import without XSD annotation", //$NON-NLS-1$
                            null);
                } else {
                    return new Status(IStatus.CANCEL, Activator.PLUGIN_ID,
                            "Business Object Model: Abort XML Schema import", //$NON-NLS-1$
                            null);
                }
            }
        }
        return new Status(
                IStatus.OK,
                Activator.PLUGIN_ID,
                "Business Object Model: Proceed with XML Schema import with XSD annotation", //$NON-NLS-1$
                null);
    }

    @Override
    protected void performOperation(IProgressMonitor monitor)
            throws CoreException, InterruptedException {

        List<FilteredFSElement> importResources = getSelectedResources();

        Set<File> rootSelectedFiles = getRootSelectedFiles(importResources);

        final IContainer destContainer = getDestinationContainer();
        OverwriteStatus overwriteStatus = getOverwriteStatus();
        if (rootSelectedFiles != null && !rootSelectedFiles.isEmpty()
                && destContainer != null) {
            monitor.beginTask(Messages.ImportXSDWizard_importXSDFiles_label,
                    importResources.size() * 5);

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

            // go through each file and transform to BOM - overwriting if user
            // selects it is appropriate
            if (isBOMspecialFolderOutput(folder)) {

                for (File file : rootSelectedFiles) {

                    if (monitor.isCanceled()) {
                        throw new OperationCanceledException();
                    }

                    /*
                     * If overwrite dialog was shown and NO selected then we
                     * need to reset it to the default value, otherwise no
                     * further files will be processed
                     */
                    if (overwriteStatus == OverwriteStatus.NO) {
                        overwriteStatus = OverwriteStatus.FILE;
                    }
                    monitor.subTask(file.getName());

                    final IFile diagramFile =
                            destContainer.getFile(new Path(
                                    getDestinationFileName(project, file)));

                    if (diagramFile.exists()
                            && overwriteStatus != OverwriteStatus.ALL_FILES) {
                        final OverwriteStatus status[] = new OverwriteStatus[1];

                        getShell().getDisplay().syncExec(new Runnable() {
                            @Override
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

                        boolean doRemoveXSDNotation;
                        synchronized (this) {
                            doRemoveXSDNotation = removeXSDNotation;
                        }
                        List<IStatus> res =
                                XSDUtil.transformXSDToBOM(file,
                                        outPath,
                                        new SubProgressMonitor(monitor, 5),
                                        false,
                                        true,
                                        doRemoveXSDNotation);

                        // If the result reports an error then throw core
                        // exception
                        List<IStatus> errors = new ArrayList<IStatus>();
                        for (IStatus status : res) {
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
                                            Messages.ImportXSDWizard_importXSDError_message,
                                            null));
                        }
                    }
                }
            }
        }
    }

    /**
     * @param folder
     * @return <code>true</code> if specified folder is a BOM special folder,
     *         <code>false</code> otherwise.
     */
    private boolean isBOMspecialFolderOutput(IFolder folder) {

        SpecialFolder sf = getSpecialFolder(folder);
        if (sf != null) {
            return BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND.equals(sf
                    .getKind());
        }

        return false;
    }

    /**
     * This method returns the filtered set of resources to be passed to the bom
     * generator. The filter eliminates duplicates when are: 1.- Imported
     * schemas 2.- Included schemas 3.- There is a cyclic dependency and
     * upstream elements are not source files.
     * 
     * @param importResources
     * @return
     * @throws CoreException
     */
    private Set<File> getRootSelectedFiles(
            List<FilteredFSElement> importResources) throws CoreException {
        if (importResources != null && !importResources.isEmpty()) {
            Set<File> filesToIgnore = new HashSet<File>();
            Set<File> sourceFiles = new HashSet<File>();
            Map<XSDSchema, File> schemaFileMap = new HashMap<XSDSchema, File>();
            for (FilteredFSElement filteredFSElement : importResources) {
                if (filteredFSElement.getFileSystemObject() instanceof File) {
                    File file = (File) filteredFSElement.getFileSystemObject();
                    XSDSchema schemaFromFile = getSchemaFromFile(file);
                    if (schemaFromFile != null) {
                        schemaFileMap.put(schemaFromFile, file);
                    }
                }
            }
            Map<File, Set<File>> importedFiles = new HashMap<File, Set<File>>();
            Map<File, Set<File>> includedFiles = new HashMap<File, Set<File>>();
            Set<XSDSchema> allSchemas = schemaFileMap.keySet();
            for (XSDSchema schema : allSchemas) {
                if (schema != null) {
                    File sourceFile = schemaFileMap.get(schema);
                    Set<File> sourceFileImports = new HashSet<File>();
                    Set<File> sourceFileIncludes = new HashSet<File>();
                    TreeIterator<EObject> allContents = schema.eAllContents();
                    if (allContents != null) {
                        while (allContents.hasNext()) {
                            EObject node = allContents.next();
                            if (node instanceof XSDImport) {
                                XSDImport xsdImport = (XSDImport) node;
                                String schemaLocation =
                                        xsdImport.getSchemaLocation();
                                if (schemaLocation != null) {
                                    try {
                                        URI resolve =
                                                resolveReferencedFile(schemaLocation,
                                                        sourceFile);
                                        if (resolve != null) {
                                            File importedFile =
                                                    new File(
                                                            resolve.toFileString());
                                            filesToIgnore.add(importedFile);
                                            sourceFileImports.add(importedFile);
                                        }
                                    } catch (Exception e) {
                                        // Report error
                                        throw new CoreException(
                                                new Status(
                                                        IStatus.ERROR,
                                                        Activator.PLUGIN_ID,
                                                        Messages.ImportXSDWizard_importXSDError_message,
                                                        e));
                                    }
                                }
                            } else if (node instanceof XSDInclude) {
                                XSDInclude xsdInclude = (XSDInclude) node;
                                String schemaLocation =
                                        xsdInclude.getSchemaLocation();
                                if (schemaLocation != null) {
                                    try {
                                        URI resolve =
                                                resolveReferencedFile(schemaLocation,
                                                        sourceFile);
                                        if (resolve != null) {
                                            File includedFile =
                                                    new File(
                                                            resolve.toFileString());
                                            filesToIgnore.add(includedFile);
                                            sourceFileIncludes
                                                    .add(includedFile);
                                        }
                                    } catch (Exception e) {
                                        // Report error
                                        throw new CoreException(
                                                new Status(
                                                        IStatus.ERROR,
                                                        Activator.PLUGIN_ID,
                                                        Messages.ImportXSDWizard_importXSDError_message,
                                                        e));
                                    }
                                }
                            }
                        }
                    }
                    importedFiles.put(sourceFile, sourceFileImports);
                    includedFiles.put(sourceFile, sourceFileIncludes);
                }
            }

            for (FilteredFSElement filteredFSElement : importResources) {
                if (filteredFSElement.getFileSystemObject() instanceof File) {
                    File file = (File) filteredFSElement.getFileSystemObject();
                    if (!containsFile(file, filesToIgnore)) {
                        sourceFiles.add(file);
                    }
                }
            }

            // At this point the sourceFiles are the ones not imported or
            // included by any xsd or
            Map<File, Set<File>> upperStreamMap =
                    getUpperStreamMap(importedFiles);

            // Add the cyclic dependencies that don't have any import
            Set<File> candidateFiles = upperStreamMap.keySet();
            for (File candidateFile : candidateFiles) {
                Set<File> upstream = upperStreamMap.get(candidateFile);
                if (containsFile(candidateFile, upstream)) {
                    // There is a cyclic dependency
                    if (!upstreamIsSource(upstream, sourceFiles)) {
                        sourceFiles.add(candidateFile);
                    }
                }
            }

            return sourceFiles;
        }
        return Collections.emptySet();
    }

    /**
     * Method to identify whether any upstream file in the list is source file
     * 
     * @param upstream
     * @param sourceFiles
     * @return
     */
    private boolean upstreamIsSource(Set<File> upstream, Set<File> sourceFiles) {
        for (File upstreamFile : upstream) {
            if (containsFile(upstreamFile, sourceFiles)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method returns the upper stream relationships for a given file
     * 
     * @param file
     * @param importedFiles
     * @return
     */
    private Map<File, Set<File>> getUpperStreamMap(
            Map<File, Set<File>> importedFiles) {
        if (importedFiles != null && !importedFiles.isEmpty()) {
            Map<File, Set<File>> upperStreamMap =
                    new HashMap<File, Set<File>>();
            Set<File> candidateFiles = importedFiles.keySet();
            for (File candidateFile : candidateFiles) {
                Set<File> upstream = new HashSet<File>();
                addUpStreamList(candidateFile, importedFiles, upstream);
                upperStreamMap.put(candidateFile, upstream);
            }

            return upperStreamMap;
        }
        return Collections.emptyMap();
    }

    /**
     * Adds a upstream file to the list and calls recursively to itself to look
     * for the next upstream file
     * 
     * @param file
     * @param importedFiles
     * @param upstream
     */
    private void addUpStreamList(File file, Map<File, Set<File>> importedFiles,
            Set<File> upstream) {
        if (file != null && importedFiles != null && !importedFiles.isEmpty()
                && upstream != null) {
            Set<File> candidateFiles = importedFiles.keySet();
            for (File candidateFile : candidateFiles) {
                if (!candidateFile.getAbsolutePath()
                        .equals(file.getAbsolutePath())) {
                    Set<File> importedSet = importedFiles.get(candidateFile);
                    if (containsFile(file, importedSet)) {
                        if (!containsFile(candidateFile, upstream)) {
                            upstream.add(candidateFile);
                            addUpStreamList(candidateFile,
                                    importedFiles,
                                    upstream);
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }

    private boolean containsFile(File file, Set<File> fileSet) {
        for (File fileToIgnore : fileSet) {
            if (fileToIgnore != null
                    && file != null
                    && fileToIgnore.getAbsolutePath()
                            .equals(file.getAbsolutePath())) {
                return true;
            }
        }
        return false;
    }

    private URI resolveReferencedFile(String schemaLocation, File sourceFile)
            throws URISyntaxException {
        if (schemaLocation != null && sourceFile != null) {
            /*
             * Sid XPD-1741: Use EMF URI this deals MUCH better with space vs
             * %20 and so on
             */
            URI importedURI = URI.createURI(schemaLocation, true);

            URI baseURI = URI.createFileURI(sourceFile.getAbsolutePath());

            URI resolve = importedURI.resolve(baseURI);
            return resolve;
        }
        return null;
    }

    private XSDSchema getSchemaFromFile(File file) throws CoreException {
        if (file != null) {
            try {

                FileInputStream fstream = new FileInputStream(file);
                XSDParser parser = new XSDParser(null);
                parser.parse(fstream);
                return parser.getSchema();
            } catch (Exception e) {// Catch exception if any
                // Report error
                throw new CoreException(new MultiStatus(Activator.PLUGIN_ID,
                        IStatus.ERROR,
                        Messages.ImportXSDWizard_importXSDError_message, e));
            }
        }
        return null;
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
        if (createResource instanceof XSDResourceImpl) {
            XSDResourceImpl xsdFile = (XSDResourceImpl) createResource;
            try {
                xsdFile.load(Collections.EMPTY_MAP);
                if (xsdFile.getSchema() != null
                        && xsdFile.getSchema().getTargetNamespace() != null) {
                    String tmpName =
                            NamespaceURIToJavaPackageMapper
                                    .getJavaPackageNameFromNamespaceURI(project,
                                            xsdFile.getSchema()
                                                    .getTargetNamespace())
                                    + "." + BOMResourcesPlugin.BOM_FILE_EXTENSION; //$NON-NLS-1$
                    xsdFile.unload();
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
    @Override
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
    @Override
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
    @Override
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
    @Override
    public String[] getFileExtensionFilter() {
        return new String[] { "xsd" }; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.importexport.importWizard.IImportWizardPageProvider#
     * validateDestinationContainer(org.eclipse.core.resources.IContainer)
     */
    @Override
    public IStatus validateDestinationContainer(IContainer container) {
        IStatus status =
                new Status(
                        IStatus.ERROR,
                        Activator.PLUGIN_ID,
                        Messages.ImportXSDWizard_destinationNotBOMFolder_warning_shortdesc);

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
                            Messages.ImportXSDWizard_destinationProject_error_shortdesc);
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

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#addPages()
     */
    @Override
    public void addPages() {
        // Add the source/destination selection page
        pageIO = new ImportWizardPageIO(selection, this) {

            /**
             * @see com.tibco.xpd.ui.importexport.importwizard.pages.ImportWizardPageIO#createOptionsGroupButtons(org.eclipse.swt.widgets.Group)
             * 
             * @param optionsGroup
             */
            @Override
            protected void createOptionsGroupButtons(Group optionsGroup) {
                super.createOptionsGroupButtons(optionsGroup);
                applyXSDNotationButton = new Button(optionsGroup, SWT.CHECK);
                applyXSDNotationButton
                        .setText(Messages.ImportXSDWizard_preserveXSDNotation_button);
                applyXSDNotationButton.setSelection(true);
            }
        };

        pageIO.setTitle(getWindowTitle());
        pageIO.setDescription(getWindowMessage());

        // If wizard banner image specified then set it
        if (imgWizardBanner != null)
            pageIO.setImageDescriptor(imgWizardBanner);
        addPage(pageIO);
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
            setTitle(Messages.ImportXSDWizard_selectFolderDialog_title);
            setMessage(Messages.ImportXSDWizard_selectFolderDialog_shortdesc);
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
        @Override
        public void handleEvent(Event event) {
            // Set the result
            Object selection = containerGroup.getSelection();
            setResult(selection != null ? Collections.singletonList(selection)
                    : null);
        }
    }
}
