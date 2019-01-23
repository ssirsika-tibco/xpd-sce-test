/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.resources.ui.picker;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Determines how object {@link PickerItem}s will be presented in a Picker
 * dialog. The implementations of this interface are referenced from
 * <code>com.tibco.xpd.resources.ui.pickerContent</code> extension.
 * 
 * @see CommonPickerDialog
 * @see IPickerContentProvider
 * 
 * @since 3.3.1
 * @author Jan Arciuchiewicz
 */
public interface IPickerLabelProvider extends IBaseLabelProvider {

    /**
     * Text label of item show in the picker.
     * 
     * @param item
     *            picker item.
     * @return text label of item show in the picker.
     */
    String getText(PickerItem item);

    /**
     * Image icon of item show in the picker.
     * 
     * @param item
     *            picker item.
     * @return icon of item show in the picker.
     */
    Image getImage(PickerItem item);

    /**
     * Decorated text label of the item when it is selected.
     * 
     * @param text
     *            original text of the label.
     * @param item
     *            picker item.
     * @return decorated text.
     */
    String decorateSelectedItemText(String text, PickerItem item);

    /**
     * Decorated image of the item when it is selected.
     * 
     * @param image
     *            original text of the label.
     * @param item
     *            picker item.
     * @return decorated image.
     */
    Image decorateSelectedItemImage(Image image, PickerItem item);

    /**
     * Text shown in the status field for a selected item.
     * 
     * @param item
     *            picker item.
     * @return text shown in the status field for a selected item.
     */
    String getStatusText(PickerItem item);

    /**
     * Icon shown in the status field for a selected item.
     * 
     * @param item
     *            picker item.
     * @return image shown in the status field for a selected item.
     */
    Image getStatusImage(PickerItem item);
}
