/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.framework.test;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Test to check if the fix in XPD-6463 works fine.
 * <p>
 * This test checks that when a xpdl file name is changed (i.e. from abc.xpdl to
 * abc_1.xpdl) because the file with same name is already present in the target
 * process package special folder then all the package and external package
 * references are updated to reference the new name of the xpdl(i.e. abc_1.xpdl)
 * 
 * @author kthombar
 * @since 20-Jun-2014
 */
public class IpmBpm14_XpdlWithNameAlreadyPresentReferenceUpdateCheckTest extends
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
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @SuppressWarnings("nls")
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {

        assertEquals("Unexpected number of converted files",
                3,
                convertedXpdls.size());

        boolean testSuccessfull = false;

        for (IFile iFile : convertedXpdls) {

            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(iFile);

            Package pkg = (Package) workingCopy.getRootElement();

            if (iFile.getName().equals("MSTSUB.xpdl")) {
                /* check if the external package reference is correct */
                inspectExternalPackageReference(pkg);

                /*
                 * check if the implemented interface has correct package
                 * reference
                 */
                Process process = pkg.getProcesses().get(0);
                Object otherElement =
                        Xpdl2ModelUtil
                                .getOtherElement(process,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ImplementedInterface());

                if (otherElement instanceof ImplementedInterface) {
                    /* go ahead only if the process implements an interface */
                    ImplementedInterface implementedIfc =
                            (ImplementedInterface) otherElement;

                    assertEquals("The external Package referenced is incorrect",
                            "MSTTEMPL_1",
                            implementedIfc.getPackageRef());
                } else {
                    fail("The process is expected to implement a process interface.");
                }

            } else if (iFile.getName().equals("MSTMAIN.xpdl")) {
                /* check if the external package reference is correct */
                inspectExternalPackageReference(pkg);

                /*
                 * check if the external package referenced by the dynamic sub
                 * proc is correct
                 */

                Process process = pkg.getProcesses().get(0);

                Collection<Activity> allActivitiesInProc =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);

                boolean foundReusableSubProc = false;

                for (Activity eachActivity : allActivitiesInProc) {

                    Implementation impl = eachActivity.getImplementation();

                    if (impl instanceof SubFlow) {
                        foundReusableSubProc = true;
                        /*
                         * We are interested only in Re-usable Sub proc
                         * activities.
                         */
                        SubFlow subFlow = (SubFlow) impl;

                        assertEquals("The external Package referenced is incorrect",
                                "MSTTEMPL_1",
                                subFlow.getPackageRefId());

                        testSuccessfull = true;
                    }

                }
                assertTrue("We expected the Sub proc to implement the  interface, however no such activity was found",
                        foundReusableSubProc);

            }

        }

        assertTrue("Test did not run successfully", testSuccessfull);

    }

    /**
     * checks if the external package reference is as expected.
     * 
     * @param pkg
     */
    private void inspectExternalPackageReference(Package pkg) {

        EList<ExternalPackage> externalPackages = pkg.getExternalPackages();

        assertEquals("The external Package referenced is incorrect", //$NON-NLS-1$
                "MSTTEMPL_1", //$NON-NLS-1$
                externalPackages.get(0).getHref());

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
                        "resources/FrameworkTests", "IpmBpm14_XpdlWithNameAlreadyPresentReferenceUpdateCheckTest/ImportIpmXpdls/MSTMAIN.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/FrameworkTests", "IpmBpm14_XpdlWithNameAlreadyPresentReferenceUpdateCheckTest/Process Packages{processes}/MSTTEMPL.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {

        return "IpmBpm14_XpdlWithNameAlreadyPresentReferenceUpdateCheckTest"; //$NON-NLS-1$
    }

}
