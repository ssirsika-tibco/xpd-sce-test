/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.internal.navigator.actions;

import java.util.Collection;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.workspace.EMFOperationCommand;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.ResourceType;
import com.tibco.xpd.om.core.om.provider.TransientItemProvider;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Project explorer delete action for the Organization Model. This will run the
 * GMF delete action for elements that appear in the diagram, otherwise runs the
 * EMF delete operation.
 * 
 * @author njpatel
 * 
 */
public class OMDeleteAction extends DeleteAction {

    private Shell shell;

    private boolean askUser;

    /**
     * Organization Model delete action.
     * 
     * @param domain
     *            editing domain
     * @param removeAllReferences
     *            <code>true</code> to remove all references
     */
    public OMDeleteAction(EditingDomain domain, boolean removeAllReferences) {
        super(domain, removeAllReferences);
    }

    /**
     * Set the shell if the user needs to be asked whether this delete can go
     * ahead.
     * 
     * @see #setAskUserBeforeDelete()
     * 
     * @param shell
     * @since 3.5.20
     */
    public void setShell(Shell shell) {
        this.shell = shell;
    }

    /**
     * Call this if the user should be asked before the delete of the selection
     * can go ahead (by default the user will not be asked). If this is set then
     * you have to set the shell using {@link #setShell(Shell)}.
     * 
     * @since 3.5.20
     */
    public void setAskUserBeforeDelete() {
        this.askUser = true;
    }

    /**
     * Organization Model delete action. This is equivalent of calling
     * {@link #OMDeleteAction(EditingDomain, boolean) OMDeleteAction(domain,
     * false)}.
     * 
     * @param domain
     *            editing domain
     */
    public OMDeleteAction(EditingDomain domain) {
        super(domain);
    }

    @Override
    public Command createCommand(Collection<?> selection) {
        String label = null;
        if (selection != null && selection.size() > 0) {
            Object firstItem = selection.iterator().next();

            if (firstItem instanceof NamedElement) {
                String label_pattern = Messages.OMDeleteAction_label;
                if (selection.size() == 1) {
                    label =
                            String.format(label_pattern,
                                    ((NamedElement) firstItem).getDisplayName());
                } else {
                    EStructuralFeature feature =
                            ((EObject) firstItem).eContainingFeature();
                    if (feature != null) {
                        label = String.format(label_pattern, feature.getName());
                    }
                }
            }

            if (firstItem instanceof TransientItemProvider
                    || (firstItem instanceof ResourceType && ((ResourceType) firstItem)
                            .isHumanResourceType())) {
                // Cannot delete logical groups or human resource type
                return UnexecutableCommand.INSTANCE;
            }

            if (firstItem instanceof Organization
                    || firstItem instanceof OrgUnit
                    || firstItem instanceof Position) {
                CompositeCommand result = new CompositeCommand(getText());

                for (Object obj : selection) {
                    if (obj instanceof EObject) {
                        EObject eo = (EObject) obj;
                        DestroyElementRequest req =
                                new DestroyElementRequest(eo, false);
                        ICommand editCommand =
                                ElementTypeRegistry.getInstance()
                                        .getElementType(eo).getEditCommand(req);
                        if (editCommand != null) {
                            result.add(editCommand);
                        }
                    }
                }

                if (label != null) {
                    result.setLabel(label);
                }
                return new EMFOperationCommand(XpdResourcesPlugin.getDefault()
                        .getEditingDomain(), result);
            }
        }

        Command cmd = super.createCommand(selection);
        if (cmd instanceof AbstractCommand && label != null) {
            ((AbstractCommand) cmd).setLabel(label);
        }

        return cmd;
    }

    /**
     * @see org.eclipse.emf.edit.ui.action.CommandActionHandler#run()
     * 
     */
    @Override
    public void run() {
        if (!askUser || canDelete()) {
            super.run();
        }
    }

    /**
     * Ask the user whether they want to delete. (Only called if
     * setAskUserBeforeDelete() was called).
     * 
     * @return
     */
    private boolean canDelete() {
        Assert.isNotNull(shell, "Use setShell() to set the Shell."); //$NON-NLS-1$

        final Boolean[] res = new Boolean[] { false };

        shell.getDisplay().syncExec(new Runnable() {

            @Override
            public void run() {
                String msg;

                IStructuredSelection sel = getStructuredSelection();

                if (sel.size() == 1
                        && sel.iterator().next() instanceof Organization) {
                    msg =
                            String.format(Messages.OMDeleteAction_askUserForConfirmationForOneOrganizationDelete_shortdesc,
                                    ((Organization) sel.iterator().next())
                                            .getLabel());
                } else {
                    msg =
                            Messages.OMDeleteAction_askUserForConfirmationForManyOrganizationDelete_shortdesc;
                }

                res[0] =
                        MessageDialog
                                .openQuestion(shell,
                                        Messages.OMDeleteAction_DeleteOrganization_dialog_title,
                                        msg);

            }
        });
        return res[0];
    }
}
