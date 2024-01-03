/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.test.cases;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivityManager;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.om.test.AbstractOMTestCase;
import com.tibco.xpd.om.test.OMTestUtil;

/**
 * Tests the naming system and namespaces.
 * <p>
 * <i>Created: 31 Jan 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class OMNamespacesTest extends AbstractOMTestCase {

    private OrgModel model;

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.om.test.AbstractOMTestCase#setUp()
     */
    @SuppressWarnings("nls")
    @Override
    protected void setUp() throws Exception {
        super.setUp();

		/*
		 * Historic change broke this test (which doesn't appear to have run in infra for a long time. At some point we
		 * changed Label Provider for model elements to only show "Label" unless solutionDesign capability was
		 * activated, only then show "Label (name)"
		 * 
		 * So in order for testGetLabel() to work correctly, we need to enabled solutionDesign capability.
		 */
		IActivityManager activityManager = PlatformUI.getWorkbench().getActivitySupport().getActivityManager();
		Set<String> enabledActivityIds = activityManager.getEnabledActivityIds();
		Set activities = new HashSet<>(enabledActivityIds);
		activities.add("com.tibco.xpd.om.solutiondesign");
		PlatformUI.getWorkbench().getActivitySupport().setEnabledActivityIds(activities);

        model = factory.createOrgModel();
        model.setName("OrgModel");
        OMTestUtil.createResource(testResourceURI, Arrays.asList(model), null);
    }

    /**
     * Test for
     * {@link OMUtil#getQualifiedName(com.tibco.xpd.om.core.om.NamedElement)}.
     * 
     * @throws Exception
     */
    public void testGetQualifiedName() throws Exception {
        stack.execute(new RecordingCommand(ed) {
            @SuppressWarnings("nls")
            @Override
            protected void doExecute() {

                // all right
                Organization organization = factory.createOrganization();
                organization.setName("Organization");
                model.getOrganizations().add(organization);

                OrgUnit orgUnit = factory.createOrgUnit();
                orgUnit.setName("OrgUnit");
                organization.getUnits().add(orgUnit);

                Position position = factory.createPosition();
                position.setName("Position");
                orgUnit.getPositions().add(position);

                String qualifiedName = OMUtil.getQualifiedName(position);
                System.out.println("Pos qualified name: " + qualifiedName);

                String expectedQualifiedName =
                        "OrgModel_organizations::Organization_units::OrgUnit_positions::Position";

                assertEquals("Incorrect qualified name.",
                        expectedQualifiedName,
                        qualifiedName);

                // with null
                orgUnit.setName(null);
                String expectedQualifiedName2 =
                        "OrgModel_organizations::Organization_units::_positions::Position";
                String qualifiedName2 = OMUtil.getQualifiedName(position);
                System.out.println("Pos qualified name with null: "
                        + qualifiedName2);

                assertEquals("Incorrect qualified name.",
                        expectedQualifiedName2,
                        qualifiedName2);

            }
        },
                Collections.EMPTY_MAP);
    }

    public void testGetLabel() throws Exception {
        stack.execute(new RecordingCommand(ed) {
            @SuppressWarnings("nls")
            @Override
            protected void doExecute() { // not translated

                Organization organization = factory.createOrganization();
                organization.setDisplayName("Organization");
                model.getOrganizations().add(organization);

                OrgUnit orgUnit = factory.createOrgUnit();
                orgUnit.setDisplayName("OrgUnit");
                organization.getUnits().add(orgUnit);

                Position position = factory.createPosition();
                position.setDisplayName("Position");
				position.setName(position.getName() + "XYZ");
                orgUnit.getPositions().add(position);

                String positionLabel = OMUtil.getLabel(position);
                System.out.println("Pos label: " + positionLabel);

				String expectedPositionLabel = "Position (PositionXYZ)";

                assertEquals("Incorrect label.",
                        expectedPositionLabel,
                        positionLabel);

                Resource resource = model.eResource();
                Properties generatedLabels = OMUtil.generateLabels(resource);
                System.out.println("Lables:\n");
                generatedLabels.list(System.out);

            }
        }, Collections.EMPTY_MAP);
    }
}
