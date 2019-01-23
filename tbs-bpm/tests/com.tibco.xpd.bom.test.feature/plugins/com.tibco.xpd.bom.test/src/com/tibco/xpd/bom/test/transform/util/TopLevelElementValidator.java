/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.util;

import java.util.List;

import junit.framework.Assert;

import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper;
import com.tibco.xpd.bom.xsdtransform.utils.TopLevelElementWrapper;

/**
 * A class to encapsulate JUnit validation of a UML2 PrimitiveType of type Text.<br>
 * </br> Once the classs is initialized with the element to be tested, the
 * isValid method can be called with expected values as its parameters.
 * 
 * @author rgreen
 * 
 */
public class TopLevelElementValidator {

    private List<TopLevelElementWrapper> elementWrappers;

    public TopLevelElementValidator(Package tmpPackage,
            int expectedNumOfXsdElements) {

        elementWrappers = TransformHelper.getTopLevelElementWrappers(tmpPackage);

        Assert.assertNotNull("XsdTopLevelElement stereotype not set",
                elementWrappers);

        Assert.assertTrue("Number of XsdElements set:",
                elementWrappers.size() == expectedNumOfXsdElements);
    }

    /**
     * @param expName
     *            name of the element - cannot be <code>null</code>.
     * @param expId
     * @param expDefault
     * @param expFixed
     * @param expNillable
     *            if this is <code>null</code> then this value be assumed
     *            <code>false</code>.
     * @param expAbstract
     *            if this is <code>null</code> then this value be assumed
     *            <code>false</code>.
     * @param expFinal
     * @param expBlock
     * @param expSubstGroup
     * @return true, or if assertions fail will throw JUnit exception.
     */
    public boolean isValid(String expName, String expId, String expDefault,
            String expFixed, Boolean expNillable, Boolean expAbstract,
            String expFinal, String expBlock, String expSubstGroup) {

        Assert.assertNotNull("Name cannot be null", expName);

        TopLevelElementWrapper wrapper = findElement(expName, expId);

        Assert.assertNotNull(expId != null ? String
                .format("XSD Element with name '%s' and id '%s' not found.",
                        expName,
                        expId) : String
                .format("XSD Element with name '%s' not found.", expName),
                wrapper);

        /*
         * We should test all values for null, the test should not be omitted if
         * null is passed as the parameter - this is so that we can test for
         * when we expect the result to be null.
         */

        Assert.assertEquals("XsdTopLevelElement default:", expDefault, wrapper
                .getDefault());

        Assert.assertEquals("XsdTopLevelElement fixed:", expFixed, wrapper
                .getFixed());

        Assert.assertEquals("XsdTopLevelElement nillable:",
                expNillable != null ? expNillable : Boolean.FALSE,
                wrapper.getNillable());

        Assert.assertEquals("XsdTopLevelElement abstract:",
                expAbstract != null ? expAbstract : Boolean.FALSE,
                wrapper.getAbstract());

        Assert.assertEquals("XsdTopLevelElement final:", expFinal, wrapper
                .getFinal());

        Assert.assertEquals("XsdTopLevelElement block:", expBlock, wrapper
                .getBlock());

        Assert.assertEquals("XsdTopLevelElement substitutionGroup:",
                expSubstGroup,
                wrapper.getSubstitutionGroup());

        return true;

    }

    /**
     * Find the element with the given name. If the id is provided then that
     * will be matched too to find the element.
     * 
     * @param expName
     * @param expId
     * @return
     */
    private TopLevelElementWrapper findElement(String expName, String expId) {
        for (TopLevelElementWrapper elem : elementWrappers) {            
            if (expName.equals(elem.getName())
                    && (expId == null || expId.equals(elem.getID()))) {
                return elem;
            }
        }
        return null;
    }

}
