/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.resources.ui.picker;

/**
 * Represents picker content extension element (See:
 * <code>com.tibco.xpd.resources.ui.pickerContent</code> extension for more
 * details.). It provides an unique ID and a references to
 * {@link IPickerItemProvider} and {@link IPickerLabelProvider}.
 * {@link IPickerItemProvider} is responsible for providing a content in form of
 * a collection of {@link PickerItem}s, while {@link IPickerLabelProvider} is to
 * tell how this item will be displayed.
 * <p/>
 * Use {@link PickerService#getPickerContentExtension(String)} if you need to
 * obtain a reference.
 * 
 * 
 * <p>
 * This class is not intended to be instantiated or extended outside of the
 * framework.
 * </p>
 * 
 * @since 3.3.1
 * @author Jan Arciuchiewicz
 */
public class PickerContentExtension {

    private final String id;

    private final IPickerItemProvider pickerItemProvider;

    private final IPickerLabelProvider pickerLabelProvider;

    /**
     * Creates instance.
     * 
     * @param id
     *            identifier of the extension.
     * @param pickerContentProvider
     *            associated content provider for items in the picker.
     * @param pickerLabelProvider
     *            associated label provider for items in the picker.
     */
    /* package */PickerContentExtension(String id,
            IPickerItemProvider pickerContentProvider,
            IPickerLabelProvider pickerLabelProvider) {
        this.id = id;
        this.pickerItemProvider = pickerContentProvider;
        this.pickerLabelProvider = pickerLabelProvider;
    }

    /**
     * Gets ID of the extension.
     * 
     * @return ID of the extension.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets PickerItemProvider for this extension.
     * 
     * @return PickerItemProvider for this extension.
     */
    public IPickerItemProvider getPickerItemProvider() {
        return pickerItemProvider;
    }

    /**
     * Gets PickerLabelProvider for this extension.
     * 
     * @return
     */
    public IPickerLabelProvider getPickerLabelProvider() {
        return pickerLabelProvider;
    }

}
