/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.providers;

import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.IProcessPickerProxyItem;

/**
 * This interface will be implemented by the provider of the content of the
 * Process picker.
 * 
 * @author Miguel Torres
 * 
 */
public interface IProcessPickerProvider {

    /**
     * Type of items required for the picker.
     * 
     * @author Miguel Torres
     * 
     */
    enum ProcessType {
        PROCESS, PROCESSINTERFACE, SERVICEPROCESSINTERFACE, SERVICEPROCESS, PROCESS_PROCESSINTERFACE, PAGEFLOW, PAGEFLOW_PROCESSINTERFACE;
    }

    /**
     * Get the content of the given type for the Process picker to display.
     * 
     * @param monitor
     *            progress monitor.
     * @param type
     *            type of data required.
     * @return array of <code>IProcessPickerProxyItem</code> objects to display
     *         in the picker.
     */
    IProcessPickerProxyItem[] getContent(IProgressMonitor monitor,
            ProcessType type);

    /**
     * Get the item with the given <code>URI</code>.
     * 
     * @param uri
     *            item uri
     * @param name
     *            fully qualified name
     * @return <code>IProcessPickerProxyItem</code> for the given
     *         <code>URI</code>.
     */
    IProcessPickerProxyItem getItem(String uri, String name);
}
