/*
 * Copyright (c) TIBCO Software Inc 2014. All rights reserved.
 */

package com.tibco.xpd.bom.modeler.diagram.part;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.ui.action.AbstractActionHandler;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.globaldata.commands.GlobalDataPropertyStereotypeCommand;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PropertyEditPart;

/**
 * Action to mutate a property type to a case state or case identifier
 * 
 */
public class MutatePropertyTypeAction extends AbstractActionHandler {

    protected GlobalDataProfileManager gdManager = GlobalDataProfileManager
            .getInstance();

    protected StereotypeKind propertyStereotypeToMutateTo = null;

    /**
     * @param workbenchPage
     * @param kind
     */
    public MutatePropertyTypeAction(IWorkbenchPage workbenchPage,
            StereotypeKind kind) {
        super(workbenchPage);
        propertyStereotypeToMutateTo = kind;
    }

    /**
     * @param workbenchPage
     */
    public MutatePropertyTypeAction(IWorkbenchPart part) {
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
                if ((object != null) && (object instanceof PropertyEditPart)) {
                    PropertyEditPart editPart = (PropertyEditPart) object;
                    EObject toMutateObject = editPart.resolveSemanticElement();

                    if (toMutateObject instanceof Property) {
                        Property toMutateProperty = (Property) toMutateObject;

                        Command cmd = null;

                        // Check for the case where the property is to be
                        // mutated into a normal attribute
                        if (propertyStereotypeToMutateTo == null) {
                            // For attributes we just remove the stereotypes
                            if (gdManager.isCaseState(toMutateProperty)) {
                                cmd =
                                        new GlobalDataPropertyStereotypeCommand.Unset(
                                                getEditingDomain(),
                                                toMutateProperty,
                                                StereotypeKind.CASE_STATE);
                            }
                            // There can actually only be one of these types of
                            // stereotypes set at a time
                            if (gdManager.isCID(toMutateProperty)) {
                                cmd =
                                        new GlobalDataPropertyStereotypeCommand.Unset(
                                                getEditingDomain(),
                                                toMutateProperty,
                                                StereotypeKind.CID);
                            }
                            if (gdManager
                                    .isAutoCaseIdentifier(toMutateProperty)) {
                                cmd =
                                        new GlobalDataPropertyStereotypeCommand.Unset(
                                                getEditingDomain(),
                                                toMutateProperty,
                                                StereotypeKind.AUTO_CASE_IDENTIFIER);
                            }
                            if (gdManager
                                    .isCompositeCaseIdentifier(toMutateProperty)) {
                                cmd =
                                        new GlobalDataPropertyStereotypeCommand.Unset(
                                                getEditingDomain(),
                                                toMutateProperty,
                                                StereotypeKind.COMPOSITE_CASE_IDENTIFIER);
                            }

                        } else {
                            // get suggestion of what to mutate property to
                            Stereotype stereotypeToAdd =
                                    gdManager
                                            .getStereotype(propertyStereotypeToMutateTo);

                            if (stereotypeToAdd != null) {
                                // check to ensure the stereotype is not already
                                // set
                                if (!gdManager
                                        .getAppliedStereotypeKinds(toMutateProperty)
                                        .contains(stereotypeToAdd)) {
                                    TransactionalEditingDomain editingDomain =
                                            getEditingDomain();
                                    cmd =
                                            new GlobalDataPropertyStereotypeCommand.Set(
                                                    editingDomain,
                                                    toMutateProperty,
                                                    propertyStereotypeToMutateTo);
                                }
                            }
                        }

                        // If a command is needed, add it
                        if (cmd != null) {
                            cc.append(cmd);
                        }
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
        if (propertyStereotypeToMutateTo == null) {
            return Messages.MutatePropertyTypeAction_ConvertToAttribute;
        } else if (StereotypeKind.CASE_STATE
                .equals(propertyStereotypeToMutateTo)) {
            return Messages.MutatePropertyTypeAction_ConvertToCaseState;
        } else if (StereotypeKind.AUTO_CASE_IDENTIFIER
                .equals(propertyStereotypeToMutateTo)) {
            return Messages.MutatePropertyTypeAction_ConvertToCaseIdentifier;
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
