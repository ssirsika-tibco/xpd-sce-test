/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.validation;

import com.tibco.xpd.xpdl2.Coordinates;

/**
 * A sample validator interface for {@link com.tibco.xpd.xpdl2.NodeGraphicsInfo}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface NodeGraphicsInfoValidator {
    boolean validate();

    boolean validateCoordinates(Coordinates value);

    boolean validateBorderColor(String value);

    boolean validateFillColor(String value);

    boolean validateHeight(double value);

    boolean validateIsVisible(boolean value);

    boolean validateLaneId(String value);

    boolean validatePage(String value);

    boolean validateShape(String value);

    boolean validateToolId(String value);

    boolean validateWidth(double value);

    boolean validatePageId(String value);
}
