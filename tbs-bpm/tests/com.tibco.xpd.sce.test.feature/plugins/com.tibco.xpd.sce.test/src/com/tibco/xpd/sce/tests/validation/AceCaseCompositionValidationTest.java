/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.sce.tests.validation;

import java.util.List;

import org.eclipse.core.resources.IFile;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * CaseClassCompositionValidationTest
 * 
 * Check validation that Case class cannot be used as a composition from any
 * other property or class. And that it isn't raised for valid use cases.
 *
 * @author aallway
 * @since 16 Jun 2019
 */
public class AceCaseCompositionValidationTest extends AbstractN2BaseValidationTest {

	public AceCaseCompositionValidationTest() {
		super(true);
	}

	/**
     * CaseClassCompositionValidationTest
     * 
     * @throws Exception
     */
    public void testCaseClassCompositionValidationTest() throws Exception {
		doTestValidations();        

        /*
         * Check that the BOM that has valid relationships between classes and
         * case classes etc has no problem markers.
         */
        IFile noErrorsBom = getTestFile("NoErrors.bom"); //$NON-NLS-1$

        assertTrue("NoErrors.bom should exist.",
                noErrorsBom != null && noErrorsBom.isAccessible());

        List<ValidationsTestProblemMarkerInfo> problemMarkers =
                getProblemMarkers(noErrorsBom);

        assertTrue(
                "NoErrors.bom should have no problem markers (has at least one)",
                problemMarkers.size() == 0);

        return;
	}

	@Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[] { 
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/CaseClassCompositionValidationTest/Business Objects/WithErrors.bom", //$NON-NLS-1$ 
			    		"ace.bom.class.property.is.case.class", //$NON-NLS-1$ 
			    		"_-45W8I0VEemFSJi5ncrI2A", //$NON-NLS-1$ 
			    		"BPM  : You cannot use properties and compositions of Case Class type (you can only use association between case types). (badCaseAttribute (com.example.caseclasscompositionvalidationtest))", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/CaseClassCompositionValidationTest/Business Objects/WithErrors.bom", //$NON-NLS-1$ 
			    		"ace.bom.class.property.is.case.class", //$NON-NLS-1$ 
			    		"_97Nmso0VEemFSJi5ncrI2A", //$NON-NLS-1$ 
			    		"BPM  : You cannot use properties and compositions of Case Class type (you can only use association between case types). (BAD Case1 (com.example.caseclasscompositionvalidationtest))", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/CaseClassCompositionValidationTest/Business Objects/WithErrors.bom", //$NON-NLS-1$ 
			    		"ace.bom.class.property.is.case.class", //$NON-NLS-1$ 
			    		"_T5OWII0WEemFSJi5ncrI2A", //$NON-NLS-1$ 
			    		"BPM  : You cannot use properties and compositions of Case Class type (you can only use association between case types). (badCaseToCaseProperty (com.example.caseclasscompositionvalidationtest))", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			                
        };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "AceCaseCompositionValidationTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
            new TestResourceInfo("resources/CaseClassCompositionValidationTest", "CaseClassCompositionValidationTest/Business Objects{bom}/NoErrors.bom"), //$NON-NLS-1$ //$NON-NLS-2$
            new TestResourceInfo("resources/CaseClassCompositionValidationTest", "CaseClassCompositionValidationTest/Business Objects{bom}/WithErrors.bom"), //$NON-NLS-1$ //$NON-NLS-2$
        };
    
        return testResources;
    }

}
