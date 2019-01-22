/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Spinner;

import com.tibco.xpd.ui.properties.AbstractXpdSection;
import com.tibco.xpd.ui.properties.CommandSpinnerHandler;

/**
 * In order to work around <b>Defect 32037 Subclass of AbstractEObjectSection is
 * not always given opportunity to update list of refresh notifiers at
 * appropriate times.</b> this class ensures that the property sheet gets a
 * chance to update the list of model objects being listened to by forcing the
 * base AbstractEObjectSection to perform a complete refresh (including asking
 * property section for the latest list of notifier objects).
 * 
 * @author aallway
 * 
 */
public class ForceRefreshOnDeactivateSpinnerHandler extends
        CommandSpinnerHandler {
    private AbstractXpdSection commandProvider;

    public ForceRefreshOnDeactivateSpinnerHandler(Spinner spinner,
            AbstractXpdSection commandProvider) {
        super(spinner, commandProvider);
        this.commandProvider = commandProvider;

    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
     */
    public void handleEvent(Event event) {
        super.handleEvent(event);
        
        switch (event.type) {
        case SWT.Deactivate:
            commandProvider.refresh();
            break;
        }
        
    }

}
