/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.quickfixtooltip.api;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IMarker;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMarkerHelpRegistry;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.quickfixtooltip.control.QuickFixToolTipControl;
import com.tibco.xpd.quickfixtooltip.control.QuickFixToolTipControl.QuickFixPopupContentProvider;

/**
 * 
 * 
 * @author aallway
 * @since 3.3 (12 Jan 2010)
 */
public class QuickFixPopupStandalone {

    private Shell popupShell;

    private Collection<IMarker> markers;

    private KillQuickFixOnDeactivateListener popupDeactivateListener;

    private Composite quickFixControlContainer;

    private QuickFixToolTipControl quickFixToolTipControl;

    private Point initialLocation = new Point(0, 0);

    public QuickFixPopupStandalone(Collection<IMarker> markers, Point location) {
        super();

        initialLocation.x = location.x;
        initialLocation.y = location.y;

        this.markers = markers;
        popupShell =
                new Shell((Display) null, SWT.NO_TRIM | SWT.ON_TOP | SWT.TOOL);

        FillLayout fl = new FillLayout();
        fl.marginHeight = 0;
        fl.marginWidth = 0;
        popupShell.setLayout(fl);

        /* Create it. */
        createContent(popupShell);

        popupDeactivateListener = new KillQuickFixOnDeactivateListener();

        popupShell.addShellListener(popupDeactivateListener);

        quickFixToolTipControl.setInput(this);
    }

    /**
     * @param quickFixPopup2
     */
    private void createContent(Shell popupShell) {
        quickFixControlContainer = new Composite(popupShell, SWT.BORDER);
        FillLayout fl = new FillLayout();
        fl.marginHeight = 2;
        fl.marginWidth = 2;
        quickFixControlContainer.setLayout(fl);

        quickFixControlContainer
                .setBackground(ColorConstants.tooltipBackground);

        quickFixToolTipControl =
                new QuickFixToolTipControl(quickFixControlContainer,
                        new QuickFixContentProvider(), true, true, true);

        Point sz =
                quickFixControlContainer.computeSize(SWT.DEFAULT, SWT.DEFAULT);

        popupShell.setSize(sz.x, sz.y);
        popupShell.layout();
        return;
    }

    public void openPopup() {
        /* Make sure we stay within display bounds! */

        Point sz = popupShell.getSize();
        Rectangle dBnds = popupShell.getDisplay().getBounds();
        if ((initialLocation.x + sz.x) > dBnds.width) {
            initialLocation.x = (dBnds.width - sz.x);
        }

        if ((initialLocation.y + sz.y) > dBnds.height) {
            initialLocation.y = (dBnds.height - sz.y);
        }

        popupShell.setLocation(initialLocation);

        popupShell.open();
    }

    /**
     * Close the quick fix popup.
     */
    public void closePopup() {
        if (popupShell != null && !popupShell.isDisposed()) {
            popupShell.removeShellListener(popupDeactivateListener);
            popupShell.close();
        }

        return;
    }

    /**
     * Closes the quick fix popup when it is deactivated.
     * 
     * 
     * @author aallway
     * @since 3.3 (12 Jan 2010)
     */
    private class KillQuickFixOnDeactivateListener extends ShellAdapter {

        @Override
        public void shellDeactivated(ShellEvent e) {
            // Quick fix popup has been deactivated

            popupShell.getDisplay().asyncExec(new Runnable() {
                public void run() {
                    if (!popupShell.isDisposed()) {
                        closePopup();
                    }
                    return;
                }
            });
        }
    }

    private class QuickFixContentProvider implements
            QuickFixPopupContentProvider {

        public void executeQuickFix(Object input, IMarker marker,
                IMarkerResolution quickFix) {
            final IMarker finalMarker = marker;
            final IMarkerResolution finalQuickFix = quickFix;

            Display.getCurrent().asyncExec(new Runnable() {
                public void run() {
                    closePopup();
                    finalQuickFix.run(finalMarker);
                }
            });
            return;
        }

        public Collection<IMarker> getMarkers(Object input) {
            return markers;
        }

        public Collection<IMarkerResolution> getResolutions(Object input,
                IMarker marker) {

            IMarkerHelpRegistry markerHelpRegistry =
                    IDE.getMarkerHelpRegistry();

            IMarkerResolution[] resolutions =
                    markerHelpRegistry.getResolutions(marker);

            if (resolutions != null) {
                return Arrays.asList(resolutions);
            }

            return Collections.emptyList();
        }

    }
}
