/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.xpdl2.commands;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * This command may be slightly easier to use than
 * {@link LateExecuteCompoundCommand} in some circumstances.
 * <p>
 * Basically, this command will wait until execution before asking the subclass
 * to generate the command.
 * <p>
 * A context object can be given on construction that allows the subclass to
 * pass info between construction and command creation later.
 * <p>
 * This can be very useful when dealing with commands wherein all previous
 * compounded commands must have been executed before this command can be built
 * and evaluated.
 * 
 * @author aallway
 * @since 3.2
 */
public abstract class AbstractLateExecuteCommand extends CompoundCommand {
    private EditingDomain editingDomain;

    private Object contextObject;

    public AbstractLateExecuteCommand(EditingDomain editingDomain,
            Object contextObject) {
        super();
        this.editingDomain = editingDomain;
        this.contextObject = contextObject;
    }

    /**
     * Create the command to execute - this method is not invoked until command
     * itself is executed.
     * 
     * @param editingDomain
     * @param contextObject
     *            This is the object passed to construction - allows subclass to
     *            store some context information that can be used to build the
     *            command etc.
     * @return Command or null if nothing to do.
     */
    protected abstract Command createCommand(EditingDomain editingDomain,
            Object contextObject);

    @Override
    public void execute() {
        // Caller Should not really have added their own commands but if they
        // did then exec them.
        if (!isEmpty()) {
            super.execute();
        }

        Command cmd = createCommand(editingDomain, contextObject);
        if (cmd != null) {
            appendAndExecute(cmd);
        }

        return;
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    /**
     * @return the editingDomain
     */
    public EditingDomain getEditingDomain() {
        return editingDomain;
    }

    /**
     * @return the contextObject
     */
    public Object getContextObject() {
        return contextObject;
    }

}
