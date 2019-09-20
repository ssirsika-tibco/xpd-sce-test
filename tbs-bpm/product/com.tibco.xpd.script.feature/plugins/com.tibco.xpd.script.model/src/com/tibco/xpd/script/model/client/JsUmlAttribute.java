/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.script.model.client;

import org.eclipse.uml2.uml.Type;

/**
 *
 *
 * @author nwilson
 * @since 20 Sep 2019
 */
public interface JsUmlAttribute extends JsAttribute {
    /**
     * Returns the UML type for the JsAttribute
     * 
     * @return the UML type for the JsAttribute
     */
    Type getUmlType();

}
