package com.tibco.xpd.quickfixtooltip.api;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.PopUpHelper;
import org.eclipse.draw2d.SWTEventDispatcher;
import org.eclipse.draw2d.ToolTipHelper;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This class is extends ToolTipHelper but basically completely overrides it's
 * normal behaviour so that we can handle different scenarios.
 * <p>
 * In order to take make any given tooltip then either the tooltip itself or the
 * tooltip source must implement {@link StickyToolTip}. Alternatively, the
 * {@link StickyTooltipWithNotification} can be implemented (this extension
 * allows tooltip or source figure to be notified when tooltip is about to be
 * shown, shown, about to be hidden and hidden)
 * <p>
 * You can also use the class {@link StickyTooltipFigure} that implements these
 * interfaces, handles selection state display and allows you to provide the
 * content of the tooltip as a figure.
 * <p>
 * For instance original ToolTip helper does not give any access to it's
 * "kill popup after 5 seconds" timer which means we couldn't have a popup that
 * could be selected to be made permanent.
 * <p>
 * However, the GEF DomainEventDispatcher requires that the tooltip helper is
 * instanceof TooTipHelper so we can't create our own completely.
 * 
 * 
 * @author aallway
 * @since 3.2
 */
public class StickyToolTipHelper extends ToolTipHelper {

    protected Timer killPopupTimer;

    protected IFigure currentTipSource;

    private boolean allTipsSticky = false;

    private boolean currentToolTipIsSticky = false;

    private IFigure currentToolTip = null;

    private boolean isShowing = false;

    private boolean stickyPopupSelected = false;

    private boolean consoleLogging = false;

    public StickyToolTipHelper(Control c, boolean allTipsSticky) {
        super(c);
        this.allTipsSticky = allTipsSticky;

        c.getShell().addShellListener(new ShellListener() {

            @Override
            public void shellDeactivated(ShellEvent e) {
                if (isShowing()) {
                    // Shell active = Display.getDefault().getActiveShell();
                    // hide();
                }
            }

            @Override
            public void shellActivated(ShellEvent e) {
            }

            @Override
            public void shellClosed(ShellEvent e) {
            }

            @Override
            public void shellDeiconified(ShellEvent e) {
            }

            @Override
            public void shellIconified(ShellEvent e) {
            }
        });
    }

    /**
     * @see org.eclipse.draw2d.PopUpHelper#createLightweightSystem()
     * 
     * @return
     */
    @Override
    protected LightweightSystem createLightweightSystem() {
        /*
         * PopupHelper only ever creates popup shell once (and then shows /
         * hides it as appropriate.
         * 
         * Unfortunately, because we can handle click-activation of the tooltip
         * shell it means that if the user does so and then presses Escape, the
         * default behaviour is to close the shell and dispoase it. Next time we
         * try and show the popup, we will get SWT control disposed errors.
         * 
         * We can get around this by setting our own event dispatcher that sets
         * the "doit" on SWT.TRAVERSE_ESCAPE events back to false.
         */
        LightweightSystem lightweightSystem = super.createLightweightSystem();

        lightweightSystem.setEventDispatcher(new SWTEventDispatcher() {
            /**
             * @see org.eclipse.draw2d.SWTEventDispatcher#dispatchKeyTraversed(org.eclipse.swt.events.TraverseEvent)
             * 
             * @param e
             */
            @Override
            public void dispatchKeyTraversed(TraverseEvent e) {
                if (e.detail == SWT.TRAVERSE_ESCAPE) {
                    e.doit = false;
                }
                super.dispatchKeyTraversed(e);

            }
        });

        return lightweightSystem;
    }

    @Override
    public void updateToolTip(IFigure figureUnderMouse, IFigure tip,
            int eventX, int eventY) {
        if (!stickyPopupSelected) {
            // If the tip is a StickyToolTip OR we are currently showing a
            // sticky
            // tooltip then switch to our special behaviour.

            if (isSticky(figureUnderMouse) || isSticky(tip)
                    || currentToolTipIsSticky) {

                boolean mouseIsOverTooltip = false;

                if (getShell() != null && isShowing()) {

                    Point dispCoords;

                    if (figureUnderMouse == null) {
                        dispCoords = control.toDisplay(eventX, eventY);
                    } else {
                        dispCoords = new Point(eventX, eventY);
                    }

                    Rectangle b = getShell().getBounds();

                    if (b.contains(dispCoords)) {
                        mouseIsOverTooltip = true;
                    }
                }

                if (mouseIsOverTooltip) {
                    // If the mouse is over the current sticky tooltip then do
                    // nothing.
                    return;
                }
            }

            if (figureUnderMouse == null) {
                if (isShowing()) {
                    hide();
                    cancelKillToolTipTimer();
                }
            }

            if (isShowing() && figureUnderMouse != currentTipSource) {
                hide();
                cancelKillToolTipTimer();
                displayToolTipNear(figureUnderMouse, tip, eventX, eventY);

            } else if (!isShowing() && figureUnderMouse != currentTipSource) {
                currentTipSource = null;
            }
        }
    }

    /**
     * Cancel the timer running to quit tooltip.
     */
    public void cancelKillToolTipTimer() {
        if (killPopupTimer != null) {
            killPopupTimer.cancel();
        }
        return;
    }

    @Override
    public void displayToolTipNear(IFigure hoverSource, IFigure tip,
            int eventX, int eventY) {
        //
        // Only re-display toolip IF...
        // - User has not clicked on (and hence made 'sticky') a sticky tooltip.
        // - Or the user has hovered over another figure that has sticky
        // tooltips.
        if (!stickyPopupSelected || isSticky(hoverSource) || isSticky(tip)) {
            currentToolTip = tip;

            if (tip != null && hoverSource != currentTipSource) {

                if (isSticky(hoverSource) || isSticky(tip)) {
                    currentToolTipIsSticky = true;

                    eventX -= 1;
                    eventY -= 1;

                } else {
                    currentToolTipIsSticky = false;

                    eventY += 26;
                }

                /*
                 * Set the content of the canvas, calculate size and then
                 * display.
                 */
                getLightweightSystem().setContents(tip);

                /*
                 * Notify tooltip it's about to be shown.
                 */
                if (currentToolTip instanceof StickyTooltipWithNotification) {
                    ((StickyTooltipWithNotification) currentToolTip)
                            .aboutToShow(getShell());
                }

                if (currentTipSource instanceof StickyTooltipWithNotification) {
                    ((StickyTooltipWithNotification) currentTipSource)
                            .aboutToShow(getShell());
                }

                Point displayPoint =
                        computeLocation(hoverSource, tip, eventX, eventY);

                Dimension shellSize =
                        computeSize(hoverSource, tip, eventX, eventY);

                setShellBounds(displayPoint.x,
                        displayPoint.y,
                        shellSize.width,
                        shellSize.height);

                currentTipSource = hoverSource;

                if (!stickyPopupSelected) {
                    show();

                    killPopupTimer = new Timer(true);

                    if (!currentToolTipIsSticky) {
                        killPopupTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Display.getDefault().syncExec(new Runnable() {
                                    @Override
                                    public void run() {
                                        hide();
                                        cancelKillToolTipTimer();
                                    }
                                });
                            }
                        }, 5000);
                    }
                }
            }
        }
        return;
    }

    @Override
    protected void hookShellListeners() {
        getShell().addMouseTrackListener(createPopupMouseTrackListener());

        getShell().addShellListener(createPopupActivationListener());

        getShell().addKeyListener(createPopupKeyListener());

    }

    protected MouseTrackListener createPopupMouseTrackListener() {
        return new PopupMouseTrackListener(this);
    }

    protected ShellListener createPopupActivationListener() {
        return new PopupShellActivationListener(this);
    }

    protected KeyListener createPopupKeyListener() {
        return new PopupKeyListener(this);
    }

    /**
     * Disposes of the tooltip's shell and kills the timer.
     * 
     * @see PopUpHelper#dispose()
     */
    @Override
    public void dispose() {
        if (isShowing()) {
            cancelKillToolTipTimer();
            hide();
        }
        getShell().dispose();
    }

    public IFigure getCurrentToolTip() {
        return currentToolTip;
    }

    private boolean isSticky(IFigure fig) {
        return allTipsSticky || (fig instanceof StickyToolTip);
    }

    protected Point computeLocation(IFigure hoverSource, IFigure tip,
            int eventX, int eventY) {
        org.eclipse.swt.graphics.Rectangle clientArea =
                control.getDisplay().getClientArea();

        /*
         * Sid SNA-20. Place the popup just to the right/down of mouse so that
         * popup doesn't intefere with user's first click to select.
         */
        Point preferredLocation = new Point(eventX + 2, eventY + 2);

        Dimension tipSize = computeSize(hoverSource, tip, eventX, eventY);

        // Adjust location if tip is going to fall outside display
        if (preferredLocation.y + tipSize.height > clientArea.height) {
            preferredLocation.y = eventY - tipSize.height;
        }

        if (preferredLocation.x + tipSize.width > clientArea.width)
            preferredLocation.x -=
                    (preferredLocation.x + tipSize.width) - clientArea.width;

        return preferredLocation;
    }

    /**
     * @param hoverSource
     * @param tip
     * @param eventX
     * @param eventY
     * @return
     */
    protected Dimension computeSize(IFigure hoverSource, IFigure tip,
            int eventX, int eventY) {
        return getLightweightSystem().getRootFigure().getPreferredSize()
                .getExpanded(getShellTrimSize());
    }

    protected void handlePopupActivated() {
        logToConsole("ACTIVATED"); //$NON-NLS-1$
        //
        // Don't kill on mouse exit once user has activated the shell (clicked
        // on a child control. Until the user selects a different control.
        if (currentToolTipIsSticky) {
            stickyPopupSelected = true;
        }

        return;
    }

    protected void handlePopupDeactivated() {
        logToConsole("DEACTIVATED"); //$NON-NLS-1$
        if (stickyPopupSelected) {
            hide();
            stickyPopupSelected = false;
        }
        return;
    }

    protected void handleMouseEnterPopupOrChild(MouseEvent e) {

        //
        // This is designed to work when the PopupMouseTrackListener is added to
        // the shell itself OR any child of the shell.
        boolean cursorOverPopup = isCursorOverPopupOrChild();
        logToConsole("IN: cursorOVerPopup: " + cursorOverPopup); //$NON-NLS-1$

        if (cursorOverPopup) {
            // Kill popup on mouse enter unless it is a sticky one.
            if (!currentToolTipIsSticky) {
                hide();
                currentTipSource = null;
                cancelKillToolTipTimer();
            }
        }
        return;
    }

    protected void handleMouseExitPopupOrChild(MouseEvent e) {
        //
        // This is designed to work when the PopupMouseTrackListener is added to
        // the shell itself OR any child of the shell.
        Control c = control.getDisplay().getCursorControl();
        boolean cursorOverPopup = isCursorOverPopupOrChild();
        logToConsole("OUT: cursorOVerPopup: " + cursorOverPopup); //$NON-NLS-1$

        //
        // Ignore mouse exit control IF mouse is still over popup - i.e. because
        // PopupMouseTrackListener can be added to the popup OR ANY child of the
        // popup, we will get enter/exits when the mouse passes between child
        // controls in the popup, therefore we shouldn't kill until the mouse is
        // out of whole popup.
        if (!cursorOverPopup) {
            if (currentToolTipIsSticky && !stickyPopupSelected) {

                hide();
                currentTipSource = null;

                // Shouldn't be a timer but just in case...
                cancelKillToolTipTimer();
            }
        }

        return;
    }

    protected void handleMouseHoverOnPopupOrChild(MouseEvent e) {
    }

    private boolean isCursorOverPopupOrChild() {
        if (control != null && !control.isDisposed()) {
            Shell popup = getDisplayingToolTip();

            Control c = control.getDisplay().getCursorControl();

            while (c != null) {
                if (c == popup) {
                    return true;
                }
                c = c.getParent();
            }
        }
        return false;
    }

    @Override
    protected void show() {
        stickyPopupSelected = false;
        setIsShowing(true);

        /*
         * Show the tooltip.
         */
        super.show();

        /*
         * Notify tooltip it has been shown.
         */
        if (currentToolTip instanceof StickyTooltipWithNotification) {
            ((StickyTooltipWithNotification) currentToolTip).shown();
        }

        if (currentTipSource instanceof StickyTooltipWithNotification) {
            ((StickyTooltipWithNotification) currentTipSource).shown();
        }

    }

    protected Shell getDisplayingToolTip() {
        if (isShowing()) {
            return getShell();
        }
        return null;
    }

    @Override
    protected void hide() {
        /*
         * Notify tooltip it is about to be hidden.
         */
        if (currentToolTip instanceof StickyTooltipWithNotification) {
            ((StickyTooltipWithNotification) currentToolTip).aboutToHide();
        }

        if (currentTipSource instanceof StickyTooltipWithNotification) {
            ((StickyTooltipWithNotification) currentTipSource).aboutToHide();
        }

        /*
         * Hide it.
         */
        super.hide();
        setIsShowing(false);

        /*
         * Notify tooltip it has been hidden.
         */
        if (currentToolTip instanceof StickyTooltipWithNotification) {
            ((StickyTooltipWithNotification) currentToolTip).hidden();
        }

        if (currentTipSource instanceof StickyTooltipWithNotification) {
            ((StickyTooltipWithNotification) currentTipSource).hidden();
        }

    }

    @Override
    public boolean isShowing() {
        // Override the subclass is showing so that we can give caller access to
        // it.
        return isShowing;
    }

    public void setIsShowing(boolean isShowing) {
        this.isShowing = isShowing;
    }

    public static class PopupMouseTrackListener implements MouseTrackListener {
        StickyToolTipHelper helper;

        public PopupMouseTrackListener(StickyToolTipHelper helper) {
            this.helper = helper;
        }

        @Override
        public void mouseEnter(MouseEvent e) {
            helper.handleMouseEnterPopupOrChild(e);
        }

        @Override
        public void mouseExit(MouseEvent e) {
            helper.handleMouseExitPopupOrChild(e);
        }

        @Override
        public void mouseHover(MouseEvent e) {
            helper.handleMouseHoverOnPopupOrChild(e);
        }

    }

    public static class PopupShellActivationListener implements ShellListener {
        StickyToolTipHelper helper;

        public PopupShellActivationListener(StickyToolTipHelper helper) {
            this.helper = helper;
        }

        @Override
        public void shellActivated(ShellEvent e) {
            helper.handlePopupActivated();
        }

        @Override
        public void shellDeactivated(ShellEvent e) {
            helper.handlePopupDeactivated();
        }

        @Override
        public void shellClosed(ShellEvent e) {
        }

        @Override
        public void shellDeiconified(ShellEvent e) {
        }

        @Override
        public void shellIconified(ShellEvent e) {
        }

    }

    public static class PopupKeyListener implements KeyListener {
        StickyToolTipHelper helper;

        public PopupKeyListener(StickyToolTipHelper helper) {
            this.helper = helper;
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.keyCode == SWT.ESC) {
                helper.hide();
                e.doit = false;
            }
            return;
        }
    }

    protected void logToConsole(String msg) {
        if (consoleLogging) {
            System.out.println(msg);
        }
    }

}
