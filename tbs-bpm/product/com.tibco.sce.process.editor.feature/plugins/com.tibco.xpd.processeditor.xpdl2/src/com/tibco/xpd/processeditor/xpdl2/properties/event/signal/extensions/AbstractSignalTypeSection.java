/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Abstract signal type section that allows user to contribute new signal types
 * to signal event(and manage their lifecycle) via the
 * <b>"com.tibco.xpd.processeditor.xpdl2.signalType"</b> extension point .
 * <p>
 * Note: the user is expected to return the command to set the signal type
 * through the method{@link #getSetSelectedSignalTypeCommand(Object)}
 * <p>
 * Additionally the user may create Event Handler controls using the method
 * {@link #createAdditionalEventHandlerInitializerControls(Composite, XpdFormToolkit)}
 * 
 * @author kthombar
 * @since Jan 27, 2015
 */
public abstract class AbstractSignalTypeSection extends
        AbstractFilteredTransactionalSection {
    /**
 * 
 */
    public AbstractSignalTypeSection() {
        this(null);
    }

    /**
     * 
     * @param eClass
     */
    public AbstractSignalTypeSection(EClass eClass) {
        super(eClass);
    }

    /**
     * 
     * @return should return <code>true</code> if the Signal Type contributed by
     *         the current section is selected else return <code>false</code>
     */
    abstract public boolean isSelectedSignalType();

    /**
     * 
     * @param obj
     * @return Command to set the SignalType which is contributed by the
     *         section.
     */
    abstract public Command getSetSelectedSignalTypeCommand(Object obj);

    /**
     * 
     * @return the tooltip to be shown for the signal type button.
     */
    abstract public String getSignalTypeToolTip();

    /**
     * Allows creation of additional event handler initializer controls.
     * 
     * @param parent
     *            the Event Handler Group
     * @param toolkit
     * @return
     */
    public Composite createAdditionalEventHandlerInitializerControls(
            Composite parent, XpdFormToolkit toolkit) {
        return null;
    }

    /**
     * Get the selected input object as an activity
     * 
     * @return activity for event or null on error.
     */
    protected Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            Activity act = (Activity) o;

            return act;
        }
        return null;
    }

    /**
     * 
     * @param activity
     *            the activity in focus
     * @return <code>true</code> if event sub process event handler controls
     *         should be shown for the passed activity else return
     *         <code>false</code>
     */
    abstract public boolean isEventSubProcessEventHandlerControlsApplicabale(
            Activity activity);

    /**
     * 
     * @return <code>true</code> if the Flow Controls are applicable in the
     *         event handler controls else return <code>false</code>
     */
    abstract public boolean isFlowControlsApplicable();

    /**
     * 
     * @return <code>true</code> if the Flow Controls are supposed to be
     *         disabled in the event handler controls else return
     *         <code>false</code>
     */
    abstract public boolean shouldDisableFlowControls();

    /**
     * 
     * @return <code>true</code> if the Initialiser activity Controls are
     *         applicable in the event handler controls else return
     *         <code>false</code>
     */
    abstract public boolean isInitialiserActivityControlsApplicable();
}
