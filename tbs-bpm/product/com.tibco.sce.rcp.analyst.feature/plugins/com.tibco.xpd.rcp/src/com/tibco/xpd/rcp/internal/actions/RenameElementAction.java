/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.ltk.ui.refactoring.resource.RenameResourceWizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Action to rename the name of the active editor. This may rename a file,
 * {@link Process}, {@link ProcessInterface} or an {@link Organization}.
 * 
 * @author njpatel
 * 
 */
public class RenameElementAction extends Action {

    private ISelection selection;

    private IWorkbenchWindow window;

    public RenameElementAction(IWorkbenchWindow window) {
        this.window = window;
    }

    @Override
    public void run() {
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;

            if (sel.getFirstElement() instanceof IFile) {
                /*
                 * Use LTK framework to rename the file.
                 */
                RenameResourceWizard wizard =
                        new RenameResourceWizard(
                                (IResource) sel.getFirstElement());
                RefactoringWizardOpenOperation op =
                        new RefactoringWizardOpenOperation(wizard);
                try {
                    op.run(window.getShell(),
                            Messages.RenameEditorAction_renameResource_dialog_title);
                } catch (InterruptedException e) {
                    // do nothing
                }

            } else if (sel.getFirstElement() instanceof OtherAttributesContainer) {
                new RenameProcess(window.getShell(),
                        (OtherAttributesContainer) sel.getFirstElement()).run();
            } else if (sel.getFirstElement() instanceof Organization) {
                new RenameOrganization(window.getShell(),
                        (NamedElement) sel.getFirstElement()).run();
            }
        }
    }

    /**
     * @param selection
     *            the selection to set
     */
    public void setSelection(ISelection selection) {
        this.selection = selection;
    }

    /**
     * Abstract rename action that will show the user a dialog to get the new
     * name of the object being renamed and then rename the resource.
     * 
     * @author njpatel
     * 
     * @param <T>
     *            object to rename
     */
    private abstract class Rename<T> {
        private final Shell shell;

        private final T object;

        public Rename(Shell shell, T object) {
            this.shell = shell;
            this.object = object;
        }

        protected abstract String getName(T object);

        protected abstract String isValid(String newName);

        protected abstract void rename(T object, String newName)
                throws CoreException;

        public void run() {
            InputDialog dlg =
                    new InputDialog(shell,
                            Messages.RenameEditorAction_newNameDialog_title,
                            Messages.RenameEditorAction_newNameDialog_message,
                            getName(object), new IInputValidator() {

                                @Override
                                public String isValid(String newText) {
                                    return Rename.this.isValid(newText);
                                }
                            });
            if (dlg.open() == InputDialog.OK) {
                try {
                    rename(object, dlg.getValue());
                } catch (CoreException e) {
                    ErrorDialog.openError(shell,
                            Messages.RenameEditorAction_errorDialog_title,
                            e.getLocalizedMessage(),
                            e.getStatus());
                }
            }
        }
    }

    /**
     * Rename an XPDL process / process interface.
     * 
     * @author njpatel
     * 
     */
    private class RenameProcess extends Rename<OtherAttributesContainer> {

        private final List<String> existingProcesses;

        private String currentName;

        public RenameProcess(Shell shell, OtherAttributesContainer object) {
            super(shell, object);
            existingProcesses = new ArrayList<String>();

            currentName = getName(object);

            EObject container = object.eContainer();
            if (container != null && object.eContainingFeature() != null) {
                Object value = container.eGet(object.eContainingFeature());
                if (value instanceof Collection<?>) {
                    for (Object child : ((Collection<?>) value)) {
                        if (child instanceof OtherAttributesContainer
                                && child != object) {
                            existingProcesses
                                    .add(getName((OtherAttributesContainer) child));
                        }
                    }
                }
            }
        }

        @Override
        protected String getName(OtherAttributesContainer container) {
            return (String) Xpdl2ModelUtil
                    .getOtherAttribute(container, XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_DisplayName());
        }

        @Override
        protected String isValid(String newName) {
            if (newName.length() > 0) {
                if (existingProcesses.contains(newName)) {
                    return Messages.RenameEditorAction_processAlreadyExists_error_shortdesc;
                }
            } else {
                return Messages.RenameEditorAction_blankName_error_shortdesc;
            }
            return null;
        }

        @Override
        protected void rename(OtherAttributesContainer container, String newName)
                throws CoreException {

            // Execute command if name has changed
            if (!currentName.equals(newName)) {
                TransactionalEditingDomain ed =
                        XpdResourcesPlugin.getDefault().getEditingDomain();
                ed.getCommandStack()
                        .execute(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                                container,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName(),
                                newName));
            }
        }

    }

    /**
     * Rename action for the Organization.
     * 
     * @author njpatel
     * 
     */
    private class RenameOrganization extends Rename<NamedElement> {

        private List<String> existingOrganizations;

        private String currentName;

        public RenameOrganization(Shell shell, NamedElement object) {
            super(shell, object);

            existingOrganizations = new ArrayList<String>();

            currentName = object.getDisplayName();

            if (object instanceof Organization) {
                EObject container = object.eContainer();
                if (container instanceof OrgModel) {
                    for (Organization org : ((OrgModel) container)
                            .getOrganizations()) {

                        if (org != object) {
                            existingOrganizations.add(org.getDisplayName());
                        }
                    }
                }
            }
        }

        @Override
        protected String getName(NamedElement object) {
            return object.getDisplayName();
        }

        @Override
        protected String isValid(String newName) {
            if (newName.length() > 0) {
                if (existingOrganizations.contains(newName)) {
                    return Messages.RenameEditorAction_OrganizationAlreadyExists_error_shortdesc;
                }
            } else {
                return Messages.RenameEditorAction_blankName_error_shortdesc;
            }
            return null;
        }

        @Override
        protected void rename(NamedElement object, String newName)
                throws CoreException {

            // Rename if the name has changed
            if (!currentName.equals(newName)) {
                CompoundCommand ccmd = new CompoundCommand();
                EditingDomain editingDomain =
                        WorkingCopyUtil.getEditingDomain(object);
                // Add command to update label
                ccmd.append(SetCommand.create(editingDomain,
                        object,
                        OMPackage.eINSTANCE.getNamedElement_DisplayName(),
                        newName));

                // Update the name if it was generated from the label
                String currentName = object.getName();

                if (currentName.equals(NameUtil.getInternalName(object
                        .getDisplayName(), false))) {
                    ccmd.append(SetCommand.create(editingDomain,
                            object,
                            OMPackage.eINSTANCE.getNamedElement_Name(),
                            NameUtil.getInternalName(newName, false)));
                }

                editingDomain.getCommandStack().execute(ccmd);
            }
        }
    }

}
