/**
 * ModelToImage.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.adapters;

import java.util.Collection;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Image;

/**
 * ModelToImage
 * 
 */
public interface DiagramModelImageProvider {

    public static int PAINT_UNSELECTED_OBJECTS = 0x01;

    public static int HIGHLIGHT_CHILDREN = 0x02;

    public static int MAX_SCALE_1_TO_1 = 0x04;

    /**
     * Return daigram image for the given model objects highlighting the given
     * objects when doing so.
     * 
     * @param areaObjects
     *            List of objects that define the area to return image for (or
     *            null for complete process).
     * @param selectionObjects
     *            List of objects around which to paint a selection rectangle
     * @param highlightObjects
     *            List of objects to highlight (or null if all are to be paint
     *            highlighted).
     * @param sizeHint
     *            Hint of size to fit image to or <b>null</b> if full size
     *            image required.
     * @param margin
     *            extra margin around area objects when grabbing image from full
     *            process image.
     * @param flags
     *            Bitwise Combination of the following...
     *            <li>PAINT_UNSELECTED_OBJECTS true if caller wants objects
     *            that are not in the selectionObjects list painted faded out in
     *            the background.</li>
     *            <li>HIGHLIGHT_CHILDREN true if caller wants to highlight
     *            children of objects in the highlightObjects list.</li>
     *            <li>MAX_SCALE_1_TO_1 Do not allow scale to bigger than
     *            original image.</li>
     * 
     * @return
     */
    Image getDiagramImageFromModel(Collection areaObjects,
            Collection selectionObjects, Collection highlightObjects,
            Dimension sizeHint, int margin, int flags);

}
