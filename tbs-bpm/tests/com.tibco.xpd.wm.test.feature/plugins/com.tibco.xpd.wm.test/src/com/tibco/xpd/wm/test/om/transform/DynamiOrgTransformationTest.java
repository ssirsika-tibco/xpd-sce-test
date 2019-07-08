/**
 * 
 */
package com.tibco.xpd.wm.test.om.transform;

import com.tibco.n2.directory.model.de.AllocationMethod;
import com.tibco.n2.directory.model.de.ModelOrgUnit;
import com.tibco.n2.directory.model.de.ModelTemplate;
import com.tibco.n2.directory.model.de.ModelType;
import com.tibco.n2.directory.model.de.Position;
import com.tibco.xpd.om.core.om.OrgModel;

import junit.framework.Assert;

/**
 * Test to validate the transformed DynamicOrgModel and DynamicOrgUnit. This
 * Test checks for these in ModelTemplate and ModelOrgUnit <li>Total Number of
 * Positions</li> <li>Existence of particular Position</li> <li>Total Number Of
 * ModelOrgUnits</li> <li>Existence of particular ModelOrgUnit</li> <li>Total
 * number of Privilege Held</li> <li>
 * Existence of specific Privilege</li> <li>Allocation method</li> <li>Total
 * Number of System Actions</li> <li>existence of particular SystemAction</li>
 * <li>Association relationship between OrgUnits should not be transformed</li>
 * And additionally <li>Template Identifier [for ModelTemplate]</li> <li>Total
 * number of Model Templates in Model</li>
 * 
 * @author aprasad
 * 
 */
public class DynamiOrgTransformationTest extends AbstractDETransformationTest {

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.wm.test.om.transform.AbstractDETransformationTest#
     * validateTransformedModel()
     */
    @SuppressWarnings("nls")
    @Override
    protected void assertTransformedModel(ModelType deModel, OrgModel orgModel) {
        // Validate the ModelTemplates
        // MODEL TEMPLATE
        ModelTemplate modelTemplate =
                findModelTemplateByName(deModel, "HRDepartment", true);
        // Allocation Method
        assertEquals("Model Template - AllocationMethod does not match",
                AllocationMethod.NEXT,
                modelTemplate.getAllocMethod());
        // check Identifier attributes
        assertEquals("ModelTemplate - Number of Identifiers do not match ",
                4,
                modelTemplate.getInstanceIdAttribute().size());
        assertTrue("ModelTemplate - Identifier Missing", modelTemplate
                .getInstanceIdAttribute().contains("HRIdentifierName"));
        // Association should not be transformed
        assertNull("ModelOrgUnit - Association relationship should not be transformed",
                findModelOrgUnitByName(modelTemplate,
                        "RecruitmentSubUnit",
                        !RECURSE,
                        !ASSERT_EXISTS));

        // check contained the ModelOrgUnits and ModelPositions
        // check PrivilegeHeld
        assertEquals("ModelTemplate - Number of Privileges Held is wrong ",
                1,
                modelTemplate.getPrivilegeHeld().size());
        assertNotNull("ModelTemplate - Privilege is missing",
                getPrivilegeHoldingByName(modelTemplate.getPrivilegeHeld(),
                        "EnumPrivilege"));
        // check SystemActions
        assertEquals("ModelTemplate - Number of SystemAction  is wrong ",
                2,
                modelTemplate.getSystemAction().size());
        assertNotNull("ModelTemplate - SystemAction is missing",
                getSystemActionByName(modelTemplate.getSystemAction(),
                        "closeOtherResourcesItems"));
        // check ModelPosition
        assertEquals("ModelTemplate - Number of Positions is wrong ",
                2,
                modelTemplate.getModelPosition().size());
        assertNotNull("ModelTemplate - Position 'DepartmentHead' is Missing",
                getModelPositionByName(modelTemplate.getModelPosition(),
                        "DepartmentHead"));
        // Count ModelOrgUnit
        assertEquals("ModelTemplate - Number of ModelOrgUnits wrong ",
                3,
                modelTemplate.getModelOrgUnit().size());
        // Recruitment OrgUnit
        ModelOrgUnit recruitmentUnit =
                findModelOrgUnitByName(modelTemplate,
                        "Recruitment",
                        !RECURSE,
                        ASSERT_EXISTS);

        // MODEL ORG UNIT

        // Allocation Method
        assertEquals("ModelOrgUnit - AllocationMethod does not match",
                AllocationMethod.NEXT,
                recruitmentUnit.getAllocMethod());
        // Count Positions in Recruitment ModelOrgUnit
        assertEquals("ModelOrgUnit - Number of Positions is wrong ",
                4,
                recruitmentUnit.getModelPosition().size());
        Position managerPosition =
                getModelPositionByName(recruitmentUnit.getModelPosition(),
                        "Manager");
        assertNotNull("ModelOrgUnit - Model Position 'Manager' is Missing",
                managerPosition);

        // Count ModelOrgUnit
        assertEquals("ModelOrgUnit - Number of ModelOrgUnits wrong ",
                1,
                recruitmentUnit.getModelOrgUnit().size());

        findModelOrgUnitByName(recruitmentUnit,
                "RecruitmentSubUnit",
                !RECURSE,
                ASSERT_EXISTS);
        // PrivilegesHeld
        // check SystemActions
        assertEquals("ModelOrgUnit - Total Number of Priveleges  is wrong ",
                2,
                recruitmentUnit.getPrivilegeHeld().size());
        assertNotNull("ModelOrgUnit - Privilege is missing",
                getPrivilegeHoldingByName(recruitmentUnit.getPrivilegeHeld(),
                        "EnumPrivilege"));

        // check SystemActions
        assertEquals("ModelOrgUnit - Total Number of SystemAction  is wrong ",
                1,
                recruitmentUnit.getSystemAction().size());
        assertNotNull("ModelOrgUnit - SystemAction is missing",
                getSystemActionByName(recruitmentUnit.getSystemAction(),
                        "reallocateToOfferSet"));
        // Should not include ModelOrgUnits connected with
        // Association relationship
        assertNull("ModelOrgUnit - Association relationship should not be transformed",
                findModelOrgUnitByName(recruitmentUnit,
                        "EmployeeBenefits",
                        !RECURSE,
                        !ASSERT_EXISTS));

        // POSITION TEST
        // Allocation Method
        assertEquals("Position - AllocationMethod does not match",
                AllocationMethod.NEXT,
                managerPosition.getAllocMethod());

        // Tests Failing at the moment
        // check count of ModelTemplates
        Assert.assertEquals("Number of ModelTemplates wrong ", 2, deModel
                .getModelTemplate().size());
    }

}
