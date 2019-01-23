/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.test.transform.util.TransformUtil;

/**
 * 
 * Tests the simplest form of top level element of type xsd base type.
 * <p>
 * &lt;xsd:element name="testStringData" type="xsd:string"&gt;
 * </p>
 * The schema used in this test contains a top level element for each supported
 * xsd base types <br>
 * </br>
 * <p>
 * <i> Tests 3.1 and 3.2 as described in 'BDS Support for Handling - Studio BOM
 * Import'. </i>
 * </p>
 * 
 * @author glewis
 */
public class XSDBOM_TLE21_TopLevelWithAnonymousMinMaxAttributes extends
        AbstractTLETest {

    public XSDBOM_TLE21_TopLevelWithAnonymousMinMaxAttributes() {
        super("XSDBOM_TLE21_TopLevelWithAnonymousMinMaxAttributes.xsd");
    }

    protected void checkBOMElements(Model model) throws Exception {

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        checkClassez(packagedElements);

    }

    private void checkClassez(EList<PackageableElement> packagedElements)
            throws Exception {

        assertEquals("Number of Classes expected", 5, packagedElements.size());

        // check classes
        Class bucketTypesCls =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "BucketsType");
        Class bucketTypeCls =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "BucketType");

        TransformUtil.assertPackagedElementClass(packagedElements, "Price");

        // check attributes
        Property bucketProp =
                TransformUtil.assertAttributeInClass(bucketTypesCls
                        .getAllAttributes(), "bucket", null);
        Property elem2Prop =
                TransformUtil.assertAttributeInClass(bucketTypesCls
                        .getAllAttributes(), "elem2", null);
        Property averagesProp =
                TransformUtil.assertAttributeInClass(bucketTypeCls
                        .getAllAttributes(), "averages", null);

        // check multiplicity
        TransformUtil.assertPropertyMultiplicity(bucketProp, 0, -1);
        TransformUtil.assertPropertyMultiplicity(elem2Prop, 1, 6);
        TransformUtil.assertPropertyMultiplicity(averagesProp, 0, 1);
    }

}
