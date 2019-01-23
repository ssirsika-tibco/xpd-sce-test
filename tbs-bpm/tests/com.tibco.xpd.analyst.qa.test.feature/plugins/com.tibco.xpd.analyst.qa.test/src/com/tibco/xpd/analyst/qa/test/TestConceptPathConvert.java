/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.analyst.qa.test;

import junit.framework.TestCase;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;

/**
 * TestConceptPathConvert
 * 
 * 
 * @author aallway
 * @since 3.3 (17 Sep 2009)
 */
public class TestConceptPathConvert extends TestCase {

    private String[] controlPaths =
            {
                    "simpleField", //$NON-NLS-1$
                    "simpleField/@Attribute", //$NON-NLS-1$
                    "simpleField/BOMChild/@Attribute", //$NON-NLS-1$
                    "simpleField/BOMChild/BOMGrandChild", //$NON-NLS-1$
                    "simpleField/BOMChildSequence[1]/BOMGrandChild/@Attribute", //$NON-NLS-1$
                    "simpleField/BOMChildSequence[2]/BOMGrandChild/@Attribute", //$NON-NLS-1$
                    "simpleField/BOMChildSequence[3]/BOMGrandChildSequence[1]/@Attribute", //$NON-NLS-1$
                    "simpleField/@Attribute + simpleField/BOMChildSequence[1]/BOMGrandChild/@Attribute", //$NON-NLS-1$

                    // Badly formed should stay as they were.
                    "simpleField/BadDefinedSequence[1/BOMGrandChildSequence[1]/@Attribute", //$NON-NLS-1$
                    "simpleField/BadDefinedSequence2[aa]/BOMGrandChildSequence[1]/@Attribute", //$NON-NLS-1$
            };

    private String[] testPaths =
            {
                    "simpleField", //$NON-NLS-1$
                    "simpleField.Attribute", //$NON-NLS-1$
                    "simpleField.BOMChild.Attribute", //$NON-NLS-1$
                    "simpleField.BOMChild.BOMGrandChild", //$NON-NLS-1$
                    "simpleField.BOMChildSequence[0].BOMGrandChild.Attribute", //$NON-NLS-1$
                    "simpleField.BOMChildSequence[1].BOMGrandChild.Attribute", //$NON-NLS-1$
                    "simpleField.BOMChildSequence[2].BOMGrandChildSequence[0].Attribute", //$NON-NLS-1$
                    "simpleField.Attribute + simpleField.BOMChildSequence[0].BOMGrandChild.Attribute", //$NON-NLS-1$

                    // Badly formed should stay as they were.
                    "simpleField/BadDefinedSequence[1/BOMGrandChildSequence[1]/@Attribute", //$NON-NLS-1$
                    "simpleField/BadDefinedSequence2[aa]/BOMGrandChildSequence[1]/@Attribute", //$NON-NLS-1$
            };

    public void testV32ConceptPathConvert() throws Exception {

        for (int i = 0; i < controlPaths.length; i++) {
            String convert =
                    ProcessDataUtil.convertV32ConceptPath(controlPaths[i]);

            assertEquals(testPaths[i], convert);

        }

    }

}
