/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.xpd.processwidget.policies.EditPolicyEnablementProvider;

/**
 * Action that can enable / disable edit policy.
 * <p>
 * Note: this requires co-operation from the Edit Policy itself (via a class
 * that implements PolicyEnablementProvider).
 * 
 * @author aallway
 * @since 3.2
 */
public class EnableDisableEditPolicyAction extends Action {

    private EditPolicyEnablementProvider policyEnablementProvider = null;

    private String editPolicyId;

    /**
     * Create action that can enable / disable the given edit policy.
     * 
     * @param label
     * @param img
     * @param editPolicyId
     */
    public EnableDisableEditPolicyAction(
            EditPolicyEnablementProvider policyEnablementProvider,
            String label, ImageDescriptor img, String editPolicyId) {
        super(label, img);

        this.editPolicyId = editPolicyId;
        this.policyEnablementProvider = policyEnablementProvider;
        
        setChecked(policyEnablementProvider.isPolicyEnabled(editPolicyId));
        
        return;
    }

    @Override
    public void run() {
        policyEnablementProvider.setPolicyEnabled(editPolicyId, isChecked());
        return;
    }
  
    
}
