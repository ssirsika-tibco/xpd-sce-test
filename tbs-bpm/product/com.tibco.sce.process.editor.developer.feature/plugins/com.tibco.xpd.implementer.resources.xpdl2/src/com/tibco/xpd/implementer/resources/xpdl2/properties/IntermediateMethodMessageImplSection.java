/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * 
 * Property section for {@link IntermediateMethod} of a {@link ProcessInterface}
 * 
 * 
 * @author rsomayaj
 * 
 * 
 */
public class IntermediateMethodMessageImplSection extends
        EventMessageImplSection {

    public IntermediateMethodMessageImplSection() {
        super(XpdExtensionPackage.eINSTANCE.getIntermediateMethod());
    }

    @Override
    protected WebServiceDetailsSection createWebServiceDetailsSection() {
        return new InterfaceMethodWebServiceDetailsSection();
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit tk) {

        Control root = super.doCreateControls(parent, tk);
        impCombo.setEnabled(false);

        return root;
    }

    // @Override
    // protected void doRefresh() {
    // super.doRefresh();
    // detailsPageBook.showPage(WEB_SERVICE_IMPL_NAME);
    // }
    //
}
