/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.javascript;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * In ACE the old "Process" and "WorkManagerFactory" static JS classes are now
 * wrapped up in as "bpm.process" and "bpm.workManager" class properties
 * respectively.
 * 
 * @author aallway
 * @since 02 Jul 2019
 */
public class AceProcessAndWMScriptTest extends AbstractN2BaseValidationTest {

    private ProjectImporter projectImporter;

    public AceProcessAndWMScriptTest() {
        super(true);
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#customTestResourceSetup()
     *
     */
    @Override
    protected void customTestResourceSetup() {
        /*
         * This test doesn't create files via the normal getTestResources()
         * because we want to import and migrate a whole project from AMX BPM.
         */
        projectImporter = TestUtil.importProjectsFromZip(
                "com.tibco.xpd.sce.test", //$NON-NLS-1$
                new String[] {
                        "resources/ProcessAndWMScriptClassTests/ProcessAndWorkManagerJSClassTest/" }, //$NON-NLS-1$
                new String[] { "ProcessAndWorkManagerJSClassTest" }); //$NON-NLS-1$

        assertTrue(
                "Failed to load projects from resources/ProcessAndWMScriptClassTests/", //$NON-NLS-1$
                projectImporter != null);

        TestUtil.buildAndWait();
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#tearDown()
     *
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        projectImporter.performDelete();
        super.tearDown();
    }

    /**
     * AceBomFactoryValidationsTest
     * 
     * @throws Exception
     */
    public void testAceBomFactoryValidationsTest() throws Exception {
        cleanProjectAtEnd = false;

        doTestValidations();

        /*
         * Check that the XPDL that has valid use of class factories and enum
         * packages is free from error markers.
         */
        IFile noErrorsXpdl =
                ResourcesPlugin.getWorkspace().getRoot().getProject("ProcessAndWorkManagerJSClassTest") //$NON-NLS-1$
                        .getFolder("Process Packages") //$NON-NLS-1$
                        .getFile("ValidUsage.xpdl"); //$NON-NLS-1$

        assertTrue("ValidUsage.xpdl should exist.", //$NON-NLS-1$
                noErrorsXpdl != null && noErrorsXpdl.isAccessible());

        List<ValidationsTestProblemMarkerInfo> problemMarkers = getProblemMarkers(noErrorsXpdl);

        for (ValidationsTestProblemMarkerInfo markerInfo : problemMarkers) {
            assertFalse("ValidUsage.xpdl should not have error level problem markers (has at least one)", //$NON-NLS-1$
                    markerInfo.getSourceMarker().getAttribute(IMarker.SEVERITY, -1) == IMarker.SEVERITY_ERROR);

        }

        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[] {

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.validateScriptTask", //$NON-NLS-1$
                        "_68N2kJzaEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : At Line:1 column:25, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:Mappy:Script1)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.validateScriptTask", //$NON-NLS-1$
                        "_68N2kJzaEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : At Line:2 column:1, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:Mappy:Script1)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.validateTimerEventScript", //$NON-NLS-1$
                        "_7piFAJzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : Timer Script::At Line:1 column:18, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:TimerEvent)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.validateTimerEventScript", //$NON-NLS-1$
                        "_7piFAJzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : Timer Script::At Line:1 column:23, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:TimerEvent)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.validateScriptTask", //$NON-NLS-1$
                        "_B1VqgJzbEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : At Line:1 column:24, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:Mappy:Script1)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.validateScriptTask", //$NON-NLS-1$
                        "_B1VqgJzbEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : At Line:2 column:1, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:Mappy:Script1)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.validateStdLoopExpr", //$NON-NLS-1$
                        "_bqwl0JzaEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : Std Loop Expr::At Line:1 column:19, Numeric Unary Operators should be only on Numeric values (InvalidUsageProcess:Loopy)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.validateStdLoopExpr", //$NON-NLS-1$
                        "_bqwl0JzaEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : Std Loop Expr::At Line:1 column:19, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:Loopy)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.error.taskCancelScript", //$NON-NLS-1$
                        "_hhYQYZzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : CancelScript::At Line:1 column:25, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:UserTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.error.taskCancelScript", //$NON-NLS-1$
                        "_hhYQYZzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : CancelScript::At Line:2 column:40, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:UserTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.error.taskCompleteScript", //$NON-NLS-1$
                        "_hhYQYZzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : CompleteScript::At Line:1 column:25, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:UserTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.error.taskCompleteScript", //$NON-NLS-1$
                        "_hhYQYZzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : CompleteScript::At Line:2 column:40, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:UserTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.error.taskInitiateScript", //$NON-NLS-1$
                        "_hhYQYZzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : InitiateScript::At Line:10 column:58, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:UserTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.error.taskInitiateScript", //$NON-NLS-1$
                        "_hhYQYZzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : InitiateScript::At Line:2 column:25, The process priority property must be in the range 100 to 400 (or null for system default). (InvalidUsageProcess:UserTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.error.taskInitiateScript", //$NON-NLS-1$
                        "_hhYQYZzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : InitiateScript::At Line:5 column:61, Property workManager is invalid for the current context (InvalidUsageProcess:UserTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.error.taskInitiateScript", //$NON-NLS-1$
                        "_hhYQYZzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : InitiateScript::At Line:7 column:25, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:UserTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.error.taskInitiateScript", //$NON-NLS-1$
                        "_hhYQYZzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : InitiateScript::At Line:8 column:40, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:UserTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.error.taskTimoutScript", //$NON-NLS-1$
                        "_hhYQYZzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : TimeoutScript::At Line:1 column:25, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:UserTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.error.taskTimoutScript", //$NON-NLS-1$
                        "_hhYQYZzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : TimeoutScript::At Line:2 column:40, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:UserTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.error.userTaskCloseScript", //$NON-NLS-1$
                        "_hhYQYZzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : CloseScript::At Line:1 column:72, Variable WorkManagerFactory not defined or is not associated in the task interface. (InvalidUsageProcess:UserTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.error.userTaskOpenScript", //$NON-NLS-1$
                        "_hhYQYZzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : OpenScript::At Line:1 column:72, Variable WorkManagerFactory not defined or is not associated in the task interface. (InvalidUsageProcess:UserTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.error.userTaskRescheduleScript", //$NON-NLS-1$
                        "_hhYQYZzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : RescheduleScript::At Line:1 column:72, Variable WorkManagerFactory not defined or is not associated in the task interface. (InvalidUsageProcess:UserTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.error.userTaskScheduleScript", //$NON-NLS-1$
                        "_hhYQYZzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : ScheduleScript::At Line:2 column:27, Property process is invalid for the current context (InvalidUsageProcess:UserTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.error.userTaskScheduleScript", //$NON-NLS-1$
                        "_hhYQYZzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : ScheduleScript::At Line:5 column:72, Variable WorkManagerFactory not defined or is not associated in the task interface. (InvalidUsageProcess:UserTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.error.userTaskScheduleScript", //$NON-NLS-1$
                        "_hhYQYZzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : ScheduleScript::At Line:7 column:64, Variable WorkManagerFactory not defined or is not associated in the task interface. (InvalidUsageProcess:UserTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.error.userTaskSubmitScript", //$NON-NLS-1$
                        "_hhYQYZzZEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : SubmitScript::At Line:1 column:72, Variable WorkManagerFactory not defined or is not associated in the task interface. (InvalidUsageProcess:UserTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.validateScriptTask", //$NON-NLS-1$
                        "_J7UIkJzaEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : At Line:1 column:51, Variable Process not defined or is not associated in the task interface. (InvalidUsagePageflowProcess:ScriptTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.validateMIAdditionalInstancesExpr", //$NON-NLS-1$
                        "_kFqVkJzaEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : MI Additional Instances Expr::At Line:1 column:27, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:Loopy2)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.validateMIComplexExitExpr", //$NON-NLS-1$
                        "_kFqVkJzaEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : MI Complex Exit Expr::At Line:1 column:23, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:Loopy2)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.validateMIComplexExitExpr", //$NON-NLS-1$
                        "_kFqVkJzaEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : MI Complex Exit Expr::At Line:2 column:1, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:Loopy2)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.validateMILoopExpr", //$NON-NLS-1$
                        "_kFqVkJzaEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : MI Loop Expr::At Line:1 column:19, Numeric Unary Operators should be only on Numeric values (InvalidUsageProcess:Loopy2)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/InvalidUsage.xpdl", //$NON-NLS-1$
                        "bx.validateMILoopExpr", //$NON-NLS-1$
                        "_kFqVkJzaEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : MI Loop Expr::At Line:1 column:19, Variable Process not defined or is not associated in the task interface. (InvalidUsageProcess:Loopy2)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/ProcessAndWorkManagerJSClassTest/Process Packages/ValidUsage.xpdl", //$NON-NLS-1$
                        "bx.warning.validateScriptTask", //$NON-NLS-1$
                        "_RKblTpzbEem6BdVd2XfGcQ", //$NON-NLS-1$
                        "BPM  : At Line:1 column:55, The method auditLog is not available in Pageflows/Business Services. (ValidUsagePageflowProcess:ScriptTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$
        };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "AceProcessAndWMScriptTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {};

        return testResources;
    }
}
