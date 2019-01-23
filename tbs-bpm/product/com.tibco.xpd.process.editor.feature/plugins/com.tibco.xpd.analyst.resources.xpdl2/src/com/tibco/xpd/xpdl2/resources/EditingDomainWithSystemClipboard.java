/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.xpdl2.resources;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CopyToClipboardCommand;
import org.eclipse.emf.edit.command.CutToClipboardCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.PlatformUI;

/**
 * AdapterFactoryEditingDomain that use System clipboard instead of using
 * private clipboard.
 * 
 * @author wzurek
 * 
 * @deprecated This class is no longer used since moving to Transactional
 *             Editing Domains
 */
@Deprecated
public class EditingDomainWithSystemClipboard extends
        AdapterFactoryEditingDomain {

    /**
     * The Constructor.
     * 
     * @param factory
     *            adapter factory
     * @param stack
     *            command stack
     */
    public EditingDomainWithSystemClipboard(AdapterFactory factory,
            CommandStack stack) {
        super(factory, stack);
    }

    /**
     * Override createCommand() so that on request for CopyToClipboard command
     * we return our own XPDCopyToClipboard which does not include clipboard
     * operation in undo / redo behaviour.
     * 
     * @see org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain#createCommand(java.lang.Class,
     *      org.eclipse.emf.edit.command.CommandParameter)
     */
    @Override
    public Command createCommand(Class commandClass,
            CommandParameter commandParameter) {
        if (commandClass == CopyToClipboardCommand.class) {
            return new XPDCopyToClipboardCommand(this,
                    commandParameter.getCollection(),
                    XPDCopyToClipboardCommand
                            .calculateSourceContextObject(commandParameter
                                    .getCollection()));

        } else if (commandClass == CutToClipboardCommand.class) {
            return new XPDCutToClipboardCommand(this,
                    RemoveCommand.create(this,
                            commandParameter.getOwner(),
                            commandParameter.getFeature(),
                            commandParameter.getCollection()));

        }

        return super.createCommand(commandClass, commandParameter);
    }

    /**
     * Use System clipboard instead of using Editing Domain clipboard
     * 
     * @see org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain#setClipboard(java.util.Collection)
     */
    @Override
    public void setClipboard(Collection clipboard) {
        Clipboard clp = new Clipboard(PlatformUI.getWorkbench().getDisplay());
        try {
            LocalTransfer lt = LocalTransfer.getInstance();
            clp.setContents(new Object[] { clipboard }, new Transfer[] { lt });
        } finally {
            clp.dispose();
        }
    }

    /**
     * Use System clipboard instead of using Editing Domain clipboard Task
     * 
     * @see org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain#getClipboard()
     */
    @Override
    public Collection getClipboard() {
        Clipboard clp = new Clipboard(PlatformUI.getWorkbench().getDisplay());
        Object cnt = null;
        try {
            LocalTransfer lt = LocalTransfer.getInstance();
            cnt = clp.getContents(lt);
        } finally {
            clp.dispose();
        }
        if (cnt instanceof Collection) {
            // Give call sub-class chance to fix unique id's
            fixClipboardCopyObjects((Collection) cnt);
            return (Collection) cnt;
        }
        return Collections.EMPTY_SET;
    }

    /**
     * Override this in sub-class to fix anythin that needs to be done before
     * pasting etc. etc before getClipboard() returns copy of objects on
     * clipboard.
     * 
     * @param collection
     */
    protected void fixClipboardCopyObjects(Collection collection) {
        // Nothing to do..
        return;
    }

}
