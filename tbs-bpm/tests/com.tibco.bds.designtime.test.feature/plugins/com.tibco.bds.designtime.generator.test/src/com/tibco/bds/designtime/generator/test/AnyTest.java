/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.bds.designtime.generator.test;


/**
 * Test the conversion of XSDs with an any defined in it
 * 
 */
public class AnyTest extends AbstractXsdBasedTest {

    public AnyTest() {
        super("any"); //$NON-NLS-1$
    }

    /**
     * Basic test that just checks the support of xsd:any
     */
    public void testSimpleAnyNamed() {
        generateBDSFromXSD("SimpleAnyNamed"); //$NON-NLS-1$
    }

    public void testAnyNestedElement() {
        generateBDSFromXSD("AnyNestedElement"); //$NON-NLS-1$
    }

    public void testNsMultiAny() {
        generateBDSFromXSD("nsMultiAny"); //$NON-NLS-1$
    }

    public void testNsOtherAny() {
        generateBDSFromXSD("nsOtherAny"); //$NON-NLS-1$
    }

    public void testNsLocalAny() {
        generateBDSFromXSD("nsLocalAny"); //$NON-NLS-1$
    }

    public void testNsTargetNamespaceAny() {
        generateBDSFromXSD("nsTargetNamespaceAny"); //$NON-NLS-1$
    }

    public void testNameClashAny() {
        generateBDSFromXSD("NameClashAny"); //$NON-NLS-1$
    }

    public void testMidOrderedAny() {
        generateBDSFromXSD("MidOrderedAny"); //$NON-NLS-1$
    }

    public void testPcStrictAnyNamespaceAny() {
        generateBDSFromXSD("PcStrictAnyNamespaceAny"); //$NON-NLS-1$
    }

    public void testPcStrictOtherNamespaceAny() {
        generateBDSFromXSD("PcStrictOtherNamespaceAny"); //$NON-NLS-1$
    }

    public void testBasicAnyType() {
        generateBDSFromXSD("BasicAnyType"); //$NON-NLS-1$
    }

    public void testAnySimpleTypes() {
        generateBDSFromXSD("AnySimpleTypes"); //$NON-NLS-1$
    }

    public void testAnyAttributes() {
        generateBDSFromXSD("AttributeAnys"); //$NON-NLS-1$
    }

    public void testNamedAnyAttribute() {
        generateBDSFromXSD("NamedAnyAttribute"); //$NON-NLS-1$
    }

}
