/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.sce.tests.validation;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * AceSearchableArrayResolutionTest
 * <p>
 * AceSearchableArrayResolutionTest - Test selected validations are correctly raised.
 * </p>
 * <p>
 * Generated by: createBaseTest.javajet
 * </p>
 *
 * 
 *
 * @author
 * @since
 */
@SuppressWarnings("nls")
public class AceSearchableArrayResolutionTest extends AbstractBaseValidationTest {

	public AceSearchableArrayResolutionTest() {
		super(true);
	}

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest#setUp()
     */
    @Override
    protected void setUp() throws Exception {
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
    }

    /**
     * SearchSummaryValidationTest
     * 
     * @throws Exception
     */
    public void testAceSearchableArrayResolutionTest() throws Exception {
        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
                new String[] { "resources/AceSearchableArrayResolutionTest/simple-data.zip" },
                new String[] { "simple-data" });
        try {
            buildAndWait();

            doTestValidations();
        } finally {
            assertTrue(projectImporter.performDelete());
        }
        return;
    }

	@Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[] { 
			
			    new ValidationsTestProblemMarkerInfo(
                        "/simple-data/Business Objects/simpledata.bom", "ace.bom.searchable.must.be.nonarray",
                        "_GKsrMLm5Eem9OKUbUb_5OA",
                        "BPM  : Searchable attributes cannot be arrays (attribute1 (com.example.simpledata))",
                        "Make attribute non-searchable"),
			    		
			
			    new ValidationsTestProblemMarkerInfo(
                        "/simple-data/Business Objects/simpledata.bom", "ace.bom.searchable.must.be.nonarray",
                        "_GMdlwLm5Eem9OKUbUb_5OA",
                        "BPM  : Searchable attributes cannot be arrays (attribute2 (com.example.simpledata))",
                        "Make attribute non-searchable"),
			    		
			                
        };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "AceSearchableArrayResolutionTest";
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test";
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
                new TestResourceInfo("resources/AceSearchableArrayResolutionTest",
                        "simple-data/Business Objects{bom}/simpledata.bom"),
                new TestResourceInfo("resources/AceSearchableArrayResolutionTest",
                        "simple-data/Business Objects{bom}/simpledata.bom"),
        };
    
        return testResources;
    }

}
