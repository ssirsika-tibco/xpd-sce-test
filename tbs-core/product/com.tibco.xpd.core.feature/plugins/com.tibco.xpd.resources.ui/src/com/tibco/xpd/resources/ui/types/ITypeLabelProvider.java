/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.types;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.ui.picker.IPickerLabelProvider;

/**
 * This interface must be implemented by the types provider, when the element
 * labelProviders is specified in the typeProviders extension. It's purpose is
 * to determine how the content is displayed in the <code>PickerDialog</code>.
 * 
 * @author rassisi
 * @deprecated Use {@link IPickerLabelProvider} instead.
 */
@Deprecated
public interface ITypeLabelProvider {

    /**
     * The text for a given item, which is displayed by the PickerDialog in the
     * selection list.
     * 
     * @param typedItem
     * 
     * @return
     */
    public String getText(TypedItem typedItem);

    /**
     * Returns the string, that will be displayed in the status bar of the
     * PickerDialog.
     * 
     * @param typedItem
     * @return
     */
    public String getResourceText(TypedItem typedItem);

    /**
     * Similar to #getText. The additional parameter #text will be leading the
     * displayed text.
     * 
     * @param text
     * @param typedItem
     * @return
     */
    public String decorateText(String text, TypedItem typedItem);

    /**
     * The image that is displayed in the selection list of the PickerDialog.
     * 
     * @return
     */
    public Image getImage(TypedItem typedItem);

    /**
     * Similar to {@link #getImage(TypedItem)}. In addition the given Image
     * decorates the corresponding image.
     * 
     * @param image
     * @param typedItem
     * @return
     */
    public Image decorateImage(Image image, TypedItem typedItem);

    /**
     * Returns the image, that is related to the resource, that contains the
     * typed item.
     * 
     * @param typedItem
     * @return
     */
    public Image getResourceImage(TypedItem typedItem);

    /**
     * The contributors id.
     * 
     * @param providerId
     */
    public void setProviderId(String providerId);

}
