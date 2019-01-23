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
 * This validation Test, checks that when All processes being imported already
 * exist in workspace, it SHOULD return Error saying All Processes already
 * exists in workspace.This is simulated by initialising the Project with a BPM
 * xpdl [03ProcessA.xpdl] containing PROCA process, and importing iProcess XPDLs
 * 03ProcessA.xpdl [PROCA] and 03ProcessB.xpdl [PROCB] Test Sheet M1-Conversion
 * Framework Tests->5d
 * 
 * @author aprasad
 * @since 12-May-2014
 */
public class IpmBpm_5d_AllProcAlreadyInWorkspaceTest extends
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
        return "IpmBpm_5d_AllProcAlreadyInWorkspaceTest"; //$NON-NLS-1$
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
                        "IpmBpm_5d_AllProcAlreadyInWorkspaceTest/ImportIpmXpdls/5c_ProcessA.ipmxpdl") }; //$NON-NLS-1$
        return importResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getOtherResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {
        TestResourceInfo[] importResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/FrameworkValidationTests", //$NON-NLS-1$
                        "AllProcAlreadyInWorkspace/Process Packages{processes}/5c_ProcessA.xpdl") }; //$NON-NLS-1$
        return importResources;
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
        boolean errorPresent = false;
        if (errors != null && !errors.isEmpty()) {

            for (ImportValidationError importValidationError : errors) {

                if (Messages.IProcessXpdlValidator_ErrorMsgAllProcessOrInterfaceAlreadyExistInWorkspace
                        .equals(importValidationError.getMessage())) {
                    errorPresent = true;

                    break;
                }

            }
        }
        Assert.assertTrue("All process Already exits Error should be raised", //$NON-NLS-1$
                errorPresent);

    }
}
