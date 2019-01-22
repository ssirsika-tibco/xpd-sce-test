/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.core.validate.system.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.forms.events.IHyperlinkListener;

/**
 * Interface implemented by the configuration validation logger. This will allow
 * the printing of information on the validate dialog.
 * 
 * @author njpatel
 * 
 */
public interface IConfigurationLogger {

    /**
     * Add heading to the dialog.
     * 
     * @param message
     *            heading
     */
    void heading(String message);

    /**
     * Add a message to the dialog with the given severity. The severity will
     * determine which icon to use.
     * 
     * @param message
     *            message to print
     * @param severity
     *            {@link IStatus} severity.
     */
    void message(String message, int severity);

    /**
     * Add a message to the dialog with the given severity. The severity will
     * determine which icon to use. Optionally, the addition bullet points will
     * also be added under the message if provided.
     * 
     * @param message
     *            message to print
     * @param additionalBullets
     *            optionally, bullet points will be added under the message
     * @param severity
     *            {@link IStatus} severity.
     */
    void message(String message, String[] additionalBullets, int severity);

    /**
     * Add a hyperlink to process the given listener when activated in the
     * dialog.
     * 
     * @param message
     *            hyperlink message.
     * @param href
     *            href tag to set for this listener.
     * @param listener
     *            hyperlink listener.
     * @param revalidateOnLinkActivate
     *            set to <code>true</code> if the validation should be re-run
     *            once this link is activated
     */
    void hyperlink(String message, String href, IHyperlinkListener listener,
            boolean revalidateOnLinkActivate);

    /**
     * Add the given advice to the dialog.
     * 
     * @param message
     *            advice
     */
    void advice(String message);

    /**
     * Print the final result. This will be added to the top of the form and
     * should indicate the final verdict of the check.
     * 
     * @param status
     *            result
     */
    void result(IStatus status);
}
