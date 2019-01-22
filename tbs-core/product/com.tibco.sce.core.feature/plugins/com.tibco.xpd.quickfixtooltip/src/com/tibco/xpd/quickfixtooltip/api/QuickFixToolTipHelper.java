/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.quickfixtooltip.api;

import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.ui.parts.DomainEventDispatcher;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMarkerResolution;

import com.tibco.xpd.quickfixtooltip.api.QuickFixToolTipProviderFigure.IQuickFixToolTipContentProvider;
import com.tibco.xpd.quickfixtooltip.control.QuickFixToolTipControl;
import com.tibco.xpd.quickfixtooltip.control.QuickFixToolTipControl.QuickFixPopupContentProvider;

/**
 * <b>Nominally you should not need to use this class directly. You should
 * simply use {@link QuickFixToolTipEnabledDomainEventDispatcher} which will
 * employ it to handle tooltips (including quick fix tooltips)</b>
 * <p>
 * When using this tooltip helper (from
 * {@link QuickFixToolTipEnabledDomainEventDispatcher} or equivalent), any
 * diagram figure that returns a tooltip figure that is a
 * {@link QuickFixToolTipProviderFigure} will have a special quick fix tooltip
 * window.
 * <p>
 * This is a 'sticky' tooltip (disappears when mouse exits it but can be
 * activated with a mouse click so that it remains permanent until another
 * window / control is selected).
 * <p>
 * It shows a list (one at a time) of IMarker's returned by the
 * {@link IQuickFixToolTipContentProvider} used to construct the
 * {@link QuickFixToolTipProviderFigure}.
 * <p>
 * For the currently shown marker it shows a list of resolutions (also accessed
 * via {@link IQuickFixToolTipContentProvider} used to construct the
 * {@link QuickFixToolTipProviderFigure}).
 * <p>
 * This strategy is employed because the standard GEF tooltip mechanism works by
 * it's {@link DomainEventDispatcher} detecting mouse hover events then asking
 * the figure under the mouse for it's tooltip figure and then this is passed to
 * the tooltip helper (also supplied by the {@link DomainEventDispatcher}.
 * 
 * @author aallway
 * @since 3.2
 */
public class QuickFixToolTipHelper extends StickyToolTipHelper {

    private Shell quickFixShell = null;

    private QuickFixToolTipControl quickFixToolTipControl = null;

    private boolean currentPopupIsQuickFix = false;

    private MouseTrackListener popupMouseTrackListener;

    private KeyListener popupKeyListener;

    private Composite quickFixControlContainer;

    private QuickFixPopupContentProvider quickFixContentProvider =
            new QuickFixControlResolutionsProvider();

    public QuickFixToolTipHelper(Control c) {
        super(c, false);
    }

    @Override
    public void displayToolTipNear(IFigure hoverSource, IFigure tip,
            int eventX, int eventY) {
        if (tip instanceof QuickFixToolTipProviderFigure) {
            QuickFixToolTipProviderFigure qfProvider =
                    (QuickFixToolTipProviderFigure) tip;

            // If the tooltip is a quick fix provider then set it as the input
            // to the tooltip popup control. It will then ask OUR quick fix
            // content provider which will delegate stuff to this tio.
            getQuickFixToolTipControl().setInput(tip);

            Collection<IMarker> markers =
                    quickFixContentProvider.getMarkers(tip);
            if (markers == null || markers.isEmpty()) {
                // Don't display if marker list is empty!
                return;
            }
        }

        super.displayToolTipNear(hoverSource, tip, eventX, eventY);

        return;
    }

    @Override
    protected void show() {
        setIsShowing(true);
        if (getCurrentToolTip() instanceof QuickFixToolTipProviderFigure) {
            if (getShell().getVisible()) {
                getShell().setVisible(false);
            }

            currentPopupIsQuickFix = true;
            quickFixControlContainer
                    .setBackground(ColorConstants.tooltipBackground);

            getQuickFixShell().setVisible(true);

            getQuickFixShell().moveAbove(null);

        } else {
            if (getQuickFixShell().getVisible()) {
                getQuickFixShell().setVisible(false);
            }

            currentPopupIsQuickFix = false;
            super.show();
        }
    }

    @Override
    protected Shell getDisplayingToolTip() {
        if (isShowing() && currentPopupIsQuickFix) {
            return getQuickFixShell();
        }
        return super.getDisplayingToolTip();
    }

    @Override
    protected void hide() {
        if (currentPopupIsQuickFix) {
            if (quickFixShell != null && !quickFixShell.isDisposed()) {
                quickFixShell.setVisible(false);
            }

            setIsShowing(false);

        } else {
            super.hide();
        }
    }

    @Override
    protected Dimension computeSize(IFigure hoverSource, IFigure tip,
            int eventX, int eventY) {
        if (tip instanceof QuickFixToolTipProviderFigure) {
            Point sz = getQuickFixShell().getSize();

            return new Dimension(sz.x, sz.y);
        }
        return super.computeSize(hoverSource, tip, eventX, eventY);
    }

    @Override
    protected void setShellBounds(int x, int y, int width, int height) {
        super.setShellBounds(x, y, width, height);

        getQuickFixShell().setBounds(x, y, width, height);
        return;
    }

    @Override
    public void dispose() {
        super.dispose();

        if (quickFixShell != null) {
            quickFixShell.dispose();
            quickFixShell = null;
        }
    }

    @Override
    protected Shell createShell() {
        //
        // Our strategy is to provide an alternative popup shell window for
        // quick fix tooltips AS WELL AS the standard tooltip.
        //
        // We will do EVERYTHING we are asked to both (this is because
        // underlying TooltipHelper really was not designed to be overridden
        // sensibly).
        //
        // Then at the very last moment possible (i.e. show() / hide() we will
        // show / hide the quick fix tooltip instead of standard one ifthe
        // current tooltip is instanceof QuickFixToolTipProviderFigure.
        //
        createQuickFixShell();

        return super.createShell();
    }

    public Shell getQuickFixShell() {
        if (quickFixShell == null) {
            createQuickFixShell();
        }
        return quickFixShell;
    }

    protected void createQuickFixShell() {
        if (quickFixShell == null) {
            quickFixShell =
                    new Shell(control.getDisplay(), shellStyle | SWT.ON_TOP);

            FillLayout fl = new FillLayout();
            fl.marginHeight = 0;
            fl.marginWidth = 0;
            quickFixShell.setLayout(fl);

            quickFixControlContainer = new Composite(quickFixShell, SWT.NONE);
            fl = new FillLayout();
            fl.marginHeight = 2;
            fl.marginWidth = 2;
            quickFixControlContainer.setLayout(fl);
            quickFixControlContainer
                    .setBackground(ColorConstants.tooltipBackground);

            quickFixToolTipControl =
                    new QuickFixToolTipControl(quickFixControlContainer,
                            quickFixContentProvider, true, true);

            quickFixShell.addShellListener(createPopupActivationListener());

            popupMouseTrackListener = createPopupMouseTrackListener();
            popupKeyListener = createPopupKeyListener();

            recursiveAddConrtolListenersListener(quickFixShell);
        }

        return;
    }

    private void recursiveAddConrtolListenersListener(Control c) {
        c.addMouseTrackListener(popupMouseTrackListener);
        c.addKeyListener(popupKeyListener);

        if (c instanceof Composite) {
            Control[] children = ((Composite) c).getChildren();
            if (children != null) {
                for (Control child : children) {
                    recursiveAddConrtolListenersListener(child);
                }
            }
        }
        return;
    }

    public QuickFixToolTipControl getQuickFixToolTipControl() {
        if (quickFixToolTipControl == null) {
            createQuickFixShell();
        }
        return quickFixToolTipControl;
    }

    @Override
    protected void handlePopupActivated() {
        super.handlePopupActivated();

        // Fake having a 'border' round an activated quick fix popup by setting
        // the 'border' composite around the quick fix control grey
        quickFixControlContainer.setBackground(ColorConstants.lightGray);
        return;
    }

    /**
     * Resolution provider for the quick fix control.
     * <p>
     * This acts as a bridge between the {@link IQuickFixToolTipContentProvider}
     * in the {@link QuickFixToolTipProviderFigure} tooltip returned by a
     * diagram figure and the quick fix popup control's
     * {@link QuickFixPopupContentProvider}.
     * 
     * @author aallway
     * @since 3.2
     */
    private class QuickFixControlResolutionsProvider implements
            QuickFixPopupContentProvider {

        public Collection<IMarker> getMarkers(Object input) {
            if (input instanceof QuickFixToolTipProviderFigure) {
                QuickFixToolTipProviderFigure qfProviderFig =
                        (QuickFixToolTipProviderFigure) input;

                return qfProviderFig.getMarkersProvider()
                        .getMarkers(qfProviderFig.getMarkerHost());
            }

            return null;
        }

        public Collection<IMarkerResolution> getResolutions(Object input,
                IMarker marker) {
            if (input instanceof QuickFixToolTipProviderFigure) {
                QuickFixToolTipProviderFigure qfProviderFig =
                        (QuickFixToolTipProviderFigure) input;

                return qfProviderFig.getMarkersProvider()
                        .getMarkerResolutions(marker);
            }
            return null;
        }

        public void executeQuickFix(Object input, IMarker marker,
                IMarkerResolution quickFix) {
            final IMarker finalMarker = marker;
            final IMarkerResolution finalQuickFix = quickFix;

            Display.getCurrent().asyncExec(new Runnable() {
                public void run() {
                    finalQuickFix.run(finalMarker);
                    hide();
                }
            });
        }

    }

}
