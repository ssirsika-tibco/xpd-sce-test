/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.EmailResource;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Tests that Checks if post conversion the expected number of Email system
 * participants are created and each Email task has 1 system partic assigned to
 * it.
 * 
 * @author kthombar
 * @since 14-Jul-2014
 */
public class IpsBpm07_EmailTaskSyatemParticContributionTest extends
        AbstractIProcessConversionTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {
        checkGeneratedSystemPartic(convertedXpdls);

    }

    /**
     * Checks if the expected number of Email system participants are created
     * and each Email task has 1 system partic assigned to it.
     * 
     * @param convertedXpdls
     */
    private void checkGeneratedSystemPartic(Collection<IFile> convertedXpdls) {
        assertEquals("unexpected number of converted xpdls", //$NON-NLS-1$
                1,
                convertedXpdls.size());

        int createdSystemPartic = 0;

        for (IFile iFile : convertedXpdls) {

            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(iFile);
            if (workingCopy == null) {
                fail("working copy cannnot be null"); //$NON-NLS-1$
            }
            Package pkg = (Package) workingCopy.getRootElement();
            EList<Participant> participants = pkg.getParticipants();
            assertEquals("unexpected number of participants", //$NON-NLS-1$
                    2,
                    participants.size());

            for (Participant participant : participants) {
                if (participant.getName().startsWith("EMailSystemParticipant")) { //$NON-NLS-1$
                    if (isEmailSystemParticipant(participant)) {
                        createdSystemPartic++;
                    } else {
                        fail("Participant with name starting with 'E-Mail System Participant' should be a Email system participant"); //$NON-NLS-1$
                    }
                }
            }
            Process process = pkg.getProcesses().get(0);
            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);

            for (Activity activity : allActivitiesInProc) {

                Implementation impl = activity.getImplementation();
                if (impl instanceof Task) {

                    Task task = (Task) impl;
                    TaskService taskService = task.getTaskService();
                    if (taskService != null) {
                        /* we are interested only in service tasks */
                        Object extensionAttribute =
                                Xpdl2ModelUtil
                                        .getOtherAttribute(taskService,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ImplementationType());

                        if (extensionAttribute instanceof String) {

                            String ImplType = extensionAttribute.toString();

                            if (TaskImplementationTypeDefinitions.EMAIL_SERVICE
                                    .equalsIgnoreCase(ImplType)) {

                                assertEquals("Email task should have 1 system participant configures", //$NON-NLS-1$
                                        1,
                                        activity.getPerformerList().size());
                            }
                        }
                    }
                }
            }
        }

        assertEquals("unexpected number of created Email System Participants", //$NON-NLS-1$
                1,
                createdSystemPartic);
    }

    /**
     * 
     * @param participant
     * @return <code>true</code> if the passed participant is a Email system
     *         Participant , else return <code>false</code>
     */
    private boolean isEmailSystemParticipant(Participant participant) {

        ParticipantTypeElem participantType = participant.getParticipantType();

        if (participantType != null) {

            ParticipantType type = participantType.getType();
            if (type != null && type.equals(ParticipantType.SYSTEM_LITERAL)) {

                Object extensionElement =
                        Xpdl2ModelUtil
                                .getOtherElement(participant,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ParticipantSharedResource());

                if (extensionElement instanceof ParticipantSharedResource) {

                    ParticipantSharedResource psr =
                            (ParticipantSharedResource) extensionElement;
                    EmailResource email = psr.getEmail();
                    if (email != null) {

                        return true;
                    }
                }
            }
        }
        return false;
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
                        "resources/ConversionTests", //$NON-NLS-1$
                        "IpsBpm07_EmailTaskSyatemParticContributionTest/Process Packages{processes}/test.xpdl"), }; //$NON-NLS-1$

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

        return "IpsBpm07_EmailTaskSyatemParticContributionTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {
        return CONVERSION_TYPE.IPS_TO_BPM_CONVERT;
    }

}
