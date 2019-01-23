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
 * Tests Import validation for error , when multiple files are being imported
 * and one of them is not a valid IPM xpdl file. Test Sheet M1-Conversion
 * Framework Tests->5b
 * 
 * note: valid file used is dh8clmgt.xpdl received from Siemens , after renaming
 * 
 * @author aprasad
 * @since 12-May-2014
 */
public class IpmBpm_5b_MultipleImportInvalidFileTest extends
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
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractValidationTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {

        TestResourceInfo[] importResources =
                new TestResourceInfo[] {
                        new TestResourceInfo(
                                "resources/FrameworkValidationTests", //$NON-NLS-1$
                                "IpmBpm_5b_MultipleImportInvalidFileTest/ImportIpmXpdls/5a_InvalidIProcessFile.ipmxpdl"), //$NON-NLS-1$
                        new TestResourceInfo(
                                "resources/FrameworkValidationTests", //$NON-NLS-1$
                                "IpmBpm_5b_MultipleImportInvalidFileTest/ImportIpmXpdls/5b_validImportIProcessFile.ipmxpdl"), }; //$NON-NLS-1$
        return importResources;

    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getOtherResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {
        return null;
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
                    19,
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

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {

        return "IpmBpm_5b_MultipleImportInvalidFileTest"; //$NON-NLS-1$
    }
}
