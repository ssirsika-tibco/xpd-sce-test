/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.bds.designtime.generator.test;


/**
 * This test class tests the "Other COnstructs" these are tests for simple
 * constructs that only require one or two test cases
 * 
 */
public class OtherConstructsTest extends AbstractXsdBasedTest {

    public OtherConstructsTest() {
        super("other_constructs");         //$NON-NLS-1$
    }
    
    /**
     * Basic test that just checks the support of xsd:any
     */
    public void testSimpleAll() {
        generateBDSFromXSD("SimpleAll"); //$NON-NLS-1$
    }

    public void testAbstractElement() {
        generateBDSFromXSD("AbstractElement"); //$NON-NLS-1$
    }

    public void testAbstractComplex() {
        generateBDSFromXSD("AbstractComplex"); //$NON-NLS-1$
    }

    public void testSubstitutionGroup() {
        generateBDSFromXSD("SubstitutionGroup"); //$NON-NLS-1$
    }

    public void testMixedContent() {
        generateBDSFromXSD("MixedContent"); //$NON-NLS-1$
    }

    public void testUnionElement() {
        generateBDSFromXSD("UnionElement"); //$NON-NLS-1$
    }

    public void testUnionUsage() {
        generateBDSFromXSD("UnionUsage"); //$NON-NLS-1$
    }

    public void testNonUniqueUnion() {
        generateBDSFromXSD("NonUniqueUnion"); //$NON-NLS-1$
    }
    
    public void testMultipleOccursChoice() {
        generateBDSFromXSD("MultipleOccursChoice"); //$NON-NLS-1$
    }

    public void testMultipleOccursSequence() {
        generateBDSFromXSD("MultipleOccursSequence"); //$NON-NLS-1$
    }
    
    public void testComplexRestriction() {
        generateBDSFromXSD("ComplexRestriction"); //$NON-NLS-1$
    }
}
