/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.tibco.xpd.bom.test.transform.rules_wsdl.BOMWSDLRule01_ClassifierOtherBOMReferenceNotAllowedTest;
import com.tibco.xpd.bom.test.transform.rules_wsdl.BOMWSDLRule02_OperationOtherBOMReferenceNotAllowedRule;
import com.tibco.xpd.bom.test.transform.rules_wsdl.BOMWSDLRule03_OperationParamMultiplicityNotSupportedRule;
import com.tibco.xpd.bom.test.transform.rules_wsdl.BOMWSDLRule04_PropertyOtherBOMReferenceNotAllowedRule;
import com.tibco.xpd.bom.test.transform.wsdlbom_tle.XSDBOM_TLE1_ClashingNamespaceDefinitions;
import com.tibco.xpd.bom.test.transform.wsdlbom_tle.XSDBOM_TLE2_DeepImportTopLevelElements;
import com.tibco.xpd.bom.test.transform.wsdlbom_tle.XSDBOM_TLE3_CheckNoFalseMultipleDependencies;
import com.tibco.xpd.bom.test.transform.wsdlbom_tle.XSDBOM_TLE4A_DependencyChecker;
import com.tibco.xpd.bom.test.transform.wsdlbom_tle.XSDBOM_TLE4B_DependencyChecker;
import com.tibco.xpd.bom.test.transform.xsdbom_all.XSDBOM_All01_ComplexType;
import com.tibco.xpd.bom.test.transform.xsdbom_all.XSDBOM_All02_TopLevelAnonComplexType;
import com.tibco.xpd.bom.test.transform.xsdbom_all.XSDBOM_All03_NestedAnonComplexType;
import com.tibco.xpd.bom.test.transform.xsdbom_all.XSDBOM_All04_ComplexContent;
import com.tibco.xpd.bom.test.transform.xsdbom_any.XSDBOM_Any01_Sequence;
import com.tibco.xpd.bom.test.transform.xsdbom_any.XSDBOM_Any03_Choice;
import com.tibco.xpd.bom.test.transform.xsdbom_any.XSDBOM_Any04_Composition;
import com.tibco.xpd.bom.test.transform.xsdbom_any.XSDBOM_Any06_AttributeContent;
import com.tibco.xpd.bom.test.transform.xsdbom_any.XSDBOM_Any07_AnyAttributes;
import com.tibco.xpd.bom.test.transform.xsdbom_any.XSDBOM_Any08_AnyTypes;
import com.tibco.xpd.bom.test.transform.xsdbom_any.XSDBOM_Any09_AnySimpleTypes;
import com.tibco.xpd.bom.test.transform.xsdbom_attr_groups.XSDBOM_AttributeGroup01A_SimpleAttrGroup;
import com.tibco.xpd.bom.test.transform.xsdbom_attr_groups.XSDBOM_AttributeGroup01B_SimpleAttrGroup_ImportAttrGroup;
import com.tibco.xpd.bom.test.transform.xsdbom_attr_groups.XSDBOM_AttributeGroup01C_SimpleAttrGroup_IncludeAttrGroup;
import com.tibco.xpd.bom.test.transform.xsdbom_attr_groups.XSDBOM_AttributeGroup02_NestedAttrGroup;
import com.tibco.xpd.bom.test.transform.xsdbom_choice.XSDBOM_Choice01_SimpleChoice;
import com.tibco.xpd.bom.test.transform.xsdbom_choice.XSDBOM_Choice02_ComplexChoice;
import com.tibco.xpd.bom.test.transform.xsdbom_choice.XSDBOM_Choice03_EnumChoice;
import com.tibco.xpd.bom.test.transform.xsdbom_choice.XSDBOM_Choice04_MixedChoice;
import com.tibco.xpd.bom.test.transform.xsdbom_choice.XSDBOM_Choice05_NestedChoice;
import com.tibco.xpd.bom.test.transform.xsdbom_choice.XSDBOM_Choice06_AnonChoice;
import com.tibco.xpd.bom.test.transform.xsdbom_choice.XSDBOM_Choice07_Hierarchy;
import com.tibco.xpd.bom.test.transform.xsdbom_group.XSDBOM_Group01A_SimpleGroup;
import com.tibco.xpd.bom.test.transform.xsdbom_group.XSDBOM_Group01B_SimpleGroup_ImportGroup;
import com.tibco.xpd.bom.test.transform.xsdbom_group.XSDBOM_Group01C_SimpleGroup_IncludeGroup;
import com.tibco.xpd.bom.test.transform.xsdbom_group.XSDBOM_Group02_NestedGroup;
import com.tibco.xpd.bom.test.transform.xsdbom_group.XSDBOM_Group03_Multiplicity;
import com.tibco.xpd.bom.test.transform.xsdbom_group.XSDBOM_Group04A_DuplicateRefs;
import com.tibco.xpd.bom.test.transform.xsdbom_group.XSDBOM_Group04B_DuplicateRefs;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE01A_SimpleElementTypeWithAttributes;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE01B_SimpleElementTypeAllBaseTypes;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE02_ComplexElementType;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE03_MultipleElementsOfSameComplexType;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE04_MultipleElementsOfDifferentComplexType;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE05_AnonymousComplexType;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE06_TopLevelAttributes;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE07_TopLevelEnumeration;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE08_AnonymousSimpleTypes;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE09_NestedAnonymousTypes;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE10A_RootElementReference;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE10B_RootElementReference;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE11_ComplexRootElementReference;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE12A_ComplexTypeExtension;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE12B_ComplexTypeExtension;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE13_DuplicateNamesValidationCheck;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE15_ElementsWithTypeOutsideSchemaNonCircular;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE16_DuplicateElementNamesWithComplexAndSimpleTypes;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE17_InvalidCharsInTargetNamespace;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE18_JustTLEWithExternalTypes;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE19_ChainLink;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE20_SameNameComplexTypesWithAttributes;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE21_TopLevelWithAnonymousMinMaxAttributes;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE22_CheckDifferentTypesImportedSameName;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE23_DuplicateElementsWithAnonymousComplexTypes;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE24_ElementAndComplexWithAbstract;
import com.tibco.xpd.bom.test.transform.xsdbom_tle.XSDBOM_TLE25_ElementsWithSubstitutionGroups;

/**
 * @author wzurek
 * 
 */
public class AllTestsC {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.bom.test - set C"); //$NON-NLS-1$

        addBOMtoWSDLRulestests(suite);
        addXSDTOBOMTopLevelTests(suite);
        addWSDLToBOMTopLevelTests(suite);
        addXSDtoBOMChoiceTests(suite);
        addXSDtoBOMGroupTests(suite);
        addXSDtoBOMAttributeGroupTests(suite);
        addXSDtoBOMAnyTests(suite);
        addXSDtoBOMAllTests(suite);

        return suite;
    }

    private static void addBOMtoWSDLRulestests(TestSuite suite) {
        suite.addTestSuite(BOMWSDLRule01_ClassifierOtherBOMReferenceNotAllowedTest.class);
        suite.addTestSuite(BOMWSDLRule02_OperationOtherBOMReferenceNotAllowedRule.class);
        suite.addTestSuite(BOMWSDLRule03_OperationParamMultiplicityNotSupportedRule.class);
        suite.addTestSuite(BOMWSDLRule04_PropertyOtherBOMReferenceNotAllowedRule.class);

    }

    private static void addXSDTOBOMTopLevelTests(TestSuite suite) {
        suite.addTestSuite(XSDBOM_TLE01A_SimpleElementTypeWithAttributes.class);
        suite.addTestSuite(XSDBOM_TLE01B_SimpleElementTypeAllBaseTypes.class);
        suite.addTestSuite(XSDBOM_TLE02_ComplexElementType.class);
        suite.addTestSuite(XSDBOM_TLE03_MultipleElementsOfSameComplexType.class);
        suite.addTestSuite(XSDBOM_TLE04_MultipleElementsOfDifferentComplexType.class);
        suite.addTestSuite(XSDBOM_TLE05_AnonymousComplexType.class);
        suite.addTestSuite(XSDBOM_TLE06_TopLevelAttributes.class);
        suite.addTestSuite(XSDBOM_TLE07_TopLevelEnumeration.class);
        suite.addTestSuite(XSDBOM_TLE08_AnonymousSimpleTypes.class);
        suite.addTestSuite(XSDBOM_TLE09_NestedAnonymousTypes.class);
        suite.addTestSuite(XSDBOM_TLE10A_RootElementReference.class);
        suite.addTestSuite(XSDBOM_TLE10B_RootElementReference.class);
        suite.addTestSuite(XSDBOM_TLE11_ComplexRootElementReference.class);
        suite.addTestSuite(XSDBOM_TLE12A_ComplexTypeExtension.class);
        suite.addTestSuite(XSDBOM_TLE12B_ComplexTypeExtension.class);
        suite.addTestSuite(XSDBOM_TLE13_DuplicateNamesValidationCheck.class);

        /*
         * Sid: XSDBOM_TLE14_ElementsWithTypeOutsideSchema has been disabled on
         * purpose because it involves a Cyclic-Dependency (via TLE's) and we
         * now strictly validate against this in BOMs, so there is no point in
         * this test.
         * 
         * XSDBOM_TLE14_ElementsWithTypeOutsideSchema
         * suite.addTestSuite(XSDBOM_TLE14_ElementsWithTypeOutsideSchema.class);
         */

        suite.addTestSuite(XSDBOM_TLE15_ElementsWithTypeOutsideSchemaNonCircular.class);
        suite.addTestSuite(XSDBOM_TLE16_DuplicateElementNamesWithComplexAndSimpleTypes.class);
        suite.addTestSuite(XSDBOM_TLE17_InvalidCharsInTargetNamespace.class);
        suite.addTestSuite(XSDBOM_TLE18_JustTLEWithExternalTypes.class);
        suite.addTestSuite(XSDBOM_TLE19_ChainLink.class);
        suite.addTestSuite(XSDBOM_TLE20_SameNameComplexTypesWithAttributes.class);
        suite.addTestSuite(XSDBOM_TLE21_TopLevelWithAnonymousMinMaxAttributes.class);
        suite.addTestSuite(XSDBOM_TLE22_CheckDifferentTypesImportedSameName.class);
        suite.addTestSuite(XSDBOM_TLE23_DuplicateElementsWithAnonymousComplexTypes.class);
        suite.addTestSuite(XSDBOM_TLE24_ElementAndComplexWithAbstract.class);
        suite.addTestSuite(XSDBOM_TLE25_ElementsWithSubstitutionGroups.class);
    }

    private static void addWSDLToBOMTopLevelTests(TestSuite suite) {
        suite.addTestSuite(XSDBOM_TLE1_ClashingNamespaceDefinitions.class);
        suite.addTestSuite(XSDBOM_TLE2_DeepImportTopLevelElements.class);
        suite.addTestSuite(XSDBOM_TLE3_CheckNoFalseMultipleDependencies.class);
        suite.addTestSuite(XSDBOM_TLE4A_DependencyChecker.class);
        suite.addTestSuite(XSDBOM_TLE4B_DependencyChecker.class);
    }

    private static void addXSDtoBOMChoiceTests(TestSuite suite) {
        suite.addTestSuite(XSDBOM_Choice01_SimpleChoice.class);
        suite.addTestSuite(XSDBOM_Choice02_ComplexChoice.class);
        suite.addTestSuite(XSDBOM_Choice03_EnumChoice.class);
        suite.addTestSuite(XSDBOM_Choice04_MixedChoice.class);
        suite.addTestSuite(XSDBOM_Choice05_NestedChoice.class);
        suite.addTestSuite(XSDBOM_Choice06_AnonChoice.class);
        suite.addTestSuite(XSDBOM_Choice07_Hierarchy.class);
    }

    private static void addXSDtoBOMGroupTests(TestSuite suite) {
        suite.addTestSuite(XSDBOM_Group01A_SimpleGroup.class);
        suite.addTestSuite(XSDBOM_Group01B_SimpleGroup_ImportGroup.class);
        suite.addTestSuite(XSDBOM_Group01C_SimpleGroup_IncludeGroup.class);
        suite.addTestSuite(XSDBOM_Group02_NestedGroup.class);
        suite.addTestSuite(XSDBOM_Group03_Multiplicity.class);
        suite.addTestSuite(XSDBOM_Group04A_DuplicateRefs.class);
        suite.addTestSuite(XSDBOM_Group04B_DuplicateRefs.class);
    }

    private static void addXSDtoBOMAttributeGroupTests(TestSuite suite) {
        suite.addTestSuite(XSDBOM_AttributeGroup01A_SimpleAttrGroup.class);
        suite.addTestSuite(XSDBOM_AttributeGroup01B_SimpleAttrGroup_ImportAttrGroup.class);
        suite.addTestSuite(XSDBOM_AttributeGroup01C_SimpleAttrGroup_IncludeAttrGroup.class);
        suite.addTestSuite(XSDBOM_AttributeGroup02_NestedAttrGroup.class);
    }

    private static void addXSDtoBOMAnyTests(TestSuite suite) {
        suite.addTestSuite(XSDBOM_Any01_Sequence.class);

        // Kapil: This test "XSDBOM_Any02_NestedSequence" is commented out on
        // purpose because the Model file
        // which it refers to has 2 <xsd:any> tags under the same complex type.
        // This is not supported and causes errors.
        // suite.addTestSuite(XSDBOM_Any02_NestedSequence.class);

        suite.addTestSuite(XSDBOM_Any03_Choice.class);
        suite.addTestSuite(XSDBOM_Any04_Composition.class);

        // Kapil: This test "XSDBOM_Any05_Generalization" is commented out on
        // purpose because the Model file
        // which it refers to has 2 <xsd:any> tags under the same complex type.
        // This is not supported and causes errors.
        // suite.addTestSuite(XSDBOM_Any05_Generalization.class);

        suite.addTestSuite(XSDBOM_Any06_AttributeContent.class);
        suite.addTestSuite(XSDBOM_Any07_AnyAttributes.class);
        suite.addTestSuite(XSDBOM_Any08_AnyTypes.class);
        suite.addTestSuite(XSDBOM_Any09_AnySimpleTypes.class);
    }

    private static void addXSDtoBOMAllTests(TestSuite suite) {
        suite.addTestSuite(XSDBOM_All01_ComplexType.class);
        suite.addTestSuite(XSDBOM_All02_TopLevelAnonComplexType.class);
        suite.addTestSuite(XSDBOM_All03_NestedAnonComplexType.class);
        suite.addTestSuite(XSDBOM_All04_ComplexContent.class);
    }

}
