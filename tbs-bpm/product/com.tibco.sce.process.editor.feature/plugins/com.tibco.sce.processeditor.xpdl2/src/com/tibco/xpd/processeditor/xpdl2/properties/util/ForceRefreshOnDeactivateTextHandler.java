/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.util;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.ui.properties.AbstractEObjectSection;
import com.tibco.xpd.ui.properties.CommandTextFieldHandler;

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
public class ForceRefreshOnDeactivateTextHandler extends
        CommandTextFieldHandler {
    private AbstractEObjectSection commandProvider;

    public ForceRefreshOnDeactivateTextHandler(Text text,
            AbstractEObjectSection commandProvider) {
        super(text, commandProvider);
        this.commandProvider = commandProvider;

    }

    @Override
    protected void doDeactivate(Event event) {
        super.doDeactivate(event);

        // Force a refresh on deactivate - this should ensure that the property
        // sheet's doRefresh gets a chance to reset the notifier objects so that
        // it can start listening to the sub-eobject-element that this text
        // fields
        // attribute belongs to.
        commandProvider.forceNotifyChanged();

    }
}
