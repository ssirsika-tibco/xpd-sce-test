/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.internal.navigator.actions;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.CopyAction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.EMFOperationCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.resources.ui.clipboard.BOMCopyPasteCommandFactory;

/**
 * Project explorer copy action for the BOM objects.
 * 
 * @author njpatel
 * 
 */
public class BOMCopyAction extends CopyAction {

    /**
     * BOM copy action.
     * 
     * @param ed
     */
    public BOMCopyAction(EditingDomain ed) {
        super(ed);
    }

    @Override
    public Command createCommand(Collection<?> selection) {

        Collection<?> notationObjs = BOMEditActionProvider.getNotationObjects(
                selection, getEditingDomain());

        if (notationObjs != null && !notationObjs.isEmpty()) {
            ICommand copyCommand = BOMCopyPasteCommandFactory.getInstance()
                    .createCopyCommand(
                            (TransactionalEditingDomain) getEditingDomain(),
                            (EObject) notationObjs.iterator().next(),
                            notationObjs);

            return new EMFOperationCommand(
                    (TransactionalEditingDomain) getEditingDomain(),
                    copyCommand);
        }

        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public boolean updateSelection(IStructuredSelection selection) {
        boolean update = true;

        // Cannot copy model objects
        for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
            if (iter.next() instanceof Model) {
                update = false;
                break;
            }
        }

        return update ? super.updateSelection(selection) : update;
    }

}
