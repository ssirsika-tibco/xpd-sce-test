/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.sce.tests.validation;

import java.util.Arrays;
import java.util.Collections;

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
 * AceCompositeCaseIdTest
 * <p>
 * AceCompositeCaseIdTest - Test selected validations are correctly raised.
 * 
 * Sid ACE-8370 Introduced Composite Case Id support validations tester, ALSO it is based on import from a v4.x project
 * in order to ensure that composite case-ids aren't removed etc during migration.
 * </p>
 *
 * @author Sid
 * @since July 2024
 */
@SuppressWarnings("nls")
public class AceCompositeCaseIdTest extends AbstractN2BaseValidationTest {

	public AceCompositeCaseIdTest() {
		super(true);
	}

	/**
     * AceCompositeCaseIdTest
     * 
     * @throws Exception
     */
    public void testAceCompositeCaseIdTest() throws Exception {
		ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
				new String[]{"resources/AceCompositeCaseIdTest/v433CaseDataCompositeCaseIds/"},
				new String[]{"v433CaseDataCompositeCaseIds"});
		try
		{
			buildAndWait();

			/* Check that correct validations exist. */
			doTestValidations();

			/* Make sure no problems in the valid file... */
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("v433CaseDataCompositeCaseIds");
			IFile validFile = project.getFile(new Path("Business Objects/ValidInV5.bom"));
			
			assertFalse(
					"Valid composite case id file should only have 'case state must have terminal state' problems, but also has...\n"
							+ TestUtil.getErrorProblemMarkerList(validFile, true),
					TestUtil.hasErrorProblemMarker(validFile, true,
							Collections.singletonList("casestate.no.terminal.states.issue"),
							getTestName()));

			IFile invalidFile = project.getFile(new Path("Business Objects/InValidInV5.bom"));

			assertFalse(
					"Valid composite case id file should only have 'case state must have terminal state' problems, but also has...\n"
							+ TestUtil.getErrorProblemMarkerList(validFile, true),
					TestUtil.hasErrorProblemMarker(invalidFile, true, Arrays.asList(
							new String[]{"ace.bom.case.must.have.caseid", "casestate.no.terminal.states.issue"}),
							getTestName()));

		}
		finally
		{
			assertTrue(projectImporter.performDelete());
		}
	}

	@Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[] { 
			
			    new ValidationsTestProblemMarkerInfo(
						"/v433CaseDataCompositeCaseIds/Business Objects/InValidInV5.bom", //$NON-NLS-1$
			    		"ace.bom.case.must.have.caseid", //$NON-NLS-1$ 
			    		"_0_18cD7BEe-JkqHQSqsJqA", //$NON-NLS-1$ 
			    		"BPM  : Case classes must have either a single non-composite identifier or multiple composite case identifiers (NoCaseId (invalidInV5))", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
						"/v433CaseDataCompositeCaseIds/Business Objects/InValidInV5.bom", //$NON-NLS-1$
			    		"casestate.no.terminal.states.issue", //$NON-NLS-1$ 
			    		"_2CDAVj7BEe-JkqHQSqsJqA", //$NON-NLS-1$ 
			    		"BPM  : A Case State must have at least one Terminal State set (caseState (invalidInV5))", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
						"/v433CaseDataCompositeCaseIds/Business Objects/InValidInV5.bom", //$NON-NLS-1$
			    		"ace.bom.case.must.have.caseid", //$NON-NLS-1$ 
			    		"_YiTP_T6_Ee-JkqHQSqsJqA", //$NON-NLS-1$ 
			    		"BPM  : Case classes must have either a single non-composite identifier or multiple composite case identifiers (MultiTextManualCaseId (invalidInV5))", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
						"/v433CaseDataCompositeCaseIds/Business Objects/InValidInV5.bom", //$NON-NLS-1$
			    		"casestate.no.terminal.states.issue", //$NON-NLS-1$ 
			    		"_YiTQBD6_Ee-JkqHQSqsJqA", //$NON-NLS-1$ 
			    		"BPM  : A Case State must have at least one Terminal State set (caseState (invalidInV5))", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
						"/v433CaseDataCompositeCaseIds/Business Objects/InValidInV5.bom", //$NON-NLS-1$
			    		"ace.bom.case.must.have.caseid", //$NON-NLS-1$ 
			    		"_YiTQBz6_Ee-JkqHQSqsJqA", //$NON-NLS-1$ 
			    		"BPM  : Case classes must have either a single non-composite identifier or multiple composite case identifiers (CompositeAndManualCaseId (invalidInV5))", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
						"/v433CaseDataCompositeCaseIds/Business Objects/InValidInV5.bom", //$NON-NLS-1$
			    		"casestate.no.terminal.states.issue", //$NON-NLS-1$ 
			    		"_YiTQFD6_Ee-JkqHQSqsJqA", //$NON-NLS-1$ 
			    		"BPM  : A Case State must have at least one Terminal State set (caseState (invalidInV5))", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			                
        };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "AceCompositeCaseIdTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
		return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
        };
    
        return testResources;
    }

}
