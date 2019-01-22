/**
 * ButtonStackPaletteEditPart.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.gmf.extensions.palette;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.AbstractLayout;
import org.eclipse.draw2d.ActionEvent;
import org.eclipse.draw2d.ActionListener;
import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.ButtonBorder;
import org.eclipse.draw2d.ButtonGroup;
import org.eclipse.draw2d.ButtonModel;
import org.eclipse.draw2d.Clickable;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.ToggleButton;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerPreferences;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.gmf.extensions.XpdGmfExtensionsPlugin;
import com.tibco.xpd.gmf.extensions.internal.Messages;

/**
 * ButtonStackPaletteEditPart
 * 
 */
public class ButtonStackPaletteEditPart extends AbstractGraphicalEditPart {

    /**
     * Small class representing the data associated with an individual palette
     * entry (button) within the button stack
     */
    private class ChildDataEntry {
        AbstractGraphicalEditPart editPart;

        ButtonModel btnModel;

        String label;

        String description;

        Image smallIcon;

        Image largeIcon;

        public void dispose() {
            if (smallIcon != null) {
                smallIcon.dispose();
                smallIcon = null;
            }

            if (largeIcon != null) {
                largeIcon.dispose();
                largeIcon = null;
            }

        }
    }

    /**
     * ButtonStackPaletteFigure
     * 
     */
    private final class ButtonStackPaletteFigure extends Figure {
        public Label labelFigure;

        Dimension labelMaxSize;

        private IFigure btnContainer;

        Dimension btnContainerSize;

        private ToggleButton pinButton;

        private static final int LABEL_SPACER = 16;

        private static final int MIN_TEXT_WIDTH = 48;

        private static final int VERTICAL_SPACER = 2;

        private static final int HORIZONTAL_SPACER = 3;

        private static final int EXTRA_BOTTOM_MARGIN = 2;

        private static final int VERTICAL_LABEL_MARGIN = 3;

        private boolean mouseIn = false;

        int flags = 0;

        /**
         * 
         */
        public ButtonStackPaletteFigure() {
            labelFigure = new Label() {
                /*
                 * (non-Javadoc)
                 * 
                 * @see org.eclipse.draw2d.Figure#getToolTip()
                 */
                public IFigure getToolTip() {
                    if (curSelection != null) {
                        return curSelection.editPart.getFigure().getToolTip();
                    }
                    return null;
                }
            };

            labelFigure.setLabelAlignment(PositionConstants.LEFT);
            if (getPreferenceSource().getLayoutSetting() == PaletteViewerPreferences.LAYOUT_COLUMNS) {
                labelFigure.setLabelAlignment(PositionConstants.CENTER);
                labelFigure.setTextAlignment(PositionConstants.CENTER);
                labelFigure.setTextPlacement(PositionConstants.SOUTH);
            }
            
            add(labelFigure);

            pinButton = new ToggleButton(new ImageFigure(PIN_IMAGE));
            pinButton.setBorder(BUTTON_BORDER);
            // pinButton.setRolloverEnabled(true);
            pinButton.setRequestFocusEnabled(false);
            pinButton.setToolTip(new Label(
                    Messages.PaletteButtonStackEditPart_PinOpen_tooltip));
            pinButton.setVisible(false);

            boolean initialPinState = getInitialPinState();

            pinButton.getModel().setSelected(initialPinState);
            add(pinButton);

            pinButton.getModel().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    mainControlSelect();

                    storeInitialPinState(pinButton.getModel().isSelected());
                }
            });

            // Add property change listener to ensure button container made
            // visible when change to icons only view.
            getPreferenceSource().addPropertyChangeListener(
                    new PropertyChangeListener() {
                        public void propertyChange(PropertyChangeEvent evt) {
                            if (getPreferenceSource().getLayoutSetting() == PaletteViewerPreferences.LAYOUT_ICONS) {
                                btnContainer.setVisible(true);
                            } else {

                                if (getPreferenceSource().getLayoutSetting() == PaletteViewerPreferences.LAYOUT_COLUMNS) {
                                    labelFigure
                                            .setLabelAlignment(PositionConstants.CENTER);
                                    labelFigure
                                            .setTextAlignment(PositionConstants.CENTER);
                                    labelFigure
                                            .setTextPlacement(PositionConstants.SOUTH);
                                } else {
                                    labelFigure
                                            .setLabelAlignment(PositionConstants.LEFT);
                                    labelFigure
                                            .setTextAlignment(PositionConstants.CENTER);
                                    labelFigure
                                            .setTextPlacement(PositionConstants.EAST);
                                }
                            }

                            // Reset current icon - this is purely to get Icon
                            // reset in case it has changed size.
                            if (curSelection != null) {
                                // Reset the data in our label.
                                Image img;
                                if (getPreferenceSource().useLargeIcons()) {
                                    img = curSelection.largeIcon;
                                } else {
                                    img = curSelection.smallIcon;
                                }

                                labelFigure.setIcon(img);
                            }

                        }
                    });

            btnContainer = new Figure();

            btnContainer.setOpaque(false);
            if (!(getPreferenceSource().getLayoutSetting() == PaletteViewerPreferences.LAYOUT_ICONS)
                    && !initialPinState) {
                btnContainer.setVisible(false);
            }
            add(btnContainer);

            btnContainer.setLayoutManager(new AbstractLayout() {
                /*
                 * (non-Javadoc)
                 * 
                 * @see org.eclipse.draw2d.AbstractLayout#getMinimumSize(org.eclipse.draw2d.IFigure,
                 *      int, int)
                 */
                public Dimension getMinimumSize(IFigure container, int wHint,
                        int hHint) {
                    // Minimum size is 'one column' of children.
                    int width = 0;
                    int height = 0;
                    for (Iterator iter = container.getChildren().iterator(); iter
                            .hasNext();) {
                        IFigure child = (IFigure) iter.next();

                        Dimension sz;

                        if (getPreferenceSource().getLayoutSetting() == PaletteViewerPreferences.LAYOUT_COLUMNS) {
                            Rectangle rc = labelFigure.getIconBounds()
                                    .getCopy();
                            sz = new Dimension(rc.width + 6, rc.height + 6);

                        } else {
                            sz = child.getPreferredSize(SWT.DEFAULT,
                                    SWT.DEFAULT).getCopy();
                        }

                        if (sz.width > width) {
                            width = sz.width;
                        }
                        height += sz.height;
                    }
                    return new Dimension(width, height);
                }

                /**
                 * Preferred size when no default width is provided is all child
                 * tools layed out horizontally. Otherwise it is all child tools
                 * stacked.
                 */
                protected Dimension calculatePreferredSize(IFigure container,
                        int wHint, int hHint) {

                    // Calculate preferred horizontal size.
                    Dimension allHorzSize = new Dimension(0, 0);
                    for (Iterator iter = container.getChildren().iterator(); iter
                            .hasNext();) {
                        IFigure child = (IFigure) iter.next();

                        Dimension sz;

                        if (getPreferenceSource().getLayoutSetting() == PaletteViewerPreferences.LAYOUT_COLUMNS) {
                            Rectangle rc = labelFigure.getIconBounds()
                                    .getCopy();
                            sz = new Dimension(rc.width + 6, rc.height + 6);

                        } else {
                            sz = child.getPreferredSize(SWT.DEFAULT,
                                    SWT.DEFAULT).getCopy();
                        }

                        if (sz.height > allHorzSize.height) {
                            allHorzSize.height = sz.height;
                        }
                        allHorzSize.width += sz.width;
                    }

                    // If given a specific width to fit to and it's smaller than
                    // the preferred 'all horizontal' layout then calculate best
                    // fit.
                    if (wHint != SWT.DEFAULT && wHint < allHorzSize.width) {
                        int maxRowWidth = 0;
                        int curRowWidth = 0;
                        int curRowHeight = 0;
                        int height = 0;

                        for (Iterator iter = container.getChildren().iterator(); iter
                                .hasNext();) {
                            IFigure child = (IFigure) iter.next();

                            Dimension sz;

                            if (getPreferenceSource().getLayoutSetting() == PaletteViewerPreferences.LAYOUT_COLUMNS) {
                                Rectangle rc = labelFigure.getIconBounds()
                                        .getCopy();
                                sz = new Dimension(rc.width + 6, rc.height + 6);

                            } else {
                                sz = child.getPreferredSize(SWT.DEFAULT,
                                        SWT.DEFAULT).getCopy();
                            }

                            if ((curRowWidth + sz.width) > wHint
                                    && curRowWidth != 0) {
                                // We've overrun suggested width AND this isn't
                                // the first child on row.

                                // Move down a row.
                                height += curRowHeight;

                                // Kee track of widest row.
                                if (curRowWidth > maxRowWidth) {
                                    maxRowWidth = curRowWidth;
                                }

                                // Allow space for child on start of next row.
                                curRowWidth = sz.width;
                                curRowHeight = sz.height;

                            } else {
                                // There's enough space for child on current row
                                // OR it is first child on row.
                                // So accumulate space for it.
                                curRowWidth += sz.width;

                                // Keep track of highest child in row.
                                if (sz.height > curRowHeight) {
                                    curRowHeight = sz.height;
                                }
                            }
                        }

                        // Add height for last row.
                        height += curRowHeight;

                        return new Dimension(maxRowWidth, height);
                    }

                    // Return preferred 'all horizontal' width IF
                    // no width hint supplied or width hint supplied
                    // and it's big enough for all horizontal.
                    return allHorzSize;
                }

                /*
                 * (non-Javadoc)
                 * 
                 * @see org.eclipse.draw2d.AbstractLayout#getPreferredSize(org.eclipse.draw2d.IFigure,
                 *      int, int)
                 */
                public Dimension getPreferredSize(IFigure container, int wHint,
                        int hHint) {
                    return calculatePreferredSize(container, wHint, hHint);
                }

                public void layout(IFigure container) {
                    Rectangle ca = container.getClientArea().getCopy();

                    boolean isColumns = (getPreferenceSource()
                            .getLayoutSetting() == PaletteViewerPreferences.LAYOUT_COLUMNS);

                    // Set location and size of all the child buttons.
                    int curRowWidth = 0;
                    int curRowHeight = 0;
                    int height = 0;

                    List childrenInRow = new ArrayList();

                    for (Iterator iter = container.getChildren().iterator(); iter
                            .hasNext();) {
                        IFigure child = (IFigure) iter.next();

                        Dimension sz;
                        if (isColumns) {
                            Rectangle rc = labelFigure.getIconBounds()
                                    .getCopy();
                            sz = new Dimension(rc.width + 6, rc.height + 6);

                        } else {
                            sz = child.getPreferredSize(SWT.DEFAULT,
                                    SWT.DEFAULT).getCopy();
                        }

                        // Keep track of highest child in row.
                        if (sz.height > curRowHeight) {
                            curRowHeight = sz.height;
                        }

                        if ((curRowWidth + sz.width) > ca.width
                                && curRowWidth != 0) {
                            // We've overrun suggested width AND this isn't
                            // the first child on row.

                            // Before moving down a row - centre the
                            // current row.
                            if (isColumns) {
                                int extraX = (ca.width - curRowWidth) / 2;
                                if (extraX > 0) {
                                    for (Iterator iterator = childrenInRow
                                            .iterator(); iterator.hasNext();) {
                                        IFigure rowChild = (IFigure) iterator
                                                .next();

                                        Rectangle loc = rowChild.getBounds();

                                        rowChild.setLocation(new Point(loc.x
                                                + extraX, loc.y));
                                    }
                                }
                            }
                            childrenInRow.clear();

                            // Move down a row.
                            height += curRowHeight;
                            curRowHeight = 0;
                            curRowWidth = 0;

                        }

                        child.setLocation(new Point(ca.x + curRowWidth, ca.y
                                + height));
                        child.setSize(sz);

                        curRowWidth += sz.width;

                        childrenInRow.add(child);
                    }

                    // Re-centre final row.
                    if (isColumns) {
                        int extraX = (ca.width - curRowWidth) / 2;
                        if (extraX > 0) {
                            for (Iterator iterator = childrenInRow.iterator(); iterator
                                    .hasNext();) {
                                IFigure rowChild = (IFigure) iterator.next();

                                Rectangle loc = rowChild.getBounds();

                                rowChild.setLocation(new Point(loc.x + extraX,
                                        loc.y));
                            }
                        }
                    }

                }
            }); // End of layout for button container.

            addMouseMotionListener(new MouseMotionListener.Stub() {
                public void mouseEntered(MouseEvent me) {
                    if (!mouseIn) {
                        mouseIn = true;
                        if (btnGroup.getSelected() == null) {
                            doRepaint();
                        }
                    }
                }

                public void mouseExited(MouseEvent me) {
                    if (mouseIn) {
                        mouseIn = false;
                        // autoShowButtons(false);
                        doRepaint();
                    }
                }
            });
        }

        /**
         * Show or hide the buttons. Buttons are ONLY hidden IF the main control
         * is not pinned open.
         * 
         * @param show
         */
        public void showHideButtons(boolean show) {
            if (!pinButton.getModel().isSelected()) {
                if (show
                        || !(getPreferenceSource().getLayoutSetting() == PaletteViewerPreferences.LAYOUT_ICONS)) {
                    // Never hide if in icons only mode.
                    btnContainer.setVisible(show);
                    getViewer().getRootEditPart().refresh();
                }
            }
        }

        public void doRepaint() {
            repaint();
        }

        public void setLabelInfo(String name, String description, Image image) {
            labelFigure.setText(name);
            labelFigure.setIcon(image);
            doRepaint();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.draw2d.Figure#add(org.eclipse.draw2d.IFigure,
         *      java.lang.Object, int)
         */
        public void add(IFigure figure, Object constraint, int index) {
            // Redirect of child adds to button container.
            // Make sure our label remains at start of list of children.
            if (figure != labelFigure && figure != btnContainer
                    && figure != pinButton && !(figure instanceof ImageFigure)) {
                btnContainer.add(figure, constraint, index);
            } else {
                super.add(figure, constraint, index);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
         */
        protected void paintFigure(Graphics graphics) {
            // If any of our child controls are selected then fill the
            // checked background as if we are.

            if (mainControlPaintSelected() || pinButton.getModel().isSelected()) {
                if (mainControlPaintSelected()) {
                    fillCheckeredRectangle(graphics);
                }

                // Draw border around item.
                Rectangle bnds = getClientArea().getCopy();
                bnds.width -= 1;
                bnds.height -= 1;
                graphics.setForegroundColor(ColorConstants.buttonDarkest);
                graphics.drawLine(bnds.x, bnds.y, bnds.x + bnds.width, bnds.y);
                graphics.drawLine(bnds.x, bnds.y, bnds.x, bnds.y + bnds.height);

                graphics.setForegroundColor(ColorConstants.buttonLightest);
                graphics.drawLine(bnds.x, bnds.y + bnds.height, bnds.x
                        + bnds.width, bnds.y + bnds.height);
                graphics.drawLine(bnds.x + bnds.width, bnds.y, bnds.x
                        + bnds.width, bnds.y + bnds.height);

            } else {
                graphics.setBackgroundColor(getBackgroundColor());
                graphics.fillRectangle(getBounds());

                if (mouseIn) {
                    Rectangle bnds = getClientArea().getCopy();
                    bnds.width -= 1;
                    bnds.height -= 1;
                    graphics.setForegroundColor(ColorConstants.buttonLightest);
                    graphics.drawLine(bnds.x, bnds.y, bnds.x + bnds.width,
                            bnds.y);
                    graphics.drawLine(bnds.x, bnds.y, bnds.x, bnds.y
                            + bnds.height);

                    graphics.setForegroundColor(ColorConstants.buttonDarkest);
                    graphics.drawLine(bnds.x, bnds.y + bnds.height, bnds.x
                            + bnds.width, bnds.y + bnds.height);
                    graphics.drawLine(bnds.x + bnds.width, bnds.y, bnds.x
                            + bnds.width, bnds.y + bnds.height);

                }
            }

            // Draw around selected button item to highlight it
            // (it is transparent)
            if (curSelection.editPart instanceof AbstractGraphicalEditPart) {
                IFigure fig = ((AbstractGraphicalEditPart) curSelection.editPart)
                        .getFigure();
                if (fig != null && btnContainer.isVisible()) {
                    Rectangle childBnds = fig.getBounds().getCopy();
                    fig.translateToParent(childBnds);
                    childBnds.x -= 1;
                    childBnds.y -= 1;
                    childBnds.width += 1;
                    childBnds.height += 1;

                    graphics.setForegroundColor(ColorConstants.buttonDarkest);
                    graphics.drawRectangle(childBnds);
                }
            }
            graphics.restoreState();
        }

        /**
         * Fill background of selected tool button.
         * 
         * @param graphics
         */
        private void fillCheckeredRectangle(Graphics graphics) {
            graphics.setBackgroundColor(ColorConstants.button);
            graphics.setForegroundColor(ColorConstants.buttonLightest);
            Rectangle rect = getClientArea(Rectangle.SINGLETON).crop(
                    new Insets(2, 2, 2, 2));
            graphics.fillRectangle(rect.x, rect.y, rect.width, rect.height);

            graphics.clipRect(rect);
            graphics.translate(rect.x, rect.y);
            int n = rect.width + rect.height;
            for (int i = 1; i < n; i += 2) {
                graphics.drawLine(0, i, i, 0);
            }
            graphics.restoreState();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.draw2d.Figure#getPreferredSize(int, int)
         */
        public Dimension getPreferredSize(int wHint, int hHint) {
            Dimension min = getMinimumSize(wHint, hHint);
            if (!btnContainer.isVisible()) {

                if (wHint != SWT.DEFAULT) {
                    if (getPreferenceSource().getLayoutSetting() == PaletteViewerPreferences.LAYOUT_COLUMNS) {
                        if (min.width < wHint) {
                            min.width = wHint;
                        }
                    }
                }
                return min;
            }

            // Our preferred size (if we have a choice) is to be totally
            // horizontal with enough space for max label

            labelMaxSize = getMaxLabelSize();

            boolean iconsOnly = (getPreferenceSource().getLayoutSetting() == PaletteViewerPreferences.LAYOUT_ICONS);
            int btnSpacer = LABEL_SPACER + HORIZONTAL_SPACER;
            if (iconsOnly) {
                btnSpacer = 0;
            }

            // When given a specific width to work with,
            // tell button container to work with that width
            // minus the indent we will use with it.
            int btnsWHint = SWT.DEFAULT;
            if (wHint != SWT.DEFAULT) {
                btnsWHint = wHint - btnSpacer;
            }

            btnContainerSize = btnContainer.getPreferredSize(btnsWHint,
                    SWT.DEFAULT).getCopy();

            Dimension pinSize = pinButton.getPreferredSize();
            if (iconsOnly) {
                pinSize.width = 0; // No pin with icons only.
            }

            Dimension hrzSz;

            if (getPreferenceSource().getLayoutSetting() == PaletteViewerPreferences.LAYOUT_COLUMNS) {
                // In columns mode, subtype buttons are always under
                hrzSz = new Dimension(Math.max(wHint, Math.max(
                        btnContainerSize.width, labelMaxSize.width
                                + (HORIZONTAL_SPACER + pinSize.width))), Math
                        .max(hHint,
                                (btnContainerSize.height + labelMaxSize.height)
                                        + (3 * VERTICAL_SPACER))
                        + VERTICAL_LABEL_MARGIN + EXTRA_BOTTOM_MARGIN);

            } else {
                hrzSz = new Dimension(Math.max(wHint, btnContainerSize.width
                        + labelMaxSize.width + btnSpacer + HORIZONTAL_SPACER
                        + (HORIZONTAL_SPACER + pinSize.width)), Math.max(hHint,
                        Math.max(btnContainerSize.height, labelMaxSize.height)
                                + (2 * VERTICAL_SPACER))
                        + EXTRA_BOTTOM_MARGIN);
            }

            if (wHint != SWT.DEFAULT) {
                if (wHint < hrzSz.width) {
                    return min;
                }
            }

            return hrzSz;
        }

        /*
         * Minimum width = max space needed for largest tool icon + it's label
         * text. Minimum height = Current label height + number of sub-types *
         * height of sub-types.
         * 
         * @see org.eclipse.draw2d.Figure#getMinimumSize(int, int)
         */
        public Dimension getMinimumSize(int wHint, int hHint) {
            labelMaxSize = getMaxLabelSize();

            boolean iconsOnly = (getPreferenceSource().getLayoutSetting() == PaletteViewerPreferences.LAYOUT_ICONS);

            int btnSpacer = LABEL_SPACER + HORIZONTAL_SPACER;
            if (iconsOnly) {
                btnSpacer = 0;
            }

            // When given a specific width to work with,
            // tell button container to work with that width
            // minus the indent we will use with it.
            int btnsWHint = SWT.DEFAULT;
            if (wHint != SWT.DEFAULT) {
                btnsWHint = wHint - btnSpacer;
            }
            btnContainerSize = btnContainer.getPreferredSize(btnsWHint, hHint)
                    .getCopy();

            Dimension pinSize = pinButton.getPreferredSize();
            if (iconsOnly) {
                pinSize.width = 0; // No pin with icons only.
            }

            if (!btnContainer.isVisible()) {
                // If sub-type buttons not currently displayed then
                // only take max possible label size into account.
                return new Dimension(Math.max(btnContainerSize.width
                        + btnSpacer, MIN_TEXT_WIDTH + HORIZONTAL_SPACER
                        + pinSize.width)
                        + HORIZONTAL_SPACER, labelMaxSize.height
                        + (2 * VERTICAL_SPACER));
            }

            int width = Math.max(MIN_TEXT_WIDTH
                    + (HORIZONTAL_SPACER + pinSize.width),
                    btnContainerSize.width + btnSpacer)
                    + HORIZONTAL_SPACER;

            int height = labelMaxSize.height + btnContainerSize.height
                    + (3 * VERTICAL_SPACER) + VERTICAL_LABEL_MARGIN
                    + EXTRA_BOTTOM_MARGIN;

            Dimension sz = new Dimension(width, height);
            return sz;
        }

        private Dimension calculateTextSize(String text) {
            return FigureUtilities.getTextExtents(text, getFont());
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.draw2d.Figure#layout()
         */
        protected void layout() {
            Rectangle ca = getClientArea();
            int layoutMode = getPreferenceSource().getLayoutSetting();

            if (layoutMode == PaletteViewerPreferences.LAYOUT_ICONS) {
                labelFigure.setVisible(false);

                btnContainer.setLocation(new Point(ca.x + HORIZONTAL_SPACER,
                        ca.y + VERTICAL_SPACER));
                btnContainer.setSize(ca.width, ca.height);

            } else if (layoutMode == PaletteViewerPreferences.LAYOUT_COLUMNS) {
                // In columns mode we have to centre.
                labelFigure.setVisible(true);

                int topMargin = VERTICAL_SPACER;
                int leftMargin = 1;
                if (mainControlPaintSelected()) {
                    topMargin += VERTICAL_SPACER;
                    leftMargin += 1;
                }

                labelFigure.setLocation(new Point((ca.x + leftMargin), ca.y
                        + topMargin));
                labelFigure.setSize(ca.width, labelMaxSize.height);

                btnContainer.setLocation(new Point((ca.x + (ca.width / 2))
                        - (btnContainerSize.width / 2), ca.y + topMargin
                        + labelMaxSize.height + VERTICAL_SPACER
                        + VERTICAL_LABEL_MARGIN));

                btnContainer.setSize(btnContainerSize.getCopy());

            } else {
                labelFigure.setVisible(true);

                int topMargin = VERTICAL_SPACER;
                int leftMargin = 1;

                Dimension pinSize = pinButton.getPreferredSize();

                if (mainControlPaintSelected()) {
                    topMargin += VERTICAL_SPACER;
                    leftMargin += 1;
                }

                if (btnContainer.isVisible() && btnContainerSize != null) {
                    // If no room for btns at right of label, then place
                    // underneath.
                    if ((labelMaxSize.width + btnContainerSize.width
                            + LABEL_SPACER + (2 * HORIZONTAL_SPACER)) >= ca.width) {
                        // No room width-ways, stack one ontop of other.
                        Point labelLoc = new Point(ca.x + leftMargin, ca.y
                                + topMargin);
                        labelFigure.setLocation(labelLoc);
                        btnContainer.setLocation(new Point(ca.x
                                + HORIZONTAL_SPACER + LABEL_SPACER, ca.y
                                + topMargin + labelMaxSize.height
                                + VERTICAL_LABEL_MARGIN));
                        btnContainer.setSize(btnContainerSize.getCopy());

                        labelFigure.setSize(ca.width - pinSize.width
                                - HORIZONTAL_SPACER - (labelLoc.x - ca.x),
                                labelMaxSize.height);

                    } else {
                        // There is room width-ways, align btns to right.
                        int maxh = Math.max(labelMaxSize.height,
                                btnContainerSize.height);

                        Point labelLoc = new Point(ca.x + leftMargin, (ca.y
                                + topMargin + (maxh / 2))
                                - (labelMaxSize.height / 2));
                        labelFigure.setLocation(labelLoc);

                        Point btnContainerLoc = new Point(
                                ((ca.x + ca.width) - HORIZONTAL_SPACER
                                        - btnContainerSize.width - (HORIZONTAL_SPACER + pinSize.width)),
                                (ca.y + topMargin + (maxh / 2))
                                        - (btnContainerSize.height / 2));
                        btnContainer.setLocation(btnContainerLoc);

                        btnContainer.setSize(btnContainerSize.getCopy());

                        labelFigure.setSize(btnContainerLoc.x - labelLoc.x,
                                labelMaxSize.height);
                    }

                } else {
                    labelFigure.setLocation(new Point(ca.x + leftMargin,
                            (ca.y + topMargin)));

                    Dimension labSz = labelFigure.getPreferredSize(ca.width, SWT.DEFAULT);
                    labelFigure.setSize(labSz);
                }
            }

            if (btnContainer.isVisible()) {
                if (layoutMode != PaletteViewerPreferences.LAYOUT_ICONS) {
                    Dimension sz = pinButton.getPreferredSize();

                    pinButton.setLocation(new Point((ca.x + ca.width) - 2
                            - sz.width, (ca.y + 2)));
                    pinButton.setSize(sz);
                    pinButton.setVisible(true);
                } else {
                    pinButton.setVisible(false);
                }
            } else {
                pinButton.setVisible(false);
            }

        }

        /**
         * Return the maximum size of any tool label that can be selected in
         * this button stack.
         * 
         * @return
         */
        private Dimension getMaxLabelSize() {
            Dimension labelMax = labelFigure.getPreferredSize(SWT.DEFAULT,
                    SWT.DEFAULT).getCopy();

            boolean useLargeIcons = getPreferenceSource().useLargeIcons();
            boolean iconsOnly = (getPreferenceSource().getLayoutSetting() == PaletteViewerPreferences.LAYOUT_ICONS);

            if (iconsOnly) {
                return new Dimension(0, 0);
            } else {
                List childList = getChildDataAsList();
                for (Iterator iter = childList.iterator(); iter.hasNext();) {
                    ChildDataEntry childData = (ChildDataEntry) iter.next();

                    Dimension labSz = calculateTextSize(childData.label);

                    Image img;
                    if (useLargeIcons) {
                        img = childData.largeIcon;
                    } else {
                        img = childData.smallIcon;
                    }

                    labSz.width += img.getImageData().width
                            + labelFigure.getIconTextGap();

                    int imgHgt = img.getImageData().height;
                    if (imgHgt > labSz.height) {
                        labSz.height = imgHgt;
                    }

                    if (labSz.width > labelMax.width) {
                        labelMax.width = labSz.width;
                    }

                    if (labSz.height > labelMax.height) {
                        labelMax.height = labSz.height;
                    }

                }

                return labelMax;
            }
        }

    }

    /**
     * ================================================
     * <p>
     * PaletteButtonStackEditPart Local data
     * </p>
     * ================================================
     */

    private ButtonStackPaletteFigure btnStackFigure; // The main figure

    private final static String PROPERTY_PIN_STATE = "PaletteButtonStack.PinState."; //$NON-NLS-1$

    private String pinStatePreferenceId = null;

    private PaletteContainer paletteStackModel; // Our palette model

    // Map of child edit part to stored info about them
    private Map childDataMap;

    private ChildDataEntry curSelection = null;

    private ButtonGroup btnGroup;

    private boolean btnSelectInTransition = false;

    private boolean selectionInProgress = false;

    public static final Image PIN_IMAGE = new Image(null,
            ImageDescriptor.createFromFile(ButtonStackPaletteEditPart.class,
                    "pin_view.gif").getImageData()); //$NON-NLS-1$

    protected static final Border BUTTON_BORDER = new ButtonBorder(
            ButtonBorder.SCHEMES.TOOLBAR);

    //
    // In order for any tool being able to be added to a button stack we have to
    // remove the label from the model so that the sub-tool in the button stack
    // does not show its text label.
    // However, the model has a longer life-span than the edit part and
    // therefore can be used to create another editpart (nominally when user
    // detachs/reattachs existing palette).
    // We therefore need to srtatically save the label text when we remove it.
    private static Map<Object, String> preservedPaletteEntryLabels = new HashMap<Object, String>();

    /**
     * Construct button stack edit part from button stack container model.
     * 
     * @param model
     * @param pinStatePreferenceId
     *            Id to uniquely identify this palette containter and editor or
     *            <b>null</b> if pin button state is not to be preserved
     *            between sessions.
     */
    public ButtonStackPaletteEditPart(PaletteContainer model,
            String pinStatePreferenceId) {
        this.paletteStackModel = model;

        this.pinStatePreferenceId = pinStatePreferenceId;

        /*
         * Have to use a button group to keep easy track of child buton
         * selection. We could listen to their edit parts BUT the edit part
         * listeners don't get signalled when someone else does setActiveTool() -
         * parent button groups do.
         */
        btnGroup = new ButtonGroup() {
            /*
             * (non-Javadoc)
             * 
             * @see org.eclipse.draw2d.ButtonGroup#setSelected(org.eclipse.draw2d.ButtonModel,
             *      boolean)
             */
            public void setSelected(ButtonModel model, boolean value) {
                if (model != null && value) {
                    ChildDataEntry childData = getChildDataEntryFromBtnModel(model);
                    if (childData != null) {
                        // Update our idea of what 'current selection' is
                        // (basically change label etc
                        // btnStackFigure.layout();
                        if (mainControlPaintSelected()) {
                            animateNewSelection(childData);
                        }

                    }
                }

                if (value) {
                    super.setSelected(model, value);
                } else {
                    super.setSelected(null);
                    mainControlDeselect();
                }

            }
        };

        btnGroup.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if (ButtonModel.SELECTED_PROPERTY.equals(evt.getPropertyName())) {
                    // A selection of a button in group has occurred.

                    btnSelectInTransition = false;
                    if (evt.getNewValue() == null && evt.getOldValue() == null) {
                        // tool being deselected, collapse buttons.
                        btnStackFigure.showHideButtons(false);
                        btnStackFigure.doRepaint();
                    } else {
                        if (evt.getOldValue() != null) {
                            // With new as null and old as not null then
                            // we are in between states, this extra
                            // flag will ensure that paintFigure doesn't
                            // paint as if unselected in this condition.
                            btnSelectInTransition = true;
                        }
                        btnStackFigure.showHideButtons(true);
                        btnStackFigure.doRepaint();

                    }
                }

            }

        });

        childDataMap = new HashMap();

    }

    /**
     * ANimate making of new selection (slide copy of just about to be selected
     * tool icon up to label icon.
     * 
     * @param childData
     */
    private void animateNewSelection(ChildDataEntry childData) {
        if (childData != null
                && getPreferenceSource().getLayoutSetting() != PaletteViewerPreferences.LAYOUT_ICONS) {

            final ChildDataEntry myChildData = childData;

            Display.getCurrent().asyncExec(new Runnable() {

                public void run() {

                    Image img;
                    if (getPreferenceSource().useLargeIcons()) {
                        img = myChildData.largeIcon;
                    } else {
                        img = myChildData.smallIcon;
                    }

                    IFigure fig = new ImageFigure(img);
                    fig.setSize(fig.getPreferredSize());

                    Point start = myChildData.editPart.getFigure().getBounds()
                            .getTopLeft();
                    Point end = btnStackFigure.labelFigure.getIconBounds()
                            .getTopLeft();

                    List<Point> pts = new ArrayList<Point>();

                    final float numFrames = 10.0f;

                    float deltaX = ((float) (start.x - end.x)) / numFrames;
                    float deltaY = ((float) (start.y - end.y)) / numFrames;

                    for (int i = 0; i < numFrames; i++) {
                        pts.add(new Point(start.x - Math.floor(deltaX * i),
                                start.y - Math.floor(deltaY * i)));
                    }

                    btnStackFigure.add(fig);
                    fig.setVisible(true);

                    int frameNum = 1;
                    long startTime = System.currentTimeMillis();
                    long maxFrameTime = 15;

                    for (Iterator iter = pts.iterator(); iter.hasNext(); frameNum++) {
                        Point pt = (Point) iter.next();

                        fig.setLocation(pt);
                        getViewer().flush();

                        long currTime = System.currentTimeMillis();

                        // Properly judge how long to sleep for by counting
                        // accumulated frame time.
                        long shouldBe = frameNum * maxFrameTime;
                        long actual = currTime - startTime;

                        long sleepTime = Math.min(maxFrameTime,
                                (shouldBe - actual));

                        if (sleepTime > 0) {
                            try {
                                Thread.sleep(sleepTime);

                            } catch (InterruptedException e) {
                                break;
                            }
                        }
                    }

                    btnStackFigure.remove(fig);

                    setCurrentSelection(myChildData);
                }
            });

        } else {
            // No animation, just set selection 
            setCurrentSelection(childData);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.internal.ui.palette.editparts.ToolEntryEditPart#createFigure()
     */
    public IFigure createFigure() {

        btnStackFigure = new ButtonStackPaletteFigure();

        return btnStackFigure;
    }

    /**
     * Update when selected child changes Basically resets the main label; and
     * icon
     * 
     * @param childData
     */
    public void setCurrentSelection(ChildDataEntry childData) {
        curSelection = childData;

        // Reset the data in our label.
        Image img;
        if (getPreferenceSource().useLargeIcons()) {
            img = childData.largeIcon;
        } else {
            img = childData.smallIcon;
        }

        btnStackFigure
                .setLabelInfo(childData.label, childData.description, img);

    }

    public PaletteViewerPreferences getPreferenceSource() {
        return ((PaletteViewer) getViewer()).getPaletteViewerPreferences();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractEditPart#getModel()
     */
    public Object getModel() {
        return paletteStackModel;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
     */
    protected List getModelChildren() {
        return paletteStackModel.getChildren();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractEditPart#addChild(org.eclipse.gef.EditPart,
     *      int)
     */
    protected void addChild(EditPart child, int index) {
        // We don't want to display text for children, just the buttons. So keep
        // the original then remove from child.
        if (child instanceof AbstractGraphicalEditPart
                && ((AbstractGraphicalEditPart) child).getFigure() instanceof Clickable
                && child.getModel() instanceof PaletteEntry) {
            PaletteEntry pe = (PaletteEntry) child.getModel();

            ChildDataEntry childData = new ChildDataEntry();
            childData.editPart = (AbstractGraphicalEditPart) child;

            // May be re-initialising palette without reinit of model therefore
            // it's labels will have been set to "" first time in. Therefore if
            // possible get the preserved original label.
            if (preservedPaletteEntryLabels.containsKey(pe)) {
                // This model palette entry has been thru here already.
                childData.label = preservedPaletteEntryLabels.get(pe);
            } else {
                // this model palette entry has not been thru here (or at least
                // not used in a button stacker so get the label from the
                // oriiginal.
                childData.label = pe.getLabel();

                // And cache the original label in the preserved map.
                preservedPaletteEntryLabels.put(pe, pe.getLabel());
            }

            childData.description = pe.getDescription();
            childData.smallIcon = pe.getSmallIcon().createImage();
            childData.largeIcon = pe.getLargeIcon().createImage();
            childDataMap.put(child, childData);

            pe.setLabel(""); //$NON-NLS-1$
            pe.setDescription(childData.label + ": " + childData.description); //$NON-NLS-1$

            childData.btnModel = ((Clickable) ((AbstractGraphicalEditPart) child)
                    .getFigure()).getModel();
            btnGroup.add(childData.btnModel);

            if (childDataMap.size() == 1) {
                setCurrentSelection(childData);
            }
        }
        super.addChild(child, index);

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractEditPart#setSelected(int)
     */
    public void setSelected(int value) {
        // Don't allow re-entry!
        // This can happen because when doing setActiveTool() on child below,
        // the
        // viewer unsets selection on current tool and then by default sets
        // selection back on remaining primary tool.. which might be US!
        if (selectionInProgress) {
            return;
        }

        selectionInProgress = true;

        // When main tool is selected, set current active tool
        // to current child.
        if (value != EditPart.SELECTED_NONE) {
            ((PaletteViewer) getViewer())
                    .setActiveTool((ToolEntry) curSelection.editPart.getModel());

        } else {
            // When we get deselected, deselect our current child.
            btnGroup.setSelected(null);
        }
        super.setSelected(value);

        selectionInProgress = false;
    }

    /**
     * Make sure that palette view knows we're deselected
     */
    public void mainControlDeselect() {
        ((PaletteViewer) getViewer()).deselect(this);
    }

    public void mainControlSelect() {
        ((PaletteViewer) getViewer()).select(this);
    }

    /**
     * It is possible to get into an intermediate state (when main tool selected
     * after other top-level tool and then mouse down on sub-type button). This
     * function returns the correct state even in these circumstances.
     * 
     * @return
     */
    public boolean mainControlPaintSelected() {
        return (btnGroup.getSelected() != null || btnSelectInTransition);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractEditPart#removeChild(org.eclipse.gef.EditPart)
     */
    protected void removeChild(EditPart child) {
        // 
        // Set the child data back to what it was before we took control (just
        // in case)
        ChildDataEntry childData = (ChildDataEntry) childDataMap.get(child);
        if (childData != null) {
            childData.dispose();

            PaletteEntry pe = (PaletteEntry) child.getModel();

            if (pe != null) {
                pe.setLabel(childData.label);
                pe.setDescription(childData.description);

                childDataMap.remove(child);
            }
        }
        super.removeChild(child);
    }

    /**
     * Returnn our child button data map as a list.
     * 
     * @return
     */
    private List getChildDataAsList() {
        List childList = new ArrayList(childDataMap.size());

        // Dispose copies of button images.
        for (Iterator iter = childDataMap.entrySet().iterator(); iter.hasNext();) {
            childList.add(((Map.Entry) iter.next()).getValue());
        }

        return childList;
    }

    /**
     * Given a child button's model, return our map entry for it.
     * 
     * @param btnModel
     * @return
     */
    private ChildDataEntry getChildDataEntryFromBtnModel(ButtonModel btnModel) {
        List childList = getChildDataAsList();

        for (Iterator iter = childList.iterator(); iter.hasNext();) {
            ChildDataEntry childData = (ChildDataEntry) iter.next();

            if (childData.btnModel == btnModel) {
                return childData;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate()
     */
    public void deactivate() {
        // Dispose copies of button images.
        for (Iterator iter = getChildDataAsList().iterator(); iter.hasNext();) {
            ChildDataEntry childData = (ChildDataEntry) iter.next();

            childData.dispose();
        }

        super.deactivate();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
     */
    protected void createEditPolicies() {
        // None to do.
    }

    /**
     * Get the pin state for the given button stacker drawer from the preference
     * store.
     * 
     * @return
     */
    private boolean getInitialPinState() {
        if (pinStatePreferenceId != null) {
            IPreferenceStore prefStore = XpdGmfExtensionsPlugin.getDefault()
                    .getPreferenceStore();
            return prefStore.getBoolean(pinStatePreferenceId);
        } else {
            return false;
        }
    }

    /**
     * Store the drawer pin state to preferences store.
     * 
     * @param newState
     */
    private void storeInitialPinState(boolean newState) {
        if (pinStatePreferenceId != null) {
            IPreferenceStore prefStore = XpdGmfExtensionsPlugin.getDefault()
                    .getPreferenceStore();

            prefStore.setValue(pinStatePreferenceId, newState);
        }
    }

}
