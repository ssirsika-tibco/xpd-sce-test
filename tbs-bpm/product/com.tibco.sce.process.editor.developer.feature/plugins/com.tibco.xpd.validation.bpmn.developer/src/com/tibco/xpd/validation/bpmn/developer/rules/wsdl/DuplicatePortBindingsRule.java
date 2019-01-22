/*
 * Copyright (c) TIBCO Software Inc 2004, 2018. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules.wsdl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.eclipse.wst.wsdl.Binding;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Port;
import org.eclipse.wst.wsdl.Service;

import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Sid XPD-8438
 * 
 * As we index SERVICE_OPERATION as the URI to the binding-Operation it is a
 * problem if 2 ports refer to the same port-type binding.
 * 
 * So we need to track if we are adding multiples and ignore them when we index
 * them.
 * 
 * This means that some ports will not be included, but majority of apps use
 * port-type and late binding anyway so will not be affected. So we should also
 * highlight as a validation warning.
 *
 * @author aallway
 * @since 1 Nov 2018
 */
public class DuplicatePortBindingsRule implements IValidationRule {

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

            /*
             * Check for duplicate bindings (same as WsdlIndexProvider) and warn
             * about duplicates.
             */
            Set<QName> bindingsAdded = new HashSet<QName>();

            List<Service> services = definition.getEServices();
            for (Service service : services) {

                List<Port> ports = service.getEPorts();
                for (Port port : ports) {
                    Binding binding = port.getEBinding();

                    if (binding != null) {
                        QName bindingName = binding.getQName();

                        if (bindingsAdded.contains(bindingName)) {
                            String problemLocation = String.format(
                                    Messages.BindingOperationStyleRule_WSDLPortLocation_label,
                                    port.getName());

                            List<String> params = new ArrayList<>();
                            params.add(port.getName());
                            params.add(binding.getQName().toString());

                            scope.createIssue(
                                    "bpmn.dev.multiplePortsWithSameBinding",
                                    problemLocation,
                                    definition.eResource().getURIFragment(port),
                                    params);

                        } else {
                            bindingsAdded.add(bindingName);
                        }
                    }
                }
            }
        }
    }

}
