/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.wm.test.om.transform;

import com.tibco.n2.directory.model.de.ModelOrgUnit;
import com.tibco.n2.directory.model.de.ModelTemplate;
import com.tibco.n2.directory.model.de.ModelType;
import com.tibco.n2.directory.model.de.OrgUnit;
import com.tibco.n2.directory.model.de.Organization;
import com.tibco.xpd.om.core.om.OrgModel;

/**
 * Validates the ModelTemapletOrgUnit and OrgUnit Hierarchy. checks done <li>
 * ModelTemaplateOrgUnit Hierarchy contains expected elements</li> <li>OrgUnit
 * Hierarchy contains expected elements</li> <li>Association relationships are
 * not included in the hierarchy</li> <li>Dynamic OrgUnit is transformed as
 * ModelTemplateRef</li> <li>Dynamic OrgUnit is NOT transformed as OrgUnit</li>
 * 
 * @author aprasad
 * @since 25-Oct-2013
 */
public class NestedOrgUnitTest extends AbstractDETransformationTest {

    /**
     * @see com.tibco.xpd.wm.test.om.transform.AbstractDETransformationTest#validateTransformedModel()
     * 
     */
    @Override
    protected void assertTransformedModel(ModelType deModel, OrgModel orgModel) {
        // Test OrgUnit Hierarchy
        validateOrgUnitHeirarchy(deModel);
        // Test ModelOrgUnitHierarchy
        validateModelOrgUnitHeirarchy(deModel);

        assertModelTemplateReferences(deModel);
    }

    /**
     * Validates the OrgUnit Hierarchy in the DE model.
     */
    private void validateOrgUnitHeirarchy(ModelType deModel) {
        Organization organization =
                findOrganizationByName(deModel, "Organization1", ASSERT_EXISTS); //$NON-NLS-1$
        validateOrgUnit("ParentOrganizationUnit", //$NON-NLS-1$
                organization,
                new String[] { "OrgUnit_US", "OrgUnit_ASIA", "OrgUnit_EMEA" }, //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
                new String[] { "Finance", "EMEA_HR" }); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
        // Check a DynamiOrgUnit "SalesDept" 1. should be transformed as
        // ModelTemplate and 2. not OrgUnit
        // 1. should be transformed as ModelTemplate get OrgUnit
        OrgUnit parentOrgUnit =
                findOrgUnitByName(organization,
                        "ParentOrganizationUnit", !RECURSE, ASSERT_EXISTS); //$NON-NLS-1$
        // getModelTemplate
        ModelTemplate modelTemplateRef = parentOrgUnit.getModelTemplateRef();
        assertNotNull("ModelTemplateRef is missing for ParentOrganizationUnit ", //$NON-NLS-1$
                modelTemplateRef);
        String dynamicOrgUnitName = "SalesDept";//$NON-NLS-1$
        assertEquals("ModelTemaplateRef in ParentOrganizationUnit is not correct", //$NON-NLS-1$
                dynamicOrgUnitName,
                modelTemplateRef.getName());

        // 2. not OrgUnit
        assertNull("DynamicOrgUnit in ParentOrganizationUnit should not be transformed as OrgUnit ", //$NON-NLS-1$
                findOrgUnitByName(organization,
                        dynamicOrgUnitName,
                        RECURSE,
                        !ASSERT_EXISTS));
    }

    private void validateOrgUnit(String parentOrgUnitName,
            Organization organization, String[] orgUnitShouldExist,
            String[] orgUnitShouldNotExist) {
        OrgUnit orgUnit =
                findOrgUnitByName(organization,
                        parentOrgUnitName,
                        !RECURSE,
                        ASSERT_EXISTS);
        // Confirm existence of hierarchical OrgUnit relations
        for (String subOrgUnitName : orgUnitShouldExist) {
            findOrgUnitByName(orgUnit, subOrgUnitName, !RECURSE, ASSERT_EXISTS);
        }

        // Confirm absence of Transformed Association relations
        for (String subOrgUnitName : orgUnitShouldNotExist) {
            assertNull(String.format("'%s' should not be a direct child of '%s',", subOrgUnitName, orgUnit.getName()), //$NON-NLS-1$
                    findOrgUnitByName(orgUnit,
                            subOrgUnitName,
                            !RECURSE,
                            !ASSERT_EXISTS));
        }
    }

    /**
     * Validates the ModelOrgUnit hierarchy in the DE model.
     */
    private void validateModelOrgUnitHeirarchy(ModelType deModel) {
        ModelTemplate modelTemplate =
                findModelTemplateByName(deModel, "HRDepartment", ASSERT_EXISTS); //$NON-NLS-1$
        validateModelOrgUnit("Recruitment", //$NON-NLS-1$
                modelTemplate,
                new String[] { "RecruitmentSubUnit" }, //$NON-NLS-1$
                new String[] { "EmployeeBenefits", "TrainingandDevelopment" }); //$NON-NLS-1$//$NON-NLS-2$
    }

    private void validateModelOrgUnit(String parentModelOrgUnitName,
            ModelTemplate modelTemplate, String[] mOrgUnitShouldExist,
            String[] mOrgUnitShouldNotExist) {
        ModelOrgUnit modelOrgUnit =
                findModelOrgUnitByName(modelTemplate,
                        parentModelOrgUnitName,
                        !RECURSE,
                        ASSERT_EXISTS);
        // Confirm existence of hierarchical OrgUnit relations
        for (String subMOrgUnitName : mOrgUnitShouldExist) {
            findModelOrgUnitByName(modelOrgUnit,
                    subMOrgUnitName,
                    !RECURSE,
                    ASSERT_EXISTS);
        }

        // Confirm absence of Transformed Association relations
        for (String subMOrgUnitName : mOrgUnitShouldNotExist) {
            assertNull(String.format("'%s' should not be a direct child of '%s',", subMOrgUnitName, modelOrgUnit.getName()), //$NON-NLS-1$
                    findModelOrgUnitByName(modelOrgUnit,
                            subMOrgUnitName,
                            !RECURSE,
                            !ASSERT_EXISTS));

        }
    }

    @SuppressWarnings("nls")
    private void assertModelTemplateReferences(ModelType deModel) {
        Organization o1 =
                findOrganizationByName(deModel, "Organization1", ASSERT_EXISTS);

        ModelTemplate mt =
                findModelTemplateByName(deModel, "HRDepartment", ASSERT_EXISTS);

        OrgUnit dynOrgParent1 =
                findOrgUnitByName(o1, "OrgUnit_ASIA", RECURSE, ASSERT_EXISTS);
        OrgUnit dynOrgParent2 =
                findOrgUnitByName(o1, "Finance", RECURSE, ASSERT_EXISTS);
        OrgUnit dynOrgParent3 =
                findOrgUnitByName(o1, "OrgUnit_EMEA", RECURSE, ASSERT_EXISTS);

        assertTrue("OrgUnit_ASIA not linked to model template.",
                dynOrgParent1.getModelTemplateRef() == mt);
        assertTrue("Finance not linked to model template.",
                dynOrgParent2.getModelTemplateRef() == mt);
        assertTrue("OrgUnit_EMEA not linked to model template.",
                dynOrgParent3.getModelTemplateRef() == mt);
    }
}
