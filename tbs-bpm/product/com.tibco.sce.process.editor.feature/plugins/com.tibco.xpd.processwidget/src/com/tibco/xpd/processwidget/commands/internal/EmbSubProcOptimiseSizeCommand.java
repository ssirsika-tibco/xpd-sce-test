/**
 * SetOptimumSizeCommand.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.commands.internal;

import java.util.Iterator;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.editparts.AbstractEditPart;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * <b>EmbSubProcOptimiseSizeCommand</b>
 * <p>
 * This command looks at the content of an embedded sub-process and resizes the
 * embedded sub-process so that it's content pane is large enough to display
 * it's content without scaling it down.
 * </p>
 * <p>
 * A margin is added around the content.
 * </p>
 * <p>
 * Optionally the content can be realigned to the top left of sub-process
 * content.
 * </p>
 * <p>
 * Optionally the sub-process can be shrunk if it is bigger than required.
 * </p>
 * 
 */
public class EmbSubProcOptimiseSizeCommand extends AbstractCommand {

    private CompoundCommand cmd;

    private EditingDomain editingDomain = null;

    private boolean realignObjectsTopLeft = true;

    private boolean shrinkIfTooBig = true;

    TaskEditPart taskEP = null;

    /**
     * Resize the given embedded sub-process to its optimum size. Content is
     * realigned to the top-left of the embedded sub-process.
     * <p>
     * The content will be realigned to top left and the sub-process will be
     * shrunk if it is larger than necessary.
     * </p>
     * 
     * @param embeddedSubProcEP
     */
    public EmbSubProcOptimiseSizeCommand(TaskEditPart embeddedSubProcEP) {
        this(embeddedSubProcEP, true, true);
    }

    /**
     * Resize the given embedded sub-process to its optimum size.
     * 
     * @param embeddedSubProcEP
     * @param realignObjectsTopLeft
     *            Whether to realign content to top left of sub-process content.
     * @param shrinkIfTooBig
     *            Whether to shrink sub-process object if it is larger than
     *            necessary.
     */
    public EmbSubProcOptimiseSizeCommand(TaskEditPart embeddedSubProcEP,
            boolean realignObjectsTopLeft, boolean shrinkIfTooBig) {

        taskEP = embeddedSubProcEP;
        editingDomain = taskEP.getEditingDomain();

        this.realignObjectsTopLeft = realignObjectsTopLeft;
        this.shrinkIfTooBig = shrinkIfTooBig;

        // Create a command that we can save for undo's.
        // On execute this will be loaded up with
        cmd = new CompoundCommand();
        cmd.setLabel(Messages.EmbSubProcOptimiseSizeCommand_OptimumSize_menu);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.Command#execute()
     */
    public void execute() {

        // Go thru the model children of this embedded sub-flow
        // find out what the top left and bottom right is.

        Rectangle contentBounds = null;
        
        for (Iterator iter = taskEP.getChildren().iterator(); iter.hasNext();) {
            AbstractEditPart child = (AbstractEditPart) iter.next();

            if (child instanceof BaseGraphicalEditPart) {
                Rectangle rc = EditPartUtil.getModelBounds((BaseGraphicalEditPart)child);

                if (contentBounds == null) {
                    contentBounds = rc.getCopy();
                } else {
                    contentBounds = contentBounds.union(rc);
                }
                
            }
        }

        // 
        // Now we know how big the content is we can re-offset the position
        // of all contained objects and set the size of task
        // to the optimum size.
        if (contentBounds != null) {
            int optWidth = contentBounds.width;
            int optHeight = contentBounds.height;

            if (realignObjectsTopLeft) {
                // Add a margin.
                optWidth += 2 * ProcessWidgetConstants.EMB_SUBPROC_CONTENT_MARGIN;
                optHeight += 2 * ProcessWidgetConstants.EMB_SUBPROC_CONTENT_MARGIN;

                // Add commands to re-offset all the contained objects.
                int xOffset = contentBounds.x - ProcessWidgetConstants.EMB_SUBPROC_CONTENT_MARGIN;
                int yOffset = contentBounds.y - ProcessWidgetConstants.EMB_SUBPROC_CONTENT_MARGIN;

                for (Iterator iter = taskEP.getChildren().iterator(); iter
                        .hasNext();) {
                    AbstractEditPart child = (AbstractEditPart) iter.next();

                    if (child instanceof BaseGraphicalEditPart) {

                        if (((BaseGraphicalEditPart) child).getModelAdapter() instanceof BaseGraphicalNodeAdapter) {

                            BaseGraphicalNodeAdapter gna = (BaseGraphicalNodeAdapter) ((BaseGraphicalEditPart) child)
                                    .getModelAdapter();

                            Point loc = gna.getLocation().getCopy();
                            Dimension size = gna.getSize().getCopy();

                            loc.x -= xOffset;
                            loc.y -= yOffset;
                            cmd.appendAndExecute(gna.getSetLocationCommand(
                                    editingDomain, loc, size));
                        }
                    }
                }

            } else {
                // 
                // We're not re-aligning objects to the top-left of content. So
                // we want to keep the margin before the top left and add margin
                // to the bottom right.
                optWidth += contentBounds.x + ProcessWidgetConstants.EMB_SUBPROC_CONTENT_MARGIN;
                optHeight += contentBounds.y + ProcessWidgetConstants.EMB_SUBPROC_CONTENT_MARGIN;

            }

            // That's all the children moved.
            // Now get the optimum size for this subflow task
            // and create the command to set it.
            Dimension taskOptimum = taskEP.getOptimumSize(new Dimension(
                    optWidth, optHeight));

            BaseGraphicalNodeAdapter gnTask = (BaseGraphicalNodeAdapter) taskEP
                    .getModelAdapter();

            // Want to keep top-left location same so convert from centre
            // then back again.
            Point taskLoc = gnTask.getLocation().getCopy();
            Dimension taskSize = gnTask.getSize();

            // If we are allowed to shrink or the current size is not large
            // enough for content then resize the embedded sub-process.
            // Unless we've been told to shrink if too big then only resize
            // orientations that are currently too small.
            Dimension newSize = taskSize.getCopy();
            
            if (shrinkIfTooBig || taskSize.width < taskOptimum.width) {
                taskLoc.x -= taskSize.width / 2;
                taskLoc.x += taskOptimum.width / 2;
                newSize.width = taskOptimum.width;
            }

            if (shrinkIfTooBig || taskSize.height < taskOptimum.height) {
                taskLoc.y -= taskSize.height / 2;
                taskLoc.y += taskOptimum.height / 2;
                newSize.height = taskOptimum.height;
            }

            if (!newSize.equals(taskSize)) {
                cmd.appendAndExecute(gnTask.getSetLocationCommand(
                        editingDomain, taskLoc, newSize));

                // 
                // If we have overlayed anything then move it out of the way...
                // The make space command will deal with resizing the parent
                // embebbed sub-process / lane of this embedded sub-process if
                // necessary.
                //
                cmd.appendAndExecute(new MakeSpaceInParentCommand(editingDomain, taskEP, (BaseGraphicalEditPart)taskEP.getParent()));
            }
        }

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
