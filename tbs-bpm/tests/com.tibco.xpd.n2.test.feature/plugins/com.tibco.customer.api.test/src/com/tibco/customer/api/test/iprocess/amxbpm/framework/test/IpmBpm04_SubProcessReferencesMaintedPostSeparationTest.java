/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.framework.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Test that verifies if the sub process referernces are maintained post
 * separation of processes to separate XPDL files.
 * 
 * @author kthombar
 * @since 30-Apr-2014
 */
public class IpmBpm04_SubProcessReferencesMaintedPostSeparationTest extends
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
        checkReusableSubProcessReferences(convertedXpdls);
    }

    /**
     * Checks if the re-usable sub proc references are maintained post
     * conversion. If not then fails the test.
     * 
     * @param convertedXpdls
     */
    private void checkReusableSubProcessReferences(
            Collection<IFile> convertedXpdls) {

        assertEquals("Expected number of converted xpdl's not found", //$NON-NLS-1$
                4,
                convertedXpdls.size());

        Map<String, String> procIdToPackageName = new HashMap<String, String>();

        populateMap(convertedXpdls, procIdToPackageName);

        for (IFile iFile : convertedXpdls) {

            if (iFile.getName().equals("RESUSABL.xpdl")) { //$NON-NLS-1$
                WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(iFile);
                if (workingCopy == null) {
                    fail("working copy cannot be null"); //$NON-NLS-1$
                }
                EObject rootElement = workingCopy.getRootElement();

                if (rootElement instanceof Package) {
                    com.tibco.xpd.xpdl2.Package pkg = (Package) rootElement;

                    /**
                     * The package should have 3 external package references
                     */
                    assertEquals("Unexpected number of external package referernces found", //$NON-NLS-1$
                            3,
                            pkg.getExternalPackages().size());

                    Process process = pkg.getProcesses().get(0);
                    if (process == null) {
                        fail("Process cannot be null"); //$NON-NLS-1$
                    }
                    Collection<Activity> allActivitiesInProc =
                            Xpdl2ModelUtil.getAllActivitiesInProc(process);

                    for (Activity activity : allActivitiesInProc) {

                        Implementation impl = activity.getImplementation();

                        if (impl instanceof SubFlow) {
                            SubFlow flow = (SubFlow) impl;

                            String pkgName =
                                    procIdToPackageName
                                            .get(flow.getProcessId());
                            if (pkgName == null || pkgName.isEmpty()) {
                                fail("Error , Re-usable sub process references are broken post conversion."); //$NON-NLS-1$
                            }

                            assertEquals("Un-expected package reference found", //$NON-NLS-1$
                                    pkgName,
                                    flow.getPackageRefId());

                        }

                    }

                } else {
                    fail("Root Element should be an instance of Package"); //$NON-NLS-1$
                }
            }
        }
    }

    /**
     * Populates the map with the process id to the package name that are
     * contained within the passed list of converted XPDL's
     * 
     * @param convertedXpdls
     * @param procIdToPackageName
     */

    private void populateMap(Collection<IFile> convertedXpdls,
            Map<String, String> procIdToPackageName) {
        for (IFile iFile : convertedXpdls) {
            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(iFile);
            EObject rootElement = workingCopy.getRootElement();
            if (rootElement instanceof Package) {
                Package pkg = (Package) rootElement;

                procIdToPackageName.put(pkg.getProcesses().get(0).getId(),
                        pkg.getName());
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
                        "resources/FrameworkTests", "IpmBpm04_SubProcessReferencesMaintedPostSeparationTest/ImportIpmXpdls/resusabl2.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {

        return "IpmBpm04_SubProcessReferencesMaintedPostSeparationTest"; //$NON-NLS-1$
    }

}