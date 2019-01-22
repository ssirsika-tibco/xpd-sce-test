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
 * Tests Import validation for presence of Warning , when Composite types are
 * Used.
 * 
 * 
 * @author aprasad
 * @since 12-May-2014
 */
public class IpmBpm_6d_CompositeTypeNotSupportedWarningTest extends
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
        return "IpmBpm_6d_CompositeTypeNotSupportedWarningTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractValidationTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {

        TestResourceInfo[] importResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/FrameworkValidationTests", //$NON-NLS-1$
                        "IpmBpm_6d_CompositeTypeNotSupportedWarningTest/ImportIpmXpdls/6d_ProcesssWithCompositeField.ipmxpdl"), }; //$NON-NLS-1$
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
        /*
         * Check if validator throws warnings and if not, then test fails.
         */

        boolean errorExists = false;

        if (errors != null && !errors.isEmpty()) {

            for (ImportValidationError importValidationError : errors) {
                String message = importValidationError.getMessage();

                if (message
                        .equals(Messages.IProcessXpdlValidator_CompositeFieldsNotSupported_message)) {
                    errorExists = true;
                    break;
                }

            }
        }
        Assert.assertTrue("Validation Warning Composite type Not Supproted is Missing", //$NON-NLS-1$
                errorExists);

    }
}
