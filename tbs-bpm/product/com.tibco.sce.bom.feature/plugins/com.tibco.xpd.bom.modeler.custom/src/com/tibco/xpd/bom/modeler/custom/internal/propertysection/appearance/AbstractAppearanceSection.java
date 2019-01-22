/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.appearance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.properties.sections.AbstractNotationPropertiesSection;
import org.eclipse.gmf.runtime.diagram.ui.properties.sections.appearance.ColorPalettePopup;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.emf.core.util.PackageUtil;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.bom.modeler.custom.internal.BOMDiagramUIPropertiesImages;

/**
 * Abstract section for the appearance property section. This provides methods
 * to set colours in the properties page (using a popup palette).
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractAppearanceSection extends
        AbstractNotationPropertiesSection {

    /** the default preference color */
    protected static final RGB DEFAULT_PREF_COLOR = new RGB(0, 0, 0);

    /** default color icon width */
    private static final Point ICON_SIZE = new Point(16, 16);

    /**
     * Result of the selection in the color popup palette.
     * 
     * @author njpatel
     * 
     */
    protected interface SelectedColor {
        /**
         * Set to <code>true</code> if the user selected to use default color in
         * the popup.
         * 
         * @return
         */
        public boolean useDefaultColor();

        /**
         * Set to the color the user selected in the popup palette.
         * 
         * @return
         */
        public RGB getColor();
    }

    /**
     * Update if nessesary, upon receiving the model event. This event will only
     * be processed when the reciever is visible (the default behavior is not to
     * listen to the model events while not showing). Therefore it is safe to
     * refresh the UI. Sublclasses, which will choose to override event
     * listening behavior should take into account that the model events are
     * sent all the time - regardless of the section visibility. Thus special
     * care should be taken by the section that will choose to listen to such
     * events all the time. Also, the default implementation of this method
     * synchronizes on the GUI thread. The subclasses that overwrite it should
     * do the same if they perform any GUI work (because events may be sent from
     * a non-GUI thread).
     * 
     * @see #aboutToBeShown()
     * @see #aboutToBeHidden()
     * 
     * @param notification
     *            notification object
     * @param element
     *            element that has changed
     */
    public void update(final Notification notification, final EObject element) {
        if (!isDisposed() && isCurrentSelection(notification, element)) {
            postUpdateRequest(new Runnable() {

                public void run() {
                    if (!isDisposed()
                            && isCurrentSelection(notification, element)
                            && !isNotifierDeleted(notification)) {
                    }
                    updateColorCache();
                    refresh();

                }
            });
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.views.properties.tabbed.ISection#setInput(org.eclipse.
     * ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
     */
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);
        updateColorCache();
    }

    /**
     * React to selection or model change - update local cache.
     */
    protected abstract void updateColorCache();

    /**
     * Show the color palette popup for the user to select a color.
     * 
     * @param button
     *            the button that triggered this popup.
     * @param currentColor
     *            current color to show in the palette
     * @return {@link SelectedColor result} of the user selection.
     */
    protected SelectedColor showColorPalette(Button button, int currentColor) {
        final ColorPalettePopup popup =
                new ColorPalettePopup(button.getParent().getShell(),
                        IDialogConstants.BUTTON_BAR_HEIGHT);
        popup.setPreviousColor(currentColor);

        Rectangle r = button.getBounds();
        Point location = button.getParent().toDisplay(r.x, r.y);
        Point loc =
                new Point(location.x + r.width, location.y + r.height
                        - (IDialogConstants.BUTTON_BAR_HEIGHT * 6));
        popup.open(loc);

        return new SelectedColor() {
            public RGB getColor() {
                return popup.getSelectedColor();
            }

            public boolean useDefaultColor() {
                return popup.useDefaultColor();
            }
        };
    }

    /**
     * @param event
     *            - selection event
     * @param button
     *            - event source
     * @param propertyId
     *            - id of the property
     * @param commandName
     *            - name of the command
     * @param imageDescriptor
     *            - the image to draw overlay on the button after the new color
     *            is set
     * @return - new RGB color, or null if none selected
     */
    protected RGB changeColor(SelectionEvent event, Button button,
            final String propertyId, String commandName,
            ImageDescriptor imageDescriptor, int previousColor) {

        SelectedColor result = showColorPalette(button, previousColor);

        if (result.getColor() == null && !result.useDefaultColor()) {
            return null;
        }

        // selectedColor should be null if we are to use the default color
        final RGB selectedColor = result.getColor();

        final EStructuralFeature feature =
                (EStructuralFeature) PackageUtil.getElement(propertyId);

        // Update model in response to user

        List<ICommand> commands = new ArrayList<ICommand>();
        Iterator<?> it = getInput().iterator();

        RGB colorToReturn = selectedColor;
        RGB color = selectedColor;
        while (it.hasNext()) {
            final IGraphicalEditPart ep = (IGraphicalEditPart) it.next();

            color = selectedColor;
            if (result.useDefaultColor()) {
                Object preferredValue = ep.getPreferredValue(feature);
                if (preferredValue instanceof Integer) {
                    color =
                            FigureUtilities
                                    .integerToRGB((Integer) preferredValue);
                }
            }

            // If we are using default colors, we want to return the color of
            // the first selected element to be consistent
            if (colorToReturn == null) {
                colorToReturn = color;
            }

            if (color != null) {
                final RGB finalColor = color; // need a final variable
                commands.add(createCommand(commandName, ((View) ep.getModel())
                        .eResource(), new Runnable() {

                    public void run() {
                        ENamedElement element =
                                PackageUtil.getElement(propertyId);
                        if (element instanceof EStructuralFeature)
                            ep.setStructuralFeatureValue(feature,
                                    FigureUtilities.RGBToInteger(finalColor));
                    }
                }));
            }
        }
        if (!commands.isEmpty()) {
            executeAsCompositeCommand(commandName, commands);
            Image overlyedImage =
                    new ColorOverlayImageDescriptor(imageDescriptor
                            .getImageData(), color).createImage();
            disposeImage(button.getImage());
            button.setImage(overlyedImage);
        }
        return colorToReturn;
    }

    /**
     * Dispose the image if it was created locally to avoid a leak. Do not
     * dispose the images in the registry.
     * 
     * @param image
     */
    protected void disposeImage(Image image) {
        if (image == null) {
            return;
        }

        if (image.equals(BOMDiagramUIPropertiesImages
                .get(BOMDiagramUIPropertiesImages.IMG_FILL_COLOR))) {
            return;
        }

        if (!image.isDisposed()) {
            image.dispose();
        }
    }

    /**
     * An image descriptor that overlays two images: a basic icon and a thin
     * color bar underneath it
     */
    protected class ColorOverlayImageDescriptor extends
            CompositeImageDescriptor {

        /** the basic icon */
        private final ImageData basicImgData;

        /** the color of the thin color bar */
        private final RGB rgb;

        /**
         * Creates a new color menu image descriptor
         * 
         * @param basicIcon
         *            The basic Image data
         * @param rgb
         *            The color bar RGB value
         */
        public ColorOverlayImageDescriptor(ImageData basicImgData, RGB rgb) {
            this.basicImgData = basicImgData;
            this.rgb = rgb;
        }

        /**
         * @see org.eclipse.jface.resource.CompositeImageDescriptor#drawCompositeImage(int,
         *      int)
         */
        protected void drawCompositeImage(int width, int height) {

            // draw the thin color bar underneath
            if (rgb != null) {
                ImageData colorBar = new ImageData(width, height / 5, 1,

                new PaletteData(new RGB[] { rgb }));
                drawImage(colorBar, 0, height - height / 5);

            }
            // draw the base image
            drawImage(basicImgData, 0, 0);
        }

        /**
         * @see org.eclipse.jface.resource.CompositeImageDescriptor#getSize()
         */
        protected Point getSize() {
            return ICON_SIZE;
        }
    }
}
