/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.validator.preferences;

import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.bom.validator.internal.Messages;
import com.tibco.xpd.validation.preferences.page.AbstractValidationPreferencePage;

/**
 * Concept validation issues' preference page. This preference page allows the
 * selection of the severity level of each validation issue
 * 
 * @author njpatel
 * 
 */
public class BOMValidationPreferencePage extends
        AbstractValidationPreferencePage {

    /**
     * Default constructor.
     */
    public BOMValidationPreferencePage() {
        super("com.tibco.xpd.bom.pref"); //$NON-NLS-1$
        setDescription(Messages.ConceptValidationPreferencePage_page_message);
        setDialogSettings(
                BOMValidatorActivator.getDefault().getDialogSettings(),
                "conceptValidationPreference"); //$NON-NLS-1$
    }
}