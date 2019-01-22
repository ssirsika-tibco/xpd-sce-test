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
 * Tests Import validation for presence of Warning , when referenced
 * process/Interface neither exist in workspace nor in the selected resources
 * being imported.Test covers 3 scenarios
 * <p>
 * 1. Main Process with a SubProc Step calling a SubProcess , missing
 * SubProcess. 2. Main process with a Dynamic Sub proc Step referencing an IO
 * Template, missing IO template. 3. SubProcess referencing an IO template,
 * missing IO template.
 * </p>
 * 
 * @author aprasad
 * @since 12-May-2014
 */
public class IpmBpm_6abc_ReferencedProcessOrInterfaceOrderWarningTest extends
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
        return "IpmBpm_6abc_ReferencedProcessOrInterfaceOrderWarningTest"; //$NON-NLS-1$
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
                                "IpmBpm_6abc_ReferencedProcessOrInterfaceOrderWarningTest/ImportIpmXpdls/6a_MainProcCallSubProcMissingRefSubproc.ipmxpdl"), //$NON-NLS-1$
                        new TestResourceInfo(
                                "resources/FrameworkValidationTests", //$NON-NLS-1$
                                "IpmBpm_6abc_ReferencedProcessOrInterfaceOrderWarningTest/ImportIpmXpdls/6b_MainProWithDynSubprocMissingRefIOTemplate.ipmxpdl"), //$NON-NLS-1$
                        new TestResourceInfo(
                                "resources/FrameworkValidationTests", //$NON-NLS-1$
                                "IpmBpm_6abc_ReferencedProcessOrInterfaceOrderWarningTest/ImportIpmXpdls/6c_SubProcRefMissingIOTempate.ipmxpdl") }; //$NON-NLS-1$
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
        // we expect one error each file.
        int errorCount = 0;
        if (errors != null && !errors.isEmpty()) {

            for (ImportValidationError importValidationError : errors) {
                String message = importValidationError.getMessage();

                if (message
                        .equals(String
                                .format(Messages.IProcessXpdlValidator_ImportOrderIncorrectWarning_shortdesc,
                                        "SPTEST8")) //$NON-NLS-1$
                        && "6a_MainProcCallSubProcMissingRefSubproc.ipmxpdl" //$NON-NLS-1$
                                .equals(importValidationError.getXpdl())) {
                    errorCount++;
                    continue;
                } else if (message
                        .equals(String
                                .format(Messages.IProcessXpdlValidator_ImportOrderIncorrectWarning_shortdesc,
                                        "MSTTEMPL")) //$NON-NLS-1$
                        && "6b_MainProWithDynSubprocMissingRefIOTemplate.ipmxpdl" //$NON-NLS-1$
                                .equals(importValidationError.getXpdl())) {
                    errorCount++;
                } else if (message
                        .equals(String
                                .format(Messages.IProcessXpdlValidator_ImportOrderIncorrectWarning_shortdesc,
                                        "MSTTEMPL")) //$NON-NLS-1$
                        && "6c_SubProcRefMissingIOTempate.ipmxpdl" //$NON-NLS-1$
                                .equals(importValidationError.getXpdl())) {
                    errorCount++;
                }

            }
        }
        Assert.assertEquals("Validation Warning for referenced Process Or I/O template requirement is Missing", //$NON-NLS-1$
                3,
                errorCount);

    }
}
