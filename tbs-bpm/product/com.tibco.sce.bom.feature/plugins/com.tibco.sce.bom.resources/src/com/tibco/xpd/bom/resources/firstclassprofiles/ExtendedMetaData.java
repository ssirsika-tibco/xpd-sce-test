/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.resources.firstclassprofiles;

import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * First-class profile extended meta data. This will allow the setting of the
 * default name and type of, for example, an {@link Property}.
 * 
 * @author njpatel
 * 
 */
public interface ExtendedMetaData {

    public static final String URI =
            "http:///com/tibco/xpd/bom/resources/firstclassprofiles/ExtendedMetaData"; //$NON-NLS-1$

    public static final String NAME = "name"; //$NON-NLS-1$

    public static final String TYPE = "type"; //$NON-NLS-1$

    public static final ExtendedMetaData INSTANCE = new BasicExtendedMetaData();

    String getName(Stereotype stereotype);

    String getType(Stereotype stereotype);

}
