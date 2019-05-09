/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.validation;

import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * Tests for terminal state validation.
 *
 * @author nwilson
 * @since 7 May 2019
 */
public class TerminalStateValidationTest extends AbstractN2BaseValidationTest {

    public TerminalStateValidationTest() {
        super(true);
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest#setUpBeforeBuild()
     *
     */
    @Override
    protected void setUpBeforeBuild() {
        super.setUpBeforeBuild();

        /*
         * make sure BOM project is configured as Business Data project to avoid
         * getting unexpected problem markers unrelated to this test.
         */
        BOMUtils.setAsBusinessDataProject(ResourcesPlugin.getWorkspace()
                .getRoot().getProject("ProjectTerminalStateValidationTest")); //$NON-NLS-1$

    }

    public void testAceBomMigrationValidationsTest() throws Exception {
        doTestValidations();
    }

    /**
     * @see com.tibco.xpd.core.test.validations.AbstractBaseValidationTest#getValidationProblemMarkerInfos()
     *
     * @return
     */
    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        return new ValidationsTestProblemMarkerInfo[] {

                new ValidationsTestProblemMarkerInfo(
                        "/ProjectTerminalStateValidationTest/Business Objects/terminalStates.bom", //$NON-NLS-1$
                        "casestate.no.terminal.states.issue", //$NON-NLS-1$
                        "_iepSEHC0Eem3Q-NuNCuNWw", //$NON-NLS-1$
                        "BPM  : A Case State must have at least one Terminal State set (state (com.example.terminalstates))", //$NON-NLS-1$
                        ""), //$NON-NLS-1$
                new ValidationsTestProblemMarkerInfo(
                        "/ProjectTerminalStateValidationTest/Business Objects/terminalStates.bom", //$NON-NLS-1$
                        "casestate.no.non.terminal.states.issue", //$NON-NLS-1$
                        "_jWHFkHC0Eem3Q-NuNCuNWw", //$NON-NLS-1$
                        "BPM  : A Case State must have at least one Non-Terminal State set (state (com.example.terminalstates))", //$NON-NLS-1$
                        ""), //$NON-NLS-1$
                new ValidationsTestProblemMarkerInfo(
                        "/ProjectTerminalStateValidationTest/Business Objects/terminalStates.bom", //$NON-NLS-1$
                        "casestate.not.enough.states.issue", //$NON-NLS-1$
                        "_hrn5sHC0Eem3Q-NuNCuNWw", //$NON-NLS-1$
                        "BPM  : A Case State must have at least two States defined (state (com.example.terminalstates))", //$NON-NLS-1$
                        "") //$NON-NLS-1$
        };
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestName()
     *
     * @return
     */
    @Override
    protected String getTestName() {
        return getClass().getSimpleName();
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestResources()
     *
     * @return
     */
    @Override
    protected TestResourceInfo[] getTestResources() {
        return new TestResourceInfo[] { new TestResourceInfo(
                "resources/TerminalStateValidationTest", //$NON-NLS-1$
                "ProjectTerminalStateValidationTest/Business Objects{bom}/terminalStates.bom") //$NON-NLS-1$
        };
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestPlugInId()
     *
     * @return
     */
    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
    }

}
