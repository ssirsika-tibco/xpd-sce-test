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

package com.tibco.xpd.processwidget.viewer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.tools.DragEditPartsTracker;
import org.eclipse.gef.ui.parts.DomainEventDispatcher;
import org.eclipse.gef.ui.parts.GraphicalViewerImpl;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.processwidget.neatstuff.AbstractFigureRevealAnimator;
import com.tibco.xpd.processwidget.parts.BaseConnectionEditPart;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.ProcessEditPart;
import com.tibco.xpd.quickfixtooltip.api.QuickFixToolTipEnabledDomainEventDispatcher;

/**
 * Implementation based on {@see ScrollingGraphicalViewer}. The only difference
 * is posibility to specify custom reveal strategy for edit part. The edit part
 * will be asked for {@see RevealRectangle} so it can specify relevant region to
 * reveal. for example it may be important for Swimlanes and Pools<br>
 * <p>
 * <i>Iryginal Javadoc:</i><br>
 * A Graphical Viewer implementation which uses a
 * {@link org.eclipse.draw2d.FigureCanvas} for native scrolling. Because the
 * scrolling is handled natively, the root editpart should not contain a
 * {@link org.eclipse.draw2d.ScrollPane} figure. Do not use root editparts which
 * provide scrollpane figures, such as <code>GraphicalRootEditPart</code>.
 * <P>
 * The RootEditPart for a ScrollingGraphicalViewer may contain a Viewport. If it
 * does, that viewport will be set as the FigureCanvas' viewport. FigureCanvas
 * has certain requirements on the viewport figure, see
 * {@link FigureCanvas#setViewport(Viewport)}.
 * 
 * @author wzurek (Bpmn extensions)
 * @author hudsonr
 */
public class BpmnScrollingGraphicalViewer extends GraphicalViewerImpl {

    private IFigure rootFigure;

    private AbstractFigureRevealAnimator animSelectionFigure = null;

    private boolean fireSelChangeEnabled = true;

    // The selection that is ABOUT to be set on th child edit parts. Normal GEF
    // selects one thing at a time for marquee select so individual selection
    // edit policies can't really tell whether the whole selection WILL be
    // multi.
    // Now they can by calling getPreSelection().
    private List<EditPart> preSelection = new ArrayList<EditPart>();

    private QuickFixToolTipEnabledDomainEventDispatcher stickyToolTipEnabledDomainEventDispatcher;

    /**
     * Rectangle that can be provided (through IAdaptable pattern) by EditPart
     * if the viewer should reveal different region then EditPart's figure
     * bounds
     * 
     * @author wzurek
     */
    public static class RevealRectangle extends Rectangle {
        private static final long serialVersionUID = 0;
    }

    /**
     * Constructs a ScrollingGraphicalViewer;
     */
    public BpmnScrollingGraphicalViewer() {
    }

    @Override
    public void setEditDomain(EditDomain domain) {
        // Override to set our own domain event dispatcher, doing so seems to be
        // the ONLY way of providing a different ToolTipHelper which seems to be
        // the only way of providing a way of not destroying the tooltip if the
        // mouse is moved away from original host figure and over the tooltip.
        super.setEditDomain(domain);

        stickyToolTipEnabledDomainEventDispatcher =
                new QuickFixToolTipEnabledDomainEventDispatcher(domain, this);
        getLightweightSystem()
                .setEventDispatcher(stickyToolTipEnabledDomainEventDispatcher);
    }

    @Override
    protected DomainEventDispatcher getEventDispatcher() {
        // Override to set our own domain event dispatcher, doing so seems to be
        // the ONLY way of providing a different ToolTipHelper which seems to be
        // the only way of providing a way of not destroying the tooltip if the
        // mouse is moved awy from original host figure and over the tooltip.
        //
        // We overrode the domain event dispatcher so we should get underlying
        // class to use ours.
        return stickyToolTipEnabledDomainEventDispatcher;
    }

    /**
     * @see org.eclipse.gef.EditPartViewer#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public final Control createControl(Composite parent) {
        FigureCanvas canvas = new FigureCanvas(parent, getLightweightSystem());
        super.setControl(canvas);
        installRootFigure();
        return canvas;
    }

    /**
     * Convenience method which types the control as a <code>FigureCanvas</code>
     * . This method returns <code>null</code> whenever the control is null.
     * 
     * @return <code>null</code> or the Control as a FigureCanvas
     */
    protected FigureCanvas getFigureCanvas() {
        return (FigureCanvas) getControl();
    }

    /**
     * If the figure is a viewport, set the canvas' viewport, otherwise, set its
     * contents.
     * 
     * @param rootFigure
     */
    private void installRootFigure() {
        if (getFigureCanvas() == null)
            return;
        if (rootFigure instanceof Viewport)
            getFigureCanvas().setViewport((Viewport) rootFigure);
        else
            getFigureCanvas().setContents(rootFigure);
    }

    /**
     * Extends the superclass implementation to scroll the native Canvas control
     * after the super's implementation has completed.
     * 
     * @see org.eclipse.gef.EditPartViewer#reveal(org.eclipse.gef.EditPart)
     */
    @Override
    public void reveal(EditPart part) {
        reveal(part, false);
    };

    public void reveal(EditPart part, boolean doHighlight) {
        reveal(part, doHighlight, true);
    }

    /**
     * 
     * @param part
     * @param doHighlight
     * @param smoothScroll
     * @return Reveal are of the element(s) or null if cannot ascertain this
     *         (multiple element select). This is in display co-ords.
     */
    public org.eclipse.swt.graphics.Rectangle reveal(EditPart part,
            boolean doHighlight, boolean smoothScroll) {
        org.eclipse.swt.graphics.Rectangle revealRect = null;

        super.reveal(part);

        Viewport port = getFigureCanvas().getViewport();

        Rectangle exposeRegion =
                exposeRegion =
                        (Rectangle) part.getAdapter(RevealRectangle.class);
        if (exposeRegion == null) {
            IFigure target = ((GraphicalEditPart) part).getFigure();
            exposeRegion = target.getBounds().getCopy();

            // Expand expose region before translating (else it's 5 pixels
            // unscaled which doesn't work).
            // NOTE: Not very nice, but I found that if these dimensions are
            // different than
            // ProcessEditPart bounding origin(5,5) then switching selecting
            // between pool and lane
            // causes small jumps in display.
            exposeRegion.expand(5, 5);

            target = target.getParent();
            while (target != null && target != port) {
                target.translateToParent(exposeRegion);
                target = target.getParent();
            }
        }

        Dimension viewportSize = port.getClientArea().getSize();

        Point topLeft = exposeRegion.getTopLeft();
        Point bottomRight =
                exposeRegion.getBottomRight().translate(viewportSize
                        .getNegated());
        Point finalLocation = new Point();
        if (viewportSize.width < exposeRegion.width)
            finalLocation.x =
                    Math.min(bottomRight.x, Math.max(topLeft.x, port
                            .getViewLocation().x));
        else
            finalLocation.x =
                    Math.min(topLeft.x, Math.max(bottomRight.x, port
                            .getViewLocation().x));

        if (viewportSize.height < exposeRegion.height)
            finalLocation.y =
                    Math.min(bottomRight.y, Math.max(topLeft.y, port
                            .getViewLocation().y));
        else
            finalLocation.y =
                    Math.min(topLeft.y, Math.max(bottomRight.y, port
                            .getViewLocation().y));

        // SID:
        // AWOOOOOOOOOOOGA!!!! Scroll to X=1 or Y=1 causes problems (bug in
        // GEF's scrollTo I think).
        // We found that when this was done for selecting a Lane, then we
        // attempted a scroll to y=1 when lane bigger than editor window - after
        // this NOTHING inside lane would paint it's lines or borders.
        if (finalLocation.x == 1) {
            finalLocation.x = 0;
        }
        if (finalLocation.y == 1) {
            finalLocation.y = 0;
        }

        if (smoothScroll) {
            getFigureCanvas().scrollSmoothTo(finalLocation.x, finalLocation.y);
        } else {
            getFigureCanvas().scrollTo(finalLocation.x, finalLocation.y);
        }

        if (doHighlight) {
            highlightRevealFigure(part);
        }

        if (part instanceof GraphicalEditPart) {
            IFigure fig = ((GraphicalEditPart) part).getFigure();
            Rectangle bnds = fig.getBounds().getCopy();
            fig.translateToAbsolute(bnds);

            org.eclipse.swt.graphics.Point p =
                    getFigureCanvas().toDisplay(bnds.x, bnds.y);
            revealRect =
                    new org.eclipse.swt.graphics.Rectangle(p.x, p.y,
                            bnds.width, bnds.height);
        }

        return revealRect;
    }

    private void highlightRevealFigure(EditPart part) {
        if (part instanceof AbstractGraphicalEditPart
                && !(part instanceof ProcessEditPart)) {

            final IFigure fig = ((AbstractGraphicalEditPart) part).getFigure();

            final IFigure layer =
                    getLayerManager().getLayer(LayerConstants.FEEDBACK_LAYER);

            /*
             * If we have a previous highlight figure then make sure it's
             * animation is ended if still running.
             */
            if (animSelectionFigure != null) {
                animSelectionFigure.endAnimation();
                animSelectionFigure = null;
            }

            /*
             * Create and start the collapsing target animation.
             */
            animSelectionFigure =
                    new CollapsingTargetRevealAnimator(getFigureCanvas()
                            .getViewport(), layer, fig);

            animSelectionFigure.startAnimation();
        }

        return;
    }

    /**
     * /**
     * 
     * @see GraphicalViewerImpl#setRootFigure(IFigure)
     */
    @Override
    protected void setRootFigure(IFigure figure) {
        rootFigure = figure;
        installRootFigure();
    }

    /**
     * Enable/Disable changes in edit part selection being notified to listeners
     * (to both edit part selection and viewer selection).
     * 
     * @param fireSelChangeEnabled
     */
    private void setFireSelectionChangeEnabled(boolean fireSelChanges) {
        this.fireSelChangeEnabled = fireSelChanges;
    }

    /**
     * @return Whether changes in edit part selection being notified to
     *         listeners (to both edit part selection and viewer seleciton) is
     *         enabled/disabled
     */
    public boolean isFireSelChangeEnabled() {
        return fireSelChangeEnabled;
    }

    /**
     * Fire selection change events at listeners of the viewer.
     */
    @Override
    public void fireSelectionChanged() {
        if (animSelectionFigure != null) {
            animSelectionFigure.endAnimation();
        }

        if (fireSelChangeEnabled) {
            Object listeners[] = selectionListeners.toArray();
            SelectionChangedEvent event =
                    new SelectionChangedEvent(this, getSelection());
            for (int i = 0; i < listeners.length; i++) {
                ((ISelectionChangedListener) listeners[i])
                        .selectionChanged(event);
            }
        }
    }

    /**
     * Fires selection changed events for internal edit part notifications
     * <p>
     * This method is in place primarily for use by SelectOnMouseUpDragTracker
     * mouse up tracker so that nothing exterior happens until after the user
     * finishes dragging.
     */
    public void fireInternalSelectionChanged() {
        ISelection sel = getSelection();
        if (sel instanceof IStructuredSelection) {
            IStructuredSelection ssel = (IStructuredSelection) sel;

            // Fire internal edit part events first.
            for (Iterator iterator = ssel.iterator(); iterator.hasNext();) {
                Object o = iterator.next();

                if (o instanceof BaseGraphicalEditPart) {
                    ((BaseGraphicalEditPart) o).forceFireSelectionChanged();
                } else if (o instanceof BaseConnectionEditPart) {
                    ((BaseConnectionEditPart) o).forceFireSelectionChanged();
                }

            }
        }

    }

    @Override
    public void setSelection(ISelection newSelection) {
        //
        // Keep preSelection list up to date.
        preSelection.clear();

        if (newSelection instanceof StructuredSelection) {
            StructuredSelection sel = ((StructuredSelection) newSelection);

            for (Iterator iterator = sel.iterator(); iterator.hasNext();) {
                Object o = iterator.next();
                if (o instanceof EditPart) {
                    preSelection.add((EditPart) o);
                }
            }
        }

        super.setSelection(newSelection);
    }

    @Override
    public void appendSelection(EditPart editpart) {
        //
        // Keep preSelection list up to date.
        preSelection.remove(editpart);
        preSelection.add(editpart);

        super.appendSelection(editpart);
    }

    @Override
    public void deselect(EditPart editpart) {
        //
        // Keep preSelection list up to date.
        preSelection.remove(editpart);

        super.deselect(editpart);
    }

    @Override
    public void deselectAll() {
        //
        // Keep preSelection list up to date.
        preSelection.clear();

        super.deselectAll();
    }

    @Override
    public void select(EditPart editpart) {
        if ((getSelectedEditParts().size() == 1)
                && (getSelectedEditParts().get(0) == editpart)) {
            return;
        }

        // the super's select doesn't call deselect for currently selected
        // element (it hacks cleaning of selection list directly)
        // So we need to clear any items out of the preSelection list.
        preSelection.clear();
        super.select(editpart);
    }

    /**
     * The selection that is ABOUT to be set on th child edit parts. Normal GEF
     * selects one thing at a time for marquee select so individual selection
     * edit policies can't really tell whether the whole selection WILL be
     * multi.
     * <p>
     * Now they can by calling getPreSelection().
     * 
     * @return The REAL selection (that will be in place once all objects
     *         selected individually)
     */
    public List<EditPart> getPreSelection() {
        return preSelection;
    }

    /**
     * There are performance issues with creation/update of property sheets -
     * this means that when user performs click-drag of certain objects (like
     * end event for instance) the 'drag doesn't start until the property sheets
     * are loaded because they lock-out the UI thread. We could keep trying to
     * make property sheet loads/layout faster but it will always be an
     * underlying problem.
     * 
     * <p>
     * Therefore we will change the process editor so that it does not fire
     * selection changed events UNTIL a Mouse Up occurs (ratner than mouse
     * down).
     * 
     * <p>
     * GEF does not make this particularly easy and we have to fight quite hard
     * to get it top do so...
     * <li>{@link BpmnScrollingGraphicalViewer} allows us to switch to a mode
     * whereby set selection on IT without firing a sel change events to the
     * rest of the world.</li>
     * <li>So we use this to prevent sel change events causing prop sheet load
     * on mouse down.</li>
     * <li>Then on mouse up we fire the actual sel change.
     * <li>To do all this we use our own subclass of drag tracker.
     * 
     * <p>
     * To use 'select on mouse up then' override the EditPart's getDragTracker()
     * and return a new instance of this drag tracker.
     * 
     * @author aallway
     * @since 3.2
     */
    public static class SelectOnMouseUpDragTracker extends DragEditPartsTracker {

        private EditPartViewer viewerOnMouseDown = null;

        private boolean selectOnMouseUpEnabled = true;

        public SelectOnMouseUpDragTracker(EditPart sourceEditPart) {
            super(sourceEditPart);
        }

        @Override
        protected void performSelection() {
            super.performSelection();
        }

        @Override
        protected boolean handleButtonDown(int button) {
            // When performSelection() is performed, tell it to tell viwer not
            // to fire selection changes to the rest of the world.
            viewerOnMouseDown = getSourceEditPart().getViewer();

            boolean doSelectOnMouseUp = doSelectOnMouseUp(button);

            if (doSelectOnMouseUp) {
                BpmnScrollingGraphicalViewer bpmnViewer =
                        (BpmnScrollingGraphicalViewer) viewerOnMouseDown;
                bpmnViewer.setFireSelectionChangeEnabled(false);
            }

            boolean ret = false;

            try {
                ret = super.handleButtonDown(button);

            } finally {
                if (doSelectOnMouseUp) {
                    BpmnScrollingGraphicalViewer bpmnViewer =
                            (BpmnScrollingGraphicalViewer) viewerOnMouseDown;
                    bpmnViewer.setFireSelectionChangeEnabled(true);
                }
            }

            return ret;
        }

        private boolean doSelectOnMouseUp(int button) {
            boolean selectOnMouseUpActive = false;

            if (selectOnMouseUpEnabled
                    && viewerOnMouseDown instanceof BpmnScrollingGraphicalViewer) {

                // On Linux things are a bit different and yuou NEVER get a
                // right-mouse up when popup context menu is displayed) so we
                // cannot do this for right mouse button on linux.
                if (System.getProperty("os.name").toUpperCase().startsWith( //$NON-NLS-1$
                "WIN")) {//$NON-NLS-1$
                    selectOnMouseUpActive = true;
                } else if (button == 1) {
                    // On linux, only do select on mouse up behaviour for left
                    // mouse button.
                    selectOnMouseUpActive = true;
                }
            }
            return selectOnMouseUpActive;
        }

        @Override
        protected boolean handleButtonUp(int button) {
            boolean ret = super.handleButtonUp(button);

            boolean doSelectOnMouseUp = doSelectOnMouseUp(button);

            if (doSelectOnMouseUp) {

                if (button == 3 || button == 1) {
                    // 
                    // On button up tell viewer to fire the selection change
                    // events.
                    //
                    notifySelectionChangedListeners();
                }
            }

            return ret;
        }

        /**
         * When cancel is pressed during drag, make sure we let other people
         * know about the change in selection (which we prevented from happening
         * during mouse down)
         */
        @Override
        protected boolean handleKeyDown(KeyEvent e) {
            if (e.character == SWT.ESC) {
                if ((getState() & STATE_DRAG_IN_PROGRESS) != 0) {
                    notifySelectionChangedListeners();
                }
            }
            return super.handleKeyDown(e);
        }

        /**
         * On button up / cancel tell viewer to fire the selection change
         * events.
         */
        private void notifySelectionChangedListeners() {

            if (selectOnMouseUpEnabled
                    && viewerOnMouseDown instanceof BpmnScrollingGraphicalViewer) {

                // Fire the edit part selection events
                ((BpmnScrollingGraphicalViewer) viewerOnMouseDown)
                        .fireInternalSelectionChanged();

                // Do events to outside world asynch to allow diagram
                // visuals chance to update first
                viewerOnMouseDown.getControl().getShell().getDisplay()
                        .asyncExec(new Runnable() {
                            public void run() {
                                ((BpmnScrollingGraphicalViewer) viewerOnMouseDown)
                                        .fireSelectionChanged();
                            }
                        });
            }
        }

    }

}
