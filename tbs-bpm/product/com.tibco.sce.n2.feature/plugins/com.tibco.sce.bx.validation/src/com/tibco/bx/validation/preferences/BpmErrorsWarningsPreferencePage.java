package com.tibco.bx.validation.preferences;

import com.tibco.bx.validation.BxValidationPlugin;
import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.validation.preferences.page.AbstractValidationPreferencePage;

public class BpmErrorsWarningsPreferencePage extends
        AbstractValidationPreferencePage {

    /**
     * @param preferenceId
     */
    public BpmErrorsWarningsPreferencePage() {
        super("com.tibco.bx.bpmGeneral"); //$NON-NLS-1$
        setDescription(Messages.BpmErrorsWarningsPreferencePage_desc); 
        setDialogSettings(BxValidationPlugin.getDefault().getDialogSettings(),
                "generalValidationPreference"); //$NON-NLS-1$
    }

}
