/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.validation;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;

/**
 * A sample validator interface for {@link com.tibco.xpd.xpdExtension.ScriptInformation}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface ScriptInformationValidator {
    boolean validate();

    boolean validateExpression(Expression value);

    boolean validateDirection(DirectionType value);

    boolean validateActivity(Activity value);

    boolean validateReference(boolean value);
}
