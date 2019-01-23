/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.internal.navigator.actions;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.CommandActionHandler;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.EMFOperationCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.ClipboardContentsHelper;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.ClipboardManager;
import org.eclipse.gmf.runtime.common.ui.util.CustomData;
import org.eclipse.gmf.runtime.common.ui.util.ICustomData;
import org.eclipse.gmf.runtime.diagram.ui.render.clipboard.AWTClipboardHelper;

import com.tibco.xpd.bom.resources.ui.clipboard.BOMCopyPasteCommandFactory;
import com.tibco.xpd.bom.resources.ui.internal.Messages;

/**
 * Project explorer paste action for the BOM objects.
 * 
 * @author njpatel
 * 
 */
public class BOMPasteAction extends CommandActionHandler {

    public BOMPasteAction(EditingDomain domain) {
        super(domain, Messages.BOMPasteAction_paste_action);
    }

    @Override
    public Command createCommand(Collection<?> selection) {
        if (selection != null && !selection.isEmpty()) {
            final List<EObject> notationObjs = BOMEditActionProvider
                    .getNotationObjects(selection, getEditingDomain());
            ICustomData[] data = getCustomDataFromClipboard();

            if (data != null && !notationObjs.isEmpty()) {
                ICommand pasteCommand = BOMCopyPasteCommandFactory
                        .getInstance()
                        .createPasteCommand(
                                (TransactionalEditingDomain) getEditingDomain(),
                                notationObjs.get(0), data, null);

                return new EMFOperationCommand(
                        (TransactionalEditingDomain) getEditingDomain(),
                        pasteCommand);
            }
        }

        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public void run() {
        // Recalculate the command as, if the selection hasn't changed then, the
        // previous command may get executed again
        command = createCommand(getStructuredSelection().toList());
        super.run();
    }

    /**
     * Get the custom data from the clipboard.
     * 
     * @return
     */
    private ICustomData[] getCustomDataFromClipboard() {
        ICustomData[] data = null;

        if (AWTClipboardHelper.getInstance().isImageCopySupported()) {
            CustomData customData = AWTClipboardHelper.getInstance()
                    .getCustomData();

            if (customData != null) {
                data = new ICustomData[] { customData };
            }
        }

        if (data == null) {
            data = ClipboardManager.getInstance().getClipboardData(
                    BOMCopyPasteCommandFactory.DRAWING_SURFACE,
                    ClipboardContentsHelper.getInstance());

            if (data == null) {
                data = ClipboardManager.getInstance().getClipboardData(
                        ClipboardManager.COMMON_FORMAT,
                        ClipboardContentsHelper.getInstance());
            }
        }

        return data;
    }

}
