/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;

import com.tibco.xpd.bom.test.transform.util.TransformUtil;

/**
 * Tests to ensure that target namespaces with invalid chars are stripped in
 * both model and top level element stereotype values so upon export the top
 * level elements can be properly put in their right schema.
 * <p>
 * Tests 3.11 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </p>
 * 
 * @author glewis
 * 
 */
public class XSDBOM_TLE17_InvalidCharsInTargetNamespace extends AbstractTLETest {

    private static final String CLASS1 = "GenericOutputIO"; //$NON-NLS-1$

    private static final String CLASS2 = "GenericOutputIOTopElmt"; //$NON-NLS-1$

    public XSDBOM_TLE17_InvalidCharsInTargetNamespace() {
        super("XSDBOM_TLE17_InvalidCharsInTargetNamespace.xsd"); //$NON-NLS-1$
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        assertEquals("Number of packageable elements", 3, packagedElements
                .size()); //$NON-NLS-1$

        TransformUtil.assertTargetNamespace(model,
                "http://www.tibco.com/xml/DOMM20Cambio20Prodotto20Input"); //$NON-NLS-1$

        TransformUtil.assertPackagedElementClass(packagedElements, CLASS1);

        TransformUtil.assertPackagedElementClass(packagedElements, CLASS2);
    }
}
