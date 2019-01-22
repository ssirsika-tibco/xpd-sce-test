/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.validation.wm.test;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;

/**
 * @author mtorres
 * @since
 */
public class ContentAssistScriptValidationTest extends AbstractBaseContentAssistTest {

	/**
     * EnumerationScriptValidationTest
     * 
     * @throws Exception
     */
    public void testContentAssistScriptValidationTest() throws Exception {
		doTestValidations();        
        return;
	}


    @Override
    protected String getTestName() {
        return "ContentAssistScriptValidationTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.validation.wm.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
            new TestResourceInfo("resources/N2JavaScriptContentAssistValidation", "N2JavaScriptContentAssistValidation/Process Packages{processes}/N2JavaScriptContentAssistValidation.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
            new TestResourceInfo("resources/N2JavaScriptContentAssistValidation", "N2JavaScriptContentAssistValidation/Business Objects{bom}/N2JavaScriptValidation.bom"), //$NON-NLS-1$ //$NON-NLS-2$
        };
    
        return testResources;
    }
    
    
    @Override
    protected void configureProject(IProject project) {
        TestUtil.addGlobalDestinationToProject(getTestPlugInId(),
                "BPM", project);//$NON-NLS-1$
    }

}
