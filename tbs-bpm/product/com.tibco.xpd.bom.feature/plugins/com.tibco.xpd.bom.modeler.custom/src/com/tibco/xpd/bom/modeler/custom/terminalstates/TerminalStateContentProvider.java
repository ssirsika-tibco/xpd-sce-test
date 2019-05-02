/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.bom.modeler.custom.terminalstates;

import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;

/**
 * Content provider for terminal states for a Case Attribute Property.
 *
 * @author nwilson
 * @since 2 Apr 2019
 */
public class TerminalStateContentProvider
        implements IStructuredContentProvider {

    /**
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    @Override
    public Object[] getElements(Object inputElement) {
        return getTerminalStates(inputElement).toArray();
    }

    public List<EnumerationLiteral> getTerminalStates(Object inputElement) {
        if (inputElement instanceof Property) {
            Property property = (Property) inputElement;
            if (GlobalDataProfileManager.getInstance().isCaseState(property)) {
                Type type = property.getType();
                if (type instanceof Enumeration) {
                    Enumeration enumeration = (Enumeration) type;
                    return enumeration.getOwnedLiterals();
                }
            }
        }
        return Collections.emptyList();
    }

}
