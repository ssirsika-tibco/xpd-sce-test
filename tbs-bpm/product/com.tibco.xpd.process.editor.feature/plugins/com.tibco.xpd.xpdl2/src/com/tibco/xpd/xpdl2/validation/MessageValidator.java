/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.validation;

import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link com.tibco.xpd.xpdl2.Message}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface MessageValidator {
    boolean validate();

    boolean validateActualParameters(EList value);

    boolean validateDataMappings(EList value);

    boolean validateFaultName(String value);

    boolean validateFrom(String value);

    boolean validateTo(String value);
}
