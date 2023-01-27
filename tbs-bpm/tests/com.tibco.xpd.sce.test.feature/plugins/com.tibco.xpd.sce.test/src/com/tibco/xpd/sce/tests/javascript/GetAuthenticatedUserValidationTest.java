/*
 * (c) 2004-2023 Cloud Software Group, Inc.
 */

package com.tibco.xpd.sce.tests.javascript;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Test script validations to prevent the use of bpm.process.getAuthenticatedUser() in business processes and
 * service-processes with process-runtime deploy target.
 *
 * @author aallway
 * @since Jan 2023
 */
public class GetAuthenticatedUserValidationTest extends AbstractN2BaseValidationTest {

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest#setUpBeforeBuild()
     *
     */
    @Override
    protected void setUpBeforeBuild() {
        super.setUpBeforeBuild();

        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("GetAuthenticatedUser"); //$NON-NLS-1$

        // and make sure we have a Forms special folder
        SpecialFolderUtil.getCreateSpecialFolderOfKind(project, "forms", "Forms"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Test script validations to prevent the use of bpm.process.getAuthenticatedUser() in business processes and
     * service-processes with process-runtime deploy target.
     * 
     * @throws Exception
     * 
     */
    public void testGetAuthenticatedUserValidations() throws Exception {
        doTestValidations();
    }

    @Override
    protected String getTestName() {
        return "GetAuthenticatedUserValidationTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        return new TestResourceInfo[] { new TestResourceInfo("resources/GetAuthenticatedUserTest", //$NON-NLS-1$
                "GetAuthenticatedUser/Process Packages{processes}/GetAuthenticatedUser.xpdl") //$NON-NLS-1$
        };
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        return new ValidationsTestProblemMarkerInfo[] {

                new ValidationsTestProblemMarkerInfo(
                        "/GetAuthenticatedUser/Process Packages/GetAuthenticatedUser.xpdl", //$NON-NLS-1$
                        "bx.error.taskInitiateScript", //$NON-NLS-1$
                        "_-lbk7J2VEe2Erf2zuSS83w", //$NON-NLS-1$
                        "BPM  : InitiateScript::At Line:1 column:48, The method getAuthenticatedUser is not available in Business processes. (GetAuthenticatedUserBusinessProcess:ScriptTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/GetAuthenticatedUser/Process Packages/GetAuthenticatedUser.xpdl", //$NON-NLS-1$
                        "bx.validateScriptTask", //$NON-NLS-1$
                        "_-lbk7J2VEe2Erf2zuSS83w", //$NON-NLS-1$
                        "BPM  : At Line:1 column:48, The method getAuthenticatedUser is not available in Business processes. (GetAuthenticatedUserBusinessProcess:ScriptTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/GetAuthenticatedUser/Process Packages/GetAuthenticatedUser.xpdl", //$NON-NLS-1$
                        "bx.error.taskCompleteScript", //$NON-NLS-1$
                        "_gJFhp52WEe2Erf2zuSS83w", //$NON-NLS-1$
                        "BPM  : CompleteScript::At Line:1 column:48, The method getAuthenticatedUser is not available in Service processes deployed to the Process-run-time. (GetAuthenticatedUserServiceProcessProcessAndPageflow:ScriptTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/GetAuthenticatedUser/Process Packages/GetAuthenticatedUser.xpdl", //$NON-NLS-1$
                        "bx.validateScriptTask", //$NON-NLS-1$
                        "_gJFhp52WEe2Erf2zuSS83w", //$NON-NLS-1$
                        "BPM  : At Line:1 column:48, The method getAuthenticatedUser is not available in Service processes deployed to the Process-run-time. (GetAuthenticatedUserServiceProcessProcessAndPageflow:ScriptTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/GetAuthenticatedUser/Process Packages/GetAuthenticatedUser.xpdl", //$NON-NLS-1$
                        "bx.error.taskCompleteScript", //$NON-NLS-1$
                        "_uMZEUp2WEe2Erf2zuSS83w", //$NON-NLS-1$
                        "BPM  : CompleteScript::At Line:1 column:48, The method getAuthenticatedUser is not available in Service processes deployed to the Process-run-time. (GetAuthenticatedUserServiceProcessProcessOnly:ScriptTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/GetAuthenticatedUser/Process Packages/GetAuthenticatedUser.xpdl", //$NON-NLS-1$
                        "bx.validateScriptTask", //$NON-NLS-1$
                        "_uMZEUp2WEe2Erf2zuSS83w", //$NON-NLS-1$
                        "BPM  : At Line:1 column:48, The method getAuthenticatedUser is not available in Service processes deployed to the Process-run-time. (GetAuthenticatedUserServiceProcessProcessOnly:ScriptTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

        };
    }
}
