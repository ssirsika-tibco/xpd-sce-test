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
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * JUnit to protect "User Task Addressee Handling" contribution.
 * 
 * @author sajain
 * @since Jun 12, 2014
 */
public class IpmBpm19_UserTaskAddresseeContributionTest extends
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
     * RQL expression text which is supposed to be put for an Org model query
     * type participant.
     */
    private final String RQL_EXPRESSION_TEXT = "resource(name = \"%s\")"; //$NON-NLS-1$

    /**
     * RQL grammar type.
     */
    private static final String RQL_GRAMMAR = "RQL"; //$NON-NLS-1$

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {

        for (IFile file : convertedXpdls) {

            if ("SAKET1.xpdl".equalsIgnoreCase(file.getName())) { //$NON-NLS-1$

                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);

                EObject rootElement = wc.getRootElement();

                if (rootElement instanceof Package) {

                    Package pkg = (Package) rootElement;

                    for (Process eachProcess : pkg.getProcesses()) {

                        Collection<Activity> allActivitiesInProc =
                                Xpdl2ModelUtil
                                        .getAllActivitiesInProc(eachProcess);

                        checkUserTaskPerformersToBeOfOrgModelQueryType(allActivitiesInProc,
                                eachProcess);

                    }
                }
            }
        }

    }

    /**
     * @param allActivitiesInProc
     * @param eachProcess
     */
    private void checkUserTaskPerformersToBeOfOrgModelQueryType(
            Collection<Activity> allActivitiesInProc, Process eachProcess) {

        Package pkg = eachProcess.getPackage();

        EList<Participant> participants = pkg.getParticipants();

        for (Activity eachActivity : allActivitiesInProc) {

            Implementation impl = eachActivity.getImplementation();

            if (impl instanceof Task) {

                Task task = (Task) impl;

                TaskUser userTask = task.getTaskUser();

                /*
                 * Check if it is a user task.
                 */
                if (null != userTask) {

                    EList<Performer> allPerformers =
                            eachActivity.getPerformerList();

                    /*
                     * We don't want to test anything when the user task doesn't
                     * have any performer at all. So proceed only if there is at
                     * least one performer.
                     */
                    if (allPerformers != null && !allPerformers.isEmpty()) {

                        for (Performer eachPerformer : allPerformers) {

                            /*
                             * Check if there's a participant with the given
                             * performer id
                             */
                            Participant currentParticipant =
                                    getParticipantFromPerformerId(eachPerformer.getValue(),
                                            participants);

                            if (currentParticipant != null) {

                                /*
                                 * There is a participant with the specified
                                 * performer id, so now check if it's type is
                                 * set to 'Organisation Model Query'.
                                 */
                                if (!isOrgModelQueryTypeParticipant(currentParticipant)) {

                                    /*
                                     * If it is not, then test fails.
                                     */
                                    fail(String
                                            .format("Participant %s is performer of a user task, but it is not of Organisation Model Query type.", //$NON-NLS-1$
                                                    currentParticipant
                                                            .getName()));
                                } else {
                                    /*
                                     * If it is, then check if the grammar is
                                     * set to RQL and also validate the RQL
                                     * expression.
                                     */

                                    checkGrammarAndScript(currentParticipant);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns <code>true</code> if the passed participant is an Organisation
     * Model Query Type participant, returns <code>false</code> otherwise.
     * 
     * @param participant
     * 
     * @return <code>true</code> if the passed participant is an Org Model Query
     *         Type participant, return <code>false</code> otherwise.
     */
    private boolean isOrgModelQueryTypeParticipant(Participant participant) {

        ParticipantTypeElem participantType = participant.getParticipantType();

        if (participantType != null) {

            ParticipantType type = participantType.getType();

            if (type != null) {

                return type.equals(ParticipantType.RESOURCE_SET_LITERAL);
            }
        }
        return false;
    }

    /**
     * Return the participant with the specified performer id. If no such
     * participant exists, return <code>null</code>.
     * 
     * @param performerId
     * @param participants
     * @return
     */
    private Participant getParticipantFromPerformerId(String performerId,
            EList<Participant> participants) {

        for (Participant eachParticipant : participants) {

            if (performerId.equals(eachParticipant.getId())) {

                /*
                 * Participant with the specified performer id found, return it.
                 */
                return eachParticipant;
            }
        }

        return null;
    }

    /**
     * Sets the participant type of the passed participant to 'Organisation
     * model query' and accordingly adds the RQL expression.
     * 
     * @param participant
     */
    private void checkGrammarAndScript(Participant participant) {

        /*
         * Get participant type.
         */
        ParticipantTypeElem participantType = participant.getParticipantType();

        Object expressionObj =
                Xpdl2ModelUtil.getOtherElement(participantType,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ParticipantQuery());

        if (expressionObj instanceof Expression) {
            Expression expression = (Expression) expressionObj;

            String actualGrammar = expression.getScriptGrammar();

            String actualText = expression.getText();

            /*
             * Check grammar.
             */
            assertEquals(RQL_GRAMMAR, actualGrammar);

            /*
             * Check expression text.
             */
            assertEquals(String.format(RQL_EXPRESSION_TEXT,
                    participant.getName()),
                    actualText);

        } else {
            fail("Participant query does not exist."); //$NON-NLS-1$
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
                        "resources/ConversionTests", "IpmBpm19_UserTaskAddresseeContributionTest/ImportIpmXpdls/usertskaddressee.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
        // Don't need anything here
        return null;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpmBpm19_UserTaskAddresseeContributionTest"; //$NON-NLS-1$
    }

}
