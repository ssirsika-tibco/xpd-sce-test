/**
 * ProcessPasteCommand.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.adapters;

import java.util.Collection;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.Command;

/**
 * ProcessPasteCommand
 * 
 * EMF Command wrapper that allows the creator to access the scope of the
 * objects that are going to be pasted and the area that will be occupied by
 * them (where oibjects are pasted into a lane or embedded sub-proc).
 * 
 * 
 */
public abstract class ProcessPasteCommand implements Command {

    /**
     * 
     * Exception used to convey that user cancelled the paste operation by
     * cancelling add required project references.
     * 
     * @author aprasad
     * @since 14 Sep 2012
     */
    public static class PasteCancelledByUserException extends RuntimeException {

        /**
         * @see java.lang.Throwable#getMessage()
         * 
         * @return
         */
        @Override
        public String getMessage() {
            return "Paste cancelled by user."; //$NON-NLS-1$
        }
    }

    private final Command cmd;

    private Rectangle occupiedArea;

    private CopyPasteScope pasteScope;

    private Collection pasteObjects;

    /**
     * Wrap the given command. and provide various info that process widget will
     * require.
     */
    public ProcessPasteCommand(Command cmd, Rectangle occupiedArea,
            CopyPasteScope pasteScope, Collection pasteObjects) {
        this.cmd = cmd;
        this.occupiedArea = occupiedArea;
        this.pasteScope = pasteScope;
        this.pasteObjects = pasteObjects;
    }

    /**
     * Return the occupied area of the paste for paste's of activities /
     * artifacts.
     * 
     * @return the occupiedArea OR NULL if the paste is not for activities or
     *         artifacts.
     */
    public Rectangle getOccupiedArea() {
        if (pasteScope.getCopyScope() == CopyPasteScope.COPY_ACTIVITIES_AND_ARTIFACTS) {
            return occupiedArea;
        }
        return null;
    }

    /**
     * @param occupiedArea
     *            the occupiedArea to set
     */
    public void setOccupiedArea(Rectangle occupiedArea) {
        this.occupiedArea = occupiedArea.getCopy();
    }

    /**
     * Find the activity (Task/Event/Gateway) that is (in flow terms) at the
     * start of the flow represented by the paste objects.
     * 
     * <p>
     * i.e. the first object without incoming sequence flow. Ignoring events on
     * task borders.
     * 
     * @return Start Activity or <b>null</b> if none found (i.e. paste objects
     *         are at lane/pool level).
     */
    public abstract Object getFlowStartActivity();

    /**
     * Get the id of the activity that is returned by getFlowStartActivity()
     * 
     * @return Id of start of flow activity or null
     */
    public abstract String getFlowStartActivityId();

    /**
     * Find the activity (Task/Event/Gateway) that is (in flow terms) at the end
     * of the flow represented by the paste objects.
     * 
     * <p>
     * i.e. the first object without outgoing sequence flow that is...
     * Discounting event on task border. Downstream from start object.
     * 
     * @return Start Activity or <b>null</b> if none found (i.e. paste objects
     *         are at lane/pool level).
     */
    public abstract Object getFlowEndActivity();

    /**
     * Get the id of the activity that is returned by getFlowEndActivity()
     * 
     * @return Id of start of flow activity or null
     */
    public abstract String getFlowEndActivityId();

    /**
     * Offset the paste location in command so that the given paste activity
     * object is centred on given location in parent.
     * <p>
     * <b>NOTE: This will only ever be called for Activity scope pastes<b>
     * </p>
     * 
     * @param pasteObject
     * @param location
     */
    public abstract void offsetToLocation(Object pasteObject, Point location);

    /**
     * Return the offset required to move the paste objects so that the given
     * object would be centred on the given location.
     * 
     * <p>
     * <b>NOTE: This will only ever be called for Activity scope pastes<b>
     * </p>
     * 
     * @param pasteObject
     * @param location
     * 
     * @return null on error
     */
    public abstract Point getOffsetForLocation(Object pasteObject,
            Point location);

    /**
     * Return the scope of the objects being pasted (i.e.
     * Pools/Lanes/Activities-and-artifacts)
     * 
     * @return the pasteScope
     */
    public CopyPasteScope getPasteScope() {
        return pasteScope;
    }

    /**
     * Get list of new model objects that will be created on paste execution.
     * 
     * @return the pasteObjects
     */
    public Collection getPasteObjects() {
        return pasteObjects;
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#canExecute()
     */
    @Override
    public boolean canExecute() {
        return cmd.canExecute();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#canUndo()
     */
    @Override
    public boolean canUndo() {
        return cmd.canUndo();
    }

    /**
     * @param command
     * @return
     * @see org.eclipse.emf.common.command.Command#chain(org.eclipse.emf.common.command.Command)
     */
    @Override
    public Command chain(Command command) {
        return cmd.chain(command);
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#dispose()
     */
    @Override
    public void dispose() {
        cmd.dispose();
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#execute()
     */
    @Override
    public void execute() {
        cmd.execute();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#getAffectedObjects()
     */
    @Override
    public Collection getAffectedObjects() {
        return cmd.getAffectedObjects();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#getDescription()
     */
    @Override
    public String getDescription() {
        return cmd.getDescription();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#getLabel()
     */
    @Override
    public String getLabel() {
        return cmd.getLabel();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#getResult()
     */
    @Override
    public Collection getResult() {
        return cmd.getResult();
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#redo()
     */
    @Override
    public void redo() {
        cmd.redo();
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#undo()
     */
    @Override
    public void undo() {
        cmd.undo();
    }

}
