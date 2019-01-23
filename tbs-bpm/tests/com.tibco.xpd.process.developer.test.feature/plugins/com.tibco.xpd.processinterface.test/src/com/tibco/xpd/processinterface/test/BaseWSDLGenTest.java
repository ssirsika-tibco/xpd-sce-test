/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processinterface.test;

import java.util.Collections;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * TODO Base class for the process interface tests
 * 
 * @author rsomayaj
 * @since 3.3 (15 Feb 2010)
 */
public abstract class BaseWSDLGenTest extends AbstractBaseValidationTest {

    protected IProject project;

    /**
     * @see com.tibco.xpd.core.test.validations.AbstractBaseValidationTest#setUp()
     * 
     * @throws Exception
     */

    @Override
    protected void setUp() throws Exception {
        ProjectImporter projectImporter =
                ProjectImporter
                        .createPluginProjectImporter(ProcessInterfaceTestPlugin.PLUGIN_ID,
                                Collections
                                        .singletonList("resources/BPMSimpleTypesProcIfc/")); //$NON-NLS-1$

        boolean projectImported = projectImporter.performImport();
        assertTrue("Project Imported:", projectImported); //$NON-NLS-1$
        project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject("BPMSimpleTypesProcIfc"); //$NON-NLS-1$

        assertTrue("Project Doesn't exist", project.exists()); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#tearDown()
     * 
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        // TODO Auto-generated method stub
        super.tearDown();
    }

}
