/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.framework.test.validations;

import java.util.List;

import junit.framework.Assert;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionValidationTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;
import com.tibco.xpd.iprocess.amxbpm.imports.validations.ImportValidationError;

/**
 * Tests Import validation for error , when Single file being imported is not a
 * valid IPM xpdl file. Test Sheet M1-Conversion Framework Tests->5a
 * 
 * @author aprasad
 * @since 12-May-2014
 */
public class IpmBpm_5a_SingleImportInvalidFileTest extends
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
        return "IpmBpm_5a_SingleImportInvalidFileTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractValidationTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        // /com.tibco.customer.api.test/resources/iProcess
        // XPDLs/Framework/InvalidIProcess.xpdl
        TestResourceInfo[] importResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/FrameworkValidationTests", //$NON-NLS-1$
                        "IpmBpm_5a_SingleImportInvalidFileTest/ImportIpmXpdls/5a_InvalidIProcessFile.ipmxpdl"), }; //$NON-NLS-1$
        return importResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getOtherResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {
        // None required
        return null;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionValidationTest#doTestImportValidations(java.util.List)
     * 
     * @param errors
     */
    @Override
    protected void doTestImportValidations(List<ImportValidationError> errors) {
        // TODO Auto-generated method stub
        boolean errorPresent = false;
        if (errors != null && !errors.isEmpty()) {
            // There should be Single Error raised
            Assert.assertEquals("Multiple validation Errors are raised, ", //$NON-NLS-1$
                    1,
                    errors.size());
            for (ImportValidationError importValidationError : errors) {

                if (Messages.IProcessXpdlValidator_NotIProcessResourceMessage
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
