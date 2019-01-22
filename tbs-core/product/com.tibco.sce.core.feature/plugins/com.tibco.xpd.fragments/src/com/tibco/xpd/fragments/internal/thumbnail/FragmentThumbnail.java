/**
 * FragmentThumbnail.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2007
 */
package com.tibco.xpd.fragments.internal.thumbnail;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Widget;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.internal.FragmentsViewPart;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.resources.logger.Logger;

/**
 * FragmentThumbnail
 * 
 * Fragment thumbnail control.
 */
public class FragmentThumbnail extends Composite {

    private static final Logger LOG = FragmentsActivator.getDefault()
            .getLogger();

    public static final int IMAGE_BORDER_MARGIN = 2;

    private FragmentsViewPart fragmentsViewPart;

    private IFragment fragment;

    private FragmentFigureCanvas fragmentCanvas = null;

    private Composite imgBorder = null;

    private Label textLabel = null;

    private MouseListener clickSelectListener = null;

    private List<MouseListener> mouseListeners = new ArrayList<MouseListener>();

    /**
     * Create a fragment thumbnail (fragment image canvas + text label) - also
     * handles mouse listening etc
     * 
     * @param parent
     * @param style
     * @param viewPart
     *            fragments view part is drag should be supported,
     *            <code>null</code> if no drag should be supported.
     * @param fragment
     * @param isCategoryView
     *            <code>true</code> if this thumbnail is shown in category view
     *            - this will add a label under each thumbnail.
     * @param contextMenu
     *            context menu to add to the thumbnail, <code>null</code> if no
     *            menu required.
     */
    public FragmentThumbnail(Composite parent, int style,
            FragmentsViewPart viewPart, IFragment fragment,
            boolean isCategoryView) {
        super(parent, style);

        this.fragmentsViewPart = viewPart;
        this.fragment = fragment;

        clickSelectListener = new MouseListener() {
            @Override
            public void mouseDoubleClick(MouseEvent e) {
                fireMouseDoubleClick(e);
            }

            @Override
            public void mouseDown(MouseEvent e) {
                fireMouseDown(e);
            }

            @Override
            public void mouseUp(MouseEvent e) {
                fireMouseUp(e);
            }
        };

        setBackground(getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));

        // Setup the layout info.
        GridLayout gridLayout = new GridLayout(1, false);
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        gridLayout.verticalSpacing = 3;
        setLayout(gridLayout);

        createImageControl();
        if (isCategoryView) {
            // Show label in category view
            createLabelControl();
        }
    }

    /**
     * @return the fragment definition
     */
    public IFragment getFragment() {
        return fragment;
    }

    /**
     * @return the fragmentCanvas
     */
    public FragmentFigureCanvas getFragmentCanvas() {
        return fragmentCanvas;
    }

    /**
     * Set the height hint for the label.
     * 
     * @param hHint
     */
    public void setLabelHeightHint(int hHint) {
        if (textLabel != null) {
            GridData gd = (GridData) textLabel.getLayoutData();
            gd.heightHint = hHint;
        }
    }

    /**
     * Compute the label height to get the max. wrapped height.
     * 
     * @param preferedWidth
     * @return
     */
    public int computeLabelHeight(int preferedWidth) {
        int y = 0;
        if (textLabel != null) {
            Point sz = textLabel.computeSize(preferedWidth, SWT.DEFAULT);
            y = sz.y;
        }

        return y;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.widgets.Control#setToolTipText(java.lang.String)
     */
    @Override
    public void setToolTipText(String tip) {
        if (textLabel != null) {
            textLabel.setToolTipText(tip);
        }
        fragmentCanvas.setToolTipText(tip);

        super.setToolTipText(tip);
    }

    /**
     * Set selected state of control WITHOUT NOTIFYING LISTENERS!
     * 
     * @param isSelected
     *            the isSelected to set
     */
    public void setSelected(boolean isSelected, boolean focused) {
        if (!isDisposed()) {

            // this.isSelected = isSelected;

            Color borderColor;
            Color textColor;
            Color textBgColor;

            if (isSelected) {
                if (!focused) {
                    borderColor =
                            getDisplay()
                                    .getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
                    textBgColor =
                            getDisplay()
                                    .getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
                    textColor =
                            getDisplay()
                                    .getSystemColor(SWT.COLOR_LIST_FOREGROUND);
                } else {
                    borderColor =
                            getDisplay()
                                    .getSystemColor(SWT.COLOR_LIST_SELECTION);
                    textBgColor =
                            getDisplay()
                                    .getSystemColor(SWT.COLOR_LIST_SELECTION);
                    textColor =
                            getDisplay()
                                    .getSystemColor(SWT.COLOR_LIST_SELECTION_TEXT);
                }

            } else {
                borderColor =
                        getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
                textBgColor =
                        getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
                textColor =
                        getDisplay().getSystemColor(SWT.COLOR_LIST_FOREGROUND);
            }

            imgBorder.setBackground(borderColor);
            if (textLabel != null) {
                textLabel.setBackground(textBgColor);
                textLabel.setForeground(textColor);
            }
        }

        return;
    }

    /**
     * Add a listener for selections.
     * 
     * @param listener
     */
    @Override
    public void addMouseListener(MouseListener listener) {
        mouseListeners.add(listener);

        super.addMouseListener(listener);
    }

    /**
     * Remove a listener for selections.
     * 
     * @param listener
     */
    @Override
    public void removeMouseListener(MouseListener listener) {
        mouseListeners.remove(listener);

        super.removeMouseListener(listener);
    }

    @Override
    public void dispose() {
        // Don't own the menu so don't want to dispose
        if (fragmentCanvas != null && !fragmentCanvas.isDisposed()) {
            // Don't dispose the context menu as it is shared with the fragment
            // thumbnails
            fragmentCanvas.dispose();
            fragmentCanvas = null;
        }
        super.dispose();
    }

    @Override
    public void setMenu(Menu menu) {
        if (!isDisposed()) {
            super.setMenu(menu);

            if (fragmentCanvas != null && !fragmentCanvas.isDisposed()) {
                fragmentCanvas.setMenu(menu);
            }

            if (textLabel != null && !textLabel.isDisposed()) {
                textLabel.setMenu(menu);
            }
        }
    }

    /**
     * Create the image part of the thumb nail.
     * 
     * @param fragment
     * @param container
     * @return
     */
    private FragmentFigureCanvas createImageControl() {

        imgBorder = new Composite(this, SWT.BORDER);
        imgBorder.setLayoutData(new GridData(GridData.FILL_BOTH));

        /*
         * <XPD-4173> tmp debugging added to understand occasional exception
         * coming from below getDisplay() calls
         */
        try {
            getDisplay();
        } catch (SWTException e) {
            String errFmt =
                    "Could not obtain Display from FragmentThumbnail widget. isDisposed() = %s; Display.getCurrent() = %s"; //$NON-NLS-1$
            LOG.error(e,
                    String.format(errFmt, isDisposed(), Display.getCurrent()));
        }
        /* </XPD-4173> */

        imgBorder.setBackground(getDisplay()
                .getSystemColor(SWT.COLOR_LIST_BACKGROUND));

        GridLayout ctlay = new GridLayout(1, false);
        ctlay.marginHeight = IMAGE_BORDER_MARGIN;
        ctlay.marginWidth = IMAGE_BORDER_MARGIN;
        imgBorder.setLayout(ctlay);

        fragmentCanvas =
                new FragmentFigureCanvas(imgBorder, SWT.NONE, fragment,
                        fragmentsViewPart);

        fragmentCanvas.setBackground(getDisplay()
                .getSystemColor(SWT.COLOR_LIST_BACKGROUND));

        GridData gridData = new GridData(GridData.FILL_BOTH);
        fragmentCanvas.setLayoutData(gridData);

        // Add Mouse listener for selection purposes.
        fragmentCanvas.addMouseListener(clickSelectListener);

        return fragmentCanvas;
    }

    /**
     * @param fragment
     * @param container
     */
    private Label createLabelControl() {

        textLabel = new Label(this, SWT.WRAP | SWT.CENTER);

        String name = fragment.getNameLabel();
        if (name == null || name.length() == 0) {
            name = Messages.FragmentThumbnail_noName_label;
        }
        textLabel.setText(name);

        textLabel.setBackground(getDisplay()
                .getSystemColor(SWT.COLOR_LIST_BACKGROUND));

        textLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // Add Mouse listener for selection purposes.
        textLabel.addMouseListener(clickSelectListener);

        return textLabel;
    }

    private void fireMouseDown(MouseEvent e) {
        Widget oldw = e.widget;
        e.widget = this;

        for (MouseListener listener : mouseListeners) {
            listener.mouseDown(e);
        }

        e.widget = oldw;
    }

    private void fireMouseUp(MouseEvent e) {
        Widget oldw = e.widget;
        e.widget = this;

        for (MouseListener listener : mouseListeners) {
            listener.mouseUp(e);
        }

        e.widget = oldw;
    }

    private void fireMouseDoubleClick(MouseEvent e) {
        Widget oldw = e.widget;
        e.widget = this;

        for (MouseListener listener : mouseListeners) {
            listener.mouseDoubleClick(e);
        }

        e.widget = oldw;
    }

}
