/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.deploy;

import java.net.URL;
import java.util.Collection;

import com.tibco.xpd.deploy.model.extension.ResourceBinding;

/**
 * This interface should be implemented by all module deployment wizards which
 * supports late binding.
 * <p>
 * <i>Created: 10 Feb 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public interface IBindingSupportWizard extends IDeployWizard {

    /**
     * Sets the context modules for which there could be provided bindings.
     * 
     * @param modules
     *            the collection of module's URLs.
     */
    void setContextModules(Collection<URL> modules);

    /**
     * Collects list of completed bindings for modules.
     * 
     * @return maps with module URL and associated bindings.
     */
    Collection<ResourceBinding> getBindings();
}
