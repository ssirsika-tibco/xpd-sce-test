/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.parts;

import org.eclipse.emf.common.notify.AdapterFactory;

import com.tibco.xpd.processwidget.policies.connectgadget.ClickOrDragCreateConnectionPolicy;

/**
 * Editparts representing nodes within lanes/embedded sub-process that are
 * connectable.
 * 
 * @author aallway
 * @since 3.2
 */
public abstract class BaseConnectableNodeEditPart extends BaseGraphicalEditPart {

    public BaseConnectableNodeEditPart(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    @Override
    protected void createClickOrDragGadgetPolicies() {
        super.createClickOrDragGadgetPolicies();
        
        installEditPolicy(ClickOrDragCreateConnectionPolicy.EDIT_POLICY_ID,
                new ClickOrDragCreateConnectionPolicy(getAdapterFactory(),
                        getEditingDomain(), getProcessWidget()
                                .getEditPolicyEnablementProvider()));
        return;
    }
}
