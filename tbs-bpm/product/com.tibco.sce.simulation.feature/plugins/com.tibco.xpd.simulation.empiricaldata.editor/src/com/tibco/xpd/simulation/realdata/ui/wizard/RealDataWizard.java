/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.realdata.ui.wizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.xmi.XMLResource;

import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.jface.dialogs.MessageDialog;

import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.IStructuredSelection;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import org.eclipse.ui.actions.WorkspaceModifyOperation;

import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ISetSelectionTarget;

import com.tibco.xpd.simulation.EmpircalDataEditorPlugin;
import com.tibco.xpd.simulation.empiricaldata.EmpiricalDataFactory;
import com.tibco.xpd.simulation.empiricaldata.EmpiricalDataPackage;
import com.tibco.xpd.simulation.realdata.ui.RealDataModelWizard.RealDataModelWizardNewFileCreationPage;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

/**
 * This is a simple wizard for creating a new model file. <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class RealDataWizard extends Wizard implements INewWizard {

    private static final String TXT_IMPORT_TYPE = "txtImportType";

    private static final String XLS_IMPORT_TYPE = "xlsImportType";

    private static final String NEW_FILE_CREATION_PAGE = "NewFileCreationPage";

    private static final String SELECT_IMPORT_TYPE_PAGE = "SelectImportTypePage";

    private static final String CHOOSE_PARAMETERS_PAGE = "ChooseParametersPage";

    private static final String SELECT_PERIODS_PAGE = "SelectPeriodsPage";
    
    private static final String PERIODS_DATA_EDITOR_PAGE = "PeriodsDataEditorPage";

    /**
     */
    public static final String copyright = "TIBCO Software Inc.";

    /**
     * Import types.
     */
    private String[] importTypes = new String[] { XLS_IMPORT_TYPE, //$NON-NLS-1$
            TXT_IMPORT_TYPE }; //$NON-NLS-1$

    /** Current selected Import type */
    private String currentImportType = null;

    private RealDataModelImporter modelImporter;

    /**
     * This is the file creation page.
     */
    protected NewFileCreationPage newFileCreationPage;

    /**
     * This is the file creation page.
     */
    private SelectImportTypePage importTypePage;

    /**
     * This is the page with xsl import properties.
     */
    private XlsImportPropertiesPage xslImportPropertiesPage;

    /**
     * This is the page with txt import properties.
     */
    private TxtImportPropertiesPage txtImportPropertiesPage;

    /**
     * This is the page with choosing parameters from imported.
     */
    private ChooseParametersPage chooseParametersPage;
    
    /**
     * Remember the selection during initialization for populating the default
     * container.
     */
    protected IStructuredSelection selection;

    /**
     * Remember the workbench during initialization.
     */
    protected IWorkbench workbench;

    /**
     * Caches the names of the features representing global elements.
     */
    protected List initialObjectNames;


    public RealDataWizard() {
        modelImporter = new RealDataModelImporter();
    }

    /**
     * This just records the information. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        this.selection = selection;
        setWindowTitle(EmpircalDataEditorPlugin.INSTANCE
                .getString("_UI_Wizard_label"));
        setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE
                .getImageDescriptor(EmpircalDataEditorPlugin.INSTANCE
                        .getImage("full/wizban/NewEmpiricalData")));
    }

    /**
     * Do the work after everything is specified.
     */
    public boolean performFinish() {
        try {
            // Remember the file.
            //
            final IFile modelFile = getModelFile();

            // Do the work within an operation.
            //
            WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
                protected void execute(IProgressMonitor progressMonitor) {
                    try {
                        // Create a resource set
                        //
                        ResourceSet resourceSet = new ResourceSetImpl();

                        // Get the URI of the model file.
                        //
                        URI fileURI = URI.createPlatformResourceURI(modelFile
                                .getFullPath().toString());

                        // Create a resource for this file.
                        //
                        Resource resource = resourceSet.createResource(fileURI);

                        //Create the model from DataTable
                        modelImporter.createCasesFromTableData();

                        // Add the initial model object to the contents.
                        //
                        EObject rootObject = modelImporter.getRootObject();
                        if (rootObject != null) {
                            resource.getContents().add(rootObject);
                        }

                        // Save the contents of the resource to the file system.
                        //
                        Map options = new HashMap();
                        options.put(XMLResource.OPTION_ENCODING,
                                getFileEncoding());
                        resource.save(options);
                    } catch (Exception exception) {
                        EmpircalDataEditorPlugin.INSTANCE.log(exception);
                    } finally {
                        progressMonitor.done();
                    }
                }
            };

            getContainer().run(false, false, operation);

            // Select the new file resource in the current view.
            //
            IWorkbenchWindow workbenchWindow = workbench
                    .getActiveWorkbenchWindow();
            IWorkbenchPage page = workbenchWindow.getActivePage();
            final IWorkbenchPart activePart = page.getActivePart();
            if (activePart instanceof ISetSelectionTarget) {
                final ISelection targetSelection = new StructuredSelection(
                        modelFile);
                getShell().getDisplay().asyncExec(new Runnable() {
                    public void run() {
                        ((ISetSelectionTarget) activePart)
                                .selectReveal(targetSelection);
                    }
                });
            }

            // Open an editor on the new file.
            //
            try {
                page.openEditor(new FileEditorInput(modelFile), workbench
                        .getEditorRegistry().getDefaultEditor(
                                modelFile.getFullPath().toString()).getId());
            } catch (PartInitException exception) {
                MessageDialog.openError(workbenchWindow.getShell(),
                        EmpircalDataEditorPlugin.INSTANCE
                                .getString("_UI_OpenEditorError_label"),
                        exception.getMessage());
                return false;
            }

            return true;
        } catch (Exception exception) {
            EmpircalDataEditorPlugin.INSTANCE.log(exception);
            return false;
        }
    }

    public IWizardPage getNextPage(IWizardPage page) {
        if (page instanceof ImportPropertiesPage) {
            return getPage(CHOOSE_PARAMETERS_PAGE);
        }
        if (SELECT_IMPORT_TYPE_PAGE.equals(page.getName())) {
            return getPage(currentImportType);
        }
        return super.getNextPage(page);
    }

    public IWizardPage getPreviousPage(IWizardPage page) {
        if (page instanceof ImportPropertiesPage) {
            return getPage(SELECT_IMPORT_TYPE_PAGE);
        }
        if (CHOOSE_PARAMETERS_PAGE.equals(page.getName())) {
            return getPage(currentImportType);
        }
        return super.getPreviousPage(page);
    }

    /**
     * The framework calls this to create the contents of the wizard.
     */
    public void addPages() {

        createImportTypePage();
        addPage(importTypePage);

        createXslImportPropertiesPage();
        addPage(xslImportPropertiesPage);

        createTxtImportPropertiesPage();
        addPage(txtImportPropertiesPage);

        createChooseParametersPage();
        addPage(chooseParametersPage);

        createNewFileCreationPage();
        addPage(newFileCreationPage);

    }

    private void createChooseParametersPage() {
        if (chooseParametersPage == null) {
            chooseParametersPage = new ChooseParametersPage(modelImporter,
                    CHOOSE_PARAMETERS_PAGE);
            chooseParametersPage.setTitle(EmpircalDataEditorPlugin.INSTANCE
                    .getString("_UI_RealDataModelWizard_ChooseParameters_label")); //$NON-NLS-1$
            chooseParametersPage
                    .setDescription(EmpircalDataEditorPlugin.INSTANCE
                            .getString("_UI_RealDataModelWizard_ChooseParameters_description")); //$NON-NLS-1$
        }

    }

    private void createImportTypePage() {
        if (importTypePage == null) {
            importTypePage = new SelectImportTypePage(this,
                    SELECT_IMPORT_TYPE_PAGE);
            importTypePage.setTitle(EmpircalDataEditorPlugin.INSTANCE
                    .getString("_UI_RealDataModelWizard_SelectImport_label"));
            importTypePage.setDescription(EmpircalDataEditorPlugin.INSTANCE
                    .getString("_UI_RealDataModelWizard_SelectImport_description"));
        }
    }

    private void createXslImportPropertiesPage() {
        if (xslImportPropertiesPage == null) {
            xslImportPropertiesPage = new XlsImportPropertiesPage(
                    modelImporter, XLS_IMPORT_TYPE);
            xslImportPropertiesPage.setTitle(EmpircalDataEditorPlugin.INSTANCE
                    .getString("_UI_RealDataModelWizard_XlsImportProperties_label"));
            xslImportPropertiesPage
                    .setDescription(EmpircalDataEditorPlugin.INSTANCE
                            .getString("_UI_RealDataModelWizard_XlsImportProperties_description"));
        }
    }

    private void createTxtImportPropertiesPage() {
        if (txtImportPropertiesPage == null) {
            txtImportPropertiesPage = new TxtImportPropertiesPage(
                    modelImporter, TXT_IMPORT_TYPE);
            txtImportPropertiesPage.setTitle(EmpircalDataEditorPlugin.INSTANCE
                    .getString("_UI_RealDataModelWizard_TxtImportProperties_label"));
            txtImportPropertiesPage
                    .setDescription(EmpircalDataEditorPlugin.INSTANCE
                            .getString("_UI_RealDataModelWizard_TxtImportProperties_description"));
        }
    }

    private void createNewFileCreationPage() {
        if (newFileCreationPage == null) {
            newFileCreationPage = new NewFileCreationPage(
                    NEW_FILE_CREATION_PAGE, selection);
            newFileCreationPage.setTitle(EmpircalDataEditorPlugin.INSTANCE
                    .getString("_UI_RealDataModelWizard_NewFileCreation_label"));
            newFileCreationPage.setDescription(EmpircalDataEditorPlugin.INSTANCE
                    .getString("_UI_RealDataModelWizard_NewFileCreation_description"));
            newFileCreationPage.setFileName(EmpircalDataEditorPlugin.INSTANCE
                    .getString("_UI_RealDataEditorFilenameDefaultBase")
                    + "."
                    + EmpircalDataEditorPlugin.INSTANCE
                            .getString("_UI_RealDataEditorFilenameExtension"));
        }
        // Try and get the resource selection to determine a current directory
        // for the file dialog.
        //
        if (selection != null && !selection.isEmpty()) {
            // Get the resource...
            //
            Object selectedElement = selection.iterator().next();
            if (selectedElement instanceof IResource) {
                // Get the resource parent, if its a file.
                //
                IResource selectedResource = (IResource) selectedElement;
                if (selectedResource.getType() == IResource.FILE) {
                    selectedResource = selectedResource.getParent();
                }

                // This gives us a directory...
                //
                if (selectedResource instanceof IFolder
                        || selectedResource instanceof IProject) {
                    // Set this for the container.
                    //
                    newFileCreationPage.setContainerFullPath(selectedResource
                            .getFullPath());

                    // Make up a unique new name here.
                    //
                    String defaultModelBaseFilename = EmpircalDataEditorPlugin.INSTANCE
                            .getString("_UI_RealDataEditorFilenameDefaultBase");
                    String defaultModelFilenameExtension = EmpircalDataEditorPlugin.INSTANCE
                            .getString("_UI_RealDataEditorFilenameExtension");
                    String modelFilename = defaultModelBaseFilename + "."
                            + defaultModelFilenameExtension;
                    for (int i = 1; ((IContainer) selectedResource)
                            .findMember(modelFilename) != null; ++i) {
                        modelFilename = defaultModelBaseFilename + i + "."
                                + defaultModelFilenameExtension;
                    }
                    newFileCreationPage.setFileName(modelFilename);
                }
            }
        }
    }

    /**
     * Get the file from the page.
     */
    public IFile getModelFile() {
        return newFileCreationPage.getModelFile();
    }

    public String getFileEncoding() {
        return "UTF-8"; //$NON-NLS-1$
    }

    public void setCurrentImportType(String importType) {
        currentImportType = importType;
    }

    public String[] getImportTypes() {
        return importTypes;
    }
}
