/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.framework.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest.CONVERSION_TYPE;
import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Test to check if the fix in XPD-6517 works fine. Checks if after ips to bpm
 * conversion all the process and interface ids are regenerated.
 * 
 * @author kthombar
 * @since 10-Jul-2014
 */
public class IpsBpm01_RegeneratedProcessIfcIdsTest extends
        AbstractIProcessConversionTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIpmConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @SuppressWarnings("nls")
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {
        List<String> oldIds = new ArrayList<String>();
        oldIds.add("_bc17gAgmEeS7qfmgayqbRg");
        oldIds.add("_8qw48AgmEeS7qfmgayqbRg");
        oldIds.add("_Yfc9UAgmEeS7qfmgayqbRg");

        for (IFile eachFile : convertedXpdls) {

            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(eachFile);

            if (workingCopy == null) {
                fail("Working copy cannot be null.");
            }

            Package pkg = (Package) workingCopy.getRootElement();

            EList<Process> processes = pkg.getProcesses();

            if (!processes.isEmpty()) {

                Process process = processes.get(0);
                if (oldIds.contains(process.getId())) {
                    fail("We expect the converted process to have new id's, however the process had old id");
                }

            } else {
                ProcessInterfaces processInterfaces =
                        ProcessInterfaceUtil.getProcessInterfaces(pkg);

                EList<ProcessInterface> processInterface =
                        processInterfaces.getProcessInterface();

                if (processInterface.isEmpty()) {
                    fail("If the package doesn't have a process then it should atleast have a process interface.");
                }

                ProcessInterface processInterface2 = processInterface.get(0);
                if (oldIds.contains(processInterface2.getId())) {
                    fail("We expect the converted process interface to have new id's, however the process interface had old id");
                }
            }
        }
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
                        "resources/FrameworkTests", "IpsBpm01_RegeneratedProcessIfcIdsTest/Process Packages{processes}/myProc.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
        return null;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpsBpm01_RegeneratedProcessIfcIdsTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {
        return CONVERSION_TYPE.IPS_TO_BPM_CONVERT;
    }

}
