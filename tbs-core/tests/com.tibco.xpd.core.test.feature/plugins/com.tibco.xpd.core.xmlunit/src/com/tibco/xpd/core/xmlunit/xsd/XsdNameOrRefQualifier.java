package com.tibco.xpd.core.xmlunit.xsd;

import org.custommonkey.xmlunit.ElementQualifier;
import org.w3c.dom.Element;

/**
 * xsd:element list comparison qualifier (add to xmlDiff to allow xsd:element
 * list to be ordered different as long as same @name'd / or
 * 
 * @ref'd elements are present and the same.
 * 
 * @author aallway
 * @since 15 Apr 2011
 */
public class XsdNameOrRefQualifier implements ElementQualifier {

    public boolean qualifyForComparison(Element control, Element test) {
        /*
         * xsd:element's with the same @name or same
         * 
         * @ref are equivalent for comparison
         */
        String controlName = control.getAttribute("name");
        if (controlName != null && controlName.length() > 0) {
            String testName = test.getAttribute("name");

            if (controlName.equals(testName)) {
                return true;
            }
        }

        String controlRef = control.getAttribute("ref");
        if (controlRef != null && controlRef.length() > 0) {
            String testRef = test.getAttribute("ref");

            if (controlRef.equals(testRef)) {
                return true;
            }
        }

        return false;
    }
}