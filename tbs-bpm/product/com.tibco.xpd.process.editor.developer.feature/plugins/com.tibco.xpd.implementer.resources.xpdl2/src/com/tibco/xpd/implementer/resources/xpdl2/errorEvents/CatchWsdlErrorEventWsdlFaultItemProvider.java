/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.errorEvents;

import com.tibco.xpd.implementer.resources.xpdl2.properties.ActivityMessageWsdlItemProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.WsdlFilter.WsdlDirection;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * WSDL Fault mapper source content provider for Catch Error Events that catch
 * errors thrown by operations invoked by other tasks / events.
 * 
 * @author aallway
 * @since 3.2
 */
public class CatchWsdlErrorEventWsdlFaultItemProvider extends
        ActivityMessageWsdlItemProvider {

    public CatchWsdlErrorEventWsdlFaultItemProvider() {
        super(MappingDirection.OUT, DirectionType.OUT_LITERAL,
                WsdlDirection.FAULT);
    }
}
