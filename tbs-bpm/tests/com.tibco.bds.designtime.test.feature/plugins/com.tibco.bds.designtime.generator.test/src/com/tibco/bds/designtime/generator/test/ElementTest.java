/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.bds.designtime.generator.test;


/**
 * This class tests the generation of the BDS package when passed an XSD that
 * contains elements
 * 
 */
public class ElementTest extends AbstractXsdBasedTest {

    public ElementTest() {
        super("elements"); //$NON-NLS-1$
    }

    /**
     * Basic test that just checks the creation of additional elements
     */
    public void testBasicComplexType() {
        generateBDSFromXSD("ElmAndComplex"); //$NON-NLS-1$
    }

    /**
     * Tests the case where there are no elements to be added as everything is
     * already there
     */
    public void testNoAdditionsRequired() {
        generateBDSFromXSD("MatchingElemAndComplex"); //$NON-NLS-1$
    }

    /**
     * Tests the case where there are multiple complex types, each with multiple
     * elements where one complex type is an extension of another
     */
    public void testMultipleElmDiffComplex() {
        generateBDSFromXSD("MultipleElmDiffComplex"); //$NON-NLS-1$
    }

    /**
     * Tests the handling on anonymous complex types
     */
    public void testAnonComplex() {
        generateBDSFromXSD("AnonComplex"); //$NON-NLS-1$
    }

    /**
     * Tests the case where one anonymous complex type is nested inside another
     */
    public void testNestedAnonComplex() {
        generateBDSFromXSD("NestedAnonComplex"); //$NON-NLS-1$
    }

    /**
     * Tests the case where a complex object references a root element
     */
    public void testRefRootElement() {
        generateBDSFromXSD("RefRootElement"); //$NON-NLS-1$
    }

    /**
     * Tests the case where a complex object references a root element of
     * type-complex
     */
    public void testRefComplexRootElement() {
        generateBDSFromXSD("RefComplexRootElement"); //$NON-NLS-1$
    }

    /**
     * Tests that if the internal key work is used as a name that the conversion
     * still works
     */
    public void testKeyWordNameClash() {
        generateBDSFromXSD("KeyWordNameClash"); //$NON-NLS-1$
    }

    /**
     * Tests the behaviour when there is already a DocumentRoot named type
     */
    public void testDocumentRootNameClash() {
        generateBDSFromXSD("DocumentRootNameClash"); //$NON-NLS-1$
    }

    /**
     * Test that elements with different cases are handled as different
     */
    public void testCaseDifferenceMatch() {
        generateBDSFromXSD("CaseDifferenceMatch"); //$NON-NLS-1$
    }

    /**
     * Test that elements with different cases are handled as different
     */
    public void testDeclaredTypes() {
        generateBDSFromXSD("elementDeclaredTypes"); //$NON-NLS-1$
    }

    /**
     * Test when there are name clashes within elements
     */
    public void testNameClashes() {
        generateBDSFromXSD("NameClashes"); //$NON-NLS-1$
    }

    /**
     * Test that when a complex object has the name DocumentRoot, that when BDS
     * creates it's own document root, it gets correctly renamed
     */
    public void testDocRootRename() {
        generateBDSFromXSD("DocumentRootRename"); //$NON-NLS-1$
    }

    /**
     * Test the case where there is a recursive reference to the same anonymous
     * object
     */
    public void testRecursiveRef() {
        generateBDSFromXSD("RecursiveRef"); //$NON-NLS-1$
    }

    /**
     * Schema contains non-standard names for various things via ecore:name.
     * This is to test that we honour these correctly, so verifies that we're
     * not accidentally ignoring them and applying standard logic.
     */
    public void testEcoreNames() {
        generateBDSFromXSD("EcoreNames"); //$NON-NLS-1$
    }
}
