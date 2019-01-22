/**
 * CloseEmbSubProcCommand.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.commands.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * <b>CloseEmbSubProcCommand</b>
 * <p>
 * Close the given embedded sub-process. This basically just reduces the size of
 * the embedded sub-process and moves objects right and below the sub-proc to
 * the left and up as appropriate.
 * </p>
 * 
 */
public class CloseEmbSubProcCommand extends AbstractCommand {

    private CompoundCommand cmd;

    private EditingDomain editingDomain = null;

    TaskEditPart embSubProcEP = null;

    /**
     * Resize the given embedded sub-process to its optimum size.
     * 
     * @param embeddedSubProcEP
     */
    public CloseEmbSubProcCommand(TaskEditPart embeddedSubProcEP) {

        embSubProcEP = embeddedSubProcEP;
        editingDomain = embSubProcEP.getEditingDomain();

        // Create a command that we can save for undo's.
        // On execute this will be loaded up with
        cmd = new CompoundCommand();
        cmd.setLabel(Messages.CloseEmbSubProcCommand_menu);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.Command#execute()
     */
    public void execute() {

        TaskAdapter embSubProcAdp =
                (TaskAdapter) embSubProcEP.getModelAdapter();

        Rectangle curBnds = EditPartUtil.getModelBounds(embSubProcEP);

        Dimension defaultCollapdsSize =
                new Dimension(ProcessWidgetConstants.SUBFLOW_WIDTH_SIZE,
                        ProcessWidgetConstants.SUBFLOW_HEIGHT_SIZE);
        Dimension actualCollapsedSize = new Dimension();

        cmd.appendAndExecute(embSubProcAdp
                .getCollapseSubProcessCommand(editingDomain,
                        defaultCollapdsSize,
                        actualCollapsedSize));

        Rectangle newBnds = curBnds.getCopy();
        newBnds.width = actualCollapsedSize.width;
        newBnds.height = actualCollapsedSize.height;

        List ignore = new ArrayList();
        ignore.add(embSubProcEP);

        Command closeSpaceCmd =
                new CloseSpaceInContainerCommand(editingDomain, embSubProcEP
                        .getParent(), curBnds, newBnds.getSize(), ignore);

        cmd.appendAndExecute(closeSpaceCmd);

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
     */
    protected boolean prepare() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.Command#redo()
     */
    public void redo() {
        cmd.redo();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.AbstractCommand#undo()
     */
    public void undo() {
        cmd.undo();
    }

}
