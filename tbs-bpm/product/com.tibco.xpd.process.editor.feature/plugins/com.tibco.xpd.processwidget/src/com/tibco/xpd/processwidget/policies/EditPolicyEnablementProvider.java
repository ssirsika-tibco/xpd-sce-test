/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.policies;

import com.tibco.xpd.processwidget.actions.EnableDisableEditPolicyAction;

/**
 * Simple interface for controlling enablement of edit policies.
 * <p>
 * A policy can use the interface to find out whether it should be enabled.
 * <p>
 * The {@link EnableDisableEditPolicyAction} can use the interface the interface
 * to get / set enablement of policies.
 * <p>
 * Then all you need is the provider itself that stores the enablement state.
 * 
 * @author aallway
 * @since 3.2
 */
public interface EditPolicyEnablementProvider {
    boolean isPolicyEnabled(String policyId);

    void setPolicyEnabled(String policyId, boolean enabled);
}