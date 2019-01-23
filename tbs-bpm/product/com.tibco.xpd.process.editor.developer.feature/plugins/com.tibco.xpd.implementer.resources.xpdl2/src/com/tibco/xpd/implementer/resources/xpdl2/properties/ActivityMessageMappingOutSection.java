/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.WsdlFilter.WsdlDirection;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * @author scrossle
 */
public class ActivityMessageMappingOutSection extends
        AbstractMessageMappingOutSection {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.implementer.resources.xpdl2.properties.
     * AbstractActivityParameterMappingSection#getTitle()
     */
    @Override
    protected String getTitle() {
        return Messages.ActivityParameterMappingInSection_Title;
    }

    @Override
    protected ITreeContentProvider createWsdlPartsSourceContentProvider() {
        return new ActivityMessageWsdlItemProvider(MappingDirection.OUT,
                DirectionType.OUT_LITERAL, WsdlDirection.OUT);
    }

}
