/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.validator.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.validator.internal.Messages;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Abstract class intended to be implemented by validation issue resolutions
 * that need a renaming of a <code>NamedElement</code> object. This resolution
 * provides an input dialog to enter the new name.
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractRenameResolution extends
        AbstractWorkingCopyResolution {

    private final String dialogMessage;

    /**
     * Constructor.
     * 
     * @param dialogMessage
     *            The input dialog message.
     */
    public AbstractRenameResolution(String dialogMessage) {
        this.dialogMessage = dialogMessage;
    }

    /**
     * Get the input validator for the input dialog
     * 
     * @return <code>IInputValidator</code> if input validation is required,
     *         <code>null</code> otherwise.
     */
    protected abstract IInputValidator getValidator();

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        Command cmd = null;

        if (editingDomain != null && target instanceof NamedElement) {
            NamedElement elem = (NamedElement) target;

            InputDialog dlg = new InputDialog(Display.getCurrent()
                    .getActiveShell(), getRenameTitle(), getDialogMessage(), elem.getName(),
                    getValidator());

            if (dlg.open() == InputDialog.OK) {
                cmd = SetCommand.create(editingDomain, elem,
                        UMLPackage.eINSTANCE.getNamedElement_Name(), dlg
                                .getValue());
            }
        }

        return cmd;
    }

	public String getDialogMessage() {
		return dialogMessage;
	}
	
	public String getRenameTitle() {
		return Messages.AbstractRenameResolution_rename_title;
	}
}
