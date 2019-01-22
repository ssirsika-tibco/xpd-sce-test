/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;

/**
 * Simple test to check if post conversion the participants are successfully
 * moved from process level to package level.
 * 
 * @author aallway
 * @since 3 Jun 2014
 */
public class IpmBpm01_MoveParticipantToPackageLevelSimpleTest extends
        AbstractIProcessConversionTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpmBpm01_MoveParticipantToPackageLevelSimpleTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/ConversionTests", "IpmBpm01_MoveParticipantToPackageLevelSimpleTest/ImportIpmXpdls/particip.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
        for (IFile iFile : convertedXpdls) {

            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(iFile);

            if (workingCopy == null) {
                fail("Working copy was found to be null"); //$NON-NLS-1$
            }

            EObject rootElement = workingCopy.getRootElement();

            if (rootElement instanceof Package) {
                Package pkg = (Package) rootElement;
                EList<Participant> pkgLevelParticipants = pkg.getParticipants();
                if (pkgLevelParticipants == null
                        || pkgLevelParticipants.isEmpty()) {
                    fail("Package is expected to have participants."); //$NON-NLS-1$
                }

                assertEquals("Expected number of participants were not found", //$NON-NLS-1$
                        1,
                        pkgLevelParticipants.size());

                EList<Process> processes = pkg.getProcesses();

                for (Process process : processes) {
                    EList<Participant> participants = process.getParticipants();
                    if (!participants.isEmpty()) {
                        fail("Participants were found at process level, however there should not be any participants at process level."); //$NON-NLS-1$
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

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {

        return CONVERSION_TYPE.IPM_TO_BPM_IMPORT_AND_CONVERT;
    }

}
