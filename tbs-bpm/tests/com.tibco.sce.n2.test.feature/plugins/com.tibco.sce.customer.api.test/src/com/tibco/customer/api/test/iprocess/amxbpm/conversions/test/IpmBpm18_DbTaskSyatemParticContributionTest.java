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
import com.tibco.xpd.xpdExtension.JdbcResource;
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
 * Tests that Checks if post conversion the expected number of jdbc system
 * participants are created and each DB task has 1 system partic assigned to it.
 * 
 * @author kthombar
 * @since 06-Jun-2014
 */
public class IpmBpm18_DbTaskSyatemParticContributionTest extends
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
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractValidationTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/ConversionTests", "IpmBpm18_DbTaskSyatemParticContributionTest/ImportIpmXpdls/dbtask.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionComparisonTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {

        checkGeneratedSystemPartic(convertedXpdls);

    }

    /**
     * Checks if the expected number of jdbc system participants are craeted and
     * each DB task has 1 system partic assigned to it.
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
                    5,
                    participants.size());

            for (Participant participant : participants) {
                if (participant.getName().startsWith("DatabaseParticipant")) { //$NON-NLS-1$
                    if (isDatabaseSystemParticipant(participant)) {
                        createdSystemPartic++;
                    } else {
                        fail("Participant with name starting with Database Participant should be a Jdbc system participant"); //$NON-NLS-1$
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

                            if (TaskImplementationTypeDefinitions.DATABASE_SERVICE
                                    .equalsIgnoreCase(ImplType)) {

                                assertEquals("DB task should have 1 system participant configures", //$NON-NLS-1$
                                        1,
                                        activity.getPerformerList().size());
                            }
                        }
                    }
                }
            }
        }

        assertEquals("unexpected number of created Jdbc System Participants", //$NON-NLS-1$
                4,
                createdSystemPartic);
    }

    /**
     * 
     * @param participant
     * @return <code>true</code> if the passed participant is a Java system
     *         Participant , else return <code>false</code>
     */
    private boolean isDatabaseSystemParticipant(Participant participant) {

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
                    JdbcResource jdbc = psr.getJdbc();
                    if (jdbc != null) {

                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        // TODO Auto-generated method stub
        return "IpmBpm18_DbTaskSyatemParticContributionTest"; //$NON-NLS-1$
    }

}
