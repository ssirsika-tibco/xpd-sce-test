/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;

/**
 * JUnit test to make sure that script validations work fine for
 * CaseSignalAttributes class.
 * 
 * @author sajain
 * @since Apr 17, 2015
 */
public class N2_48_CaseDataSignalScriptTest extends AbstractBaseValidationTest {

    public N2_48_CaseDataSignalScriptTest() {
        super(true);
    }

    /**
     * N2_48_CaseDataSignalScriptTest
     * 
     * @throws Exception
     */
    public void testN2_48_CaseDataSignalScriptTest() throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_48_CaseDataSignalScriptTestProj/Process Packages/N2_48_CaseDataSignalScriptTestProj.xpdl", //$NON-NLS-1$ 
                                "bx.caseDataSigEventsMustSpecifyCaseRefData", //$NON-NLS-1$ 
                                "_4f6RIOToEeSHdpKeQUMm5Q", //$NON-NLS-1$ 
                                "Process Manager  : Case Data Signal Events must specify a case reference field / parameter. (N2_48_CaseDataSignalScriptTestProjProcess:CatchSignalEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_48_CaseDataSignalScriptTestProj/Process Packages/N2_48_CaseDataSignalScriptTestProj.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskCompleteScript", //$NON-NLS-1$ 
                                "_4f6RIOToEeSHdpKeQUMm5Q", //$NON-NLS-1$ 
                                "Process Manager  : CompleteScript::At Line:1 column:59, Method getChangedAssociationNames is invalid for the current context (N2_48_CaseDataSignalScriptTestProjProcess:CatchSignalEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_48_CaseDataSignalScriptTestProj/Process Packages/N2_48_CaseDataSignalScriptTestProj.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskCompleteScript", //$NON-NLS-1$ 
                                "_4f6RIOToEeSHdpKeQUMm5Q", //$NON-NLS-1$ 
                                "Process Manager  : CompleteScript::At Line:10 column:44, Assignment of Integer from Boolean is not supported (N2_48_CaseDataSignalScriptTestProjProcess:CatchSignalEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_48_CaseDataSignalScriptTestProj/Process Packages/N2_48_CaseDataSignalScriptTestProj.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskCompleteScript", //$NON-NLS-1$ 
                                "_4f6RIOToEeSHdpKeQUMm5Q", //$NON-NLS-1$ 
                                "Process Manager  : CompleteScript::At Line:3 column:54, The left-hand side of an assignment must be a variable (N2_48_CaseDataSignalScriptTestProjProcess:CatchSignalEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_48_CaseDataSignalScriptTestProj/Process Packages/N2_48_CaseDataSignalScriptTestProj.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskCompleteScript", //$NON-NLS-1$ 
                                "_4f6RIOToEeSHdpKeQUMm5Q", //$NON-NLS-1$ 
                                "Process Manager  : CompleteScript::At Line:4 column:46, Assignment of Boolean from Integer is not supported (N2_48_CaseDataSignalScriptTestProjProcess:CatchSignalEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_48_CaseDataSignalScriptTestProj/Process Packages/N2_48_CaseDataSignalScriptTestProj.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskCompleteScript", //$NON-NLS-1$ 
                                "_4f6RIOToEeSHdpKeQUMm5Q", //$NON-NLS-1$ 
                                "Process Manager  : CompleteScript::At Line:4 column:46, The left-hand side of an assignment must be a variable (N2_48_CaseDataSignalScriptTestProjProcess:CatchSignalEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_48_CaseDataSignalScriptTestProj/Process Packages/N2_48_CaseDataSignalScriptTestProj.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskCompleteScript", //$NON-NLS-1$ 
                                "_4f6RIOToEeSHdpKeQUMm5Q", //$NON-NLS-1$ 
                                "Process Manager  : CompleteScript::At Line:5 column:41, Assignment of Boolean from Integer is not supported (N2_48_CaseDataSignalScriptTestProjProcess:CatchSignalEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_48_CaseDataSignalScriptTestProj/Process Packages/N2_48_CaseDataSignalScriptTestProj.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskCompleteScript", //$NON-NLS-1$ 
                                "_4f6RIOToEeSHdpKeQUMm5Q", //$NON-NLS-1$ 
                                "Process Manager  : CompleteScript::At Line:5 column:41, The left-hand side of an assignment must be a variable (N2_48_CaseDataSignalScriptTestProjProcess:CatchSignalEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_48_CaseDataSignalScriptTestProj/Process Packages/N2_48_CaseDataSignalScriptTestProj.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskCompleteScript", //$NON-NLS-1$ 
                                "_4f6RIOToEeSHdpKeQUMm5Q", //$NON-NLS-1$ 
                                "Process Manager  : CompleteScript::At Line:6 column:41, Assignment of Boolean from Integer is not supported (N2_48_CaseDataSignalScriptTestProjProcess:CatchSignalEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_48_CaseDataSignalScriptTestProj/Process Packages/N2_48_CaseDataSignalScriptTestProj.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskCompleteScript", //$NON-NLS-1$ 
                                "_4f6RIOToEeSHdpKeQUMm5Q", //$NON-NLS-1$ 
                                "Process Manager  : CompleteScript::At Line:6 column:41, The left-hand side of an assignment must be a variable (N2_48_CaseDataSignalScriptTestProjProcess:CatchSignalEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_48_CaseDataSignalScriptTestProj/Process Packages/N2_48_CaseDataSignalScriptTestProj.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskCompleteScript", //$NON-NLS-1$ 
                                "_4f6RIOToEeSHdpKeQUMm5Q", //$NON-NLS-1$ 
                                "Process Manager  : CompleteScript::At Line:7 column:44, Assignment of Boolean from Integer is not supported (N2_48_CaseDataSignalScriptTestProjProcess:CatchSignalEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_48_CaseDataSignalScriptTestProj/Process Packages/N2_48_CaseDataSignalScriptTestProj.xpdl", //$NON-NLS-1$ 
                                "bx.error.taskCompleteScript", //$NON-NLS-1$ 
                                "_4f6RIOToEeSHdpKeQUMm5Q", //$NON-NLS-1$ 
                                "Process Manager  : CompleteScript::At Line:7 column:44, The left-hand side of an assignment must be a variable (N2_48_CaseDataSignalScriptTestProjProcess:CatchSignalEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_48_CaseDataSignalScriptTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/N248CaseDataSignalScriptTest", "N2_48_CaseDataSignalScriptTestProj/Process Packages{processes}/N2_48_CaseDataSignalScriptTestProj.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
