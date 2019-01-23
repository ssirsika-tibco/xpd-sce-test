/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * /** JUnit test to protect 'Set Unique Activity Name' contribution.
 * 
 * @author sajain
 * @since Jul 14, 2014
 */
public class IpsBpm09_SetUniqueActivityNameTest extends
        AbstractIProcessConversionTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {

        return CONVERSION_TYPE.IPS_TO_BPM_CONVERT;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpsBpm09_SetUniqueActivityNameTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iProcessToAMXBPMConversion.AbstractConversionTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/ConversionTests", "IpsBpm09_SetUniqueActivityNameTest/Process Packages{processes}/setunique.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };
        return testResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {
        for (IFile file : convertedXpdls) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);
            EObject rootElement = wc.getRootElement();
            if (rootElement instanceof Package) {
                Package pkg = (Package) rootElement;
                for (Process eachProcess : pkg.getProcesses()) {
                    if (eachProcess.getName().equalsIgnoreCase("ACTXPDL6")) { //$NON-NLS-1$
                        Collection<Activity> allActs =
                                Xpdl2ModelUtil
                                        .getAllActivitiesInProc(eachProcess);
                        Set<String> activityNames = new HashSet<String>();
                        for (Activity eachActivity : allActs) {
                            String name = eachActivity.getName();
                            if (null != name && activityNames.contains(name)) {
                                /*
                                 * Duplicate name detected, test fails.
                                 */

                                fail("Duplicate name detected!"); //$NON-NLS-1$
                            }
                            activityNames.add(name);
                        }
                    }
                }
            }
        }
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getOtherResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {
        // No other resources.
        return null;
    }
}
