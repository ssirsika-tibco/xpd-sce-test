/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.validation;

import com.tibco.xpd.xpdl2.Description;

/**
 * A sample validator interface for {@link com.tibco.xpd.xpdl2.DescribedElement}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface DescribedElementValidator {
    boolean validate();

    boolean validateDescription(Description value);

    boolean validateDescription(String value);
}
