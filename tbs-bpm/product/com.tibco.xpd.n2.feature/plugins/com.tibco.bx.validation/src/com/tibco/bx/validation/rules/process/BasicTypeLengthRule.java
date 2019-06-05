/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.NamedElement;

/**
 * 
 * This rule enforces the length of the integer type fields to be a maximum of
 * 15 digits. (XPD-5700)
 * 
 * @author bharge
 * 
 */
public class BasicTypeLengthRule extends
        com.tibco.xpd.validation.bpmn.rules.BasicTypeLengthRule {

    /**
     * 
     */
    private static final int INTEGER_MAX_LENGTH =
            PrimitivesUtil.MAX_FIXED_POINT_NUMBER_LENGTH;

    private static final String INTEGER_CROSSES_MAX_LENGTH =
            "bx.integerCrossMaxLength"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.BasicTypeLengthRule#validateLengthOrScale(com.tibco.xpd.xpdl2.NamedElement,
     *      com.tibco.xpd.xpdl2.BasicType)
     * 
     * @param namedElement
     * @param basicType
     */
    @Override
    protected void validateLengthOrScale(NamedElement namedElement,
            BasicType basicType) {

        // super.validateLengthOrScale(namedElement, basicType);

        if (basicType != null) {
            if (basicType.getType() != null
                    && basicType.getType()
                            .equals(BasicTypeType.INTEGER_LITERAL)) {
                Short value = 0;

                if (basicType.getPrecision() != null) {
                    value = basicType.getPrecision().getValue();
                    if (value > INTEGER_MAX_LENGTH) {
                        addIssue(INTEGER_CROSSES_MAX_LENGTH, namedElement);
                    }
                }
            }
        }
    }
}
