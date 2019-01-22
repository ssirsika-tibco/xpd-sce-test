/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.internal.wsdl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.PortType;

import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.util.WsdlIndexerUtil.WsdlElementType;
import com.tibco.xpd.util.WsdlIndexerUtil.WsdlIndexerAttributes;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Duplicate <code>Operation</code> rule for the unbound (abstract) operation.
 * 
 * @author njpatel
 * 
 */
public class DuplicateUnboundOperationRule extends DuplicateOperationRule {

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     */
    @Override
    public Class<?> getTargetClass() {
        return PortType.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd
     * .validation.provider.IValidationScope, java.lang.Object)
     */
    @Override
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof PortType) {
            PortType type = (PortType) o;
            IProject project = getProject(type);

            if (project != null) {
                List<?> operations = type.getOperations();

                if (operations != null) {
                    for (Object obj : operations) {
                        if (obj instanceof Operation) {
                            Operation op = (Operation) obj;
                            Map<WsdlIndexerAttributes, String> attrs =
                                    new HashMap<WsdlIndexerAttributes, String>();

                            attrs.put(WsdlIndexerAttributes.QUALIFICATION, type
                                    .getQName().getLocalPart());

                            attrs.put(WsdlIndexerAttributes.TARGET_NAMESPACE,
                                    type.getQName().getNamespaceURI());

                            Collection<IndexerItem> items =
                                    WsdlIndexerUtil.getIndexedItems(op
                                            .getName(),
                                            WsdlElementType.PORTTYPE_OPERATION,
                                            null,
                                            attrs,
                                            true,
                                            true);

                            if (items != null && items.size() > 1) {

                                /*
                                 * XPD-4070: Check whether the corresponding
                                 * wsdl files still exist. Prior to this check
                                 * sometimes this rule failed due to the fact
                                 * that a indexer update (related to a deleted
                                 * wsdl) was still pending
                                 */
                                int itemsExisting = 0;

                                for (IndexerItem indexerItem : items) {
                                    String wsdlFileLoc =
                                            indexerItem
                                                    .get(WsdlIndexerAttributes.PATH
                                                            .toString());
                                    if (wsdlFileLoc != null
                                            && !wsdlFileLoc.trim().isEmpty()) {
                                        IFile wsdlFile =
                                                ResourcesPlugin
                                                        .getWorkspace()
                                                        .getRoot()
                                                        .getFile(new Path(
                                                                wsdlFileLoc));
                                        if (wsdlFile != null
                                                && wsdlFile.exists()) {
                                            itemsExisting++;
                                        }
                                    }
                                }

                                if (itemsExisting > 1) {
                                    // Multiple operations
                                    addOperationIssue(project,
                                            scope,
                                            DUPLICATE_UNBOUND_OPERATION_ISSUE_ID,
                                            op,
                                            EcoreUtil.getURI(op).toString(),
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
