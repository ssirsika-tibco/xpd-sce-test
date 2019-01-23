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
import com.tibco.xpd.ipm.iProcessExt.EAIStepDefinition;
import com.tibco.xpd.ipm.iProcessExt.IProcessExtPackage;
import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Conversion Test for EAIJavaStepConversionConmtribution.
 * 
 * @author aprasad
 * @since 28-Jul-2014
 */
public class IpmBpm29_EAIJavaStepConversionContributionTest extends
        AbstractIProcessConversionTest {

    /**
     * 
     */
    private static final String EAI_JAVA_STEP_TYPE = "EAIJAVA"; //$NON-NLS-1$

    private static final String IPROCESS_EAI_IMPLEMENTATION_TYPE =
            "iProcessEAI"; //$NON-NLS-1$

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

            if (rootElement instanceof com.tibco.xpd.xpdl2.Package) {

                Package pkg = (Package) rootElement;

                for (com.tibco.xpd.xpdl2.Process process : pkg.getProcesses()) {

                    // there is only one process in this file
                    /* Get all Activities in this process */

                    Collection<Activity> allActivitiesInProc =
                            Xpdl2ModelUtil.getAllActivitiesInProc(process);

                    for (Activity activity : allActivitiesInProc) {

                        /*
                         * an EAI Java Step should have a FIXME Annotation
                         * attached
                         */
                        TaskType taskType =
                                TaskObjectUtil.getTaskTypeStrict(activity);

                        if (TaskType.SERVICE_LITERAL.equals(taskType)) {
                            /* For Service Task */

                            /*
                             * we can skip type check for Task and null Check
                             * for TaskService as TaskType is already checked
                             * above
                             */
                            Task task = (Task) activity.getImplementation();
                            TaskService taskService = task.getTaskService();

                            Object otherAttribute =
                                    Xpdl2ModelUtil
                                            .getOtherAttribute(taskService,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_ImplementationType());

                            if (otherAttribute instanceof String) {
                                String implementationName =
                                        (String) otherAttribute;

                                if (IPROCESS_EAI_IMPLEMENTATION_TYPE
                                        .equals(implementationName)) {

                                    Object otherElement =
                                            Xpdl2ModelUtil
                                                    .getOtherElement(taskService,
                                                            IProcessExtPackage.eINSTANCE
                                                                    .getDocumentRoot_EAIStepDefinition());

                                    if (otherElement instanceof EAIStepDefinition) {

                                        /* Check for FIXME Annotation */

                                        EList<Association> incomingAssociations =
                                                activity.getIncomingAssociations();
                                        Artifact artifact = null;

                                        for (Association association : incomingAssociations) {

                                            Artifact tmpArtifact =
                                                    process.getPackage()
                                                            .getArtifact(association
                                                                    .getSource());

                                            if (tmpArtifact != null
                                                    && ArtifactType.ANNOTATION_LITERAL
                                                            .equals(tmpArtifact
                                                                    .getArtifactType())) {
                                                /*
                                                 * This is an annotation
                                                 * Artifact check for the text
                                                 */
                                                if (tmpArtifact
                                                        .getTextAnnotation() != null
                                                        && tmpArtifact
                                                                .getTextAnnotation()
                                                                .equals(Messages.EAIJavaStepConversionContribution_EAIJavaStepFIXMEText)) {
                                                    artifact = tmpArtifact;
                                                    break;
                                                }
                                            }

                                        }

                                        assertNotNull("FIXME Annotation Artifact missing", artifact); //$NON-NLS-1$

                                        /*
                                         * Check for Step Definition in the Step
                                         * Description
                                         */

                                        Description activityDescription =
                                                activity.getDescription();

                                        assertNotNull("Activity Description is NULL", activityDescription); //$NON-NLS-1$
                                        assertTrue("Activity Description does not contain Step Definition", (activityDescription.getValue() != null && !activityDescription.getValue().isEmpty())); //$NON-NLS-1$

                                    }
                                }
                            }
                        }
                    }

                }

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
                        "resources/ConversionTests", "IpmBpm29_EAIJavaStepConversionContributionTest/ImportIpmXpdls/eaijava.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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

        return "IpmBpm29_EAIJavaStepConversionContributionTest"; //$NON-NLS-1$
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
