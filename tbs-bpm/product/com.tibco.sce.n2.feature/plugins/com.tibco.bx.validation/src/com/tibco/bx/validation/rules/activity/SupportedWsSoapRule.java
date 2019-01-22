/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import javax.wsdl.Binding;
import javax.wsdl.BindingOperation;

import org.eclipse.wst.wsdl.binding.soap.SOAPBinding;
import org.eclipse.wst.wsdl.binding.soap.SOAPOperation;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;

/**
 * Validate against use of soap over http v 1.2 on web service activities and
 * also validate that soap operation and binding styles match.
 * 
 * @author Jan Arciuchiewicz
 * @since 3.5
 */
public class SupportedWsSoapRule extends FlowContainerValidationRule {

    private static final String ISSUE_SOAP_OPERATION_AND_BINDING_STYLE_DONT_MATCH =
            "bx.soapOperationAndBindingStyleDontMatch"; //$NON-NLS-1$

    @Override
    public void validate(FlowContainer container) {
        for (Activity activity : container.getActivities()) {
            WsdlServiceKey currentKey =
                    ProcessUIUtil.getSpecificWsdlServiceKey(activity);
            if (currentKey != null) {
                BindingOperation bindingOperation =
                        WsdlIndexerUtil.getBindingOperation(WorkingCopyUtil
                                .getProjectFor(activity),
                                currentKey,
                                true,
                                true);
                Binding binding =
                        WsdlIndexerUtil.getBinding(WorkingCopyUtil
                                .getProjectFor(activity),
                                currentKey,
                                true,
                                true);
                if (bindingOperation != null && binding != null
                        && !hasMatchingBindingStyle(bindingOperation, binding)) {
                    addIssue(ISSUE_SOAP_OPERATION_AND_BINDING_STYLE_DONT_MATCH,
                            activity);
                }
            }
        }
    }

    /**
     * Returns 'true' if operation's binding style match it's binding style.
     */
    private boolean hasMatchingBindingStyle(BindingOperation bindingOperation,
            Binding binding) {
        String bindingOperationStyle = null;
        for (Object extElement : bindingOperation.getExtensibilityElements()) {
            if (extElement instanceof SOAPOperation) {
                SOAPOperation soapOperation = (SOAPOperation) extElement;
                bindingOperationStyle = soapOperation.getStyle();
            }
        }
        String bindingStyle = null;
        for (Object extElement : binding.getExtensibilityElements()) {
            if (extElement instanceof SOAPBinding) {
                SOAPBinding soapBinding = (SOAPBinding) extElement;
                bindingStyle = soapBinding.getStyle();
            }
        }
        /*
         * XPD-2897: bindingOperationStyle can be 'null' it doesn't need to be
         * repeated on the operation level.
         */
        if ((bindingStyle != null && bindingStyle
                .equalsIgnoreCase(bindingOperationStyle))
                || bindingOperationStyle == null) {
            return true;
        }
        return false;
    }
}
