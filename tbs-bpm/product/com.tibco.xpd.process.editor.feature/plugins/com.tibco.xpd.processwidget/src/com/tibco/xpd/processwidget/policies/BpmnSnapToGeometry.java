/**
 * Copyright 2005 TIBCO Software Inc.
 */
package com.tibco.xpd.processwidget.policies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.tibco.xpd.processwidget.parts.LaneEditPart;
import com.tibco.xpd.processwidget.parts.PoolEditPart;

/**
 * Snap to geometry - snap to all children and children of all siblings of the
 * parent
 * 
 * @author wzurek
 */
public class BpmnSnapToGeometry extends SnapToGeometry {

    public BpmnSnapToGeometry(GraphicalEditPart part) {
        super(part);
    }

    @Override
    public int snapRectangle(Request request, int snapOrientation,
            PrecisionRectangle baseRect, PrecisionRectangle result) {

        if (request.getType().equals(RequestConstants.REQ_CREATE)
                || RequestConstants.REQ_CREATE_BENDPOINT.equals(request
                        .getType())
                || RequestConstants.REQ_MOVE_BENDPOINT
                        .equals(request.getType())) {
            // Studio tools for creating new objects preserve the create request
            // extended data. This means that certain info stored there (the
            // alignment guide on/off anchor keys) remain in place.
            // This caused alignment guides to remain on screen during create
            // object even when they were not pertinent to current position.
            //
            // So Remove anchor keys left over from last time.
            Map extendedData = request.getExtendedData();
            if (extendedData != null) {
                extendedData.remove(SnapToGeometry.KEY_EAST_ANCHOR);
                extendedData.remove(SnapToGeometry.KEY_NORTH_ANCHOR);
                extendedData.remove(SnapToGeometry.KEY_SOUTH_ANCHOR);
                extendedData.remove(SnapToGeometry.KEY_WEST_ANCHOR);
            }
        }

        return super.snapRectangle(request, snapOrientation, baseRect, result);
    }

    @Override
    public int snapRectangle(Request request, int snapOrientation,
            PrecisionRectangle[] baseRects, PrecisionRectangle result) {

        if (request.getType().equals(RequestConstants.REQ_CREATE)
                || RequestConstants.REQ_CREATE_BENDPOINT.equals(request
                        .getType())
                || RequestConstants.REQ_MOVE_BENDPOINT
                        .equals(request.getType())) {
            // Studio tools for creating new objects preserve the create request
            // extended data. This means that certain info stored there (the
            // alignment guide on/off anchor keys) remain in place.
            // This caused alignment guides to remain on screen during create
            // object even when they were not pertinent to current position.
            //
            // So Remove anchor keys left over from last time.
            Map extendedData = request.getExtendedData();
            if (extendedData != null) {
                extendedData.remove(SnapToGeometry.KEY_EAST_ANCHOR);
                extendedData.remove(SnapToGeometry.KEY_NORTH_ANCHOR);
                extendedData.remove(SnapToGeometry.KEY_SOUTH_ANCHOR);
                extendedData.remove(SnapToGeometry.KEY_WEST_ANCHOR);
            }
        }
        return super.snapRectangle(request, snapOrientation, baseRects, result);
    }

    /**
     * Returns the correction value for the given entries and sides. During a
     * move, the left, right, or center is free to snap to a location.
     * 
     * @param entries
     *            the entries
     * @param extendedData
     *            the requests extended data
     * @param vert
     *            <code>true</code> if the correction is vertical
     * @param near
     *            the left/top side of the rectangle
     * @param far
     *            the right/bottom side of the rectangle
     * @return the correction amount or #getThreshold () if no correction was
     *         made
     */
    @Override
    protected double getCorrectionFor(Entry entries[], Map extendedData,
            boolean vert, double near, double far) {
        far -= 1.0;
        double total = near + far;

        /**
         * NOTE THAT WE OPVERRIDE THIS METHOD TO GET RID OF THE HORRIBLE FUDGE
         * BELOW.
         * 
         * This is supposed to make up for the fact that populateRowsAndCols()
         * calculates centre positions like this
         * 
         * :: "bounds.x + (bounds.width - 1) / 2"
         * 
         * However, it didn't work for both even and odd sized objects.
         * 
         * As it happens getting rid of THIS fudge, and the overriding the
         * calculation in populateRowsAndCols() top get rid of the above "- 1"
         * in calculation above seems to make things work perfectly for both
         * even and odd sized objects.
         */
        if (false) {
            // If the width is even (i.e., odd right now because we have reduced
            // one
            // pixel from
            // far) there is no middle pixel so favor the left-most/top-most
            // pixel
            // (which is what
            // populateRowsAndCols() does by using int precision).
            if ((int) (near - far) % 2 != 0)
                total -= 1.0;
        }

        double result =
                getCorrectionFor(entries, extendedData, vert, total / 2, 0);
        if (result == getThreshold())
            result = getCorrectionFor(entries, extendedData, vert, near, -1);
        if (result == getThreshold())
            result = getCorrectionFor(entries, extendedData, vert, far, 1);
        return result;
    }

    /**
     * Updates the cached row and column Entries using the provided parts.
     * 
     * @since 3.0
     * @param parts
     *            a List of EditParts
     */
    @Override
    protected void populateRowsAndCols(List parts) {
        rows = new Entry[parts.size() * 3];
        cols = new Entry[parts.size() * 3];
        for (int i = 0; i < parts.size(); i++) {
            GraphicalEditPart child = (GraphicalEditPart) parts.get(i);
            Rectangle bounds = getFigureBounds(child);
            cols[i * 3] = new MyEntry(-1, bounds.x);
            rows[i * 3] = new MyEntry(-1, bounds.y);

            /**
             * The default implementation of this give different results for odd
             * sized things than it does for even sized things.
             */
            // cols[i * 3 + 1] = new MyEntry(0, bounds.x + (bounds.width - 1) /
            // 2);
            // rows[i * 3 + 1] =
            // new MyEntry(0, bounds.y + (bounds.height - 1) / 2);

            /* So drop the -1 fudge factor! */
            cols[i * 3 + 1] = new MyEntry(0, bounds.x + (bounds.width / 2));
            rows[i * 3 + 1] = new MyEntry(0, bounds.y + (bounds.height / 2));

            cols[i * 3 + 2] = new MyEntry(1, bounds.right() - 1);
            rows[i * 3 + 2] = new MyEntry(1, bounds.bottom() - 1);
        }
    }

    private class MyEntry extends Entry {

        /**
         * @param arg0
         * @param arg1
         */
        protected MyEntry(int type, int location) {
            super(type, location);
        }

    }

    /**
     * Generates a list of parts which should be snapped to. The list is the
     * original children, minus the given exclusions, minus and children whose
     * figures are not visible.
     * 
     * @since 3.0
     * @param exclusions
     *            the children to exclude
     * @return a list of parts which should be snapped to
     * 
     *         Modification:<br>
     *         Use also children of all siblings, they are represented as
     *         DummyEditParts in the list here
     */
    @Override
    protected List generateSnapPartsList(List exclusions) {
        // Don't snap to any figure that is being dragged
        List children = new ArrayList(container.getChildren());
        children.removeAll(exclusions);

        // Don't snap to hidden figures
        List hiddenChildren = new ArrayList();
        for (Iterator iter = children.iterator(); iter.hasNext();) {
            GraphicalEditPart child = (GraphicalEditPart) iter.next();
            if (!child.getFigure().isVisible())
                hiddenChildren.add(child);
        }
        children.removeAll(hiddenChildren);

        // If object being created / moved is in lane the allow alignment across
        // all lanes in process
        List siblings = Collections.EMPTY_LIST;

        if (container instanceof LaneEditPart) {
            siblings = new ArrayList();

            PoolEditPart pEP = ((LaneEditPart) container).getParentPool();

            for (Iterator iter = pEP.getParent().getChildren().iterator(); iter
                    .hasNext();) {
                EditPart ep = (EditPart) iter.next();

                if (ep instanceof PoolEditPart) {
                    PoolEditPart poolEP = (PoolEditPart) ep;

                    for (Iterator iterator = poolEP.getChildren().iterator(); iterator
                            .hasNext();) {
                        EditPart childEP = (EditPart) iterator.next();

                        if (childEP instanceof LaneEditPart) {
                            siblings.add(childEP);
                        }
                    }
                }
            }
        }

        for (Iterator iter = siblings.iterator(); iter.hasNext();) {
            EditPart parentEP = (EditPart) iter.next();
            if (parentEP != container) {
                for (Iterator otcherChild = parentEP.getChildren().iterator(); otcherChild
                        .hasNext();) {
                    GraphicalEditPart gep =
                            (GraphicalEditPart) otcherChild.next();
                    if (gep.getFigure().isVisible()) {
                        Rectangle rect = gep.getFigure().getBounds().getCopy();
                        rect.translate(0, -rect.y - rect.height - 10);
                        children.add(new DummyEditPart(rect));
                    }
                }
            }
        }

        return children;
    }

    class DummyEditPart extends AbstractGraphicalEditPart {

        private final Rectangle bounds;

        protected DummyEditPart(Rectangle bounds) {
            this.bounds = bounds;
        }

        @Override
        protected IFigure createFigure() {
            RectangleFigure fig = new RectangleFigure();
            fig.setBounds(bounds);
            return fig;
        }

        @Override
        protected void createEditPolicies() {
        }
    }
}
