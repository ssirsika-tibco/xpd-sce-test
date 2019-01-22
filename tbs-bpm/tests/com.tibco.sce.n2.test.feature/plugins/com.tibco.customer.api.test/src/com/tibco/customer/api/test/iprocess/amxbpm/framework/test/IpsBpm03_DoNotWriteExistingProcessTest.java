/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.framework.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Test that checks if a process with the same name as the process to be
 * converted is already present in the workspace then all such processes are
 * ignored and are not converted.
 * 
 * @author kthombar
 * @since 15-Jul-2014
 */
public class IpsBpm03_DoNotWriteExistingProcessTest extends
        AbstractIProcessConversionTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {
        assertEquals("Unexpected number of converted files", //$NON-NLS-1$
                1,
                convertedXpdls.size());

        List<IFile> file = new ArrayList<IFile>(convertedXpdls);

        IFile iFile = file.get(0);

        assertEquals("Unexpected name of xpdl file", //$NON-NLS-1$
                "testProcess.xpdl", //$NON-NLS-1$
                iFile.getName());

        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(iFile);
        Package pkg = (Package) workingCopy.getRootElement();
        Process process = pkg.getProcesses().get(0);

        assertEquals("Unexpected name of process", //$NON-NLS-1$
                "testProcess", //$NON-NLS-1$
                process.getName());

    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/FrameworkTests", "IpsBpm03_DoNotWriteExistingProcessTest/Process Packages{processes}/test.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };
        return testResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getOtherResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] {
                        new TestResourceInfo(
                                "resources/FrameworkTests", "IpsBpm03_DoNotWriteExistingProcessTest_Project2/Process Packages{processes}/testProcess2.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/FrameworkTests", "IpsBpm03_DoNotWriteExistingProcessTest_Project2/Process Packages{processes}/testProcess3.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$

                };
        return testResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {

        return "IpsBpm03_DoNotWriteExistingProcessTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {
        return CONVERSION_TYPE.IPS_TO_BPM_CONVERT;
    }

}
