/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.ImportDisplayStatus;

/**
 * Test that checks is some processes are already in workspace then they are
 * ignored, any re-usable sub process references to them are resolved and the
 * user is informed about the duplicate processes.
 * 
 * @author kthombar
 * @since 30-Apr-2014
 */
public class IpmBpm14_ProcessAlreadyInWorkSpaceTest extends
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
                        "resources/ConversionTests", //$NON-NLS-1$
                        "IpmBpm14_ProcessAlreadyInWorkSpaceTest/ImportIpmXpdls/resusabl2.ipmxpdl"), }; //$NON-NLS-1$

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
                new TestResourceInfo("resources/ConversionTests", //$NON-NLS-1$
                        "IpmBpm14_ProcessAlreadyInWorkSpaceTest/BpmXpdls{processes}/SUB.xpdl"), //$NON-NLS-1$
                new TestResourceInfo("resources/ConversionTests", //$NON-NLS-1$
                        "IpmBpm14_ProcessAlreadyInWorkSpaceTest/BpmXpdls{processes}/SUB2.xpdl"), //$NON-NLS-1$
                new TestResourceInfo("resources/ConversionTests", //$NON-NLS-1$
                        "IpmBpm14_ProcessAlreadyInWorkSpaceTest/BpmXpdls{processes}/SUB3.xpdl"), }; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {

        return "IpmBpm14_ProcessAlreadyInWorkSpaceTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {

        assertEquals("Unexpected number of XPDL's were generated after conversion.", //$NON-NLS-1$
                1,
                convertedXpdls.size());

    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#handleErrors(org.eclipse.core.runtime.IStatus)
     * 
     * @param importConvertStatus
     * @throws Exception
     */
    @Override
    protected void checkConversionStatusMessages(IStatus importConvertStatus)
            throws Exception {

        List<ImportDisplayStatus> allImportDisplayStatus =
                new ArrayList<ImportDisplayStatus>();

        populateImportstatusDisplays(importConvertStatus,
                allImportDisplayStatus);

        int ignoredProcessCount = 0;

        for (ImportDisplayStatus iDS : allImportDisplayStatus) {

            IStatus[] children = iDS.getChildren();
            for (IStatus iStatus2 : children) {
                /*
                 * scan through the statuses checking if wee get the expected
                 * statuses.
                 */
                if (iStatus2
                        .getMessage()
                        .contains("SUB3 (resusabl2.xpdl) already exists in: /IpmBpm14_ProcessAlreadyInWorkSpaceTest/BpmXpdls/SUB3.xpdl")) { //$NON-NLS-1$
                    ignoredProcessCount++;
                } else if (iStatus2
                        .getMessage()
                        .contains("SUB (resusabl2.xpdl) already exists in: /IpmBpm14_ProcessAlreadyInWorkSpaceTest/BpmXpdls/SUB.xpdl")) { //$NON-NLS-1$
                    ignoredProcessCount++;
                } else if (iStatus2
                        .getMessage()
                        .contains("SUB2 (resusabl2.xpdl) already exists in: /IpmBpm14_ProcessAlreadyInWorkSpaceTest/BpmXpdls/SUB2.xpdl")) { //$NON-NLS-1$
                    ignoredProcessCount++;
                }
            }
        }

        assertEquals("The number of processes to be ignored is not as expected.", //$NON-NLS-1$
                3,
                ignoredProcessCount);

    }

    /**
     * Reccursively scans through all the statuses and collects the
     * ImportDisplayStatus
     * 
     * @param importConvertStatus
     * @param allImportDisplayStatus
     */
    private void populateImportstatusDisplays(IStatus importConvertStatus,
            List<ImportDisplayStatus> allImportDisplayStatus) {

        if (importConvertStatus instanceof MultiStatus) {
            IStatus[] children = importConvertStatus.getChildren();

            for (IStatus iStatus : children) {
                if (iStatus instanceof ImportDisplayStatus) {
                    allImportDisplayStatus.add((ImportDisplayStatus) iStatus);
                } else {
                    populateImportstatusDisplays(iStatus,
                            allImportDisplayStatus);
                }
            }
        }
    }
}