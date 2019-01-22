/**
 * ResizeFlowContainerIfNeeded.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.commands.internal;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processwidget.adapters.LaneAdapter;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.LaneEditPart;

/**
 * <b>ResizeLaneIfNeeded</b>
 * <p>
 * Resize the given lane if it is not large enough to fully contain it's
 * contents.
 * </p>
 * <p>
 * Calculation of minimum size is delayed until execution so that it works
 * consistently as part of a compound command.
 * </p>
 * 
 */
public class ResizeLaneIfNeededCommand extends AbstractCommand {

    private CompoundCommand cmd = null;

    private EditingDomain editingDomain = null;

    LaneEditPart laneEP = null;

    public ResizeLaneIfNeededCommand(EditingDomain editingDomain, LaneEditPart laneEP) {
        this.laneEP = laneEP;
        
        this.editingDomain = editingDomain;
        
        cmd = new CompoundCommand();
        cmd.setLabel(Messages.ResizeLaneIfNeededCommand_Resize_menu);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.Command#execute()
     */
    public void execute() {
        LaneAdapter la = (LaneAdapter)laneEP.getModelAdapter();
        
        cmd.appendAndExecute(la.getSetSizeCommand(editingDomain, la.getSize()));
        
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
