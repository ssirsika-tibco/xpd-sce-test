/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.framework.test;

import java.util.List;

import junit.framework.Assert;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionValidationTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;
import com.tibco.xpd.iprocess.amxbpm.imports.validations.ImportValidationError;

/**
 * Test to check if the the validator throws errors while imporing an iProcess
 * xpdl whose BPM version already exists in another project of the same
 * workspace.
 * 
 * @author sajain
 * @since Apr 29, 2014
 */
public class IpmBpm05_SameXpdlInOtherProjectTest extends
        AbstractIProcessConversionValidationTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {

        return CONVERSION_TYPE.IPM_TO_BPM_IMPORT_AND_CONVERT;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpmBpm05_SameXpdlInOtherProjectTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractValidationTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/FrameworkTests", //$NON-NLS-1$
                        "IpmBpm05_SameXpdlInOtherProjectTest/ImportIpmXpdls/eai.ipmxpdl"), }; //$NON-NLS-1$

        return testResources;
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestResources()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {
        return new TestResourceInfo[] { new TestResourceInfo(
                "resources/FrameworkTests", //$NON-NLS-1$
                "IpmBpm05_SameXpdlInOtherProjectTest_OtherProject/Process Packages{processes}/EAI.xpdl"), }; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionValidationTest#doTestImportValidations(java.util.List)
     * 
     * @param errors
     */
    @Override
    protected void doTestImportValidations(List<ImportValidationError> errors) {
        boolean errorPresent = false;
        if (errors != null && !errors.isEmpty()) {
            // There should be Single Error raised
            Assert.assertEquals("Multiple validation Errors are raised, ", //$NON-NLS-1$
                    1,
                    errors.size());
            for (ImportValidationError importValidationError : errors) {

                if (Messages.IProcessXpdlValidator_ErrorMsgAllProcessOrInterfaceAlreadyExistInWorkspace
                        .equals(importValidationError.getMessage())) {
                    errorPresent = true;

                    break;
                }

            }
        }
        Assert.assertTrue("Invalid File validation Error is Missing", //$NON-NLS-1$
                errorPresent);
    }
}
