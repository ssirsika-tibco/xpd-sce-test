/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */
package com.tibco.xpd.processwidget.figures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.handles.HandleBounds;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.figures.anchors.LineAnchor;
import com.tibco.xpd.processwidget.figures.layouts.TaskFigureLayout;
import com.tibco.xpd.processwidget.figures.layouts.TaskFigureLayout.TaskTextBlock;
import com.tibco.xpd.resources.ui.XpdColorRegistry;

/**
 * Process editor's Task Figure.
 * 
 * 
 * @author wzurek / aallway
 * @since v1.0
 */
public class TaskFigure extends BaseLogExceptionFadeableFigure implements
        HandleBounds, LineAnchor.LineAnchorLinesProvider, IHighlightableFigure {

    public static final int CORNER_ARC = 8;

    public static final int TRANSACTIONAL_BORDER_SIZE = 3;

    private boolean isTransactional = false;

    private boolean highlightOn = false;

    private Figure openCloseButton = null;

    /**
     * ABPM-911: Saket: Set {@link TooltipFigure} <code>true</code> if dotted
     * border is needed for a figure.
     */
    private boolean needDottedBorder = false;

    /*
     * ABPM-911: Saket: Switching type from ImageFigure to IFigure to allow this
     * imageFigure to be either an internally created ImageFigure or any figure
     * of the owner’s choice.
     */
    private IFigure imageFigure;

    private static Image referenceTaskIcon = ProcessWidgetPlugin.getDefault()
            .getImageRegistry()
            .get(ProcessWidgetConstants.IMG_TOOL_REFERENCETASK_OVERLAY);

    private ImageFigure referenceImageFigure;

    private ScalableFreeformLayeredPane content;

    private XPDGridLayer gridLayer;

    private List markers = Collections.EMPTY_LIST;

    private String text;

    private TaskFigureLayout taskFigureLayout;

    protected static final String STD_FONT = "STD_FONT"; //$NON-NLS-1$

    protected static final String BIG_FONT = "BIG_FONT"; //$NON-NLS-1$

    private int borderWidth = 1;

    public static FontRegistry registry;
    static {
        if (PlatformUI.isWorkbenchRunning()) {
            PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
                @Override
                public void run() {
                    registry = new FontRegistry();

                    Font df = JFaceResources.getDialogFont();
                    FontData fds = df.getFontData()[0];

                    registry.put(STD_FONT, new FontData[] { fds });

                    FontData big = new FontData();
                    big.setName(fds.getName());
                    big.setLocale(fds.getLocale());
                    big.setStyle(fds.getStyle());
                    big.setHeight((int) (fds.getHeight() * 1.5f));

                    registry.put(BIG_FONT, new FontData[] { big });
                }
            });
        }
    };

    /**
     * Task / Embedded sub-process figure.
     */
    public TaskFigure() {
        setMinimumSize(new Dimension(15, 15));

        // flow.setFont(registry.get(BIG_FONT));

        content = new XPDScalableFreeformLayeredPane();
        FreeformLayer pl = new FreeformLayer();
        pl.setLayoutManager(new XYLayout());

        gridLayer = new XPDGridLayer();
        content.add(gridLayer, LayerConstants.GRID_LAYER);

        content.add(pl, LayerConstants.PRIMARY_LAYER);

        content.setLayoutManager(new StackLayout());
        content.setVisible(false);
        add(content);

        taskFigureLayout = new TaskFigureLayout(this);

        setLayoutManager(taskFigureLayout);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.draw2d.Figure#setForegroundColor(org.eclipse.swt.graphics
     * .Color)
     */
    @Override
    public void setForegroundColor(Color fg) {
        super.setForegroundColor(fg);

        if (imageFigure != null && !(imageFigure instanceof ImageFigure)) {
            imageFigure.setForegroundColor(fg);
        }
    }

    /**
     * Set the task text.
     * 
     * @param text
     */
    public void setText(String text) {
        this.text = text;

        // TODO Check whether we may need to layout first (may be done via
        // repaint();
        repaint();
    }

    /**
     * @return the task text
     */
    public String getText() {
        return text;
    }

    public void setContentsVisible(boolean showContent) {
        if (showContent != isContentsVisible()) {
            if (showContent) {
                // add content figure
                content.setVisible(true);

                if (imageFigure != null) {
                    imageFigure.setVisible(false);
                }
            } else {
                // remove content figure
                content.setVisible(false);

                if (imageFigure != null) {
                    imageFigure.setVisible(true);
                }
            }
            invalidate();
            // layout();
        }
    }

    /**
     * ABPM-911: If passed <code>true</code>, then task border will be dotted.
     * 
     * @param dottedBorder
     */
    public void setNeedDottedBorder(boolean dottedBorder) {
        if (dottedBorder != needDottedBorder) {
            needDottedBorder = dottedBorder;
            repaint();

        }
    }

    public boolean isContentsVisible() {
        return content.isVisible();
    }

    /**
     * @param list
     *            list of Image-s
     */
    public void setMarkers(List list) {
        for (Iterator iter = markers.iterator(); iter.hasNext();) {
            remove((IFigure) iter.next());
        }
        markers = new ArrayList(list.size());
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object obj = iter.next();
            IFigure f;
            if (obj instanceof Image) {
                f = new PrintableImageFigure();
                ((ImageFigure) f).setImage((Image) obj);
            } else {
                f = (IFigure) obj;
            }
            markers.add(f);
            add(f);
        }
        invalidate();
    }

    public void setOpenCloseButton(Figure btn) {
        if (btn == openCloseButton) {
            // Don't remove button if its the same as we've already got.
            // Otherwise the mouse up event can go astray when we get set
            // during processing command for button.
            return;
        }

        if (openCloseButton != null) {
            remove(openCloseButton);
        }

        openCloseButton = btn;

        if (openCloseButton != null) {
            add(openCloseButton);
        }

        invalidate();
    }

    /**
     * Set ImageFigure.
     * 
     * @param newImageFigure
     *            The new image figure to be set.
     */
    public void setImageFigure(IFigure newImageFigure) {

        /*
         * Remove figure if it's there.
         */
        if (imageFigure != null) {
            remove(imageFigure);
        }

        /*
         * Set new imageFigure.
         */
        imageFigure = newImageFigure;

        /*
         * Add a new image figure.
         */
        if (newImageFigure != null) {

            add(newImageFigure);

        }

        /*
         * Lay out and invalidate.
         */
        layout();
        invalidate();

    }

    /**
     * Set ImageFigure and then set the image on it.
     * 
     * @param image
     *            The image to be set on the ImageFigure.
     * @param isReferenceTask
     */
    public void setImage(Image image, boolean isReferenceTask) {
        if (!isReferenceTask) {
            if (referenceImageFigure != null) {
                remove(referenceImageFigure);
                referenceImageFigure = null;
            }
        }

        if (image == null) {
            if (imageFigure != null) {
                remove(imageFigure);
                imageFigure = null;
            }

        } else {

            if (imageFigure == null
                    || !(imageFigure instanceof PrintableImageFigure)) {

                if (imageFigure != null) {
                    remove(imageFigure);
                }

                imageFigure = new PrintableImageFigure();

                // Make sure reference image is re-added on top of normal image.
                if (referenceImageFigure != null) {
                    remove(referenceImageFigure);
                }

                add(imageFigure);

                if (referenceImageFigure != null) {
                    add(referenceImageFigure);
                }
            }

            ((ImageFigure) imageFigure).setImage(image);
        }

        if (isReferenceTask) {
            if (referenceImageFigure == null) {
                referenceImageFigure = new PrintableImageFigure();
                referenceImageFigure.setImage(referenceTaskIcon);
                add(referenceImageFigure);
            }
        }

        layout();
        invalidate();

        return;
    }

    public void setTransactional(boolean isTransactional) {
        this.isTransactional = isTransactional;
    }

    @Override
    protected void paintFigure(Graphics gr) {

        gr.pushState();
        Rectangle ca = getHandleBounds().getCopy();

        int aa = gr.getAntialias();
        gr.setAntialias(SWT.ON);

        /*
         * THIS WAS A QUICK TRYOUT FOR SHADOW ON Task Figures... Will probably
         * be better eventually having a separate layer for the painting of
         * shadows... ca.width -= XPDFigureUtilities.SHADOW_OFFSETCX; ca.height
         * -= XPDFigureUtilities.SHADOW_OFFSETCY;
         * 
         * 
         * if (!(gr instanceof PrinterGraphics)) { // Paint shadow...
         * 
         * Rectangle shadowRC = ca.getCopy();
         * 
         * int dx = (CORNER_ARC/2); int dy = (CORNER_ARC/2);
         * 
         * shadowRC.x += dx; shadowRC.y += dy;
         * 
         * shadowRC.resize(-(dx - XPDFigureUtilities.SHADOW_OFFSETCX), -(dy -
         * XPDFigureUtilities.SHADOW_OFFSETCY) );
         * 
         * Color color = gr.getBackgroundColor();
         * gr.setBackgroundColor(ColorConstants.darkGray);
         * 
         * int alpha = gr.getAlpha(); // Paint a fuzzy shadow // If we get
         * problems with paint performance then we cand use the // second 'less
         * fuzzy' but less to do method or the 3rd, or not at all. if (true) {
         * gr.setAlpha(20); gr.fillRoundRectangle(shadowRC, CORNER_ARC,
         * CORNER_ARC);
         * 
         * shadowRC.translate(-1, -1); gr.setAlpha(30);
         * gr.fillRoundRectangle(shadowRC, CORNER_ARC, CORNER_ARC);
         * 
         * shadowRC.translate(-1, -1); gr.setAlpha(40);
         * gr.fillRoundRectangle(shadowRC, CORNER_ARC, CORNER_ARC); } else if
         * (true) { gr.setAlpha(20); gr.fillRoundRectangle(shadowRC, CORNER_ARC,
         * CORNER_ARC);
         * 
         * shadowRC.translate(-(XPDFigureUtilities.SHADOW_OFFSETCX/2),
         * -(XPDFigureUtilities.SHADOW_OFFSETCY/2)); gr.setAlpha(50);
         * gr.fillRoundRectangle(shadowRC, CORNER_ARC, CORNER_ARC); } else {
         * gr.setAlpha(50); gr.fillRoundRectangle(shadowRC, CORNER_ARC,
         * CORNER_ARC); }
         * 
         * gr.setBackgroundColor(color); gr.setAlpha(alpha); }
         */

        if (highlightOn) {
            gr.setLineWidth(borderWidth + 1);

            /*
             * The nominal border width may not be 1 anymore so take into
             * account.
             */
            int borderDelta = (borderWidth - 1) + 1;

            ca.x += borderDelta;
            ca.y += borderDelta;
            ca.width -= borderDelta * 2;
            ca.height -= borderDelta * 2;

        } else {
            gr.setLineWidth(borderWidth);

            /*
             * The nominal border width may not be 1 anymore so take into
             * account.
             */
            int borderDelta = borderWidth - 1;
            ca.x += borderDelta;
            ca.y += borderDelta;
            ca.width -= borderDelta;
            ca.height -= borderDelta;

        }

        /* Lighten the gradient pattern if we have an alpha seting. */
        float alphaPercent = getAlpha() / 255.0f;
        if (alphaPercent > 1.0f) {
            alphaPercent = 1.0f;
        }

        boolean bgColorCreated = false;
        Color actualBgColor = null;
        if (false && alphaPercent < 1.0f) {
            /* Make sure we paint over a white background when alpha-blending! */
            gr.setAlpha(255);
            gr.setBackgroundColor(ColorConstants.white);
            gr.fillRoundRectangle(ca, CORNER_ARC, CORNER_ARC);
            gr.setAlpha(getAlpha());

            actualBgColor =
                    XpdColorRegistry.getDefault()
                            .mixColors(getBackgroundColor(),
                                    ColorConstants.white,
                                    1.0f - alphaPercent);
            bgColorCreated = true;
        } else {
            actualBgColor = getBackgroundColor();
        }

        gr.setBackgroundColor(actualBgColor);
        gr.setForegroundColor(getForegroundColor());

        Color endGradientColor =
                ProcessWidgetColors
                        .getGradientEndColor(gr.getBackgroundColor());

        // If this is a transactional task (subprocess only) then paint
        // outer rectangle with white background before overwriting with
        // inner background.
        int corner_arc = CORNER_ARC;
        if (isTransactional) {
            gr.setBackgroundColor(endGradientColor);
            gr.fillRoundRectangle(ca, CORNER_ARC, CORNER_ARC);

            Rectangle outer = ca.getCopy();
            outer.width -= 1;
            outer.height -= 1;
            gr.setForegroundColor(gr.getForegroundColor());
            gr.drawRoundRectangle(outer, CORNER_ARC, CORNER_ARC);

            // And adjust the rectangle ready for grdient fill inner section.
            ca.shrink(TRANSACTIONAL_BORDER_SIZE, TRANSACTIONAL_BORDER_SIZE);

            corner_arc -= TRANSACTIONAL_BORDER_SIZE;
        }

        gr.setBackgroundColor(actualBgColor);
        gr.setForegroundColor(getForegroundColor());

        Pattern p = null;
        if (isContentsVisible()) {
            // Need slightly darker end gradient than normal
            // else content can get confused with outer fill
            // in bottom right corner.
            /*
             * Color endGrad = FigureUtilities .lighter(getBackgroundColor());
             */
            Color endGrad =
                    XpdColorRegistry.getDefault()
                            .mixColors(gr.getBackgroundColor(),
                                    ColorConstants.white,
                                    0.25);

            gr.setForegroundColor(endGrad);

            p = XPDFigureUtilities.setBackgroundPattern(gr, ca);

            gr.fillRoundRectangle(ca, corner_arc, corner_arc);
            XPDFigureUtilities.resetBackgroundPattern(gr, p);

            /* Removed dispose - colors are cached now anyway. */
        } else {
            gr.setForegroundColor(endGradientColor);
            p = XPDFigureUtilities.setBackgroundPattern(gr, ca);

            gr.fillRoundRectangle(ca, corner_arc, corner_arc);
            XPDFigureUtilities.resetBackgroundPattern(gr, p);

        }

        gr.setBackgroundColor(actualBgColor);
        gr.setForegroundColor(getForegroundColor());

        /*
         * ABPM-911: Saket: If dotted border is needed, then set it.
         */
        if (needDottedBorder) {
            Rectangle tmpCa = ca.getCopy();
            tmpCa.x += 1;
            tmpCa.width -= 2;
            tmpCa.y += 1;
            tmpCa.height -= 2;
            gr.setLineDash(new int[] { 3, 3 });

            int origiLineWidth = gr.getLineWidth();
            gr.setLineWidth(origiLineWidth + 1);
            gr.drawRoundRectangle(tmpCa, corner_arc, corner_arc);
            gr.setLineWidth(origiLineWidth);

            gr.setLineDash(new int[0]);

        } else {
            ca.width -= 1;
            ca.height -= 1;
            gr.drawRoundRectangle(ca, corner_arc, corner_arc);
        }

        // IF embedded sub-proc then highlight content within.
        if (isContentsVisible()) {
            Rectangle rc = content.getBounds().getCopy();
            content.translateToAbsolute(rc);
            translateToRelative(rc);

            gr.setBackgroundColor(ColorConstants.white);
            gr.setAlpha(255);
            gr.fillRoundRectangle(rc, corner_arc, corner_arc);
            gr.setAlpha(getAlpha());

            // Only paint inner content separating border if the user has not
            // selected white as background colour.
            Color bgCol = getBackgroundColor();
            if (bgCol.getRed() < 255 || bgCol.getGreen() < 255
                    && bgCol.getBlue() < 255) {
                rc.width -= 1;
                rc.height -= 1;
                gr.setForegroundColor(ColorConstants.lightGray);
                gr.drawRoundRectangle(rc, corner_arc, corner_arc);
            }

        }

        /**
         * Paint the text
         * 
         * For BPMN 2.0 The text is now just painted directly into the task
         * figure (instead of having child text flow figures).
         * 
         * TaskFigureLayout now stores a set of text blocks containing a
         * TextLayout's with the text and the location to paint it.
         */
        Rectangle taskBounds = getBounds().getCopy();

        gr.setForegroundColor(getForegroundColor());

        /* Clip the text output (so doesn't overrun lower border). */
        Rectangle oldClipRect = new Rectangle();
        gr.getClip(oldClipRect);

        Rectangle textClipRect = ca.getCopy();

        textClipRect.height -= TaskFigureLayout.BPMN20_TEXT_MARGIN / 2;

        gr.setClip(textClipRect);

        for (TaskTextBlock textBlock : taskFigureLayout.getTextBlocks()) {
            /*
             * Text Block coords are relative to task top-left so adjust back
             * again.
             */
            gr.drawTextLayout(textBlock.textLayout,
                    taskBounds.x + textBlock.x,
                    taskBounds.y + textBlock.y);
        }
        gr.setClip(oldClipRect);

        gr.setAntialias(aa);
        gr.popState();

        if (bgColorCreated) {
            actualBgColor.dispose();
        }

        return;
    }

    /**
     * @return icon figure or <code>null</code> if no icon.
     */
    public IFigure getIconFigure() {
        return imageFigure;
    }

    /**
     * @return the referenceImageFigure
     */
    public ImageFigure getReferenceImageFigure() {
        return referenceImageFigure;
    }

    /**
     * Get the handle bounds (for task figures this is the part of the figure
     * that does not include shadow.
     * 
     * @return
     */
    @Override
    public Rectangle getHandleBounds() {
        Rectangle hBounds = getBounds().getCopy();

        /*
         * If we had shadow then selection bounds are different. hBounds.width
         * -= XPDFigureUtilities.SHADOW_OFFSETCX; hBounds.height -=
         * XPDFigureUtilities.SHADOW_OFFSETCY;
         */
        return hBounds;
    }

    public IFigure getContentPane() {
        return content.getLayer(LayerConstants.PRIMARY_LAYER);
    }

    public XPDGridLayer getGridLayer() {
        return (gridLayer);
    }

    @Override
    protected void fireFigureMoved() {
        super.fireFigureMoved();
        if (isContentsVisible()) {
            content.invalidateTree();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Figure#useLocalCoordinates()
     */
    @Override
    public boolean useLocalCoordinates() {
        return true;
    }

    /**
     * Passed a size that the caller WANTS the content pane to be return the
     * size of the task required to contain that. i.e. allowing for text,
     * margins etc.
     * 
     * @return new Dimension of task to contain given content.
     */
    public Dimension getOptimumSize(Dimension requiredContentSize) {
        layout();
        Rectangle b = getBounds();
        Dimension taskSize = new Dimension(b.width, b.height);

        if (isContentsVisible()) {
            // Get current size and size of content to see how much margin
            // difference there is in current size (margins etc won't change
            // according to content size so this gives us diff we want.
            Dimension currContentSize = content.getSize();

            taskSize.width -= currContentSize.width;
            taskSize.height -= currContentSize.height;

            taskSize.width += requiredContentSize.width;
            taskSize.height += requiredContentSize.height;
        }

        return (taskSize);
    }

    /**
     * Return whether the given position is within borderline tolerance
     * 
     * @param pt
     * @return
     */
    public boolean borderLineContainsPoint(Point pt) {
        Rectangle outer = getSelectionBounds();
        if (outer.contains(pt)) {

            // We'll consider just the few pixels between selection bounds and
            // figure bounds as a hit
            Rectangle inner = getHandleBounds().getCopy();

            if (!inner.contains(pt)) {
                return true;
            }
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.figures.LineAnchor.LinePointsProvider#
     * getLinePoints()
     */
    @Override
    public PointList getLineAnchorLinePoints() {
        Rectangle b = getBounds();

        PointList pts = new PointList(5);

        // NOTE!!!! Don't change order of lines WITHOUT
        // lookiong at5 getLineDirectionFromAnchorPoint
        pts.addPoint(b.getTopRight());
        pts.addPoint(b.getBottomRight());
        pts.addPoint(b.getBottomLeft());
        pts.addPoint(b.getTopLeft());
        pts.addPoint(b.getTopRight());

        translateToAbsolute(pts);
        return pts;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.figures.LineAnchor.LineAnchorLinesProvider
     * #getLineDirectionFromAnchorPoint(org.eclipse.draw2d.geometry.Point,
     * org.eclipse.draw2d.geometry.Point)
     */
    @Override
    public int getLineDirectionFromAnchorPoint(Point anchorPos, Point refPoint) {
        int retDirection = PositionConstants.NONE;

        // For task figures, the line direction to reference point depends
        // on what line the anchor actually rests on.

        // So first off find out which line the anchor point actually sits on.
        PointList pts = getLineAnchorLinePoints();
        Point closestPoint = new Point(0, 0);

        int startLineIdx =
                XPDLineUtilities.getPolylinePointClosestToPoint(pts,
                        anchorPos,
                        closestPoint);

        Point lineStartPoint = pts.getPoint(startLineIdx);

        // Because we KNOW getLinePoints() returns lines in clockwise order
        // from top-right...
        // IF index to start of line point that anchor falls on is ODD
        // - Then the line is horizontal
        if ((startLineIdx & 1) == 1) {
            // On a horixontal line the direction is ALWAYS NORTH OR SOUTH
            // depending on reference point.
            if (refPoint.y >= lineStartPoint.y) {
                retDirection = PositionConstants.SOUTH;
            } else {
                retDirection = PositionConstants.NORTH;
            }
        }
        // ELSE if the start of line index is EVEN
        // - Then the line is vertical.
        else {
            // On a vertical line the direction is ALWAYS WEST OR EAST
            // depending on reference point.
            if (refPoint.x >= lineStartPoint.x) {
                retDirection = PositionConstants.EAST;
            } else {
                retDirection = PositionConstants.WEST;
            }
        }

        return retDirection;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.figures.IHighlightableFigure#setHighlight
     * (boolean)
     */
    @Override
    public void setHighlight(boolean on) {
        if (on == highlightOn) {
            return;
        }
        highlightOn = on;

        repaint();
    }

    /**
     * Override so that selection area of task figures is slightly larger than
     * the boundary so that fixed connection anchor point on border is easier to
     * select.
     * 
     * @see org.eclipse.draw2d.Figure#containsPoint(int, int)
     */
    @Override
    public boolean containsPoint(int x, int y) {
        Rectangle b = getSelectionBounds();

        return b.contains(x, y);

    }

    /**
     * Return new rectangle bounds of task figure INCLUDING border hit tolerance
     * (i.e. space outside of bounds figure).
     * 
     * @return
     */
    public Rectangle getSelectionBounds() {
        Rectangle b = getHandleBounds().getCopy();

        b.expand(ProcessWidgetConstants.BORDER_HIT_TOLERANCE,
                ProcessWidgetConstants.BORDER_HIT_TOLERANCE);

        return (b);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Figure#remove(org.eclipse.draw2d.IFigure)
     */
    @Override
    public void remove(IFigure figure) {
        //
        // Sometimes we get asked to remove a child figure when we are
        // transitioning between embedded sub-process and non-embedded
        // sub-process task.
        // Sometimes When this happens the remove child task figure happens
        // after the embedded sub-proc task has already been set to be a normal
        // task. In this circumstance the child task can be attepmted to be
        // removed from the Task figure itself rather than the content pane.
        //
        // So make sure we remove from the correct parent.
        if (figure.getParent() != this) {
            figure.getParent().remove(figure);
        } else {
            super.remove(figure);
        }

    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        if (text != null) {
            return String
                    .format("%s[%d]: %s", this.getClass().getSimpleName(), hashCode(), text); //$NON-NLS-1$
        }
        return super.toString();
    }

    /**
     * @see org.eclipse.draw2d.Figure#setBackgroundColor(org.eclipse.swt.graphics.Color)
     * 
     * @param bg
     */
    @Override
    public void setBackgroundColor(Color bg) {
        super.setBackgroundColor(bg);

        if (imageFigure != null && !(imageFigure instanceof ImageFigure)) {
            imageFigure.setBackgroundColor(bg);
        }
    }

    /**
     * @return the content figure
     */
    public ScalableFreeformLayeredPane getContentFigure() {
        return content;
    }

    /**
     * @return the isTransactional
     */
    public boolean isTransactional() {
        return isTransactional;
    }

    /**
     * @return the markers
     */
    public List getMarkers() {
        return markers;
    }

    /**
     * @return the openCloseButton
     */
    public Figure getEmbeddedSubProcOpenCloseButton() {
        return openCloseButton;
    }

    /**
     * @return The bounding rectangle of the task text (in same coordinate
     *         system as task within it's parent)
     */
    public Rectangle getDirectEditTextBounds() {
        Rectangle editBounds =
                taskFigureLayout.getTextDirectEditBounds().getCopy();
        Rectangle taskBounds = getBounds();

        editBounds.x += taskBounds.x;
        editBounds.y += taskBounds.y;
        return editBounds;
    }

    /**
     * Set the border width (default is 1);
     * <p>
     * Allows for things like call sub-process activity to be given a thicker
     * border.
     * 
     * @param borderWidth
     */
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    /**
     * @return the borderWidth
     */
    public int getBorderWidth() {
        return borderWidth;
    }
}
