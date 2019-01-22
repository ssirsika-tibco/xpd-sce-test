/*
 * Copyright (c) TIBCO Software Inc 2008. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.wizards;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.EnumValue;
import com.tibco.xpd.om.core.om.LocationType;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitRelationshipType;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.core.om.PositionType;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.om.resources.OMResourcesActivator;
import com.tibco.xpd.om.resources.ui.OMResourcesUIActivator;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;

public class OMMetaCreationWizard extends Wizard implements INewWizard {

    /** Default meta model file name. */
    private static final String DEFAULT_OM_META_BASE_FILE_NAME = Messages.OMMetaCreationWizard_defaultFileName_label;

    private static final String OM_MODEL_WIZBAN = "icons/wizban/NewOMModel.png"; //$NON-NLS-1$

    private IWorkbench workbench;

    protected IStructuredSelection selection;

    protected FileLocationWizardPage fileLocationFilePage;

    protected Resource resource;

    private boolean openNewlyCreatedDiagramEditor = true;

    public IWorkbench getWorkbench() {
        return workbench;
    }

    public IStructuredSelection getSelection() {
        return selection;
    }

    public final Resource getResource() {
        return resource;
    }

    public final boolean isOpenNewlyCreatedDiagramEditor() {
        return openNewlyCreatedDiagramEditor;
    }

    public void setOpenNewlyCreatedDiagramEditor(
            boolean openNewlyCreatedDiagramEditor) {
        this.openNewlyCreatedDiagramEditor = openNewlyCreatedDiagramEditor;
    }

    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        this.selection = selection;
        setWindowTitle(Messages.OMMetaCreationWizard_title);
        setDefaultPageImageDescriptor(OMResourcesUIActivator
                .imageDescriptorFromPlugin(OMResourcesUIActivator.PLUGIN_ID,
                        OM_MODEL_WIZBAN));
        setNeedsProgressMonitor(true);
    }

    @Override
    public void addPages() {
        fileLocationFilePage = new FileLocationWizardPage(
                Messages.OMMetaCreationWizard_shortdesc, getSelection(),
                OMUtil.OM_META_FILE_EXTENSION);
        fileLocationFilePage
                .setTitle(Messages.OMMetaCreationWizard_filePage_title);
        fileLocationFilePage
                .setDescription(Messages.OMMetaCreationWizard_filePage_description);
        fileLocationFilePage.setFileName(DEFAULT_OM_META_BASE_FILE_NAME);
        // Add selection validator to warn user if file not created in OM
        // special folder
        fileLocationFilePage
                .setSpecialFolderSelectionValidator(
                        OMResourcesActivator.OM_SPECIAL_FOLDER_KIND,
                        new Status(
                                IStatus.WARNING,
                                OMResourcesUIActivator.PLUGIN_ID,
                                Messages.OMCreationWizardPage_notOMSpecialFolder_longdesc));
        addPage(fileLocationFilePage);
    }

    @Override
    public boolean performFinish() {
        IRunnableWithProgress op = new WorkspaceModifyOperation(null) {

            @Override
            protected void execute(IProgressMonitor monitor)
                    throws CoreException, InterruptedException {
                resource = createResource(fileLocationFilePage.getURI(),
                        getDefaultModelName(), monitor);

            }
        };
        try {
            getContainer().run(false, true, op);
        } catch (InterruptedException e) {
            return false;
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof CoreException) {
                ErrorDialog.openError(getContainer().getShell(),
                        Messages.OMMetaCreationWizard_error_title, null,
                        ((CoreException) e.getTargetException()).getStatus());
            } else {
                OMResourcesUIActivator.getDefault().getLogger().error(
                        e.getTargetException(), "Error while creating model"); //$NON-NLS-1$
            }
            return false;
        }
        return resource != null;
    }

    /**
     * This method should be called within a workspace modify operation since it
     * creates resources.
     * 
     */
    public static Resource createResource(URI resourceURI,
            final String modelName, IProgressMonitor progressMonitor) {
        TransactionalEditingDomain editingDomain = XpdResourcesPlugin
                .getDefault().getEditingDomain();
        progressMonitor.beginTask(
                Messages.OMMetaCreationWizard_creationTask_shortdesc, 3);
        final ResourceSet resourceSet = editingDomain.getResourceSet();
        final Resource resource = resourceSet.createResource(resourceURI);

        AbstractTransactionalCommand command = new AbstractTransactionalCommand(
                editingDomain,
                Messages.OMMetaCreationWizard_creationCommand_menu,
                Collections.EMPTY_LIST) {
            @Override
            protected CommandResult doExecuteWithResult(
                    IProgressMonitor monitor, IAdaptable info)
                    throws ExecutionException {

                List<EObject> model = createInitialModel(modelName);
                for (EObject modelObject : model) {
                    resource.getContents().add(modelObject);
                }

                try {
                    resource.save(getSaveOptions());
                } catch (IOException e) {

                    OMResourcesUIActivator.getDefault().getLogger().error(e,
                            "Unable to store model."); //$NON-NLS-1$
                }
                return CommandResult.newOKCommandResult();
            }

            /**
             * @generated
             */
            @Override
            public boolean canUndo() {
                // Don't want this command to be undo-able
                return false;
            }

        };
        try {
            OperationHistoryFactory.getOperationHistory().execute(command,
                    new SubProgressMonitor(progressMonitor, 1), null);
        } catch (ExecutionException e) {
            OMResourcesUIActivator.getDefault().getLogger().error(e,
                    "Unable to create model."); //$NON-NLS-1$
        }

        setCharset(WorkspaceSynchronizer.getFile(resource));
        return resource;
    }

    private String getDefaultModelName() {
        return fileLocationFilePage.getFilePath().removeFileExtension()
                .removeFileExtension().lastSegment();
    }

    public static void setCharset(IFile file) {
        if (file == null) {
            return;
        }
        try {
            file.setCharset("UTF-8", new NullProgressMonitor()); //$NON-NLS-1$
        } catch (CoreException e) {
            OMResourcesUIActivator.getDefault().getLogger().error(e,
                    "Unable to set charset for file " + file.getFullPath()); //$NON-NLS-1$
        }
    }

    @SuppressWarnings("unchecked")
    public static Map getSaveOptions() {
        Map saveOptions = new HashMap();
        saveOptions.put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
        saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED,
                Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
        return saveOptions;
    }

    private static List<EObject> createInitialModel(String modelName) {
        OMFactory f = OMFactory.eINSTANCE;

        OrgMetaModel model = f.createOrgMetaModel();
        model.setDisplayName(modelName);
        // createStandardTypes(model);

        return Collections.singletonList((EObject) model);
    }

    /**
     * Creates standard types for OM model.
     * 
     * @param model
     *            organisation meta model.
     */

    @SuppressWarnings("nls")
    protected static void createStandardTypes(OrgMetaModel model) {

        OMFactory f = OMFactory.eINSTANCE;

        // Position Type
        PositionType standardPositionType = f.createPositionType();
        standardPositionType
                .setDisplayName(Messages.OMMetaCreationWizard_stdPositionType_label);

        Attribute kindAttributeFeature = f.createAttribute();
        kindAttributeFeature
                .setDisplayName(Messages.OMMetaCreationWizard_kindAttribute_label);
        kindAttributeFeature.setType(AttributeType.ENUM);
        String[] kindValues = new String[] {
                Messages.OMMetaCreationWizard_temporaryKind_label,
                Messages.OMMetaCreationWizard_permanentKind_label,
                Messages.OMMetaCreationWizard_contractKind_label };
        for (String value : kindValues) {
            EnumValue kindValue = f.createEnumValue();
            kindValue.setValue(value);
            kindAttributeFeature.getValues().add(kindValue);
        }
        standardPositionType.getAttributes().add(kindAttributeFeature);

        model.getPositionTypes().add(standardPositionType);

        // OrgUnitRelationship Type
        OrgUnitRelationshipType standardOrgUnitRelType = f
                .createOrgUnitRelationshipType();
        standardOrgUnitRelType
                .setDisplayName(Messages.OMMetaCreationWizard_stdOrgUnitRelationshipType_label);
        model.getOrgUnitRelationshipTypes().add(standardOrgUnitRelType);

        // OrgUnit Type
        OrgUnitType standardOrgUnitType = f.createOrgUnitType();
        standardOrgUnitType
                .setDisplayName(Messages.OMMetaCreationWizard_stdOrgUnitType_label);

        OrgUnitFeature subOrgUnitFeature = f.createOrgUnitFeature();
        subOrgUnitFeature
                .setDisplayName(Messages.OMMetaCreationWizard_subOrgUnitFeature_label);
        subOrgUnitFeature.setLowerBound(0);
        subOrgUnitFeature.setUpperBound(-1);
        subOrgUnitFeature.setFeatureType(standardOrgUnitType);
        subOrgUnitFeature.setContextRelationshipType(standardOrgUnitRelType);

        PositionFeature managerPositionFeature = f.createPositionFeature();
        managerPositionFeature
                .setDisplayName(Messages.OMMetaCreationWizard_managerPosition_label);
        managerPositionFeature.setLowerBound(0);
        managerPositionFeature.setUpperBound(1);
        managerPositionFeature.setFeatureType(standardPositionType);

        PositionFeature memberPositionFeature = f.createPositionFeature();
        memberPositionFeature
                .setDisplayName(Messages.OMMetaCreationWizard_memberPosition_label);
        memberPositionFeature.setLowerBound(0);
        memberPositionFeature.setUpperBound(-1);
        memberPositionFeature.setFeatureType(standardPositionType);

        standardOrgUnitType.getOrgUnitFeatures().add(subOrgUnitFeature);
        standardOrgUnitType.getPositionFeatures().add(managerPositionFeature);
        standardOrgUnitType.getPositionFeatures().add(memberPositionFeature);

        model.getOrgUnitTypes().add(standardOrgUnitType);

        // Organisation Type
        OrganizationType standardOrganisationType = f.createOrganizationType();
        standardOrganisationType
                .setDisplayName(Messages.OMMetaCreationWizard_stdOrgType_label);

        OrgUnitFeature headOrgUnitFeature = f.createOrgUnitFeature();
        headOrgUnitFeature
                .setDisplayName(Messages.OMMetaCreationWizard_headOrgUnit_label);
        headOrgUnitFeature.setLowerBound(0);
        headOrgUnitFeature.setUpperBound(1);
        headOrgUnitFeature.setFeatureType(standardOrgUnitType);
        headOrgUnitFeature.setContextRelationshipType(standardOrgUnitRelType);

        OrgUnitFeature memberOrgUnitFeature = f.createOrgUnitFeature();
        memberOrgUnitFeature
                .setDisplayName(Messages.OMMetaCreationWizard_memberOrgUnit_label);
        memberOrgUnitFeature.setLowerBound(0);
        memberOrgUnitFeature.setUpperBound(-1);
        memberOrgUnitFeature.setFeatureType(standardOrgUnitType);
        memberOrgUnitFeature.setContextRelationshipType(standardOrgUnitRelType);

        standardOrganisationType.getOrgUnitFeatures().add(headOrgUnitFeature);
        standardOrganisationType.getOrgUnitFeatures().add(memberOrgUnitFeature);

        model.getOrganizationTypes().add(standardOrganisationType);

        // Location Type
        LocationType locationType = f.createLocationType();
        locationType
                .setDisplayName(Messages.OMMetaCreationWizard_stdLocationType_label);
        String[] stringLocationParameters = new String[] {
                Messages.OMMetaCreationWizard_countryParameter_label,
                Messages.OMMetaCreationWizard_stateParameter_label,
                Messages.OMMetaCreationWizard_cityParameter_label,
                Messages.OMMetaCreationWizard_streetProperty_label };
        for (String paramName : stringLocationParameters) {
            Attribute paramFeature = f.createAttribute();
            paramFeature.setDisplayName(paramName);
            paramFeature.setType(AttributeType.TEXT);
            locationType.getAttributes().add(paramFeature);
        }
        model.getLocationTypes().add(locationType);
    }
}
