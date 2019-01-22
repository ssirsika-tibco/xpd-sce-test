/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.tibco.xpd.bom.test.copypaste.CopyPasteAllTests;
import com.tibco.xpd.bom.test.transform.wsdl_coverage_amxbpm_api.WSDLCoverageAMXBPMAPITest;
import com.tibco.xpd.bom.test.transform.xsd_datatypes.XSDBOM_DataTypes01_DefaultValues;
import com.tibco.xpd.bom.test.transform.xsdbom_block.XSDBOM_Block01_Single;
import com.tibco.xpd.bom.test.transform.xsdbom_block.XSDBOM_Block02_Multiple;
import com.tibco.xpd.bom.test.transform.xsdbom_final.XSDBOM_Final01_Single;
import com.tibco.xpd.bom.test.transform.xsdbom_final.XSDBOM_Final02_Multiple;
import com.tibco.xpd.bom.test.transform.xsdbom_multi_explicit_groups.XSDBOM_ExpGroups01_SimpleSequence;
import com.tibco.xpd.bom.test.transform.xsdbom_multi_explicit_groups.XSDBOM_ExpGroups02_SimpleChoice;
import com.tibco.xpd.bom.test.transform.xsdbom_multi_explicit_groups.XSDBOM_ExpGroups03_SimpleSequenceWithAny;
import com.tibco.xpd.bom.test.transform.xsdbom_multi_explicit_groups.XSDBOM_ExpGroups04_SimpleChoiceWithAny;
import com.tibco.xpd.bom.test.transform.xsdbom_multi_explicit_groups.XSDBOM_ExpGroups05_SimpleSequenceInGroup;
import com.tibco.xpd.bom.test.transform.xsdbom_multi_explicit_groups.XSDBOM_ExpGroups06_SimpleChoiceInGroup;
import com.tibco.xpd.bom.test.transform.xsdbom_multi_explicit_groups.XSDBOM_ExpGroups07_NestedSequence;
import com.tibco.xpd.bom.test.transform.xsdbom_multi_explicit_groups.XSDBOM_ExpGroups08_NestedChoice;
import com.tibco.xpd.bom.test.transform.xsdbom_multi_explicit_groups.XSDBOM_ExpGroups09_MixedNested;
import com.tibco.xpd.bom.test.transform.xsdbom_multi_explicit_groups.XSDBOM_ExpGroups10_ElemsAndAttributes;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union01_Simple;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union02_SimpleWithAnon;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union03_SimpleWithAnonEnums;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union04_SimpleWithOtherSimpleMembers;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union05_ComplexWithAttrSimple;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union06_ComplexWithAttrAnonSimple;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union07_ComplexWithElemSimple;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union08_ComplexWithElemAnonSimple;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union09_TopLevelElemSimple;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union10_TopLevelAttrSimple;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union11_TopLevelElemComplexWithElemSimple;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union12_TopLevelElemSimpleWithAnon;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union13_TopLevelAttrSimpleWithAnon;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union14_TopLevelElemSimpleWithAnonEnums;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union15_TopLevelAttrSimpleWithAnonEnums;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union16_TopLevelElemSimpleWithOtherSimpleMembers;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union17_TopLevelAttrSimpleWithOtherSimpleMembers;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union18_TopLevelElemComplexWithAttrSimple;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union19_TopLevelElemComplexWithAttrAnonSimple;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union20_TopLevelElemComplexWithElemSimple;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union21_TopLevelElemComplexWithElemAnonSimple;
import com.tibco.xpd.bom.test.transform.xsdbom_union.XSDBOM_Union23_Variations;

/**
 * @author wzurek
 * 
 */
public class AllTestsD {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.bom.test - set D"); //$NON-NLS-1$

        addXSDtoBOMBlockTests(suite);
        addXSDtoBOMFinalTests(suite);
        addXSDtoBOMDataTypesTests(suite);
        addXSDtoBOMUnionTests(suite);
        addXSDtoBOMMultiExplicitGroups(suite);
        addAMXBPM_API_Tests(suite);

        // Kapil: Adding copy paste tests to the suite
        suite.addTest(CopyPasteAllTests.suite());

        return suite;
    }

    private static void addXSDtoBOMUnionTests(TestSuite suite) {
        suite.addTestSuite(XSDBOM_Union01_Simple.class);
        suite.addTestSuite(XSDBOM_Union02_SimpleWithAnon.class);
        suite.addTestSuite(XSDBOM_Union03_SimpleWithAnonEnums.class);
        suite.addTestSuite(XSDBOM_Union04_SimpleWithOtherSimpleMembers.class);
        suite.addTestSuite(XSDBOM_Union05_ComplexWithAttrSimple.class);
        suite.addTestSuite(XSDBOM_Union06_ComplexWithAttrAnonSimple.class);
        suite.addTestSuite(XSDBOM_Union07_ComplexWithElemSimple.class);
        suite.addTestSuite(XSDBOM_Union08_ComplexWithElemAnonSimple.class);
        suite.addTestSuite(XSDBOM_Union09_TopLevelElemSimple.class);
        suite.addTestSuite(XSDBOM_Union10_TopLevelAttrSimple.class);
        suite.addTestSuite(XSDBOM_Union11_TopLevelElemComplexWithElemSimple.class);
        suite.addTestSuite(XSDBOM_Union12_TopLevelElemSimpleWithAnon.class);
        suite.addTestSuite(XSDBOM_Union13_TopLevelAttrSimpleWithAnon.class);
        suite.addTestSuite(XSDBOM_Union14_TopLevelElemSimpleWithAnonEnums.class);
        suite.addTestSuite(XSDBOM_Union15_TopLevelAttrSimpleWithAnonEnums.class);
        suite.addTestSuite(XSDBOM_Union16_TopLevelElemSimpleWithOtherSimpleMembers.class);
        suite.addTestSuite(XSDBOM_Union17_TopLevelAttrSimpleWithOtherSimpleMembers.class);
        suite.addTestSuite(XSDBOM_Union18_TopLevelElemComplexWithAttrSimple.class);
        suite.addTestSuite(XSDBOM_Union19_TopLevelElemComplexWithAttrAnonSimple.class);
        suite.addTestSuite(XSDBOM_Union20_TopLevelElemComplexWithElemSimple.class);
        suite.addTestSuite(XSDBOM_Union21_TopLevelElemComplexWithElemAnonSimple.class);
        suite.addTestSuite(XSDBOM_Union23_Variations.class);
    }

    private static void addXSDtoBOMMultiExplicitGroups(TestSuite suite) {
        suite.addTestSuite(XSDBOM_ExpGroups01_SimpleSequence.class);
        suite.addTestSuite(XSDBOM_ExpGroups02_SimpleChoice.class);
        suite.addTestSuite(XSDBOM_ExpGroups03_SimpleSequenceWithAny.class);
        suite.addTestSuite(XSDBOM_ExpGroups04_SimpleChoiceWithAny.class);
        suite.addTestSuite(XSDBOM_ExpGroups05_SimpleSequenceInGroup.class);
        suite.addTestSuite(XSDBOM_ExpGroups06_SimpleChoiceInGroup.class);
        suite.addTestSuite(XSDBOM_ExpGroups07_NestedSequence.class);
        suite.addTestSuite(XSDBOM_ExpGroups08_NestedChoice.class);
        suite.addTestSuite(XSDBOM_ExpGroups09_MixedNested.class);
        suite.addTestSuite(XSDBOM_ExpGroups10_ElemsAndAttributes.class);
    }

    private static void addXSDtoBOMBlockTests(TestSuite suite) {
        suite.addTestSuite(XSDBOM_Block01_Single.class);
        suite.addTestSuite(XSDBOM_Block02_Multiple.class);
    }

    private static void addXSDtoBOMFinalTests(TestSuite suite) {
        suite.addTestSuite(XSDBOM_Final01_Single.class);
        suite.addTestSuite(XSDBOM_Final02_Multiple.class);
    }

    private static void addXSDtoBOMDataTypesTests(TestSuite suite) {
        suite.addTestSuite(XSDBOM_DataTypes01_DefaultValues.class);
    }

    private static void addAMXBPM_API_Tests(TestSuite suite) {
        suite.addTestSuite(WSDLCoverageAMXBPMAPITest.class);
    }

}
