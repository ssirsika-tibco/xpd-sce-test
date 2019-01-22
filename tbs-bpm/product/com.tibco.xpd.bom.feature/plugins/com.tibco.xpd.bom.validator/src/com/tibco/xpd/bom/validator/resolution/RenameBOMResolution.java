/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.validator.resolution;

import org.eclipse.jface.dialogs.IInputValidator;

import com.tibco.xpd.bom.validator.internal.Messages;

/**
 * Validation issue resolution for invalid concept names.
 * 
 * @author njpatel
 * 
 */
public class RenameBOMResolution extends AbstractRenameResolution {

    /**
     * Default constructor
     */
    public RenameBOMResolution() {
        super(Messages.RenameConceptResolution_rename_concept_action);
    }

    @Override
    protected IInputValidator getValidator() {
        return new IInputValidator() {

            public String isValid(String newText) {
                String err = null;

                if (newText.length() == 0) {
                    err = Messages.RenameConceptResolution_name_required_message;
                    //TODO
//                } else if (!ValidationHelper.isValidJavaId(newText)) {
                    err = Messages.RenameConceptResolution_concept_name_wrong_format_message;
                }

                return err;
            }
        };
    }

}
