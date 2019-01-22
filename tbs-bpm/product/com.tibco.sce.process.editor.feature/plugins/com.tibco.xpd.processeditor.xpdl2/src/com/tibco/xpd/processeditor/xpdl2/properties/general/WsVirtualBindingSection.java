/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Section for details of Virtual binding.
 * 
 * @author Jan Arciuchiewicz
 */
public class WsVirtualBindingSection extends WsBindingSection {

    public WsVirtualBindingSection(EClass eClass) {
        super(eClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        GridDataFactory.swtDefaults().applyTo(toolkit.createLabel(parent,
                Messages.WsVirtualBindingSection_NoProperties_desc));
        return parent;
    }
}
