/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.part;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.CanvasPackageEditPart;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * @generated
 */
public class UMLInitDiagramFileAction implements IObjectActionDelegate {

    /**
     * @generated
     */
    private IWorkbenchPart targetPart;

    /**
     * @generated
     */
    private URI domainModelURI;

    /**
     * @generated
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        this.targetPart = targetPart;
    }

    /**
     * @generated
     */
    public void selectionChanged(IAction action, ISelection selection) {
        domainModelURI = null;
        action.setEnabled(false);
        if (selection instanceof IStructuredSelection == false
                || selection.isEmpty()) {
            return;
        }
        IFile file = (IFile) ((IStructuredSelection) selection)
                .getFirstElement();
        domainModelURI = URI.createPlatformResourceURI(file.getFullPath()
                .toString(), true);
        action.setEnabled(true);
    }

    /**
     * @generated
     */
    private Shell getShell() {
        return targetPart.getSite().getShell();
    }

    /**
     * @generated
     */
    public void run(IAction action) {
        TransactionalEditingDomain editingDomain = XpdResourcesPlugin
                .getDefault().getEditingDomain();
        ResourceSet resourceSet = editingDomain.getResourceSet();

        EObject diagramRoot = null;
        try {
            Resource resource = resourceSet.getResource(domainModelURI, true);
            diagramRoot = (EObject) resource.getContents().get(0);
        } catch (WrappedException ex) {
            BOMDiagramEditorPlugin.getInstance().logError(
                    "Unable to load resource: " + domainModelURI, ex); //$NON-NLS-1$
        }
        if (diagramRoot == null) {
            MessageDialog
                    .openError(
                            getShell(),
                            Messages.UMLInitDiagramFileAction_InitDiagramFileResourceErrorDialogTitle,
                            Messages.UMLInitDiagramFileAction_InitDiagramFileResourceErrorDialogMessage);
            return;
        }
        Wizard wizard = new UMLNewDiagramFileWizard(domainModelURI,
                diagramRoot, editingDomain);
        wizard.setWindowTitle(NLS.bind(
                Messages.UMLInitDiagramFileAction_InitDiagramFileWizardTitle,
                CanvasPackageEditPart.MODEL_ID));
        UMLDiagramEditorUtil.runWizard(getShell(), wizard, "InitDiagramFile"); //$NON-NLS-1$
    }
}
