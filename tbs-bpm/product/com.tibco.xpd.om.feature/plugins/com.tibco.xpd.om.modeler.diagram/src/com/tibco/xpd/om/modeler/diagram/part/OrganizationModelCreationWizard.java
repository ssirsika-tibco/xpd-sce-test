package com.tibco.xpd.om.modeler.diagram.part;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard;

/**
 * @generated NOT
 */
public class OrganizationModelCreationWizard extends BasicNewXpdResourceWizard {

    /**
     * @generated
     */
    protected OrganizationModelCreationWizardPage diagramModelFilePage;

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
        setWindowTitle(Messages.OrganizationModelCreationWizardTitle);
        setDefaultPageImageDescriptor(OrganizationModelDiagramEditorPlugin
                .getBundledImageDescriptor("icons/wizban/Organization Model File Wizard.png")); //$NON-NLS-1$
        setNeedsProgressMonitor(true);
    }

    /**
     * @generated
     */
    @Override
    public void addPages() {
        diagramModelFilePage =
                new OrganizationModelCreationWizardPage(
                        "DiagramModelFile", getSelection(), "om"); //$NON-NLS-1$ //$NON-NLS-2$
        diagramModelFilePage
                .setTitle(Messages.OrganizationModelCreationWizard_DiagramModelFilePageTitle);
        diagramModelFilePage
                .setDescription(Messages.OrganizationModelCreationWizard_DiagramModelFilePageDescription);
        addPage(diagramModelFilePage);
    }

    private Map<String, Object> createIntialParamsMap() {
        Map<String, Object> params = new HashMap<String, Object>();
        params
                .put(OrganizationModelDiagramEditorUtil.CREATE_DEFAULT_METAMODEL_PARAM,
                        diagramModelFilePage.createDefaultSchema() ? Boolean.TRUE
                                : Boolean.FALSE);
        params
                .put(OrganizationModelDiagramEditorUtil.APPLY_STANDARD_TYPE_PARAM,
                        diagramModelFilePage.createStandardType() ? Boolean.TRUE
                                : Boolean.FALSE);

        return params;
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
                diagram =
                        OrganizationModelDiagramEditorUtil
                                .createDiagram(diagramModelFilePage.getURI(),
                                        monitor,
                                        createIntialParamsMap());
                if (isOpenNewlyCreatedDiagramEditor() && diagram != null) {
                    try {
                        OrganizationModelDiagramEditorUtil.openDiagram(diagram);
                    } catch (PartInitException e) {
                        ErrorDialog
                                .openError(getContainer().getShell(),
                                        Messages.OrganizationModelCreationWizardOpenEditorError,
                                        e.getLocalizedMessage(),
                                        e.getStatus());
                    }
                }
            }

        };
        try {
            getContainer().run(false, true, op);
            if (diagram != null) {
                EObject eo = getObjectToSelectInExplorer(diagram);
                if (eo != null) {
                    selectAndReveal(eo);
                }
            }
        } catch (InterruptedException e) {
            return false;
        } catch (InvocationTargetException e) {
            ErrorDialog
                    .openError(getContainer().getShell(),
                            Messages.OrganizationModelCreationWizardCreationError,
                            e.getLocalizedMessage(),
                            e.getTargetException() instanceof CoreException ? ((CoreException) e
                                    .getTargetException()).getStatus()
                                    : new Status(
                                            IStatus.ERROR,
                                            OrganizationModelDiagramEditorPlugin.ID,
                                            e.getLocalizedMessage(), e
                                                    .getCause()));
            return false;
        }
        return diagram != null;
    }

    /**
     * Get the object to select in the explorer for the newly created resource.
     * If an {@link Organization} is present in the resource then it will be
     * returned, otherwise the {@link OrgModel} will be returned.
     * 
     * @param res
     * @return
     */
    private EObject getObjectToSelectInExplorer(Resource res) {
        OrgModel model = null;
        if (res != null) {
            for (EObject eo : res.getContents()) {
                if (eo instanceof OrgModel) {
                    model = (OrgModel) eo;
                    EList<Organization> organizations =
                            model.getOrganizations();
                    if (!organizations.isEmpty()) {
                        return organizations.get(0);
                    }
                }
            }
        }
        return model;
    }

}
