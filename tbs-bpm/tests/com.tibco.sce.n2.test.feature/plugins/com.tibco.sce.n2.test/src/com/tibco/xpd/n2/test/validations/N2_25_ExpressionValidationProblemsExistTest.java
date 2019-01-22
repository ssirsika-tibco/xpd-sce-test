/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Tests whether all the validation problem markers in this test are correctly
 * raised on clean build and on live validation.
 * 
 * @author agondal
 * @since 26 Sep 2013
 */
public class N2_25_ExpressionValidationProblemsExistTest extends
        AbstractBaseValidationTest {

    public N2_25_ExpressionValidationProblemsExistTest() {
        super(true);
    }

    /**
     * N2_25_ExpressionValidationProblemsExistTest
     * 
     * @throws Exception
     */
    public void testProblemsExistOnCleanValidationTest() throws Exception {
        doTestValidations();

        return;
    }

    /**
     * Make two changes to package data field and expected markers should still
     * be there
     * 
     * @throws Exception
     */
    public void testProblemsExistOnLiveValidationTest() throws Exception {

        IFile testFile = getTestFile("Package1.xpdl"); //$NON-NLS-1$

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(testFile);

        if (wc != null && wc.getRootElement() instanceof Package) {

            EditingDomain ed = wc.getEditingDomain();
            Package pckg = (Package) wc.getRootElement();

            DataField dataFiled = pckg.getDataFields().get(0);

            String fieldName = "F1"; //$NON-NLS-1$
            Command cmd =
                    Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                            dataFiled,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            fieldName);

            if (cmd != null) {
                ed.getCommandStack().execute(cmd);
            }

            fieldName = "F2"; //$NON-NLS-1$
            cmd =
                    Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                            dataFiled,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            fieldName);

            if (cmd != null) {
                ed.getCommandStack().execute(cmd);
            }

            waitForValidationToFinish();

            ValidationsTestProblemMarkerInfo[] expectedMarkers =
                    getValidationProblemMarkerInfos();

            List<ValidationsTestProblemMarkerInfo> actualMarkers =
                    getProblemMarkers(testFile);

            wc.save();
            for (ValidationsTestProblemMarkerInfo expectedMarker : expectedMarkers) {
                if (!actualMarkers.contains(expectedMarker)) {
                    fail(String.format("Expected problem marker not raised: " //$NON-NLS-1$
                            + expectedMarker));
                }
            }
        }
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.scriptUnsupportedForType", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.0/@event/@triggerResultMessage/@message/@otherElements.0/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager 2.x : Input To Process: script mappings are not supported for target 'CorrelationField' data type. (Process1:CatchMessageEvent:CorrelationField)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.20/@implementation/@taskService/@messageOut/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager 2.x : Output From Service: The data types are incompatible for mapping 'Script2' to 'Param2'. (Process1:JavaServiceTask2:Param2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.scriptUnsupported", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.22/@implementation/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager 2.x : Map From Sub-Process: script mappings are not supported. (Process1:ReusableSubProcess:Param1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.scriptUnsupportedForType", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.7/@implementation/@taskReceive/@message/@otherElements.0/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager 2.x : Input To Process: script mappings are not supported for target 'CorrelationField' data type. (Process1:ReceiveTask:CorrelationField)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_-4MY4B9-EeOj_ML5qnrdmg", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:4, Variable abc not defined or is not associated in the task interface. (Process1:WebServiceTask:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_-Y1q4CBkEeOr79Nx_xXmEw", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:SendTask:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskCancelScript", //$NON-NLS-1$ 
                                "_1lnk4B67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : CancelScript::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:ReceiveTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskInitiateScript", //$NON-NLS-1$ 
                                "_1lnk4B67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : InitiateScript::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:ReceiveTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateStdLoopExpr", //$NON-NLS-1$ 
                                "_7LRl0R67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : Std Loop Expr::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:EmbeddedSubProcess)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateStdLoopExpr", //$NON-NLS-1$ 
                                "_7LRl0R67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : Std Loop Expr::At Line:2 column:1, The last statement must evaluate to Boolean (Process1:EmbeddedSubProcess)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateStdLoopExpr", //$NON-NLS-1$ 
                                "_7LRl0R67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : Std Loop Expr::At Line:2 column:1, Variable a not defined or is not associated in the task interface. (Process1:EmbeddedSubProcess)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateConditionalTransitionScript", //$NON-NLS-1$ 
                                "_7ZH0kx67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:9, Variable a not defined or is not associated in the task interface. (transitionInSubProc)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_9rrhUCEREeOYs6edSUrPuw", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:ReusableSubProcess:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "__2Tj4CBjEeOr79Nx_xXmEw", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable b not defined or is not associated in the task interface. (Process1:ReceiveTask:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskCompleteScript", //$NON-NLS-1$ 
                                "__FlUsB66EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : CompleteScript::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:Task)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskInitiateScript", //$NON-NLS-1$ 
                                "__FlUsB66EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : InitiateScript::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:Task)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_ABnmkCBlEeOr79Nx_xXmEw", //$NON-NLS-1$ 
                                "Process Manager 2.x : Unmapped User Defined Scripts are not supported (Process1:SendTask:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_ABnmkCBlEeOr79Nx_xXmEw", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:SendTask:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_Ayux0CESEeOYs6edSUrPuw", //$NON-NLS-1$ 
                                "Process Manager 2.x : Unmapped User Defined Scripts are not supported (Process1:ReusableSubProcess:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_Ayux0CESEeOYs6edSUrPuw", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:ReusableSubProcess:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_b0QHICEBEeOizdhQAlgM1w", //$NON-NLS-1$ 
                                "Process Manager 2.x : Unmapped User Defined Scripts are not supported (Process1:JavaServiceTask2:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_b0QHICEBEeOizdhQAlgM1w", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:JavaServiceTask2:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateTimerEventScript", //$NON-NLS-1$ 
                                "_BNltkB68EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : Timer Script::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:EmbeddedSubProcess:TimerEvent2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskCompleteScript", //$NON-NLS-1$ 
                                "_bZ--cB67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : CompleteScript::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:ScriptTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskTimoutScript", //$NON-NLS-1$ 
                                "_bZ--cB67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : TimeoutScript::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:ScriptTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_bZ--cB67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:ScriptTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_cj07YB67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:EmbeddedSubProcess:ScriptTask2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_CNqf4B9_EeOj_ML5qnrdmg", //$NON-NLS-1$ 
                                "Process Manager 2.x : Unmapped User Defined Scripts are not supported (Process1:WebServiceTask:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_CNqf4B9_EeOj_ML5qnrdmg", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:4, Variable abc not defined or is not associated in the task interface. (Process1:WebServiceTask:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_Co_v4CESEeOYs6edSUrPuw", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable b not defined or is not associated in the task interface. (Process1:ReusableSubProcess:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateMIAdditionalInstancesExpr", //$NON-NLS-1$ 
                                "_CrJaQB7IEeOTNrloxjaEgw", //$NON-NLS-1$ 
                                "Process Manager 2.x : MI Additional Instances Expr::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:Task2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateMIAdditionalInstancesExpr", //$NON-NLS-1$ 
                                "_CrJaQB7IEeOTNrloxjaEgw", //$NON-NLS-1$ 
                                "Process Manager 2.x : MI Additional Instances Expr::At Line:2 column:1, The last statement must evaluate to Number (Process1:Task2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateMIAdditionalInstancesExpr", //$NON-NLS-1$ 
                                "_CrJaQB7IEeOTNrloxjaEgw", //$NON-NLS-1$ 
                                "Process Manager 2.x : MI Additional Instances Expr::At Line:2 column:1, Variable a not defined or is not associated in the task interface. (Process1:Task2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateMIComplexExitExpr", //$NON-NLS-1$ 
                                "_CrJaQB7IEeOTNrloxjaEgw", //$NON-NLS-1$ 
                                "Process Manager 2.x : MI Complex Exit Expr::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:Task2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateMIComplexExitExpr", //$NON-NLS-1$ 
                                "_CrJaQB7IEeOTNrloxjaEgw", //$NON-NLS-1$ 
                                "Process Manager 2.x : MI Complex Exit Expr::At Line:2 column:1, The last statement must evaluate to number or to boolean (Process1:Task2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateMIComplexExitExpr", //$NON-NLS-1$ 
                                "_CrJaQB7IEeOTNrloxjaEgw", //$NON-NLS-1$ 
                                "Process Manager 2.x : MI Complex Exit Expr::At Line:2 column:1, Variable a not defined or is not associated in the task interface. (Process1:Task2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateMILoopExpr", //$NON-NLS-1$ 
                                "_CrJaQB7IEeOTNrloxjaEgw", //$NON-NLS-1$ 
                                "Process Manager 2.x : MI Loop Expr::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:Task2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateMILoopExpr", //$NON-NLS-1$ 
                                "_CrJaQB7IEeOTNrloxjaEgw", //$NON-NLS-1$ 
                                "Process Manager 2.x : MI Loop Expr::At Line:2 column:1, The last statement must evaluate to Number (Process1:Task2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateMILoopExpr", //$NON-NLS-1$ 
                                "_CrJaQB7IEeOTNrloxjaEgw", //$NON-NLS-1$ 
                                "Process Manager 2.x : MI Loop Expr::At Line:2 column:1, Variable a not defined or is not associated in the task interface. (Process1:Task2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_DtlZYCESEeOYs6edSUrPuw", //$NON-NLS-1$ 
                                "Process Manager 2.x : Unmapped User Defined Scripts are not supported (Process1:ReusableSubProcess:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_DtlZYCESEeOYs6edSUrPuw", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:ReusableSubProcess:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_Dw9VAyxsEeOaW7GpExN9nQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:9, Variable ParamPI2 not defined or is not associated in the task interface. (Process3:ScriptTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_EjMTQCE0EeOmtt4YyQdqBQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : Unmapped User Defined Scripts are not supported (Process1:ErrorEvent:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_EjMTQCE0EeOmtt4YyQdqBQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable b not defined or is not associated in the task interface. (Process1:ErrorEvent:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_gp2kICEBEeOizdhQAlgM1w", //$NON-NLS-1$ 
                                "Process Manager 2.x : Unmapped User Defined Scripts are not supported (Process1:ServiceTask3:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_gp2kICEBEeOizdhQAlgM1w", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:ServiceTask3:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_gRjtMCEzEeOmtt4YyQdqBQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : Unmapped User Defined Scripts are not supported (Process1:NewOperation:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_gRjtMCEzEeOmtt4YyQdqBQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable b not defined or is not associated in the task interface. (Process1:NewOperation:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskInitiateScript", //$NON-NLS-1$ 
                                "_hMB1gB9_EeOj_ML5qnrdmg", //$NON-NLS-1$ 
                                "Process Manager 2.x : InitiateScript::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:ManualTask2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "n2pe.manualTaskNotSupported", //$NON-NLS-1$ 
                                "_hMB1gB9_EeOj_ML5qnrdmg", //$NON-NLS-1$ 
                                "Process Manager 2.x : Manual Task is not supported. (Process1:ManualTask2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_iaEuUiEyEeOmtt4YyQdqBQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable b not defined or is not associated in the task interface. (Process1:CatchSignal1:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_iaEuUSEyEeOmtt4YyQdqBQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : Unmapped User Defined Scripts are not supported (Process1:CatchSignal1:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_iaEuUSEyEeOmtt4YyQdqBQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable b not defined or is not associated in the task interface. (Process1:CatchSignal1:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_jcIegCEBEeOizdhQAlgM1w", //$NON-NLS-1$ 
                                "Process Manager 2.x : Unmapped User Defined Scripts are not supported (Process1:ServiceTask3:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_jcIegCEBEeOizdhQAlgM1w", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable b not defined or is not associated in the task interface. (Process1:ServiceTask3:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateStdLoopExpr", //$NON-NLS-1$ 
                                "_jpsuwCBkEeOr79Nx_xXmEw", //$NON-NLS-1$ 
                                "Process Manager 2.x : Std Loop Expr::At Line:1 column:9, Variable a not defined or is not associated in the task interface. (Process1:Task3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateStdLoopExpr", //$NON-NLS-1$ 
                                "_jpsuwCBkEeOr79Nx_xXmEw", //$NON-NLS-1$ 
                                "Process Manager 2.x : Std Loop Expr::At Line:2 column:1, Variable a not defined or is not associated in the task interface. (Process1:Task3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_kFkxYCEzEeOmtt4YyQdqBQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : Unmapped User Defined Scripts are not supported (Process1:ReplyToNewOperation:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_kFkxYCEzEeOmtt4YyQdqBQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable b not defined or is not associated in the task interface. (Process1:ReplyToNewOperation:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_l8i6ECEzEeOmtt4YyQdqBQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable b not defined or is not associated in the task interface. (Process1:ReplyToNewOperation:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.rescheduleTimerScriptOnUserTaskOnly", //$NON-NLS-1$ 
                                "_LCD9cB9uEeO7WKqBJlJO6w", //$NON-NLS-1$ 
                                "Process Manager 2.x : Reschedule Timer Script is only supported for timer events on user-task boundary. (Process1:TimerEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateRescheduleTimerEventScript", //$NON-NLS-1$ 
                                "_LCD9cB9uEeO7WKqBJlJO6w", //$NON-NLS-1$ 
                                "Process Manager 2.x : Reschedule Timer Script::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:TimerEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateTimerEventScript", //$NON-NLS-1$ 
                                "_LCD9cB9uEeO7WKqBJlJO6w", //$NON-NLS-1$ 
                                "Process Manager 2.x : Timer Script::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:TimerEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_LI7KsB9_EeOj_ML5qnrdmg", //$NON-NLS-1$ 
                                "Process Manager 2.x : Unmapped User Defined Scripts are not supported (Process1:WebServiceTask:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_LI7KsB9_EeOj_ML5qnrdmg", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:WebServiceTask:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_lQ3ykCEBEeOizdhQAlgM1w", //$NON-NLS-1$ 
                                "Process Manager 2.x : Unmapped User Defined Scripts are not supported (Process1:JavaServiceTask2:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_lQ3ykCEBEeOizdhQAlgM1w", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:JavaServiceTask2:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_M2YH8CBkEeOr79Nx_xXmEw", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable b not defined or is not associated in the task interface. (Process1:CatchMessageEvent:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskInitiateScript", //$NON-NLS-1$ 
                                "_NuFUQB9tEeO7WKqBJlJO6w", //$NON-NLS-1$ 
                                "Process Manager 2.x : InitiateScript::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:EndEvent3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_pjqUAB9tEeO7WKqBJlJO6w", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:WebServiceTask:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskInitiateScript", //$NON-NLS-1$ 
                                "_QpcUcCBlEeOr79Nx_xXmEw", //$NON-NLS-1$ 
                                "Process Manager 2.x : InitiateScript::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:ThrowSignalEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_TK-RcCEzEeOmtt4YyQdqBQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable b not defined or is not associated in the task interface. (Process1:NewOperation:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_u7mZICHKEeOLa6Si9FiVrQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:JavaServiceTask2:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskCancelScript", //$NON-NLS-1$ 
                                "_uE8BsB67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : CancelScript::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:UserTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskInitiateScript", //$NON-NLS-1$ 
                                "_uE8BsB67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : InitiateScript::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:UserTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.error.userTaskCloseScript", //$NON-NLS-1$ 
                                "_uE8BsB67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : CloseScript::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:UserTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.error.userTaskOpenScript", //$NON-NLS-1$ 
                                "_uE8BsB67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : OpenScript::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:UserTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.error.userTaskRescheduleScript", //$NON-NLS-1$ 
                                "_uE8BsB67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : RescheduleScript::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:UserTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.error.userTaskScheduleScript", //$NON-NLS-1$ 
                                "_uE8BsB67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : ScheduleScript::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:UserTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.error.userTaskSubmitScript", //$NON-NLS-1$ 
                                "_uE8BsB67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : SubmitScript::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:UserTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskInitiateScript", //$NON-NLS-1$ 
                                "_uWv-sCEREeOYs6edSUrPuw", //$NON-NLS-1$ 
                                "Process Manager 2.x : InitiateScript::At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:ReusableSubProcess)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_VLrvgCE0EeOmtt4YyQdqBQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : Unmapped User Defined Scripts are not supported (Process1:CatchAll:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_VLrvgCE0EeOmtt4YyQdqBQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable b not defined or is not associated in the task interface. (Process1:CatchAll:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_yfQGECHKEeOLa6Si9FiVrQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable a not defined or is not associated in the task interface. (Process1:JavaServiceTask2:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_Ys3-UCE0EeOmtt4YyQdqBQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : Unmapped User Defined Scripts are not supported (Process1:CatchFault:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_Ys3-UCE0EeOmtt4YyQdqBQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable b not defined or is not associated in the task interface. (Process1:CatchFault:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskInitiateScript", //$NON-NLS-1$ 
                                "_z9-RoB67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : InitiateScript::At Line:1 column:4, Variable abc not defined or is not associated in the task interface. (Process1:WebServiceTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package1.xpdl", //$NON-NLS-1$ 
                                "bx.validateConditionalTransitionScript", //$NON-NLS-1$ 
                                "_z9-RoR67EeO8Ze-aFfTtOQ", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:2, Variable a not defined or is not associated in the task interface. (transitionX)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N225ExpressionValidationProblemsExistTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/ExpressionValidationRulesTest", "BpmProject/Process Packages{processes}/Package1.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
