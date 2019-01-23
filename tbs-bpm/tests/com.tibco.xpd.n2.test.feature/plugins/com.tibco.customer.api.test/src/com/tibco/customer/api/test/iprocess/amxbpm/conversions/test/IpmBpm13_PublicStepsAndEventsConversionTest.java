/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * 
 * @author aprasad
 * @since 04-Jun-2014
 */
public class IpmBpm13_PublicStepsAndEventsConversionTest extends
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
        for (IFile iFile : convertedXpdls) {

            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(iFile);
            assertNotNull("Working copy was found to be null", workingCopy); //$NON-NLS-1$

            EObject rootElement = workingCopy.getRootElement();
            assertNotNull("Root Element is Missing", rootElement); //$NON-NLS-1$

            assertTrue("Root Element Should be a Package", //$NON-NLS-1$
                    (rootElement instanceof Package));

            Package xpdlPackage = (Package) rootElement;

            EList<Process> processes = xpdlPackage.getProcesses();
            assertNotNull("Processes is Missing", processes); //$NON-NLS-1$
            assertEquals("Package Should have one Process", //$NON-NLS-1$
                    processes.size(),
                    1);

            Process process = processes.get(0);
            assertNotNull("Process is Null", process); //$NON-NLS-1$

            /* Public Steps */
            /* Check Public Step Activities to exist */

            Map<String, Boolean> publicFieldsData =
                    new HashMap<String, Boolean>();
            publicFieldsData.put("PUBTEXT", Boolean.TRUE); //$NON-NLS-1$
            checkPublicStartStep(process, "PUBUSER", publicFieldsData, iFile); //$NON-NLS-1$

            publicFieldsData = Collections.EMPTY_MAP;
            checkPublicStartStep(process, "PUBSCRPT", publicFieldsData, iFile); //$NON-NLS-1$
        }
    }

    /**
     * @param process
     * @param iFile
     */
    private void checkPublicStartStep(Process process, String publicStepName,
            Map<String, Boolean> publicStepFields, IFile iFile) {

        System.out.print(String.format("Process %1s Public Step %2s",//$NON-NLS-1$
                process.getName(),
                publicStepName));
        Activity publicStepAct =
                Xpdl2ModelUtil.getActivityByName(process, publicStepName);

        assertNotNull("Public Step Activity is missing", publicStepAct); //$NON-NLS-1$

        EList<Transition> incomingTransitions =
                publicStepAct.getIncomingTransitions();

        /* Check Start Event to be linked to the Public Step */
        assertNotNull("Start Event to  Public Step Link is missing", //$NON-NLS-1$
                incomingTransitions);

        for (Transition transition : incomingTransitions) {

            assertNotNull("Start Event to  Public Step Link is missing", //$NON-NLS-1$
                    transition);

            /*
             * Check Start Event for Public Step to exist and configured for
             * WebService and associated Parameters.
             */
            String startEventId = transition.getFrom();

            Activity linkedFromActivity =
                    Xpdl2ModelUtil.getActivityById(process, startEventId);

            assertNotNull("Activity Linked to Public Step is missing", //$NON-NLS-1$
                    linkedFromActivity);

            Event event = linkedFromActivity.getEvent();

            if (event != null && event instanceof StartEvent
                    && String.format("StartAt%1s", publicStepName) //$NON-NLS-1$
                            .equals(linkedFromActivity.getName())) {

                assertEquals("Start Event should be of type MESSAGE", //$NON-NLS-1$
                        EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL,
                        EventObjectUtil.getEventTriggerType(linkedFromActivity));

                checkOtherElements(((StartEvent) event).getTriggerResultMessage(),
                        process);
                checkOtherAttributes(((StartEvent) event)
                        .getTriggerResultMessage());

                checkPublicFields(linkedFromActivity, publicStepFields);
            }
        }

    }

    /**
     * @param startEventAct
     * @param publicStepFields
     */
    private void checkPublicFields(Activity activity,
            Map<String, Boolean> publicStepFields) {

        Object otherElement =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedParameters());

        String actName = activity.getName();

        String procName = activity.getProcess().getName();
        assertNotNull(String.format("AssociatedParameters type for Activity %1s in process %2s is Missing", //$NON-NLS-1$
                actName,
                procName),
                otherElement);

        assertTrue(String.format("AssociatedParameters type for Activity %1s in process %2s is wrong", //$NON-NLS-1$
                actName,
                procName),
                otherElement instanceof AssociatedParameters);

        AssociatedParameters assoParams = (AssociatedParameters) otherElement;

        if (publicStepFields.isEmpty()) {
            // Check for "No Interface ....." flag
            assertTrue(" \"No interface data association required\" flag should be set", //$NON-NLS-1$
                    assoParams.isDisableImplicitAssociation());
        } else {

            assertTrue("Associated Public Fields count is not correct", //$NON-NLS-1$
                    assoParams.getAssociatedParameter().size() >= publicStepFields
                            .size());

            Map<String, Boolean> actInfDataMap = new HashMap<String, Boolean>();

            for (AssociatedParameter assoParam : assoParams
                    .getAssociatedParameter()) {
                actInfDataMap.put(assoParam.getFormalParam(),
                        assoParam.isMandatory());
            }

            for (String publicField : publicStepFields.keySet()) {

                assertTrue(String.format("Public Field is missing for Activity %1s in Process %2s", //$NON-NLS-1$
                        actName,
                        procName),
                        actInfDataMap.containsKey(publicField));

                Boolean required = actInfDataMap.get(publicField);

                assertEquals(String.format("'%1s Public Field 'required' attribute is not correct for  for Activity %2s in Process %3s", //$NON-NLS-1$
                        publicField,
                        actName,
                        procName),
                        publicStepFields.get(publicField),
                        required);

            }

        }

    }

    /**
     * @param triggerResultMessage
     */
    private void checkOtherElements(
            OtherElementsContainer otherElementsContainer, Process process) {

        Object object =
                Xpdl2ModelUtil.getOtherElement(otherElementsContainer,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_PortTypeOperation());

        assertTrue("Port Type Operation not set correctly", //$NON-NLS-1$
                object instanceof PortTypeOperation);

        PortTypeOperation portTypeOp = (PortTypeOperation) object;

        assertEquals("Port type name is wrong ", process.getName(), portTypeOp.getPortTypeName()); //$NON-NLS-1$

        ExternalReference externalReference = portTypeOp.getExternalReference();

        assertNotNull("Port type Ext ref is Missing ", externalReference); //$NON-NLS-1$

        String wsldLocation = externalReference.getLocation();
        assertEquals("Port type External Location is not correct", //$NON-NLS-1$
                Xpdl2ModelUtil.getWsdlFileName(String.format("%1s.xpdl", //$NON-NLS-1$
                        process.getName())),
                wsldLocation);

    }

    private void checkOtherAttributes(
            OtherAttributesContainer otherAttribContainer) {

        /* Check generated */
        Object otherAttribute =
                Xpdl2ModelUtil.getOtherAttribute(otherAttribContainer,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Generated());

        assertNotNull("Document Root Generated Attribute is missing ", //$NON-NLS-1$
                otherAttribute);

        assertTrue("Generated attribute should be boolean ", //$NON-NLS-1$
                otherAttribute instanceof Boolean);

        assertTrue("Should be set to Generate WSDL ", //$NON-NLS-1$
                ((Boolean) otherAttribute));

        otherAttribute =
                Xpdl2ModelUtil.getOtherAttribute(otherAttribContainer,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType());

        assertNotNull("Impleentation Type Attribute is missing ", //$NON-NLS-1$
                otherAttribute);

        assertEquals("Implementation type Should be web Service ", //$NON-NLS-1$
                TaskImplementationTypeDefinitions.WEB_SERVICE,
                otherAttribute);
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
                        "resources/ConversionTests", "IpmBpm13_PublicStepsAndEventsConversionTest/ImportIpmXpdls/PublicStepsAndEvents.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
        // Not required
        return null;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {

        return "IpmBpm13_PublicStepsAndEventsConversionTest"; //$NON-NLS-1$
    }
}
