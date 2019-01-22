/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Section to configure of SOAP security policy composed inside a Group
 * Container to be used for SOAP JMS Consumer Section.
 * 
 * @author aprasad
 */
public class WsSoapSecurityPolicyGroupSection extends
        WsSoapSecurityPolicySection {

    public WsSoapSecurityPolicyGroupSection(EClass eClass) {
        super(eClass);
    }

    /**
     * Returns Container {@link Composite} for the controls of this Section.
     * 
     * @param parent
     * @param toolkit
     * @return {@link Group} Container.
     */
    @Override
    protected Composite getContainer(Composite parent, XpdFormToolkit toolkit) {

        Group securityGroup =
                toolkit.createGroup(parent,
                        Messages.WsSoapSecurityPolicySection_SecurityConfig_label);

        GridDataFactory.fillDefaults().grab(true, true).applyTo(securityGroup);

        GridLayoutFactory.fillDefaults().numColumns(2).margins(1, 5)
                .applyTo(securityGroup);

        return securityGroup;
    }

    /**
     * Returns the style for the Policy Combo drop down . Set BORDER style, as
     * the border of the CCombo is not visible when put inside a Group.
     * 
     * @return SWT.READ_ONLY & BORDER.
     */
    @Override
    protected int getPolicyComboStyle() {
        return SWT.READ_ONLY | SWT.BORDER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        Composite parentContainer = toolkit.createComposite(parent);

        GridLayoutFactory.fillDefaults().equalWidth(true).margins(1, 0)
                .applyTo(parentContainer);
        /*
         * Did not check for type of Layout on parent, as it is already
         * implemented taken into consideration that parent has GridLayout set
         */
        GridDataFactory.fillDefaults().span(1, 0).grab(true, true)
                .applyTo(parentContainer);

        createSeurityPolicyControls(parentContainer, toolkit);
        return parentContainer;
    }

}
