/**
 * CreateFlowObjectCommand.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.adapters;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;

/**
 * CreateAccessibleObjectCommand
 * 
 * EMF Command wrapper that allows the creator to store the EMF objectS that
 * were actually created as part of the create operation.
 * 
 * The receiver of the command can then access the object to be created from
 * this class.
 * 
 */
public class CreateAccessibleObjectsCommand implements Command {

    private final Command cmd;

    private final List<EObject> createdNodes;

    /**
     * Wrap the given command. The caller must
     */
    public CreateAccessibleObjectsCommand(Command cmd, List<EObject> createdNodes) {
        this.cmd = cmd;
        this.createdNodes = createdNodes;
    }

    /**
     * Returns the new EMF object that will be created by this command.
     * 
     * @return the createdNode
     */
    public List<EObject> getCreatedObjects() {
        return createdNodes;
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#canExecute()
     */
    public boolean canExecute() {
        return cmd.canExecute();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#canUndo()
     */
    public boolean canUndo() {
        return cmd.canUndo();
    }

    /**
     * @param command
     * @return
     * @see org.eclipse.emf.common.command.Command#chain(org.eclipse.emf.common.command.Command)
     */
    public Command chain(Command command) {
        return cmd.chain(command);
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#dispose()
     */
    public void dispose() {
        cmd.dispose();
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#execute()
     */
    public void execute() {
        cmd.execute();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#getAffectedObjects()
     */
    public Collection getAffectedObjects() {
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
    public Collection getResult() {
        return cmd.getResult();
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#redo()
     */
    public void redo() {
        cmd.redo();
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#undo()
     */
    public void undo() {
        cmd.undo();
    }

}
