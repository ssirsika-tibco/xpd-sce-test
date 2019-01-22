/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.bom.test.transform.util.TopLevelElementValidator;

/**
 * This extends the {@link XSDBOM_TLE02_ComplexElementType} test to test
 * multiple top-level elements that are of the same complex type.
 * <p>
 * <i> Tests 3.4 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </i>
 * </p>
 * 
 * @author njpatel
 * 
 */
public class XSDBOM_TLE03_MultipleElementsOfSameComplexType extends
        XSDBOM_TLE02_ComplexElementType {

    public XSDBOM_TLE03_MultipleElementsOfSameComplexType() {
        super("XSDBOM_TLE03_MultipleElementsOfSameComplexType.xsd");
    }

    @Override
    protected void validateAppliedStereotypes(Class cl) {
        TopLevelElementValidator validator = new TopLevelElementValidator(cl.getModel(),4);
      
        validator.isValid("PersonElement1",
                "elem1",
                null,
                null,
                false,
                false,
                null,
                null,
                null);

        validator.isValid("PersonElement2",
                null,
                null,
                null,
                true,
                false,
                "#all",
                null,
                null);

        validator.isValid("PersonElement3",
                null,
                null,
                null,
                false,
                false,
                null,
                null,
                null);

        validator.isValid("PersonElement4",
                null,
                null,
                null,
                null,
                null,
                "#all",
                null,
                null);
    }
}
