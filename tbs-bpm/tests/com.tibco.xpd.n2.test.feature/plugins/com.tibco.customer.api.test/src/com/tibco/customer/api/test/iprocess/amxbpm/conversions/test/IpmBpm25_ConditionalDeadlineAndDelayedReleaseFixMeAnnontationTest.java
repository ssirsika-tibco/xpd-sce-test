/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import java.util.Collection;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * JUnit to test if a FIX-ME annotation gets added to activities havind
 * conditional delayed release or conditional deadline set.
 * 
 * @author sajain
 * @since 02-Jul-2014
 */
public class IpmBpm25_ConditionalDeadlineAndDelayedReleaseFixMeAnnontationTest
        extends AbstractIProcessConversionTest {

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
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpmBpm25_ConditionalDeadlineAndDelayedReleaseFixMeAnnontationTest"; //$NON-NLS-1$
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
                        "resources/ConversionTests", "IpmBpm25_ConditionalDeadlineAndDelayedReleaseFixMeAnnontationTest/ImportIpmXpdls/testlab.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };
        return testResources;
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
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {
        for (IFile file : convertedXpdls) {
            if ("TESTLAB.xpdl".equals(file.getName())) { //$NON-NLS-1$

                WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(file);
                Assert.assertNotNull("Working Copy Can't be loaded for the converted XPDL", //$NON-NLS-1$
                        workingCopy);

                EObject rootElement = workingCopy.getRootElement();
                Assert.assertNotNull("Root Element is Missing", rootElement); //$NON-NLS-1$

                Assert.assertTrue("Root Element Should be a Package", //$NON-NLS-1$
                        (rootElement instanceof Package));

                Package xpdlPackage = (Package) rootElement;

                EList<Process> processes = xpdlPackage.getProcesses();
                Assert.assertNotNull("Processes is Missing", processes); //$NON-NLS-1$
                Assert.assertEquals("Package Should have one Process", //$NON-NLS-1$
                        processes.size(),
                        1);

                Process process = processes.get(0);
                Collection<Activity> allActivitiesInProc =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);

                Assert.assertEquals("Expected Activities Wrong", //$NON-NLS-1$
                        allActivitiesInProc.size(),
                        9);

                // check for Annotations
                Collection<Artifact> allArtifactsInProcess =
                        Xpdl2ModelUtil.getAllArtifactsInProcess(process);
                // check that artifacts should exist
                Assert.assertNotNull("Artifacts Missing", allActivitiesInProc); //$NON-NLS-1$
                /* Track the Artifacts encountered */
                boolean fixMeAnnotCreatedCorrect1 = false;
                boolean fixMeAnnotCreatedCorrect2 = false;

                for (Artifact artifact : allArtifactsInProcess) {

                    if (artifact
                            .getTextAnnotation()
                            .startsWith("FIXME: Original iProcess activity used the conditional deadline feature which has no direct equivalent in BPM.")) { //$NON-NLS-1$

                        EList<Association> outgoingAssociations =
                                artifact.getOutgoingAssociations();

                        Assert.assertTrue("Association from FixME Artifact to User task is missing", (outgoingAssociations != null && !outgoingAssociations.isEmpty())); //$NON-NLS-1$

                        Assert.assertEquals("Annotation should have only 1 outgoing Association to the User task", //$NON-NLS-1$
                                outgoingAssociations.size(),
                                1);
                        Association association = outgoingAssociations.get(0);

                        Assert.assertNotNull("Association from FixME Artifact to User task is NULL", association); //$NON-NLS-1$

                        Activity usertsk =
                                Xpdl2ModelUtil.getActivityByName(process, "UT"); //$NON-NLS-1$

                        Assert.assertEquals("FIXME Association Target should be the User task 'UT'", //$NON-NLS-1$
                                association.getTarget(),
                                usertsk.getId());

                        /* after every test has success */
                        fixMeAnnotCreatedCorrect1 = true;

                    } else if (artifact
                            .getTextAnnotation()
                            .startsWith("FIXME: Original iProcess activity used the conditional delayed release feature which has no direct equivalent in BPM.")) { //$NON-NLS-1$

                        EList<Association> outgoingAssociations =
                                artifact.getOutgoingAssociations();

                        Assert.assertTrue("Association from FixME Artifact to EAI task is missing", (outgoingAssociations != null && !outgoingAssociations.isEmpty())); //$NON-NLS-1$

                        Assert.assertEquals("Annotation should have only 1 outgoing Association to the EAI task", //$NON-NLS-1$
                                outgoingAssociations.size(),
                                1);
                        Association association = outgoingAssociations.get(0);

                        Assert.assertNotNull("Association from FixME Artifact to EAI task is NULL", association); //$NON-NLS-1$

                        Activity eaistp =
                                Xpdl2ModelUtil.getActivityByName(process,
                                        "EAISTEP"); //$NON-NLS-1$

                        Assert.assertEquals("FIXME Association Target should be the EAI task 'EAISTEP'", //$NON-NLS-1$
                                association.getTarget(),
                                eaistp.getId());

                        /* after every test has success */
                        fixMeAnnotCreatedCorrect2 = true;

                    }
                }
                Assert.assertTrue("Annotation for activities having conditional delayed release or condition deadline is either missing or Not Created Correctly", fixMeAnnotCreatedCorrect1 && fixMeAnnotCreatedCorrect2); //$NON-NLS-1$
            }
        }
    }
}
