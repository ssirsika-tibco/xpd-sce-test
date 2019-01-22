/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * @author rsomayaj
 * 
 * 
 */
public class StartMethodMessageImplSection extends EventMessageImplSection {

    public StartMethodMessageImplSection() {
        super(XpdExtensionPackage.eINSTANCE.getStartMethod());
    }

    @Override
    protected WebServiceDetailsSection createWebServiceDetailsSection() {
        return new InterfaceMethodWebServiceDetailsSection();
    }

    // @Override
    // public void setInput(Collection items) {
    // super.setInput(items);
    // }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit tk) {

        Control root = super.doCreateControls(parent, tk);
        impCombo.setEnabled(false);

        return root;
    }

    // @Override
    // protected void doRefresh() {
    // super.doRefresh();
    // webServiceDetailsContainer.setVisible(true);
    // }
}
