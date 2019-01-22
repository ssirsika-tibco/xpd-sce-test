/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.dbimport.wizards;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;

import com.tibco.xpd.bom.db.api.DbToBomTransformUtil;
import com.tibco.xpd.bom.dbimport.DBImageConstants;
import com.tibco.xpd.bom.dbimport.DBImportPlugin;
import com.tibco.xpd.bom.dbimport.internal.Messages;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.CanvasPackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditorUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.UserInfoUtil;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog.OverwriteStatus;
import com.tibco.xpd.ui.util.NameUtil;

/**
 * Wizard page which provides a table viewer for selecting the profile from
 * which the BOM needs to be imported.
 * 
 * @author rsomayaj
 * 
 */
public class ProfileSelectImportWizard extends Wizard {

    private IConnectionProfile connectionProfile;

    private ProfileTablesDisplayPage tablesInProfilePage;

    private final Object bomFolder;

    private BOMFileSelectionWizardPage bomFileSelectionPage;

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    public ProfileSelectImportWizard() {
        this(null);
    }

    public ProfileSelectImportWizard(Object bomFolder) {
        this.bomFolder = bomFolder;
        setWindowTitle(Messages.ProfileSelectImportWizard_ImportDBtoBOM_Label);
        setDefaultPageImageDescriptor(DBImportPlugin.getDefault()
                .getImageRegistry()
                .getDescriptor(DBImageConstants.IMG_BOM_IMPORT_WIZARD));
        setNeedsProgressMonitor(true);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     * 
     * @return
     */
    @Override
    public boolean performFinish() {
        final List<?> listOfTables = tablesInProfilePage.getListOfTables();
        final TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        final IPath containerPath = bomFileSelectionPage.getContainerFullPath();
        final IPath newFilePath =
                containerPath.append(bomFileSelectionPage.getFileName());
        /*
         * If existing BOM file is selected, then ask the user if it should be
         * overwritten
         */
        if (!bomFileSelectionPage.isCreateNewBomFile()) {

            OverwriteFileMessageDialog dlg =
                    new OverwriteFileMessageDialog(
                            getShell(),
                            Messages.ProfileSelectImportWizard_ResourceExistsLabel,
                            String.format(Messages.ProfileSelectImportWizard_ConfirmResourceOverWriteMessage,
                                    bomFileSelectionPage.getFileName()));

            OverwriteStatus status = dlg.getOverwriteStatus(dlg.open());
            if (status.isCancelled()) {
                return false;
            } else if (!status.canOverwrite()) {
                return true;
            }
        }

        final Resource[] bomResource = new Resource[] { null };
        IRunnableWithProgress op = new WorkspaceModifyOperation(null) {
            @Override
            protected void execute(final IProgressMonitor monitor)
                    throws CoreException, InvocationTargetException,
                    InterruptedException {
                try {
                    monitor.beginTask(Messages.ProfileSelectImportWizard_Monitor_label,
                            IProgressMonitor.UNKNOWN);
                    monitor.worked(1);
                    bomResource[0] =
                            createBomFile(ed, newFilePath, listOfTables);
                } catch (Exception e) {
                    LOG.error(e);
                } finally {
                    monitor.done();
                }
            }
        };
        try {
            // Display.getCurrent().asyncExec(op);
            getContainer().run(true, true, op);
            getShell().getDisplay().asyncExec(new Runnable() {

                public void run() {
                    if (bomResource[0] != null) {
                        try {
                            UMLDiagramEditorUtil.openDiagram(bomResource[0]);
                        } catch (PartInitException e) {
                            LOG.error(e, "PartInitException"); //$NON-NLS-1$
                        }

                    }
                }
            });
        } catch (InvocationTargetException e) {
            LOG.error(e, "Invocation target exception"); //$NON-NLS-1$
        } catch (InterruptedException e) {
            LOG.error(e, "Interrupted exception"); //$NON-NLS-1$
        }
        return true;
    }

    private Resource createBomFile(TransactionalEditingDomain ed,
            IPath newFilePath, List<?> listOfTables) {
        Model modelObj = UMLFactory.eINSTANCE.createModel();

        String modelName = null;
        if (tablesInProfilePage.getConnectionProfile().getName() != null) {
            modelName =
                    NameUtil.getInternalName(tablesInProfilePage
                            .getConnectionProfile().getName().toLowerCase(),
                            false);
        } else {
            modelName =
                    tablesInProfilePage.getConnectionProfile()
                            .getProviderName();
        }

        modelObj.setName(modelName);

        final IFile bomFileToBeCreated =
                ResourcesPlugin.getWorkspace().getRoot().getFile(newFilePath);
        String user =
                UserInfoUtil
                        .getProjectPreferences(bomFileToBeCreated.getProject())
                        .getUserName();
        /*
         * Adds the author and timestamp details to the UML Model
         */
        DbToBomTransformUtil
                .addAuthorTimestampAnnotationToModel(modelObj, user);

        /*
         * Adds the generated - from database annotations to the UML model
         */
        DbToBomTransformUtil.addDBGeneratedAnnotationToModel(modelObj);

        ResourceSet resourceSet = ed.getResourceSet();

        Resource bomResource =
                resourceSet.createResource(URI.createFileURI(bomFileToBeCreated
                        .getLocation().toPortableString()));

        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Transaction.OPTION_UNPROTECTED, Boolean.TRUE);
        Transaction transaction = null;
        try {
            transaction =
                    ((InternalTransactionalEditingDomain) ed)
                            .startTransaction(false, attrs);
            bomResource.getContents().add(modelObj);
            DbToBomTransformUtil.transformDbTablesToBom(ed,
                    listOfTables,
                    modelObj);
            // add diagram to the resource so we can
            // actually open it in graphical editor later on
            Diagram diagram =
                    ViewService.createDiagram(modelObj,
                            CanvasPackageEditPart.MODEL_ID,
                            BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
            if (diagram != null) {
                bomResource.getContents().add(diagram);
                diagram.setElement(modelObj);
            }
            bomResource
                    .save(com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditorUtil
                            .getSaveOptions());
            UMLDiagramEditorUtil.setCharset(WorkspaceSynchronizer
                    .getFile(bomResource));

        } catch (IOException e) {
            BOMDiagramEditorPlugin
                    .getInstance()
                    .logError(Messages.ProfileSelectImportWizard_FileUnfoundErr_shortdesc);
        } catch (Exception e) {
            LOG.error(e);
        } finally {
            if (transaction != null) {
                try {
                    transaction.commit();
                } catch (RollbackException e) {
                    LOG.error(e);
                }
            }
        }
        return bomResource;
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     * 
     */
    @Override
    public void addPages() {
        bomFileSelectionPage =
                new BOMFileSelectionWizardPage(
                        Messages.ProfileSelectImportWizard_WizardDesc_shortdesc,
                        new StructuredSelection(bomFolder));
        addPage(bomFileSelectionPage);

        tablesInProfilePage = new ProfileTablesDisplayPage();
        tablesInProfilePage.setConnectionProfile(connectionProfile);
        addPage(tablesInProfilePage);
    }

    public void setConnectionProfile(IConnectionProfile connectionProfile) {
        this.connectionProfile = connectionProfile;
        if (tablesInProfilePage != null) {
            tablesInProfilePage.setConnectionProfile(connectionProfile);
        }
    }

}
