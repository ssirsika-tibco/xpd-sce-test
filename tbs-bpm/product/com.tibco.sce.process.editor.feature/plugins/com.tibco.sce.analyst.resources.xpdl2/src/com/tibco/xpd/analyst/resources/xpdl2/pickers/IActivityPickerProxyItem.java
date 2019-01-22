/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.pickers;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;

/**
 * This represents an item that will be displayed in the Activity Picker. The
 * implementation of this interface is a proxy item that will be resolved to an
 * {@link EObject} once a selection is made in the picker (when OK is pressed)
 * Note that the EObject item is contained in this proxy.
 * 
 * @author Miguel Torres
 * 
 */
public interface IActivityPickerProxyItem {

    /**
     * Get the name of the item.
     * 
     * @return name.
     */
    String getName();

    /**
     * Get the fully qualified name.
     * 
     * @return qualified name. If there is no qualification then this will
     *         return the same result as {@link #getName()}.
     */
    String getQualifiedName();

    /**
     * Get the image that will represent this item in the picker.
     * 
     * @return
     */
    Image getImage();

    /**
     * Get the URI of this item. This URI will be used to resolve this item to
     * it's {@link EObject}.
     * 
     * @return <code>URI</code>
     */
    URI getURI();
    
    /**
     * Get the item
     * 
     * @return <code>Object</code>
     */
    Object getItem();

}
