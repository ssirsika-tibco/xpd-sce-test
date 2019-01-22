/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.internal.wsdl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.wst.wsdl.Binding;
import org.eclipse.wst.wsdl.BindingOperation;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Port;
import org.eclipse.wst.wsdl.Service;

import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.util.WsdlIndexerUtil.WsdlElementType;
import com.tibco.xpd.util.WsdlIndexerUtil.WsdlIndexerAttributes;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Duplicate <code>Operation</code> rule for the bound (concrete) operation.
 * 
 * @author njpatel
 * 
 */
public class DuplicateBoundOperationRule extends DuplicateOperationRule {

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     */
    public Class<?> getTargetClass() {
        return Service.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd
     * .validation.provider.IValidationScope, java.lang.Object)
     */
    public void validate(IValidationScope scope, Object o) {

        if (o instanceof Service) {
            Service service = (Service) o;
            IProject project = getProject(service);

            if (project != null) {
                Collection<?> values = service.getPorts().values();

                if (values != null) {
                    for (Object obj : values) {
                        if (obj instanceof Port) {
                            Port port = (Port) obj;
                            Binding binding = port.getEBinding();

                            if (binding != null) {
                                List<?> operations =
                                        binding.getBindingOperations();

                                if (operations != null) {
                                    for (Object next : operations) {
                                        BindingOperation bindingOp =
                                                (BindingOperation) next;
                                        Operation operation =
                                                bindingOp.getEOperation();

                                        if (operation != null) {
                                            Map<WsdlIndexerAttributes, String> attrs =
                                                    new HashMap<WsdlIndexerAttributes, String>();
                                            attrs
                                                    .put(WsdlIndexerAttributes.QUALIFICATION,
                                                            WsdlIndexerUtil
                                                                    .createQualification(service
                                                                            .getQName()
                                                                            .getLocalPart(),
                                                                            port
                                                                                    .getName()));
                                            attrs
                                                    .put(WsdlIndexerAttributes.TARGET_NAMESPACE,
                                                            service
                                                                    .getQName()
                                                                    .getNamespaceURI());

                                            Collection<IndexerItem> items =
                                                    WsdlIndexerUtil
                                                            .getIndexedItems(operation
                                                                    .getName(),
                                                                    WsdlElementType.SERVICE_OPERATION,
                                                                    null,
                                                                    attrs,
                                                                    true,
                                                                    true);

                                            if (items != null
                                                    && items.size() > 1) {
                                                // Multiple operations
                                                addOperationIssue(project,
                                                        scope,
                                                        DUPLICATE_BOUND_OPERATION_ISSUE_ID,
                                                        operation,
                                                        EcoreUtil
                                                                .getURI(bindingOp)
                                                                .toString(),
                                                        items);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
