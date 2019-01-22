/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */

package com.tibco.xpd.xpdl2.commands;

import java.util.Collection;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 *
 * @author aallway
 * @since 
 */
public abstract class AbstractWaitTillExecutionCommand extends AbstractCommand {

    private EditingDomain editingDomain;

    private Command cmd = UnexecutableCommand.INSTANCE;
    
    public AbstractWaitTillExecutionCommand(EditingDomain editingDomain) {
        super();
        this.editingDomain = editingDomain;
    }
    
    protected abstract Command createCommand(EditingDomain editingDomain);
    
    public void execute() {
        cmd = createCommand(editingDomain);
        if (cmd != null && cmd.canExecute()) {
            cmd.execute();
        } else {
            cmd = UnexecutableCommand.INSTANCE;
            throw new RuntimeException("No command created"); //$NON-NLS-1$;
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.emf.common.command.Command#redo()
     */
    public void redo() {
        cmd.redo();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#canExecute()
     */
    public boolean canExecute() {
        // Have to assume we can execute until we actually get asked to.
        return true;
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#canUndo()
     */
    public boolean canUndo() {
            return cmd.canUndo();
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#dispose()
     */
    public void dispose() {
        cmd.dispose();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#getAffectedObjects()
     */
    public Collection<?> getAffectedObjects() {
        return cmd.getAffectedObjects();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#getDescription()
     */
    public String getDescription() {
        return cmd.getDescription();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#getLabel()
     */
    public String getLabel() {
        return cmd.getLabel();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#getResult()
     */
    public Collection<?> getResult() {
        return cmd.getResult();
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#undo()
     */
    public void undo() {
        cmd.undo();
    }


}
