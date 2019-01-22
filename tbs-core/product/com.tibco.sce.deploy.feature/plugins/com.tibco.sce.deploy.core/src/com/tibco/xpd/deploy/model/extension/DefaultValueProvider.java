/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.deploy.model.extension;

import com.tibco.xpd.deploy.ConfigParameterInfo;

/**
 * Provides default value for property.
 * 
 * @author Jan Arciuchiewicz
 */
public interface DefaultValueProvider {

    public String getDefaultValue(ConfigParameterInfo configParameterInfo);
}
