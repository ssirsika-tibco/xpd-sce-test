/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.deploy.ui.components;

import org.eclipse.jface.action.Action;

import com.tibco.xpd.deploy.Server;

/**
 * Subclasses are used to rendered as an action button attached to the string
 * parameter. Use: <li>{@link #runWithServer(Server)} method to get access to
 * the temporary server containing current context configuration.</li> or <li>
 * {@link #getStringControl()} to access context control.</li>
 * <p/>
 * <br/>
 * The action should also be configured inside this constructor using the
 * setXxx(...) methods. For example:
 * <li>
 * {@link #setImageDescriptor(org.eclipse.jface.resource.ImageDescriptor)}</li>,
 * <li>{@link #setText(String)}</li>,
 * <li>{@link #setToolTipText(String)}</li>
 * 
 * @author Jan Arciuchiewicz
 */
public abstract class StringControlAction extends Action {

    protected StringControl stringControl;

    /**
     * Creates a new action with no text and no image.
     * <p>
     * Configure the action inside this constructor using the setXxx(...)
     * methods. For example use:
     * <li>
     * {@link #setImageDescriptor(org.eclipse.jface.resource.ImageDescriptor)}</li>,
     * <li>{@link #setText(String)}</li>,
     * <li>{@link #setToolTipText(String)}</li>
     * </p>
     */
    public StringControlAction() {
        super();
    }

    /**
     * @param stringControl
     *            the stringControl to set
     */
    /* package */void setStringControl(StringControl stringControl) {
        this.stringControl = stringControl;
    }

    /**
     * 
     * @return the stringControl
     */
    public StringControl getStringControl() {
        return stringControl;
    }

    /**
     * Runs this action passing a temporary copy of a server reflecting current
     * server configuration.
     */
    public void runWithServer(Server server) {
        run();
    }

}
