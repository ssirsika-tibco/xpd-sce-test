/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.modeler.diagram.part;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.ui.action.AbstractActionHandler;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.globaldata.commands.GlobalDataStereotypeCommand;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassEditPart;

/**
 * Action to mutate a class type to a global data type (Global / Case)
 * 
 * @author patkinso
 * @since 9 Oct 2013
 */
public class MutateClassTypeAction extends AbstractActionHandler {

    protected GlobalDataProfileManager gdManager = GlobalDataProfileManager
            .getInstance();

    protected StereotypeKind classStereotypeToMutateTo = null;

    /**
     * @param workbenchPage
     * @param kind
     */
    public MutateClassTypeAction(IWorkbenchPage workbenchPage,
            StereotypeKind kind) {
        super(workbenchPage);
        classStereotypeToMutateTo = kind;
    }

    /**
     * @param workbenchPage
     */
    public MutateClassTypeAction(IWorkbenchPart part) {
        super(part);
    }

    /**
     * @see org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#doRun(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param progressMonitor
     */
    @Override
    protected void doRun(IProgressMonitor progressMonitor) {

        IStructuredSelection structuredSelection = getStructuredSelection();

        if (structuredSelection != null && !structuredSelection.isEmpty()) {
            CompoundCommand cc = new CompoundCommand(getToolTipText());

            // Run the commands for everything selected
            for (Object object : structuredSelection.toList()) {
                if ((object != null) && (object instanceof ClassEditPart)) {
                    ClassEditPart editPart = (ClassEditPart) object;
                    Class toMutateClass = editPart.getElement();

                    Command cmd = null;

                    // Check for the case where the class is to be mutated into
                    // a local class
                    if (classStereotypeToMutateTo == null) {
                        // For local we just remove the stereotype for either
                        // the Class or Global
                        if (gdManager.isCase(toMutateClass)) {
                            cmd =
                                    new GlobalDataStereotypeCommand.Unset(
                                            getEditingDomain(), toMutateClass,
                                            StereotypeKind.CASE);
                        } else if (gdManager.isGlobal(toMutateClass)) {
                            cmd =
                                    new GlobalDataStereotypeCommand.Unset(
                                            getEditingDomain(), toMutateClass,
                                            StereotypeKind.GLOBAL);
                        }
                    } else {
                        // get suggestion of what to mutate class to
                        Stereotype stereotypeToAdd =
                                gdManager
                                        .getStereotype(classStereotypeToMutateTo);

                        if (stereotypeToAdd != null) {
                            // check to ensure the stereotype is not already set
                            if (!gdManager
                                    .getAppliedStereotypeKinds(toMutateClass)
                                    .contains(stereotypeToAdd)) {
                                TransactionalEditingDomain editingDomain =
                                        getEditingDomain();
                                cmd =
                                        new GlobalDataStereotypeCommand.Set(
                                                editingDomain, toMutateClass,
                                                classStereotypeToMutateTo);
                            }
                        }
                    }

                    // If a command is needed, add it
                    if (cmd != null) {
                        cc.append(cmd);
                    }
                }
            }
            // Make sure there is something can can be run before running it
            if (cc.canExecute()) {
                getEditingDomain().getCommandStack().execute(cc);
            }
        }
    }

    /**
     * Gets my editing domain from my workbench part.
     * 
     * @return my editing domain
     */
    protected TransactionalEditingDomain getEditingDomain() {

        // try adapting the workbench part
        IWorkbenchPart part = getWorkbenchPart();

        if (part != null) {
            IEditingDomainProvider edProvider =
                    (IEditingDomainProvider) part
                            .getAdapter(IEditingDomainProvider.class);

            if (edProvider != null) {
                EditingDomain domain = edProvider.getEditingDomain();

                if (domain instanceof TransactionalEditingDomain) {
                    return (TransactionalEditingDomain) domain;
                }
            }
        }
        return null;
    }

    /**
     * @see org.eclipse.jface.action.Action#getDescription()
     * 
     * @return
     */
    @Override
    public String getDescription() {
        return super.getDescription();
    }

    /**
     * @see org.eclipse.jface.action.Action#getText()
     * 
     * @return
     */
    @Override
    public String getText() {
        if (classStereotypeToMutateTo == null) {
            return Messages.MutateClassTypeAction_ConvertToLocal;
        } else if (StereotypeKind.CASE.equals(classStereotypeToMutateTo)) {
            return Messages.MutateClassTypeAction_ConvertToCase;
        } else if (StereotypeKind.GLOBAL.equals(classStereotypeToMutateTo)) {
            return Messages.MutateClassTypeAction_ConvertToGlobal;
        }

        return null;
    }

    /**
     * @see org.eclipse.jface.action.Action#getToolTipText()
     * 
     * @return
     */
    @Override
    public String getToolTipText() {
        return super.getToolTipText();
    }

    /**
     * @see org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#isSelectionListener()
     * 
     * @return
     */
    @Override
    protected boolean isSelectionListener() {
        return true;
    }

    /**
     * @see org.eclipse.gmf.runtime.common.ui.action.IActionWithProgress#refresh()
     * 
     */
    @Override
    public void refresh() {
    }
}
