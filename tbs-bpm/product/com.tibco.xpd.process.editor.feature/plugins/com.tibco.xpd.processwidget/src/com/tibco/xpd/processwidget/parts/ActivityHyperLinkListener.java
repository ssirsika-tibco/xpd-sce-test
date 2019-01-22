/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processwidget.parts;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.gef.EditPart;

import com.tibco.xpd.processwidget.IActivityHyperlinkHandler;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.internal.Messages;

/**
 * Listener that can be added as a goto-hyperlink on the given figure.
 * <p>
 * Hyperlink action provision is via extension point.
 * <p>
 * Hyperlink is activated ONLY on DOUBLE-CLICK - some activity icons occupy
 * nearly the entire activity figure (event for instance) and therefore it would
 * be hard for the user to move the activity.
 * 
 * @author aallway
 * @since 19 Aug 2011
 */
public class ActivityHyperLinkListener implements MouseListener,
        MouseMotionListener {

    private EditPart editPart;

    private IFigure hyperlinkFigure = null;

    /**
     * The current heyperlink handler (only available bnetween mouseEnter and
     * mouseExit events).
     */
    private IActivityHyperlinkHandler currentHyperlinkHandler = null;

    /**
     * The enablement context object (returned by the enabled hyerplink handler
     * during mouse enter and used until mouse exit)
     */
    private Object enablementContextObject = null;

    private static Set<IActivityHyperlinkHandler> hyperlinkHandlerCache = null;

    int pressCount = 0;

    /**
     * @param editPart
     *            Owner editpart
     * @param hyperLinkFigure
     *            Specific figure within the activity that acts as hyperlink.
     */
    public ActivityHyperLinkListener(EditPart editPart) {
        super();
        this.editPart = editPart;
    }

    /**
     * Set the figure to be used as the hyperlink control.
     * <p>
     * This adds all necessary listeners etc.
     * <p>
     * Pass <code>null</code> to disconnect the listener from hyperlink figure.
     * <b> This should always be done before figure is disposed.
     * 
     * @param hyperlinkFigure
     *            the hyperlinkFigure to set
     */
    public void setHyperlinkFigure(IFigure hyperlinkFigure) {

        if (this.hyperlinkFigure != null) {
            this.hyperlinkFigure.removeMouseListener(this);
            this.hyperlinkFigure.removeMouseMotionListener(this);
        }

        this.hyperlinkFigure = hyperlinkFigure;

        if (this.hyperlinkFigure != null) {
            this.hyperlinkFigure.addMouseListener(this);
            this.hyperlinkFigure.addMouseMotionListener(this);
        }
    }

    /**
     * @see org.eclipse.draw2d.MouseMotionListener#mouseEntered(org.eclipse.draw2d.MouseEvent)
     * 
     * @param me
     */
    @Override
    public void mouseEntered(MouseEvent me) {
        String tooltip = null;
        pressCount = 0;

        /*
         * Setup ready for hyperlink - find the hyper link handler and if there
         * is one, setup the tooltip and so on.
         */
        currentHyperlinkHandler = getActivityHyperlinkHandler();

        if (currentHyperlinkHandler != null) {
            if (enablementContextObject != null) {
                hyperlinkFigure.setCursor(Cursors.HAND);
            } else {
                hyperlinkFigure.setCursor(Cursors.NO);
            }

            tooltip =
                    currentHyperlinkHandler.getHyperlinkTooltip(editPart
                            .getModel(), enablementContextObject);

        } else {
            /* Use default mouse cursor if no provider. */
            hyperlinkFigure.setCursor(null);
        }

        if (tooltip != null && tooltip.length() > 0) {
            if (enablementContextObject != null) {
                hyperlinkFigure
                        .setToolTip(new Label(
                                " "     + Messages.ActivityHyperLinkListener_DoubleClick_label + " " + tooltip + " ")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            } else {
                hyperlinkFigure.setToolTip(new Label(" " + tooltip + " ")); //$NON-NLS-1$ //$NON-NLS-2$

            }

        } else {
            hyperlinkFigure.setToolTip(null);
        }

    }

    /**
     * @see org.eclipse.draw2d.MouseListener#mousePressed(org.eclipse.draw2d.MouseEvent)
     * 
     * @param me
     */

    @Override
    public void mousePressed(MouseEvent me) {
        /*
         * This may seem odd, but basically the mouse listener will ONLY pass us
         * double-click events IF we tag the event as consumed during the mouse
         * pressed event.
         * 
         * However, if we consume the mouse pressed then the underlying diagram
         * editor does not receive the events - this means that the user cannot
         * click-drag on the activity anywhere in its icon area to move the
         * whole activity.
         * 
         * However, if we don't consume the pressed event then when user
         * performs a 'double-click' operation then we receive 2*pressed
         * followed by 1*release. So we can use that as a basis for detectig
         * double-click without consuming events and therefore teh underlying
         * editor will still receive them.
         * 
         * Hopefully this is reliable enough to continue working into the future
         * and on other platforms!!
         */
        pressCount++;
    }

    /**
     * @see org.eclipse.draw2d.MouseListener#mouseReleased(org.eclipse.draw2d.MouseEvent)
     * 
     * @param me
     */
    @Override
    public void mouseReleased(MouseEvent me) {
        /*
         * This may seem odd, but basically the mouse listener will ONLY pass us
         * double-click events IF we tag the event as consumed during the mouse
         * pressed event.
         * 
         * However, if we consume the mouse pressed then the underlying diagram
         * editor does not receive the events - this means that the user cannot
         * click-drag on the activity anywhere in its icon area to move the
         * whole activity.
         * 
         * However, if we don't consume the pressed event then when user
         * performs a 'double-click' operation then we receive 2*pressed
         * followed by 1*release. So we can use that as a basis for detectig
         * double-click without consuming events and therefore teh underlying
         * editor will still receive them.
         * 
         * Hopefully this is reliable enough to continue working into the future
         * and on other platforms!!
         */
        if (pressCount >= 2) {
            if (currentHyperlinkHandler != null
                    && enablementContextObject != null) {
                me.consume();
                currentHyperlinkHandler.doHyperLink(editPart.getModel(),
                        enablementContextObject);
            }
        }
        pressCount = 0;
    }

    /**
     * @see org.eclipse.draw2d.MouseMotionListener#mouseExited(org.eclipse.draw2d.MouseEvent)
     * 
     * @param me
     */
    @Override
    public void mouseExited(MouseEvent me) {
        /* Clear up transient stuff. */
        currentHyperlinkHandler = null;
        enablementContextObject = null;
        pressCount = 0;
        hyperlinkFigure.setToolTip(null);

    }

    /**
     * @see org.eclipse.draw2d.MouseMotionListener#mouseHover(org.eclipse.draw2d.MouseEvent)
     * 
     * @param me
     */
    @Override
    public void mouseHover(MouseEvent me) {
        //        System.out.println("hover"); //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.draw2d.MouseMotionListener#mouseMoved(org.eclipse.draw2d.MouseEvent)
     * 
     * @param me
     */
    @Override
    public void mouseMoved(MouseEvent me) {
    }

    /**
     * @see org.eclipse.draw2d.MouseListener#mouseDoubleClicked(org.eclipse.draw2d.MouseEvent)
     * 
     * @param me
     */
    @Override
    public void mouseDoubleClicked(MouseEvent me) {
    }

    /**
     * @see org.eclipse.draw2d.MouseMotionListener#mouseDragged(org.eclipse.draw2d.MouseEvent)
     * 
     * @param me
     */
    @Override
    public void mouseDragged(MouseEvent me) {
    }

    /**
     * 
     * @return The first enabled activity hyperlink handler contribution found
     *         otherwise the first disabled handler otherwise <code>null</code>
     *         if there are no handlers. Also sets
     *         {@link #enablementContextObject} when enabledcontribution found.
     */
    private IActivityHyperlinkHandler getActivityHyperlinkHandler() {
        /*
         * Cache the set of hyperlink handlers first time.
         */
        if (hyperlinkHandlerCache == null) {
            cacheHyperlinkHandlers();
        }

        enablementContextObject = null;

        /*
         * Find the first enabled contribution for acvity type (or first found
         * if none enabled)
         */
        Object activityModelObject = editPart.getModel();

        if (activityModelObject != null) {
            IActivityHyperlinkHandler firstFound = null;

            IActivityHyperlinkHandler enabledFound = null;

            for (IActivityHyperlinkHandler hyperlinkHandler : hyperlinkHandlerCache) {
                if (hyperlinkHandler.isApplicableActivity(activityModelObject)) {
                    enablementContextObject =
                            hyperlinkHandler
                                    .getEnablementContextObject(activityModelObject);

                    if (enablementContextObject != null) {
                        enabledFound = hyperlinkHandler;
                        break;
                    }

                    if (firstFound == null) {
                        firstFound = hyperlinkHandler;
                    }
                }
            }

            if (enabledFound != null) {
                return enabledFound;
            } else if (firstFound != null) {
                return firstFound;
            }
        }

        return null;
    }

    /**
     * Cache the hyperlink handlers.
     */
    private void cacheHyperlinkHandlers() {
        hyperlinkHandlerCache = new HashSet<IActivityHyperlinkHandler>();

        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(ProcessWidgetPlugin.ID,
                                "activityHyperlinkHandler"); //$NON-NLS-1$

        if (point != null) {
            IExtension[] extensions = point.getExtensions();

            if (extensions != null) {
                for (IExtension ext : extensions) {
                    IConfigurationElement[] configurationElements =
                            ext.getConfigurationElements();

                    if (configurationElements != null) {

                        for (IConfigurationElement configElement : configurationElements) {
                            if ("activityHyperlinkHandler".equals(configElement.getName())) { //$NON-NLS-1$
                                try {
                                    Object clazz =
                                            configElement
                                                    .createExecutableExtension("class"); //$NON-NLS-1$
                                    if (clazz instanceof IActivityHyperlinkHandler) {
                                        hyperlinkHandlerCache
                                                .add((IActivityHyperlinkHandler) clazz);
                                    } else {
                                        ProcessWidgetPlugin
                                                .getDefault()
                                                .getLogger()
                                                .error("activityHyperlinkHandler contribution must implement IActivityHyperlinkActionHandler. Contributor: " //$NON-NLS-1$
                                                        + ext.getContributor()
                                                                .getName());
                                    }

                                } catch (CoreException e) {
                                    ProcessWidgetPlugin
                                            .getDefault()
                                            .getLogger()
                                            .error("Failed to load activityHyperlinkHandler from contributor: " //$NON-NLS-1$
                                                    + ext.getContributor()
                                                            .getName());
                                }

                            }
                        }
                    }
                }
            }
        }
        return;
    }

}
