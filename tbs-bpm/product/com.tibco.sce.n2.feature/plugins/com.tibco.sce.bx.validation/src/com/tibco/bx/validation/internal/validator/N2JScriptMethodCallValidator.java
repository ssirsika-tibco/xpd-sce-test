/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.internal.validator;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.globaldata.CaseUMLScriptRelevantData;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.validator.jscript.JScriptMethodCallValidator;

/**
 * 
 * ExpressionValidator class that handles the validation of the method call
 * expression ie: ClassName.methodCallToValidate(param1);
 * 
 * Introduced this class during XPD-5943 to validate assignment of a case ref
 * type to an Object (for instance static global function Number(Object) must be
 * validated for case ref types)
 * 
 * @author bharge
 * @since 11 Mar 2014
 */
public class N2JScriptMethodCallValidator extends JScriptMethodCallValidator {

    /**
     * @see com.tibco.xpd.script.parser.internal.validator.jscript.AbstractExpressionValidator#isExplicitAssignmentAllowance(com.tibco.xpd.script.model.client.IScriptRelevantData,
     *      com.tibco.xpd.script.model.client.IScriptRelevantData)
     * 
     * @param lhsDataType
     * @param rhsDataType
     * @return
     */
    @Override
    protected boolean isExplicitAssignmentAllowance(
            IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType) {

        /*
         * XPD-5943: static functions like Number(Object) must be validated for
         * case ref types
         */
        if (rhsDataType instanceof CaseUMLScriptRelevantData) {

            if (JScriptUtils.isEObjectType(lhsDataType)
                    || JsConsts.OBJECT.equals(lhsDataType.getType())) {
                /*
                 * XPD-5943: Return false if a case ref type is assigned to an
                 * Object or a BOM class.
                 */
                return false;
            }
        }
        return super.isExplicitAssignmentAllowance(lhsDataType, rhsDataType);
    }
}
