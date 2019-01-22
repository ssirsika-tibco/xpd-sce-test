/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.quickfixtooltip.control;

import java.awt.Cursor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Cursors;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.quickfixtooltip.QuickFixToolTipActivator;
import com.tibco.xpd.quickfixtooltip.QuickFixToolTipConstants;
import com.tibco.xpd.quickfixtooltip.api.QuickFixToolTipProviderFigure;
import com.tibco.xpd.quickfixtooltip.internal.Messages;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.provider.IIssue;

/**
 * Handles main aspects of the quick fix popup tooltip control (that sits within
 * a shell)
 * <p>
 * On construction it is passed a {@link QuickFixPopupContentProvider} then when
 * you call {@link QuickFixToolTipControl#setInput(Object)} this will be used
 * refresh the content problem markers and quick fixes (the content provider
 * will be passed the given input.
 * 
 * @author aallway
 * @since 3.2
 */
public class QuickFixToolTipControl {

    /**
     * Interface for creator to execute quick fix on behalf of this control.
     * 
     * @author aallway
     * @since 3.2
     */
    public interface QuickFixPopupContentProvider {
        /**
         * Return the markers for the gven markerHost.
         * 
         * @param markerHost
         *            This is the markerHost object that the
         *            {@link QuickFixToolTipProviderFigure} was constructed with
         *            initially.
         * 
         * @return The markers for the given markerHost object.
         */
        Collection<IMarker> getMarkers(Object input);

        /**
         * Return the resolutions for the given marker.
         * 
         * @param marker
         * @return the resolutions for the given marker.
         */
        Collection<IMarkerResolution> getResolutions(Object input,
                IMarker marker);

        /**
         * Execute quick fix on behalf of control.
         * 
         * @param marker
         * @param quickFix
         */
        void executeQuickFix(Object input, IMarker marker,
                IMarkerResolution quickFix);
    }

    private Object input = null;

    private Composite rootContainer;

    private Composite titleSection;

    private Label markerIcon;

    private FormText markerDescription;

    private boolean mouseInMarkerDescription = false;

    private Control prevNextContainer;

    private CLabel prevActionButton;

    private CLabel nextActionButton;

    private Composite quickFixSection;

    private CLabel quickFixItemsHeader;

    private Composite quickFixLabelContainer;

    private List<FormText> quickFixItemHyperLinks = new ArrayList<FormText>();

    private List<Control> quickFixItemIcons = new ArrayList<Control>();

    private static final String QUICKFIX_LABEL_IMARKERRESOLUTION =
            "QUICKFIX_IMARKERESOLUTION"; //$NON-NLS-1$

    private static final String QUICKFIX_LABEL_IMARKER = "QUICKFIX_IMARKER"; //$NON-NLS-1$

    private static final int MAX_MARKERS = 20;

    private List<MarkerAndResolutions> markerList = Collections.emptyList();

    private MarkerAndResolutions currentMarker = null;

    private Shell parentShell;

    private int optimumTitleWidth = 0;

    private QuickFixPopupContentProvider quickFixContentProvider;

    private boolean resizeParentShellOnUpdate = false;

    private boolean canDragParentShell = false;

    private boolean hyperlinkMarkerDescription = false;

    private boolean firstUpdateAfterSetMarkers = false;

    private static final String NEXTPREV_CURRENT_IMAGE =
            "PrevNextMouseButtonMouseListener.curr_image"; //$NON-NLS-1$

    /**
     * Listens for user pressing a quick fix hyperlink and executes the quick
     * fix.
     */
    private IHyperlinkListener quickFixHyperLinkListener =
            new HyperlinkAdapter() {
                @Override
                public void linkActivated(HyperlinkEvent e) {
                    executeQuickFix((IMarker) e.widget.getData(QUICKFIX_LABEL_IMARKER),
                            (IMarkerResolution) e.widget
                                    .getData(QUICKFIX_LABEL_IMARKERRESOLUTION));
                }
            };

    /**
     * Quick fix list icon listener - executes quick fix when user clicks icon.
     */
    private MouseListener quickFixIconClickListener = new MouseAdapter() {
        @Override
        public void mouseUp(MouseEvent e) {
            executeQuickFix((IMarker) e.widget.getData(QUICKFIX_LABEL_IMARKER),
                    (IMarkerResolution) e.widget
                            .getData(QUICKFIX_LABEL_IMARKERRESOLUTION));
        }
    };

    /**
     * Marker description (goto marker locaiton) hyperlink underline on/off
     * controller.
     */
    private MouseTrackListener markerDescriptionHyperLinkTrackListener =
            new MouseTrackListener() {

                @Override
                public void mouseEnter(MouseEvent e) {
                    mouseInMarkerDescription = true;
                    updateMarkerDescription();
                }

                @Override
                public void mouseExit(MouseEvent e) {
                    mouseInMarkerDescription = false;
                    updateMarkerDescription();
                }

                @Override
                public void mouseHover(MouseEvent e) {
                }
            };

    /**
     * Marker description hyperlink clicked listener.
     */
    private IHyperlinkListener markerDescriptionHyperLinkClickListener =
            new HyperlinkAdapter() {
                @Override
                public void linkActivated(HyperlinkEvent e) {
                    if (currentMarker != null) {
                        IWorkbench wb = PlatformUI.getWorkbench();
                        if (wb != null) {
                            IWorkbenchWindow win =
                                    wb.getActiveWorkbenchWindow();
                            if (win != null) {
                                IWorkbenchPage page = win.getActivePage();

                                if (page != null) {
                                    try {
                                        IDE.openEditor(page,
                                                currentMarker.marker,
                                                OpenStrategy.activateOnOpen());
                                    } catch (PartInitException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            };

    /**
     * Key listener for Left/Right Arrow.
     */
    private KeyListener prevNextActionKeyListener = new KeyListener() {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.keyCode == SWT.ARROW_LEFT) {
                gotoPrevMarker();
            } else if (e.keyCode == SWT.ARROW_RIGHT) {
                gotoNextMarker();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    };

    /**
     * Add a problem markers and quick fix control to the given container.
     * 
     * @param container
     * @param quickFixContentProvider
     * @param resizeParentShellOnUpdate
     */
    public QuickFixToolTipControl(Composite container,
            QuickFixPopupContentProvider quickFixContentProvider,
            boolean resizeParentShellOnUpdate, boolean canDragParentShell) {
        parentShell = container.getShell();
        this.quickFixContentProvider = quickFixContentProvider;
        this.resizeParentShellOnUpdate = resizeParentShellOnUpdate;
        this.canDragParentShell = canDragParentShell;

        init(container);
    }

    /**
     * Add a problem markers and quick fix control to the given container.
     * 
     * @param container
     * @param quickFixContentProvider
     * @param resizeParentShellOnUpdate
     */
    public QuickFixToolTipControl(Composite container,
            QuickFixPopupContentProvider quickFixContentProvider,
            boolean resizeParentShellOnUpdate, boolean canDragParentShell,
            boolean hyperlinkMarkerDescription) {

        parentShell = container.getShell();
        this.quickFixContentProvider = quickFixContentProvider;
        this.resizeParentShellOnUpdate = resizeParentShellOnUpdate;
        this.canDragParentShell = canDragParentShell;
        this.hyperlinkMarkerDescription = hyperlinkMarkerDescription;

        init(container);
    }

    /**
     * Reset the input, the control will be refresh with the content returned by
     * the {@link QuickFixPopupContentProvider} for the given input.
     * <p>
     * Note that the set input will culminate in a resize of the parent shell
     * control if constructed with resizeParentShellOnUpdate=true;
     * 
     * @param input
     *            Input will be used with the caller's
     *            {@link QuickFixPopupContentProvider}
     */
    public void setInput(Object input) {
        this.input = input;

        Collection<IMarker> markers = quickFixContentProvider.getMarkers(input);
        if (markers == null) {
            markers = Collections.emptyList();
        }

        setMarkers(markers);

        return;
    }

    /**
     * Initialise the control (called during construction.
     * 
     * @param container
     */
    protected void init(Composite container) {
        setDefaultControlAttributes(parentShell);
        createControls(container);

    }

    /**
     * Reset the control from the given problem markers.
     * 
     * @param markers
     */
    private void setMarkers(Collection<IMarker> markers) {
        firstUpdateAfterSetMarkers = true;
        optimumTitleWidth = 100;

        if (!rootContainer.isDisposed()) {
            // Grab a GC to calculate optimum width.
            GC gc = new GC(markerDescription);
            Shell activeShell = Display.getDefault().getActiveShell();

            try {
                if (activeShell != null) {
                    activeShell.setCursor(Display.getDefault()
                            .getSystemCursor(Cursor.WAIT_CURSOR));
                }

                currentMarker = null;
                markerList = Collections.emptyList();

                if (markers != null) {
                    /*
                     * Get MAX_MARKERS markers at the most (preferrign problems
                     * over warnings if ther are any
                     */
                    markerList = getMostImportant50Markers(markers);

                    //
                    // Sort to Group by severity (giving priority within group
                    // to those that have quick fix).

                    //
                    // For optimum order of appearance we will sort the list of
                    // markers in the following way...
                    // - First Grouped by severity All Errors, All Warnings, All
                    // Infos.
                    // - Within these groups, group by XPD Validation
                    // Destination Id - with user-selectable destinations taking
                    // priority over internal destinations (if the marker does
                    // not have the XPD destination info in then it is treated
                    // as if it were user selectable and a name of "").
                    // - Then within this group, markers with resolutions are
                    // given priority.
                    //
                    // This way the most important and most useful problems are
                    // prioritised.
                    //
                    Collections.sort(markerList,
                            new Comparator<MarkerAndResolutions>() {

                                @Override
                                public int compare(MarkerAndResolutions o1,
                                        MarkerAndResolutions o2) {
                                    //
                                    // Group by severity.
                                    int dest1Sev = getSeverityValue(o1.marker);
                                    int dest2Sev = getSeverityValue(o2.marker);

                                    if (dest1Sev != dest2Sev) {
                                        return dest2Sev - dest1Sev;
                                    }

                                    //
                                    // Place selectables before unselectable
                                    // destinations (i.e. used
                                    // chosen destination problems above the
                                    // statutory ones like
                                    // BPMNB).
                                    String destId1 =
                                            o1.marker
                                                    .getAttribute(IIssue.DESTINATION_ID,
                                                            ""); //$NON-NLS-1$

                                    String destId2 =
                                            o2.marker
                                                    .getAttribute(IIssue.DESTINATION_ID,
                                                            ""); //$NON-NLS-1$

                                    Destination dest1 =
                                            ValidationActivator.getDefault()
                                                    .getDestination(destId1);
                                    Destination dest2 =
                                            ValidationActivator.getDefault()
                                                    .getDestination(destId2);

                                    boolean dest1Selectable = true;
                                    boolean dest2Selectable = true;

                                    if (dest1 != null) {
                                        dest1Selectable = dest1.isSelectable();
                                    }

                                    if (dest2 != null) {
                                        dest2Selectable = dest2.isSelectable();
                                    }

                                    if (dest1Selectable && !dest2Selectable) {
                                        return -1;
                                    }

                                    if (dest2Selectable && !dest1Selectable) {
                                        return 1;
                                    }

                                    //
                                    // Selectable state the same, group by dest
                                    // id.
                                    if (!destId1.equals(destId2)) {
                                        return destId2.compareTo(destId1);
                                    }

                                    //
                                    // Finally, place same level problems for
                                    // same destination environment, place them
                                    // first.
                                    Collection<IMarkerResolution> resolutions1 =
                                            o1.getResolutions();
                                    Collection<IMarkerResolution> resolutions2 =
                                            o2.getResolutions();

                                    if (!resolutions1.isEmpty()
                                            && resolutions2.isEmpty()) {
                                        return -1;
                                    }

                                    if (!resolutions2.isEmpty()
                                            && resolutions1.isEmpty()) {
                                        return 1;
                                    }

                                    return 0;
                                }

                                private int getSeverityValue(IMarker marker) {
                                    int severity =
                                            marker.getAttribute(IMarker.SEVERITY,
                                                    -1);
                                    if (severity == IMarker.SEVERITY_ERROR) {
                                        return 3;
                                    }
                                    if (severity == IMarker.SEVERITY_WARNING) {
                                        return 2;
                                    }
                                    if (severity == IMarker.SEVERITY_INFO) {
                                        return 1;
                                    }
                                    return 0;
                                }
                            });

                }

                if (!markerList.isEmpty()) {
                    currentMarker = markerList.get(0);

                    String msg =
                            markerList.get(0).marker
                                    .getAttribute(IMarker.MESSAGE, ""); //$NON-NLS-1$
                    Point e = getTextExtent(gc, msg);
                    if (e.x > optimumTitleWidth) {
                        optimumTitleWidth = e.x;
                    }
                }

                // for (MarkerAndResolutions m : markerList) {
                //                    String msg = m.marker.getAttribute(IMarker.MESSAGE, ""); //$NON-NLS-1$
                // Point e = getTextExtent(gc, msg);
                // if (e.x > optimumTitleWidth) {
                // optimumTitleWidth = e.x;
                // }
                // }

            } finally {
                // Make sure we dispose GC whatever happens.
                gc.dispose();

                if (activeShell != null) {
                    activeShell.setCursor(Display.getDefault()
                            .getSystemCursor(Cursor.DEFAULT_CURSOR));
                }
            }

            //
            // Reset the controls to show first marker and fixes.
            updateControls();
        }

        return;

    }

    /**
     * @param markers
     * 
     * @return List of at most MAX_MARKERS size with errors taking preference
     *         over warnings.
     */
    private List<MarkerAndResolutions> getMostImportant50Markers(
            Collection<IMarker> markers) {
        markerList = new ArrayList<MarkerAndResolutions>();

        List<IMarker> errors = new ArrayList<IMarker>();
        List<IMarker> warns = new ArrayList<IMarker>();
        List<IMarker> others = new ArrayList<IMarker>();

        for (IMarker marker : markers) {
            int severity = marker.getAttribute(IMarker.SEVERITY, -1);
            if (severity == IMarker.SEVERITY_ERROR) {
                errors.add(marker);
            } else if (severity == IMarker.SEVERITY_WARNING) {
                warns.add(marker);
            } else {
                others.add(marker);
            }
        }

        int count = 0;

        for (IMarker marker : errors) {
            if (++count > MAX_MARKERS) {
                break;
            }
            markerList.add(new MarkerAndResolutions(marker, input,
                    quickFixContentProvider));
        }

        if (count < MAX_MARKERS) {
            for (IMarker marker : warns) {
                if (++count > MAX_MARKERS) {
                    break;
                }
                markerList.add(new MarkerAndResolutions(marker, input,
                        quickFixContentProvider));
            }
        }

        if (count < MAX_MARKERS) {
            for (IMarker marker : others) {
                if (++count > MAX_MARKERS) {
                    break;
                }
                markerList.add(new MarkerAndResolutions(marker, input,
                        quickFixContentProvider));
            }
        }
        return markerList;
    }

    /**
     * @param gc
     * @param text
     * @return The size in pixels of given string.
     */
    private Point getTextExtent(GC gc, String text) {
        Point e;
        if (text == null || text.length() == 0) {
            e = new Point(0, gc.getFontMetrics().getHeight());
        } else {
            e = gc.textExtent(text);
        }
        return e;
    }

    /**
     * Reset the controls from the current markerList, the current marker and
     * it's resolutions.
     * <p>
     * First time after a setInput has been called then width is set according
     * to last set optimum width (set by setMarkers).
     * <p>
     * After this the size will be resest only in the vertical direction to
     * accomodate for more/less quick fixes.
     */
    private void updateControls() {
        GridData gd;

        Collection<IMarkerResolution> quickFixes = Collections.emptyList();
        if (currentMarker != null) {
            quickFixes = currentMarker.getResolutions();
        }

        updateMarkerDescription();

        /*
         * On first update after setinput adjust horizontal size as well as
         * vertical.
         */
        boolean expandForBiggerMsg = false;
        if (!firstUpdateAfterSetMarkers) {
            GC gc = new GC(markerDescription);
            try {
                String msg =
                        currentMarker.marker.getAttribute(IMarker.MESSAGE, ""); //$NON-NLS-1$
                Point e = getTextExtent(gc, msg);
                if (e.x > optimumTitleWidth) {
                    optimumTitleWidth = e.x;
                    expandForBiggerMsg = true;
                }

            } finally {
                gc.dispose();
            }
        }

        /*
         * Set Layout on first update after a set markers or if the optimum
         * width of new marker message is bigger than previous.
         */
        if (firstUpdateAfterSetMarkers || expandForBiggerMsg) {
            gd = new GridData(GridData.FILL_HORIZONTAL);
            if (optimumTitleWidth > 0) {
                gd.widthHint = Math.min(optimumTitleWidth + 20, 450);
            }
            markerDescription.setLayoutData(gd);

            //
            // Show hide prev/next
            if (markerList.size() < 2) {
                GridData tbGridData =
                        new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
                tbGridData.heightHint = 0;
                tbGridData.widthHint = 0;
                prevNextContainer.setLayoutData(tbGridData);
                prevNextContainer.setVisible(false);
            } else {
                GridData tbGridData =
                        new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
                prevNextContainer.setLayoutData(tbGridData);
                prevNextContainer.setVisible(true);
            }
            firstUpdateAfterSetMarkers = false;
        } else {

        }

        //
        // Remove any old quick fix hyperlinks.
        for (Control quickFixIcon : quickFixItemIcons) {
            quickFixIcon.dispose();
        }
        quickFixItemIcons = new ArrayList<Control>();

        for (FormText quickFixHyper : quickFixItemHyperLinks) {
            quickFixHyper.dispose();
        }

        quickFixItemHyperLinks = new ArrayList<FormText>();

        if ((quickFixes == null || quickFixes.isEmpty())) {
            //
            // No quick fixes, hide and collapse it if necessary.
            if (quickFixSection.getVisible()) {
                gd = new GridData(GridData.FILL_BOTH);
                gd.heightHint = 0;
                quickFixSection.setLayoutData(gd);

                quickFixSection.setVisible(false);
            }

            setQuickFixItemsHeaderText(0);

        } else {
            //
            // Add a new CLabel for each quick fix.
            for (IMarkerResolution quickFix : quickFixes) {
                String label = quickFix.getLabel();
                if (label != null) {
                    addQuickFixItem(currentMarker.marker, quickFix, label);
                }
            }

            gd = new GridData(GridData.FILL_BOTH);
            quickFixLabelContainer.setLayoutData(gd);
            quickFixLabelContainer.layout(true);
            gd = new GridData(GridData.FILL_BOTH);
            quickFixSection.setLayoutData(gd);

            quickFixSection.setVisible(true);

            setQuickFixItemsHeaderText(quickFixes.size());
        }

        //
        // Enable / disabled prev / next buttons.
        boolean prevEnabled = false;
        boolean nextEnabled = false;
        if (currentMarker != null && markerList.size() > 1) {
            int idx = markerList.indexOf(currentMarker);

            if (idx > 0) {
                prevEnabled = true;
            }

            if (idx < (markerList.size() - 1)) {
                nextEnabled = true;
            }
        }

        nextActionButton.setEnabled(nextEnabled);
        nextActionButton
                .setImage(nextEnabled ? (Image) nextActionButton
                        .getData(NEXTPREV_CURRENT_IMAGE)
                        : QuickFixToolTipActivator
                                .getDefault()
                                .getImage(QuickFixToolTipConstants.IMG_NEXTDISABLED_SMALL_ICON));

        prevActionButton.setEnabled(prevEnabled);
        prevActionButton
                .setImage(prevEnabled ? (Image) prevActionButton
                        .getData(NEXTPREV_CURRENT_IMAGE)
                        : QuickFixToolTipActivator
                                .getDefault()
                                .getImage(QuickFixToolTipConstants.IMG_PREVIOUSDISABLED_SMALL_ICON));

        //
        // Re-layout
        rootContainer.layout(true);

        if (resizeParentShellOnUpdate) {
            Point sz = parentShell.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
            parentShell.setSize(sz);
        }

        return;
    }

    private void updateMarkerDescription() {
        Image icon = null;
        String markerMessage = ""; //$NON-NLS-1$

        ImageRegistry imgReg =
                QuickFixToolTipActivator.getDefault().getImageRegistry();
        if (currentMarker != null) {

            int severity =
                    currentMarker.marker.getAttribute(IMarker.SEVERITY, -1);

            switch (severity) {
            case IMarker.SEVERITY_ERROR:
                icon = imgReg.get(QuickFixToolTipConstants.IMG_ERROR_MARKER);
                break;

            case IMarker.SEVERITY_WARNING:
                icon = imgReg.get(QuickFixToolTipConstants.IMG_WARNING_MARKER);
                break;

            case IMarker.SEVERITY_INFO:
                icon = imgReg.get(QuickFixToolTipConstants.IMG_INFO_MARKER);
                break;
            }

            markerMessage =
                    currentMarker.marker.getAttribute(IMarker.MESSAGE, ""); //$NON-NLS-1$

        }

        //
        // Update title section.
        markerIcon.setImage(icon);

        markerIcon.setToolTipText(getMarkerCountDescription());

        markerMessage = markerMessage.replaceAll("&", "&amp;"); //$NON-NLS-1$//$NON-NLS-2$
        markerMessage = markerMessage.replaceAll("<", "&lt;"); //$NON-NLS-1$ //$NON-NLS-2$
        markerMessage = markerMessage.replaceAll(">", "&gt;"); //$NON-NLS-1$ //$NON-NLS-2$

        if (markerMessage.trim().length() == 0) {
            /*
             * If the marker message is empty then this is normally because the
             * marker has been removed by a background validation job in between
             * getMarkers and display markers.
             */
            markerMessage =
                    Messages.QuickFixToolTipControl_ProblemMarkerNotAvailable_message;
        }

        /*
         * SCF-431: If the error marker is on the project, the marker resource
         * is not an IFile. And if we still leave the description as a
         * hyper-link and if the hyper-link is clicked, logs the exception when
         * IDE.openEditor() for hyper-link is called. So make it a simple text
         * and not hyper-link
         */
        boolean isFileResource = false;

        if (null != currentMarker && null != currentMarker.marker) {

            IResource markerResource = currentMarker.marker.getResource();
            if (markerResource instanceof IFile) {

                isFileResource = true;
            }
        }

        if (mouseInMarkerDescription && hyperlinkMarkerDescription
                && isFileResource) {
            /*
             * MR 42807: Parse out any special tags that might appear in marker
             * message.
             */
            String linkText =
                    "<form><p><a href = '" + "MARKER_DESCRIPTION_HYPERLINK" + "'>" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                            + markerMessage + "</a></p></form>"; //$NON-NLS-1$

            markerDescription.setText(linkText, true, false);
            markerDescription
                    .setToolTipText(Messages.QuickFixToolTipControl_GotoProblemLocation_tooltip);

        } else {
            /*
             * Parse out any special tags that might appear in marker message.
             */
            String linkText = "<form><p>" + markerMessage + "</p></form>"; //$NON-NLS-1$ //$NON-NLS-2$
            markerDescription.setText(linkText, true, false);
            markerDescription.setToolTipText(""); //$NON-NLS-1$
        }

        return;
    }

    /**
     * @return The markers count description (grouped by severity)
     */
    private String getMarkerCountDescription() {
        String desc = ""; //$NON-NLS-1$
        int errors = 0;
        int warnings = 0;
        int infos = 0;

        for (MarkerAndResolutions m : markerList) {
            int sev = m.marker.getAttribute(IMarker.SEVERITY, -1);

            if (sev == IMarker.SEVERITY_ERROR) {
                errors++;
            } else if (sev == IMarker.SEVERITY_WARNING) {
                warnings++;
            } else if (sev == IMarker.SEVERITY_INFO) {
                warnings++;
            }
        }

        if (errors > 0) {
            desc =
                    desc
                            + String.format(Messages.QuickFixToolTipControl_NumErrors_tooltip,
                                    errors);
        }

        if (warnings > 0) {
            if (desc.length() > 0) {
                desc = desc + "\n"; //$NON-NLS-1$
            }
            desc =
                    desc
                            + String.format(Messages.QuickFixToolTipControl_NumWarnings_tooltip,
                                    warnings);
        }

        if (infos > 0) {
            if (desc.length() > 0) {
                desc = desc + "\n"; //$NON-NLS-1$
            }
            desc =
                    desc
                            + String.format(Messages.QuickFixToolTipControl_NumInfos_tooltip,
                                    infos);
        }

        return desc;
    }

    /**
     * Add the controls to support the given individual quick fix item to the
     * quickFixLabelContainer
     * 
     * @param marker
     * @param quickFix
     * @param label
     */
    private void addQuickFixItem(IMarker marker, IMarkerResolution quickFix,
            String label) {
        Image quickFixIcon = null;
        String quickFixDesc = null;

        if (quickFix instanceof IMarkerResolution2) {
            IMarkerResolution2 res2 = (IMarkerResolution2) quickFix;

            quickFixIcon = res2.getImage();
            quickFixDesc = res2.getDescription();
        }

        if (quickFixIcon == null) {
            quickFixIcon =
                    QuickFixToolTipActivator.getDefault().getImageRegistry()
                            .get(QuickFixToolTipConstants.IMG_DEFAULT_QUICKFIX);
        }

        if (quickFixDesc == null) {
            quickFixDesc = label;
        }

        //
        // Icon button.
        Label quickFixItemIcon = new Label(quickFixLabelContainer, SWT.LEFT);
        quickFixItemIcons.add(quickFixItemIcon);
        quickFixItemIcon.setImage(quickFixIcon);
        Rectangle iconSz = quickFixIcon.getBounds();
        GridData iconLD = new GridData();
        iconLD.widthHint = iconSz.width;
        iconLD.heightHint = iconSz.height;
        quickFixItemIcon.setLayoutData(iconLD);

        quickFixItemIcon.setToolTipText(quickFixDesc);
        setDefaultControlAttributes(quickFixItemIcon);

        quickFixItemIcon.setCursor(Cursors.HAND);

        quickFixItemIcon.addMouseListener(quickFixIconClickListener);
        quickFixItemIcon.setData(QUICKFIX_LABEL_IMARKER, marker);
        quickFixItemIcon.setData(QUICKFIX_LABEL_IMARKERRESOLUTION, quickFix);

        //
        // Hyperlink.
        FormText quickFixHyper =
                new FormText(quickFixLabelContainer, SWT.WRAP | SWT.LEFT);
        quickFixItemHyperLinks.add(quickFixHyper);
        quickFixHyper.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        setDefaultControlAttributes(quickFixHyper);
        quickFixHyper.setData(QUICKFIX_LABEL_IMARKER, marker);
        quickFixHyper.setData(QUICKFIX_LABEL_IMARKERRESOLUTION, quickFix);

        /*
         * Parse out any special tags that might appear in marker message.
         */
        label = label.replaceAll("<", "&lt;"); //$NON-NLS-1$ //$NON-NLS-2$
        label = label.replaceAll(">", "&gt;"); //$NON-NLS-1$ //$NON-NLS-2$

        String linkText =
                "<form><p><a href = '" + QUICKFIX_LABEL_IMARKER + "'>" //$NON-NLS-1$ //$NON-NLS-2$
                        + label + "</a></p></form>"; //$NON-NLS-1$

        quickFixHyper.setText(linkText, true, false);
        quickFixHyper.setToolTipText(quickFixDesc);

        quickFixHyper.addHyperlinkListener(quickFixHyperLinkListener);
        return;
    }

    /**
     * execute the quick fix.
     * 
     * @param marker
     * @param quickFix
     */
    protected void executeQuickFix(IMarker marker, IMarkerResolution quickFix) {
        if (input != null) {
            quickFixContentProvider.executeQuickFix(input, marker, quickFix);
        }
        return;
    }

    /**
     * Select next problem marker.
     */
    private void gotoNextMarker() {
        if (currentMarker != null && markerList.size() > 1) {
            int idx = markerList.indexOf(currentMarker);
            if (idx < (markerList.size() - 1)) {
                currentMarker = markerList.get(idx + 1);
                updateControls();
            }
        }
        return;
    }

    /**
     * Select previous marker
     */
    private void gotoPrevMarker() {
        if (currentMarker != null && markerList.size() > 1) {
            int idx = markerList.indexOf(currentMarker);
            if (idx > 0) {
                currentMarker = markerList.get(idx - 1);
                updateControls();
            }
        }
        return;
    }

    /**
     * Create the controls
     * 
     * @param container
     */
    protected void createControls(Composite container) {
        //
        // root container is actually this control (done this way to make it
        // easier to change to a child composite later if necessary).
        rootContainer = new Composite(container, SWT.NONE);
        GridLayout gl = new GridLayout(1, false);
        gl.marginTop = 0;
        gl.marginHeight = 0;
        gl.marginWidth = 2;
        gl.verticalSpacing = 2;

        rootContainer.setLayout(gl);
        setDefaultControlAttributes(rootContainer);

        titleSection = createTitleControls(rootContainer);
        titleSection.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        quickFixSection = createQuickFixControls(rootContainer);
        // Initially there will be no quick fixes (we'll update them when we get
        // shown.
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.heightHint = 0;
        quickFixSection.setLayoutData(gd);
        quickFixSection.setVisible(false);

        return;
    }

    /**
     * Set the default colours for any tooltip child control.
     * 
     * @param c
     */
    protected void setDefaultControlAttributes(Control c) {
        c.setBackground(ColorConstants.tooltipBackground);
        c.setForeground(ColorConstants.tooltipForeground);

        c.addKeyListener(prevNextActionKeyListener);

        return;
    }

    /**
     * Create title area controls.
     * 
     * @param parent
     * @return the title area container
     */
    protected Composite createTitleControls(Composite parent) {
        Composite c = new Composite(parent, SWT.NONE);
        GridLayout gl = new GridLayout(4, false);
        gl.marginHeight = 0;
        gl.marginWidth = 0;
        gl.marginRight = -5;
        gl.verticalSpacing = 0;
        c.setLayout(gl);

        setDefaultControlAttributes(c);

        Composite iconAndActions = new Composite(c, SWT.NONE);
        gl = new GridLayout(1, false);
        gl.marginTop = 4;
        gl.marginHeight = 0;
        gl.marginWidth = 0;
        gl.verticalSpacing = 0;
        gl.horizontalSpacing = 0;
        iconAndActions.setLayout(gl);
        iconAndActions.setLayoutData(new GridData(
                GridData.VERTICAL_ALIGN_BEGINNING));

        setDefaultControlAttributes(iconAndActions);

        markerIcon = new Label(iconAndActions, SWT.NONE);
        markerIcon.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER
                | GridData.VERTICAL_ALIGN_BEGINNING));
        setDefaultControlAttributes(markerIcon);

        prevNextContainer = createPrevNextToolbar(iconAndActions);
        prevNextContainer.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_CENTER));

        /*
         * innerc is only there to reset the cursor to arrow (think formtext
         * defaults to it's parent container's set cursor
         */
        Composite innerc = new Composite(c, SWT.NONE);
        innerc.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.VERTICAL_ALIGN_BEGINNING));
        gl = new GridLayout(1, false);
        gl.marginHeight = 0;
        gl.marginWidth = 0;
        innerc.setLayout(gl);
        innerc.setCursor(Cursors.ARROW);
        setDefaultControlAttributes(innerc);

        markerDescription = new FormText(innerc, SWT.WRAP | SWT.LEFT);
        markerDescription.setCursor(Cursors.ARROW);

        markerDescription.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        setDefaultControlAttributes(markerDescription);

        markerDescription.setCursor(Cursors.HAND);
        markerDescription
                .addMouseTrackListener(markerDescriptionHyperLinkTrackListener);
        markerDescription
                .addHyperlinkListener(markerDescriptionHyperLinkClickListener);

        if (canDragParentShell) {
            WindowIconDragMoveHandler windowDragListener =
                    new WindowIconDragMoveHandler();
            markerIcon.setCursor(Cursors.SIZEALL);
            markerIcon.addMouseListener(windowDragListener);
            markerIcon.addMouseMoveListener(windowDragListener);

            c.addMouseListener(windowDragListener);
            c.addMouseMoveListener(windowDragListener);
            c.setCursor(Cursors.SIZEALL);
        }

        return c;
    }

    /**
     * Create our previous / next problem 'toolbar' - except it is actually two
     * Label control with which we mimic a toolbar. The main reason for this is
     * that swt toolbar spaces things out too much for our purposes.
     * 
     * @param iconAndActions
     * @return container for the toolbar
     */
    private Control createPrevNextToolbar(Composite parent) {
        Composite c = new Composite(parent, SWT.NONE);
        GridLayout gl = new GridLayout(4, false);
        gl.marginHeight = 0;
        gl.marginWidth = 0;
        gl.marginRight = 0;
        gl.marginLeft = 0;
        gl.verticalSpacing = 0;
        gl.horizontalSpacing = 0;
        c.setLayout(gl);
        c.setCursor(Cursors.ARROW);

        setDefaultControlAttributes(c);

        prevActionButton = new CLabel(c, SWT.NONE);
        prevActionButton.setEnabled(false);
        prevActionButton
                .setImage(QuickFixToolTipActivator
                        .getDefault()
                        .getImage(QuickFixToolTipConstants.IMG_PREVIOUSDISABLED_SMALL_ICON));
        Image nextSmallIcon =
                QuickFixToolTipActivator
                        .getDefault()
                        .getImage(QuickFixToolTipConstants.IMG_PREVIOUS_SMALL_ICON);

        prevActionButton.setLayoutData(new GridData());

        prevActionButton.setData(NEXTPREV_CURRENT_IMAGE, nextSmallIcon);
        setDefaultControlAttributes(prevActionButton);

        prevActionButton
                .addMouseTrackListener(new HotImageMouseTrackListener(
                        prevActionButton,
                        nextSmallIcon,
                        QuickFixToolTipActivator
                                .getDefault()
                                .getImage(QuickFixToolTipConstants.IMG_PREVIOUSHOT_SMALL_ICON)));
        prevActionButton.addMouseListener(new PrevNextMouseButtonMouseListener(
                false));
        prevActionButton
                .setToolTipText(Messages.QuickFixToolTipControl_Previous_tooltip);

        nextActionButton = new CLabel(c, SWT.NONE);
        nextActionButton.setEnabled(false);
        nextActionButton
                .setImage(QuickFixToolTipActivator
                        .getDefault()
                        .getImage(QuickFixToolTipConstants.IMG_NEXTDISABLED_SMALL_ICON));
        nextActionButton
                .setData(NEXTPREV_CURRENT_IMAGE,
                        QuickFixToolTipActivator
                                .getDefault()
                                .getImage(QuickFixToolTipConstants.IMG_NEXT_SMALL_ICON));

        nextActionButton.setLayoutData(new GridData());

        setDefaultControlAttributes(nextActionButton);
        nextActionButton
                .addMouseTrackListener(new HotImageMouseTrackListener(
                        nextActionButton,
                        QuickFixToolTipActivator
                                .getDefault()
                                .getImage(QuickFixToolTipConstants.IMG_NEXT_SMALL_ICON),
                        QuickFixToolTipActivator
                                .getDefault()
                                .getImage(QuickFixToolTipConstants.IMG_NEXTHOT_SMALL_ICON)));
        nextActionButton.addMouseListener(new PrevNextMouseButtonMouseListener(
                true));
        nextActionButton
                .setToolTipText(Messages.QuickFixToolTipControl_Next_tooltip);

        return c;
    }

    /**
     * Create quick fix area controls.
     * 
     * @param parent
     * @return the title area container
     */
    private Composite createQuickFixControls(Composite parent) {
        Composite c = new Composite(parent, SWT.NONE);
        GridLayout gl = new GridLayout(1, false);
        gl.marginBottom = 0;
        gl.marginHeight = 0;
        gl.verticalSpacing = 0;
        gl.marginWidth = 0;
        gl.marginTop = 0;
        c.setLayout(gl);

        setDefaultControlAttributes(c);

        Label separator = new Label(c, SWT.SEPARATOR | SWT.HORIZONTAL);
        GridData sepgd = new GridData(GridData.FILL_HORIZONTAL);
        sepgd.heightHint = 3;
        separator.setLayoutData(sepgd);
        setDefaultControlAttributes(separator);

        quickFixItemsHeader = new CLabel(c, SWT.NONE);
        quickFixItemsHeader
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        setQuickFixItemsHeaderText(0);
        setDefaultControlAttributes(quickFixItemsHeader);

        quickFixLabelContainer = new Composite(c, SWT.NONE);
        GridLayout qfGl = new GridLayout(2, false);
        qfGl.marginLeft = 12;
        qfGl.marginRight = 0;
        qfGl.marginWidth = 0;
        qfGl.marginHeight = 0;
        qfGl.verticalSpacing = 2;
        qfGl.marginTop = 0;

        quickFixLabelContainer.setLayout(qfGl);
        setDefaultControlAttributes(quickFixLabelContainer);
        GridData gd = new GridData(GridData.FILL_BOTH);
        quickFixLabelContainer.setLayoutData(gd);

        if (canDragParentShell) {
            WindowIconDragMoveHandler windowDragListener =
                    new WindowIconDragMoveHandler();

            c.addMouseListener(windowDragListener);
            c.addMouseMoveListener(windowDragListener);
            c.setCursor(Cursors.SIZEALL);

            quickFixItemsHeader.addMouseListener(windowDragListener);
            quickFixItemsHeader.addMouseMoveListener(windowDragListener);
            quickFixItemsHeader.setCursor(Cursors.SIZEALL);

            quickFixLabelContainer.setCursor(Cursors.ARROW);
        }

        return c;
    }

    /**
     * Set the header text for the list of quick fixes ('n' quick fixes
     * available)
     */
    private void setQuickFixItemsHeaderText(int numQuickFixes) {

        if (numQuickFixes == 1) {
            quickFixItemsHeader
                    .setText(Messages.QuickFixToolTipControl_OneQuickFixAvailable_label);
        } else {
            String numQFMsg =
                    Messages.QuickFixToolTipControl_NumQuickFixesFormatMsg_label;
            quickFixItemsHeader.setText(String.format(numQFMsg, numQuickFixes));
        }
        return;
    }

    /**
     * Mouse listener that controls click drag on any control and moves the
     * parent shell.
     * 
     * @author aallway
     * @since 3.2
     */
    private class WindowIconDragMoveHandler implements MouseListener,
            MouseMoveListener {
        private boolean dragStarted = false;

        private Point startDragOffset = null;

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            if (parentShell != null && !parentShell.isDisposed()
                    && e.widget instanceof Control) {
                ((Control) e.widget).setCapture(true);
                dragStarted = true;

                Point popupLoc = parentShell.getLocation();

                Point mouseLoc = ((Control) e.widget).toDisplay(e.x, e.y);

                startDragOffset =
                        new Point(mouseLoc.x - popupLoc.x, mouseLoc.y
                                - popupLoc.y);
            }
        }

        @Override
        public void mouseUp(MouseEvent e) {
            if (dragStarted) {
                dragStarted = false;
                if (parentShell != null && !parentShell.isDisposed()
                        && e.widget instanceof Control) {
                    ((Control) e.widget).setCapture(false);
                }
            }
        }

        @Override
        public void mouseMove(MouseEvent e) {
            if (dragStarted) {
                if (parentShell != null && !parentShell.isDisposed()
                        && e.widget instanceof Control) {
                    Point mouseLoc = ((Control) e.widget).toDisplay(e.x, e.y);
                    Point popupLoc =
                            new Point(mouseLoc.x - startDragOffset.x,
                                    mouseLoc.y - startDragOffset.y);
                    parentShell.setLocation(popupLoc);
                }
            }
        }

    }

    /**
     * Data class for a marker and it's resoltions (makes sort by severity +
     * hasResolutions more efficient.
     * 
     * @author aallway
     * @since 3.2
     */
    private static class MarkerAndResolutions {
        private IMarker marker;

        private Collection<IMarkerResolution> resolutions = null;

        private Object input;

        private QuickFixPopupContentProvider quickFixContentProvider;

        MarkerAndResolutions(IMarker marker, Object input,
                QuickFixPopupContentProvider quickFixContentProvider) {
            this.marker = marker;
            this.input = input;
            this.quickFixContentProvider = quickFixContentProvider;
        }

        /**
         * @return the resolutions
         */
        public Collection<IMarkerResolution> getResolutions() {
            if (resolutions == null) {
                resolutions =
                        quickFixContentProvider.getResolutions(input, marker);
                if (resolutions == null) {
                    resolutions = Collections.emptyList();
                }
            }

            return resolutions;
        }
    }

    /**
     * The mouse motion listener that tracks mouse over the prev/next button and
     * displays the 'hot' image when mouse is over the icon.
     * 
     * @author aallway
     * @since 3.4.2 (3 Aug 2010)
     */
    private static class HotImageMouseTrackListener extends MouseTrackAdapter {
        private Image normal;

        private Image hot;

        private CLabel icon;

        /**
         * @param normal
         * @param hot
         */
        public HotImageMouseTrackListener(CLabel icon, Image normal, Image hot) {
            super();
            this.icon = icon;
            this.normal = normal;
            this.hot = hot;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.events.MouseTrackAdapter#mouseEnter(org.eclipse.swt
         * .events.MouseEvent)
         */
        @Override
        public void mouseEnter(MouseEvent e) {
            if (!icon.isDisposed()) {
                if (icon.isEnabled()) {
                    icon.setData(NEXTPREV_CURRENT_IMAGE, hot);
                    icon.setImage(hot);
                }
            }
            return;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.events.MouseTrackAdapter#mouseExit(org.eclipse.swt
         * .events.MouseEvent)
         */
        @Override
        public void mouseExit(MouseEvent e) {
            if (!icon.isDisposed()) {
                if (icon.isEnabled()) {
                    icon.setData(NEXTPREV_CURRENT_IMAGE, normal);
                    icon.setImage(normal);
                }
            }
            return;
        }

    }

    /**
     * Handles previous/next problem marker action when user clicks on the
     * prev/next CLabel icon.
     * 
     * 
     * @author aallway
     * @since 3.4.2 (3 Aug 2010)
     */
    private class PrevNextMouseButtonMouseListener extends MouseAdapter {
        private boolean isNext = false;

        /**
         * @param isNext
         */
        public PrevNextMouseButtonMouseListener(boolean isNext) {
            super();
            this.isNext = isNext;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.events.MouseAdapter#mouseUp(org.eclipse.swt.events
         * .MouseEvent)
         */
        @Override
        public void mouseUp(MouseEvent e) {
            if (isNext) {
                nextActionButton.setImage((Image) nextActionButton
                        .getData(NEXTPREV_CURRENT_IMAGE));
                gotoNextMarker();
            } else {
                prevActionButton.setImage((Image) prevActionButton
                        .getData(NEXTPREV_CURRENT_IMAGE));
                gotoPrevMarker();
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.events
         * .MouseEvent)
         */
        @Override
        public void mouseDown(MouseEvent e) {
            if (isNext) {
                nextActionButton
                        .setImage(QuickFixToolTipActivator
                                .getDefault()
                                .getImage(QuickFixToolTipConstants.IMG_NEXTDOWN_SMALL_ICON));
            } else {
                prevActionButton
                        .setImage(QuickFixToolTipActivator
                                .getDefault()
                                .getImage(QuickFixToolTipConstants.IMG_PREVIOUSDOWN_SMALL_ICON));
            }
        }
    }

}