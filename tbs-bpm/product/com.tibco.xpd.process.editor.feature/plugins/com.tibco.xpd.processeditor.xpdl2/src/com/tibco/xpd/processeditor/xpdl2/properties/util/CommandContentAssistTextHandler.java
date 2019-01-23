/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.util;

import org.eclipse.emf.common.command.Command;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldChangedListener;
import com.tibco.xpd.ui.properties.CommandProvider;

/**
 * Handles command creation for ContentAssistText fields.
 * 
 * @author nwilson
 * @since 25 Sep 2014
 */
public class CommandContentAssistTextHandler implements
        FixedValueFieldChangedListener {
    /**
     * The content assist text control.
     */
    private ContentAssistText control;

    /**
     * A command provider for this control.
     */
    private CommandProvider provider;

    /**
     * @param control
     *            The content assist text control.
     * @param provider
     *            A command provider for this control.
     */
    public CommandContentAssistTextHandler(ContentAssistText control,
            CommandProvider provider) {
        this.control = control;
        this.provider = provider;
        control.addValueChangeListener(this);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2
     *      .properties.util.FixedValueFieldAssistHelper
     *      .FixedValueFieldChangedListener#fixedValueFieldChanged
     *      (java.lang.Object)
     * 
     * @param newValue
     *            The new field value.
     */
    @Override
    public void fixedValueFieldChanged(Object newValue) {
        Text txt = control.getText();
        Command cmd = provider.getCommand(txt);
        if (cmd != null && cmd.canExecute()) {
            provider.getEditingDomain().getCommandStack().execute(cmd);
        }
    }
}
