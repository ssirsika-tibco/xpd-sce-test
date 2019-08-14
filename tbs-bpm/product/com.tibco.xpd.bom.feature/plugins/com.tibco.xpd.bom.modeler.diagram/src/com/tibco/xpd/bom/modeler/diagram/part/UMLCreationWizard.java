/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.part;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.modeler.diagram.part.custom.firstclassprofiles.BOMTypesWizardPage;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.resources.firstclassprofiles.IFirstClassProfileExtension;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.wizard.TemplatesWizardPage;
import com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard;

/**
 * @generated NOT
 */
public class UMLCreationWizard extends BasicNewXpdResourceWizard implements
        IExecutableExtension {

    /**
     * @generated
     */
    protected UMLCreationWizardPage diagramModelFilePage;

    protected TemplatesWizardPage templatesPage;

    /**
     * @generated NOT
     */
    protected BOMTypesWizardPage bomTypesPage;

    private IFirstClassProfileExtension extension;

    /**
     * First-class profile extensions manager
     */
    private final FirstClassProfileManager firstClassManager =
            FirstClassProfileManager.getInstance();

    /**
     * @generated
     */
    protected Resource diagram;

    /**
     * @generated
     */
    private boolean openNewlyCreatedDiagramEditor = true;

    /**
     * @generated
     */
    public final Resource getDiagram() {
        return diagram;
    }

    /**
     * @generated
     */
    public final boolean isOpenNewlyCreatedDiagramEditor() {
        return openNewlyCreatedDiagramEditor;
    }

    /**
     * @generated
     */
    public void setOpenNewlyCreatedDiagramEditor(
            boolean openNewlyCreatedDiagramEditor) {
        this.openNewlyCreatedDiagramEditor = openNewlyCreatedDiagramEditor;
    }

    /**
     * @generated NOT
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        super.init(workbench, selection);
        setWindowTitle(Messages.UMLCreationWizardTitle);
        setDefaultPageImageDescriptor(BOMDiagramEditorPlugin
                .getBundledImageDescriptor("icons/wizban/NewBOMModel.png")); //$NON-NLS-1$
        setNeedsProgressMonitor(true);
    }

    /**
     * @generated NOT
     */
    @Override
    public void addPages() {

        addPagesGen();

        // Add a new page for 1st Class Support of profile.
        // This page will show a drop down list of all
        // the different types of BOMs that can be created
        if (extension == null) {

            List<IFirstClassProfileExtension> profileExts =
                    FirstClassProfileManager.getInstance().getExtensions();
            Set<String> excludedExts =
                    Collections.<String> singleton(BOMUtils.CDS_EXTENSION_ID);

            profileExts =
                    BOMUtils.getCreatableProfileExtensions(profileExts,
                            excludedExts);
            if ((profileExts != null) && !profileExts.isEmpty()) {

                // No extension has been defined in the initializing data so the
                // user selected the BOM wizard - so show page to select model
                // types
                // (if any)

                bomTypesPage = new BOMTypesWizardPage("DiagramModelBOMType"); //$NON-NLS-1$
                bomTypesPage
                        .setTitle(Messages.UMLCreationWizard_DiagramModelFilePageTitle);
                bomTypesPage
                        .setDescription(Messages.UMLCreationWizard_BOMTypePageDescription);
                addPage(bomTypesPage);
            }
        }

        /*
         * If first-class profile extensions are available then the template
         * page will be added by the BOMTypesWizardPage as it is a wizard
         * selection page, in which case the templatesPage here is just for
         * sizing the wizard. If no first-class profiles are registered then
         * this will be used as the templates page.
         */
        templatesPage =
                new TemplatesWizardPage(
                        "templates", //$NON-NLS-1$
                        Messages.UMLCreationWizard_templatesPage_title, null,
                        new String[] { "com.tibco.xpd.bom.fragments" }); //$NON-NLS-1$
        templatesPage
                .setDescription(Messages.UMLCreationWizard_templatesPage_shortdesc);
        templatesPage.setSelectTemplateCheck(false);

        addPage(templatesPage);
    }

    /**
     * @generated
     */
    public void addPagesGen() {

        diagramModelFilePage =
                new UMLCreationWizardPage(
                        "DiagramModelFile", getSelection(), "bom"); //$NON-NLS-1$ //$NON-NLS-2$
        diagramModelFilePage
                .setTitle(Messages.UMLCreationWizard_DiagramModelFilePageTitle);
        diagramModelFilePage
                .setDescription(Messages.UMLCreationWizard_DiagramModelFilePageDescription);

        if (extension != null) {
            diagramModelFilePage.setTitle(String
                    .format(Messages.UMLCreationWizard_createModel_shortdesc,
                            extension.getLabel()));
            setWindowTitle(String
                    .format(Messages.UMLCreationWizard_newModelType,
                            extension.getLabel()));
        }
        addPage(diagramModelFilePage);
    }

    /**
     * Get the URI of the model diagram to create.
     * 
     * @return
     * @since 3.5
     */
    protected URI getModelDiagramURI() {
        return diagramModelFilePage != null ? diagramModelFilePage.getURI()
                : null;
    }

    /**
     * Get the label to give the new model. Default implementation returns
     * <code>null</code>, subclasses may override to provide a label.
     * 
     * @return the label to give the model, <code>null</code> to use default
     *         display label.
     * @since 3.5
     */
    protected String getModelDisplayName() {
        return null;
    }

    /**
     * @generated NOT
     */
    @Override
    public boolean performFinish() {
        IRunnableWithProgress op = new WorkspaceModifyOperation(null) {

            @Override
            protected void execute(IProgressMonitor monitor)
                    throws CoreException, InterruptedException {

                // Get first class support choice. Will be null for
                // a standard BOM type
                if (extension == null) {
                    extension = getChosenExtension();
                }

                IFragment template = null;
                /*
                 * Check if a template was selected by first checking bom types
                 * page, otherwise ask the template page
                 */
                if (bomTypesPage != null) {
                    if (bomTypesPage.isApplyTemplate()) {
                        template = bomTypesPage.getTemplate();
                    }
                } else if (templatesPage != null) {
                    if (templatesPage.applyTemplate()) {
                        template = templatesPage.getTemplate();
                    }
                }

                URI diagramURI = getModelDiagramURI();
                Assert.isNotNull(diagramURI, "Model diagram URI is null."); //$NON-NLS-1$
                String modelLabel = getModelDisplayName();

                diagram =
                        UMLDiagramEditorUtil.createDiagram(diagramURI,
                                modelLabel,
                                monitor,
                                extension,
                                template);

                if (isOpenNewlyCreatedDiagramEditor() && diagram != null) {
                    try {
                        UMLDiagramEditorUtil.openDiagram(diagram);
                    } catch (PartInitException e) {
                        ErrorDialog.openError(getContainer().getShell(),
                                Messages.UMLCreationWizardOpenEditorError,
                                null,
                                e.getStatus());
                    }
                }
            }
        };
        try {
            getContainer().run(false, true, op);
            // Expand the created bom file in the project explorer
            if (diagram != null) {
                Model model = getModel(diagram);
                if (model != null) {
                    selectAndReveal(model);
                }
            }
        } catch (InterruptedException e) {
            return false;
        } catch (InvocationTargetException e) {
            ErrorDialog
                    .openError(getContainer().getShell(),
                            Messages.UMLCreationWizardCreationError,
                            e.getLocalizedMessage(),
                            e.getTargetException() instanceof CoreException ? ((CoreException) e
                                    .getTargetException()).getStatus()
                                    : new Status(IStatus.ERROR,
                                            BOMDiagramEditorPlugin.ID, e
                                                    .getLocalizedMessage(), e
                                                    .getTargetException()));
            return false;
        }
        return diagram != null;
    }

    /**
     * Get the {@link Model} from the newly created BOM resource.
     * 
     * @param res
     * @return
     */
    private Model getModel(Resource res) {
        if (res != null) {
            for (EObject eo : res.getContents()) {
                if (eo instanceof Model) {
                    return (Model) eo;
                }
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org
     * .eclipse.core.runtime.IConfigurationElement, java.lang.String,
     * java.lang.Object)
     */
    @Override
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {

        if (data instanceof String) {
            extension = firstClassManager.getExtensionById((String) data);
        }

    }

    @Override
    public IWizardPage getNextPage(IWizardPage page) {
        // Overide this method so that we can check for first class
        // profile support and set parameters for the following
        // wizard pages accordingly

        IWizardPage nextPage = super.getNextPage(page);

        if (nextPage != null) {
            if (canFinish()) {
                if (nextPage.equals(diagramModelFilePage)) {
                    /*
                     * Set the filename and file extension depending on whether
                     * first class profile extensions exist
                     */
                    // Get the radio button choice from the
                    // UMLCreationBOMTypeWizardPage page
                    IFirstClassProfileExtension ext = getChosenExtension();

                    if (ext != null) {
                        String fileName = ext.getLabel().replace(" ", ""); //$NON-NLS-1$ //$NON-NLS-2$
                        // Set the filename from the extension label (with
                        // whitespace removed)
                        diagramModelFilePage.setFileName(UMLDiagramEditorUtil
                                .getUniqueFileName(diagramModelFilePage
                                        .getContainerFullPath(),
                                        fileName,
                                        BOMResourcesPlugin.BOM_FILE_EXTENSION));

                    }
                }
            } else {
                nextPage = null;
            }
        }

        return nextPage;
    }

    @Override
    public void createPageControls(Composite pageContainer) {
        super.createPageControls(pageContainer);

        if (extension != null) {
            String fileName = extension.getLabel().replace(" ", ""); //$NON-NLS-1$ //$NON-NLS-2$
            // Set the filename from the extension label (with
            // whitespace removed)
            diagramModelFilePage.setFileName(UMLDiagramEditorUtil
                    .getUniqueFileName(diagramModelFilePage
                            .getContainerFullPath(),
                            fileName,
                            BOMResourcesPlugin.BOM_FILE_EXTENSION));
        }
    }

    /**
     * 
     * Returns the FirstClassProfileExtension object relevant to the radio
     * button selection on the BOM type selection wizard page.
     * 
     * @return FirstClassProfileExtension
     */
    private IFirstClassProfileExtension getChosenExtension() {
        // Get the radio button choice from the
        // UMLCreationBOMTypeWizardPage page
        IFirstClassProfileExtension ext = null;
        if (bomTypesPage != null) {
            String id = bomTypesPage.getSelectedExtensionId();
            if (id != null) {
                ext = firstClassManager.getExtensionById(id);
            }
        }

        return ext;
    }

    @Override
    public boolean canFinish() {

        if (diagramModelFilePage != null) {

            IProject project = null;

            if (null != diagramModelFilePage.getFilePath()) {

                project =
                        ResourcesPlugin
                                .getWorkspace()
                                .getRoot()
                                .getProject(diagramModelFilePage.getFilePath()
                                        .segments()[0]);
            }


            /*
             * There is a problem (currently if there are spaces or other
             * characters that get encoded when converted to URI).
             * 
             * This in the fact that some things (especially xpdl process
             * references to BOM files) store the path as a URI BUT not all
             * readers read it as a URI and hence try to use the encoded URI
             * (with %20 etc) as a file name which then fails.
             * 
             * So this short term fix is to ensure that the encoded name is the
             * same as the original - then we should be ok.
             */
            String resourceName = diagramModelFilePage.getFileName();

            if (resourceName != null) {

                String encodedPathString =
                        URI.encodeFragment(resourceName, false);

                if (!resourceName.equals(encodedPathString)) {

                    diagramModelFilePage
                            .setErrorMessage(Messages.UMLCreationWizard_InvalidFileNameErr_shortdesc);
                    return false;
                }
            }
        }

        return super.canFinish();
    }

}
