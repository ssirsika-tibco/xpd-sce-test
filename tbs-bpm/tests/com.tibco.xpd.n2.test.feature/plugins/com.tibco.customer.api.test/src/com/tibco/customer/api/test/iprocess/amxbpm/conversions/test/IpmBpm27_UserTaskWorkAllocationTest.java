/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AllocationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Test to check if the fix in XPD-6558 works fine. Checks if all the user tasks
 * in the process have work allocation strategy set.
 * 
 * @author kthombar
 * @since 17-Jul-2014
 */
public class IpmBpm27_UserTaskWorkAllocationTest extends
        AbstractIProcessConversionTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {

        for (IFile iFile : convertedXpdls) {
            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(iFile);

            assertNotNull("Working copy cannot be null", workingCopy); //$NON-NLS-1$

            Package pkg = (Package) workingCopy.getRootElement();

            EList<Process> processes = pkg.getProcesses();

            for (Process process : processes) {

                boolean foundUserTask = false;

                Collection<Activity> allActivitiesInProc =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);

                for (Activity activity : allActivitiesInProc) {
                    Implementation impl = activity.getImplementation();

                    if (impl instanceof Task) {

                        Task task = (Task) impl;

                        if (task.getTaskUser() != null) {
                            foundUserTask = true;
                            EStructuralFeature feature =
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ActivityResourcePatterns();

                            Object resourcePattern =
                                    Xpdl2ModelUtil.getOtherElement(activity,
                                            feature);

                            assertNotNull("The User Task Resource Patter cannot be null", //$NON-NLS-1$
                                    resourcePattern);

                            ActivityResourcePatterns patterns =
                                    (ActivityResourcePatterns) resourcePattern;

                            AllocationStrategy allocationStrategy =
                                    patterns.getAllocationStrategy();

                            assertNotNull("The User Task Allocation Strategy cannot be null", //$NON-NLS-1$
                                    allocationStrategy);

                            AllocationType offer =
                                    allocationStrategy.getOffer();

                            assertNotNull("The User Task Allocation Type cannot be null", //$NON-NLS-1$
                                    offer);

                        }
                    }
                }

                assertTrue("The Process was expected to have atlease one User Task.", //$NON-NLS-1$
                        foundUserTask);
            }
        }
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
                        "resources/ConversionTests", "IpmBpm27_UserTaskWorkAllocationTest/ImportIpmXpdls/stpobj1.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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

        return null;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {

        return "IpmBpm27_UserTaskWorkAllocationTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {

        return CONVERSION_TYPE.IPM_TO_BPM_IMPORT_AND_CONVERT;
    }

}