/*
 * Copyright 2006 TIBCO Software Inc. 
 */

package com.tibco.xpd.processwidget.parts;

import java.util.List;

import org.eclipse.draw2d.AbstractLayout;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter;
import com.tibco.xpd.processwidget.adapters.GraphicalColorAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.ProcessWidgetEvent;
import com.tibco.xpd.processwidget.figures.IHighlightableFigure;
import com.tibco.xpd.processwidget.policies.AssociationOnlyNodeFlowConnectionEditPolicy;
import com.tibco.xpd.processwidget.policies.NamedElementDirectEditPolicy;
import com.tibco.xpd.processwidget.tools.AutoSizingDirectEditLocator;

/**
 * BPMN's Group
 * 
 * @author wzurek
 */
public class GroupEditPart extends BaseConnectableNodeEditPart implements
        NodeEditPart {

    /**
     * GroupFigure - Bpmn group rectangle figure.
     */
    private final class GroupFigure extends RoundedRectangle implements
            IHighlightableFigure {
        Label textFigure = null;

        private boolean highlightOn = false;

        public GroupFigure() {
            setCornerDimensions(new Dimension(10, 10));
            setLineStyle(SWT.LINE_DASHDOT);
            setOpaque(true);
            setForegroundColor(ColorConstants.darkBlue);

            if (highlightOn) {
                setLineWidth(3);
            } else {
                setLineWidth(2);
            }

            setFill(false);
            setLayoutManager(new AbstractLayout() {

                protected Dimension calculatePreferredSize(IFigure container,
                        int wHint, int hHint) {
                    return container.getSize();
                }

                public void layout(IFigure container) {
                    List children = container.getChildren();

                    // if label exist, position it inside the top part of the
                    // group
                    Rectangle parent = container.getBounds();
                    Dimension pSize = textFigure.getPreferredSize();

                    textFigure.setBounds(new Rectangle(parent.x + 5,
                            parent.y + 5, parent.width - 10, Math
                                    .min(pSize.height, parent.height - 10)));
                }
            });
            textFigure = new Label();
            add(textFigure);

        }

        public IFigure getTextFigure() {
            return textFigure;
        }

        protected void outlineShape(Graphics gr) {
            int aa = gr.getAntialias();
            gr.setAntialias(SWT.ON);
            super.outlineShape(gr);
            gr.setAntialias(aa);
        }

        public boolean containsPoint(int x, int y) {
            IFigure l = (IFigure) getChildren().get(0);
            if (l.containsPoint(x, y)) {
                return true;
            }
            Rectangle b = getBounds().getCopy().expand(2, 2);
            return b.contains(x, y) && !b.getCopy().shrink(7, 7).contains(x, y);
        }

        public void setHighlight(boolean on) {
            if (on == highlightOn) {
                return;
            }
            highlightOn = on;

            repaint();
        }
    }

    private ConnectionAnchor associationAnchor;

    public GroupEditPart(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    protected IFigure createFigure() {
        RoundedRectangle fig = new GroupFigure();

        return fig;
    }

    protected void createEditPolicies() {
        super.createEditPolicies();

        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
                new NamedElementDirectEditPolicy());
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new AssociationOnlyNodeFlowConnectionEditPolicy(
                        getEditingDomain()));
    }

    public void notifyChanged(ProcessWidgetEvent processEvent) {
        refreshSourceConnections();
        refreshTargetConnections();
        refreshVisuals();
    }

    protected List getModelSourceConnections() {
        return getGraphicalNodeAdapter().getSourceAssociations();
    }

    protected List getModelTargetConnections() {
        return getGraphicalNodeAdapter().getTargetAssociations();
    }

    private BaseGraphicalNodeAdapter getGraphicalNodeAdapter() {
        return (BaseGraphicalNodeAdapter) getModelAdapter();
    }

    protected void refreshVisuals() {
        RoundedRectangle f = (RoundedRectangle) getFigure();
        BaseGraphicalNodeAdapter aa = getGraphicalNodeAdapter();

        // the only child of the rectangle is a label
        ((Label) f.getChildren().get(0)).setText(aa.getName());

        ProcessWidgetColors colors = ProcessWidgetColors.getInstance(aa);

        Color lineColor =
                colors
                        .getGraphicalNodeColor((GraphicalColorAdapter) getModelAdapter(),
                                getLineColorIDForPartType()).getColor();

        f.setForegroundColor(lineColor);

        IFigure p = f.getParent();
        if (p != null) {
            Dimension size = aa.getSize().getCopy();
            Point loc = aa.getLocation().getCopy();

            LayoutManager layout = p.getLayoutManager();
            Rectangle r = new Rectangle(loc, size);
            if (!r.equals(layout.getConstraint(f))) {
                layout.setConstraint(f, r);
                layout.layout(p);
                p.invalidate();
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getFillColorID()
     */
    public String getFillColorIDForPartType() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getLineColorID()
     */
    public String getLineColorIDForPartType() {
        return ProcessWidgetColors.GROUP_LINE;
    }

    public ConnectionAnchor getXpdSourceConnectionAnchor(
            ConnectionEditPart connection) {
        return getAnchor();
    }

    public ConnectionAnchor getXpdTargetConnectionAnchor(
            ConnectionEditPart connection) {
        return getAnchor();
    }

    public ConnectionAnchor getXpdSourceConnectionAnchor(Request request) {
        return getAnchor();
    }

    public ConnectionAnchor getXpdTargetConnectionAnchor(Request request) {
        return getAnchor();
    }

    /**
     * @return lazy created target anchor
     */
    private ConnectionAnchor getAnchor() {
        if (associationAnchor == null) {
            associationAnchor = new ChopboxAnchor(getFigure());
        }
        return associationAnchor;
    }

    /**
     * Override default bounds based locator with locator thats take care of
     * bounds of the text instead of the whole activity
     */
    protected CellEditorLocator getDirectEditEditorLocator() {
        return new AutoSizingDirectEditLocator() {
            /*
             * (non-Javadoc)
             * 
             * @see
             * com.tibco.xpd.processwidget.parts.AutoSizingDirectEditLocator
             * #getTextBoundsLocation()
             */
            @Override
            public Rectangle getTextBoundsLocation() {
                IFigure tfig = ((GroupFigure) getFigure()).getTextFigure();
                Rectangle textBnds = tfig.getBounds().getCopy();

                tfig.translateToAbsolute(textBnds);

                return textBnds;
            }

            @Override
            public int getDesiredWidth() {
                IFigure tfig = ((GroupFigure) getFigure()).getTextFigure();
                Dimension size = tfig.getSize();
                return size.width;
            }
        };
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getDirectEditStyle
     * ()
     */
    @Override
    protected int getDirectEditStyle() {
        return SWT.WRAP | SWT.CENTER;
    }

}
