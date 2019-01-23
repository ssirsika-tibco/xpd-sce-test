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

package com.tibco.xpd.processwidget.parts;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayeredPane;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.LayeredPane;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.ToolTipHelper;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.GridLayer;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.ProcessWidgetEvent;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.ProcessWidgetListener;
import com.tibco.xpd.processwidget.figures.LaneFigure;
import com.tibco.xpd.processwidget.figures.PaginationLayer;
import com.tibco.xpd.processwidget.figures.PoolFigure;
import com.tibco.xpd.processwidget.figures.ProcessConnectionLayer;
import com.tibco.xpd.processwidget.figures.RootXPDScalableFreeformLayeredPane;
import com.tibco.xpd.processwidget.figures.XPDGridLayer;

/**
 * @author wzurek
 */
public class WidgetRootEditPart extends ScalableFreeformRootEditPart implements
        ProcessWidgetListener {

    @Override
    protected GridLayer createGridLayer() {
        return new XPDGridLayer();
    }

    @Override
    protected void createLayers(LayeredPane layeredPane) {
        super.createLayers(layeredPane);

        // Not used
        // ShortcutsLayer shortcutsLayer = new ShortcutsLayer();
        // shortcutsLayer.setVisible(true);
        // layeredPane.add(shortcutsLayer,
        // ProcessWidgetConstants.SHORTCUTS_LAYER);

        // Print Pagination layer moved to scaled layers.

        ConnectionLayer cn = (ConnectionLayer) getLayer(CONNECTION_LAYER);
        cn.setAntialias(SWT.ON);
    }

    @Override
    protected LayeredPane createPrintableLayers() {
        // LayeredPane pane = super.createPrintableLayers();
        FreeformLayeredPane pane = new FreeformLayeredPane();
        pane.add(new FreeformLayer(), PRIMARY_LAYER);
        pane.add(new ProcessConnectionLayer(), CONNECTION_LAYER);
        //

        // layer for display groups
        FreeformLayer groupLayer = new FreeformLayer();
        groupLayer.setLayoutManager(new XYLayout());
        groupLayer.setVisible(true);
        pane.add(groupLayer, ProcessWidgetConstants.GROUP_LAYER);

        return pane;
    }

    @Override
    protected IFigure createFigure() {
        IFigure fig = super.createFigure();
        fig.setBackgroundColor(ColorConstants.white);
        fig.setOpaque(true);
        return fig;
    }

    @Override
    protected ScalableFreeformLayeredPane createScaledLayers() {
        // We have to use our own XPDScalableFreeformLayeredPane class so that
        // we can override
        // the std scaling behaviour of Point/Rectangle/Dimension etc.
        // So we have to create the scalable layers for ourselves.
        // if it looks a bit weird that we're getting the super's then copying
        // them
        // to our own XPDScalable 'hard coded', this is because super adds
        // FeedbackLayer
        // but we can't create a Feedback layer because that is an inner class
        // of ScalableFreeFormLayerdPane.
        ScalableFreeformLayeredPane tmpScaled = super.createScaledLayers();
        ScalableFreeformLayeredPane scaled =
                new RootXPDScalableFreeformLayeredPane();

        Layer layer;

        layer = tmpScaled.getLayer(GRID_LAYER);
        if (layer != null) {
            scaled.add(layer, GRID_LAYER);
        }
        layer = tmpScaled.getLayer(PRINTABLE_LAYERS);
        if (layer != null) {
            scaled.add(layer, PRINTABLE_LAYERS);
        }

        layer = tmpScaled.getLayer(SCALED_FEEDBACK_LAYER);
        if (layer != null) {
            scaled.add(layer, SCALED_FEEDBACK_LAYER);
        }

        tmpScaled = null;

        FreeformLayer extLayer = new FreeformLayer() {

            @Override
            protected void paintChildren(Graphics graphics) {
                /**
                 * allows us to check when extra stuff is added to annotaitons
                 * layer that may interfere with normal mouse stuff (like the
                 * Debug layer seems to do).
                 */
                if (false) {
                    for (Iterator iterator = this.getChildren().iterator(); iterator
                            .hasNext();) {
                        IFigure child = (IFigure) iterator.next();
                        if (true || child.isEnabled()) {
                            graphics.pushState();
                            Rectangle b = child.getBounds();
                            graphics.setBackgroundColor(ColorConstants.red);
                            graphics.fillRectangle(b);

                            graphics.popState();
                        }
                    }
                } else {
                    super.paintChildren(graphics);
                }
            }
        };

        extLayer.setVisible(true);
        scaled.add(extLayer, ProcessWidgetConstants.EXTENSIONS_LAYER);

        /* XPD-1431: Added edit part highlighter layer. */
        FreeformLayer highlighterLayer = new FreeformLayer();

        highlighterLayer.setVisible(false);
        highlighterLayer.setEnabled(false);
        scaled.add(highlighterLayer,
                ProcessWidgetConstants.EDITPART_HIGHLIGHTER_LAYER);

        FreeformLayer progressionLayer = new FreeformLayer();
        progressionLayer.setVisible(false);
        progressionLayer.setEnabled(false);
        scaled.add(progressionLayer,
                ProcessWidgetConstants.PROGRESSION_FIGURES_LAYER);

        FreeformLayer progressionControlLayer = new FreeformLayer();
        progressionControlLayer.setVisible(false);
        progressionControlLayer.setEnabled(true);
        scaled.add(progressionControlLayer,
                ProcessWidgetConstants.PROGRESSION_CONTROLS_LAYER);

        PaginationLayer pagLayer = new PaginationLayer();
        pagLayer.setVisible(true);
        scaled.add(pagLayer, ProcessWidgetConstants.PAGINATION_LAYER);

        return scaled;
    }

    /**
     * Get the extent of the process diagram i.e. height will be pool height
     * (which in turn is height of all lanes within it). width will be width of
     * pool which is width of lanes whose width's are set from the furthest X
     * offset object within them.
     */
    public Dimension getDiagramExtent() {
        ProcessEditPart processEP = (ProcessEditPart) (getChildren().get(0));
        if (processEP != null) {
            return (processEP.getDiagramExtent());
        }
        return (new Dimension(0, 0));
    }

    @Override
    public void notifyChanged(ProcessWidgetEvent processEvent) {
        // TODO Auto-generated method stub

    }

    /**
     * Error tool tip helper. stuff.
     * 
     * When trying to execute a mouse operation the code can now set an error
     * tool tip that is shown after 1 second unless the mouse is moved away from
     * the invalid operation point.
     * 
     * In order to do so the So For example, you can choose to call
     * setErrorTipHelperText() giving the target host figure that is invalid for
     * the given operation and text to display.
     * 
     * The error tool tip helper tracks the mouse and will cancel the tooltip
     * (before or after it is displayed) when the mouse is moved out of the
     * bounds of the host figure in question.
     * 
     * 
     */
    ToolTipHelper errorTipHelper = null;

    IFigure errorTipHost = null;

    String errorTipText = ""; //$NON-NLS-1$

    Label errorTipLabel = null;

    Timer errorTipTimer = null;

    Point errorTipPos = null;

    Point errorTipFirstPos = null;

    Control control = null;

    public void setErrorTipHelperText(IFigure hostFigure, String text) {
        control = getViewer().getControl();

        if (errorTipHelper == null) {
            errorTipLabel = new Label(text);
            errorTipLabel.setBackgroundColor(ProcessWidgetPlugin.getDefault()
                    .getColor(new RGB(206, 231, 255)));
            errorTipLabel.setOpaque(true);
            MarginBorder mb = new MarginBorder(0, 5, 3, 5);
            errorTipLabel.setBorder(mb);

            errorTipHelper = new ToolTipHelper(getViewer().getControl());

            control.addMouseMoveListener(new MouseMoveListener() {

                @Override
                public void mouseMove(MouseEvent e) {
                    errorTipPos = new Point(e.x, e.y);

                    if (errorTipHost != null && errorTipHelper != null
                            && errorTipFirstPos != null) {

                        // If we have moved by 40- pixels from original position
                        // then clear the tool tip.
                        int maxChg = 20;
                        if (e.x < (errorTipFirstPos.x - maxChg)
                                || e.x > (errorTipFirstPos.x + maxChg)
                                || e.y < (errorTipFirstPos.y - maxChg)
                                || e.y > (errorTipFirstPos.y + maxChg)) {
                            clearErrorTipHelper();
                        } else {
                            // Or if we have moved out of original host figure.
                            Point loc = new Point(e.x, e.y);
                            errorTipHost.translateToRelative(loc);

                            if (!errorTipHost.containsPoint(loc)) {
                                // For pool / lane, count off-of right hand side
                                // as
                                // contains for this purpose.
                                if (errorTipHost instanceof PoolFigure
                                        || errorTipHost instanceof LaneFigure) {
                                    Rectangle b = errorTipHost.getBounds();

                                    if (loc.y < b.y || loc.y > (b.y + b.height)
                                            || loc.x < b.x) {
                                        clearErrorTipHelper();
                                    }
                                } else {
                                    clearErrorTipHelper();
                                }
                            }
                        }
                    }
                }
            });

            control.addMouseTrackListener(new MouseTrackAdapter() {
                @Override
                public void mouseExit(org.eclipse.swt.events.MouseEvent e) {
                    clearErrorTipHelper();
                }
            });
        }

        if (hostFigure != errorTipHost && !errorTipText.equals(text)) {
            errorTipFirstPos = null;
            if (text != null) {
                errorTipHost = hostFigure;
                errorTipText = text;
                errorTipLabel.setText(errorTipText);
                errorTipTimer = new Timer(true);
                errorTipTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Display.getDefault().syncExec(new Runnable() {
                            @Override
                            public void run() {
                                org.eclipse.swt.graphics.Point absolute;
                                if (errorTipPos != null
                                        && errorTipHelper != null
                                        && errorTipHost != null
                                        && errorTipLabel != null) {
                                    absolute =
                                            control.toDisplay(new org.eclipse.swt.graphics.Point(
                                                    errorTipPos.x,
                                                    errorTipPos.y));
                                    errorTipHelper
                                            .displayToolTipNear(errorTipHost,
                                                    errorTipLabel,
                                                    absolute.x,
                                                    absolute.y);
                                    errorTipFirstPos = errorTipPos.getCopy();
                                }
                                errorTipTimer.cancel();
                            }
                        });
                    }
                },
                        500);

            } else {
            }
        }
    }

    public void clearErrorTipHelper() {
        if (errorTipTimer != null) {
            errorTipTimer.cancel();
        }

        if (errorTipHelper != null) {
            errorTipHelper.updateToolTip(null, null, 0, 0);
        }
        errorTipHost = null;
        errorTipText = ""; //$NON-NLS-1$
    }

    public ProcessEditPart getProcessEditPart() {
        List children = getChildren();

        for (Iterator iter = children.iterator(); iter.hasNext();) {
            EditPart processEP = (EditPart) iter.next();

            if (processEP instanceof ProcessEditPart) {
                return (ProcessEditPart) processEP;
            }
        }

        return null;
    }

}
