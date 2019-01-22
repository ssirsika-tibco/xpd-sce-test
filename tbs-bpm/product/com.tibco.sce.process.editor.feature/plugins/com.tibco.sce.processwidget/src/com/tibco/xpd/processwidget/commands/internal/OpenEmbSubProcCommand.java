/**
 * OpenEmbSubProcCommand.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.commands.internal;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;

import com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.figures.layouts.TaskFigureLayout;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.LaneEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * <b>OpenEmbSubProcCommand</b>
 * <p>
 * Open the given embedded sub-process. This basically just increases the size
 * of the embedded sub-process and moves objects right and below the sub-proc to
 * the right and down as appropriate.
 * </p>
 * 
 */
public class OpenEmbSubProcCommand extends AbstractCommand {

    private CompoundCommand cmd;

    private EditingDomain editingDomain = null;

    TaskEditPart embSubProcEP = null;

    private int MAX_COORD = Integer.MAX_VALUE / 2;

    /**
     * Resize the given embedded sub-process to its optimum size.
     * 
     * @param embeddedSubProcEP
     */
    public OpenEmbSubProcCommand(TaskEditPart embeddedSubProcEP) {

        embSubProcEP = embeddedSubProcEP;
        editingDomain = embSubProcEP.getEditingDomain();

        // Create a command that we can save for undo's.
        // On execute this will be loaded up with
        cmd = new CompoundCommand();
        cmd.setLabel(Messages.OpenEmbSubProcCommand_OpenEmbSubProc_menu);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.Command#execute()
     */
    @Override
    public void execute() {

        Rectangle curBnds = EditPartUtil.getModelBounds(embSubProcEP);

        TaskAdapter embSubProcAdp =
                (TaskAdapter) embSubProcEP.getModelAdapter();

        Dimension minimumExpandedSize =
                embSubProcEP.getMinimumContentArea().getSize();

        minimumExpandedSize.height +=
                TaskFigureLayout.TOP_MARGIN
                        + (TaskFigureLayout.MARKER_SIZE * 2)
                        + (TaskFigureLayout.MARKERS_MARGIN * 2)
                        + TaskFigureLayout.CONTENT_BOTTOM_MARGIN;

        minimumExpandedSize.width += TaskFigureLayout.CONTENT_HORZ_MARGIN * 2;

        Dimension actualExpandedSize = new Dimension();

        cmd.append(embSubProcAdp.getExpandSubProcessCommand(editingDomain,
                minimumExpandedSize,
                actualExpandedSize));

        Rectangle newBnds = curBnds.getCopy();
        newBnds.setSize(actualExpandedSize);

        Rectangle rightOfObject =
                new Rectangle(curBnds.right(), curBnds.y, MAX_COORD,
                        curBnds.height);

        Rectangle belowObject =
                new Rectangle(curBnds.x, curBnds.bottom(), curBnds.width,
                        MAX_COORD);

        Rectangle rightAndBelowObject =
                new Rectangle(curBnds.right(), curBnds.bottom(), MAX_COORD,
                        MAX_COORD);

        // Calc the delta's for moving left / up.
        int deltaX = newBnds.width - curBnds.width;
        int deltaY = newBnds.height - curBnds.height;

        double ratioX = (double) newBnds.width / (double) curBnds.width;
        double ratioY = (double) newBnds.height / (double) curBnds.height;

        // Go thru our siblings moving them left / up as necessary.
        List siblings = embSubProcEP.getParent().getChildren();

        for (Iterator iter = siblings.iterator(); iter.hasNext();) {
            EditPart ep = (EditPart) iter.next();

            if (ep instanceof BaseGraphicalEditPart && ep != embSubProcEP) {
                BaseGraphicalEditPart gep = (BaseGraphicalEditPart) ep;

                BaseGraphicalNodeAdapter siblingAdp =
                        (BaseGraphicalNodeAdapter) gep.getModelAdapter();

                Rectangle bnds = EditPartUtil.getModelBounds(gep);

                Point modelLocation = siblingAdp.getLocation().getCopy();
                Point origLocation = modelLocation.getCopy();

                if (bnds.intersects(rightOfObject)) {
                    // For objects intersecting the area to the right of current
                    // object rect. Shift right by the size difference between
                    // opened and closed.
                    modelLocation.x += deltaX;

                    // CloseEmbeddedSubProcCommand will also move right hand
                    // side objects UP by the ratio which the vertical
                    // size is changing (So as not to leave objects 'hanging
                    // down').
                    //
                    // So we MUST do the converse in order to put things back
                    // where they were before we closed.
                    double offset = modelLocation.y - newBnds.y;
                    if (offset > 0) {
                        offset *= ratioY;
                    }
                    modelLocation.y = newBnds.y + (int) offset;

                } else if (bnds.intersects(belowObject)) {
                    // For objects intersecting the area below the current
                    // object rect. Shift down by the size difference between
                    // opened and closed.
                    modelLocation.y += deltaY;

                    // CloseEmbeddedSubProcCommand will also move below
                    // side objects LEFT by the ratio which the horizontal
                    // size is changing (So as not to leave objects 'hanging
                    // right').
                    //
                    // So we MUST do the converse in order to put things back
                    // where they were before we closed.
                    double offset = modelLocation.x - newBnds.x;
                    if (offset > 0) {
                        offset *= ratioX;
                    }
                    modelLocation.x = newBnds.x + (int) offset;

                } else if (rightAndBelowObject.contains(bnds)) {
                    // For objects that are below and right of sub-proc, move
                    // right and down.
                    modelLocation.x += deltaX;
                    modelLocation.y += deltaY;
                }

                if (!origLocation.equals(modelLocation)) {
                    cmd.appendAndExecute(siblingAdp
                            .getSetLocationCommand(editingDomain,
                                    modelLocation,
                                    siblingAdp.getSize()));
                }

            }
        }

        //
        // Resize lane / sub-proc if needed.
        EditPart parent = embSubProcEP.getParent();

        if (parent instanceof TaskEditPart) {
            cmd.appendAndExecute(new EmbSubProcOptimiseSizeCommand(
                    (TaskEditPart) parent, false, false));

        } else if (parent instanceof LaneEditPart) {
            cmd.appendAndExecute(new ResizeLaneIfNeededCommand(editingDomain,
                    (LaneEditPart) parent));

        }

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
     */
    @Override
    protected boolean prepare() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.Command#redo()
     */
    @Override
    public void redo() {
        cmd.redo();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.AbstractCommand#undo()
     */
    @Override
    public void undo() {
        cmd.undo();
    }

}
