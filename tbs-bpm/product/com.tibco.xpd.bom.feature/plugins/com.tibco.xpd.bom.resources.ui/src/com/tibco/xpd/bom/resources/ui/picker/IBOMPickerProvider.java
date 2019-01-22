/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.picker;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * This interface will be implemented by the provider of the content of the BOM
 * picker.
 * 
 * @author njpatel
 * 
 */
@Deprecated
public interface IBOMPickerProvider {

    /**
     * Type of items required for the picker.
     * 
     * @author njpatel
     * 
     */
    enum BOMType {
        CLASS, PRIMITIVE_TYPE, BASE_PRIMITIVE;
    }

    /**
     * Get the content of the given type for the BOM picker to display.
     * 
     * @param monitor
     *            progress monitor.
     * @param type
     *            type of data required.
     * @return array of <code>IBOMPickerProxyItem</code> objects to display in
     *         the picker.
     */
    IBOMPickerProxyItem[] getContent(IProgressMonitor monitor, BOMType type);

    /**
     * Get the item with the given <code>URI</code>.
     * 
     * @param uri
     *            item uri
     * @param name
     *            fully qualified name
     * @return <code>IBOMPickerProxyItem</code> for the given <code>URI</code>.
     */
    IBOMPickerProxyItem getItem(String uri, String name);
}
