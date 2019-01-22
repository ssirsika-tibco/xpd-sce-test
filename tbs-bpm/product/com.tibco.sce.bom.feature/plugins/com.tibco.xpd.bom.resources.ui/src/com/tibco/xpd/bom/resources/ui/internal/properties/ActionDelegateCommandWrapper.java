package com.tibco.xpd.bom.resources.ui.internal.properties;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandActionDelegate;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.EMFOperationCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;

public class ActionDelegateCommandWrapper implements Command,
        CommandActionDelegate {
    private final Command cmd;

    private String text;

    private String toolTipText;

    private Object image;

    private IUndoableOperation undoableCmd;

    public ActionDelegateCommandWrapper(Command cmd) {
        this.cmd = cmd;
    }

    public ActionDelegateCommandWrapper(TransactionalEditingDomain domain,
            IUndoableOperation cmd) {
        undoableCmd = cmd;
        this.cmd = new EMFOperationCommand(domain, cmd);
    }

    public boolean canExecute() {
        return cmd.canExecute();
    }

    public boolean canUndo() {
        return cmd.canUndo();
    }

    public Command chain(Command command) {
        return cmd.chain(command);
    }

    public void dispose() {
        cmd.dispose();
    }

    public void execute() {
        cmd.execute();
    }

    public Collection<?> getAffectedObjects() {
        return cmd.getAffectedObjects();
    }

    public String getDescription() {
        return cmd.getDescription();
    }

    public String getLabel() {
        return cmd.getLabel();
    }

    public Collection<?> getResult() {
        /*
         * EMFOperationCommand's get result returns an empty list so if this is
         * wrapping a CreateElementCommand then get the new element from it and
         * return as the result.
         */
        if (undoableCmd instanceof CreateElementCommand) {
            EObject element =
                    ((CreateElementCommand) undoableCmd).getNewElement();
            if (element != null) {
                return Collections.singleton(element);
            }
        }
        return cmd.getResult();
    }

    public void redo() {
        cmd.redo();
    }

    public void undo() {
        cmd.undo();
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text == null ? cmd.getLabel() : text;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public Object getImage() {
        return image;
    }

    public String getToolTipText() {
        return toolTipText == null ? cmd.getDescription() : toolTipText;
    }

    public void setToolTipText(String toolTipText) {
        this.toolTipText = toolTipText;
    }
}
