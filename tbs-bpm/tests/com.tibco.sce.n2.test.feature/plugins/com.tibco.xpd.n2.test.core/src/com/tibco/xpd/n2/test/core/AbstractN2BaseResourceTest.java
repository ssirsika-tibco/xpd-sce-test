/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.test.core;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest;

/**
 * Base resource test for N2 projects
 * 
 * @author aallway
 * @since 18 Apr 2011
 */
public abstract class AbstractN2BaseResourceTest extends
        AbstractBuildingBaseResourceTest {

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#configureProject(org.eclipse.core.resources.IProject)
     * 
     * @param testProject
     */
    @Override
    protected void configureProject(IProject testProject) {
        super.configureProject(testProject);
        N2TestCreationUtil.configureAsN2Project(testProject, getTestPlugInId());
    }

}
