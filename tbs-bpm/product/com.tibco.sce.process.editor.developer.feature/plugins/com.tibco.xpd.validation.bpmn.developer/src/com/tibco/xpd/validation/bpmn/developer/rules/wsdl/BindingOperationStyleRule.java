/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules.wsdl;

import java.util.List;

import org.eclipse.wst.wsdl.Binding;
import org.eclipse.wst.wsdl.BindingOperation;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.binding.soap.SOAPBinding;
import org.eclipse.wst.wsdl.binding.soap.SOAPOperation;

import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * XPD-1338 requires that User WSDL shouldn't have a construct where the binding
 * style attribute is not the same as the style attribute on the operation.
 * 
 * @author rsomayaj
 * @since 15 Dec 2010
 */
public class BindingOperationStyleRule implements IValidationRule {

    private static String STYLE_ATTRIB_MATCH_ISSUE_ID =
            "binding_operation_style_attribute_match"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    @Override
    public Class<?> getTargetClass() {
        return Definition.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    @Override
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof Definition) {
            Definition definition = (Definition) o;
            List bindings = definition.getEBindings();

            for (Object bindingObj : bindings) {
                if (bindingObj instanceof Binding) {
                    Binding binding = (Binding) bindingObj;
                    List bindingOperations = binding.getEBindingOperations();
                    List extensibilityElements =
                            binding.getEExtensibilityElements();
                    String soapBindingStyle = null;
                    for (Object extEl : extensibilityElements) {
                        if (extEl instanceof SOAPBinding) {
                            SOAPBinding soapBinding = (SOAPBinding) extEl;
                            soapBindingStyle = soapBinding.getStyle();
                            break;
                        }
                    }

                    for (Object bindingOpObj : bindingOperations) {
                        if (bindingOpObj instanceof BindingOperation) {
                            BindingOperation bindingOp =
                                    (BindingOperation) bindingOpObj;
                            String bindingOperationStyle = null;

                            List eExtensibilityElements =
                                    bindingOp.getEExtensibilityElements();

                            for (Object extEl : eExtensibilityElements) {
                                if (extEl instanceof SOAPOperation) {
                                    SOAPOperation soapOperation =
                                            (SOAPOperation) extEl;
                                    bindingOperationStyle =
                                            soapOperation.getStyle();

                                }
                            }

                            if (bindingOperationStyle != null
                                    && !(bindingOperationStyle
                                            .equals(soapBindingStyle))) {
                                String problemLocation =
                                        String.format(Messages.BindingOperationStyleRule_WSDLBindingLocation_label,
                                                binding.getQName()
                                                        .getLocalPart());
                                scope.createIssue(STYLE_ATTRIB_MATCH_ISSUE_ID,
                                        problemLocation,
                                        definition.eResource()
                                                .getURIFragment(bindingOp));
                            }
                        }
                    }

                }
            }
        }
    }

}
