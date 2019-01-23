/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.Xpdl2Factory;

/**
 * Utility class for getting and setting extended attributes.
 * 
 * @author nwilson
 * @since 16 Feb 2015
 */
public class ExtendedAttributeManager {

    public void setAttribute(ExtendedAttributesContainer container,
            String name, String value) {
        if (container != null) {
            ExtendedAttribute ea = getAttributeByName(container, name);

            /*
             * Sid XPD-7543 On unset value, remove the ext attr (makes
             * SharedResourceUtil config comparison easier)
             */
            if (value == null || value.length() == 0) {
                if (ea != null) {
                    container.getExtendedAttributes().remove(ea);
                }
            } else {
                if (ea == null) {
                    ea = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
                    EList<ExtendedAttribute> eas =
                            container.getExtendedAttributes();
                    eas.add(ea);
                }
                ea.setName(name);
                ea.setValue(value);
            }
        }
    }

    public String getAttribute(ExtendedAttributesContainer container,
            String name) {
        ExtendedAttribute ea = getAttributeByName(container, name);
        return ea != null ? ea.getValue() : null;

    }

    private ExtendedAttribute getAttributeByName(
            ExtendedAttributesContainer container, String name) {
        ExtendedAttribute found = null;
        if (container != null) {
            EList<ExtendedAttribute> eas = container.getExtendedAttributes();
            for (ExtendedAttribute ea : eas) {
                if (name.equals(ea.getName())) {
                    found = ea;
                    break;
                }
            }
        }
        return found;
    }
}
