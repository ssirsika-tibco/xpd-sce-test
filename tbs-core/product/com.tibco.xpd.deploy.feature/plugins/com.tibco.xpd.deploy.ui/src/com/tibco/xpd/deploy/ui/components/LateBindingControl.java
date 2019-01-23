/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.components;

import java.util.Collection;

import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.deploy.model.extension.ResourceBinding;

/**
 * Late binding control should implement this interface to communicate with
 * framework. The implementation should also be
 * <p>
 * <i>Created: 16 Feb 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public abstract class LateBindingControl extends Composite {

    /**
     * Creates the control.
     * 
     * @see Composite
     * 
     */
    public LateBindingControl(Composite parent, int style) {
        super(parent, style);
    }

    /**
     * @param validatePage
     */
    public abstract boolean isControlComplete();

    /**
     * Sets the message for this page with an indication of what type of message
     * it is.
     * <p>
     * The valid message types are one of <code>NONE</code>,
     * <code>INFORMATION</code>,<code>WARNING</code>, or <code>ERROR</code>.
     * </p>
     * <p>
     * Note that for backward compatibility, a message of type
     * <code>ERROR</code> is different than an error message (set using
     * <code>setErrorMessage</code>). An error message overrides the current
     * message until the error message is cleared. This method replaces the
     * current message and does not affect the error message.
     * </p>
     * 
     * @param newMessage
     *            the message, or <code>null</code> to clear the message
     * @param newType
     *            the message type
     * @since 2.0
     */
    public abstract void setMessage(String newMessage, int newType);

    /**
     * Returns list of associated servers.
     */
    public abstract Collection<ResourceBinding> getBindings();

    public abstract void initializeControlDefaults();

    public abstract void commitBindings() throws Exception;

    public abstract void updateBindings(Collection<ResourceBinding> bindings);

}