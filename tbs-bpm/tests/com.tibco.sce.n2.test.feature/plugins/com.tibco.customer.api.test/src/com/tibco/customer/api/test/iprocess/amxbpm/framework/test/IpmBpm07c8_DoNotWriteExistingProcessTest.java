/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.framework.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * This test checks that a process already existing in the workspace , whether
 * in same project or not, should not be imported again. This test is simulated
 * a follows, ProjectA is initialised with an xpdl with process MPTEST8. Import
 * xpdls containing MPTEST8 and SPTEST8 into ProjectB. Only MPTEST8 should be
 * imported and not SPTEST8.
 * 
 * @author aprasad
 * @since 12-May-2014
 */
public class IpmBpm07c8_DoNotWriteExistingProcessTest extends
        AbstractIProcessConversionTest {

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
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/FrameworkTests", "IpmBpm07c8_DoNotWriteExistingProcessTest/ImportIpmXpdls/7c8_MainProcBRefProcA.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;

    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getOtherResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {
        return new TestResourceInfo[] {
                new TestResourceInfo("resources/FrameworkTests", //$NON-NLS-1$
                        "DoNotWriteExistingProcessTest/Process Packages{processes}/generalProcess.xpdl"), //$NON-NLS-1$
                new TestResourceInfo("resources/FrameworkTests", //$NON-NLS-1$
                        "SameFileNameExistsInTargetFolder/Process Packages{processes}/PROCA.xpdl"), }; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {

        return "IpmBpm07c8_DoNotWriteExistingProcessTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {

        assertEquals("Unexpected number of converted xpdls", //$NON-NLS-1$
                1,
                convertedXpdls.size());

        List<IFile> convertedFiles = new ArrayList<IFile>(convertedXpdls);

        IFile iFile = convertedFiles.get(0);

        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(iFile);
        EObject rootElement = workingCopy.getRootElement();
        Package pkg = (Package) rootElement;
        Process process = pkg.getProcesses().get(0);

        if (!"MPTEST8".equals(process.getName())) { //$NON-NLS-1$
            fail("Expected process 'MPTEST8' not found"); //$NON-NLS-1$
        }

    }
}