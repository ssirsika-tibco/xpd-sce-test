/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.daa;

import java.util.Collection;

import org.eclipse.core.resources.IProject;

/**
 * Custom Feature Contributor interface containing method declarations required
 * for <{@link ProjectDAAGenerator} lifecycle.
 * 
 * Has enabled reversion of CompositieContributor change, done in XPD-3888. The
 * base Contributor should not need to know specifics about Contributors that
 * deal with custom feature generation.
 * 
 * @author patkinso
 * @since 24 Sep 2013
 */
public interface ICustomCompositeContributor {

    /**
     * @return Collection of feature IDs for features added by the contributor
     */
    Collection<String> getCustomFeatureIds(IProject project);

}
