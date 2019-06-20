/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.model.internal.client;

import java.util.Collections;
import java.util.List;

import org.eclipse.uml2.uml.Enumeration;

import com.tibco.xpd.script.model.client.JsAttribute;

/**
 * This class provides a wrapper for the Enumeration classifier in UML model
 * 
 * Sid ACE-542. This DefaultJsEnumeration object used to be used for BOTH
 * Enumeration classes AND Enumeration literals (so that enum typed properties
 * and the literals were considered to be the same type.
 * 
 * 1) In ACE enumeration literals are implemented as static strings.
 * 
 * 2) ALSO in AMX BPM this used to be a bug because the the enumeration literal
 * also contained the list of enumeration literals and a get(xxx) method as well
 * as the main enumeration class object.
 * 
 * 3) ALSO in AMX BPM you can do classfield.colourEnum = com_my_bom.ColoursEnum
 * instead of a literal.
 * 
 * Enumeration classes are now handled by dynamic creation of UML JS
 * contributions in AceCdsFactoriesWrapperFactory. That allows us to resolve (1)
 * and (2) and (3) here because NOW DefaultJsEnumeration is ONLY used for
 * enumeration literals and enumeration properties. Therefore if we now DON'T
 * return the list of literals and the get(xxx) method on the literal then you
 * won't see this in script content assist/validation for literal, you will only
 * see what AceCdsFactoriesWrapperFactory decides to put in the content assist
 * class for the Enumeration Classes (which in ACE is just the enumeration
 * literals).
 * 
 * So basically, enum literals served by this DefaultJsEnumeration class will
 * not have any attributes OR methods - exactly what is required for ACE.
 * 
 * @author mtorres
 * 
 */
public class DefaultJsEnumeration extends AbstractDefaultJsDataType
        implements JsEnumeration {

    private String qualifiedName = null;

    private Enumeration umlEnumeration;

    public DefaultJsEnumeration(Enumeration umlEnumeration) {
        super(umlEnumeration);

        this.umlEnumeration = umlEnumeration;
    }

    @Override
    @SuppressWarnings("unchecked") //$NON-NLS-1$
    public List<JsAttribute> getAttributeList() {

        /*
        
         */

        return Collections.EMPTY_LIST;
    }


    /**
     * Set qualified name.If set will take precedence over name, hence SHOULD
     * NOT be set where unqualified name is to be used.
     * 
     * @param scriptingName
     *            the scriptingName to set
     */
    public void setQualifiedName(String scriptingName) {
        this.qualifiedName = scriptingName;
    }

    /**
     * Returns true is qualified name is set.
     * 
     * @return
     */
    public boolean requireQualifiedName() {
        return this.qualifiedName != null && this.qualifiedName.length() > 0;
    }

    /**
     * This method will return qualified name if set, otherwise returns the
     * unqualified name.
     * 
     * @see com.tibco.xpd.script.model.internal.client.AbstractDefaultJsDataType#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        if (requireQualifiedName()) {
            return this.qualifiedName;
        }
        return super.getName();
    }

}
