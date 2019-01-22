/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.models.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorPlugin;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorUtil;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrganizationSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelSubDiagramEditorPlugin;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Action to add a new {@link Organization}.
 * 
 * @author njpatel
 * 
 */
public class NewOrganizationAction extends ModelAction {

    private OrgModel orgModel;

    public NewOrganizationAction(IWorkbenchWindow window, String label,
            String image, OrgModel orgModel) {
        super(window, label, image);
        this.orgModel = orgModel;
    }

    @Override
    public void run() {
        IEditingDomainItemProvider provider =
                (IEditingDomainItemProvider) XpdResourcesPlugin.getDefault()
                        .getAdapterFactory()
                        .adapt(orgModel, IEditingDomainItemProvider.class);
        if (provider instanceof ItemProviderAdapter) {
            TransactionalEditingDomain ed =
                    XpdResourcesPlugin.getDefault().getEditingDomain();
            CommandParameter parameter =
                    getNewOrganizationCommandParameter((ItemProviderAdapter) provider,
                            orgModel,
                            ed);
            if (parameter != null) {
                Organization newOrg = (Organization) parameter.getValue();
                InputDialog dlg =
                        new InputDialog(
                                getWorkbenchWindow().getShell(),
                                Messages.NewOrganizationAction_newOrganizationName_dialog_title,
                                Messages.NewOrganizationAction_newOrganizationName_dialog_message,
                                newOrg.getDisplayName(),
                                new NewOrganizationValidator(orgModel));
                if (dlg.open() == InputDialog.OK) {
                    CompoundCommand ccmd = new CompoundCommand();
                    Command cmd =
                            getCreateNewOrganizationCommand(parameter,
                                    (ItemProviderAdapter) provider,
                                    orgModel,
                                    ed,
                                    dlg.getValue());

                    if (cmd != null) {
                        ccmd.append(cmd);
                        ccmd.append(new NewOrganizationDiagramCommand(ed,
                                newOrg));
                        ed.getCommandStack().execute(ccmd);
                        new OrganizationSelectionListener(
                                (Organization) parameter.getValue())
                                .widgetSelected(null);
                    }
                }
            }
        }
    }

    @Override
    public boolean isNewAction() {
        return true;
    }

    /**
     * Get {@link Command} to create a new Organization.
     * 
     * @param editingDomain
     * @param elementName
     * @return <code>Command</code> to create new element or <code>null</code>
     *         if the element cannot be created.
     */
    private Command getCreateNewOrganizationCommand(
            CommandParameter descriptor, ItemProviderAdapter itemProvider,
            OrgModel model, EditingDomain editingDomain, String elementName) {

        if (descriptor != null) {
            com.tibco.xpd.om.core.om.NamedElement elem =
                    (com.tibco.xpd.om.core.om.NamedElement) descriptor
                            .getValue();
            if (elementName != null
                    && !elementName.equals(elem.getDisplayName())) {
                elem.setDisplayName(elementName);
            }

            return CreateChildCommand.create(editingDomain,
                    model,
                    descriptor,
                    Collections.singleton(model));

        }
        return null;
    }

    /**
     * Get the new Organization child descriptor from the given item provider.
     * 
     * @param itemProvider
     * @param model
     * @param ed
     * @return
     */
    private CommandParameter getNewOrganizationCommandParameter(
            ItemProviderAdapter itemProvider, OrgModel model, EditingDomain ed) {

        if (itemProvider != null && itemProvider.getTarget() != null) {

            Collection<?> descriptors =
                    itemProvider.getNewChildDescriptors(model, ed, null);
            if (descriptors != null) {
                for (Object desc : descriptors) {
                    if (desc instanceof CommandParameter
                            && ((CommandParameter) desc).getValue() instanceof Organization) {
                        return (CommandParameter) desc;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Validator for the Organization name input in the New Organization dialog.
     * 
     * @author njpatel
     * 
     */
    private class NewOrganizationValidator implements IInputValidator {

        private final List<String> names;

        public NewOrganizationValidator(OrgModel model) {
            names = new ArrayList<String>();
            for (Organization org : model.getOrganizations()) {
                String label = org.getDisplayName();
                if (label != null) {
                    names.add(label);
                }
            }
        }

        @Override
        public String isValid(String newText) {
            newText = newText.trim();
            if (newText.length() == 0) {
                return Messages.NewOrganizationAction_blankName_error_shortdesc;
            } else if (names.contains(newText)) {
                return Messages.NewOrganizationAction_OrgAlreadyExists_error_shortdesc;
            }
            return null;
        }
    }

    /**
     * Command to create a new Organization diagram.
     * 
     * @author njpatel
     * 
     */
    private class NewOrganizationDiagramCommand extends RecordingCommand {

        private final Organization organization;

        private Diagram diagram;

        public NewOrganizationDiagramCommand(TransactionalEditingDomain domain,
                Organization organization) {
            super(domain);
            this.organization = organization;
        }

        public Diagram getDiagram() {
            return diagram;
        }

        @Override
        protected void doExecute() {
            // Create diagram
            diagram =
                    ViewService
                            .createDiagram(organization,
                                    "Organization Model Sub", //$NON-NLS-1$
                                    OrganizationModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
            organization.eResource().getContents().add(diagram);

            // Add the badge
            Node subDiagBadgeNode =
                    ViewService
                            .createNode(diagram,
                                    organization,
                                    OrganizationSubBadgeEditPart.VISUAL_ID,
                                    OrganizationModelSubDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);

            LayoutConstraint constraintSubDiagBadge =
                    subDiagBadgeNode.getLayoutConstraint();

            if (constraintSubDiagBadge instanceof Bounds) {
                ((Bounds) constraintSubDiagBadge).setX(0);
                ((Bounds) constraintSubDiagBadge).setY(0);
            }
        }
    }

    /**
     * Organization menu selection listener - this will open the editor for the
     * selected Organization.
     * 
     * @author njpatel
     * 
     */
    private class OrganizationSelectionListener extends SelectionAdapter {

        private Organization organization;

        public OrganizationSelectionListener(Organization organization) {
            this.organization = organization;
        }

        @Override
        public void widgetSelected(SelectionEvent e) {
            if (organization != null) {
                try {
                    OrganizationModelDiagramEditorUtil
                            .openDiagram(organization);
                } catch (PartInitException e1) {
                    ErrorDialog
                            .openError(getWorkbenchWindow().getShell(),
                                    Messages.NewOrganizationAction_openEditor_errorDialog_title,
                                    Messages.NewOrganizationAction_openEditor_errorDialog_message,
                                    new Status(IStatus.ERROR,
                                            RCPActivator.PLUGIN_ID, e1
                                                    .getLocalizedMessage(), e1));
                }
            }
        }
    }

    /**
     * @return the orgModel
     */
    public OrgModel getOrgModel() {
        return orgModel;
    }

    /**
     * @param orgModel
     *            the orgModel to set
     */
    public void setOrgModel(OrgModel orgModel) {
        this.orgModel = orgModel;
    }

}
