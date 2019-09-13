/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.sce.tests.importmigration;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * <p>
 * CaseSignalMigrationTest
 * 
 * Test that bpm.caseSignal (after migration) is not allowed on anything outside of a case signal event handler / event
 * sub-process flow
 * 
 * Test that when used in an event handler / event sub-process flow then all the bpm.caseSignal.xxxxx functions are ok
 * (no problem markers means that they were migrated OK and that the new bpm.caseSignal script contributions are working
 * fine.
 * </p>
 *
 * @author aallway
 * @since 13th Sept 2019
 */
public class CaseSignalMigrationTest extends AbstractN2BaseValidationTest {

    private ProjectImporter projectImporter;

    public CaseSignalMigrationTest() {
		super(true);
	}

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#customTestResourceSetup()
     *
     */
    @Override
    protected void customTestResourceSetup() {
        /*
         * This test doesn't create files via the normal getTestResources() because we want to import and migrate a
         * whole project from AMX BPM.
         */
        projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test", //$NON-NLS-1$
                new String[] { "resources/ScriptMigrationTests/case-signal/CaseSignalData/", //$NON-NLS-1$
                        "resources/ScriptMigrationTests/case-signal/CaseSignalProcess/" }, //$NON-NLS-1$
                new String[] { "CaseSignalData", //$NON-NLS-1$
                        "CaseSignalProcess" }); //$NON-NLS-1$

        assertTrue("Failed to load projects from \"\"ScriptMigrationTests/case-signal/", //$NON-NLS-1$
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
        if (projectImporter != null) {
            // projectImporter.performDelete();
        }
        super.tearDown();
    }

	/**
     * ServiceProcessTimerEventValidationTest
     * 
     * @throws Exception
     */
    public void testServiceProcessTimerEventValidationTest() throws Exception {
        doTestValidations();

        /* The xpdl with the same timer events in a business process should not have errors. */
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("CaseSignalProcess"); //$NON-NLS-1$
        
        IFile bizProcFile =
                project.getFile(new Path("Process Packages").append("Case Signal Process.xpdl")); //$NON-NLS-1$//$NON-NLS-2$
        
        assertTrue(
                "CaseSignalProcess/Process Packages/Case Signal Process.xpdl should exist", //$NON-NLS-1$
                bizProcFile.exists());

        assertFalse(
                "CaseSignalProcess/Process Packages/Case Signal Process.xpdl should have no error problem markers.", //$NON-NLS-1$
                TestUtil.hasErrorProblemMarker(bizProcFile, true, "testServiceProcessTimerEventValidationTest")); //$NON-NLS-1$

	}

	@Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[] { 
			
                new ValidationsTestProblemMarkerInfo("/CaseSignalProcess/Process Packages/CaseSignalScriptInvalid.xpdl", //$NON-NLS-1$
                        "bx.error.taskCompleteScript", //$NON-NLS-1$
                        "_f-BEsNYHEemom_3l2K1D9Q", //$NON-NLS-1$
                        "BPM  : CompleteScript::At Line:2 column:61, Property caseSignal is invalid for the current context (CaseSignalScriptInvalidProcess:CatchLocalEventhandler)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo("/CaseSignalProcess/Process Packages/CaseSignalScriptInvalid.xpdl", //$NON-NLS-1$
                        "bx.validateConditionalTransitionScript", //$NON-NLS-1$
                        "_GGpoo9YIEemom_3l2K1D9Q", //$NON-NLS-1$
                        "BPM  : At Line:1 column:35, Property caseSignal is invalid for the current context (unnamed -> EndEvent4)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo("/CaseSignalProcess/Process Packages/CaseSignalScriptInvalid.xpdl", //$NON-NLS-1$
                        "bx.validateScriptTask", //$NON-NLS-1$
                        "_gwXustYHEemom_3l2K1D9Q", //$NON-NLS-1$
                        "BPM  : At Line:2 column:61, Property caseSignal is invalid for the current context (CaseSignalScriptInvalidProcess:ScriptTask2)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo("/CaseSignalProcess/Process Packages/CaseSignalScriptInvalid.xpdl", //$NON-NLS-1$
                        "bx.validateConditionalTransitionScript", //$NON-NLS-1$
                        "_lh91cNYHEemom_3l2K1D9Q", //$NON-NLS-1$
                        "BPM  : At Line:1 column:35, Property caseSignal is invalid for the current context (unnamed -> ThrowSignal1)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo("/CaseSignalProcess/Process Packages/CaseSignalScriptInvalid.xpdl", //$NON-NLS-1$
                        "bx.validateScriptTask", //$NON-NLS-1$
                        "_LqKxAdYHEemom_3l2K1D9Q", //$NON-NLS-1$
                        "BPM  : At Line:2 column:61, Property caseSignal is invalid for the current context (CaseSignalScriptInvalidProcess:ScriptTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo("/CaseSignalProcess/Process Packages/CaseSignalScriptInvalid.xpdl", //$NON-NLS-1$
                        "bx.validateConditionalTransitionScript", //$NON-NLS-1$
                        "_Lv-opNYIEemom_3l2K1D9Q", //$NON-NLS-1$
                        "BPM  : At Line:1 column:35, Property caseSignal is invalid for the current context (unnamed -> unnamed)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo("/CaseSignalProcess/Process Packages/CaseSignalScriptInvalid.xpdl", //$NON-NLS-1$
                        "bx.error.taskCompleteScript", //$NON-NLS-1$
                        "_uQnnoNYHEemom_3l2K1D9Q", //$NON-NLS-1$
                        "BPM  : CompleteScript::At Line:2 column:61, Property caseSignal is invalid for the current context (CaseSignalScriptInvalidProcess:EventSubProcess:CatchLocalEventSubproc)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo("/CaseSignalProcess/Process Packages/CaseSignalScriptInvalid.xpdl", //$NON-NLS-1$
                        "bx.validateScriptTask", //$NON-NLS-1$
                        "_wRGRotYHEemom_3l2K1D9Q", //$NON-NLS-1$
                        "BPM  : At Line:2 column:61, Property caseSignal is invalid for the current context (CaseSignalScriptInvalidProcess:EventSubProcess:ScriptTask3)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

        };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "ServiceProcessTimerEventValidationTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        return new TestResourceInfo[0];
    }

}
