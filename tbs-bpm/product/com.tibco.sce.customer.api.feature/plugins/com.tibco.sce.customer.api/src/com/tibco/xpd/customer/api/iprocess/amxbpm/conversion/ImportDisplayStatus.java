/*
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */

package com.tibco.xpd.customer.api.iprocess.amxbpm.conversion;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

/**
 * Status class which should be only used for Status messages intended to be
 * displayed to User on completion of Import/Convert.
 * <p>
 * This class extends MultiStatus to be able to display messages in multiple
 * lines, which is not feasible when using Status, which displays message in one
 * line, even if formatted with next line character.Add a child IStatus entry
 * for each message to be displayed.
 * </p>
 * 
 * @author TIBCO Software Inc
 * @since TIBCO Business Studio BPM Edition v3.8
 */
public class ImportDisplayStatus extends MultiStatus {

    /**
     * Returns combined message containing message entry of all child
     * {@link IStatus} element, with the individual child IStatus message in its
     * separate line.
     * 
     * @see org.eclipse.core.runtime.Status#getMessage()
     * 
     * @return the formatted message String ready for display. Formatting
     *         separates the each child {@link IStatus} message entry to
     *         separate lines.
     */
    @Override
    public String getMessage() {

        StringBuffer msg = new StringBuffer();

        for (IStatus status : getChildren()) {

            msg.append(status.getMessage());
            msg.append(System.getProperty("line.separator")); //$NON-NLS-1$
        }

        return msg.toString();
    }

    /**
     * This Constructor creates one child Status entry for each message in the
     * messages Array.Use messages parameter to pass message split into strings
     * to be displayed in separate lines.
     * <p>
     * To Display something like this
     * 
     * Following is the list of element : <br>
     * element1,element2...</br>
     * 
     * 
     * use <code>
     * String line1 = "Following is the list of element :";
     * String line2 = "element1,element2...";
     * IStatus status = new ImportDisplayStatus(IStatus.INFO,pluginId, new String[]{line1,line2});
     * </code>
     * </p>
     * 
     * @param severity
     *            , severity of this {@link IStatus} message.
     * @param pluginId
     *            , id of the plugin.
     * @param messages
     *            , array of {@link String} each represent the separate line of
     *            message.
     */
    public ImportDisplayStatus(int severity, String pluginId, String[] messages) {

        super(pluginId, severity, "", null); //$NON-NLS-1$ No Message is required here.

        for (String msg : messages) {
            // create status for each message
            add(new Status(severity, pluginId, msg));
        }

    }

}
