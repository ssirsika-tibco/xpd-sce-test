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
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * JUnit to protect transaction steps contribution.
 * 
 * @author aprasad
 * @since 07-May-2014
 */
public class IpmBpm03_TransactionStepsConversionTest extends
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
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpmBpm03_TransactionStepsConversionTest"; //$NON-NLS-1$
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
                        "resources/ConversionTests", "IpmBpm03_TransactionStepsConversionTest/ImportIpmXpdls/IPM_TransactionControlStep.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
            if ("TRANSTEP.xpdl".equals(file.getName())) { //$NON-NLS-1$

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
                        6);
                /*
                 * Check for Expected Activity for transaction Control Step
                 * Commit.
                 */
                Activity commitActivity =
                        Xpdl2ModelUtil.getActivityByName(process, "COMMIT1"); //$NON-NLS-1$
                /* Check Activity to Exit */
                Assert.assertNotNull("Commit Task is Missing", commitActivity); //$NON-NLS-1$
                /* Check for TASK TYPE to be NONE */
                Assert.assertNotNull("Commit Task Type Implementation is missing", //$NON-NLS-1$
                        commitActivity.getImplementation());
                Assert.assertEquals("Commit Task type should be NONE", //$NON-NLS-1$
                        com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil
                                .getTaskTypeStrict(commitActivity),
                        TaskType.NONE_LITERAL);

                /*
                 * Check for Expected Activity for transaction Control Step
                 * Commit.
                 */
                Activity abortTaskActivity =
                        Xpdl2ModelUtil.getActivityByName(process, "ABORTSTE"); //$NON-NLS-1$
                /* Check Activity to Exit */
                Assert.assertNotNull("Abort task is Missing", abortTaskActivity); //$NON-NLS-1$
                /* Check for TASK TYPE to be NONE */
                Assert.assertNotNull("Abort Task Type Implementation is missing", //$NON-NLS-1$
                        abortTaskActivity.getImplementation());
                Assert.assertEquals("Abort Task type should be NONE", //$NON-NLS-1$
                        com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil
                                .getTaskTypeStrict(abortTaskActivity),
                        TaskType.NONE_LITERAL);

                // check for Annotations
                Collection<Artifact> allArtifactsInProcess =
                        Xpdl2ModelUtil.getAllArtifactsInProcess(process);
                // check that artifacts should exist
                Assert.assertNotNull("Artifacts Missing", allActivitiesInProc); //$NON-NLS-1$
                /* Track the Artifacts encountered */
                boolean fixMeAnnotCreatedCorrect = false, convInfoAnnotCreatedCorrect =
                        false;

                for (Artifact artifact : allArtifactsInProcess) {

                    if (artifact.getTextAnnotation().startsWith("FIXME:")) { //$NON-NLS-1$
                        EList<Association> outgoingAssociations =
                                artifact.getOutgoingAssociations();

                        Assert.assertTrue("Association from FixME Artifact to ABORT Task is missing", (outgoingAssociations != null && !outgoingAssociations.isEmpty())); //$NON-NLS-1$
                        /*
                         * Should have only one Association to the Task type
                         * NONE for Abort
                         */
                        Assert.assertEquals("Annotation should have only 1 outgoing Association to the Task type NONE for Abort", //$NON-NLS-1$
                                outgoingAssociations.size(),
                                1);
                        Association association = outgoingAssociations.get(0);

                        Assert.assertNotNull("Association from FixME Artifact to ABORT Task is NULL", association); //$NON-NLS-1$

                        Assert.assertEquals("FIXME Association Target should be the Abort Task", //$NON-NLS-1$
                                association.getTarget(),
                                abortTaskActivity.getId());
                        /* after every test has success */
                        fixMeAnnotCreatedCorrect = true;

                    } else if (artifact.getTextAnnotation()
                            .startsWith("Conversion Info:")) { //$NON-NLS-1$

                        EList<Association> outgoingAssociations =
                                artifact.getOutgoingAssociations();

                        Assert.assertTrue("Association from 'Converiosn Info:' Artifact to COMMIT Task is missing",//$NON-NLS-1$
                                (outgoingAssociations != null && !outgoingAssociations
                                        .isEmpty()));
                        /*
                         * Should have only one Association to the Task type
                         * NONE for Commit
                         */
                        Assert.assertEquals("Annotation should have only 1 outgoing Association to the Task type NONE for COMMIT task", //$NON-NLS-1$
                                outgoingAssociations.size(),
                                1);
                        Association association = outgoingAssociations.get(0);

                        Assert.assertNotNull("Association from 'Conversion Info:' Artifact to COMMIT Task is NULL", association); //$NON-NLS-1$

                        Assert.assertEquals("'Conversion Info:' Artifact Target should be the COMMIT Task", //$NON-NLS-1$
                                association.getTarget(),
                                commitActivity.getId());
                        /* after every test has success */
                        convInfoAnnotCreatedCorrect = true;
                    }
                }

                Assert.assertTrue("Annotation for COMMIT Task is either missing or Not Created Correctly", convInfoAnnotCreatedCorrect); //$NON-NLS-1$
                Assert.assertTrue("Annotation for ABORT Task is either missing or Not Created Correctly", fixMeAnnotCreatedCorrect); //$NON-NLS-1$

            }
        }
    }
}
