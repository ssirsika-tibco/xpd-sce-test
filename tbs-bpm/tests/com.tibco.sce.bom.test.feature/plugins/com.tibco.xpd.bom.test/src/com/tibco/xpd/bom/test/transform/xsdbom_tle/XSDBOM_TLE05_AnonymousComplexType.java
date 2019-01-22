package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.test.transform.util.PrimitiveTypeTextValidator;

/**
 * Tests an anonymous complex type top-level element.
 * <p>
 * Tests 3.6 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </p>
 * 
 * @author njpatel
 */
public class XSDBOM_TLE05_AnonymousComplexType extends AbstractTLETest {

    private static final String TOPELEMENT_CLASS = "topElementType";

    private static final String NAME_ATTR = "name";

    public XSDBOM_TLE05_AnonymousComplexType() {
        super("XSDBOM_TLE05_AnonymousComplexType.xsd");
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
        /*
         * Expect one class
         */
        assertEquals("Number of classes", 1, model.getPackagedElements().size());
        assertTrue("Expected the only element to be a Class", model
                .getPackagedElements().get(0) instanceof Class);

        checkClass((Class) model.getPackagedElements().get(0));
    }

    /**
     * Validate the top level element class.
     * 
     * @param cl
     */
    private void checkClass(Class cl) {
        assertEquals("Name of class", TOPELEMENT_CLASS, cl.getName());
        assertEquals("Number of attributes", 1, cl.getAttributes().size());
        assertEquals("Number of Operations", 0, cl.getOperations().size());

        Property property = cl.getAttributes().get(0);
        assertEquals("Name of Property", NAME_ATTR, property.getName());
        assertTrue("Expected the attribute type to be a PrimitiveType",
                property.getType() instanceof PrimitiveType);
        checkMultiplicity(property, 1, 1);

        new PrimitiveTypeTextValidator((PrimitiveType) property.getType())
                .isValid(null, null, null, null);
    }

}
