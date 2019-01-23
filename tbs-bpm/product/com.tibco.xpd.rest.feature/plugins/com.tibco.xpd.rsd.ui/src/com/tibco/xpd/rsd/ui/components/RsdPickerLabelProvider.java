/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.components;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.ui.picker.BasePickerLabelProvider;
import com.tibco.xpd.resources.ui.picker.PickerItem;
import com.tibco.xpd.rsd.HttpMethod;
import com.tibco.xpd.rsd.ui.RsdImage;
import com.tibco.xpd.rsd.wc.RsdIndexProvider;

/**
 * Picker label provider to add images to UML types.
 * 
 * @author nwilson
 * @since 23 Jan 2015
 */
public class RsdPickerLabelProvider extends BasePickerLabelProvider {

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getImage(PickerItem pickerItem) {
        String type = pickerItem.getType();
        if (RestMethodPicker.METHOD_TYPE.equals(type)) {
            HttpMethod httpMethod =
                    HttpMethod
                            .get(pickerItem.get(RsdIndexProvider.HTTP_METHOD));
            switch (httpMethod) {
            case GET:
                return RsdImage.getImage(RsdImage.METHOD_GET);
            case POST:
                return RsdImage.getImage(RsdImage.METHOD_POST);
            case PUT:
                return RsdImage.getImage(RsdImage.METHOD_PUT);
            case DELETE:
                return RsdImage.getImage(RsdImage.METHOD_DELETE);
            }
            return RsdImage.getImage(RsdImage.METHOD);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText(PickerItem pickerItem) {
        String type = pickerItem.getType();
        if (RestMethodPicker.METHOD_TYPE.equals(type)) {
            String httpMethod = pickerItem.get(RsdIndexProvider.HTTP_METHOD);
            StringBuilder sb = new StringBuilder(pickerItem.getName());
            sb.append(" (").append(httpMethod).append(")"); //$NON-NLS-1$ //$NON-NLS-2$
            return sb.toString();
        }
        return super.getText(pickerItem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String decorateSelectedItemText(String text, PickerItem element) {
        String type = element.getType();
        if (RestMethodPicker.METHOD_TYPE.equals(type)) {
            String serviceName = element.get(RsdIndexProvider.SERVICE_NAME);
            String resourceName = element.get(RsdIndexProvider.RESOURCE_NAME);
            StringBuilder sb = new StringBuilder(text);
            sb.append(" - ").append(serviceName).append("/").append(resourceName); //$NON-NLS-1$ //$NON-NLS-2$
            return sb.toString();
        }
        return super.decorateSelectedItemText(text, element);
    }
}
