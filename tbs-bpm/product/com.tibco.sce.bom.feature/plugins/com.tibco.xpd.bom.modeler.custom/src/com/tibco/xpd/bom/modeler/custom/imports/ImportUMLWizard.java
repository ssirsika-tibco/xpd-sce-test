/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.imports;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.commands.CreateDiagramCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.dialogs.FileSystemElement;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;

import com.tibco.xpd.bom.modeler.custom.Activator;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.CanvasPackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditorUtil;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.ui.dialogs.SpecialFolderContainerGroup;
import com.tibco.xpd.ui.importexport.importwizard.AbstractImportWizard;
import com.tibco.xpd.ui.importexport.importwizard.IImportWizardPageProvider2;
import com.tibco.xpd.ui.importexport.importwizard.pages.FilteredFSElement;

/**
 * Import UML wizard to convert an UML file to BOM model.
 * <p>
 * Since 3.2 this will now import any profiles that are applied to the UML
 * models being imported (user will be asked whether the profiles should be
 * imported).
 * </p>
 * 
 * @author njpatel
 * 
 */
public class ImportUMLWizard extends AbstractImportWizard implements
        IImportWizardPageProvider2 {

    private static final String IMG_LOC = "icons/wizban/BOMImport.png"; //$NON-NLS-1$

    private final TransactionalEditingDomain editingDomain;

    /*
     * Keep track of profiles already imported when multiple uml models.
     */
    private final Set<Profile> profilesAlreadyImported;

    /**
     * Import UML Wizard.
     */
    public ImportUMLWizard() {
        setWindowTitle(Messages.ImportUMLWizard_importUMLWizard_title);
        setDefaultPageImageDescriptor(Activator.imageDescriptorFromPlugin(
                Activator.PLUGIN_ID, IMG_LOC));

        profilesAlreadyImported = new HashSet<Profile>();

        /*
         * Use a temporary editing domain to import the resources - this will
         * avoid the main resource set from getting polluted
         */
        editingDomain = TransactionalEditingDomain.Factory.INSTANCE
                .createEditingDomain();
    }

    @Override
    public void dispose() {
        // Dispose the temporary editing domain.
        if (editingDomain != null) {
            editingDomain.dispose();
        }

        super.dispose();
    }

    @Override
    protected void performOperation(IProgressMonitor monitor)
            throws CoreException, InterruptedException {

        List<FilteredFSElement> importResources = getSelectedResources();
        OverwriteStatus overwriteStatus = getOverwriteStatus();
        final IContainer destContainer = getDestinationContainer();

        // Reset the profiles already imported list
        profilesAlreadyImported.clear();

        if (importResources != null && !importResources.isEmpty()
                && destContainer != null) {
            monitor.beginTask(Messages.ImportUMLWizard_importUMLFiles_label,
                    importResources.size());

            List<File> filesNotImported = new ArrayList<File>();
            for (FilteredFSElement elem : importResources) {
                try {
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

                    if (elem.getFileSystemObject() instanceof File) {
                        File fileToImport = (File) elem.getFileSystemObject();
                        monitor.subTask(fileToImport.getName());

                        final IFile diagramFile = destContainer
                                .getFile(new Path(
                                        getDestinationFileName(fileToImport)));

                        if (diagramFile.exists()
                                && overwriteStatus != OverwriteStatus.ALL_FILES) {
                            final OverwriteStatus status[] = new OverwriteStatus[1];

                            getShell().getDisplay().syncExec(new Runnable() {
                                public void run() {
                                    status[0] = canOverwriteFile(diagramFile
                                            .getProjectRelativePath()
                                            .toString(), destContainer
                                            .getProject().getName());
                                }
                            });

                            overwriteStatus = status[0];
                        }

                        if (overwriteStatus.isCancelled()) {
                            // User selected to cancel the import
                            throw new OperationCanceledException();
                        } else if (overwriteStatus.canOverwrite()) {
                            // Import the UML file by creating diagram after
                            // import
                            final Resource umlResource = editingDomain
                                    .getResourceSet().getResource(
                                            URI.createFileURI(fileToImport
                                                    .getAbsolutePath()), true);
                            if (umlResource != null
                                    && umlResource.getContents() != null) {

                                // Only import UML files that have a Model
                                if (hasModel(editingDomain, umlResource)) {
                                    try {
                                        // Process any profiles
                                        IStatus status = processProfiles(
                                                editingDomain, umlResource,
                                                destContainer);

                                        if (status.isOK()) {
                                            // Import UML model
                                            ImportUMLCommand cmd = new ImportUMLCommand(
                                                    editingDomain,
                                                    Messages.ImportUMLWizard_title,
                                                    diagramFile, umlResource);
                                            status = OperationHistoryFactory
                                                    .getOperationHistory()
                                                    .execute(cmd, null, null);
                                        }

                                        // Check status
                                        if (!status.isOK()) {
                                            if (status.getSeverity() == IStatus.CANCEL) {
                                                throw new OperationCanceledException();
                                            } else if (status.getException() != null) {
                                                throw status.getException();
                                            }
                                        }
                                    } catch (InterruptedException e) {
                                        throw e;
                                    } catch (CoreException e) {
                                        throw e;
                                    } catch (OperationCanceledException e) {
                                        throw e;
                                    } catch (Throwable e) {
                                        throw new CoreException(new Status(
                                                IStatus.ERROR,
                                                Activator.PLUGIN_ID, e
                                                        .getLocalizedMessage(),
                                                e));
                                    }
                                } else {
                                    // No model in the file being imported
                                    filesNotImported.add(fileToImport);
                                }

                            }
                        }
                    }
                } finally {
                    monitor.worked(1);
                }
            } // end for-loop of selected files to import

            // Report any files that were not imported
            if (!filesNotImported.isEmpty()) {
                if (filesNotImported.size() == importResources.size()) {
                    // None of the files were imported
                    getShell().getDisplay().syncExec(new Runnable() {
                        public void run() {
                            MessageDialog
                                    .openError(
                                            getShell(),
                                            getShell().getText(),
                                            Messages.ImportUMLWizard_noFilesImported_err_message);
                        }
                    });
                    // Cancel the operation
                    throw new OperationCanceledException();
                } else {
                    // Some of the files were not imported
                    List<IStatus> results = new ArrayList<IStatus>();
                    for (File file : filesNotImported) {
                        results
                                .add(new Status(
                                        IStatus.WARNING,
                                        Activator.PLUGIN_ID,
                                        String
                                                .format(
                                                        Messages.ImportUMLWizard_fileHasNoModel_warn_message,
                                                        file.getPath())));
                    }

                    // Log multi-status so not using the activator logger as it
                    // does not allow this.
                    ILog log = Platform.getLog(Activator.getDefault()
                            .getBundle());
                    log
                            .log(new MultiStatus(
                                    Activator.PLUGIN_ID,
                                    IStatus.WARNING,
                                    results
                                            .toArray(new IStatus[results.size()]),
                                    Messages.ImportUMLWizard_someUMLFilesNotImported_warn_message,
                                    null));

                    getShell().getDisplay().syncExec(new Runnable() {
                        public void run() {
                            MessageDialog
                                    .openWarning(
                                            getShell(),
                                            getShell().getText(),
                                            Messages.ImportUMLWizard_someUMLFilesNotImportedCheckLog_warn_message);
                        }
                    });
                }
            }
        }
    }

    /**
     * Process any Profiles in the selected source resource.
     * 
     * @param editingDomain
     *            transactional editing domain
     * @param srcResource
     *            the resource being imported
     * @param target
     *            target container
     * @return status - OK if processed successfully, CANCEL if the user decided
     *         to cancel the operation.
     * @throws InvocationTargetException
     * @throws InterruptedException
     */
    private IStatus processProfiles(TransactionalEditingDomain editingDomain,
            Resource srcResource, IContainer target)
            throws InvocationTargetException, InterruptedException {

        // Check for profiles
        Set<Profile> profiles = getAppliedProfiles(editingDomain, srcResource);

        /*
         * If there are any profiles that have already been imported by the
         * previous model then do not process it again.
         */
        if (!profilesAlreadyImported.isEmpty()) {
            for (Iterator<Profile> iter = profiles.iterator(); iter.hasNext();) {
                if (profilesAlreadyImported.contains(iter.next())) {
                    iter.remove();
                }
            }
        }

        if (!profiles.isEmpty()) {
            // Update the profiles list
            profilesAlreadyImported.addAll(profiles);

            // Import the profiles
            final ImportProfilesDialog dlg = new ImportProfilesDialog(
                    getShell(), profiles, srcResource.getURI().lastSegment());
            getShell().getDisplay().syncExec(new Runnable() {
                public void run() {
                    dlg.open();
                }
            });
            switch (dlg.getReturnCode()) {
            case 0: // Yes
                // Import the profiles
                ImportOperation op = getImportProfilesOperation(target,
                        profiles);
                if (op != null) {
                    op.run(null);
                }
                break;
            case 1: // No
                // Do nothing as user selected
                // not to import profiles
                break;
            default: // Cancel
                return Status.CANCEL_STATUS;
            }
        }
        return Status.OK_STATUS;
    }

    /**
     * Get the operation to import the {@link Profile} files.
     * 
     * @param target
     *            target container
     * @param profiles
     *            the profiles to import
     * @return {@link ImportOperation} or <code>null</code> if there are no
     *         files to import.
     */
    private ImportOperation getImportProfilesOperation(IContainer target,
            Set<Profile> profiles) {
        List<File> files = new ArrayList<File>();

        // Get the profile files
        for (Profile profile : profiles) {
            Resource resource = profile.eResource();
            if (resource != null) {
                URI uri = resource.getURI();
                if (uri.isFile()) {
                    File file = new File(uri.toFileString());
                    if (file.exists() && !files.contains(file)) {
                        files.add(file);
                    }
                }
            }
        }

        if (!files.isEmpty()) {
            ImportOperation operation = new ImportOperation(target
                    .getFullPath(), FileSystemStructureProvider.INSTANCE,
                    new IOverwriteQuery() {

                        public String queryOverwrite(String pathString) {
                            Path path = new Path(pathString);

                            String messageString;
                            // Break the message up if there is a file name and
                            // a directory and there are at least 2 segments.
                            if (path.getFileExtension() == null
                                    || path.segmentCount() < 2) {
                                messageString = String
                                        .format(
                                                Messages.ImportUMLWizard_fileExists_overwrite_message,
                                                pathString);
                            } else {
                                messageString = String
                                        .format(
                                                Messages.ImportUMLWizard_overwriteFile_message,
                                                path.lastSegment(), path
                                                        .removeLastSegments(1)
                                                        .toOSString());
                            }

                            final MessageDialog dialog = new MessageDialog(
                                    getContainer().getShell(),
                                    Messages.ImportUMLWizard_profileExists_dialog_title,
                                    null, messageString,
                                    MessageDialog.QUESTION, new String[] {
                                            IDialogConstants.YES_LABEL,
                                            IDialogConstants.YES_TO_ALL_LABEL,
                                            IDialogConstants.NO_LABEL,
                                            IDialogConstants.NO_TO_ALL_LABEL,
                                            IDialogConstants.CANCEL_LABEL }, 0);
                            String[] response = new String[] { YES, ALL, NO,
                                    NO_ALL, CANCEL };
                            // run in syncExec because callback is from an
                            // operation, which is probably not running in the
                            // UI thread.
                            getShell().getDisplay().syncExec(new Runnable() {
                                public void run() {
                                    dialog.open();
                                }
                            });
                            return dialog.getReturnCode() < 0 ? CANCEL
                                    : response[dialog.getReturnCode()];
                        }

                    }, files);

            operation.setContext(getShell());
            operation.setCreateContainerStructure(false);
            return operation;
        }

        return null;
    }

    /**
     * Get all applied profiles to the Model in the given resource. NOTE: This
     * runs in a read-only transaction.
     * 
     * @param umlResource
     *            resource containing the UML model
     * @return list of applied <code>Profile</code>s, empty list if none.
     * @throws InterruptedException
     */
    private Set<Profile> getAppliedProfiles(TransactionalEditingDomain ed,
            final Resource umlResource) throws InterruptedException {
        final Set<Profile> profiles = new HashSet<Profile>();
        if (ed != null && umlResource != null) {
            ed.runExclusive(new Runnable() {
                public void run() {
                    if (umlResource.getContents() != null) {
                        for (EObject eo : umlResource.getContents()) {
                            if (eo instanceof Model) {
                                profiles.addAll(((Model) eo)
                                        .getAllAppliedProfiles());
                            }
                        }
                    }
                }
            });
        }
        return profiles;
    }

    /**
     * Check if the given resource contains an UML model. NOTE: This runs in a
     * read-only transaction.
     * 
     * @param res
     *            resource to check
     * @return <code>true</code> if the resource contains a model.
     * @throws InterruptedException
     */
    private boolean hasModel(TransactionalEditingDomain ed, final Resource res)
            throws InterruptedException {
        final Boolean[] ret = new Boolean[] { false };
        if (res != null && ed != null) {
            ed.runExclusive(new Runnable() {

                public void run() {
                    if (res.getContents() != null) {
                        for (EObject eo : res.getContents()) {
                            if (eo instanceof Model) {
                                ret[0] = true;
                                break;
                            }
                        }
                    }
                }

            });
        }

        return ret[0];
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
    private String getDestinationFileName(File srcFile) {

        String szFileName = srcFile.getName();
        String szRetVal = null;

        // Find the file extension and replace with ".concepts"
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
        return new String[] { "uml" }; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.importexport.importWizard.IImportWizardPageProvider#
     * validateDestinationContainer(org.eclipse.core.resources.IContainer)
     */
    public IStatus validateDestinationContainer(IContainer container) {
        IStatus status = new Status(
                IStatus.WARNING,
                Activator.PLUGIN_ID,
                Messages.ImportUMLWizard_destinationNotBOMFolder_warning_shortdesc);

        if (container instanceof IFolder) {
            // If destination folder is BOM special folder then return OK status
            SpecialFolder sFolder = getSpecialFolder((IFolder) container);

            if (sFolder != null) {
                if (sFolder.getKind().equals(
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND)) {
                    status = Status.OK_STATUS;
                }
            } else if (isContainedInSpecialFolder((IFolder) container,
                    BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND)) {
                status = Status.OK_STATUS;
            }
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
            ProjectConfig config = XpdResourcesPlugin.getDefault()
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

        ProjectConfig config = XpdResourcesPlugin.getDefault()
                .getProjectConfig(folder.getProject());

        if (config != null) {
            SpecialFolder container = config.getSpecialFolders()
                    .getFolderContainer(folder);

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
            setTitle(Messages.ImportUMLWizard_selectFolderDialog_title);
            setMessage(Messages.ImportUMLWizard_selectFolderDialog_shortdesc);
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
                    SpecialFolder sFolder = getSpecialFolder((IFolder) container);

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

    /**
     * UML Import command. This will create the BOM resource and add the root
     * element and the <code>Diagram</code> from the UML resource to import.
     * 
     * @author njpatel
     * 
     */
    private class ImportUMLCommand extends AbstractTransactionalCommand {

        private IFile diagramFile;
        private Resource umlResource;

        /**
         * UML Import command
         * 
         * @param domain
         *            transactional editing domain
         * @param label
         *            command label
         * @param diagramFile
         *            destination BOM resource
         * @param umlResource
         *            import model
         */
        public ImportUMLCommand(TransactionalEditingDomain domain,
                String label, IFile diagramFile, Resource umlResource) {
            super(domain, label, Collections.singletonList(diagramFile));
            this.umlResource = umlResource;
            this.diagramFile = diagramFile;
        }

        @Override
        public boolean canRedo() {
            return false;
        }

        @Override
        public boolean canUndo() {
            return false;
        }

        @Override
        protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
                IAdaptable info) throws ExecutionException {

            if (umlResource != null && diagramFile != null) {
                Resource tgtResource = getEditingDomain().getResourceSet()
                        .createResource(
                                URI.createPlatformResourceURI(diagramFile
                                        .getFullPath().toString(), true));

                if (tgtResource != null) {
                    // Attach URI handler to target so profile uris can
                    // be updated
                    if (tgtResource instanceof XMLResource) {
                        attachURIHandler((XMLResource) tgtResource);
                    }

                    // Get all objects to be copied from the source
                    List<EObject> objsToImport = new ArrayList<EObject>();

                    for (EObject eo : umlResource.getContents()) {
                        if (eo instanceof Model
                                || eo instanceof DynamicEObjectImpl) {
                            objsToImport.add(eo);
                        }
                    }

                    // Copy all objects
                    Collection<EObject> copyObjs = EcoreUtil
                            .copyAll(objsToImport);
                    EList<EObject> contents = tgtResource.getContents();
                    URI targetURI = tgtResource.getURI();

                    // Add all copied objects to the target resource
                    for (EObject obj : copyObjs) {
                        contents.add(obj);
                        obj.eResource().setURI(targetURI);

                        if (obj instanceof Model) {
                            Model model = (Model) obj;
                            // Add the diagram for this model
                            CreateDiagramCommand cmd = new CreateDiagramCommand(
                                    getEditingDomain(),
                                    getLabel(),
                                    model,
                                    CanvasPackageEditPart.MODEL_ID,
                                    BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
                            IStatus status = cmd.execute(monitor, info);

                            if (status.isOK()) {
                                Diagram value = (Diagram) cmd
                                        .getCommandResult().getReturnValue();
                                value.setName(model.getName());
                                tgtResource.getContents().add(value);
                            } else {
                                throw new ExecutionException(status
                                        .getMessage(), status.getException());
                            }
                        }
                    }

                    // Save the resource
                    try {
                        tgtResource.save(UMLDiagramEditorUtil.getSaveOptions());

                        return CommandResult.newOKCommandResult(diagramFile);
                    } catch (IOException e) {
                        throw new ExecutionException(
                                Messages.ImportUMLWizard_failedToSave_err_message,
                                e);
                    }
                }
            }

            return CommandResult
                    .newErrorCommandResult(Messages.ImportUMLWizard_failedToImport_err_message);
        }

        /**
         * Attach an URI handler to the resource being created so that all
         * references to profiles can be deresolved.
         * 
         * @param res
         */
        private void attachURIHandler(XMLResource res) {
            if (res != null) {
                res.getDefaultSaveOptions().put(XMLResource.OPTION_URI_HANDLER,
                        new URIHandlerImpl() {
                            @Override
                            public URI deresolve(URI uri) {
                                if (uri.isFile()) {
                                    String fragment = uri.fragment();

                                    URI deresolvedUri = URI.createURI(uri
                                            .lastSegment());
                                    if (fragment != null) {
                                        deresolvedUri = deresolvedUri
                                                .appendFragment(fragment);
                                    }
                                    return deresolvedUri;
                                }
                                return super.deresolve(uri);
                            }
                        });
            }
        }
    }

    private class ImportProfilesDialog extends MessageDialog {

        private final Collection<Profile> profiles;

        public ImportProfilesDialog(Shell parentShell,
                Collection<Profile> profiles, String srcFileName) {

            super(
                    parentShell,
                    Messages.ImportUMLWizard_importProfiles_dialog_title,
                    null,
                    String
                            .format(
                                    Messages.ImportUMLWizard_importProfiles_dialog_message,
                                    srcFileName), MessageDialog.QUESTION,
                    new String[] { IDialogConstants.YES_LABEL,
                            IDialogConstants.NO_LABEL,
                            IDialogConstants.CANCEL_LABEL }, 0);
            this.profiles = profiles;
        }

        @Override
        protected Control createCustomArea(Composite parent) {
            /*
             * Display the profiles to be imported in a table view.
             */
            Composite root = new Composite(parent, SWT.NONE);
            GridData gData = new GridData(SWT.FILL, SWT.FILL, true, true);
            gData.heightHint = 150;
            root.setLayoutData(gData);
            FillLayout layout = new FillLayout();
            layout.marginHeight = 0;
            layout.marginWidth = 10;
            root.setLayout(layout);

            TableViewer viewer = new TableViewer(root);
            TableColumn col = new TableColumn(viewer.getTable(), SWT.NONE);
            col.setText(Messages.ImportUMLWizard_nameColumn_title);
            col.setWidth(100);
            col = new TableColumn(viewer.getTable(), SWT.NONE);
            col.setText(Messages.ImportUMLWizard_locationColumn_title);
            col.setWidth(275);
            viewer.getTable().setHeaderVisible(true);
            viewer.getTable().setLinesVisible(true);

            viewer.setLabelProvider(new DlgLabelProvider());
            viewer.add(profiles.toArray());

            return root;
        }

        /**
         * Import Profile dialog's label provider.
         * 
         * @author njpatel
         * 
         */
        private class DlgLabelProvider extends LabelProvider implements
                ITableLabelProvider {

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(
             * java.lang.Object, int)
             */
            public Image getColumnImage(Object element, int columnIndex) {
                return null;
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java
             * .lang.Object, int)
             */
            public String getColumnText(Object element, int columnIndex) {
                Profile profile = (Profile) element;
                switch (columnIndex) {
                case 0:
                    return profile.getName();
                case 1:
                    Resource res = profile.eResource();
                    if (res != null) {
                        URI uri = res.getURI();
                        if (uri != null) {
                            return uri.isFile() ? uri.toFileString() : uri
                                    .toString();
                        }
                    }
                }
                return null;
            }

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.importexport.importWizard.IImportWizardPageProvider2
     * #validateResourceSelection(java.util.List)
     */
    public IStatus validateResourceSelection(List<?> selectedResources) {

        if (selectedResources != null && !selectedResources.isEmpty()) {
            for (Object res : selectedResources) {
                if (res instanceof FileSystemElement) {
                    Object obj = ((FileSystemElement) res)
                            .getFileSystemObject();

                    if (obj instanceof File) {
                        File file = (File) obj;

                        URI uri = URI.createFileURI(file.getAbsolutePath());
                        Resource resource = editingDomain.loadResource(uri
                                .toString());

                        if (resource != null
                                && (resource.getErrors() == null || resource
                                        .getErrors().isEmpty())) {
                            try {
                                if (!hasModel(editingDomain, resource)) {
                                    return new Status(
                                            IStatus.ERROR,
                                            Activator.PLUGIN_ID,
                                            String
                                                    .format(
                                                            Messages.ImportUMLWizard_selectionDoesNotContainModel_error_shortdesc,
                                                            file.getName()));
                                }
                            } catch (InterruptedException e) {
                                // Do nothing
                            }
                        } else {
                            return new Status(
                                    IStatus.ERROR,
                                    Activator.PLUGIN_ID,
                                    String
                                            .format(
                                                    Messages.ImportUMLWizard_unableToLoadModelFromSelection_error_shortdesc,
                                                    file.getName()));
                        }
                    }
                }
            }
        }

        return Status.OK_STATUS;
    }
}
