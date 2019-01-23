/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.framework.test;

import java.util.Collection;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Test that verifies that the fix in XPD-6375 works fine. Checks if post
 * conversion the Reusable sub proc that references a process interface has
 * proper refernce to the interface and has external package reference added.
 * 
 * @author kthombar
 * @since 19-Jun-2014
 */
public class IpmBpm12_ReusableSubProcInterfaceReferencesCheckTest extends
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

        IContainer parent = null;

        for (IFile iFile : convertedXpdls) {
            parent = iFile.getParent();
            break;
        }

        String interfaceID = null;
        String startMethodId = null;
        String packageName = null;

        try {
            IResource[] members = parent.members();

            for (IResource iResource : members) {
                if (iResource.getName().equals("MSTTEMPL.xpdl")) {
                    /*
                     * we search the process interface that the re-usable sub
                     * process will reference and store the process interface id
                     * and package name so that we can verify later that the sub
                     * process references are maintained.
                     */
                    WorkingCopy workingCopy =
                            WorkingCopyUtil.getWorkingCopy(iResource);

                    Package rootElement =
                            (Package) workingCopy.getRootElement();

                    packageName = rootElement.getName();

                    ProcessInterfaces processInterfaces =
                            ProcessInterfaceUtil
                                    .getProcessInterfaces(rootElement);

                    EList<ProcessInterface> processInterface =
                            processInterfaces.getProcessInterface();

                    ProcessInterface ifc = processInterface.get(0);

                    interfaceID = ifc.getId();

                    EList<StartMethod> startMethods = ifc.getStartMethods();

                    startMethodId = startMethods.get(0).getId();

                }
            }
        } catch (CoreException e) {

            e.printStackTrace();
        }

        assertNotNull("Package name cannot be null at this point", packageName);
        assertNotNull("Interface Id cannot be null at this point", interfaceID);
        assertNotNull("Start method Id cannot be null at this point",
                startMethodId);

        boolean testSuccessfull = false;

        for (IFile iFile : convertedXpdls) {
            if (iFile.getName().equals("MSTMAIN.xpdl")) {
                WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(iFile);
                Package pkg = (Package) workingCopy.getRootElement();

                EList<ExternalPackage> externalPackages =
                        pkg.getExternalPackages();

                assertNotNull("External Package cannot be null at this point",
                        externalPackages);

                assertEquals("Expected External package not found",
                        packageName,
                        externalPackages.get(0).getHref());

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

                        String processId = subFlow.getProcessId();

                        String packageRefId = subFlow.getPackageRefId();

                        assertEquals("Implemented process id's do not match",
                                interfaceID,
                                processId);

                        assertEquals("The Package names of the implmented and the actual package do not match",
                                packageName,
                                packageRefId);

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
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/FrameworkTests", "IpmBpm12_ReusableSubProcInterfaceReferencesCheckTest/ImportIpmXpdls/MSTMAIN.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
        return "IpmBpm12_ReusableSubProcInterfaceReferencesCheckTest"; //$NON-NLS-1$
    }

}
