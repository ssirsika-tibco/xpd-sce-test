/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.general.AbstractGeneralSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * General section for the {@link OrgUnitRelationship}. This will allow user to
 * set the is hierarchical selection for the relationship.
 * 
 * @author njpatel
 * 
 */
public class OrgUnitRelationshipSection extends AbstractGeneralSection {

    private Button isHierarchicalBtn;

    private boolean doRefreshTabs;

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        // Create blank label to align the check box in the second column
        createLabel(root, toolkit, ""); //$NON-NLS-1$

        isHierarchicalBtn =
                toolkit.createButton(root,
                        Messages.OrgUnitRelationshipSection_isHierarchical_checkbox_label,
                        SWT.CHECK,
                        "is-hierarchical-checkbox"); //$NON-NLS-1$
        isHierarchicalBtn
                .setToolTipText(Messages.OrgUnitRelationshipSection_isHierarchical_checkbox_tooltip);
        manageControl(isHierarchicalBtn);

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        EObject input = getInput();

        if (input instanceof OrgUnitRelationship) {
            if (obj == isHierarchicalBtn) {
                doRefreshTabs = true;
                return SetCommand.create(getEditingDomain(),
                        input,
                        OMPackage.eINSTANCE
                                .getOrgUnitRelationship_IsHierarchical(),
                        isHierarchicalBtn.getSelection());
            }
        }
        return null;
    }

    @Override
    protected void doRefresh() {
        if (isHierarchicalBtn != null && !isHierarchicalBtn.isDisposed()) {
            EObject input = getInput();
            boolean isHierarchical = false;
            if (input instanceof OrgUnitRelationship) {
                OrgUnitRelationship relationship = (OrgUnitRelationship) input;
                isHierarchical = relationship.isIsHierarchical();

                boolean isEnabled = true;
                if (!isHierarchical) {
                    OrgUnit from = relationship.getFrom();
                    OrgUnit to = relationship.getTo();

                    if (from != null && to != null) {
                        if (OMUtil.isOrgUnitInHierarchy(from, to)) {

                            isEnabled = false;
                        } else {
                            /*
                             * If the target already has a hierarchical
                             * relationship and this is an association then
                             * disable the checkbox as the target cannot have
                             * more than 1 parent.
                             */
                            isEnabled =
                                    (!(to.getIncomingHierachicalRelationship() != null));
                        }
                    }
                }
                isHierarchicalBtn.setEnabled(isEnabled);
            }
            isHierarchicalBtn.setSelection(isHierarchical);

            if (doRefreshTabs) {
                doRefreshTabs = false;
                refreshTabs();
            }
        }
    }

}
