/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;

/**
 * @author NWilson
 * 
 */
public class TypeDeclarationNameSection extends NamedElementSection {

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    public boolean select(Object toTest) {
        boolean select = super.select(toTest);
        Object item = getBaseSelectObject(toTest);
        if (!(item instanceof TypeDeclaration)) {
            select = false;
        }
        return select;
    }

    @Override
    protected String getDuplicateNameMessage(EObject duplicate) {
        return Messages.TypeDeclarationPropertySection_DuplicateType_longdesc;
    }

    protected boolean allowLeadingNumerics() {
        return false;
    }
}
