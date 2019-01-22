/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.ui;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolItem;

/**
 * Command handler for drop down toolbar buttons where clicking on the main
 * button part is the same as clicking on the drop-down menu part to it's right
 * (i.e. it displays the dropdown menu list for the button.
 * 
 * @author aallway
 * @since 7 Dec 2012
 */
public class DropdownToolbarButtonHandler extends AbstractHandler {

    /**
     * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     * 
     * @param event
     * @return
     * @throws ExecutionException
     */
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        /*
         * The org.eclipse.ui.menus extension point does not allow for
         * main-button-drops list down behaviour at all, so we have to fudge
         * this by simulating a selection event on the dropdown part of the
         * button.
         * 
         * Event = SWT.SELECTION Detail = 4 (!! Not sure where this is taken
         * from but is hard coded into CommandContributionItem.openDropDownMenu)
         */
        if (event.getTrigger() instanceof Event) {
            Event triggerEvent = (Event) event.getTrigger();

            if (triggerEvent.widget instanceof ToolItem) {
                ToolItem toolItem = (ToolItem) triggerEvent.widget;

                Listener[] listeners = toolItem.getListeners(SWT.Selection);

                if (listeners != null && listeners.length > 0) {
                    Event dropdownSelEvent = new Event();

                    /*
                     * FUDGE-FACTOR-10!!! Event = SWT.SELECTION Detail = 4 (!!
                     * Not sure where this is taken from but is hard coded into
                     * CommandContributionItem.openDropDownMenu)
                     */
                    dropdownSelEvent.type = SWT.Selection;
                    dropdownSelEvent.detail = 4;

                    dropdownSelEvent.widget = toolItem;
                    dropdownSelEvent.y = toolItem.getBounds().height;

                    listeners[0].handleEvent(dropdownSelEvent);
                }
            }
        }
        return null;
    }

}
