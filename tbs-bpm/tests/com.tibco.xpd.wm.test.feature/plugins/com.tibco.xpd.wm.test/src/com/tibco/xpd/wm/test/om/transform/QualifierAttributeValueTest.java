/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.wm.test.om.transform;

import java.math.BigDecimal;

import javax.xml.bind.DatatypeConverter;

import com.tibco.n2.directory.model.de.DataType;
import com.tibco.n2.directory.model.de.ModelOrgUnit;
import com.tibco.n2.directory.model.de.ModelTemplate;
import com.tibco.n2.directory.model.de.ModelType;
import com.tibco.n2.directory.model.de.Privilege;
import com.tibco.n2.directory.model.de.PrivilegeHolding;
import com.tibco.n2.directory.model.de.QualifiedHolding;
import com.tibco.n2.directory.model.de.Qualifier;
import com.tibco.n2.directory.model.de.SystemAction;
import com.tibco.xpd.om.core.om.OrgModel;

/**
 * The Test validates the Qualifier Attribute & Qualifier Values.
 * 
 * @author aprasad
 * @since 24-Oct-2013
 */
@SuppressWarnings("nls")
public class QualifierAttributeValueTest extends AbstractDETransformationTest {

    private static final String MODEL_TEMPLATE = "HRDepartment";

    private static final String ENUMSET_PRIVILEGE_NAME = "EnumSetPrivilege";

    private static final String ENUM_PRIVILEGE_NAME = "EnumPrivilege";

    private static final String DATE_PRIVILEGE_NAME = "DatePrivilege";

    private static final String DATETIME_PRIVILEGE_NAME = "DateTimePrivilege";

    private static final String TIME_PRIVILEGE_NAME = "TimePrivilege";

    // private static final String BOOLEAN_PRIVILEGE_NAME = "BooleanPrivilege";

    private static final String STRING_PRIVILEGE_NAME = "StringPrivilege";

    private static final String DECIMAL_PRIVILEGE_NAME = "DecimalPrivilege";

    private static final String INTEGER_PRIVILEGE_NAME = "IntegerPrivilege";

    private static final String SET_PRIVILEGE_NAME = "SetPrivilege";

    /**
     * @see com.tibco.xpd.wm.test.om.transform.AbstractDETransformationTest#validateTransformedModel()
     * 
     */
    @Override
    protected void assertTransformedModel(ModelType deModel, OrgModel orgModel) {

        // Test ModelTemplate Privileges and Capability for Qualifier Attribute
        validatePrivileges(deModel);

        // Test usage of Qualifier Attribute Value in for Model Template
        ModelTemplate hrDeptTemplate =
                findModelTemplateByName(deModel, MODEL_TEMPLATE, ASSERT_EXISTS);
        ModelOrgUnit traininglOrgUnit =
                findModelOrgUnitByName(hrDeptTemplate,
                        "TrainingandDevelopment",
                        !RECURSE,
                        ASSERT_EXISTS);
        PrivilegeHolding accessPrivHolding =
                getPrivilegeHoldingByName(traininglOrgUnit.getPrivilegeHeld(),
                        ENUMSET_PRIVILEGE_NAME);
        validatePrivilageHolding(accessPrivHolding,
                true,
                DataType.ENUM_SET,
                "WRITE");
        PrivilegeHolding accessSetPrivHolding =
                getPrivilegeHoldingByName(traininglOrgUnit.getPrivilegeHeld(),
                        SET_PRIVILEGE_NAME);
        validatePrivilageHolding(accessSetPrivHolding,
                true,
                DataType.SET,
                "REMOVE");

        SystemAction systemAction =
                getSystemActionByName(traininglOrgUnit.getSystemAction(),
                        "workItemAllocation");
        assertNotNull("ModelOrgUnit -  SystemAction missing", systemAction);
        accessPrivHolding =
                getPrivilegeHoldingByName(systemAction.getReqPrivilege(),
                        ENUMSET_PRIVILEGE_NAME);
        validatePrivilageHolding(accessPrivHolding,
                true,
                DataType.ENUM_SET,
                "DELETE");

        // EmployeeBenefits ModelOrgUnit

        ModelOrgUnit empBenefitOrgUnit =
                findModelOrgUnitByName(hrDeptTemplate,
                        "EmployeeBenefits",
                        !RECURSE,
                        ASSERT_EXISTS);
        accessPrivHolding =
                getPrivilegeHoldingByName(empBenefitOrgUnit.getPrivilegeHeld(),
                        ENUMSET_PRIVILEGE_NAME);
        validatePrivilageHolding(accessPrivHolding,
                true,
                DataType.ENUM_SET,
                "READ");
        accessPrivHolding =
                getPrivilegeHoldingByName(empBenefitOrgUnit.getPrivilegeHeld(),
                        ENUM_PRIVILEGE_NAME);
        validatePrivilageHolding(accessPrivHolding,
                true,
                DataType.ENUM,
                "Value2");

        accessPrivHolding =
                getPrivilegeHoldingByName(empBenefitOrgUnit.getPrivilegeHeld(),
                        DECIMAL_PRIVILEGE_NAME);
        validatePrivilageHolding(accessPrivHolding,
                true,
                DataType.DECIMAL,
                "4343432467");

        accessPrivHolding =
                getPrivilegeHoldingByName(empBenefitOrgUnit.getPrivilegeHeld(),
                        STRING_PRIVILEGE_NAME);
        validatePrivilageHolding(accessPrivHolding,
                true,
                DataType.STRING,
                "ALLOW");
        accessPrivHolding =
                getPrivilegeHoldingByName(empBenefitOrgUnit.getPrivilegeHeld(),
                        INTEGER_PRIVILEGE_NAME);
        validatePrivilageHolding(accessPrivHolding,
                true,
                DataType.INTEGER,
                "1223454354");
        accessPrivHolding =
                getPrivilegeHoldingByName(empBenefitOrgUnit.getPrivilegeHeld(),
                        DATE_PRIVILEGE_NAME);
        // TODO when the DATE related issue in transformation is resolved ,
        // uncomment this code and added section included below to the
        // OrgMOdel.om file.
        // validatePrivilageHolding(accessPrivHolding,
        // true,
        // DataType.DATE,
        // "2013-10-02");
        // accessPrivHolding =
        // getPrivilegeHoldingByName(empBenefitOrgUnit.getPrivilegeHeld(),
        // DATETIME_PRIVILEGE_NAME);
        // validatePrivilageHolding(accessPrivHolding,
        // true,
        // DataType.DATE_TIME,
        // "2013-10-23T10:40:00+0000");
        // accessPrivHolding =
        // getPrivilegeHoldingByName(empBenefitOrgUnit.getPrivilegeHeld(),
        // TIME_PRIVILEGE_NAME);
        // validatePrivilageHolding(accessPrivHolding,
        // true,
        // DataType.TIME,
        // "20:42:00+0000");
        // UNCOMMENT ABOVE CODE when DATE related issues are resolved and add
        // extract included below OM file for EmployeeBenefit ModelOrgUnit
        /*
         * <privilegeAssociations xmi:type="om:PrivilegeAssociation"
         * xmi:id="_RIX9wTyhEeO29YWL2tz2-w" privilege="_kRy0wDyYEeO29YWL2tz2-w">
         * <qualifierValue xmi:type="om:AttributeValue"
         * xmi:id="_fDnwoDyhEeO29YWL2tz2-w" attribute="_lfIxoDyYEeO29YWL2tz2-w"
         * value="2013-10-02"/> </privilegeAssociations> <privilegeAssociations
         * xmi:type="om:PrivilegeAssociation" xmi:id="_RIX9wjyhEeO29YWL2tz2-w"
         * privilege="_o3dYUDyYEeO29YWL2tz2-w"> <qualifierValue
         * xmi:type="om:AttributeValue" xmi:id="_ev9NwDyhEeO29YWL2tz2-w"
         * attribute="_pvDusDyYEeO29YWL2tz2-w"
         * value="2013-10-23T10:40:00+0000"/> </privilegeAssociations>
         * <privilegeAssociations xmi:type="om:PrivilegeAssociation"
         * xmi:id="_RIX9yTyhEeO29YWL2tz2-w" privilege="_6cac0DyYEeO29YWL2tz2-w">
         * <qualifierValue xmi:type="om:AttributeValue"
         * xmi:id="_XIwy4DyhEeO29YWL2tz2-w" attribute="_6ySmMDyYEeO29YWL2tz2-w"
         * value="20:42:00+0000"/> </privilegeAssociations>
         */

    }

    /**
     * 
     */
    private void validatePrivileges(ModelType deModel) {
        validatePrivilegeQualifier(deModel,
                ENUMSET_PRIVILEGE_NAME,
                true,
                4,
                DataType.ENUM_SET,
                "ALL");
        validatePrivilegeQualifier(deModel,
                ENUM_PRIVILEGE_NAME,
                true,
                4,
                DataType.ENUM,
                "Value2");
        validatePrivilegeQualifier(deModel,
                DATE_PRIVILEGE_NAME,
                true,
                0,
                DataType.DATE,
                null);
        validatePrivilegeQualifier(deModel,
                DATETIME_PRIVILEGE_NAME,
                true,
                0,
                DataType.DATE_TIME,
                null);
        validatePrivilegeQualifier(deModel,
                TIME_PRIVILEGE_NAME,
                true,
                0,
                DataType.TIME,
                null);
        validatePrivilegeQualifier(deModel,
                DECIMAL_PRIVILEGE_NAME,
                true,
                0,
                DataType.DECIMAL,
                null);
        validatePrivilegeQualifier(deModel,
                STRING_PRIVILEGE_NAME,
                true,
                0,
                DataType.STRING,
                null);
        validatePrivilegeQualifier(deModel,
                INTEGER_PRIVILEGE_NAME,
                true,
                0,
                DataType.INTEGER,
                null);
        validatePrivilegeQualifier(deModel,
                "GeneralPrivilegeNoQualifier",
                false,
                0,
                null,
                null);
    }

    /**
     * @param accessPrivHolding
     */
    private void validatePrivilageHolding(PrivilegeHolding accessPrivHolding,
            boolean qualifier, DataType qualifierDataType, String qualifierValue) {

        assertNotNull("ModelOrgUnit - Privilege Missing", accessPrivHolding);
        if (qualifier) {

            DataType dataType =
                    accessPrivHolding.getPrivilege().getQualifier()
                            .getDataType();
            assertEquals("QualifierHolding DataType mismatch",
                    dataType,
                    qualifierDataType);
            validateQualifierValue(accessPrivHolding, qualifierValue, dataType);
        }
    }

    /**
     * @param quafiliedHolding
     * @param qualifierValue
     * @param dataType
     */
    private void validateQualifierValue(QualifiedHolding quafiliedHolding,
            String qualifierValue, DataType dataType) {
        switch (dataType) {
        // case
        // BOOLEAN:assertEquals(String.format("Qualifier value does not match for %1s",
        // privilegeName),
        // qualifierValue,
        // accessPrivHolding.getBoolean());
        // break;
        case TIME:
            assertNotNull("ModelOrgUnit - qualifier value missing",
                    quafiliedHolding.getTime());
            assertEquals(String.format("Qualifier value does not match for %1s",
                    DatatypeConverter.parseTime(qualifierValue)),
                    DatatypeConverter.parseTime(qualifierValue),
                    quafiliedHolding.getTime());
            break;
        case DATE:
            assertNotNull("ModelOrgUnit - qualifier value missing",
                    quafiliedHolding.getDate());
            assertEquals(String.format("Qualifier value does not match for %1s",
                    DatatypeConverter.parseDate(qualifierValue)),
                    DatatypeConverter.parseDate(qualifierValue),
                    quafiliedHolding.getDate());
            break;
        case DATE_TIME:
            assertNotNull("ModelOrgUnit - qualifier value missing",
                    quafiliedHolding.getDateTime());
            assertEquals(String.format("Qualifier value does not match for %1s",
                    qualifierValue),
                    DatatypeConverter.parseDateTime(qualifierValue),
                    quafiliedHolding.getDateTime());
            break;
        case DECIMAL:
            assertEquals(String.format("Qualifier value does not match for %1s",
                    qualifierValue),
                    new BigDecimal(qualifierValue),
                    quafiliedHolding.getDecimal());
            break;
        case ENUM:
            assertNotNull("ModelOrgUnit - qualifier value missing",
                    quafiliedHolding.getEnum());
            assertEquals(String.format("Qualifier value does not match for %1s",
                    qualifierValue),
                    qualifierValue,
                    quafiliedHolding.getEnum());
            break;
        case ENUM_SET:
            assertNotNull("ModelOrgUnit - qualifier value missing",
                    quafiliedHolding.getEnumSet());
            assertTrue(String.format("Qualifier value does not match for %1s",
                    qualifierValue),
                    quafiliedHolding.getEnumSet().contains(qualifierValue));
            break;
        case SET:
            assertNotNull("ModelOrgUnit - qualifier value missing",
                    quafiliedHolding.getElement());
            assertTrue(String.format("Qualifier value does not match for %1s",
                    qualifierValue),
                    quafiliedHolding.getElement().contains(qualifierValue));
            break;
        case INTEGER:

            assertEquals(String.format("Qualifier value does not match for %1s",
                    qualifierValue),
                    Long.parseLong(qualifierValue),
                    quafiliedHolding.getInteger());
            break;

        case STRING:
            assertNotNull("ModelOrgUnit - qualifier value missing",
                    quafiliedHolding.getString());
            assertEquals(String.format("Qualifier value does not match for %1s",
                    qualifierValue),
                    qualifierValue,
                    quafiliedHolding.getString());
            break;

        }
    }

    /**
     * Validates Privilege Qualifier.
     * 
     * @param deModel
     * 
     * @param name
     *            , Privilege
     * @param totalQualifierValues
     *            , number of values , only used for Enum/EnumSet
     * @param dataType
     *            , Qualifier DataType
     * @param enumValue
     *            , Enum entry to test, pass null if not an Enum/EnumSet type
     */
    private void validatePrivilegeQualifier(ModelType deModel,
            String privilegeName, boolean hasQualifier,
            int totalQualifierValues, DataType dataType, String enumValue) {
        Privilege accessPrivilege = getPrivilegeByName(deModel, privilegeName);
        assertNotNull(String.format("Model - Privilege %1s is missing ",
                privilegeName), accessPrivilege);
        if (hasQualifier) {
            Qualifier qualifier = accessPrivilege.getQualifier();
            validateQualifier(qualifier,
                    totalQualifierValues,
                    dataType,
                    enumValue);
        }

    }

    private void validateQualifier(Qualifier qualifier,
            int totalQualifierValues, DataType dataType, String enumValue) {
        assertNotNull("qualifier is missing", qualifier);
        assertEquals("qualifier DataType mismatch",
                dataType,
                qualifier.getDataType());
        if (DataType.ENUM.equals(dataType)
                || DataType.ENUM_SET.equals(dataType)) {
            assertNotNull("qualifier value missing", qualifier.getEnum());
            assertEquals("qualifier values count does not match",
                    totalQualifierValues,
                    qualifier.getEnum().size());
            if (enumValue != null) {
                assertTrue("qualifier enumValue is missing", qualifier
                        .getEnum().contains(enumValue));
            }
        }

    }
}
