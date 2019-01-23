/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.figures;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;

/**
 * Polyline connection with rounded bends
 * 
 * @author wzurek
 */
public class SequenceFlowFigure extends RoundedPolylineConnection implements
        IScaleableConnectionFigure {

    // Source and target decorations need to be scaled to the
    // source/targetobject's level.
    private double sourceDecorationScale = 1.0;

    private double targetDecorationScale = 1.0;

    private SequenceFlowType currFlowType;

    // Set-up[ the default painting stuff ...
    public SequenceFlowFigure() {
        super();

        setBackgroundColor(ColorConstants.white);
        setForegroundColor(ColorConstants.darkBlue);

        setFlowType(SequenceFlowType.UNCONTROLLED_LITERAL);
    }

    // SID - point templates moved from SequenceFlowEditPart.
    public static final int[] ARROW_TEMPLATE =
            new int[] { 0, 0, -1, 1, -1, -1 };

    public static final int[] CONDITIONAL_TEMPLATE =
            new int[] { -0, 0, -1, 1, -2, 0, -1, -1 };

    public static final int[] DEFAULT_TEMPLATE = new int[] { 0, -1, -2, 1 };

    /**
     * Setting the flow type defines the decorations (start / end cap) for line
     * NOTE: setColor() first to ensure correct decoration color.
     * 
     * @param flowType
     */
    public void setFlowType(SequenceFlowType flowType) {

        currFlowType = flowType;

        if (flowType != null) {
            // target decoration is always filled arrow
            PolygonDecoration arrow = new PolygonDecoration();

            PointList pl = new PointList(ARROW_TEMPLATE);
            arrow.setTemplate(pl);

            // btw the 7 and 3 constants shamelessly ripped out of
            // PolygoDecoration class.
            arrow.setScale(ProcessWidgetConstants.POLYGONDECORATION_XSCALE
                    * targetDecorationScale,
                    ProcessWidgetConstants.POLYGONDECORATION_YSCALE
                            * targetDecorationScale);
            setTargetDecoration(arrow);

            switch (flowType.getValue()) {
            case SequenceFlowType.CONDITIONAL: {
                PolygonDecoration pd;
                pd = new PolygonDecoration();
                pd.setFill(true);

                pd.setBackgroundColor(getBackgroundColor());
                pd.setForegroundColor(getForegroundColor());

                pd.setTemplate(new PointList(CONDITIONAL_TEMPLATE));
                pd.setScale(ProcessWidgetConstants.POLYGONDECORATION_XSCALE
                        * sourceDecorationScale,
                        ProcessWidgetConstants.POLYGONDECORATION_YSCALE
                                * sourceDecorationScale);
                setSourceDecoration(pd);
                break;
            }
            case SequenceFlowType.DEFAULT: {
                PolylineDecoration pd = new PolylineDecoration();
                pd.setBackgroundColor(getBackgroundColor());
                pd.setForegroundColor(getForegroundColor());

                pd.setTemplate(new PointList(DEFAULT_TEMPLATE));
                pd.setScale(ProcessWidgetConstants.POLYGONDECORATION_XSCALE
                        * sourceDecorationScale,
                        ProcessWidgetConstants.POLYGONDECORATION_YSCALE
                                * sourceDecorationScale);
                setSourceDecoration(pd);
                break;
            }
            default: {
                // no decoration
                setSourceDecoration(null);
            }
            }
        } else {
            // If not passed a flow type then there's no source or target
            // decoration
            setSourceDecoration(null);
            setTargetDecoration(null);
        }

        return;
    }

    /**
     * Set flag that dictates whether connection firgure paints handles for it's
     * constraints.
     * 
     * @param on
     */
    @Override
    public void setShowConstraints(boolean showConstraints) {
        this.showConstraints = showConstraints;
    }

    @Override
    public Rectangle getBounds() {
        Rectangle b = super.getBounds();

        List ch = getChildren();
        for (Iterator iter = ch.iterator(); iter.hasNext();) {
            IFigure fig = (IFigure) iter.next();
            b = b.getUnion(fig.getBounds());
        }
        return b;
    }

    /**
     * Source and target decorations need to be scaled to the
     * source/targetobject's level.
     * 
     * @param newScale
     *            the sourceDecorationScale to set
     */
    public void setSourceDecorationScale(double newScale) {
        IFigure fig = getSourceDecoration();
        if (fig instanceof PolygonDecoration) {
            // Calc original scale by down-scaling with current scale.
            ((PolygonDecoration) fig)
                    .setScale(ProcessWidgetConstants.POLYGONDECORATION_XSCALE
                            * newScale,
                            ProcessWidgetConstants.POLYGONDECORATION_YSCALE
                                    * newScale);
        }

        this.sourceDecorationScale = newScale;
    }

    /**
     * Source and target decorations need to be scaled to the
     * source/targetobject's level.
     * 
     * @param newScale
     *            the targetDecorationScale to set
     */
    public void setTargetDecorationScale(double newScale) {
        IFigure fig = getTargetDecoration();
        if (fig instanceof PolygonDecoration) {
            // Calc original scale by down-scaling with current scale.
            ((PolygonDecoration) fig)
                    .setScale(ProcessWidgetConstants.POLYGONDECORATION_XSCALE
                            * newScale,
                            ProcessWidgetConstants.POLYGONDECORATION_YSCALE
                                    * newScale);
        }

        this.targetDecorationScale = newScale;
    }

    public SequenceFlowFigure getCopy() {
        SequenceFlowFigure copy = new SequenceFlowFigure();

        copy.setBackgroundColor(getBackgroundColor());
        copy.setForegroundColor(getForegroundColor());
        copy.setFlowType(currFlowType);
        copy.setSourceDecorationScale(sourceDecorationScale);
        copy.setTargetDecorationScale(targetDecorationScale);

        copy.setSourceAnchor(getSourceAnchor());
        copy.setTargetAnchor(getTargetAnchor());

        copy.setConnectionRouter(getConnectionRouter());

        return copy;
    }

}
