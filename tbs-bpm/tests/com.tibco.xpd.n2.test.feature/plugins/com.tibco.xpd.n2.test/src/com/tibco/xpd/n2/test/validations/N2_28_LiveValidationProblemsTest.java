/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.test.validations;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Fix script expressions for various tasks and mapping and test after each fix
 * that the relevant problem marker is not raised again on live validation and
 * that the other markers are still there (i.e., the fix should only remove
 * expected marker).
 * 
 * 
 * @author agondal
 * @since 2 Oct 2013
 */
public class N2_28_LiveValidationProblemsTest extends
        AbstractBaseValidationTest {

    public static final String JAVASCRIPT_GRAMMAR = "JavaScript"; //$NON-NLS-1$

    String msgActualProblemNotFound =
            "The following problem marker to be fixed was not found on the resource: \n %1$s"; //$NON-NLS-1$

    String msgProblemStillExist =
            "The following problem marker still exists: \n %1$s"; //$NON-NLS-1$

    /**
     * Tests problem marker for an unmapped script on an activity disappears
     * after fixing the script expression
     * 
     * @throws Exception
     */
    public void testFixExpressionsAndCheckProblemsDisappearTest1()
            throws Exception {
        // Check all files created correctly.
        checkTestFilesCreated();

        IFile testFile = getTestFile("Package1.xpdl"); //$NON-NLS-1$

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(testFile);

        if (wc != null && wc.getRootElement() instanceof Package) {

            List<ValidationsTestProblemMarkerInfo> resourceMarkersBeforeFix =
                    getProblemMarkers(testFile);

            ValidationsTestProblemMarkerInfo markerToCheck =
                    getValidationProblemMarker(0);

            String msg = String.format(msgActualProblemNotFound, markerToCheck);
            assertTrue(msg, resourceMarkersBeforeFix.contains(markerToCheck));

            EditingDomain ed = wc.getEditingDomain();
            Package pckg = (Package) wc.getRootElement();

            Process process = pckg.getProcesses().get(0);
            assertNotNull(process);

            Activity activity =
                    Xpdl2ModelUtil.getActivityByName(process, "SendTask"); //$NON-NLS-1$
            assertNotNull(activity);

            ScriptInformation scriptInformation =
                    (ScriptInformation) Xpdl2ModelUtil
                            .getOtherElement(activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_Script());

            String newScriptText = "\"a\";"; //$NON-NLS-1$
            Expression newExp = Xpdl2ModelUtil.createExpression(newScriptText);
            newExp.setScriptGrammar(JAVASCRIPT_GRAMMAR);

            Command cmd =
                    SetCommand.create(ed,
                            scriptInformation,
                            XpdExtensionPackage.eINSTANCE
                                    .getScriptInformation_Expression(),
                            newExp);

            if (cmd != null) {
                ed.getCommandStack().execute(cmd);
            }

            waitForValidationToFinish();

            /*
             * check and fail if the problem marker for the above fix is still
             * there
             */

            List<ValidationsTestProblemMarkerInfo> resourceMarkersAfterFix =
                    getProblemMarkers(testFile);

            /*
             * since we are interested in live validation, so only save the
             * resource after checking the live validation problem markers
             */
            wc.save();

            msg = String.format(msgProblemStillExist, markerToCheck);

            /*
             * We should have removed the maker to check by now. So, remove it
             * from the resourceMarkersBeforeFix list and compare the two lists.
             * This makes sure that the problem is fixed but the other markers
             * have not been removed
             */
            assertFalse(msg, resourceMarkersAfterFix.contains(markerToCheck));

            resourceMarkersBeforeFix.remove(markerToCheck);

            if (resourceMarkersAfterFix.size() != resourceMarkersBeforeFix
                    .size()
                    || !resourceMarkersAfterFix
                            .containsAll(resourceMarkersBeforeFix)) {
                fail(msg);
            }
        }
    }

    /**
     * Tests problem marker for a mapped script on an activity disappears after
     * fixing the script expression
     * 
     * @throws Exception
     */
    public void testFixExpressionsAndCheckProblemsDisappearTest2()
            throws Exception {

        IFile testFile = getTestFile("Package1.xpdl"); //$NON-NLS-1$

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(testFile);

        if (wc != null && wc.getRootElement() instanceof Package) {

            List<ValidationsTestProblemMarkerInfo> resourceMarkersBeforeFix =
                    getProblemMarkers(testFile);

            ValidationsTestProblemMarkerInfo markerToCheck =
                    getValidationProblemMarker(1);
            String msg = String.format(msgActualProblemNotFound, markerToCheck);
            assertTrue(msg, resourceMarkersBeforeFix.contains(markerToCheck));

            EditingDomain ed = wc.getEditingDomain();
            Package pckg = (Package) wc.getRootElement();

            Process process = pckg.getProcesses().get(0);
            assertNotNull(process);

            Activity activity =
                    Xpdl2ModelUtil.getActivityByName(process,
                            "CatchMessageEvent"); //$NON-NLS-1$
            assertNotNull(activity);

            List<DataMapping> dataMappings =
                    Xpdl2ModelUtil.getDataMappings(activity,
                            DirectionType.OUT_LITERAL);

            DataMapping dataMapping = null;
            for (DataMapping mapping : dataMappings) {
                if (mapping.getFormal().equals("_SCRIPT_")) { //$NON-NLS-1$
                    dataMapping = mapping;
                }
            }
            assertNotNull(dataMapping);

            ScriptInformation scriptInformation =
                    EcoreUtil.copy((ScriptInformation) Xpdl2ModelUtil
                            .getOtherElement(dataMapping,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_Script()));

            String newScriptText = "\"a\";"; //$NON-NLS-1$
            Expression newExp = Xpdl2ModelUtil.createExpression(newScriptText);
            newExp.setScriptGrammar(JAVASCRIPT_GRAMMAR);
            scriptInformation.setExpression(newExp);

            Command cmd =
                    Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                            dataMapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script(),
                            scriptInformation);
            if (cmd != null) {
                ed.getCommandStack().execute(cmd);
            }

            waitForValidationToFinish();

            /*
             * check and fail if the problem marker for the above fix is still
             * there
             */

            List<ValidationsTestProblemMarkerInfo> resourceMarkersAfterFix =
                    getProblemMarkers(testFile);

            /*
             * since we are interested in live validation, so only save the
             * resource after checking the live validation problem markers
             */
            wc.save();

            msg = String.format(msgProblemStillExist, markerToCheck);

            /*
             * We should have removed the maker to check now so remove it from
             * the resourceMarkersBeforeFix list and compare the two lists. This
             * makes sure that this problem is fixed but the other markers have
             * not been removed
             */

            assertFalse(msg, resourceMarkersAfterFix.contains(markerToCheck));

            resourceMarkersBeforeFix.remove(markerToCheck);

            if (resourceMarkersAfterFix.size() != resourceMarkersBeforeFix
                    .size()
                    || !resourceMarkersAfterFix
                            .containsAll(resourceMarkersBeforeFix)) {
                fail(msg);
            }
        }
    }

    /**
     * Tests problem markers on a script task disappear when the script
     * expressions are fixed and remaining problem markers still exist
     * 
     * @throws Exception
     */
    public void testFixExpressionsAndCheckProblemsDisappearTest3()
            throws Exception {

        IFile testFile = getTestFile("Package1.xpdl"); //$NON-NLS-1$

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(testFile);

        if (wc != null && wc.getRootElement() instanceof Package) {

            List<ValidationsTestProblemMarkerInfo> resourceMarkersBeforeFix =
                    getProblemMarkers(testFile);

            ValidationsTestProblemMarkerInfo markerToCheck =
                    getValidationProblemMarker(2);
            String msg = String.format(msgActualProblemNotFound, markerToCheck);
            assertTrue(msg, resourceMarkersBeforeFix.contains(markerToCheck));

            EditingDomain ed = wc.getEditingDomain();
            Package pckg = (Package) wc.getRootElement();

            Process process = pckg.getProcesses().get(0);
            assertNotNull(process);

            Activity activity =
                    Xpdl2ModelUtil.getActivityByName(process, "ScriptTask"); //$NON-NLS-1$
            assertNotNull(activity);

            String newScriptText = "\"a\";"; //$NON-NLS-1$

            Command cmd =
                    ProcessScriptUtil.getSetScriptTaskScriptCommand(ed,
                            newScriptText,
                            activity,
                            JAVASCRIPT_GRAMMAR);
            if (cmd != null) {
                ed.getCommandStack().execute(cmd);
            }

            waitForValidationToFinish();

            /*
             * check and fail if the problem marker for the above fix is still
             * there
             */

            List<ValidationsTestProblemMarkerInfo> resourceMarkersAfterFix =
                    getProblemMarkers(testFile);

            /*
             * since we are interested in live validation, so only save the
             * resource after checking the live validation problem markers
             */
            wc.save();

            msg = String.format(msgProblemStillExist, markerToCheck);

            /*
             * We should have removed the maker to check now so remove it from
             * the resourceMarkersBeforeFix list and compare the two lists. This
             * makes sure that this problem is fixed but the other markers have
             * not been removed
             */
            resourceMarkersBeforeFix.remove(markerToCheck);

            if (resourceMarkersAfterFix.size() != resourceMarkersBeforeFix
                    .size()
                    || !resourceMarkersAfterFix
                            .containsAll(resourceMarkersBeforeFix)) {
                fail(msg);
            }
        }
    }

    /**
     * Tests problem markers on a task activity disappear when the initiated
     * script expression is fixed and remaining problem markers still exist
     * 
     * @throws Exception
     */
    public void testFixExpressionsAndCheckProblemsDisappearTest4()
            throws Exception {

        IFile testFile = getTestFile("Package1.xpdl"); //$NON-NLS-1$

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(testFile);

        if (wc != null && wc.getRootElement() instanceof Package) {

            List<ValidationsTestProblemMarkerInfo> resourceMarkersBeforeFix =
                    getProblemMarkers(testFile);

            ValidationsTestProblemMarkerInfo markerToCheck =
                    getValidationProblemMarker(3);
            String msg = String.format(msgActualProblemNotFound, markerToCheck);
            assertTrue(msg, resourceMarkersBeforeFix.contains(markerToCheck));

            EditingDomain ed = wc.getEditingDomain();
            Package pckg = (Package) wc.getRootElement();

            Process process = pckg.getProcesses().get(0);
            assertNotNull(process);

            Activity activity =
                    Xpdl2ModelUtil.getActivityByName(process, "Task"); //$NON-NLS-1$
            assertNotNull(activity);

            String newScriptText = "\"a\";"; //$NON-NLS-1$

            // get command to set initiate script on script task

            Command cmd =
                    ProcessScriptUtil.getSetInitiatedScriptCommand(ed,
                            newScriptText,
                            activity,
                            JAVASCRIPT_GRAMMAR);

            if (cmd != null) {
                ed.getCommandStack().execute(cmd);
            }

            waitForValidationToFinish();

            /*
             * check and fail if the problem marker for the above fix is still
             * there
             */

            List<ValidationsTestProblemMarkerInfo> resourceMarkersAfterFix =
                    getProblemMarkers(testFile);

            /*
             * since we are interested in live validation, so only save the
             * resource after checking the live validation problem markers
             */
            wc.save();

            msg = String.format(msgProblemStillExist, markerToCheck);

            /*
             * We should have removed the maker to check now so remove it from
             * the resourceMarkersBeforeFix list and compare the two lists. This
             * makes sure that this problem is fixed but the other markers have
             * not been removed
             */

            assertFalse(msg, resourceMarkersAfterFix.contains(markerToCheck));

            resourceMarkersBeforeFix.remove(markerToCheck);
            if (resourceMarkersAfterFix.size() != resourceMarkersBeforeFix
                    .size()
                    || !resourceMarkersAfterFix
                            .containsAll(resourceMarkersBeforeFix)) {
                fail(msg);
            }
        }
    }

    /**
     * Tests problem marker for a script on a transition disappears after fixing
     * the script expression
     * 
     * @throws Exception
     */
    public void testFixExpressionsAndCheckProblemsDisappearTest5()
            throws Exception {

        IFile testFile = getTestFile("Package1.xpdl"); //$NON-NLS-1$

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(testFile);

        if (wc != null && wc.getRootElement() instanceof Package) {

            List<ValidationsTestProblemMarkerInfo> resourceMarkersBeforeFix =
                    getProblemMarkers(testFile);

            ValidationsTestProblemMarkerInfo markerToCheck =
                    getValidationProblemMarker(4);
            String msg = String.format(msgActualProblemNotFound, markerToCheck);
            assertTrue(msg, resourceMarkersBeforeFix.contains(markerToCheck));

            EditingDomain ed = wc.getEditingDomain();
            Package pckg = (Package) wc.getRootElement();

            Process process = pckg.getProcesses().get(0);
            assertNotNull(process);

            String transName = "transitionX"; //$NON-NLS-1$
            Transition transition = null;
            for (Transition trans : process.getTransitions()) {
                if (transName.equals(trans.getName())) {
                    transition = trans;
                    break;
                }
            }
            assertNotNull(transition);

            Condition condition = transition.getCondition();

            String newScriptText = "\"a\";"; //$NON-NLS-1$

            Expression newExp = Xpdl2ModelUtil.createExpression(newScriptText);
            newExp.setScriptGrammar(JAVASCRIPT_GRAMMAR);

            Command cmd =
                    SetCommand.create(ed,
                            condition,
                            Xpdl2Package.eINSTANCE.getCondition_Expression(),
                            newExp);
            if (cmd != null) {
                ed.getCommandStack().execute(cmd);
            }

            waitForValidationToFinish();

            /*
             * check and fail if the problem marker for the above fix is still
             * there
             */

            List<ValidationsTestProblemMarkerInfo> resourceMarkersAfterFix =
                    getProblemMarkers(testFile);

            /*
             * since we are interested in live validation, so only save the
             * resource after checking the live validation problem markers
             */
            wc.save();

            /*
             * We should have removed the maker to check now so remove it from
             * the resourceMarkersBeforeFix list and compare the two lists. This
             * makes sure that this problem is fixed but the other markers have
             * not been removed
             */
            msg = String.format(msgProblemStillExist, markerToCheck);
            assertFalse(msg, resourceMarkersAfterFix.contains(markerToCheck));

            resourceMarkersBeforeFix.remove(markerToCheck);

            assertFalse(msg, resourceMarkersBeforeFix.contains(markerToCheck));
            if (resourceMarkersAfterFix.size() != resourceMarkersBeforeFix
                    .size()
                    || !resourceMarkersAfterFix
                            .containsAll(resourceMarkersBeforeFix)) {
                fail(msg);
            }
        }
    }

    /**
     * Tests problem marker for on script task script disappears after fixing
     * the process interface parameter in the same.
     * 
     * @throws Exception
     */
    public void testFixExpressionsAndCheckProblemsDisappearTest6()
            throws Exception {

        IFile testFile = getTestFile("Package1.xpdl"); //$NON-NLS-1$

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(testFile);

        if (wc != null && wc.getRootElement() instanceof Package) {

            List<ValidationsTestProblemMarkerInfo> resourceMarkersBeforeFix =
                    getProblemMarkers(testFile);

            ValidationsTestProblemMarkerInfo markerToCheck =
                    getValidationProblemMarker(5);
            String msg = String.format(msgActualProblemNotFound, markerToCheck);
            assertTrue(msg, resourceMarkersBeforeFix.contains(markerToCheck));

            EditingDomain ed = wc.getEditingDomain();
            Package pckg = (Package) wc.getRootElement();
            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(pckg);

            ProcessInterface pi =
                    processInterfaces.getProcessInterface().get(0);
            assertNotNull(pi);

            FormalParameter paramPI = pi.getFormalParameters().get(0);

            Command cmd =
                    Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                            paramPI,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            "ParamPI2"); //$NON-NLS-1$

            if (cmd != null) {
                ed.getCommandStack().execute(cmd);
            }

            waitForValidationToFinish();

            /*
             * Since this fix removes the problem marker from the task, we need
             * to remove it from the actual resource markers before comparison.
             */

            List<ValidationsTestProblemMarkerInfo> resourceMarkersAfterFix =
                    getProblemMarkers(testFile);

            /*
             * since we are interested in live validation, so only save the
             * resource after checking the live validation problem markers
             */
            wc.save();

            msg = String.format(msgProblemStillExist, markerToCheck);

            assertFalse(msg, resourceMarkersAfterFix.contains(markerToCheck));

            resourceMarkersBeforeFix.remove(markerToCheck);

            if (resourceMarkersAfterFix.size() != resourceMarkersBeforeFix
                    .size()
                    || !resourceMarkersAfterFix
                            .containsAll(resourceMarkersBeforeFix)) {
                fail(msg);
            }
        }
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestName()
     * 
     * @return
     */
    @Override
    protected String getTestName() {
        return "FixExpressionsAndCheckProblemsDisappearTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestResources()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/ExpressionValidationRulesTest", "BpmProject/Process Packages{processes}/Package1.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$                        
                };

        return testResources;
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestPlugInId()
     * 
     * @return
     */
    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    private ValidationsTestProblemMarkerInfo getValidationProblemMarker(
            int index) {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_ABnmkCBlEeOr79Nx_xXmEw", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:SendTask:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_M2YH8CBkEeOr79Nx_xXmEw", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable b not defined or is not associated in the task interface. (Process1:CatchMessageEvent:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_bZ--cB67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:ScriptTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskInitiateScript", //$NON-NLS-1$ 
                                "__FlUsB66EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : InitiateScript::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:Task)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateConditionalTransitionScript", //$NON-NLS-1$ 
                                "_z9-RoR67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable a not defined or is not associated in the task interface. (transitionX)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_Dw9VAyxsEeOaW7GpExN9nQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:9, Variable ParamPI2 not defined or is not associated in the task interface. (Process3:ScriptTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };

        return markerInfos[index];
    }

    /**
     * @see com.tibco.xpd.core.test.validations.AbstractBaseValidationTest#getValidationProblemMarkerInfos()
     * 
     * @return
     */
    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {

        return null;
    }

}
