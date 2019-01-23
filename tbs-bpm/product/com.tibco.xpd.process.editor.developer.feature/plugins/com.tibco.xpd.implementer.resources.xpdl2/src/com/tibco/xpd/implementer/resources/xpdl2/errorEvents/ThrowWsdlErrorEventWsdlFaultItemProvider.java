/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.errorEvents;

import com.tibco.xpd.implementer.resources.xpdl2.properties.ActivityMessageWsdlItemProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.WsdlFilter.WsdlDirection;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * WSDL Fault mapper source content provider for throw fault message end Error
 * Events that throw faults of operation of associated request activity.
 * 
 * @author aallway
 * @since 3.3
 */
public class ThrowWsdlErrorEventWsdlFaultItemProvider extends
        ActivityMessageWsdlItemProvider {

    public ThrowWsdlErrorEventWsdlFaultItemProvider() {
        super(MappingDirection.IN, DirectionType.IN_LITERAL,
                WsdlDirection.FAULT);
    }
}
