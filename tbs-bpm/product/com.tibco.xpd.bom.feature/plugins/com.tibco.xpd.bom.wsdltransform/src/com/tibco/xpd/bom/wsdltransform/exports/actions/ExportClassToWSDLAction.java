/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.wsdltransform.exports.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.bom.wsdltransform.exports.wizards.ExportClassToWSDLWizard;
import com.tibco.xpd.bom.wsdltransform.internal.Messages;
import com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper;

/**
 * Action to export class(es) to a wsdl file.
 * 
 * @author njpatel
 */
public class ExportClassToWSDLAction implements IObjectActionDelegate {

    private ISelection selection;
    private Shell shell;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.
     * action.IAction, org.eclipse.ui.IWorkbenchPart)
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        shell = targetPart.getSite().getShell();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {
        final List<Class> classArr = new ArrayList<Class>();

        if (selection instanceof IStructuredSelection) {
            for (Iterator<?> iter = ((IStructuredSelection) selection)
                    .iterator(); iter.hasNext();) {
                Object next = iter.next();

                if (next instanceof IAdaptable) {
                    EObject eo = (EObject) ((IAdaptable) next)
                            .getAdapter(EObject.class);
                    if (eo instanceof Class) {
                        classArr.add((Class) eo);
                    }
                }
            }
        }

        if (!classArr.isEmpty() && shell != null) {
            shell.getDisplay().syncExec(new Runnable() {
                public void run() {
                	for (Class tmpCls : classArr){
                		if (TransformHelper.isXSDNotationProfileApplied(tmpCls)){
                			MessageDialog.openInformation(shell, Messages.ExportClassToWSDLAction_title, Messages.ExportClassToWSDLAction_shortdesc);
                			return;
                		}
                	}
                    ExportClassToWSDLWizard wizard = new ExportClassToWSDLWizard(
                            classArr);
                    WizardDialog dialog = new WizardDialog(shell, wizard);
                    dialog.open();
                }
            });
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
     * .IAction, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
    }

}
