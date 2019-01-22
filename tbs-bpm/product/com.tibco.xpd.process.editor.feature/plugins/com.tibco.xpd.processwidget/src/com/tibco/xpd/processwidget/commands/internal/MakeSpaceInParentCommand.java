/**
 * MakeSpaceInParentCommand.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.commands.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;

import com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter;
import com.tibco.xpd.processwidget.adapters.LaneAdapter;
import com.tibco.xpd.processwidget.figures.XPDLineUtilities;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.parts.LaneEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * MakeSpaceInParentCommand
 * 
 * <p>
 * Make space for the given object OR rectangluar area in it's parent.
 * </p>
 * <p>
 * This command checks whether the space occupied by the object overlaps any
 * objects and if so, shuffles the objects around so that they are not
 * overlapped.
 * </p>
 * <p>
 * <b>If the parent (lane / embedded sub-process) of this object enlarged to
 * accomodate the moved content fully. In the case that the parent is an
 * embedded sub-process then this means that this command RECURSES (via
 * EmbSubProcOptimiseSizeCommand) in order to make space for it in its parent</b>
 * </p>
 * <p>
 * The overlap region is larger by EXTRA_OVERLAY_MARGIN in all directions (to
 * leave space for connections between overlapping objects)
 * </p>
 * <p>
 * <b>Strategy</b>
 * <li>If the object overlaps other sibling objects on it's top / left edge
 * then the object itself is shifted right and down so that it does not overlap
 * them</li>
 * <li>Then if the shifted object overlaps other objects (on top of, to right
 * of, below or right of and below) then all objects right of and or below and
 * shifted right / down as appropriate.</li>
 * <p>
 * <b>See moveOverlayedObjects() method comments for more details</b>
 * </p>
 * 
 * 
 */
public class MakeSpaceInParentCommand extends AbstractCommand {

    // Margin to add when checking for overlays (so that there is room for any
    // connections between resized object and overlayed objects
    public static final int DEFAULT_EXTRA_OVERLAY_MARGIN = 32;

    private int MAX_X = Integer.MAX_VALUE / 2;

    private int MAX_Y = Integer.MAX_VALUE / 2;

    private CompoundCommand cmd;

    private EditingDomain editingDomain = null;

    private Object makeSpaceForModelObject = null;

    private Rectangle makeSpaceBounds = null;

    private BaseGraphicalEditPart parentEP;

    private int overlapDetectionMargin = DEFAULT_EXTRA_OVERLAY_MARGIN;

    private int overlapInsertMargin = DEFAULT_EXTRA_OVERLAY_MARGIN;

    private Collection ignoreSiblings = Collections.EMPTY_LIST;

    private EditPartViewer viewer;

    /**
     * Make space for the given object in the given parent. Defaulting the
     * overlap detection and insert margins (DEFAULT_EXTRA_OVERLAY_MARGIN)
     * 
     * @param editingDomain
     * @param makeSpaceForObject
     * @param overlapDetectionMargin
     *            Extra space to allow around object when detecting overlaps.
     * @param overlapInsertMargin
     *            Extra space to insert around object once overlap has been
     *            detected.
     */
    public MakeSpaceInParentCommand(EditingDomain editingDomain,
            BaseGraphicalEditPart makeSpaceForObject,
            BaseGraphicalEditPart parentEP) {

        this(editingDomain, makeSpaceForObject, null, parentEP,
                DEFAULT_EXTRA_OVERLAY_MARGIN, DEFAULT_EXTRA_OVERLAY_MARGIN,
                null);
    }

    /**
     * Make space for the given object in the given parent with selectable
     * overlap detection/insertion margins.
     * 
     * @param editingDomain
     * @param makeSpaceForObject
     * @param overlapDetectionMargin
     *            Extra space to allow around object when detecting overlaps.
     * @param overlapInsertMargin
     *            Extra space to insert around object once overlap has been
     *            detected.
     */
    public MakeSpaceInParentCommand(EditingDomain editingDomain,
            BaseGraphicalEditPart makeSpaceForObject,
            BaseGraphicalEditPart parentEP, int overlapDetectionMargin,
            int overlapInsertMargin) {

        this(editingDomain, makeSpaceForObject, null, parentEP,
                overlapDetectionMargin, overlapInsertMargin, null);
    }

    /**
     * Make space for the given rectangular area in the given parent with
     * selectable overlap detection/insertion margins. The rectanglular area may
     * need to be offset down and right when there is an overlap with obejcts
     * left of / above it. This offset can be accessed using getLeftTopOffset().
     * 
     * @param editingDomain
     * @param spacewBounds
     * @param overlapDetectionMargin
     *            Extra space to allow around object when detecting overlaps.
     * @param overlapInsertMargin
     *            Extra space to insert around object once overlap has been
     *            detected.
     * @param ignoreSiblings
     *            List of sibling edit parts OR model objects to discount
     *            overlaps for
     */
    public MakeSpaceInParentCommand(EditingDomain editingDomain,
            Rectangle spaceBounds, BaseGraphicalEditPart parentEP,
            int overlapDetectionMargin, int overlapInsertMargin,
            Collection ignoreSiblings) {

        this(editingDomain, null, spaceBounds, parentEP,
                overlapDetectionMargin, overlapInsertMargin, ignoreSiblings);
    }

    /**
     * Make space for the given object in the given parent with selectable
     * overlap detection/insertion margins and list of edit parts to ignore when
     * checking for overlaps.
     * 
     * @param editingDomain
     * @param makeSpaceForObject
     * @param overlapDetectionMargin
     *            Extra space to allow around object when detecting overlaps.
     * @param overlapInsertMargin
     *            Extra space to insert around object once overlap has been
     *            detected.
     * @param ignoreSiblings
     *            List of sibling edit parts OR model objects to discount
     *            overlaps for
     */
    public MakeSpaceInParentCommand(EditingDomain editingDomain,
            BaseGraphicalEditPart makeSpaceForObject,
            BaseGraphicalEditPart parentEP, int overlapDetectionMargin,
            int overlapInsertMargin, Collection ignoreSiblings) {

        this(editingDomain, makeSpaceForObject, null, parentEP,
                overlapDetectionMargin, overlapInsertMargin, ignoreSiblings);
    }

    /**
     * Make space for the given object in the given parent with selectable
     * overlap detection/insertion margins and list of edit parts to ignore when
     * checking for overlaps.
     * 
     * @param editingDomain
     * @param makeSpaceForObject
     *            Either object edit part supplied
     * @param spaceBounds
     *            Or rectangle to make space in.
     * @param overlapDetectionMargin
     *            Extra space to allow around object when detecting overlaps.
     * @param overlapInsertMargin
     *            Extra space to insert around object once overlap has been
     *            detected.
     * @param ignoreSiblings
     *            List of sibling edit parts OR model objects to discount
     *            overlaps for
     */
    private MakeSpaceInParentCommand(EditingDomain editingDomain,
            BaseGraphicalEditPart makeSpaceForObject, Rectangle spaceBounds,
            BaseGraphicalEditPart parentEP, int overlapDetectionMargin,
            int overlapInsertMargin, Collection ignoreSiblings) {

        //
        // Depending on order and timing of refreshes, sometimes the edit part
        // may be moving between parents and not have a parent (may be the old
        // edit part not re-used in parent).
        //
        // Therefore it is MUCH safer to base our later execution decisions on
        // the edit part associated with object at time of execution.
        //
        if (makeSpaceForObject != null) {
            this.makeSpaceForModelObject = makeSpaceForObject.getModel();
        } else {
            this.makeSpaceForModelObject = null;
        }

        this.viewer = parentEP.getViewer();

        this.makeSpaceBounds = spaceBounds;
        this.parentEP = parentEP;
        this.editingDomain = editingDomain;

        cmd = new CompoundCommand();
        cmd.setLabel(Messages.MakeSpaceInParentCommand_MakeSpace_menu);

        this.overlapDetectionMargin = overlapDetectionMargin;
        this.overlapInsertMargin = overlapInsertMargin;

        if (ignoreSiblings != null) {
            this.ignoreSiblings = ignoreSiblings;
        } else {
            this.ignoreSiblings = Collections.EMPTY_LIST;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.Command#execute()
     */
    public void execute() {
        BaseGraphicalEditPart makeSpaceForObject = null;

        if (makeSpaceForModelObject != null) {
            makeSpaceForObject =
                    (BaseGraphicalEditPart) viewer.getEditPartRegistry()
                            .get(makeSpaceForModelObject);
        }

        Rectangle spaceBounds = getSpaceBounds(makeSpaceForObject);

        List siblings = getSiblings(makeSpaceForObject);

        //
        // Perform first pass to move object right and down if overlaps
        // object above or left of it.

        Rectangle shiftedBounds =
                verifyLeftTopEdge(siblings, spaceBounds, makeSpaceForObject);

        // 
        // Perform second pass, this moves everything out of the way of new
        // bounds rectangle.
        moveOverlayedObjects(siblings, shiftedBounds);

        if (parentEP instanceof TaskEditPart) {
            //
            // If the parent of THIS embedded sub-process is an ambedded
            // sub-process then get it to resize itself and make room in
            // it's parent object and so on.
            //
            // i.e. effectively this command is kind of recursive (via
            // EmbSubProcOptimiseSizeCommand)
            cmd.appendAndExecute(new EmbSubProcOptimiseSizeCommand(
                    (TaskEditPart) parentEP, false, false));

        } else if (parentEP instanceof LaneEditPart) {
            // 
            // If parent is a lane then resize it if necessary.
            LaneAdapter laneAdp = (LaneAdapter) parentEP.getModelAdapter();

            cmd.appendAndExecute(laneAdp.getSetSizeCommand(editingDomain,
                    laneAdp.getSize()));
        }

        return;
    }

    /**
     * Set the list of sibliong edit parts / model objects that shouldn't be
     * checked for overlaps.
     * 
     * @param ignoreSiblings
     *            List of sibling edit parts OR model objects to discount
     *            overlaps for
     */
    public void setIgnoredSiblings(Collection ignoreSiblings) {
        if (ignoreSiblings == null) {
            this.ignoreSiblings = Collections.EMPTY_LIST;

        } else {
            this.ignoreSiblings = ignoreSiblings;
        }

        return;
    }

    /**
     * This method checks whether the bottom / right of any sibling overlaps the
     * given space bounds and returns a dimension that represents the amount
     * that the space bounds needs to be shifted right / down to avoid the above /
     * left overlapped objects.
     * 
     */
    public Dimension getSpaceBoundsOffset() {
        BaseGraphicalEditPart makeSpaceForObject = null;

        if (makeSpaceForModelObject != null) {
            makeSpaceForObject =
                    (BaseGraphicalEditPart) viewer.getEditPartRegistry()
                            .get(makeSpaceForModelObject);
        }

        Rectangle spaceBounds = getSpaceBounds(makeSpaceForObject);

        List siblings = getSiblings(makeSpaceForObject);

        Rectangle shiftedBounds = getTopLeftOffset(siblings, spaceBounds);

        return new Dimension(shiftedBounds.x - spaceBounds.x, shiftedBounds.y
                - spaceBounds.y);
    }

    /**
     * @return
     */
    private List getSiblings(BaseGraphicalEditPart makeSpaceForObject) {
        // Get a list of the siblings of the object we're making space for
        // EXCEPT for the object itself and any sibling we are told to ignore
        // (i.e. other objects that we are also making space for).
        List children = parentEP.getChildren();
        List siblings = new ArrayList(children.size());

        for (Iterator iter = children.iterator(); iter.hasNext();) {
            Object o = (Object) iter.next();

            if (!ignoreSibling(o, makeSpaceForObject)) {
                siblings.add(o);
            }
        }
        return siblings;
    }

    /**
     * Move any objects that are overlayed by the given bounds (the original
     * resized object bounds shifted down and right after checking for
     * left/above obecjt overlaps).
     * <p>
     * <b>Current Strategy is fairly simple... </b>
     * <li>|-------|--------------------------------------------
     * <li>|..(a)..| (b)
     * <li>|-------|--------------------------------------------
     * <li>|.......|
     * <li>|..(c)..| (d)
     * <li>|.......|
     * <li>|.......|
     * </p>
     * 
     * <p>
     * <li>Go thru sibling objects...
     * <li>Any object that intersects the area (d) is moved right and down.
     * <li>Else if object intersects (b) or is entirely within (a) it is moved
     * right.
     * <li>Else if object intersects (c) then it is moved down.
     * </p>
     * <p>
     * the amount to move right / down is calculated from the top-left corner of
     * any object that intersects (a).
     * </p>
     * 
     * @param siblings
     * @param shiftedBounds
     */
    private void moveOverlayedObjects(List siblings, Rectangle shiftedBounds) {

        Rectangle objectBounds = shiftedBounds.getCopy();
        objectBounds.expand(overlapDetectionMargin, overlapDetectionMargin);

        Rectangle marginBounds = shiftedBounds.getCopy();
        marginBounds.expand(overlapInsertMargin, overlapInsertMargin);

        Rectangle rightOfObject =
                new Rectangle(objectBounds.right(), objectBounds.y, MAX_X,
                        objectBounds.height);

        Rectangle belowObject =
                new Rectangle(objectBounds.x, objectBounds.bottom(),
                        objectBounds.width, MAX_Y);

        Rectangle rightAndBelowObject =
                new Rectangle(objectBounds.right(), objectBounds.bottom(),
                        MAX_X, MAX_Y);

        // First find out if any object is going to be overlapped and from that
        // calculate the amount to move objects right / down.
        int deltaX = 0;
        int deltaY = 0;

        PointList rightHalf = new PointList();
        rightHalf.addPoint(objectBounds.getTopLeft());
        rightHalf.addPoint(objectBounds.getTopRight());
        rightHalf.addPoint(objectBounds.getBottomRight());

        for (Iterator iter = siblings.iterator(); iter.hasNext();) {
            BaseGraphicalEditPart obj = (BaseGraphicalEditPart) iter.next();

            Rectangle bnds = EditPartUtil.getModelBounds(obj);

            if (objectBounds.intersects(bnds)) {

                if (XPDLineUtilities.polygonContainsPoint(rightHalf, Math
                        .max(bnds.x, objectBounds.x), Math.max(bnds.y,
                        objectBounds.y))) {
                    // object is move in top / right than bottom left.
                    deltaX = Math.max((marginBounds.right() - bnds.x), deltaX);

                } else {
                    deltaY = Math.max((marginBounds.bottom() - bnds.y), deltaY);

                }
            }
        }

        //
        // Ok we've worked out how much to move by let's do it.
        if (deltaX != 0 || deltaY != 0) {
            for (Iterator iter = siblings.iterator(); iter.hasNext();) {
                BaseGraphicalEditPart obj = (BaseGraphicalEditPart) iter.next();

                BaseGraphicalNodeAdapter adp =
                        (BaseGraphicalNodeAdapter) obj.getModelAdapter();

                Rectangle bnds = EditPartUtil.getModelBounds(obj);

                Point location = adp.getLocation().getCopy();
                Point origLocation = location.getCopy();

                if (rightAndBelowObject.contains(bnds)) {
                    location.x += deltaX;
                    location.y += deltaY;

                } else if (objectBounds.intersects(bnds)) {
                    if (XPDLineUtilities.polygonContainsPoint(rightHalf, Math
                            .max(bnds.x, objectBounds.x), Math.max(bnds.y,
                            objectBounds.y))) {
                        // Object is further right than down.
                        location.x += deltaX;
                    } else {
                        location.y += deltaY;
                    }

                } else if (rightOfObject.intersects(bnds)) {
                    location.x += deltaX;

                } else if (belowObject.intersects(bnds)) {
                    location.y += deltaY;
                }

                if (!location.equals(origLocation)) {
                    cmd.appendAndExecute(adp
                            .getSetLocationCommand(editingDomain, location, adp
                                    .getSize()));

                }
            }
        }
    }

    /**
     * Check for the new bounds of the object overlaying objects to the left of
     * or above the new bounds. If it does then move it right / down so that it
     * misses them.
     * 
     * @param siblings
     * @return
     */
    private Rectangle verifyLeftTopEdge(List siblings, Rectangle spaceBounds,
            BaseGraphicalEditPart makeSpaceForObject) {

        Rectangle shiftedBounds = getTopLeftOffset(siblings, spaceBounds);

        // If we were given the actual object to make space for then shift it if
        // necessary.
        if (makeSpaceForObject != null) {
            if (!shiftedBounds.getTopLeft().equals(spaceBounds.getTopLeft())) {
                int deltaX = shiftedBounds.x - spaceBounds.x;
                int deltaY = shiftedBounds.y - spaceBounds.y;

                BaseGraphicalNodeAdapter adp =
                        (BaseGraphicalNodeAdapter) makeSpaceForObject
                                .getModelAdapter();

                Point location = adp.getLocation().getCopy();

                location.x += deltaX;
                location.y += deltaY;

                cmd.appendAndExecute(adp.getSetLocationCommand(editingDomain,
                        location,
                        adp.getSize()));

            }
        }

        return shiftedBounds;
    }

    /**
     * This method checks whether the bottom / right of any sibling overlaps the
     * given space bounds and returns the rectangle that represents the
     * spaceBounds shifted by an appropriate amount to avoid the overlap.
     * 
     * @param siblings
     * @param spaceBounds
     * @return
     */
    private Rectangle getTopLeftOffset(List siblings, Rectangle spaceBounds) {

        Rectangle marginBounds = spaceBounds.getCopy();
        marginBounds.expand(overlapDetectionMargin, overlapDetectionMargin);

        Rectangle shiftedBounds = spaceBounds.getCopy();
        shiftedBounds.expand(overlapInsertMargin, overlapInsertMargin);

        for (Iterator iter = siblings.iterator(); iter.hasNext();) {
            BaseGraphicalEditPart obj = (BaseGraphicalEditPart) iter.next();

            Rectangle bnds = EditPartUtil.getModelBounds(obj);

            if (bnds.intersects(marginBounds)) {
                int deltaY = 0;

                if (bnds.y < spaceBounds.y
                        && bnds.bottom() < spaceBounds.bottom()) {
                    // resized object overlaps the bottom of another.
                    if (spaceBounds.y > (bnds.y + (bnds.height / 2))) {
                        // top of make space for object is lower than half way
                        // down other object.
                        if (bnds.bottom() > shiftedBounds.y) {
                            deltaY = bnds.bottom() - shiftedBounds.y;
                        }
                    }
                }

                int deltaX = 0;
                if (bnds.x < spaceBounds.x
                        && bnds.right() < spaceBounds.right()) {
                    // resized object overlaps the right of another.
                    if (spaceBounds.x > (bnds.x + (bnds.width / 2))) {
                        // left of make space for object is further right than
                        // half way across other object.
                        if (bnds.right() > shiftedBounds.x) {
                            deltaX = bnds.right() - shiftedBounds.x;
                        }
                    }
                }

                if (deltaX != 0 || deltaY != 0) {
                    if (deltaX == 0 || (deltaY > 0 && deltaY < deltaX)) {
                        shiftedBounds.y = bnds.bottom();
                    } else {
                        shiftedBounds.x = bnds.right();
                    }

                }
            }
        }

        // Check if we need to shift the resized object.
        shiftedBounds.shrink(overlapInsertMargin, overlapInsertMargin);
        return shiftedBounds;
    }

    /**
     * @param checkObject
     * @return
     */
    private boolean ignoreSibling(Object checkObject,
            BaseGraphicalEditPart makeSpaceForObject) {
        if (checkObject instanceof EditPart) {
            EditPart checkEP = (EditPart) checkObject;

            // If edit part has moved to new parent it may have been
            // destroyed and re-created, in which case we need to check
            // whether it's effectively the same object (so we compare the
            // model for the edit part too)
            if (makeSpaceForObject != null) {
                if (checkEP == makeSpaceForObject
                        || checkEP.getModel() == makeSpaceForObject.getModel()) {
                    // Ignore the object we're making space for.
                    return true;
                }
            }

            for (Iterator iter = ignoreSiblings.iterator(); iter.hasNext();) {
                Object ignoreObj = (Object) iter.next();

                if (ignoreObj instanceof EditPart) {
                    EditPart ignoreEP = (EditPart) ignoreObj;

                    if (checkEP == ignoreEP
                            || checkEP.getModel() == ignoreEP.getModel()) {
                        return true;
                    }

                } else {
                    if (checkEP.getModel() == ignoreObj) {
                        return true;
                    }

                }

            }
            return false;
        }

        return true; // not an edit part, ignore it
    }

    /**
     * Get the area to make space in.
     * 
     * @return
     */
    private Rectangle getSpaceBounds(BaseGraphicalEditPart makeSpaceForObject) {
        // if we have been given an object to make space for then use it.
        if (makeSpaceForObject != null) {
            Rectangle bnds = EditPartUtil.getModelBounds(makeSpaceForObject);

            // If we are making space for a task edit part, take any attached
            // events on task border into account.
            if (makeSpaceForObject instanceof TaskEditPart) {
                TaskEditPart taskEP = (TaskEditPart) makeSpaceForObject;

                Collection<EventEditPart> attachedEvents =
                        taskEP.getAttachedEvents();
                for (EventEditPart event : attachedEvents) {
                    Rectangle evBnds = EditPartUtil.getModelBounds(event);

                    bnds.union(evBnds);
                }
            }

            return bnds;

        } else if (makeSpaceBounds != null) {
            return makeSpaceBounds.getCopy();

        }

        return new Rectangle(0, 0, 1, 1);
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
