/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processinterface.properties;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.NamedElementSection;
import com.tibco.xpd.xpdExtension.ProcessInterface;

/**
 * @author NWilson
 * 
 */
public class ProcessInterfaceNameSection extends NamedElementSection {

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    public boolean select(Object toTest) {
        boolean select = super.select(toTest);
        Object item = getBaseSelectObject(toTest);
        if (!(item instanceof ProcessInterface)) {
            select = false;
        }
        return select;
    }

    @Override
    protected String getDuplicateNameMessage(EObject duplicate) {
        return Messages.ProcessInterfacePropertySection_NameExistsError_shortmsg;
    }

}
