/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.deploy.model.extension;

import java.net.URL;
import java.util.Collection;
import java.util.Map;

/**
 * 
 * <p>
 * <i>Created: 10 Feb 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public interface LateBindingSupport {

    Collection<ResourceBinding> getModuleBindings(URL localURL);

    Collection<SharedResource> getSharedResources(Map parameters);

    DeploymentStatus applyModuleBindings(URL localURL,
            Collection<ResourceBinding> bindings);

}
