/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.framework.test;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.emf.common.util.EList;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Test to check if the fix in XPD-6426 works fine.
 * <p>
 * This test includes the interface in the processes packages folder while
 * loading the project. The process that refernces this interface is passed
 * through the converter and post conversion we check if the process after
 * conversion has proper refernce to the interface which already was present in
 * workspace.
 * 
 * @author kthombar
 * @since 20-Jun-2014
 */
public class IpmBpm13_ReferenceBrokenEvenIfXpdlInProjectTest extends
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
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {

        assertEquals("Unexpected number of converted files", //$NON-NLS-1$
                1,
                convertedXpdls.size());

        IFolder processPackageFolder = null;

        IFile convertedFile = null;

        /*
         * get the parent process package folder
         */
        for (IFile iFile : convertedXpdls) {
            convertedFile = iFile;
            processPackageFolder = (IFolder) iFile.getParent();

            break;
        }

        /*
         * Get the interface file already present in workspace.
         */
        IFile file = processPackageFolder.getFile("SPTEMPLA.xpdl"); //$NON-NLS-1$

        if (!file.exists()) {
            fail("We expect the file '" //$NON-NLS-1$
                    + file.getName()
                    + "' to be already loaded in the project, but that does not happen."); //$NON-NLS-1$
        }

        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(file);

        Package pkg = (Package) workingCopy.getRootElement();

        ProcessInterfaces processInterfaces =
                ProcessInterfaceUtil.getProcessInterfaces(pkg);

        EList<ProcessInterface> processInterface =
                processInterfaces.getProcessInterface();

        String interfaceId = null;
        String interfacePackageName = pkg.getName();

        if (processInterface.isEmpty()) {
            fail("Process Interface cannot be null at this point"); //$NON-NLS-1$
        }

        for (ProcessInterface processInterface2 : processInterface) {
            interfaceId = processInterface2.getId();
        }

        assertNotNull("Process interface Id cannot be null", interfaceId); //$NON-NLS-1$

        Package convertedFilePackage =
                (Package) WorkingCopyUtil.getWorkingCopy(convertedFile)
                        .getRootElement();

        EList<ExternalPackage> externalPackages =
                convertedFilePackage.getExternalPackages();

        assertNotNull("External Package cannot be null at this point", //$NON-NLS-1$
                externalPackages);

        assertEquals("Expected External package not found", //$NON-NLS-1$
                interfacePackageName,
                externalPackages.get(0).getHref());

        EList<Process> processes = convertedFilePackage.getProcesses();

        Process process = processes.get(0);

        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        boolean isTestSuccessfull = false;

        for (Activity eachActivity : allActivitiesInProc) {

            Implementation impl = eachActivity.getImplementation();

            if (impl instanceof SubFlow) {

                /*
                 * We are interested only in Re-usable Sub proc activities.
                 */
                SubFlow subFlow = (SubFlow) impl;

                String processId = subFlow.getProcessId();

                String packageRefId = subFlow.getPackageRefId();
                /*
                 * Check if the re-usable sub proc have appropriate refernce to
                 * the process interface and its package.
                 */
                assertEquals("Implemented process id's do not match", //$NON-NLS-1$
                        interfaceId,
                        processId);

                assertEquals("The Package names of the implmented and the actual package do not match", //$NON-NLS-1$
                        interfacePackageName,
                        packageRefId);

                isTestSuccessfull = true;
            }

        }

        assertTrue("The test did not run successfully", isTestSuccessfull); //$NON-NLS-1$

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
                        "resources/FrameworkTests", "IpmBpm13_ReferenceBrokenEvenIfXpdlInProjectTest/ImportIpmXpdls/TESTIPM.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
                        "resources/FrameworkTests", "IpmBpm13_ReferenceBrokenEvenIfXpdlInProjectTest/Process Packages{processes}/SPTEMPLA.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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

        return "IpmBpm13_ReferenceBrokenEvenIfXpdlInProjectTest"; //$NON-NLS-1$
    }

}
