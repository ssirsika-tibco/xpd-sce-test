/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.tibco.xpd.bom.test.transform.exports_bom_wsdl.BOMWSDL01_ExportInvalidWSDLTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_wsdl.BOMWSDL02_MultipleClassWithOperationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_wsdl.BOMWSDL03_OperationWithClassOutputNoInputsTest;
import com.tibco.xpd.bom.test.transform.exports_bom_wsdl.BOMWSDL04_OperationWithDefaultPrimitiveOutputNoInputsTest;
import com.tibco.xpd.bom.test.transform.exports_bom_wsdl.BOMWSDL05_OperationWithInputsAndOutputTest;
import com.tibco.xpd.bom.test.transform.exports_bom_wsdl.BOMWSDL06_OperationWithInputsNoOutputTest;
import com.tibco.xpd.bom.test.transform.exports_bom_wsdl.BOMWSDL07_OperationWithPrimitiveOutputNoInputsTest;
import com.tibco.xpd.bom.test.transform.exports_bom_wsdl.BOMWSDL08_SingleClassWithOperationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_wsdl.BOMWSDL09_TopLevelElementTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD01_ClassTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD02_ClassWithAttributesTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD03_ClassWithAttributeRestrictionsTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD04_ClassWithAttributesMultiTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD05_ClassGeneralizingClassTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD06_ClassSingleDirectionalCompositionTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD07_AttributeTypesTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD08_DescriptionsTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD09_PrimitiveTypeTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD10_PrimitiveTypeGeneralizingPrimitiveTypeTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD11_EnumerationTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD12_EnumerationGeneralizingEnumerationTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD13_EnumerationGeneralizingPrimitiveTypeTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD14_PackageContainerTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD15_InvalidSchemaTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD16_DecimalDigitsAndLengthTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD17_StandaloneModelTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD18_MaxTextLengthTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD19_NumberLengthTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD20_ComplexWithAnonTypeTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD21_ReferencedModelTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD22_RenamedPackageNamespaceTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD23_UserDefinedReferencingGeneratedBOMTransformationTest;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd.BOMXSD24_SimpleTypeNameClashingXSDTypeNameTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM01_OperationWithDefaultPrimitiveOutputNoInputsTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM02_OperationWithInputsAndOutputTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM03_OperationWithInputsNoOutputTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM04_OperationWithOutputNoInputsTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM05_OperationWithSimpleTypeOutputNoInputsTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM06_InlineSchemaTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM07_InlineSchemaIncludesTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM08_SinglePortTypeWithOperationTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM09_MultiplePortTypesWithOperationTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM10_CrossReferencedInlineSchemasTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM11_DifferentPathImportWSDLSchemaTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM12_GetBomNamesFromWSDLTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM13_WSDLDifferentTypesSchemaTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM14_IncludesQualifiedAndUnqualifiedTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM15_DifferentInlineTargetNamespaceSchemasTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM16_AutoGeneratedValidationTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM17_MixedImportsAndIncludesTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM18_InvalidImportTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM19_SchemasWithNoTargetNamespaceTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM20_SchemasInCertainFolderHierarchyTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM21_EnumerationExtendingSimpleTypeTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM22_TopLevelElementsTypeTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM23_ComplexTypeAndElementHavingSameNameTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM24_WsdlHasTwoSchemasWithSameComplexTypeExtensionNamesPrefixedWithDifferentNamespaces;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM25_AttributeInComplexTypeReferringSimpleTypeInDifferentPackageHavingSameNameAsComplexType;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM26_AnonymousComplexTypeTransformation_rt;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM27_GenerateBomForBRMWsdl;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM28_GenerateXsdForAttributeTypeDoubleTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM29_ClassAssociatingWrongPackageElementTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM30_AsociationReferringDiffrentClassProblemTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM31_CyclicDependencyCheckTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM32_IncludeSchemaLocationsOfIncludedSchemasInStereotypeInformation;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM33_XPD5167_AssocToSimpTypeMissDuringMerge;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM35_SameNameEnumerationInTwoSchemasTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM36_SameNamePrimitiveTypeInTwoSchemasTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM37_SameNameCompTypeAnonyElemProbInGenXsd;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM38_NameConflicts_FixedIn_XPD_5786;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM39_Base64BinarySimpleTypeTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM40_ElementInTLEandTLENameClashTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM41_TLEAndCompTypeSameNameExtAnotherClassTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM50_BOMGenFor_XPD7553;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM51_BOMGenFor_XPD7132;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM52_BOMGenFor_XPD7337;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM01A_DataTypes_PrimTypesFromSimpleTypes;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM01B_DataTypes_PrimTypesFromSimpleTypesWR;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM02_SimpleTypeTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM03_SimpleTypeEnumerationTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM04_PropertyAttributesTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM05_ComplexTypeTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM06_ComplexSimpleContentExtensionTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM07_ComplexContentExtensionTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM08_ComplexTypeElemAndAttsTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM09_ComplexTypeCompositionElementTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM10_CheckAllInvalidElementsTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM11_CheckDifferentTypesImportedTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM12_DecimalDigitsAndLengthTransformationImportTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM13_MaxTextLengthTransformationImportTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM14_NumberLengthTransformationImportTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM15_AnnotationInComplexTypeTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM16_AnonymousTypeTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM17_AnonymousTypeNameClashTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM18_IncludedSchemaTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM19_ImportedSchemaTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM20_DuplicateNamesDifferentComplexTypesTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM21_UniqueIDForDuplicateNamesTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM22_DataTypes_ComplexTypeElements;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM23_GeneralizationAndCompositionWithAttributesTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM24_XsdCachingWhenProblemTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM25_DuplicateNamesSameComplexTypeTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM26_AnonymousNestedTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM27_ComplexExplicitGroupHierarchyTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM28_ComplexContentRestrictionTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM29_CheckInvalidDuplicateElementsInSameComplexTypeTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM30_ComplexNamesSameAsBaseTypeNamesTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM31_ComplexTypeWithMixedConstruct;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM32_ComplexAnonymousTypeReferenceTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM33_SimpleTypeAndElementHavingSameNameTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM34_SupportSimpleContentSchemaTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM35_SimilarComplesTypeAndAttributeNameReferencingComplexTypeTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM36_ChildElementsWithSameNameReferingComplexTypesTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM37_XsdWithGroupAndChoiceTagsTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM38_EnumerationTypeEcoreNameField;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM39_SupportEcoreNameForEvementsTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM40_GeneratedClassDuplicateNamesTransformation;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM41_ComplexTypesAndTleWithSameNamesTransformationTest;
import com.tibco.xpd.bom.test.transform.imports_xsd_bom.XSDBOM43_RecursiveIncludesTest;
import com.tibco.xpd.bom.test.transform.incremental_exports_bom_xsd.BOMXSD02_ClassGeneralisingInnerPkgClsTransformationTest;
import com.tibco.xpd.bom.test.transform.incremental_exports_bom_xsd.BOMXSD03_ClassGeneralisingExternalModelClsTransformationTest;
import com.tibco.xpd.bom.test.transform.incremental_exports_bom_xsd.BOMXSD04_ClassWithAttributeTypeInsideInnerPackageTransformationTest;
import com.tibco.xpd.bom.test.transform.incremental_exports_bom_xsd.BOMXSD05_ClassWithAttributeTypeInsideExternalModelTransformationTest;
import com.tibco.xpd.bom.test.transform.incremental_exports_bom_xsd.BOMXSD06_ClassPreserveTransformationTest;
import com.tibco.xpd.bom.test.transform.incremental_exports_bom_xsd.BOMXSD07_ClassGeneralisingInnerPkgClsPreserveTransformationTest;
import com.tibco.xpd.bom.test.transform.incremental_exports_bom_xsd.BOMXSD08_ClassGeneralisingExternalModelClsPreserveTransformationTest;
import com.tibco.xpd.bom.test.transform.incremental_exports_bom_xsd.BOMXSD09_ClassWithAttributeTypeInsideInnerPackagePreserveTransformationTest;
import com.tibco.xpd.bom.test.transform.incremental_exports_bom_xsd.BOMXSD10_ClassWithAttributeTypeInsideExternalModelPreserveTransformationTest;
import com.tibco.xpd.bom.test.transform.rules_xsd.BOMXSDRules01_AssociationTypeRule;
import com.tibco.xpd.bom.test.transform.rules_xsd.BOMXSDRules02_CyclicDependencyRule;
import com.tibco.xpd.bom.test.transform.rules_xsd.BOMXSDRules03_CombinedTestRule;
import com.tibco.xpd.bom.test.transform.rules_xsd.BOMXSDRules04_EnumGeneralisingEnumRule;
import com.tibco.xpd.bom.test.transform.rules_xsd.BOMXSDRules05_EnumLiteralsNotMeetPatternRule;
import com.tibco.xpd.bom.test.transform.rules_xsd.BOMXSDRules06_GeneralisedClassContainsDuplicateAttributeRule;
import com.tibco.xpd.bom.test.transform.rules_xsd.BOMXSDRules07_PrimitiveTypeDefaultLostRule;

/**
 * @author wzurek
 * 
 */
public class AllTestsB {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.bom.test - set B"); //$NON-NLS-1$

        addIncrementalBOMToXSDTests(suite);
        addXSDtoBOMImportTests(suite);
        addBOMtoXSDExportTests(suite);
        addWSDLtoBOMImportTests(suite);
        addBOMtoWSDLExportTests(suite);
        addBOMtoXSDRulesTests(suite);

        return suite;
    }

    /**
     * @param suite
     * @return
     */
    private static Test addXSDtoBOMImportTests(TestSuite suite) {

        suite.addTestSuite(XSDBOM01A_DataTypes_PrimTypesFromSimpleTypes.class);
        suite.addTestSuite(XSDBOM01B_DataTypes_PrimTypesFromSimpleTypesWR.class);
        suite.addTestSuite(XSDBOM02_SimpleTypeTransformationTest.class);
        suite.addTestSuite(XSDBOM03_SimpleTypeEnumerationTransformationTest.class);
        suite.addTestSuite(XSDBOM04_PropertyAttributesTransformationTest.class);
        suite.addTestSuite(XSDBOM05_ComplexTypeTransformationTest.class);
        suite.addTestSuite(XSDBOM06_ComplexSimpleContentExtensionTransformationTest.class);
        suite.addTestSuite(XSDBOM07_ComplexContentExtensionTransformationTest.class);
        suite.addTestSuite(XSDBOM08_ComplexTypeElemAndAttsTransformationTest.class);

        suite.addTestSuite(XSDBOM09_ComplexTypeCompositionElementTransformationTest.class);

        suite.addTestSuite(XSDBOM10_CheckAllInvalidElementsTransformationTest.class);
        suite.addTestSuite(XSDBOM11_CheckDifferentTypesImportedTransformationTest.class);

        suite.addTestSuite(XSDBOM12_DecimalDigitsAndLengthTransformationImportTest.class);
        suite.addTestSuite(XSDBOM13_MaxTextLengthTransformationImportTest.class);
        suite.addTestSuite(XSDBOM14_NumberLengthTransformationImportTest.class);
        suite.addTestSuite(XSDBOM15_AnnotationInComplexTypeTransformationTest.class);
        suite.addTestSuite(XSDBOM16_AnonymousTypeTransformationTest.class);
        suite.addTestSuite(XSDBOM17_AnonymousTypeNameClashTest.class);
        suite.addTestSuite(XSDBOM18_IncludedSchemaTransformationTest.class);
        suite.addTestSuite(XSDBOM19_ImportedSchemaTransformationTest.class);
        suite.addTestSuite(XSDBOM20_DuplicateNamesDifferentComplexTypesTransformationTest.class);
        suite.addTestSuite(XSDBOM21_UniqueIDForDuplicateNamesTransformationTest.class);
        suite.addTestSuite(XSDBOM22_DataTypes_ComplexTypeElements.class);
        suite.addTestSuite(XSDBOM23_GeneralizationAndCompositionWithAttributesTest.class);
        suite.addTestSuite(XSDBOM24_XsdCachingWhenProblemTest.class);
        suite.addTestSuite(XSDBOM25_DuplicateNamesSameComplexTypeTransformationTest.class);
        suite.addTestSuite(XSDBOM26_AnonymousNestedTransformationTest.class);
        suite.addTestSuite(XSDBOM27_ComplexExplicitGroupHierarchyTransformationTest.class);
        suite.addTestSuite(XSDBOM28_ComplexContentRestrictionTransformationTest.class);
        suite.addTestSuite(XSDBOM29_CheckInvalidDuplicateElementsInSameComplexTypeTransformationTest.class);
        suite.addTestSuite(XSDBOM30_ComplexNamesSameAsBaseTypeNamesTransformationTest.class);
        suite.addTestSuite(XSDBOM31_ComplexTypeWithMixedConstruct.class);
        suite.addTestSuite(XSDBOM32_ComplexAnonymousTypeReferenceTest.class);
        suite.addTestSuite(XSDBOM33_SimpleTypeAndElementHavingSameNameTransformationTest.class);
        suite.addTestSuite(XSDBOM34_SupportSimpleContentSchemaTransformationTest.class);
        suite.addTestSuite(XSDBOM35_SimilarComplesTypeAndAttributeNameReferencingComplexTypeTransformationTest.class);
        suite.addTestSuite(XSDBOM36_ChildElementsWithSameNameReferingComplexTypesTransformationTest.class);
        suite.addTestSuite(XSDBOM37_XsdWithGroupAndChoiceTagsTransformationTest.class);
        suite.addTestSuite(XSDBOM38_EnumerationTypeEcoreNameField.class);
        suite.addTestSuite(XSDBOM39_SupportEcoreNameForEvementsTransformationTest.class);
        suite.addTestSuite(XSDBOM40_GeneratedClassDuplicateNamesTransformation.class);
        suite.addTestSuite(XSDBOM41_ComplexTypesAndTleWithSameNamesTransformationTest.class);
        suite.addTestSuite(XSDBOM43_RecursiveIncludesTest.class);

        return suite;
    }

    private static Test addIncrementalBOMToXSDTests(TestSuite suite) {
        suite.addTestSuite(com.tibco.xpd.bom.test.transform.incremental_exports_bom_xsd.BOMXSD01_ClassTransformationTest.class);
        suite.addTestSuite(BOMXSD02_ClassGeneralisingInnerPkgClsTransformationTest.class);
        suite.addTestSuite(BOMXSD03_ClassGeneralisingExternalModelClsTransformationTest.class);
        suite.addTestSuite(BOMXSD04_ClassWithAttributeTypeInsideInnerPackageTransformationTest.class);
        suite.addTestSuite(BOMXSD05_ClassWithAttributeTypeInsideExternalModelTransformationTest.class);
        suite.addTestSuite(BOMXSD06_ClassPreserveTransformationTest.class);
        suite.addTestSuite(BOMXSD07_ClassGeneralisingInnerPkgClsPreserveTransformationTest.class);
        suite.addTestSuite(BOMXSD08_ClassGeneralisingExternalModelClsPreserveTransformationTest.class);
        suite.addTestSuite(BOMXSD09_ClassWithAttributeTypeInsideInnerPackagePreserveTransformationTest.class);
        suite.addTestSuite(BOMXSD10_ClassWithAttributeTypeInsideExternalModelPreserveTransformationTest.class);
        return suite;
    }

    /**
     * @param suite
     * @return
     */
    private static Test addBOMtoXSDExportTests(TestSuite suite) {
        suite.addTestSuite(BOMXSD01_ClassTransformationTest.class);
        suite.addTestSuite(BOMXSD02_ClassWithAttributesTransformationTest.class);
        suite.addTestSuite(BOMXSD03_ClassWithAttributeRestrictionsTransformationTest.class);
        suite.addTestSuite(BOMXSD04_ClassWithAttributesMultiTransformationTest.class);
        suite.addTestSuite(BOMXSD05_ClassGeneralizingClassTransformationTest.class);
        suite.addTestSuite(BOMXSD06_ClassSingleDirectionalCompositionTransformationTest.class);
        suite.addTestSuite(BOMXSD07_AttributeTypesTransformationTest.class);
        suite.addTestSuite(BOMXSD08_DescriptionsTransformationTest.class);
        suite.addTestSuite(BOMXSD09_PrimitiveTypeTransformationTest.class);
        suite.addTestSuite(BOMXSD10_PrimitiveTypeGeneralizingPrimitiveTypeTransformationTest.class);
        suite.addTestSuite(BOMXSD11_EnumerationTransformationTest.class);
        suite.addTestSuite(BOMXSD12_EnumerationGeneralizingEnumerationTransformationTest.class);
        suite.addTestSuite(BOMXSD13_EnumerationGeneralizingPrimitiveTypeTransformationTest.class);
        suite.addTestSuite(BOMXSD14_PackageContainerTransformationTest.class);
        suite.addTestSuite(BOMXSD15_InvalidSchemaTransformationTest.class);
        suite.addTestSuite(BOMXSD16_DecimalDigitsAndLengthTransformationTest.class);
        suite.addTestSuite(BOMXSD17_StandaloneModelTransformationTest.class);
        suite.addTestSuite(BOMXSD18_MaxTextLengthTransformationTest.class);
        suite.addTestSuite(BOMXSD19_NumberLengthTransformationTest.class);
        suite.addTestSuite(BOMXSD20_ComplexWithAnonTypeTransformationTest.class);
        suite.addTestSuite(BOMXSD21_ReferencedModelTransformationTest.class);
        suite.addTestSuite(BOMXSD22_RenamedPackageNamespaceTransformationTest.class);
        suite.addTestSuite(BOMXSD23_UserDefinedReferencingGeneratedBOMTransformationTest.class);
        suite.addTestSuite(BOMXSD24_SimpleTypeNameClashingXSDTypeNameTest.class);

        return suite;
    }

    /**
     * @param suite
     * @return
     */
    private static Test addWSDLtoBOMImportTests(TestSuite suite) {
        suite.addTestSuite(WSDLBOM01_OperationWithDefaultPrimitiveOutputNoInputsTest.class);
        suite.addTestSuite(WSDLBOM02_OperationWithInputsAndOutputTest.class);
        suite.addTestSuite(WSDLBOM03_OperationWithInputsNoOutputTest.class);
        suite.addTestSuite(WSDLBOM04_OperationWithOutputNoInputsTest.class);
        suite.addTestSuite(WSDLBOM05_OperationWithSimpleTypeOutputNoInputsTest.class);

        suite.addTestSuite(WSDLBOM06_InlineSchemaTest.class);
        suite.addTestSuite(WSDLBOM07_InlineSchemaIncludesTest.class);
        suite.addTestSuite(WSDLBOM08_SinglePortTypeWithOperationTest.class);
        suite.addTestSuite(WSDLBOM09_MultiplePortTypesWithOperationTest.class);
        suite.addTestSuite(WSDLBOM10_CrossReferencedInlineSchemasTest.class);
        suite.addTestSuite(WSDLBOM11_DifferentPathImportWSDLSchemaTest.class);
        suite.addTestSuite(WSDLBOM12_GetBomNamesFromWSDLTest.class);
        suite.addTestSuite(WSDLBOM13_WSDLDifferentTypesSchemaTest.class);
        suite.addTestSuite(WSDLBOM14_IncludesQualifiedAndUnqualifiedTest.class);
        suite.addTestSuite(WSDLBOM15_DifferentInlineTargetNamespaceSchemasTest.class);
        suite.addTestSuite(WSDLBOM16_AutoGeneratedValidationTest.class);
        suite.addTestSuite(WSDLBOM17_MixedImportsAndIncludesTest.class);
        suite.addTestSuite(WSDLBOM18_InvalidImportTest.class);
        suite.addTestSuite(WSDLBOM19_SchemasWithNoTargetNamespaceTest.class);
        suite.addTestSuite(WSDLBOM20_SchemasInCertainFolderHierarchyTest.class);
        suite.addTestSuite(WSDLBOM21_EnumerationExtendingSimpleTypeTest.class);
        suite.addTestSuite(WSDLBOM22_TopLevelElementsTypeTest.class);
        suite.addTestSuite(WSDLBOM23_ComplexTypeAndElementHavingSameNameTransformationTest.class);
        suite.addTestSuite(WSDLBOM24_WsdlHasTwoSchemasWithSameComplexTypeExtensionNamesPrefixedWithDifferentNamespaces.class);
        suite.addTestSuite(WSDLBOM25_AttributeInComplexTypeReferringSimpleTypeInDifferentPackageHavingSameNameAsComplexType.class);
        suite.addTestSuite(WSDLBOM26_AnonymousComplexTypeTransformation_rt.class);
        suite.addTestSuite(WSDLBOM27_GenerateBomForBRMWsdl.class);
        suite.addTestSuite(WSDLBOM28_GenerateXsdForAttributeTypeDoubleTest.class);
        suite.addTestSuite(WSDLBOM29_ClassAssociatingWrongPackageElementTest.class);
        suite.addTestSuite(WSDLBOM30_AsociationReferringDiffrentClassProblemTest.class);
        suite.addTestSuite(WSDLBOM31_CyclicDependencyCheckTest.class);
        suite.addTestSuite(WSDLBOM32_IncludeSchemaLocationsOfIncludedSchemasInStereotypeInformation.class);
        suite.addTestSuite(WSDLBOM33_XPD5167_AssocToSimpTypeMissDuringMerge.class);
        suite.addTestSuite(WSDLBOM35_SameNameEnumerationInTwoSchemasTest.class);
        suite.addTestSuite(WSDLBOM36_SameNamePrimitiveTypeInTwoSchemasTest.class);
        suite.addTestSuite(WSDLBOM37_SameNameCompTypeAnonyElemProbInGenXsd.class);
        suite.addTestSuite(WSDLBOM38_NameConflicts_FixedIn_XPD_5786.class);
        suite.addTestSuite(WSDLBOM39_Base64BinarySimpleTypeTest.class);
        suite.addTestSuite(WSDLBOM40_ElementInTLEandTLENameClashTest.class);
        suite.addTestSuite(WSDLBOM41_TLEAndCompTypeSameNameExtAnotherClassTest.class);
        suite.addTestSuite(WSDLBOM50_BOMGenFor_XPD7553.class);
        suite.addTestSuite(WSDLBOM51_BOMGenFor_XPD7132.class);
        suite.addTestSuite(WSDLBOM52_BOMGenFor_XPD7337.class);
        return suite;
    }

    /**
     * @param suite
     * @return
     */
    private static Test addBOMtoWSDLExportTests(TestSuite suite) {
        suite.addTestSuite(BOMWSDL01_ExportInvalidWSDLTransformationTest.class);
        suite.addTestSuite(BOMWSDL02_MultipleClassWithOperationTest.class);
        suite.addTestSuite(BOMWSDL03_OperationWithClassOutputNoInputsTest.class);
        suite.addTestSuite(BOMWSDL04_OperationWithDefaultPrimitiveOutputNoInputsTest.class);
        suite.addTestSuite(BOMWSDL05_OperationWithInputsAndOutputTest.class);
        suite.addTestSuite(BOMWSDL06_OperationWithInputsNoOutputTest.class);
        suite.addTestSuite(BOMWSDL07_OperationWithPrimitiveOutputNoInputsTest.class);
        suite.addTestSuite(BOMWSDL08_SingleClassWithOperationTest.class);
        suite.addTestSuite(BOMWSDL09_TopLevelElementTest.class);
        return suite;
    }

    /**
     * @param suite
     * @return
     */
    private static Test addBOMtoXSDRulesTests(TestSuite suite) {
        suite.addTestSuite(BOMXSDRules01_AssociationTypeRule.class);
        suite.addTestSuite(BOMXSDRules02_CyclicDependencyRule.class);
        suite.addTestSuite(BOMXSDRules03_CombinedTestRule.class);
        suite.addTestSuite(BOMXSDRules04_EnumGeneralisingEnumRule.class);
        suite.addTestSuite(BOMXSDRules05_EnumLiteralsNotMeetPatternRule.class);
        suite.addTestSuite(BOMXSDRules06_GeneralisedClassContainsDuplicateAttributeRule.class);
        suite.addTestSuite(BOMXSDRules07_PrimitiveTypeDefaultLostRule.class);

        return suite;
    }

}
