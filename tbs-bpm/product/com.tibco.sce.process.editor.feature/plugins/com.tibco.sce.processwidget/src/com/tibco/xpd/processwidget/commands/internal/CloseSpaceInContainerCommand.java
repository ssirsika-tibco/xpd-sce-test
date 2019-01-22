/**
 * CloseSpaceInContainerCommand.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.commands.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;

import com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.EditPartUtil;

/**
 * <b>CloseSpaceInContainerCommand</b>
 * <p>
 * Close up the given space in the given container edit part. This basically
 * moves objects right and below the old rectangle to the left and up as
 * appropriate.
 * </p>
 * 
 */
public class CloseSpaceInContainerCommand extends AbstractCommand {

    private CompoundCommand cmd;

    private EditingDomain editingDomain = null;

    private int MAX_COORD = Integer.MAX_VALUE / 2;

    private EditPart containerEP;

    private Rectangle areaToClose;

    private Dimension newSize;

    private Collection ignoreEditParts;

    List<Rectangle> objsRightOfArea;

    List<Rectangle> objsBelowArea;

    List<Rectangle> objsRightAndBelowArea;

    Rectangle rightOfObject;

    Rectangle belowObject;

    Rectangle rightAndBelowObject;

    /**
     * Resize the given oldArea down to the smaller new Area.
     * 
     * @param editingDomain
     * @param containerEP
     * @param areaToClose
     *            Area in container to close up space for.
     * @param newSize
     *            New size for area.
     * @param ignoreEditParts
     *            List of editparts to ignore when closing space.
     */
    public CloseSpaceInContainerCommand(EditingDomain editingDomain,
            EditPart containerEP, Rectangle areaToClose, Dimension newSize,
            Collection ignoreEditParts) {
        this.containerEP = containerEP;
        this.areaToClose = areaToClose;
        this.newSize = newSize;
        this.ignoreEditParts = ignoreEditParts;

        this.editingDomain = editingDomain;

        // Create a command that we can save for undo's.
        // On execute this will be loaded up with
        cmd = new CompoundCommand();
        cmd.setLabel(Messages.CloseSpaceInContainerCommand_CloseSpace_menu);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.Command#execute()
     */
    @Override
    public void execute() {
        if (false) {
            tryOutDIfferentMethod();
            return;
        }

        // Create the list of siblings we need to worry about.

        Rectangle curBnds = areaToClose.getCopy();

        Rectangle newBnds = curBnds.getCopy();
        newBnds.width = newSize.width;
        newBnds.height = newSize.height;

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
        int deltaX = curBnds.width - newBnds.width;
        int deltaY = curBnds.height - newBnds.height;

        double ratioX = (double) newBnds.width / (double) curBnds.width;
        double ratioY = (double) newBnds.height / (double) curBnds.height;

        // Go thru our siblings moving them left / up as necessary.
        List siblings = containerEP.getChildren();

        /*
         * Map of sibling adapter to the new model location to set.
         */
        Map<BaseGraphicalNodeAdapter, Point> siblingAdapterToNewModelLocationMap =
                new HashMap<BaseGraphicalNodeAdapter, Point>();

        for (Iterator iter = siblings.iterator(); iter.hasNext();) {
            EditPart ep = (EditPart) iter.next();

            if (ignoreEditParts == null || !ignoreEditParts.contains(ep)) {

                if (ep instanceof BaseGraphicalEditPart) {
                    BaseGraphicalEditPart gep = (BaseGraphicalEditPart) ep;

                    BaseGraphicalNodeAdapter siblingAdp =
                            (BaseGraphicalNodeAdapter) gep.getModelAdapter();

                    Rectangle bnds = EditPartUtil.getModelBounds(gep);

                    Point modelLocation = siblingAdp.getLocation().getCopy();
                    Point origLocation = modelLocation.getCopy();

                    if (bnds.intersects(rightOfObject)) {
                        // For objects intersecting the area to the right of
                        // current object rect. Shift left by the size
                        // difference between opened and closed.
                        modelLocation.x -= deltaX;

                        // We may also be getting a LOT smaller in the vertical
                        // plane so we don't want to leave objects 'hanging
                        // down' way below the sub-proc (especially as this may
                        // cause them to run into objects that are being moved
                        // up and left because they're in the area right and
                        // below of closing sub-proc). So move the objects up by
                        // the ratio of the vertical size decrease.
                        double offset = modelLocation.y - newBnds.y;
                        if (offset > 0) {
                            offset *= ratioY;
                        }
                        modelLocation.y = newBnds.y + (int) offset;

                    } else if (bnds.intersects(belowObject)) {
                        // For objects intersecting the area below the current
                        // object rect. Shift up by the size difference between
                        // opened and closed.
                        modelLocation.y -= deltaY;

                        // We may also be getting a LOT smaller in the
                        // horizontal plane so we don't want to leave objects
                        // 'hanging right' way further out than the object. So
                        // move the objects left by the ratio of the horizontal
                        // size decrease.
                        double offset = modelLocation.x - newBnds.x;
                        if (offset > 0) {
                            offset *= ratioX;
                        }
                        modelLocation.x = newBnds.x + (int) offset;

                    } else if (rightAndBelowObject.contains(bnds)) {
                        // For objects that are below and right of sub-proc,
                        // move left and up.
                        modelLocation.x -= deltaX;
                        modelLocation.y -= deltaY;
                    }

                    if (!origLocation.equals(modelLocation)) {
                        /*
                         * collect all the sibling adapters and their new model
                         * location to set.
                         */
                        siblingAdapterToNewModelLocationMap.put(siblingAdp,
                                modelLocation);

                    }
                }
            }
        }

        if (!siblingAdapterToNewModelLocationMap.isEmpty()) {
            /*
             * XPD-7679: Finally before setting the model location check if the
             * new model location causes the object to cut the left or/and top
             * of the lane, if yes then add that much x or/and y co-ordinates
             * padding to all the new model locations.
             */
            // stores the additional x-coordinate padding to add in case any of
            // the sibling cuts the lane on the left.
            int xCoordinatePaddingToAdd = 0;
            // stores the additional y-coordinate padding to add in case any of
            // the sibling cuts the lane at the top.
            int yCoordinatePaddingToAdd = 0;

            for (BaseGraphicalNodeAdapter eachSibling : siblingAdapterToNewModelLocationMap
                    .keySet()) {

                Point modelLocationToSet =
                        siblingAdapterToNewModelLocationMap.get(eachSibling);
                Dimension siblingSize = eachSibling.getSize();

                if (modelLocationToSet.x < (siblingSize.width / 2)) {
                    /*
                     * if the model locations x-coordinate is < half width then
                     * the object will cut the lane on the left, hence we need
                     * to shift the model locations x-corordinate to the right.
                     */
                    int shiftX = (siblingSize.width / 2) - modelLocationToSet.x;

                    if (shiftX > xCoordinatePaddingToAdd) {
                        xCoordinatePaddingToAdd = shiftX;
                    }
                }

                if (modelLocationToSet.y < (siblingSize.height / 2)) {
                    /*
                     * if the model locations y-coordinate is < half height then
                     * the object will cut the lane at the top, hence we need to
                     * shift the model locations y-corordinate down.
                     */
                    int shiftY =
                            (siblingSize.height / 2) - modelLocationToSet.y;

                    if (shiftY > yCoordinatePaddingToAdd) {
                        yCoordinatePaddingToAdd = shiftY;
                    }
                }
            }
            for (BaseGraphicalNodeAdapter eachSibling : siblingAdapterToNewModelLocationMap
                    .keySet()) {
                /*
                 * Finally we have the additional x and y padding to add, hence
                 * add it to the resp original co-ordinates and execute command.
                 */
                Point modelLocationToSet =
                        siblingAdapterToNewModelLocationMap.get(eachSibling);
                modelLocationToSet.x += xCoordinatePaddingToAdd;
                modelLocationToSet.y += yCoordinatePaddingToAdd;

                cmd.appendAndExecute(eachSibling
                        .getSetLocationCommand(editingDomain,
                                modelLocationToSet,
                                eachSibling.getSize()));

            }
        }

        return;
    }

    /**
     * This method of close attempts to close space without allowing objects to
     * overlap because of the closing of space.
     * 
     * It works but cuases problems because it's reverse is not the same, so for
     * the moment have decided not to use it.
     */
    private void tryOutDIfferentMethod() {
        List siblings = containerEP.getChildren();

        //
        // Set up the EditPart -> Bounds map (for objects right / below /
        // right&below area to close)
        setupObjectBoundsMaps(siblings);

        //
        // Calculate the move-by deltas and ratios.
        Rectangle newBnds = areaToClose.getCopy();
        newBnds.width = newSize.width;
        newBnds.height = newSize.height;

        int deltaX = getNonOverLappingDelta(true);
        int deltaY = getNonOverLappingDelta(false);

        double ratioX = calculateDeltaRatio(deltaX, true);
        double ratioY = calculateDeltaRatio(deltaY, false);

        for (Iterator iter = siblings.iterator(); iter.hasNext();) {
            EditPart ep = (EditPart) iter.next();

            if (ignoreEditParts == null || !ignoreEditParts.contains(ep)) {

                if (ep instanceof BaseGraphicalEditPart) {
                    BaseGraphicalEditPart gep = (BaseGraphicalEditPart) ep;

                    BaseGraphicalNodeAdapter siblingAdp =
                            (BaseGraphicalNodeAdapter) gep.getModelAdapter();

                    Rectangle bnds = EditPartUtil.getModelBounds(gep);

                    Point modelLocation = bnds.getCenter();
                    Point origLocation = modelLocation.getCopy();

                    if (bnds.intersects(rightOfObject)) {
                        // For objects intersecting the area to the right of
                        // current object rect. Shift left by the size
                        // difference between opened and closed.
                        modelLocation.x -= deltaX;

                        // We may also be getting a LOT smaller in the vertical
                        // plane so we don't want to leave objects 'hanging
                        // down' way below the sub-proc (especially as this may
                        // cause them to run into objects that are being moved
                        // up and left because they're in the area right and
                        // below of closing sub-proc). So move the objects up by
                        // the ratio of the vertical size decrease.
                        double offset = modelLocation.y - newBnds.y;
                        if (offset > 0) {
                            offset *= ratioY;
                            modelLocation.y = newBnds.y + (int) offset;
                        }

                    } else if (bnds.intersects(belowObject)) {
                        // For objects intersecting the area below the current
                        // object rect. Shift up by the size difference between
                        // opened and closed.
                        modelLocation.y -= deltaY;

                        // We may also be getting a LOT smaller in the
                        // horizontal plane so we don't want to leave objects
                        // 'hanging right' way further out than the object. So
                        // move the objects left by the ratio of the horizontal
                        // size decrease.
                        double offset = modelLocation.x - newBnds.x;
                        if (offset > 0) {
                            offset *= ratioX;
                            modelLocation.x = newBnds.x + (int) offset;
                        }

                    } else if (rightAndBelowObject.contains(bnds)) {
                        // For objects that are below and right of sub-proc,
                        // move left and up.
                        modelLocation.x -= deltaX;
                        modelLocation.y -= deltaY;
                    }

                    if (!origLocation.equals(modelLocation)) {
                        cmd.appendAndExecute(siblingAdp
                                .getSetLocationCommand(editingDomain,
                                        modelLocation,
                                        siblingAdp.getSize()));
                    }

                }
            }
        }

        System.out.println("--------------\n"); //$NON-NLS-1$

    }

    /**
     * Horizontal Spacing of Objects below the area to close needs to be
     * compressed otherwise the objects below&right may overrun them (because
     * they WILL be moved to the left).
     * 
     * BUT we don't want to compress the spacing to the point that they overlap
     * each other. This method discovers the largest xDelta (amount ot move
     * left) that can be applied WITHOUT overlapping the objects below the
     * closing area.
     * 
     * @param deltaX
     * 
     * @return largest left move delta without overlapping or 0 if no move is
     *         possible without overlap.
     */
    private int getNonOverLappingDelta(boolean getXDeltaForBelowObjects) {
        // Lowest delta we've tried that does not cause over laps.
        int lowDelta = 0;

        // Highest delta we've tried so far that does cause an overlap.
        int highDelta;

        List<Rectangle> chkObjects;

        if (getXDeltaForBelowObjects) {
            chkObjects = objsBelowArea;
            highDelta = areaToClose.width - newSize.width;
        } else {
            chkObjects = objsRightOfArea;
            highDelta = areaToClose.height - newSize.height;
        }

        System.out.println("getNonOverLappingDelta(below:" //$NON-NLS-1$
                + getXDeltaForBelowObjects + ") Required Delta: " + highDelta); //$NON-NLS-1$

        if (highDelta < 2) {
            System.out.println("    Delta to small to bother with."); //$NON-NLS-1$
            return 0;
        }

        int returnDelta = 0;

        int numtries = 1;

        // First check for overlaps at the delta required to shuffle all left by
        // the amount that the area is shrinking.
        if (!checkOverlaps(chkObjects, highDelta, getXDeltaForBelowObjects)) {
            // No overlaps with beigest required move delta, so that's ok.
            returnDelta = highDelta;

        } else {

            while ((highDelta - lowDelta) > 1) {
                //
                // In a effort to be efficient we will employ a binary-chop
                // algorithm on the delta to find the best fit.
                //
                // We will check for overlaps for a delta between the lowest
                // delta
                // that we know will not cause overlaps and the highest that we
                // know
                // that does cause overlaps.

                int tryDelta = lowDelta + ((highDelta - lowDelta) / 2);

                // Check whether, using this delta, we have a overlaps.
                System.out.println("        Try delta: " + tryDelta); //$NON-NLS-1$
                boolean overlaps =
                        checkOverlaps(chkObjects,
                                tryDelta,
                                getXDeltaForBelowObjects);

                if (overlaps) {
                    // Ok, the highset delta that causes overlaps is now this.
                    // Adjust high delta and go around again.
                    highDelta = tryDelta;

                } else {
                    // There are no overlaps using this delta.
                    lowDelta = tryDelta;

                    returnDelta = tryDelta;

                }

                numtries++;
            }
        }
        System.out.println("    Num tries to find delta: " + numtries); //$NON-NLS-1$
        System.out.println("    Return delta: " + returnDelta); //$NON-NLS-1$
        return returnDelta;
    }

    /**
     * Check for overlaps in the given orientation of the given objects after
     * subtracting the given delta from that orientation from each.
     * 
     * @param objsBelowArea2
     * @param highDelta
     * @param horizontal
     * @return
     */
    private boolean checkOverlaps(List<Rectangle> objs, int delta,
            boolean horizontal) {
        boolean haveOverlaps = false;

        if (objs.size() > 1) {
            // Calculate the ration between the closing area and the sized to be
            // closed to.
            double sizeChangeRatio;

            sizeChangeRatio = calculateDeltaRatio(delta, horizontal);

            // Get a copy of the object rectangles.
            Rectangle[] objRects = new Rectangle[objs.size()];

            int o = 0;
            for (Rectangle rc : objs) {
                objRects[o++] = rc.getCopy();
            }

            // Adjust the offset by the appropriate delta.
            for (int i = 0; i < objRects.length; i++) {

                // In order to compress spacing between the objects we use ratio
                // between the original size and the size we're trying to reduce
                // by (i.e. an object that is 20% of the distance between the
                // left and right edge of the area to be closed then it PERSONAL
                // move is 20% of the nominal delta)
                //
                // i.e. objects that are further right move more than objects
                // that are further left.

                Rectangle bnds = objRects[i];
                Point center = bnds.getCenter();

                if (horizontal) {
                    // How far is the object's centre across the area To close.
                    // Only move objects that whose center is right of left
                    // edge of closed area.
                    double offset = center.x - areaToClose.x;
                    if (offset > 0) {
                        // Adjust the offset by our sizing ratio.
                        offset *= sizeChangeRatio;

                        // And move the bounds.
                        bnds.x = areaToClose.x + (int) offset;
                    }
                } else {
                    // How far is the object's centre across the area To close.
                    // Only move objects that whose center is right of left
                    // edge of closed area.
                    double offset = center.y - areaToClose.y;
                    if (offset > 0) {
                        // Adjust the offset by our sizing ratio.
                        offset *= sizeChangeRatio;

                        // And move the bounds.
                        bnds.y = areaToClose.y + (int) offset;
                    }

                }

            }

            //
            // Having adjusted all the objects, check for overlaps.
            for (int chkObj = 0; !haveOverlaps && chkObj < objRects.length; chkObj++) {

                // For each object check against all the objects AFTER it in the
                // array. This saves us checking the same 2 rectangles twice.
                // (i.e. with 3 rects we check 1 against 2 and 3, then just 2
                // against 3 (because we have already compared 1 rectangle 1 and
                // 2). This saves this algorithm being totally n'squared.

                for (int i = chkObj + 1; !haveOverlaps && i < objRects.length; i++) {

                    if (objRects[chkObj].intersects(objRects[i])) {
                        haveOverlaps = true;
                    }
                }
            }

        }

        return haveOverlaps;
    }

    /**
     * Calculate the ratio between the area to close and the amount it will be
     * closed to according to the given delta.
     * 
     * @param delta
     * @param horizontal
     * @return
     */
    private double calculateDeltaRatio(int delta, boolean horizontal) {
        double sizeChangeRatio;
        if (horizontal) {
            sizeChangeRatio =
                    (double) (areaToClose.width - delta)
                            / (double) areaToClose.width;

        } else {
            sizeChangeRatio =
                    (double) (areaToClose.height - delta)
                            / (double) areaToClose.height;

        }
        return sizeChangeRatio;
    }

    /**
     * Set up the list of siblings that will need to move to close up the given
     * area.
     * 
     * @param siblings
     * @param areaToClose
     */
    private void setupObjectBoundsMaps(List siblings) {
        objsRightOfArea = new ArrayList<Rectangle>();
        objsBelowArea = new ArrayList<Rectangle>();
        objsRightAndBelowArea = new ArrayList<Rectangle>();

        rightOfObject =
                new Rectangle(areaToClose.right(), areaToClose.y, MAX_COORD,
                        areaToClose.height);

        belowObject =
                new Rectangle(areaToClose.x, areaToClose.bottom(),
                        areaToClose.width, MAX_COORD);

        rightAndBelowObject =
                new Rectangle(areaToClose.right(), areaToClose.bottom(),
                        MAX_COORD, MAX_COORD);

        for (Iterator iter = siblings.iterator(); iter.hasNext();) {
            EditPart ep = (EditPart) iter.next();

            if (ignoreEditParts == null || !ignoreEditParts.contains(ep)) {

                if (ep instanceof BaseGraphicalEditPart) {
                    BaseGraphicalEditPart gep = (BaseGraphicalEditPart) ep;

                    BaseGraphicalNodeAdapter siblingAdp =
                            (BaseGraphicalNodeAdapter) gep.getModelAdapter();

                    Rectangle bnds = EditPartUtil.getModelBounds(gep);

                    if (bnds.intersects(rightOfObject)) {
                        objsRightOfArea.add(bnds);

                    } else if (bnds.intersects(belowObject)) {
                        objsBelowArea.add(bnds);

                    } else if (rightAndBelowObject.contains(bnds)) {
                        objsRightAndBelowArea.add(bnds);
                    }
                }
            }
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
