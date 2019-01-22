/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Locator;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.handles.AbstractHandle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import com.tibco.xpd.processwidget.figures.XPDLineUtilities;
import com.tibco.xpd.processwidget.neatstuff.IFadeableFigure;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.AbstractClickOrDragGadgetInfo.GADGET_SHAPE;

/**
 * Handle Figure for the click or drag gadget
 * 
 * @author aallway
 * @since 3.2
 */
class ClickOrDragGadgetHandle extends AbstractHandle implements IFadeableFigure {
    private int minimumAlpha = 0;

    private int alpha = 255;

    private GraphicalEditPart hostEditPart;

    private ClickOrDragGadgetRequest hoverRequest = null;

    private ClickOrDragGadgetRequest mouseEnterRequest = null;

    private AbstractClickOrDragGadgetInfo clickOrDragGagdetInfo;

    private Dimension imageSz = null;

    private boolean disposed = false;

    private MouseHoverListener mouseHoverListener;

    private ConnectionAnchor hostFigureAnchor = null;

    private GadgetHandleFeedback gadgetHandleFeeback;

    private ClickOrDragGadgetHandle mainGadgetInGroup = null;

    public ClickOrDragGadgetHandle(GraphicalEditPart hostEditPart,
            AbstractClickOrDragGadgetInfo clickOrDragGagdetInfo,
            GadgetHandleFeedback gadgetHandleFeedback) {
        super();
        this.hostEditPart = hostEditPart;
        this.clickOrDragGagdetInfo = clickOrDragGagdetInfo;
        this.gadgetHandleFeeback = gadgetHandleFeedback;
        this.mainGadgetInGroup = this;

        setOwner(hostEditPart);
        setLocator(new ClickOrDragGadgetLocator());
        setCursor(Cursors.HAND);

        setForegroundColor(ColorConstants.darkBlue);

        if (clickOrDragGagdetInfo.getReferenceTypeImage() != null) {
            ImageData imageData =
                    clickOrDragGagdetInfo.getReferenceTypeImage()
                            .getImageData();
            imageSz = new Dimension(imageData.width, imageData.height);
        }

        mouseHoverListener = new MouseHoverListener();
        this.addMouseMotionListener(mouseHoverListener);
    }

    public void dispose() {
        disposed = true;
        this.removeMouseMotionListener(mouseHoverListener);

        if (hoverRequest != null) {
            hostEditPart.eraseSourceFeedback(hoverRequest);
            hoverRequest = null;
        }

        if (mouseEnterRequest != null) {
            hostEditPart.eraseSourceFeedback(mouseEnterRequest);
            mouseEnterRequest = null;
        }

        // Gadget info has a back reference to us - so this should make garbage
        // collections's task easier
        clickOrDragGagdetInfo = null;
        hostEditPart = null;

        return;
    }

    public boolean isDisposed() {
        return disposed;
    }

    /**
     * @param hostFigureAnchor
     *            the hostFigureAnchor to set
     */
    public void setHostFigureAnchor(ConnectionAnchor hostFigureAnchor) {
        this.hostFigureAnchor = hostFigureAnchor;
    }

    @Override
    protected DragTracker createDragTracker() {
        return new ClickOrDragGadgetTracker(hostEditPart,
                clickOrDragGagdetInfo, gadgetHandleFeeback);
    }

    @Override
    public Dimension getPreferredSize(int hint, int hint2) {
        return new Dimension(18, 18);
    }

    @Override
    public Integer getAlpha() {
        return alpha;
    }

    @Override
    public void setAlpha(int alpha) {
        if (alpha != this.alpha) {
            if (alpha >= minimumAlpha) {
                this.alpha = alpha;
            } else {
                this.alpha = minimumAlpha;
            }
            revalidate();
            repaint();
        }
    }

    /**
     * @return the minimumAlpha
     */
    public int getMinimumAlpha() {
        return minimumAlpha;
    }

    /**
     * @param minimumAlpha
     *            the minimumAlpha to set
     */
    public void setMinimumAlpha(int minimumAlpha) {
        this.minimumAlpha = minimumAlpha;

    }

    @Override
    protected boolean useLocalCoordinates() {
        return true;
    }

    @Override
    protected void paintFigure(Graphics graphics) {
        graphics.pushState();
        graphics.setAlpha(alpha);
        graphics.setAntialias(SWT.ON);
        graphics.setLineWidth(2);

        Rectangle b = this.getBounds().getCopy();
        b.shrink(1, 1);

        if (GADGET_SHAPE.SQUARE.equals(clickOrDragGagdetInfo.getGadgetShape())) {
            graphics.fillRoundRectangle(b, 4, 4);
            graphics.drawRoundRectangle(b, 4, 4);
        } else {
            graphics.fillOval(b);
            graphics.drawOval(b);
        }

        Image img = clickOrDragGagdetInfo.getReferenceTypeImage();
        if (img != null) {
            Rectangle src = new Rectangle(0, 0, imageSz.width, imageSz.height);
            if (!GADGET_SHAPE.SQUARE.equals(clickOrDragGagdetInfo
                    .getGadgetShape())) {
                b.shrink(2, 2);
            } else {
                b.shrink(1, 1);
            }

            if (b.width > 0 && b.height > 0) {
                graphics.drawImage(img, src, b);
            }
        }

        graphics.popState();

        return;
    }

    /**
     * @return the mainGadgetInGroup
     */
    public ClickOrDragGadgetHandle getMainGadgetInGroup() {
        return mainGadgetInGroup;
    }

    /**
     * @param mainGadgetInGroup
     *            the mainGadgetInGroup to set
     */
    public void setMainGadgetInGroup(ClickOrDragGadgetHandle mainGadgetInGroup) {
        this.mainGadgetInGroup = mainGadgetInGroup;
    }

    /**
     * @param gadgetHandle
     * @return
     */
    public static boolean isMainGadgetInGroup(
            ClickOrDragGadgetHandle gadgetHandle) {
        return gadgetHandle == gadgetHandle.mainGadgetInGroup;
    }

    /**
     * @return Returns a new hover over gadget request
     */
    private ClickOrDragGadgetRequest createHoverRequest() {
        ClickOrDragGadgetRequest req = new ClickOrDragGadgetRequest();
        req.setType(ClickOrDragGadgetRequest.REQ_CLICKORDRAG_GADGET_HOVER);
        req.setHostEditPart(hostEditPart);
        req.setClickOrDragGadgetHandle(gadgetHandleFeeback);
        req.setClickOrDragGadgetInfo(clickOrDragGagdetInfo);
        return req;
    }

    /**
     * @return Returns a new hover over gadget request
     */
    private ClickOrDragGadgetRequest createMouseEnterRequest() {
        ClickOrDragGadgetRequest req = new ClickOrDragGadgetRequest();
        req.setType(ClickOrDragGadgetRequest.REQ_CLICKORDRAG_GADGET_MOUSEENTER);
        req.setHostEditPart(hostEditPart);
        req.setClickOrDragGadgetHandle(gadgetHandleFeeback);
        req.setClickOrDragGadgetInfo(clickOrDragGagdetInfo);
        return req;
    }

    /**
     * Mouse motion listener to allow the owning edit part (or it's policies) to
     * provider hover feedback when mouse is over gadget.
     * <p>
     * The click or drag policy (as standard) will give the gadget info the
     * chance ot contribute hover feedback.
     */
    private class MouseHoverListener implements MouseMotionListener {

        @Override
        public void mouseHover(MouseEvent me) {
            if (hoverRequest == null) {
                hoverRequest = createHoverRequest();
            }
            hostEditPart.showSourceFeedback(hoverRequest);
        }

        @Override
        public void mouseExited(MouseEvent me) {
            if (hoverRequest != null) {
                hostEditPart.eraseSourceFeedback(hoverRequest);
                hoverRequest = null;
            }

            if (mouseEnterRequest != null) {
                hostEditPart.eraseSourceFeedback(mouseEnterRequest);
                mouseEnterRequest = null;
            }
        }

        @Override
        public void mouseDragged(MouseEvent me) {
        }

        @Override
        public void mouseEntered(MouseEvent me) {
            if (mouseEnterRequest == null) {
                mouseEnterRequest = createMouseEnterRequest();
            }
            hostEditPart.showSourceFeedback(mouseEnterRequest);
        }

        @Override
        public void mouseMoved(MouseEvent me) {
        }
    }

    /**
     * Locator for the gadget.
     */
    class ClickOrDragGadgetLocator implements Locator {
        private GadgetDragLineLocator lineLocator = new GadgetDragLineLocator();

        @Override
        public void relocate(IFigure handle) {
            if (hostFigureAnchor == null) {
                throw new RuntimeException(
                        "hostFigureAnchor must be set before use."); //$NON-NLS-1$
            }

            if (clickOrDragGagdetInfo == null) {
                return;
            }

            //
            // *********************************************************
            // NOTE: ALL CALCULATIONS ARE DONE IN ABSOLUTE COORDINATES
            // UNTIL THE LAST MINUTE AT WHICH TIME WE CONVERT TO GADGET RELATIVE
            // COORDS.
            // *********************************************************
            //

            // Get all the figures and bits and bobs we're interested in.
            ClickOrDragGadgetHandle gadgetHandle =
                    (ClickOrDragGadgetHandle) handle;

            Dimension gadgetRelativeSize = gadgetHandle.getPreferredSize();
            Dimension gadgetAbsoluteSize = gadgetRelativeSize;
            hostFigureAnchor.getOwner().translateToAbsolute(gadgetAbsoluteSize);

            // get The target referenced figure centre (In the same coords
            // system as anchor works in (which is absolute coords)
            Point directionReferencePoint =
                    clickOrDragGagdetInfo
                            .getHandleDirectionAbsoluteReferencePoint();
            Point origRefPoint = directionReferencePoint;
            if (directionReferencePoint == null) {
                directionReferencePoint =
                        hostEditPart.getFigure().getBounds().getBottomRight();
                origRefPoint = directionReferencePoint.getCopy();
                directionReferencePoint.x += 10000;
                directionReferencePoint.y += 10000;
                hostEditPart.getFigure()
                        .translateToAbsolute(directionReferencePoint);
            }

            // Given that calculate the position on host border in the direction
            // of the line.
            Point srcPoint;

            if (AbstractClickOrDragGadgetPolicy.NEW_GADGET_LAYOUT) {
                srcPoint = origRefPoint.getCopy();
                hostEditPart.getFigure().translateToAbsolute(srcPoint);
            } else {
                srcPoint =
                        hostFigureAnchor.getLocation(directionReferencePoint);
            }

            PointList directionLinePoints = new PointList();
            directionLinePoints.addPoint(srcPoint);
            directionLinePoints.addPoint(directionReferencePoint);

            int lineLength =
                    (int) XPDLineUtilities.getLineLength(srcPoint,
                            directionReferencePoint);

            // Calculate first location along direction line that does not
            // overlap host figure.
            Collection<Rectangle> exclusionZones =
                    clickOrDragGagdetInfo
                            .getGadgetPositionExclusionZones(hostEditPart);

            if (exclusionZones == null || exclusionZones.isEmpty()) {
                exclusionZones = new ArrayList<Rectangle>();
                Rectangle hostBounds =
                        hostEditPart.getFigure().getBounds().getCopy();
                hostEditPart.getFigure().translateToAbsolute(hostBounds);
                exclusionZones.add(hostBounds);

            }

            boolean firstGadgetForHost = isFirstGadgetForHost(gadgetHandle);

            int startPosOnLine;
            if (AbstractClickOrDragGadgetPolicy.NEW_GADGET_LAYOUT) {
                startPosOnLine = (int) (gadgetAbsoluteSize.width * 0.5f);

                if (!firstGadgetForHost) {
                    gadgetHandleFeeback.getDragLine().setVisible(false);
                }
            } else {
                startPosOnLine = (int) (gadgetAbsoluteSize.width * 1.5f);
            }

            Rectangle gadgetBounds;

            if (!AbstractClickOrDragGadgetPolicy.NEW_GADGET_LAYOUT
                    || firstGadgetForHost) {
                //
                // For old layout OR first gadget of new layout then space each
                // gadget as far along line as it
                // needs to be to avoid other gadgets / host figure.
                //
                int posOnLine =
                        getFirstLocationOutsideZones(gadgetHandle,
                                startPosOnLine,
                                lineLength,
                                directionLinePoints,
                                exclusionZones);

                // Calculate the first location that doesn't overlap any
                // previous gadget handles in feedback layer.
                gadgetBounds =
                        avoidOtherGadgetOverlaps(gadgetHandle,
                                posOnLine,
                                lineLength,
                                directionLinePoints);

                gadgetHandle.getParent().translateToRelative(gadgetBounds);

            } else {
                //
                // In new layout scheme, position ubsequent gadgets alongside
                // first.
                boolean isMainGadgetInGroup = isMainGadgetInGroup(gadgetHandle);
                if (isMainGadgetInGroup) {
                    gadgetBounds = getNextMainGadgetBounds(gadgetHandle);
                } else {
                    gadgetBounds = getNextSubGadgetBounds(gadgetHandle);
                }
            }

            gadgetHandle.setBounds(gadgetBounds);
            lineLocator.relocate(gadgetHandleFeeback.getGadgetDragLine());

            return;
        }

        /**
         * @param gadgetHandle
         * @return The bounds of the next sub-gadget in group.
         */
        private Rectangle getNextSubGadgetBounds(
                ClickOrDragGadgetHandle gadgetHandle) {
            Rectangle prevBnds = null;

            IFigure parent = gadgetHandle.getParent();

            List children = parent.getChildren();
            for (Iterator iterator = children.iterator(); iterator.hasNext();) {
                IFigure child = (IFigure) iterator.next();

                if (child instanceof ClickOrDragGadgetHandle) {
                    if (child == gadgetHandle) {
                        if (prevBnds == null) {
                            throw new IllegalStateException(
                                    "This method should not be called for the first gadget!"); //$NON-NLS-1$
                        }

                        Dimension sz = gadgetHandle.getPreferredSize();
                        hostFigureAnchor.getOwner().translateToAbsolute(sz);

                        Rectangle bnds = new Rectangle();
                        bnds.x = prevBnds.x;
                        bnds.y = prevBnds.y + prevBnds.height + 1;
                        bnds.width = sz.width;
                        bnds.height = sz.height;
                        return bnds;

                    } else if (((ClickOrDragGadgetHandle) child)
                            .getMainGadgetInGroup() == gadgetHandle
                            .getMainGadgetInGroup()) {
                        // Record bounds of previous gadgets in group until we
                        // find the one we're dealing with now.
                        prevBnds = child.getBounds().getCopy();
                    }
                }
            }

            return new Rectangle();
        }

        /**
         * Get the bounds of the next gadget to locate.
         * 
         * @param parent
         * @return the bounds of the next gadget to locate.
         */
        private Rectangle getNextMainGadgetBounds(
                ClickOrDragGadgetHandle gadgetHandle) {
            Rectangle prevBnds = null;

            IFigure parent = gadgetHandle.getParent();

            List children = parent.getChildren();
            for (Iterator iterator = children.iterator(); iterator.hasNext();) {
                IFigure child = (IFigure) iterator.next();

                if (child instanceof ClickOrDragGadgetHandle
                        && ((ClickOrDragGadgetHandle) child).hostEditPart == gadgetHandle.hostEditPart) {
                    if (child == gadgetHandle) {
                        if (prevBnds == null) {
                            throw new IllegalStateException(
                                    "This method should not be called for the first gadget!"); //$NON-NLS-1$
                        }

                        Dimension sz = gadgetHandle.getPreferredSize();
                        hostFigureAnchor.getOwner().translateToAbsolute(sz);

                        Rectangle bnds = new Rectangle();
                        bnds.x = prevBnds.x + prevBnds.width + 1;
                        bnds.y = prevBnds.y;
                        bnds.width = sz.width;
                        bnds.height = sz.height;
                        return bnds;

                    } else if (ClickOrDragGadgetHandle
                            .isMainGadgetInGroup(((ClickOrDragGadgetHandle) child))) {
                        prevBnds = child.getBounds().getCopy();
                    }
                }
            }

            return new Rectangle();
        }

        /**
         * @param gadgetHandle
         * @return true if the given gadget is the first in the feedback layer.
         */
        private boolean isFirstGadgetForHost(
                ClickOrDragGadgetHandle gadgetHandle) {
            IFigure parent = gadgetHandle.getParent();

            List children = parent.getChildren();
            for (Iterator iterator = children.iterator(); iterator.hasNext();) {
                IFigure child = (IFigure) iterator.next();

                if (child instanceof ClickOrDragGadgetHandle) {
                    if (((ClickOrDragGadgetHandle) child).hostEditPart == gadgetHandle.hostEditPart) {
                        // Found a gadget for the same edit part, if the passed
                        // gadget is this one then it is the first. Otherwise it
                        // is not.
                        if (child == gadgetHandle) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }

            }
            return false;
        }

        /**
         * If the given gadget location overlaps previous gadgets in the same
         * parent (handle / feedback layer) then move it along the direction
         * line until it does not.
         * 
         * @param xrefHandle
         * @param lineLength
         * @param directionLinePoints
         * @return
         */
        private Rectangle avoidOtherGadgetOverlaps(
                ClickOrDragGadgetHandle xrefHandle, int startPosOnLine,
                int lineLength, PointList directionLinePoints) {
            Rectangle newGadgetBounds = null;

            // NOTE:
            // This method assumes that the handles are laid out in order that
            // they are listed in parent.
            IFigure parent = xrefHandle.getParent();
            if (parent != null) {
                List siblings = parent.getChildren();
                if (siblings != null) {
                    //
                    // Get list of siblings bounds before the gadget we are
                    // dealing with.
                    List<Rectangle> exclusionZones = new ArrayList<Rectangle>();

                    for (Iterator iter = siblings.iterator(); iter.hasNext();) {
                        IFigure fig = (IFigure) iter.next();

                        if (fig == xrefHandle) {
                            break;
                        }

                        if (fig instanceof ClickOrDragGadgetHandle) {
                            // Allow a slight overlap
                            Rectangle bounds2 = fig.getBounds().getCopy();
                            fig.translateToAbsolute(bounds2);
                            bounds2.shrink(bounds2.width / 3,
                                    bounds2.height / 3);
                            exclusionZones.add(bounds2);
                        }
                    }

                    if (exclusionZones.size() > 0) {
                        int newPosOnLine =
                                getFirstLocationOutsideZones(xrefHandle,
                                        startPosOnLine,
                                        lineLength,
                                        directionLinePoints,
                                        exclusionZones);
                        startPosOnLine = newPosOnLine;
                    }
                }
            }

            if (newGadgetBounds == null) {
                newGadgetBounds =
                        getBoundsFromPosOnLine(xrefHandle,
                                lineLength,
                                directionLinePoints,
                                startPosOnLine);
            }

            return newGadgetBounds;
        }

        /**
         * Calculate first location along direction line that does not overlap
         * the given zones.
         * 
         * @param xrefHandle
         * @param lineLength
         * @param directionLinePoints
         * @return
         */
        private int getFirstLocationOutsideZones(
                ClickOrDragGadgetHandle xrefHandle, int startPosOnLine,
                int lineLength, PointList directionLinePoints,
                Collection<Rectangle> exclusionZones) {
            Rectangle gadgetBounds;

            int posOnLine = startPosOnLine;

            while (true) {
                boolean mustEnd = false;

                if (posOnLine >= lineLength) {
                    posOnLine = startPosOnLine;
                    mustEnd = true;
                }

                gadgetBounds =
                        getBoundsFromPosOnLine(xrefHandle,
                                lineLength,
                                directionLinePoints,
                                posOnLine);

                boolean inExclusionZone = false;
                for (Rectangle zone : exclusionZones) {
                    if (gadgetBounds.intersects(zone)) {
                        inExclusionZone = true;
                        break;
                    }
                }

                if (mustEnd || !inExclusionZone) {
                    break;
                }

                posOnLine += 1;
            }

            return posOnLine;
        }

        private Rectangle getBoundsFromPosOnLine(
                ClickOrDragGadgetHandle xrefHandle, int lineLength,
                PointList directionLinePoints, int posOnLine) {
            Dimension sz = xrefHandle.getPreferredSize();

            hostFigureAnchor.getOwner().translateToAbsolute(sz);
            // xrefHandle.translateToRelative(sz);

            Point handleCentre =
                    XPDLineUtilities
                            .getLinePointFromOffset(directionLinePoints,
                                    posOnLine);

            return new Rectangle(handleCentre.x - (sz.width / 2),
                    handleCentre.y - (sz.height / 2), sz.width, sz.height);
        }

    }

    /**
     * Locator for the click or drag gadgets 'attach to host' line.
     * <p>
     * Note that this only has effect when the gadget is not being dragged.
     */
    private class GadgetDragLineLocator implements Locator {

        @Override
        public void relocate(IFigure target) {
            Rectangle b =
                    gadgetHandleFeeback.getGadgetHandle().getBounds().getCopy();
            gadgetHandleFeeback.getGadgetHandle().translateToAbsolute(b);

            Point dragStartLine =
                    gadgetHandleFeeback.getHostAnchor()
                            .getLocation(b.getCenter());
            Point dragEndLine =
                    gadgetHandleFeeback.getGadgetAnchor()
                            .getLocation(dragStartLine);
            target.translateToRelative(dragStartLine);
            target.translateToRelative(dragEndLine);

            ((Polyline) target).setStart(dragStartLine);
            ((Polyline) target).setEnd(dragEndLine);

            return;
        }
    }

}
