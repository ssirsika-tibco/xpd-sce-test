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
 * Tests Import validation for presence of error , when multiple copies of a
 * process are being imported and their GUID does not match. Test Sheet
 * M1-Conversion Framework Tests->5f To simulate this a pair of Subprocess and
 * main process is used, where in Subprocess is exported separately and GUID is
 * modified importValidationError.getMessage().And Main process is exported
 * which also includes the referenced Subprocess.Both files are imported
 * together , creating scenario where multiple copies of SubProcess being
 * imported has different GUID.
 * 
 * @author aprasad
 * @since 12-May-2014
 */
public class IpmBpm_5f_DuplicateProcessWithInvalidGUIDsTest extends
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
        return "IpmBpm_5f_DuplicateProcessWithInvalidGUIDsTest"; //$NON-NLS-1$
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
                                "IpmBpm_5f_DuplicateProcessWithInvalidGUIDsTest/ImportIpmXpdls/5f_MainProcWithSubProc.ipmxpdl"), //$NON-NLS-1$
                        new TestResourceInfo(
                                "resources/FrameworkValidationTests", //$NON-NLS-1$
                                "IpmBpm_5f_DuplicateProcessWithInvalidGUIDsTest/ImportIpmXpdls/5f_SubProcWithDiffGUID.ipmxpdl") }; //$NON-NLS-1$
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
         * Check if validator throws errors and if not, then test fails.
         */
        int numberOfGUIDErrors = 0;
        boolean errorPresent = false;

        if (errors != null && !errors.isEmpty()) {

            for (ImportValidationError importValidationError : errors) {
                String message = importValidationError.getMessage();

                // have to check starts with as this message is customized with
                // fiels details
                if (message != null
                        && message
                                .startsWith(Messages.IProcessImportValidator_ErrorMsgDuplicateProcessNonMatchingGUID)) {
                    numberOfGUIDErrors++;
                }

            }

            if (numberOfGUIDErrors == 2) {
                errorPresent = true;
            }

        }
        Assert.assertTrue("Validation Error for non matching GUIDs for process duplicates is Missing", //$NON-NLS-1$
                errorPresent);

    }
}
