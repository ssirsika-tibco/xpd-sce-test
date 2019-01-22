/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.Map;

import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.xpdl2.Activity;

/**
 * @author nwilson
 */
public interface IExpressionProvider {

    /**
     * @param activity The activity.
     * @param mapping The mapping.
     * @param direction The direction.
     * @return The script text.
     */
    String getText(Activity activity, Mapping mapping,
            MappingDirection direction);

    /**
     * @param activity The activity.
     * @param mapping The mapping.
     * @param direction The direction.
     * @return A list of fields.
     */
    Map<String, Object> getFields(Activity activity, Mapping mapping,
            MappingDirection direction);

    /**
     * @param activity The activity.
     * @param mapping The mapping.
     * @param direction The direction.
     * @return The prefix.
     */
    String getPrefix(Activity activity, Mapping mapping,
            MappingDirection direction);
}
