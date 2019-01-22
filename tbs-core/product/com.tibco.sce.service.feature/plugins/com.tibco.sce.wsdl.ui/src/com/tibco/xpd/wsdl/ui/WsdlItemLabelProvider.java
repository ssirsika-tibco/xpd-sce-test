/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui;

import org.eclipse.emf.edit.provider.IItemLabelProvider;

/**
 * Item label provider delegating to WsdlLabelProvider.
 * 
 * @author nwilson
 */
public class WsdlItemLabelProvider implements IItemLabelProvider {

    /** The label provider to delegate to. */
    private WsdlLabelProvider labelProvider;

    /**
     * Initialises the label provider.
     */
    public WsdlItemLabelProvider() {
        labelProvider = new WsdlLabelProvider();
    }

    /**
     * @param object The object to get an image for.
     * @return The image.
     * @see org.eclipse.emf.edit.provider.IItemLabelProvider#getImage(
     *      java.lang.Object)
     */
    public Object getImage(Object object) {
        return labelProvider.getImage(object);
    }

    /**
     * @param object The object to get text for.
     * @return The text.
     * @see org.eclipse.emf.edit.provider.IItemLabelProvider#getText(
     *      java.lang.Object)
     */
    public String getText(Object object) {
        return labelProvider.getText(object);
    }

}
